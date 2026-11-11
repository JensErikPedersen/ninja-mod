package dk.jens.ninjamod;

import net.minecraft.client.renderer.entity.ThrownItemRenderer;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class NinjaModClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// Tegn den flyvende kastestjerne med samme billede som i inventaret
		EntityRendererRegistry.register(ModEntities.SHURIKEN, ThrownItemRenderer::new);
	}
}
