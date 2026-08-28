/* SPDX-License-Identifier: MIT */

package io.github.janguenter.bluemap.laserbridges.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import java.util.List;

import org.junit.jupiter.api.Test;

class LaserBridgesRenderRulesTest {

    private static final int[] EXPECTED = {
        0xF9FFFE, 0xF9801D, 0xC74EBD, 0x3AB3DA,
        0xFED83D, 0x80C71F, 0xF38BAA, 0x474F52,
        0x9D9D97, 0x169C9C, 0x8932B8, 0x3C44AA,
        0x835432, 0x5E7C16, 0xB02E26, 0x1D1D21
    };

    @Test
    void mapsEveryPersistedColorToTheExactClientDiffusePalette() {
        for (int id = 0; id < EXPECTED.length; id++) {
            LaserBridgesRenderRules.DyeColor color =
                    LaserBridgesRenderRules.color(Map.of("color", Integer.toString(id)));
            assertTrue(color.valid());
            assertEquals(id, color.id());
            assertEquals(EXPECTED[id], color.rgb());
        }
    }

    @Test
    void malformedOrOutOfRangeColorFallsBackInsteadOfWrapping() {
        for (Map<String, String> properties : List.of(
                Map.<String, String>of(), Map.of("color", "-1"),
                Map.of("color", "16"),
                Map.of("color", "01"), Map.of("color", "blue")
        )) {
            assertFalse(LaserBridgesRenderRules.color(properties).valid());
        }
        assertFalse(LaserBridgesRenderRules.color(null).valid());
    }
}
