---
name: git-github-ops
description: "Handles Pokedexer Git and GitHub workflows: inspect changes, prepare commit messages, manage branches and pushes, and create or update issues and pull requests with safe file-based inputs. Use for any git or gh operation."
---

# Git and GitHub Operations

Handle Git and GitHub work from the real repository state while respecting the approval gates in `AGENTS.md`.

## Operating Rules

- Read `AGENTS.md` and `docs/CONVENTIONS.md` before mutating Git or GitHub state.
- Read-only inspection is allowed without approval.
- Obtain explicit user approval before persistently changing repository history, branches, worktrees, remotes, issues,
  or pull requests. Approval for one operation does not imply approval for later operations.
- Never discard or rewrite work that the user or another agent may own.
- Use `gh` for GitHub operations and explicit, non-interactive flags.
- Keep each shell command focused on one purpose.
- Put multiline commit, issue, PR, and comment text in a file; pass the file to `git` or `gh` instead of embedding it
  in a shell command.
- Keep labels in GitHub metadata. Never add a `Labels` section to an issue body.

## Commit Message Contract

Derive every claim from `git diff --staged`.

- Describe the concrete change and why it matters.
- Do not invent issue IDs, names, emails, or other metadata.
- Do not use typed Conventional Commit prefixes (`feat:`, `fix:`, `docs:`, `chore:`, `refactor:`, or scoped
  variants). If one area is the clear focus, a bare area label is acceptable: `search: avoid stale results`.
- Use an imperative subject no longer than 72 characters.
- Add a blank line before the body and wrap body lines at 72 characters.

Use this shape:

```text
<imperative subject, <= 72 characters>

- <specific diff-derived change>
- <specific diff-derived change>
- <validation performed>
```

## Commit Workflow

1. Run `git status --short` and inspect `git diff`.
2. Run the relevant targeted tests.
3. If the change includes `.kt` or `.kts` files, run `./gradlew ktfmtFormat`, then inspect the diff again.
4. Stage only files in scope.
5. Inspect `git diff --staged`.
6. Write the message to a temporary file, optionally starting from `templates/commit-message.txt`.
7. After explicit commit approval, run `git commit -F /tmp/pokedexer-commit-message.txt`.

Do not amend, reset, rebase, or otherwise rewrite a commit unless the user explicitly approves that exact operation.

## Branch and Push Workflow

- Use descriptive branch names such as `fix/search-race` or `feat/item-details`.
- Before creating or switching branches, inspect the current branch, status, and worktree list.
- Never pull, merge, or rebase merely to make a branch current. Report the state and ask which update strategy the user
  wants.
- Before every push or PR, run `./gradlew check` and confirm it passes. If it fails, stop unless every pre-existing check
  failure exception requirement in `AGENTS.md` is documented and satisfied.
- Push the explicit branch name; use `git push --set-upstream origin <branch>` for its first push.
- Never push directly to the default branch unless the user explicitly requests that exact action.

Every branch creation or deletion, pull, merge, rebase, push, and worktree mutation requires explicit approval under
`AGENTS.md`.

## Pull Request Workflow

Use a focused, review-friendly title:

```text
<area>: <what changed and why it matters>
```

Keep it near 72 characters and describe the outcome rather than the process.

Write the PR body to a temporary Markdown file, optionally starting from `templates/pr-description.md`, then use:

```bash
gh pr create --title "<title>" --body-file /tmp/pokedexer-pr-body.md --base <default-branch> --head <branch>
```

Opening, editing, closing, or merging a PR requires explicit user approval. A request to implement a change does not
implicitly authorize opening a PR.

For PR comments and review replies, also use file inputs:

```bash
gh pr comment <pr-number> --body-file /tmp/pokedexer-pr-comment.md
```

## Issue Workflow

Before selecting labels, inspect the repository's current labels with `gh label list`. Create issue text from
`templates/issue-body.md`, then use a file input:

```bash
gh issue create --title "<title>" --body-file /tmp/pokedexer-issue-body.md --label "<validated-label>"
```

Creating, editing, labeling, commenting on, closing, or reopening an issue requires explicit user approval.

## Safety Checklist

Before an approval-gated Git or GitHub mutation:

- Confirm the user approved the exact operation.
- Confirm the active checkout, branch, and worktree are the intended targets.
- Confirm unrelated changes are excluded and preserved.
- Confirm TDD evidence exists for behavior changes.
- Before commits, confirm formatting and targeted tests pass.
- Before pushes and PRs, confirm `./gradlew check` passes or document how every `AGENTS.md` pre-existing failure
  exception requirement is satisfied.

## Handoff

Report only operations actually completed, including:

- branch and commit subject, if created;
- push destination, if pushed;
- issue or PR link, if created or updated;
- tests and checks run;
- approvals or unresolved failures still pending.
