package com.kanomiya.mcmod.seikacreativemod;

import java.util.List;

import com.kanomiya.mcmod.kanomiyacore.KanomiyaCore;
import com.kanomiya.mcmod.kanomiyacore.util.GameRegistryUtils;
import com.kanomiya.mcmod.seikacreativemod.item.ItemCommandItem;
import com.kanomiya.mcmod.seikacreativemod.item.ItemEntityKiller;
import com.kanomiya.mcmod.seikacreativemod.item.ItemPipette;
import com.kanomiya.mcmod.seikacreativemod.item.ItemPosWand;
import com.kanomiya.mcmod.seikacreativemod.item.ItemWorldGen;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemDoor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


public class SCMItems {

	public static ItemCommandItem itemCommandItem;
	public static ItemEntityKiller itemEntityKiller;
	public static ItemWorldGen itemWorldGen;
	public static ItemPipette itemPipette;
	public static ItemDoor itemBedrockDoor;
	public static ItemPosWand itemPosWand;
	// public static Item item;

	public static void preInit(FMLPreInitializationEvent event, KanomiyaCore core) {
		final boolean client = event.getSide().isClient();

		GameRegistryUtils utils = core.getGameRegistryUtils();

		utils.registerItem(itemCommandItem = new ItemCommandItem(), "itemCommandItem", new String[] { "", "minecraft:book" }, client);
		utils.registerItem(itemEntityKiller = new ItemEntityKiller(), "itemEntityKiller", client);
		utils.registerItem(itemWorldGen = new ItemWorldGen(), "itemWorldGen", client);
		utils.registerItem(itemPipette = new ItemPipette(), "itemPipette", client);
		utils.registerItem(itemBedrockDoor = new ItemDoor(SCMBlocks.blockBedrockDoor) {
			@Override @SideOnly(Side.CLIENT)
			public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced) {
				tooltip.add("(Texture: Acacia)");
			}

		}, "itemBedrockDoor", client);

		utils.registerItem(itemPosWand = new ItemPosWand(), "itemPosWand", client);

		// utils.registerItem(item = new Item(), "item", client);


	}

	public static void init(FMLInitializationEvent event, KanomiyaCore core) {  }
	public static void postInit(FMLPostInitializationEvent event, KanomiyaCore core) {  }

}
