package com.kanomiya.mcmod.seikacreativemod.item;

import java.util.List;

import com.kanomiya.mcmod.seikacreativemod.SeikaCreativeMod;
import com.kanomiya.mcmod.seikacreativemod.network.MessageEntityPicker;
import com.kanomiya.mcmod.seikacreativemod.network.PacketHandler;
import com.kanomiya.mcmod.seikacreativemod.util.EditUtil;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants.NBT;

public class ItemEntityPicker extends Item {

	public ItemEntityPicker() {
		setMaxStackSize(1);
		setCreativeTab(SeikaCreativeMod.tabSeika);
		setUnlocalizedName("itemEntityPicker");
	}

	@Override
	public boolean itemInteractionForEntity(ItemStack item, EntityPlayer player, EntityLivingBase entity) {

		NBTTagCompound nbt = EditUtil.getItemStackTag(item);

		NBTTagCompound entityNbt = new NBTTagCompound();
		entity.writeToNBT(entityNbt);

		entityNbt.setString("id", EntityList.getEntityString(entity));
		entityNbt.setString("_entityName", entity.getName());
		entityNbt.setFloat("_health", entity.getHealth());
		entityNbt.setFloat("_maxHealth", entity.getMaxHealth());
		nbt.setTag("PickedEntity", entityNbt);

		item.setTagCompound(nbt);

		int slot = player.inventory.currentItem;

		PacketHandler.INSTANCE.sendToServer(new MessageEntityPicker(slot, item));

		if (! player.isSneaking()) entity.setDead();

		return true;
	}


	@Override
	public boolean onItemUse(ItemStack item, EntityPlayer player, World world, BlockPos pos, EnumFacing side,
			float hitX, float hitY, float hitZ) {

		if (world.isRemote) { return false; }

		if (! player.canPlayerEdit(pos.offset(side), side, item)) {
			return false;
		}

		NBTTagCompound nbt =  EditUtil.getItemStackTag(item);

		if (nbt.hasKey("PickedEntity", NBT.TAG_COMPOUND)){
			NBTTagCompound entityNbt = nbt.getCompoundTag("PickedEntity");

			Entity entity = EntityList.createEntityFromNBT(entityNbt, world);
			entity.dimension = player.dimension;

			pos = pos.offset(side);

			entity.setPosition(pos.getX(), pos.getY(), pos.getZ());

			entity.fallDistance = 0f;

			world.spawnEntityInWorld(entity);

			if (! player.isSneaking()) {
				nbt.removeTag("PickedEntity");
			}

		}

		return true;
	}

	@Override
	public void addInformation(ItemStack item, EntityPlayer player, List list, boolean bool) {
		NBTTagCompound nbt =  EditUtil.getItemStackTag(item);

		if (nbt.hasKey("PickedEntity", NBT.TAG_COMPOUND)) {
			NBTTagCompound entityNbt = nbt.getCompoundTag("PickedEntity");

			list.add(entityNbt.getString("_entityName") + " " + entityNbt.getFloat("_health") + "/" + entityNbt.getFloat("_maxHealth"));

		} else {
			list.add("Empty");
		}
	}


	@Override public boolean hasEffect(ItemStack item) {
		return EditUtil.getItemStackTag(item).hasKey("PickedEntity", NBT.TAG_COMPOUND);
	}

}
