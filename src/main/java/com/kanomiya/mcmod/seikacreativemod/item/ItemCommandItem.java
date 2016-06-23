package com.kanomiya.mcmod.seikacreativemod.item;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.List;

import com.kanomiya.mcmod.seikacreativemod.SeikaCreativeMod;
import com.kanomiya.mcmod.seikacreativemod.util.EditUtil;

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
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemCommandItem extends Item {

	public ItemCommandItem() {
		setMaxStackSize(1);
		setHasSubtypes(true);
		setCreativeTab(SeikaCreativeMod.tabSeika);
		setUnlocalizedName("itemCommandItem");
	}

	@Override
	public EnumActionResult onItemUse(ItemStack itemStackIn, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if (worldIn.isRemote || ! playerIn.isSneaking()) { return EnumActionResult.PASS; }

		if (worldIn.getBlockState(pos) == null) { return EnumActionResult.PASS; }
		Block b = worldIn.getBlockState(pos).getBlock();

		NBTTagList list =  EditUtil.getItemStackTag(itemStackIn).getTagList("CommandList", NBT.TAG_STRING);

		if (b == Blocks.COMMAND_BLOCK) {
			TileEntityCommandBlock te = (TileEntityCommandBlock) worldIn.getTileEntity(pos);
			if (te != null) {
				String command = te.getCommandBlockLogic().getCommand();

				if (! command.equals("")) {
					list.appendTag(new NBTTagString(command));
					playerIn.addChatMessage(new TextComponentString("Command set: " + command));
				} else {
					while (list.tagCount() > 0) {
						list.removeTag(0);
					}
					playerIn.addChatMessage(new TextComponentString("Command reset"));
				}

				EditUtil.getItemStackTag(itemStackIn).setTag("CommandList", list);
				return EnumActionResult.SUCCESS;
			}
		} else if (b == Blocks.CRAFTING_TABLE) {
			if (list.tagCount() == 0) { return EnumActionResult.FAIL; }

			Clipboard cp = Toolkit.getDefaultToolkit().getSystemClipboard();
			String str = "";
			for (int i=0; i<list.tagCount(); i++) {
				str += list.getStringTagAt(i) + "\n";
			}

			StringSelection ss = new StringSelection(str);
			cp.setContents(ss, ss);

			playerIn.addChatMessage(new TextComponentString("The Command is now in your Clipboard"));
		}

		return EnumActionResult.PASS;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand)
	{
		if(worldIn.isRemote) { return new ActionResult<ItemStack>(EnumActionResult.PASS, itemStackIn); }

		NBTTagList list =  EditUtil.getItemStackTag(itemStackIn).getTagList("CommandList", NBT.TAG_STRING);

		if (! playerIn.isSneaking() && list.tagCount() > 0){
			MinecraftServer minecraftserver = worldIn.getMinecraftServer();

			if (minecraftserver != null && minecraftserver.isCommandBlockEnabled()) {
				ICommandManager icommandmanager = minecraftserver.getCommandManager();

				for (int i=0; i<list.tagCount(); i++) {
					icommandmanager.executeCommand(playerIn, list.getStringTagAt(i));
				}
			}

			return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStackIn);
		}

		return new ActionResult<ItemStack>(EnumActionResult.PASS, itemStackIn);
	}







	@Override
	public void addInformation(ItemStack item, EntityPlayer player, List<String> list, boolean bool) {
		NBTTagList taglist =  EditUtil.getItemStackTag(item).getTagList("CommandList", NBT.TAG_STRING);

		if (taglist.tagCount() > 0) {
			for (int i=0; i<taglist.tagCount(); i++) {
				list.add(taglist.getStringTagAt(i));
			}

		} else {
			list.add("Empty");
		}
	}


	@Override
	public boolean hasEffect(ItemStack item) {
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
	public void getSubItems(Item par1, CreativeTabs tabs, List<ItemStack> list) {
		list.add(new ItemStack(this, 1, 0));
		list.add(new ItemStack(this, 1, 1));
	}



}
