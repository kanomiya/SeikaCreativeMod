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
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
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


	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand)
	{
		if (playerIn.isSneaking()) {
			EditUtil.getItemStackTag(itemStackIn).removeTag("dataPool");

			return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStackIn);
		}

		return new ActionResult<ItemStack>(EnumActionResult.PASS, itemStackIn);
	}

	@Override public EnumActionResult onItemUse(ItemStack itemStackIn, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		NBTTagCompound tag = EditUtil.getItemStackTag(itemStackIn).getCompoundTag("dataPool");

		if (playerIn.isSneaking()) {
			if (! worldIn.isAirBlock(pos)) {
				tag = new NBTTagCompound();

				IBlockState state = worldIn.getBlockState(pos);

				tag.setString("blockname", state.getBlock().getLocalizedName());
				tag.setInteger("blockstateid", Block.getStateId(state));

				TileEntity te = worldIn.getTileEntity(pos);
				if (te != null) {
					NBTTagCompound tetag = tag.getCompoundTag("tileentity");
					te.writeToNBT(tetag);

					tag.setTag("tileentity", tetag);
				} else {
					tag.removeTag("tileentity");
				}

				EditUtil.getItemStackTag(itemStackIn).setTag("dataPool", tag);

				return EnumActionResult.SUCCESS;

			}

		} else if (! worldIn.isRemote) {

			if (! tag.hasNoTags()) {
				if (tag.hasKey("blockstateid")) {
					BlockPos newPos = pos.add(facing.getFrontOffsetX(), facing.getFrontOffsetY(), facing.getFrontOffsetZ());
					IBlockState state = Block.getStateById(tag.getInteger("blockstateid"));

					worldIn.setBlockState(newPos, state);
					state.getBlock().onBlockPlacedBy(worldIn, newPos, state, playerIn, itemStackIn);

					if (tag.hasKey("tileentity")) {
						NBTTagCompound tetag = tag.getCompoundTag("tileentity");
						TileEntity te = state.getBlock().createTileEntity(worldIn, state);

						if (te != null) {
							te.readFromNBT(tetag);
							worldIn.setTileEntity(newPos, te);
						}

					}

				} else if (tag.hasKey("pickedEntity", NBT.TAG_COMPOUND)) {
					if (playerIn.canPlayerEdit(pos.offset(facing), facing, itemStackIn)) {

						NBTTagCompound entityNbt = tag.getCompoundTag("pickedEntity");

						Entity entity = EntityList.createEntityFromNBT(entityNbt, worldIn);
						entity.dimension = playerIn.dimension;

						pos = pos.offset(facing);

						entity.setPosition(pos.getX(), pos.getY(), pos.getZ());

						entity.fallDistance = 0f;

						worldIn.spawnEntityInWorld(entity);
					}
				}

				return EnumActionResult.SUCCESS;
			}

		}


		return EnumActionResult.PASS;
	}

	@Override public boolean itemInteractionForEntity(ItemStack itemStackIn, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand) {
		NBTTagCompound tag = new NBTTagCompound();

		NBTTagCompound targetNbt = new NBTTagCompound();
		target.writeToNBT(targetNbt);

		targetNbt.setString("id", EntityList.getEntityString(target));
		targetNbt.setString("_targetName", target.getName());
		targetNbt.setFloat("_health", target.getHealth());
		targetNbt.setFloat("_maxHealth", target.getMaxHealth());
		tag.setTag("pickedEntity", targetNbt);


		EditUtil.getItemStackTag(itemStackIn).setTag("dataPool", tag);

		int slot = playerIn.inventory.currentItem;

		PacketHandler.INSTANCE.sendToServer(new MessageEntityPicker(slot, itemStackIn));

		if (! playerIn.isSneaking()) target.setDead();

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
