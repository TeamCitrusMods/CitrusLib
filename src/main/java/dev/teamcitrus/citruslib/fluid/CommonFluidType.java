package dev.teamcitrus.citruslib.fluid;

import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.FluidType;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import java.util.function.Consumer;

public class CommonFluidType extends FluidType {
    private final ResourceLocation stillTexture;
    private final ResourceLocation flowingTexture;
    private final int tintColor;
    private final Vector3f fogColor;

    private float fogStart;
    private float fogEnd;

    public CommonFluidType(ResourceLocation stillTexture, ResourceLocation flowingTexture, int tintColor, Vector3f fogColor, Properties properties) {
        super(properties);
        this.stillTexture = stillTexture;
        this.flowingTexture = flowingTexture;
        this.tintColor = tintColor;
        this.fogColor = fogColor;
    }

    public CommonFluidType(ResourceLocation stillTexture, ResourceLocation flowingTexture, int tintColor, Vector3f fogColor, Properties properties,
                            float fogStart, float fogEnd) {
        this(stillTexture, flowingTexture, tintColor, fogColor, properties);
        this.fogStart = fogStart;
        this.fogEnd = fogEnd;
    }

    @Override
    public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
        consumer.accept(new IClientFluidTypeExtensions() {
            @Override
            public ResourceLocation getStillTexture() {
                return stillTexture;
            }

            @Override
            public ResourceLocation getFlowingTexture() {
                return flowingTexture;
            }

            @Override
            public int getTintColor() {
                return tintColor;
            }

            @Override
            public @NotNull Vector3f modifyFogColor(Camera camera, float partialTick, ClientLevel level, int renderDistance, float darkenWorldAmount, Vector3f fluidFogColor) {
                return fogColor;
            }

            @Override
            public void modifyFogRender(Camera camera, FogRenderer.FogMode mode, float renderDistance, float partialTick, float nearDistance, float farDistance, FogShape shape) {
                if (fogEnd < fogStart) throw new IllegalArgumentException("fogEnd cannot be smaller then fogStart!");
                if (!(fogStart == 0)) RenderSystem.setShaderFogStart(fogStart);
                if (!(fogEnd == 0)) RenderSystem.setShaderFogEnd(fogEnd);
            }
        });
    }
}
