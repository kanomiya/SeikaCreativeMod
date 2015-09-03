package com.kanomiya.mcmod.seikacreativemod;

import java.util.Random;

import com.kanomiya.mcmod.seikacreativemod.entity.EntitySandBag;

import net.minecraftforge.fml.common.registry.EntityRegistry;

public class SCMEntity {

	public static void registerEntity() {
		registerEntity(EntitySandBag.class, "entitySandBag", 0xD48842, 0xDE9D68);

	}


	public static void registerEntity(Class entityClass, String name) {
		int entityID = EntityRegistry.findGlobalUniqueEntityId();

		// EntityRegistry.registerGlobalEntityID(entityClass, name, entityID);
		EntityRegistry.registerModEntity(entityClass, name, entityID, SeikaCreativeMod.instance, 64, 1, true);
	}

	public static void registerEntity(Class entityClass, String name, int primaryColor, int secondaryColor) {
		int entityID = EntityRegistry.findGlobalUniqueEntityId();

		EntityRegistry.registerGlobalEntityID(entityClass, name, entityID, primaryColor, secondaryColor);
		EntityRegistry.registerModEntity(entityClass, name, entityID, SeikaCreativeMod.instance, 64, 1, true);
		// EntityList.addMapping(entityClass, name, entityID, primaryColor, secondaryColor);
	}

	public static void registerEntity(Class entityClass, String name, boolean addEgg) {

		if (addEgg) {
			long seed = name.hashCode();
			Random rand = new Random(seed);

			registerEntity(entityClass, name, rand.nextInt() * 16777215, rand.nextInt() * 16777215);
		} else {
			registerEntity(entityClass, name);
		}
	}

}
