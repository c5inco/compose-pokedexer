---
name: acli
description: Control the Android development environment: manage SDK packages, start/stop emulators, install APKs, stream device logs, take screenshots, and run Gradle builds. Use when the user wants to do anything with Android devices, emulators, or SDK tools.
allowed-tools: Bash(acli *)
disable-model-invocation: false
---

You have access to the `acli` command, which provides a unified interface to all Android development tools.

## Device & Emulator Management

```bash
acli device list --json                          # list devices/emulators with serials
acli device -d <serial> info                     # model, Android version, API level, ABI
acli device -d <serial> install path/to/app.apk  # install APK
acli device -d <serial> logs --follow --level E  # stream error logs
acli device -d <serial> screenshot screen.png    # capture screen
acli avd list --json                             # list AVDs
acli avd start <name> --headless --wait-boot     # start emulator, wait for boot
acli avd stop <serial>                           # stop a running emulator
```

## SDK Management

```bash
acli sdk list --installed --json                 # list installed packages
acli sdk list --updates                          # list packages with updates
acli sdk install "platforms;android-35"          # install a package
acli sdk licenses                                # accept all pending licenses
```

## App Management

```bash
acli app list --json                             # list all installed apps
acli app launch <package>                        # launch an app
acli app stop <package>                          # force-stop an app
acli app clear <package>                         # clear app data
acli app deep-link <uri>                         # open a URI deep link
```

## Build

```bash
acli build assemble --variant debug              # build debug APK
acli build test --unit                           # run unit tests
acli build clean                                 # clean build outputs
```

## Device Instrumentation

```bash
acli instrument battery --level 10 --status discharging
acli instrument network --speed edge --latency gprs
acli instrument location --lat 37.7749 --lng -122.4194
acli instrument input text "Hello World"
acli instrument input tap 540 960
```

## Environment Health

```bash
acli doctor --json                               # full environment check (parseable)
```

## JSON Output

Always pass `--json` when you need to parse results programmatically.
All errors are written to stderr as:
```json
{"error": {"code": "...", "message": "...", "detail": "...", "fix": ["cmd1", "cmd2"]}}
```

## Device Targeting

Use `--device <serial>` or set `ACLI_DEVICE=<serial>` to target a specific device.
When multiple devices are connected, always specify a target to avoid ambiguity.
