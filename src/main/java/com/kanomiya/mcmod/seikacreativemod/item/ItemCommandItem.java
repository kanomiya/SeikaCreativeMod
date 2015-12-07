package com.kanomiya.mcmod.seikacreativemod.item;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.command.ICommandManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntityCommandBlock;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.kanomiya.mcmod.seikacreativemod.SeikaCreativeMod;
import com.kanomiya.mcmod.seikacreativemod.util.EditUtil;

public class ItemCommandItem extends Item {

	public ItemCommandItem() {
		setMaxStackSize(1);
		setHasSubtypes(true);
		setCreativeTab(SeikaCreativeMod.tabSeika);
		setUnlocalizedName("itemCommandItem");
	}

	@Override
	public boolean onItemUse(ItemStack item, EntityPlayer player, World world, BlockPos pos, EnumFacing side,
			float hitX, float hitY, float hitZ) {
		if (world.isRemote || ! player.isSneaking()) { return false; }

		if (world.getBlockState(pos) == null) { return false; }
		Block b = world.getBlockState(pos).getBlock();

		NBTTagList list =  EditUtil.getItemStackTag(item).getTagList("CommandList", NBT.TAG_STRING);

		if (b == Blocks.command_block) {
			TileEntityCommandBlock te = (TileEntityCommandBlock) world.getTileEntity(pos);
			if (te != null) {
				String command = te.getCommandBlockLogic().getCommand();

				if (! command.equals("")) {
					list.appendTag(new NBTTagString(command));
					player.addChatMessage(new ChatComponentText("Command set: " + command));
				} else {
					while (list.tagCount() > 0) {
						list.removeTag(0);
					}
					player.addChatMessage(new ChatComponentText("Command reset"));
				}

				EditUtil.getItemStackTag(item).setTag("CommandList", list);
				return true;
			}
		} else if (b == Blocks.crafting_table) {
			if (list.tagCount() == 0) { return false; }

			Clipboard cp = Toolkit.getDefaultToolkit().getSystemClipboard();
			String str = "";
			for (int i=0; i<list.tagCount(); i++) {
				str += list.getStringTagAt(i) + "\n";
			}

			StringSelection ss = new StringSelection(str);
			cp.setContents(ss, ss);

			player.addChatMessage(new ChatComponentText("Its Command is now in the Clipboard"));
		}

		return false;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player) {
		if(world.isRemote) { return item; }

		NBTTagList list =  EditUtil.getItemStackTag(item).getTagList("CommandList", NBT.TAG_STRING);

		if (! player.isSneaking() && list.tagCount() > 0){
			MinecraftServer minecraftserver = MinecraftServer.getServer();

			if (minecraftserver != null && minecraftserver.isCommandBlockEnabled()) {
				ICommandManager icommandmanager = minecraftserver.getCommandManager();

				for (int i=0; i<list.tagCount(); i++) {
					icommandmanager.executeCommand(player, list.getStringTagAt(i));
				}
			}
		}

		return item;
	}







	@Override
	public void addInformation(ItemStack item, EntityPlayer player, List list, boolean bool) {
		NBTTagList taglist =  EditUtil.getItemStackTag(item).getTagList("CommandList", NBT.TAG_STRING);

		if (taglist.tagCount() > 0) {
			for (int i=0; i<taglist.tagCount(); i++) {
				list.add(taglist.getStringTagAt(i));
			}

		} else {
			list.add("Empty");
		}
	}


	@Override public boolean hasEffect(ItemStack item) {
		NBTTagList list = EditUtil.getItemStackTag(item).getTagList("CommandList", NBT.TAG_STRING);

		if (list.tagCount() > 0){
			return true;
		}

		return false;
	}

	@Override
	public int getMetadata(int par1) {
		return par1;
	}

	@Override
	public String getUnlocalizedName(ItemStack item) {
		return this.getUnlocalizedName() + "_" + item.getItemDamage();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item par1, CreativeTabs tabs, List list) {
		list.add(new ItemStack(this, 1, 0));
		list.add(new ItemStack(this, 1, 1));
	}



}
