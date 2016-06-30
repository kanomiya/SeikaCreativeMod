package com.kanomiya.mcmod.seikacreativemod;

import static com.kanomiya.mcmod.kanomiyacore.util.KCUtils.Client.*;
import static com.kanomiya.mcmod.seikacreativemod.SeikaCreativeMod.*;

import java.util.List;

import com.kanomiya.mcmod.seikacreativemod.item.ItemCommandItem;
import com.kanomiya.mcmod.seikacreativemod.item.ItemEntityKiller;
import com.kanomiya.mcmod.seikacreativemod.item.ItemPipette;
import com.kanomiya.mcmod.seikacreativemod.item.ItemPosWand;
import com.kanomiya.mcmod.seikacreativemod.item.ItemWorldGen;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemDoor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


public class SCMItems {

	public static ItemCommandItem itemCommandItem = new ItemCommandItem();
	public static ItemEntityKiller itemEntityKiller = new ItemEntityKiller();
	public static ItemWorldGen itemWorldGen = new ItemWorldGen();
	public static ItemPipette itemPipette = new ItemPipette();
	public static ItemDoor itemBedrockDoor = new ItemDoor(SCMBlocks.blockBedrockDoor) {
	    {
	        setUnlocalizedName("blockBedrockDoor");
	        setCreativeTab(tabSeika);
	    }

        @Override @SideOnly(Side.CLIENT)
        public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced) {
            tooltip.add("(Texture: Acacia)");
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

        GameRegistry.register(itemBedrockDoor, new ResourceLocation(MODID, "blockBedrockDoor"));
        simpleRegister.accept(itemBedrockDoor);

        GameRegistry.register(itemPosWand, new ResourceLocation(MODID, "itemPosWand"));
        simpleRegister.accept(itemPosWand);

	}

}
