# BlueMap Laser Bridges & Doors Add-on

A Java 21 BlueMap add-on for the exact `laserbridges-5.3-mc1.21.1` profile in All the Mons
`1.2.0` / Minecraft `1.21.1`.

Status: unpublished `0.1.0-alpha.2` BlueMap 5.23 migration candidate. It moves
the owner-accepted alpha.1 rendering contract to the exact feature backport and
shared Adapter API without changing the four-block profile or gallery. After
exact admission, the add-on reuses installed JSON models, textures and
animation metadata and restores the persisted `color=0..15` client tint.
Unsupported or malformed states retain stock rendering.

## Clone

Clone the toolkit submodule with the add-on:

```bash
git clone --recurse-submodules \
  https://github.com/jan-guenter/bluemap-laser-bridges-addon.git
```

Initialize it in an existing clone before running Gradle:

```bash
git submodule update --init --recursive -- \
  tooling/bluemap-addon-toolkit modules/bluemap-addon-adapter-api
```

The toolkit submodule supplies the Gradle convention source at
`v0.3.0-alpha.1`, commit
`6cd34a8368cc4ee8628fbe830a90ec5b14960629`. `requirements/toolkit.txt`
separately pins the published Python CLI used by artifact and staged-entry
checks. The Adapter API gitlink pins `0.1.0-alpha.2` commit
`e81f08bc4bfbf02d810ec8949a019130e2e61634` and compiles its four Java sources
into the add-on. Neither standalone module JAR is installed or nested.

## Build

```bash
gradle --no-daemon -PbluemapSourcePath=../bluemap-backport clean check build
```

`check` is the quick Java/checkstyle/archive gate. `prototypeCheck` additionally
requires every exact candidate JAR property and validates the focused gallery.
See `provenance/upstreams.json` for immutable artifact identities and
the [execution guide](docs/EXECUTION.md) for the prototype-to-release loop.

## Install

Place the production JAR in BlueMap's add-on pack directory and restart the
BlueMap JVM. Removal plus one restart restores stock
behavior; the add-on creates no custom world state.

Set `-Dbluemap.laserbridges.disabled=true` to leave the exact profile inactive.

## Scope boundary

The initial implementation must be limited to a small observed BlueMap defect.
Live contents, fill levels, activity overlays, particles, animation phase, and
unsupported states stay stock or deterministic-neutral unless the owner
explicitly expands scope.

No Laser Bridges & Doors binary, source, class, asset, captured mesh, or gallery is
bundled in the add-on.
