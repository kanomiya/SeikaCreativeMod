package com.kanomiya.mcmod.seikacreativemod.item;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

import com.kanomiya.mcmod.seikacreativemod.SeikaCreativeMod;
import com.kanomiya.mcmod.seikacreativemod.util.EditUtil;

public class ItemPosWand extends Item {

	public ItemPosWand() {
		setMaxStackSize(1);
		setCreativeTab(SeikaCreativeMod.tabSeika);
		setUnlocalizedName("itemPosWand");
	}

	@Override public ItemStack onItemRightClick(ItemStack stackIn, World worldIn, EntityPlayer playerIn) {

		if (! playerIn.isSneaking()) {
			if (EditUtil.hasPositon(stackIn)) {
				int[] posArray = EditUtil.getPositionIntArray(stackIn);
				playerIn.setPosition(EditUtil.intArrayToPosX(posArray), EditUtil.intArrayToPosY(posArray), EditUtil.intArrayToPosZ(posArray));
			}

		}

		return stackIn;
	}

	@Override public boolean onItemUse(ItemStack stackIn, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (worldIn.isRemote) { return false; }

		if (playerIn.isSneaking()) {
			EditUtil.setPositon(stackIn, pos);
			return true;
		}

		return false;
	}


	@Override public void addInformation(ItemStack stackIn, EntityPlayer player, List list, boolean advanced) {

		if (EditUtil.hasPositon(stackIn)) {
			int[] posArray = EditUtil.getPositionIntArray(stackIn);

			if (posArray.length == 3) {
				list.add("Position: (" + EditUtil.intArrayToPosX(posArray) + ", " + EditUtil.intArrayToPosY(posArray) + ", " + EditUtil.intArrayToPosZ(posArray) + ")");
				list.add("[Right Click to Teleport]");
			}
		}
	}


	@Override public boolean hasEffect(ItemStack stackIn) {
		return EditUtil.hasPositon(stackIn);
	}

}
