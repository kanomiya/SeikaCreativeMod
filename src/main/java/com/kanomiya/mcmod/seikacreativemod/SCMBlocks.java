package com.kanomiya.mcmod.seikacreativemod;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.kanomiya.mcmod.core.util.GameRegistryUtils;
import com.kanomiya.mcmod.seikacreativemod.block.BlockBedrockButton;
import com.kanomiya.mcmod.seikacreativemod.block.BlockBedrockDoor;
import com.kanomiya.mcmod.seikacreativemod.block.BlockBedrockGlass;
import com.kanomiya.mcmod.seikacreativemod.block.BlockBedrockLever;
import com.kanomiya.mcmod.seikacreativemod.block.BlockBedrockLight;
import com.kanomiya.mcmod.seikacreativemod.block.BlockEditMachine;
import com.kanomiya.mcmod.seikacreativemod.block.BlockFill;
import com.kanomiya.mcmod.seikacreativemod.block.BlockPreparation;
import com.kanomiya.mcmod.seikacreativemod.block.BlockSuperSponge;
import com.kanomiya.mcmod.seikacreativemod.tileentity.TileEntityEditMachine;
import com.kanomiya.mcmod.seikacreativemod.tileentity.TileEntityFill;
import com.kanomiya.mcmod.seikacreativemod.tileentity.TileEntityPreparation;


public class SCMBlocks {
	public static BlockPreparation blockPreparation;
	public static BlockFill blockFill;
	public static BlockSuperSponge blockSuperSponge;
	public static BlockBedrockLight blockBedrockLight;
	public static BlockBedrockGlass blockBedrockGlass;
	public static BlockBedrockLever blockBedrockLever;
	public static BlockBedrockButton blockBedrockButton;
	public static BlockBedrockDoor blockBedrockDoor;
	public static BlockEditMachine blockEditMachine;
	// public static Block block;


	public static void preInit(FMLPreInitializationEvent event) {
		final boolean client = event.getSide().isClient();

		GameRegistryUtils.registerBlock(blockPreparation = new BlockPreparation(), "blockPreparation", client);
		GameRegistryUtils.registerBlock(blockFill = new BlockFill(), "blockFill", client);
		GameRegistryUtils.registerBlock(blockSuperSponge = new BlockSuperSponge(), "blockSuperSponge", client);
		GameRegistryUtils.registerBlock(blockBedrockLight = new BlockBedrockLight(), "blockBedrockLight", client);
		GameRegistryUtils.registerBlock(blockBedrockGlass = new BlockBedrockGlass(), "blockBedrockGlass", client);
		GameRegistryUtils.registerBlock(blockBedrockLever = new BlockBedrockLever(), "blockBedrockLever", client);
		GameRegistryUtils.registerBlock(blockBedrockButton = new BlockBedrockButton(), "blockBedrockButton", client);
		GameRegistryUtils.registerBlock(blockBedrockDoor = new BlockBedrockDoor(), "blockBedrockDoor", client);
		GameRegistryUtils.registerBlock(blockEditMachine = new BlockEditMachine(), "blockEditMachine", client);
		// GameRegistryUtils.registerBlock(block = new Block(), "block", client);

		// TileEntity
		GameRegistry.registerTileEntity(TileEntityFill.class, "tileentityFill");
		GameRegistry.registerTileEntity(TileEntityPreparation.class, "tileentityPreparation");

		GameRegistry.registerTileEntity(TileEntityEditMachine.class, "tileentityEditMachine");

	}

	public static void init(FMLInitializationEvent event) {  }
	public static void postInit(FMLPostInitializationEvent event) {  }


}
