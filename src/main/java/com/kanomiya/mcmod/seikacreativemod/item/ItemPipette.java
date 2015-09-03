package com.kanomiya.mcmod.seikacreativemod.item;

import java.util.List;

import com.kanomiya.mcmod.seikacreativemod.SeikaCreativeMod;
import com.kanomiya.mcmod.seikacreativemod.util.EditUtil;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemPipette extends Item {

	public ItemPipette() {
		setCreativeTab(SeikaCreativeMod.tabSeika);
		setMaxStackSize(1);

		setUnlocalizedName("itemPipette");
	}


	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {

		if (player.isSneaking()) {
			if (! world.isAirBlock(pos)) {
				IBlockState state = world.getBlockState(pos);
				NBTTagCompound tag = EditUtil.getItemStackTag(stack).getCompoundTag("dataPool");

				tag.setString("blockname", state.getBlock().getLocalizedName());
				tag.setInteger("blockstateid", Block.getStateId(state));

				TileEntity te = world.getTileEntity(pos);
				if (te != null) {
					NBTTagCompound tetag = tag.getCompoundTag("tileentity");
					te.writeToNBT(tetag);

					tag.setTag("tileentity", tetag);
				} else {
					tag.removeTag("tileentity");
				}

				EditUtil.getItemStackTag(stack).setTag("dataPool", tag);
				return true;
			}

		} else if (! world.isRemote) {
			NBTTagCompound tag = EditUtil.getItemStackTag(stack).getCompoundTag("dataPool");

			if (! tag.hasNoTags()) {
				BlockPos newPos = pos.add(side.getFrontOffsetX(), side.getFrontOffsetY(), side.getFrontOffsetZ());
				IBlockState state = Block.getStateById(tag.getInteger("blockstateid"));

				world.setBlockState(newPos, state);
				state.getBlock().onBlockPlacedBy(world, newPos, state, player, stack);

				if (tag.hasKey("tileentity")) {
					NBTTagCompound tetag = tag.getCompoundTag("tileentity");
					TileEntity te = state.getBlock().createTileEntity(world, state);

					if (te != null) {
						te.readFromNBT(tetag);
						world.setTileEntity(newPos, te);
					}

				}

				return true;
			}

		}

		return false;
	}

	@Override
	public void addInformation(ItemStack item, EntityPlayer player, List list, boolean bool) {
		if (hasEffect(item)) {
			NBTTagCompound tag = EditUtil.getItemStackTag(item).getCompoundTag("dataPool");
			list.add("Name: " + tag.getString("blockname"));
			list.add("StateId: " + tag.getInteger("blockstateid"));
			list.add("HasTileEntity: " + tag.hasKey("tileentity"));
		}
	}



	@Override
	public boolean hasEffect(ItemStack item) {
		return ! EditUtil.getItemStackTag(item).getCompoundTag("dataPool").hasNoTags();
	}

}
