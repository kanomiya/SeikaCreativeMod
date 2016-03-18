package com.kanomiya.mcmod.seikacreativemod.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.feature.WorldGenTrees;

import com.kanomiya.mcmod.seikacreativemod.SeikaCreativeMod;
import com.kanomiya.mcmod.seikacreativemod.util.EditUtil;

public class ItemWorldGen extends Item {

	public ItemWorldGen() {
		setMaxStackSize(1);
		// setHasSubtypes(true);
		setCreativeTab(SeikaCreativeMod.tabSeika);
		setUnlocalizedName("itemWorldGen");
	}

	@Override
	public EnumActionResult onItemUse(ItemStack itemStackIn, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if (worldIn.isRemote) { return EnumActionResult.PASS; }

		Chunk chunk = worldIn.getChunkFromBlockCoords(pos);
		int ix = chunk.xPosition *16;
		int iz = chunk.zPosition *16;


		switch (itemStackIn.getItemDamage()) {
		case 0:
			WorldGenTrees tgen = new WorldGenTrees(true);
			int rate = itemRand.nextInt(10) + 5;

			for (int i=0; i<rate; i++) {
				int gx = ix +itemRand.nextInt(15);
				int gz = iz +itemRand.nextInt(15);
				int gy = EditUtil.getYFacingTheSky(worldIn, gx, pos.getY(), gz);

				tgen.generate(worldIn, itemRand, new BlockPos(gx, gy, gz));
			}

			return EnumActionResult.SUCCESS;

		}

		return EnumActionResult.PASS;
	}



	/*
	@Override
	public String getUnlocalizedName(ItemStack item) {
		return this.getUnlocalizedName() + "_" + item.getItemDamage();
	}

	@Override
	public int getMetadata(int par1) {
		return par1;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item par1, CreativeTabs tabs, List list) {
		list.add(new ItemStack(this, 1, 0));
	}
	*/


}
