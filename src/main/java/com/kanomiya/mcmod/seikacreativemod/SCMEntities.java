package com.kanomiya.mcmod.seikacreativemod;

import com.kanomiya.mcmod.seikacreativemod.entity.EntitySandBag;
import com.kanomiya.mcmod.seikacreativemod.render.RenderSandBag;

import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class SCMEntities {

	public static void preInit(FMLPreInitializationEvent event) {
		EntityRegistry.registerModEntity(EntitySandBag.class, "entitySandBag", 0, SeikaCreativeMod.instance, 64, 1, false, 0xD48842, 0xDE9D68);

        if (event.getSide().isClient()) {
            RenderingRegistry.registerEntityRenderingHandler(EntitySandBag.class, RenderSandBag::new);

        }

	}


}
