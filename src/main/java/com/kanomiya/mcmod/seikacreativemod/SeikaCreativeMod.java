package com.kanomiya.mcmod.seikacreativemod;

import java.io.File;

import org.apache.logging.log4j.Logger;

import com.kanomiya.mcmod.seikacreativemod.command.CommandKillPlus;
import com.kanomiya.mcmod.seikacreativemod.command.CommandSchematic;
import com.kanomiya.mcmod.seikacreativemod.gui.GuiHandler;
import com.kanomiya.mcmod.seikacreativemod.network.PacketHandler;

import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.GameSettings.Options;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Modクラス
 *
 * @author Kanomiya [2016]
 *
 */
@Mod(modid = SeikaCreativeMod.MODID, name = "Seika Creative Mod", version = "@VERSION@")
public class SeikaCreativeMod {
	public static final String MODID = "com.kanomiya.mcmod.seikacreativemod";

	public static final String SCHEMATICSPATH = "schematics";

	@Mod.Instance(MODID)
	public static SeikaCreativeMod instance;

	public static final CreativeTabs tabSeika = new CreativeTabs(MODID) {
		@Override @SideOnly(Side.CLIENT)
		public Item getTabIconItem() {
			return Item.getItemFromBlock(SCMBlocks.blockFill);
		}
	};

	public static boolean ENABLEDBRIGHTNESS;

	public static Logger logger;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		logger = event.getModLog();

		mkDir(SCHEMATICSPATH);
		SCMConfig.preInit(event);

		SCMBlocks.preInit(event);
		SCMItems.preInit(event);
		SCMEntities.preInit(event);



	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		if (ENABLEDBRIGHTNESS) {
			Options gamma = GameSettings.Options.GAMMA;
			gamma.setValueMax(10.0f);
		}

		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
		PacketHandler.init();
	}


	@EventHandler
	public void serverStarting(FMLServerStartingEvent event){
		event.registerServerCommand(new CommandSchematic());
		event.registerServerCommand(new CommandKillPlus());
	}








	public static void mkDir(String str) {
		File f1 = new File(str);
		if (! f1.exists()) { f1.mkdir(); logger.info("mkDir " + str); }
	}



}




