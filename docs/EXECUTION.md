# Add-on execution

This repository starts inactive and stock-safe. Implement only the smallest
observed Laser Bridges & Doors rendering defect before staging.

Before running Gradle gates, initialize both exact source submodules, activate
a Python 3.11 or newer virtual environment, and install the exact
development-only toolkit into it:

```bash
git submodule update --init --recursive -- \
  tooling/bluemap-addon-toolkit modules/bluemap-addon-adapter-api
python -m pip install --disable-pip-version-check --no-deps \
  --require-hashes --only-binary=:all: \
  --requirement requirements/toolkit.txt
```

## Prototype

Acquire and verify the exact candidate JARs outside Git. Their Gradle
properties are:

- `-PlaserBridgesJar=/path/to/laserbridges-1.21.1-neoforge-5.3.jar`
- `-PdeimosJar=/path/to/deimos-1.21.1-neoforge-2.7.jar`

Then run:

```bash
gradle --no-daemon -PbluemapSourcePath=../bluemap-backport \
  <exact-candidate-properties> clean prototypeCheck build
bash gallery/package.sh /tmp/laserbridges-gallery.zip
```

Deploy that JAR and gallery only to disposable staging, verify the intended
BlueMap link loads, and compare it with the matching client. Iterate from
observed defects until the owner explicitly accepts one exact staging JAR.

## Acceptance and release

The migration candidate records the production JAR, sources JAR, POM, and
Gradle module identities under `candidate_artifacts`. After visual acceptance,
change the provenance status to `owner-accepted-release-candidate`, record the
exact integration run and accepted JAR, and freeze its functional entries.
The writer refuses to overwrite an existing acceptance record:

```bash
bluemap-addon-toolkit jar-entries write \
  --jar /absolute/path/accepted-staging.jar \
  --entries provenance/accepted-staging-entries.sha256
```

Record the accepted-entry manifest and final artifacts only during owner
acceptance sealing. Do not carry alpha.1 acceptance forward as alpha.2 runtime
evidence.

Promote `addon_version` through a pull request and run with all exact candidate
properties:

```bash
gradle --no-daemon -PbluemapSourcePath=../bluemap-backport \
  <exact-candidate-properties> -PreleaseTag=v0.1.0-alpha.2 \
  clean build generatePomFileForAddonPublication \
  generateMetadataFileForAddonPublication verifyReleaseCandidate
```

Merge only after final-version CI passes this gate. Create an annotated
`v<version>` tag at reviewed `main`; the release workflow independently checks
the tag, exact BlueMap checkout, accepted bytes and draft assets before making
the prerelease public. Publication never deploys to production.
