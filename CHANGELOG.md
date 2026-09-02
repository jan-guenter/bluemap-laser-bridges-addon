# Changelog

## 0.1.0-alpha.2 - 2026-09-02

- Target only BlueMap's exact 5.23 feature backport at commit `7e07f4e`.
- Replace the local runtime, registry, and resource-extension helpers with four
  pinned Adapter API sources compiled into the add-on.
- Keep the exact Laser Bridges profile, renderer, fallback rules, and gallery
  unchanged pending combined runtime review.

## 0.1.0-alpha.1 - 2026-08-28

- Generated a fail-closed Java 21 BlueMap add-on seed for `laserbridges-5.3-mc1.21.1`.
- Added exact-artifact-gated installed-model rendering for all four Laser
  Bridges blocks, including persisted dye tint, transparency, emitted light,
  model-state rotation and animated beam texture metadata.
- Added focused palette/resource tests and a bounded gallery with direct and
  naturally generated bridge and fence cases.
- Sealed the owner-accepted staging result for immutable release publication.
