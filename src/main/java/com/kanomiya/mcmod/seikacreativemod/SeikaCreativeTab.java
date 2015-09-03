package com.kanomiya.mcmod.seikacreativemod;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SeikaCreativeTab extends CreativeTabs {
	public SeikaCreativeTab() {
		super("Seika Creative Mod");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Item getTabIconItem() {
		return Item.getItemFromBlock(SCMBlock.fill.getBlock());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getTranslatedTabLabel() {
		return "Seika Creative Mod";
	}

}
