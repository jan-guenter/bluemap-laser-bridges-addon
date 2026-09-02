/* SPDX-License-Identifier: MIT */

package io.github.janguenter.bluemap.laserbridges.adapter.bluemap523;

import de.bluecolored.bluemap.core.util.Direction;
import de.bluecolored.bluemap.core.world.LightData;
import de.bluecolored.bluemap.core.world.block.BlockNeighborhood;
import io.github.janguenter.bluemap.laserbridges.model.LaserBridgesRenderRules;

/** Samples exposed-face light while retaining the blocks' emitted light. */
final class FaceLighting {

    private FaceLighting() {
    }

    static Sample sample(BlockNeighborhood block, Direction direction) {
        var vector = direction.toVector();
        LightData own = block.getLightData();
        LightData faced = block.getNeighborBlock(
                vector.getX(), vector.getY(), vector.getZ()
        ).getLightData();
        return new Sample(
                Math.max(own.getSkyLight(), faced.getSkyLight()),
                Math.max(
                        LaserBridgesRenderRules.EMITTED_LIGHT,
                        Math.max(own.getBlockLight(), faced.getBlockLight())
                )
        );
    }

    record Sample(int sunlight, int blocklight) {
    }
}
