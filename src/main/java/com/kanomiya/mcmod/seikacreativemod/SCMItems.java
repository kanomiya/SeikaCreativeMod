package com.kanomiya.mcmod.seikacreativemod;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemDoor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.kanomiya.mcmod.core.util.GameRegistryUtils;
import com.kanomiya.mcmod.seikacreativemod.item.ItemCommandItem;
import com.kanomiya.mcmod.seikacreativemod.item.ItemEntityKiller;
import com.kanomiya.mcmod.seikacreativemod.item.ItemEntityPicker;
import com.kanomiya.mcmod.seikacreativemod.item.ItemPipette;
import com.kanomiya.mcmod.seikacreativemod.item.ItemPosWand;
import com.kanomiya.mcmod.seikacreativemod.item.ItemWorldGen;


public class SCMItems {

	public static ItemCommandItem itemCommandItem;
	public static ItemEntityKiller itemEntityKiller;
	public static ItemEntityPicker itemEntityPicker;
	public static ItemWorldGen itemWorldGen;
	public static ItemPipette itemPipette;
	public static ItemDoor itemBedrockDoor;
	public static ItemPosWand itemPosWand;
	// public static Item item;

	public static void preInit(FMLPreInitializationEvent event) {
		final boolean client = event.getSide().isClient();

		GameRegistryUtils.registerItem(itemCommandItem = new ItemCommandItem(), "itemCommandItem", new String[] { "", "minecraft:book" }, client);
		GameRegistryUtils.registerItem(itemEntityKiller = new ItemEntityKiller(), "itemEntityKiller", client);
		GameRegistryUtils.registerItem(itemEntityPicker = new ItemEntityPicker(), "itemEntityPicker", client);
		GameRegistryUtils.registerItem(itemWorldGen = new ItemWorldGen(), "itemWorldGen", client);
		GameRegistryUtils.registerItem(itemPipette = new ItemPipette(), "itemPipette", client);
		GameRegistryUtils.registerItem(itemBedrockDoor = new ItemDoor(SCMBlocks.blockBedrockDoor) {
			@Override @SideOnly(Side.CLIENT)
			public void addInformation(ItemStack stack, EntityPlayer player, List tooltip, boolean advanced) {
				tooltip.add("(Texture: Acacia)");
			}

		}, "itemBedrockDoor", client);

		GameRegistryUtils.registerItem(itemPosWand = new ItemPosWand(), "itemPosWand", client);

		// GameRegistryUtils.registerItem(item = new Item(), "item", client);


	}

	public static void init(FMLInitializationEvent event) {  }
	public static void postInit(FMLPostInitializationEvent event) {  }

}
