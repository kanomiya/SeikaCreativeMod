package com.kanomiya.mcmod.seikacreativemod.item;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants.NBT;

import com.kanomiya.mcmod.seikacreativemod.SeikaCreativeMod;
import com.kanomiya.mcmod.seikacreativemod.network.MessageEntityPicker;
import com.kanomiya.mcmod.seikacreativemod.network.PacketHandler;
import com.kanomiya.mcmod.seikacreativemod.util.EditUtil;

public class ItemPipette extends Item {

	public ItemPipette() {
		setCreativeTab(SeikaCreativeMod.tabSeika);
		setMaxStackSize(1);

		setUnlocalizedName("itemPipette");
	}


	@Override public ItemStack onItemRightClick(ItemStack stack, World worldIn, EntityPlayer playerIn) {
		if (playerIn.isSneaking()) {
			EditUtil.getItemStackTag(stack).removeTag("dataPool");
			return stack;
		}

		return stack;
	}

	@Override public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
		NBTTagCompound tag = EditUtil.getItemStackTag(stack).getCompoundTag("dataPool");

		if (player.isSneaking()) {
			if (! world.isAirBlock(pos)) {
				tag = new NBTTagCompound();

				IBlockState state = world.getBlockState(pos);

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

			if (! tag.hasNoTags()) {
				if (tag.hasKey("blockstateid")) {
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

				} else if (tag.hasKey("pickedEntity", NBT.TAG_COMPOUND)) {
					if (player.canPlayerEdit(pos.offset(side), side, stack)) {

						NBTTagCompound entityNbt = tag.getCompoundTag("pickedEntity");

						Entity entity = EntityList.createEntityFromNBT(entityNbt, world);
						entity.dimension = player.dimension;

						pos = pos.offset(side);

						entity.setPosition(pos.getX(), pos.getY(), pos.getZ());

						entity.fallDistance = 0f;

						world.spawnEntityInWorld(entity);
					}
				}

				return true;
			}

		}


		return false;
	}

	@Override public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase entity) {
		NBTTagCompound tag = new NBTTagCompound();

		NBTTagCompound entityNbt = new NBTTagCompound();
		entity.writeToNBT(entityNbt);

		entityNbt.setString("id", EntityList.getEntityString(entity));
		entityNbt.setString("_entityName", entity.getName());
		entityNbt.setFloat("_health", entity.getHealth());
		entityNbt.setFloat("_maxHealth", entity.getMaxHealth());
		tag.setTag("pickedEntity", entityNbt);


		EditUtil.getItemStackTag(stack).setTag("dataPool", tag);

		int slot = player.inventory.currentItem;

		PacketHandler.INSTANCE.sendToServer(new MessageEntityPicker(slot, stack));

		if (! player.isSneaking()) entity.setDead();

		return true;
	}

	@Override public void addInformation(ItemStack item, EntityPlayer player, List list, boolean bool) {
		if (hasEffect(item)) {
			NBTTagCompound tag = EditUtil.getItemStackTag(item).getCompoundTag("dataPool");

			if (tag.hasKey("blockstateid")) {
				list.add("Name: " + tag.getString("blockname"));
				list.add("StateId: " + tag.getInteger("blockstateid"));
				list.add("HasTileEntity: " + tag.hasKey("tileentity"));

			}

			if (tag.hasKey("pickedEntity", NBT.TAG_COMPOUND)) {
				NBTTagCompound entityNbt = tag.getCompoundTag("pickedEntity");

				list.add(entityNbt.getString("_entityName") + " " + entityNbt.getFloat("_health") + "/" + entityNbt.getFloat("_maxHealth"));

			}
		}

	}



	@Override public boolean hasEffect(ItemStack item) {
		return ! EditUtil.getItemStackTag(item).getCompoundTag("dataPool").hasNoTags();
	}

}
