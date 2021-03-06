package com.kanomiya.mcmod.seikacreativemod;

import static com.kanomiya.mcmod.kanomiyacore.util.KCUtils.Client.*;
import static com.kanomiya.mcmod.seikacreativemod.SeikaCreativeMod.*;

import com.kanomiya.mcmod.seikacreativemod.item.ItemCommandItem;
import com.kanomiya.mcmod.seikacreativemod.item.ItemEntityKiller;
import com.kanomiya.mcmod.seikacreativemod.item.ItemPipette;
import com.kanomiya.mcmod.seikacreativemod.item.ItemPosWand;
import com.kanomiya.mcmod.seikacreativemod.item.ItemWorldGen;

import net.minecraft.item.ItemDoor;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;


public class SCMItems {

	public static ItemCommandItem itemCommandItem = new ItemCommandItem();
	public static ItemEntityKiller itemEntityKiller = new ItemEntityKiller();
	public static ItemWorldGen itemWorldGen = new ItemWorldGen();
	public static ItemPipette itemPipette = new ItemPipette();
	public static ItemDoor itemBedrockDoor = new ItemDoor(SCMBlocks.blockBedrockDoor) {
	    {
	        setUnlocalizedName("itemBedrockDoor");
	        setCreativeTab(tabSeika);
	    }
    };

	public static ItemPosWand itemPosWand = new ItemPosWand();


	public static void preInit(FMLPreInitializationEvent event)
	{
        GameRegistry.register(itemCommandItem, new ResourceLocation(MODID, "itemCommandItem"));
        arrayRegister.accept(itemCommandItem, new String[] { "", "minecraft:book" });

        GameRegistry.register(itemEntityKiller, new ResourceLocation(MODID, "itemEntityKiller"));
        simpleRegister.accept(itemEntityKiller);

        GameRegistry.register(itemWorldGen, new ResourceLocation(MODID, "itemWorldGen"));
        simpleRegister.accept(itemWorldGen);

        GameRegistry.register(itemPipette, new ResourceLocation(MODID, "itemPipette"));
        simpleRegister.accept(itemPipette);

        GameRegistry.register(itemBedrockDoor, new ResourceLocation(MODID, "itemBedrockDoor"));
        simpleRegister.accept(itemBedrockDoor);

        GameRegistry.register(itemPosWand, new ResourceLocation(MODID, "itemPosWand"));
        simpleRegister.accept(itemPosWand);

	}

}
