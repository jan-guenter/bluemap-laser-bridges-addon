/*
 * SPDX-License-Identifier: MIT
 */

package io.github.janguenter.bluemap.laserbridges.adapter.bluemap523;

import de.bluecolored.bluemap.core.map.hires.block.BlockRendererType;
import de.bluecolored.bluemap.core.resources.pack.resourcepack.ResourcePack;
import de.bluecolored.bluemap.core.resources.pack.resourcepack.ResourcePackExtension;
import de.bluecolored.bluemap.core.resources.pack.resourcepack.model.Element;
import de.bluecolored.bluemap.core.resources.pack.resourcepack.model.Face;
import de.bluecolored.bluemap.core.resources.pack.resourcepack.model.Model;
import de.bluecolored.bluemap.core.resources.pack.resourcepack.texture.AnimationMeta;
import de.bluecolored.bluemap.core.resources.pack.resourcepack.texture.Texture;
import de.bluecolored.bluemap.core.util.Key;
import de.bluecolored.bluemap.core.world.BlockProperties;
import de.bluecolored.bluemap.core.world.BlockState;
import io.github.janguenter.bluemap.laserbridges.activation.AddonRuntime;
import io.github.janguenter.bluemap.laserbridges.model.LaserBridgesRenderRules;
import io.github.janguenter.bluemap.laserbridges.profile.ExactArtifactDetector;
import io.github.janguenter.bluemap.laserbridges.profile.LaserBridges53Profile;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/** Exact-artifact admission, installed-resource validation and target routing. */
final class ProfileResourceExtension implements ResourcePackExtension {

    private static final Key ANIMATED_TEXTURE =
            Key.parse("laserbridges:block/laser_block_powered");
    private static final Set<Key> REQUIRED_TEXTURES = requiredTextures();
    private static final Map<Key, Integer> REQUIRED_MODELS = requiredModels();

    private final ResourcePack resourcePack;
    private final BlockRendererType renderer;
    private final AddonRuntime runtime;
    private boolean admitted;

    ProfileResourceExtension(
            ResourcePack resourcePack,
            BlockRendererType renderer,
            AddonRuntime runtime
    ) {
        this.resourcePack = resourcePack;
        this.renderer = renderer;
        this.runtime = runtime;
    }

    @Override
    public void loadResources(Iterable<Path> roots) {
        admitted = false;
        if (Boolean.getBoolean("bluemap.laserbridges.disabled")) {
            runtime.inactive("operator-disabled");
            return;
        }
        if (!ExactArtifactDetector.matchesAll(roots, LaserBridges53Profile.ARTIFACTS)) {
            runtime.inactive("exact-artifact-missing-or-duplicate");
            return;
        }
        admitted = true;
    }

    @Override
    public Set<Key> collectUsedTextureKeys() {
        return admitted ? REQUIRED_TEXTURES : Set.of();
    }

    @Override
    public void bake() {
        if (!admitted) {
            return;
        }
        try {
            if (!validInstalledResources()) {
                runtime.inactive("installed-render-resource-invalid");
                return;
            }
            VariantRendererCatalog variants = VariantRendererCatalog.wrap(
                    resourcePack, renderer
            );
            RendererDataRegistry.install(resourcePack, variants);
            runtime.activate();
            System.out.println("BlueMap Laser Bridges add-on active: wrapped "
                    + variants.size() + " exact variants across 4 blocks.");
        } catch (IOException | RuntimeException exception) {
            runtime.inactive("route-install-" + exception.getClass().getSimpleName());
        }
    }

    @Override
    public void getBlockProperties(BlockState state, BlockProperties.Builder builder) {
        if (admitted && LaserBridgesRenderRules.TARGET_SET.contains(
                state.getId().getFormatted()
        )) {
            builder.culling(false).occluding(false).cullingIdentical(false);
        }
    }

    private boolean validInstalledResources() throws IOException {
        for (Map.Entry<Key, Integer> required : REQUIRED_MODELS.entrySet()) {
            Model model = resourcePack.getModels().get(required.getKey());
            if (!validModel(model, required.getValue())) {
                return false;
            }
        }
        for (Key key : REQUIRED_TEXTURES) {
            Texture texture = resourcePack.getTextures().get(key);
            if (!validTexture(key, texture)) {
                return false;
            }
        }
        return true;
    }

    private static boolean validModel(Model model, int expectedElements) {
        if (model == null
                || model.getElements() == null
                || model.getElements().length != expectedElements) {
            return false;
        }
        for (Element element : model.getElements()) {
            if (element == null || element.getFaces().isEmpty()) {
                return false;
            }
            for (Face face : element.getFaces().values()) {
                if (face == null || face.getTintindex() != 0) {
                    return false;
                }
                if (face.getTexture().getTexturePath(model.getTextures()::get) == null) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean validTexture(Key key, Texture texture) throws IOException {
        if (texture == null) {
            return false;
        }
        BufferedImage image = texture.getTextureImage();
        if (ANIMATED_TEXTURE.equals(key)) {
            AnimationMeta animation = texture.getAnimation();
            List<AnimationMeta.FrameMeta> frames = animation == null
                    ? null : animation.getFrames();
            return image != null
                    && image.getWidth() == 16
                    && image.getHeight() == 256
                    && animation != null
                    && !animation.isInterpolate()
                    && animation.getFrametime() == 1
                    && frames != null
                    && frames.size() == 16
                    && frames.getFirst().getIndex() == 15
                    && frames.getLast().getIndex() == 0;
        }
        return image != null && image.getWidth() == 32 && image.getHeight() == 32;
    }

    private static Set<Key> requiredTextures() {
        LinkedHashSet<Key> keys = new LinkedHashSet<>();
        keys.add(ANIMATED_TEXTURE);
        keys.add(Key.parse("laserbridges:block/laser_source_block"));
        keys.add(Key.parse("laserbridges:block/laser_source_block_powered"));
        keys.add(Key.parse("laserbridges:block/laser_fence_source_block"));
        return Set.copyOf(keys);
    }

    private static Map<Key, Integer> requiredModels() {
        LinkedHashMap<Key, Integer> models = new LinkedHashMap<>();
        models.put(Key.parse("laserbridges:block/laser_block_powered"), 1);
        models.put(Key.parse("laserbridges:block/laser_fence_powered"), 1);
        models.put(Key.parse("laserbridges:block/laser_source_block"), 1);
        models.put(Key.parse("laserbridges:block/laser_source_block_powered"), 2);
        models.put(Key.parse("laserbridges:block/laser_fence_source_block"), 1);
        models.put(Key.parse("laserbridges:block/laser_fence_source_block_powered"), 2);
        return Map.copyOf(models);
    }
}
