package com.kanomiya.mcmod.seikacreativemod;

import java.io.File;

import com.kanomiya.mcmod.seikacreativemod.block.BlockFill;
import com.kanomiya.mcmod.seikacreativemod.block.BlockSuperSponge;

import net.minecraftforge.common.config.Configuration;

public class SCMConfig {

	public static void init(File configfile) {
		Configuration config = new Configuration(configfile);

		try {
			config.load();

			BlockFill.EDIT_MINY = (config.get("FillBlock",
					"minY", 0).getInt());

			BlockSuperSponge.EDIT_LIMIT = (config.get("SuperSponge",
					"EditLimit", 4096).getInt());

			SeikaCreativeMod.ENABLEDBRIGHTNESS = config.get("Others", "brightness_plus", false).getBoolean(false);

		} catch (Exception e) {
		} finally {
			config.save();
		}

	}

}
