# Laser Bridges gallery

This generated gallery covers both source blocks and both generated surface
blocks. It includes representative colors and floor, wall and ceiling model
rotations, plus two redstone-powered rows that exercise natural bridge and
fence generation. Direct-set generated surfaces and a stone control keep the
render comparison useful if server updates disturb a powered row.

Use the stable commands:

```bash
python gallery/generate.py
python gallery/generate.py --check
python gallery/lint.py
bash gallery/package.sh /tmp/laserbridges-gallery.zip
```

Keep gallery generation deterministic, bounded, synthetic where practical, and
free of candidate assets or captured meshes.
