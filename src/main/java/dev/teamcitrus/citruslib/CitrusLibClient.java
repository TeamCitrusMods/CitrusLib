package dev.teamcitrus.citruslib;

import dev.teamcitrus.citruslib.internal.registry.BlockEntityRegistry;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = CitrusLib.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class CitrusLibClient {
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(BlockEntityRegistry.CITRUS_SIGN_BLOCK.get(), SignRenderer::new);
        event.registerBlockEntityRenderer(BlockEntityRegistry.CITRUS_HANGING_SIGN.get(), HangingSignRenderer::new);
    }
}
