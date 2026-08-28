/* SPDX-License-Identifier: MIT */

package io.github.janguenter.bluemap.laserbridges.adapter.bluemap522;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import de.bluecolored.bluemap.core.resources.adapter.ResourcesGson;
import de.bluecolored.bluemap.core.resources.pack.resourcepack.model.Element;
import de.bluecolored.bluemap.core.resources.pack.resourcepack.model.Face;
import de.bluecolored.bluemap.core.resources.pack.resourcepack.model.Model;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.imageio.ImageIO;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assumptions;

class LaserBridgesInstalledResourceTest {

    @Test
    void exactInstalledModelsRetainTintAnimationAndStateRotations() throws IOException {
        String artifact = System.getProperty("laserBridgesJar");
        Assumptions.assumeTrue(
                artifact != null,
                "exact Laser Bridges artifact is a prototype-only input"
        );
        try (ZipFile zip = new ZipFile(Path.of(artifact).toFile())) {
            assertBlockState(zip, "laser_source_block", 24, true);
            assertBlockState(zip, "laser_fence_source_block", 24, true);
            assertBlockState(zip, "laser_block_powered", 12, false);
            assertBlockState(zip, "laser_fence_powered", 12, false);

            assertModel(zip, "laser_source_block", 1);
            assertModel(zip, "laser_source_block_powered", 2);
            assertModel(zip, "laser_fence_source_block", 1);
            assertModel(zip, "laser_fence_source_block_powered", 2);
            assertModel(zip, "laser_block_powered", 1);
            assertModel(zip, "laser_fence_powered", 1);

            ZipEntry textureEntry = zip.getEntry(
                    "assets/laserbridges/textures/block/laser_block_powered.png"
            );
            assertNotNull(textureEntry);
            BufferedImage texture = ImageIO.read(zip.getInputStream(textureEntry));
            assertEquals(16, texture.getWidth());
            assertEquals(256, texture.getHeight());
            JsonObject animation = json(zip,
                    "assets/laserbridges/textures/block/laser_block_powered.png.mcmeta")
                    .getAsJsonObject("animation");
            assertEquals(1, animation.get("frametime").getAsInt());
            assertFalse(animation.get("interpolate").getAsBoolean());
            assertEquals(16, animation.getAsJsonArray("frames").size());
            assertEquals(15, animation.getAsJsonArray("frames").get(0).getAsInt());
            assertEquals(0, animation.getAsJsonArray("frames").get(15).getAsInt());
        }
    }

    private static void assertBlockState(
            ZipFile zip,
            String name,
            int expectedVariants,
            boolean poweredProperty
    ) throws IOException {
        JsonObject variants = json(
                zip, "assets/laserbridges/blockstates/" + name + ".json"
        ).getAsJsonObject("variants");
        assertEquals(expectedVariants, variants.size(), name);
        for (Map.Entry<String, JsonElement> entry : variants.entrySet()) {
            Map<String, String> state = parseState(entry.getKey());
            assertEquals(poweredProperty, state.containsKey("powered"), entry.getKey());
            JsonObject variant = entry.getValue().getAsJsonObject();
            assertEquals(expectedX(state.get("face")), intValue(variant, "x"));
            assertEquals(expectedY(state.get("face"), state.get("facing")),
                    intValue(variant, "y"));
        }
    }

    private static void assertModel(ZipFile zip, String name, int expectedElements)
            throws IOException {
        String path = "assets/laserbridges/models/block/" + name + ".json";
        ZipEntry entry = zip.getEntry(path);
        assertNotNull(entry, path);
        Model model;
        try (InputStreamReader reader = new InputStreamReader(
                zip.getInputStream(entry), StandardCharsets.UTF_8
        )) {
            model = ResourcesGson.INSTANCE.fromJson(reader, Model.class);
        }
        assertNotNull(model.getElements(), path);
        assertEquals(expectedElements, model.getElements().length, path);
        for (Element element : model.getElements()) {
            for (Face face : element.getFaces().values()) {
                assertEquals(0, face.getTintindex(), path);
            }
        }
    }

    private static JsonObject json(ZipFile zip, String path) throws IOException {
        ZipEntry entry = zip.getEntry(path);
        assertNotNull(entry, path);
        try (InputStreamReader reader = new InputStreamReader(
                zip.getInputStream(entry), StandardCharsets.UTF_8
        )) {
            return JsonParser.parseReader(reader).getAsJsonObject();
        }
    }

    private static Map<String, String> parseState(String key) {
        java.util.LinkedHashMap<String, String> result = new java.util.LinkedHashMap<>();
        for (String property : key.split(",")) {
            String[] pair = property.split("=", 2);
            result.put(pair[0], pair[1]);
        }
        return Map.copyOf(result);
    }

    private static int intValue(JsonObject object, String key) {
        JsonElement value = object.get(key);
        return value == null ? 0 : value.getAsInt();
    }

    private static int expectedX(String face) {
        return switch (face) {
            case "floor" -> 0;
            case "wall" -> 90;
            case "ceiling" -> 180;
            default -> throw new IllegalArgumentException("unexpected face " + face);
        };
    }

    private static int expectedY(String face, String facing) {
        if ("ceiling".equals(face)) {
            return switch (facing) {
                case "south" -> 0;
                case "west" -> 90;
                case "north" -> 180;
                case "east" -> 270;
                default -> throw new IllegalArgumentException("unexpected facing " + facing);
            };
        }
        return switch (facing) {
            case "north" -> 0;
            case "east" -> 90;
            case "south" -> 180;
            case "west" -> 270;
            default -> throw new IllegalArgumentException("unexpected facing " + facing);
        };
    }
}
