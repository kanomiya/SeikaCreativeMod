package com.kanomiya.mcmod.seikacreativemod.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class EntitySandBag extends EntityLiving {
	EntityPlayer owner;
	boolean notifyOnFalling;

	public EntitySandBag(World world) {
		super(world);

		getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(1000.0d);
		getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.0d);

		setHealth(getMaxHealth());
		experienceValue = 0;

		owner = worldObj.getClosestPlayerToEntity(this, 16f);
		notifyOnFalling = false;
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float damage) {
		boolean bool = super.attackEntityFrom(source, damage);
		if (worldObj.isRemote) { return bool; }

		if (owner == null) { owner = attackingPlayer; }

		if (owner != null && damage > 0.0f) {
			if (owner.isSneaking()) {
				entityDropItem(new ItemStack(Items.spawn_egg, 1, EntityList.getEntityID(this)), 1.0f);
				setDead();
				return bool;
			}

			owner.addChatMessage(new TextComponentString(((hasCustomName()) ? getCustomNameTag(): "SandBag") +
											"> Damage: "+ damage + "(" + getHealth() + "/" + getMaxHealth() + ")" +
											" Type: " + source.damageType));

			if (source == DamageSource.fall && notifyOnFalling) {
				owner.addChatMessage(new TextComponentString(((hasCustomName()) ? getCustomNameTag(): "SandBag") +
											"> Fell at : (" + (int)posX + "," + (int)posY + "," + (int)posZ + ")"));

			}

		}


		return bool;
	}

	@Override
	public boolean hitByEntity(Entity entity) {
		if (worldObj.isRemote) { return super.hitByEntity(entity); }

		if (entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;
			ItemStack is = player.getHeldItemMainhand();

			if (is != null && is.getItem() == Item.getItemFromBlock(Blocks.tnt)) {
				if (! notifyOnFalling) {
					notifyOnFalling = true;
					player.addChatMessage(new TextComponentString(((hasCustomName()) ? getCustomNameTag(): "SandBag") +
												"> Falling Notify is Enabled"));
				}
			} else if (notifyOnFalling) {
				notifyOnFalling = false;
				player.addChatMessage(new TextComponentString(((hasCustomName()) ? getCustomNameTag(): "SandBag") +
											"> Falling Notify is Disabled"));

			}
		}

		return super.hitByEntity(entity);
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
	}

	@Override
	protected void jump() { }

	@Override
	protected void onDeathUpdate() {
		if (owner != null && ! worldObj.isRemote) {
			setHealth(getMaxHealth());
		}
	}

}
