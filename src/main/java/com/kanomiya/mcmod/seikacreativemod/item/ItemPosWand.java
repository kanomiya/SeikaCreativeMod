package com.kanomiya.mcmod.seikacreativemod.item;

import java.util.List;

import com.kanomiya.mcmod.seikacreativemod.SeikaCreativeMod;
import com.kanomiya.mcmod.seikacreativemod.util.EditUtil;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemPosWand extends Item {

	public ItemPosWand() {
		setMaxStackSize(1);
		setCreativeTab(SeikaCreativeMod.tabSeika);
		setUnlocalizedName("itemPosWand");
	}

	@Override
	public boolean onItemUse(ItemStack item, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (world.isRemote) { return false; }

		NBTTagCompound nbt = EditUtil.getItemStackTag(item);

		if (player.isSneaking()) {
			int[] ipos = new int[] { pos.getX(), pos.getY(), pos.getZ() };
			nbt.setIntArray("position", ipos);

			return true;
		}

		return false;
	}


	@Override
	public void addInformation(ItemStack item, EntityPlayer player, List list, boolean bool) {
		NBTTagCompound nbt = EditUtil.getItemStackTag(item);

		if (nbt.hasKey("position")) {
			int[] i = nbt.getIntArray("position");
			if (i.length == 3) {
				list.add("Position: (" + i[0] + ", " +i[1] + ", " + i[2] + ")");
			}
		}
	}


	@Override
	public boolean hasEffect(ItemStack item) {
		return EditUtil.getItemStackTag(item).hasKey("position");
	}

}
