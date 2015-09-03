package com.kanomiya.mcmod.seikacreativemod.command;

import java.util.List;
import java.util.Vector;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityOwnable;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.INpc;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class CommandKillPlus extends CommandBase {

	@Override public String getName() {
		return "killp";
	}

	@Override public String getCommandUsage(ICommandSender p_71518_1_) {
		return "seikacreativemod.commands.killp.usage";
	}

	@Override public void execute(ICommandSender sender, String[] strs) {
		if (strs.length == 0) { missCommand(sender); return; }

		Vector<Class> vec = new Vector();

		if (strs[0].equals("living")) { // EntityLiving
			vec.add(EntityLiving.class);
		} else if (strs[0].equals("item")) { // EntityItem
			vec.add(EntityItem.class);
		} else if (strs[0].equals("monster")) { // IMob
			vec.add(IMob.class);
		} else if (strs[0].equals("slime")) { // EntitySlime
			vec.add(EntitySlime.class);
		} else if (strs[0].equals("animal")) { // IAnimals
			vec.add(IAnimals.class);
		} else if (strs[0].equals("tameable")) { // IEntityOwnable || EntityHorse
			vec.add(IEntityOwnable.class);
			vec.add(EntityHorse.class);
		} else if (strs[0].equals("bat")) { // EntityBat
			vec.add(EntityBat.class);
		} else if (strs[0].equals("npc")) { // INpc
			vec.add(INpc.class);
		} else if (strs[0].equals("merchant")) { // IMerchant
			vec.add(IMerchant.class);
		} else if (strs[0].equals("tnt")) { // EntityTNTPrimed
			vec.add(EntityTNTPrimed.class);
		} else if (strs[0].equals("projectile")) { // IProjectile
			vec.add(IProjectile.class);
		} else if (strs[0].equals("player")) { // EntityPlayer
			vec.add(EntityPlayer.class);
		}

		if (0 < vec.size()) {
			int length = 3;

			if (strs.length == 2) {
				try {
					length = Integer.valueOf(strs[1]);
				} catch (NumberFormatException e) {
					missCommand(sender);
				}
			}

			World world = sender.getEntityWorld();
			BlockPos pos = sender.getPosition();
			int killcount = 0;

			AxisAlignedBB aabb = new AxisAlignedBB(pos.add(-length, -length, -length), pos.add(length, length, length));

			List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(sender.getCommandSenderEntity(), aabb);

			for (Entity entity: list) {
				boolean flag = false;

				Class entityClass = entity.getClass();
				for (Class c: vec) {
					flag = c.isAssignableFrom(entityClass);
					if (flag) break;
				}

				if (flag) {
					entity.setDead();
					killcount ++;
				}

			}

			sender.addChatMessage(new ChatComponentText("[KILLP] Killed " + killcount + " Entities (Length: " + length + "block)"));
		} else {
			missCommand(sender); return;
		}

	}

	public void missCommand(ICommandSender sender) {
		sender.addChatMessage(new ChatComponentText("[HELP] /killp [Mode] ([Length])"));
		sender.addChatMessage(new ChatComponentText("[HELP] Mode: living/item/monster/slime/animal/tameable/bat/npc/merchant/tnt/projectile/player"));
	}

}
