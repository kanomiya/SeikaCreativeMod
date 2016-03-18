package com.kanomiya.mcmod.seikacreativemod.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

import com.kanomiya.mcmod.seikacreativemod.SeikaCreativeMod;

public class ItemEntityKiller extends Item {

	public ItemEntityKiller() {
		setMaxStackSize(1);
		setCreativeTab(SeikaCreativeMod.tabSeika);
		setUnlocalizedName("itemEntityKiller");
	}

	@Override
	public boolean itemInteractionForEntity(ItemStack itemStackIn, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand)
	{
		target.setDead();
		return true;
	}

	@Override
	public boolean hitEntity(ItemStack item, EntityLivingBase target, EntityLivingBase source) {
		target.setHealth(0);
		return true;
	}


}
