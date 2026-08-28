#!/usr/bin/env python3
# SPDX-License-Identifier: MIT
"""Compact exact-state gallery for the Laser Bridges tint profile."""

from __future__ import annotations

from dataclasses import dataclass


NAMESPACE = "laserbridges_gallery"
ENVELOPE = (158, 98, 158, 178, 103, 175)


@dataclass(frozen=True)
class Placement:
    case_id: str
    label: str
    x: int
    y: int
    z: int
    block_state: str
    expected: str
    verify_state: str | None = None
    place: bool = True


PLACEMENTS = (
    Placement(
        "bridge-white-floor", "generated bridge white safe control",
        162, 100, 160,
        "laserbridges:laser_block_powered[color=0,face=floor,facing=north]",
        "white-tinted-translucent-emissive",
    ),
    Placement(
        "bridge-yellow-wall", "generated bridge yellow wall east",
        164, 100, 160,
        "laserbridges:laser_block_powered[color=4,face=wall,facing=east]",
        "yellow-tinted-translucent-emissive",
    ),
    Placement(
        "bridge-cyan-ceiling", "generated bridge cyan ceiling west",
        166, 100, 160,
        "laserbridges:laser_block_powered[color=9,face=ceiling,facing=west]",
        "cyan-tinted-translucent-emissive",
    ),
    Placement(
        "bridge-black-floor", "generated bridge black floor south",
        168, 100, 160,
        "laserbridges:laser_block_powered[color=15,face=floor,facing=south]",
        "black-tinted-translucent-emissive",
    ),
    Placement(
        "fence-white-floor", "generated fence white safe control",
        162, 100, 162,
        "laserbridges:laser_fence_powered[color=0,face=floor,facing=north]",
        "white-tinted-translucent-emissive",
    ),
    Placement(
        "fence-light-blue-wall", "generated fence light blue wall south",
        164, 100, 162,
        "laserbridges:laser_fence_powered[color=3,face=wall,facing=south]",
        "light-blue-tinted-translucent-emissive",
    ),
    Placement(
        "fence-purple-ceiling", "generated fence purple ceiling east",
        166, 100, 162,
        "laserbridges:laser_fence_powered[color=10,face=ceiling,facing=east]",
        "purple-tinted-translucent-emissive",
    ),
    Placement(
        "fence-green-wall", "generated fence green wall west",
        168, 100, 162,
        "laserbridges:laser_fence_powered[color=13,face=wall,facing=west]",
        "green-tinted-translucent-emissive",
    ),
    Placement(
        "bridge-source-floor-support", "support for unpowered bridge source",
        162, 99, 165, "minecraft:stone", "support",
    ),
    Placement(
        "bridge-source-unpowered", "orange bridge source unpowered floor",
        162, 100, 165,
        "laserbridges:laser_source_block[color=1,face=floor,facing=north,powered=false]",
        "orange-source-unpowered",
    ),
    Placement(
        "fence-source-ceiling-support", "support for unpowered fence source",
        165, 101, 165, "minecraft:stone", "support",
    ),
    Placement(
        "fence-source-unpowered", "pink fence source unpowered ceiling",
        165, 100, 165,
        "laserbridges:laser_fence_source_block[color=6,face=ceiling,facing=south,powered=false]",
        "pink-source-unpowered",
    ),
    Placement(
        "stock-control", "stone stock rendering control",
        171, 100, 165, "minecraft:stone", "stock-visible",
    ),
    Placement(
        "natural-bridge-power", "redstone support and power for natural bridge row",
        160, 100, 170, "minecraft:redstone_block", "powered-support",
    ),
    Placement(
        "natural-bridge-source", "blue naturally generated bridge source",
        161, 100, 170,
        "laserbridges:laser_source_block[color=11,face=wall,facing=east,powered=false]",
        "blue-source-powered",
        "laserbridges:laser_source_block[color=11,face=wall,facing=east,powered=true]",
    ),
    Placement(
        "natural-bridge-first", "first naturally generated blue bridge",
        162, 100, 170,
        "laserbridges:laser_block_powered[color=11,face=wall,facing=east]",
        "blue-natural-bridge", place=False,
    ),
    Placement(
        "natural-bridge-middle", "middle naturally generated blue bridge",
        168, 100, 170,
        "laserbridges:laser_block_powered[color=11,face=wall,facing=east]",
        "blue-natural-bridge", place=False,
    ),
    Placement(
        "natural-bridge-last", "last naturally generated blue bridge",
        175, 100, 170,
        "laserbridges:laser_block_powered[color=11,face=wall,facing=east]",
        "blue-natural-bridge", place=False,
    ),
    Placement(
        "natural-fence-power", "redstone support and power for natural fence row",
        160, 100, 173, "minecraft:redstone_block", "powered-support",
    ),
    Placement(
        "natural-fence-source", "red naturally generated fence source",
        161, 100, 173,
        "laserbridges:laser_fence_source_block[color=14,face=wall,facing=east,powered=false]",
        "red-source-powered",
        "laserbridges:laser_fence_source_block[color=14,face=wall,facing=east,powered=true]",
    ),
    Placement(
        "natural-fence-first", "first naturally generated red fence",
        162, 100, 173,
        "laserbridges:laser_fence_powered[color=14,face=wall,facing=east]",
        "red-natural-fence", place=False,
    ),
    Placement(
        "natural-fence-middle", "middle naturally generated red fence",
        168, 100, 173,
        "laserbridges:laser_fence_powered[color=14,face=wall,facing=east]",
        "red-natural-fence", place=False,
    ),
    Placement(
        "natural-fence-last", "last naturally generated red fence",
        175, 100, 173,
        "laserbridges:laser_fence_powered[color=14,face=wall,facing=east]",
        "red-natural-fence", place=False,
    ),
)
