/* SPDX-License-Identifier: MIT */

package io.github.janguenter.bluemap.laserbridges.model;

import java.util.List;
import java.util.Map;
import java.util.Set;

/** Exact persisted-state rules for Laser Bridges & Doors 5.3. */
public final class LaserBridgesRenderRules {

    public static final List<String> TARGETS = List.of(
            "laserbridges:laser_source_block",
            "laserbridges:laser_fence_source_block",
            "laserbridges:laser_block_powered",
            "laserbridges:laser_fence_powered"
    );
    public static final Set<String> TARGET_SET = Set.copyOf(TARGETS);
    public static final int EMITTED_LIGHT = 10;

    private static final int[] DYE_COLORS = {
        0xF9FFFE, 0xF9801D, 0xC74EBD, 0x3AB3DA,
        0xFED83D, 0x80C71F, 0xF38BAA, 0x474F52,
        0x9D9D97, 0x169C9C, 0x8932B8, 0x3C44AA,
        0x835432, 0x5E7C16, 0xB02E26, 0x1D1D21
    };

    private LaserBridgesRenderRules() {
    }

    /** Resolves the persisted integer property, or rejects malformed states. */
    public static DyeColor color(Map<String, String> properties) {
        if (properties == null) {
            return DyeColor.INVALID;
        }
        String raw = properties.get("color");
        if (raw == null || !raw.matches("(?:[0-9]|1[0-5])")) {
            return DyeColor.INVALID;
        }
        int id = Integer.parseInt(raw);
        return new DyeColor(true, id, DYE_COLORS[id]);
    }

    /** Deterministic client DyeColor diffuse color used by tint index zero. */
    public record DyeColor(boolean valid, int id, int rgb) {
        private static final DyeColor INVALID = new DyeColor(false, -1, 0xFFFFFF);
    }
}
