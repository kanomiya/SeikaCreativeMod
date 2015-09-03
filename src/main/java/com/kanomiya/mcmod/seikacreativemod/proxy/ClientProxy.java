package com.kanomiya.mcmod.seikacreativemod.proxy;

import com.kanomiya.mcmod.seikacreativemod.entity.EntitySandBag;
import com.kanomiya.mcmod.seikacreativemod.render.RenderSandBag;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;


public class ClientProxy extends CommonProxy {

	@Override
	public void registerRenderers() {
		RenderManager manager = Minecraft.getMinecraft().getRenderManager();
		if (manager == null) {
			throw new RuntimeException("[NULL] Minecraft.getMinecraft().getRenderManager is null. registerRenderer can't continue.");
		}

		manager.entityRenderMap.put(EntitySandBag.class, new RenderSandBag(manager));
		// RenderingRegistry.registerEntityRenderingHandler(EntitySandBag.class, new RenderCreeper(manager));

	}
}
