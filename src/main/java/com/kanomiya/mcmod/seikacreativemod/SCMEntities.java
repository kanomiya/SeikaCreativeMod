package com.kanomiya.mcmod.seikacreativemod;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;

import com.kanomiya.mcmod.kanomiyacore.KanomiyaCore;
import com.kanomiya.mcmod.seikacreativemod.entity.EntitySandBag;
import com.kanomiya.mcmod.seikacreativemod.render.RenderSandBag;

public class SCMEntities {

	protected static IRenderFactory renderFactoryForSandBag = new IRenderFactory()
	{
		@Override
		public Render createRenderFor(RenderManager manager) {
			return new RenderSandBag(manager);
		}
	};

	public static void preInit(FMLPreInitializationEvent event, KanomiyaCore core) {
		EntityRegistry.registerModEntity(EntitySandBag.class, "entitySandBag", 0, core.getModInstance(), 0, 1, false, 0xD48842, 0xDE9D68);


	}

	public static void init(FMLInitializationEvent event, KanomiyaCore core) {
		if (event.getSide().isClient()) {
			RenderingRegistry.registerEntityRenderingHandler(EntitySandBag.class, renderFactoryForSandBag);

		}

	}

	public static void postInit(FMLPostInitializationEvent event, KanomiyaCore core) {  }


}
