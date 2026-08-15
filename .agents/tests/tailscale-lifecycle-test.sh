#!/usr/bin/env bash
set -euo pipefail

readonly REPO_ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/../.." && pwd)"
readonly TAILSCALE_HELPER="$REPO_ROOT/.agents/tailscale"
readonly TEST_ROOT="$(mktemp -d)"
readonly FAKE_BIN="$TEST_ROOT/bin"
readonly COMMAND_LOG="$TEST_ROOT/commands.log"
readonly STATE_FILE="$TEST_ROOT/tailscale-state"
readonly STATUS_SEQUENCE_FILE="$TEST_ROOT/status-sequence"
readonly DROP_IN="$TEST_ROOT/etc/tailscaled.service.d/amp-orb.conf"
readonly NODE_STATE="$TEST_ROOT/var/tailscaled.state"

trap 'rm -rf "$TEST_ROOT"' EXIT
mkdir -p "$FAKE_BIN"
printf 'NeedsLogin\n' > "$STATE_FILE"

cat > "$FAKE_BIN/sudo" <<'EOF'
#!/usr/bin/env bash
set -euo pipefail
printf 'sudo %s\n' "$*" >> "$COMMAND_LOG"
exec "$@"
EOF

cat > "$FAKE_BIN/systemctl" <<'EOF'
#!/usr/bin/env bash
set -euo pipefail
printf 'systemctl %s\n' "$*" >> "$COMMAND_LOG"
EOF

cat > "$FAKE_BIN/tailscale" <<'EOF'
#!/usr/bin/env bash
set -euo pipefail
printf 'tailscale %s\n' "$*" >> "$COMMAND_LOG"
case "${1:-}" in
    status)
        state="$(cat "$STATE_FILE")"
        if [[ -s "$STATUS_SEQUENCE_FILE" ]]; then
            state="$(head -n 1 "$STATUS_SEQUENCE_FILE")"
            tail -n +2 "$STATUS_SEQUENCE_FILE" > "$STATUS_SEQUENCE_FILE.next"
            mv "$STATUS_SEQUENCE_FILE.next" "$STATUS_SEQUENCE_FILE"
        fi
        printf '{"BackendState":"%s","Self":{"Online":%s}}\n' \
            "$state" \
            "$([[ "$state" == "Running" ]] && printf true || printf false)"
        ;;
    up)
        printf 'Running\n' > "$STATE_FILE"
        ;;
esac
EOF

cat > "$FAKE_BIN/amp" <<'EOF'
#!/usr/bin/env bash
set -euo pipefail
printf 'amp %s\n' "$*" >> "$COMMAND_LOG"
if [[ "${AMP_ID_TOKEN_FAIL:-false}" == "true" ]]; then
    echo "OIDC unavailable" >&2
    exit 1
fi
printf 'fake-oidc-token\n'
EOF

cat > "$FAKE_BIN/hostname" <<'EOF'
#!/usr/bin/env bash
printf 'test-orb\n'
EOF

chmod +x "$FAKE_BIN"/*

export COMMAND_LOG STATE_FILE STATUS_SEQUENCE_FILE
export PATH="$FAKE_BIN:/usr/bin:/bin"
export TAILSCALE_CLIENT_ID="test-client"
export TAILSCALE_AUDIENCE="api.tailscale.com/test-client"
export TAILSCALE_TAG="tag:amp-pokedexer-adb"
export TAILSCALE_DROP_IN="$DROP_IN"
export TAILSCALE_STATE="$NODE_STATE"

fail() {
    echo "FAIL: $*" >&2
    exit 1
}

assert_log_contains() {
    local expected="$1"
    grep -Fq -- "$expected" "$COMMAND_LOG" || fail "command log does not contain: $expected"
}

assert_log_excludes() {
    local unexpected="$1"
    if grep -Fq -- "$unexpected" "$COMMAND_LOG"; then
        fail "command log unexpectedly contains: $unexpected"
    fi
}

test_install_removes_snapshot_node_identity() {
    : > "$COMMAND_LOG"
    mkdir -p "$(dirname "$NODE_STATE")"
    printf 'authenticated-node-state\n' > "$NODE_STATE"

    "$TAILSCALE_HELPER" install > "$TEST_ROOT/install.out"

    [[ -f "$DROP_IN" ]] || fail "install did not write the systemd drop-in"
    [[ ! -e "$NODE_STATE" ]] || fail "install left authenticated node state in the snapshot"
    assert_log_contains "systemctl stop tailscaled"
    assert_log_excludes "amp orb id-token"
    assert_log_excludes "tailscale up"
}

test_resume_reconnects_without_oidc() {
    : > "$COMMAND_LOG"
    printf 'Running\n' > "$STATE_FILE"

    "$TAILSCALE_HELPER" resume > "$TEST_ROOT/resume.out"

    assert_log_contains "systemctl restart tailscaled"
    assert_log_contains "tailscale status --json"
    assert_log_excludes "amp orb id-token"
    assert_log_excludes "tailscale up"
}

test_unauthenticated_resume_requests_explicit_join() {
    : > "$COMMAND_LOG"
    printf 'NeedsLogin\n' > "$STATE_FILE"

    "$TAILSCALE_HELPER" resume > "$TEST_ROOT/needs-login.out"

    grep -Fq './.agents/tailscale join' "$TEST_ROOT/needs-login.out" ||
        fail "resume did not explain the explicit first-join command"
    assert_log_excludes "amp orb id-token"
    assert_log_excludes "tailscale up"
}

test_join_uses_non_ephemeral_oidc_registration() {
    : > "$COMMAND_LOG"
    printf 'NeedsLogin\n' > "$STATE_FILE"

    "$TAILSCALE_HELPER" join > "$TEST_ROOT/join.out"

    assert_log_contains "amp orb id-token --audience $TAILSCALE_AUDIENCE"
    assert_log_contains "tailscale up"
    assert_log_contains "--client-id=${TAILSCALE_CLIENT_ID}?ephemeral=false&preauthorized=true"
    assert_log_contains "--advertise-tags=$TAILSCALE_TAG"
    assert_log_excludes "ephemeral=true"
    if grep -Fq 'fake-oidc-token' "$TEST_ROOT/join.out"; then
        fail "join printed the OIDC token"
    fi
}

test_join_is_idempotent_when_already_running() {
    : > "$COMMAND_LOG"
    printf 'Running\n' > "$STATE_FILE"
    printf 'Starting\nRunning\n' > "$STATUS_SEQUENCE_FILE"

    "$TAILSCALE_HELPER" join > "$TEST_ROOT/already-joined.out"

    grep -Fq 'already joined' "$TEST_ROOT/already-joined.out" ||
        fail "join did not report the existing connection"
    assert_log_excludes "amp orb id-token"
    assert_log_excludes "tailscale up"
}

test_oidc_failure_does_not_attempt_registration() {
    : > "$COMMAND_LOG"
    printf 'NeedsLogin\n' > "$STATE_FILE"

    if AMP_ID_TOKEN_FAIL=true "$TAILSCALE_HELPER" join > "$TEST_ROOT/oidc-failure.out" 2>&1; then
        fail "join succeeded after OIDC token acquisition failed"
    fi

    assert_log_contains "amp orb id-token --audience $TAILSCALE_AUDIENCE"
    assert_log_excludes "tailscale up"
    if grep -Fq 'fake-oidc-token' "$TEST_ROOT/oidc-failure.out"; then
        fail "failed join printed an OIDC token"
    fi
}

test_join_requires_federation_configuration() {
    : > "$COMMAND_LOG"
    printf 'NeedsLogin\n' > "$STATE_FILE"

    if env -u TAILSCALE_CLIENT_ID "$TAILSCALE_HELPER" join > "$TEST_ROOT/missing-client.out" 2>&1; then
        fail "join succeeded without TAILSCALE_CLIENT_ID"
    fi
    if env -u TAILSCALE_AUDIENCE "$TAILSCALE_HELPER" join > "$TEST_ROOT/missing-audience.out" 2>&1; then
        fail "join succeeded without TAILSCALE_AUDIENCE"
    fi

    grep -Fq 'Set TAILSCALE_CLIENT_ID' "$TEST_ROOT/missing-client.out" ||
        fail "join did not explain the missing client ID"
    grep -Fq 'Set TAILSCALE_AUDIENCE' "$TEST_ROOT/missing-audience.out" ||
        fail "join did not explain the missing audience"
    assert_log_excludes "amp orb id-token"
    assert_log_excludes "tailscale up"
}

test_unknown_backend_fails_resume() {
    : > "$COMMAND_LOG"
    printf 'Stopped\n' > "$STATE_FILE"

    if "$TAILSCALE_HELPER" resume > "$TEST_ROOT/stopped.out" 2>&1; then
        fail "resume succeeded while the backend was stopped"
    fi

    grep -Fq 'backend state is Stopped' "$TEST_ROOT/stopped.out" ||
        fail "resume did not report the stopped backend"
    assert_log_excludes "amp orb id-token"
    assert_log_excludes "tailscale up"
}

test_install_removes_snapshot_node_identity
test_resume_reconnects_without_oidc
test_unauthenticated_resume_requests_explicit_join
test_join_uses_non_ephemeral_oidc_registration
test_join_is_idempotent_when_already_running
test_oidc_failure_does_not_attempt_registration
test_join_requires_federation_configuration
test_unknown_backend_fails_resume

echo "Tailscale lifecycle tests passed."
