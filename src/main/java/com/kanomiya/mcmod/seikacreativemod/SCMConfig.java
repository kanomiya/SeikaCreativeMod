package com.kanomiya.mcmod.seikacreativemod;

import com.kanomiya.mcmod.seikacreativemod.block.BlockFill;
import com.kanomiya.mcmod.seikacreativemod.block.BlockSuperSponge;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class SCMConfig {

	public static void preInit(FMLPreInitializationEvent event) {
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());

		try {
			config.load();

			BlockFill.EDIT_MINY = (config.get("FillBlock",
					"minY", 0).getInt());

			BlockSuperSponge.EDIT_LIMIT = (config.get("SuperSponge",
					"EditLimit", 4096).getInt());

			SeikaCreativeMod.ENABLEDBRIGHTNESS = config.get("Others", "brightness_plus", true).getBoolean(true);

		} catch (Exception e) {
		} finally {
			config.save();
		}

	}

}
