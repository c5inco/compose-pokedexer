---
name: using-git-worktree
description: "Creates and manages isolated Git worktrees with safe directory selection, explicit approval, clean-baseline verification, and Gradle-specific safeguards. Use before feature work when the main checkout should remain clean or whenever a worktree is requested."
---

# Using Git Worktrees

Use systematic directory selection and safety verification to isolate task work without disturbing another checkout.
Also follow `AGENTS.md` and the `git-github-ops` skill for every Git operation.

## Preflight: Check Whether Isolation Already Exists

Before asking about or creating a worktree:

1. Run `git rev-parse --git-common-dir` and `git rev-parse --git-dir`. Different results mean the current checkout is
   already a linked worktree.
2. Run `git branch --show-current` and identify the default branch with
   `git symbolic-ref --short refs/remotes/origin/HEAD` when available.
3. Treat a named non-default branch as existing task isolation. A detached `HEAD` is not task isolation.

If either a linked worktree or a non-default feature branch is active, report its path and branch, then stop this
workflow. Do not create nested or redundant isolation.

If the user already requested a worktree, treat that request as approval. Otherwise, when the session is in the main
checkout on the default branch, ask the worktree question prescribed by `AGENTS.md`. Continue only with explicit user
agreement; it authorizes the worktree and branch creation described in the prompt, but no unrelated Git mutation.

## Select and Verify the Directory

Use this priority:

1. Existing `.worktrees/` directory.
2. Existing `worktrees/` directory if project policy explicitly allows it.
3. Otherwise, project-local `.worktrees/`.

Before creation, verify the selected directory is ignored:

```bash
git check-ignore -q .worktrees/
```

If it is not ignored, stop. Ask for approval to add it to `.gitignore`, or choose an already ignored external
location. Do not create a project-local worktree whose directory can appear as untracked content.

## Choose the Branch and Base

1. Choose a descriptive task branch such as `fix/search-race` or `feat/item-details`.
2. Inspect the current branch and the remote's default branch.
3. If currently on the default branch, use the current `HEAD` as the base unless the user asks for another base.
4. If currently on another branch, ask whether the worktree should start from that branch, the default branch, or a
   different ref.
5. Inspect whether the selected base is behind its remote-tracking ref. Never fetch, pull, merge, or rebase without
   the explicit approval required by `AGENTS.md`; report stale state and ask how to proceed.

## Create the Worktree

Resolve an absolute target path under `.worktrees/`, then create the branch and worktree in one operation:

```bash
git worktree add <absolute-path> -b <branch-name> <base-ref>
```

For all subsequent tools and commands, use the worktree's absolute path or set the tool's working directory to that
path. Do not assume a `cd` in one shell invocation changes later tool calls.

## Verify a Clean Baseline

From the new worktree:

1. Run `git status --short` and confirm the checkout is clean.
2. Run `./gradlew test` as the baseline test suite.
3. If the baseline fails, report the exact failure and ask whether to investigate or proceed. Do not silently attribute
   a pre-existing failure to later work.
4. If it passes, report the absolute worktree path, branch, base ref, and successful command.

## Gradle and Formatting Safeguards

- Use the Gradle tasks configured by this repository, not a separately installed ktfmt CLI.
- If the change includes `.kt` or `.kts` files, run `./gradlew ktfmtFormat` before committing and inspect any
  formatting changes.
- Run targeted tests during development and `./gradlew check` before every push or PR. If it fails, follow the
  pre-existing check failure exception in `AGENTS.md`; never infer an exception from worktree isolation alone.
- After an approved rebase or merge, find the merge base, inspect the branch diff, and verify that key changes survived
  before rerunning `./gradlew check`.
- Treat `.gradle/`, `build/`, `.kotlin/`, IDE state, and Android SDK configuration as checkout-local or ignored state;
  never stage them as part of task work.

## Cleanup

Worktree removal, branch deletion, and destructive cleanup require explicit user approval. Perform cleanup from the
main checkout, never from inside the worktree being removed:

```bash
git worktree list --porcelain
git worktree remove <absolute-worktree-path>
git branch -d <branch-name>
```

Before removal, inspect `git status --short` in the target worktree. Do not use `--force`, `git branch -D`, `rm -rf`,
or another destructive fallback unless the user explicitly confirms the affected changes are disposable.

If cleanup fails, diagnose the current registration, branch attachment, and local changes before selecting a recovery
action. Never retry destructively by default.

## Invariants

Always:

- skip creation when isolation already exists;
- obtain approval before worktree or branch mutations;
- verify project-local worktrees are ignored;
- select the base branch explicitly;
- use the worktree path for every later operation;
- verify a clean baseline;
- run and resolve `./gradlew check`, or satisfy the documented pre-existing failure exception, before pushing;
- preserve all uncommitted work during cleanup.
