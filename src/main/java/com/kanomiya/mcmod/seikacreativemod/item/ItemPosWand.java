package com.kanomiya.mcmod.seikacreativemod.item;

import java.util.List;

import com.kanomiya.mcmod.seikacreativemod.SeikaCreativeMod;
import com.kanomiya.mcmod.seikacreativemod.util.EditUtil;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemPosWand extends Item {

	public ItemPosWand() {
		setMaxStackSize(1);
		setCreativeTab(SeikaCreativeMod.tabSeika);
		setUnlocalizedName("itemPosWand");
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand)
	{
		if (! playerIn.isSneaking()) {
			if (EditUtil.hasPositon(itemStackIn)) {
				int[] posArray = EditUtil.getPositionIntArray(itemStackIn);
				playerIn.setPosition(EditUtil.intArrayToPosX(posArray), EditUtil.intArrayToPosY(posArray), EditUtil.intArrayToPosZ(posArray));

				return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStackIn);
			}

		}

		return new ActionResult<ItemStack>(EnumActionResult.PASS, itemStackIn);
	}

	@Override
	public EnumActionResult onItemUse(ItemStack itemStackIn, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if (worldIn.isRemote) { return EnumActionResult.PASS; }

		if (playerIn.isSneaking()) {
			EditUtil.setPositon(itemStackIn, pos);
			return EnumActionResult.SUCCESS;
		}

		return EnumActionResult.PASS;
	}


	@Override
	public void addInformation(ItemStack stackIn, EntityPlayer player, List<String> list, boolean advanced) {

		if (EditUtil.hasPositon(stackIn)) {
			int[] posArray = EditUtil.getPositionIntArray(stackIn);

			if (posArray.length == 3) {
				list.add("Position: (" + EditUtil.intArrayToPosX(posArray) + ", " + EditUtil.intArrayToPosY(posArray) + ", " + EditUtil.intArrayToPosZ(posArray) + ")");
				list.add("[Right Click to Teleport]");
			}
		}
	}


	@Override
	public boolean hasEffect(ItemStack stackIn) {
		return EditUtil.hasPositon(stackIn);
	}

}
