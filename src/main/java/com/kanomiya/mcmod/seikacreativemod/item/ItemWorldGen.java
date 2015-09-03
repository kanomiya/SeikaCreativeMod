package com.kanomiya.mcmod.seikacreativemod.item;

import com.kanomiya.mcmod.seikacreativemod.SeikaCreativeMod;
import com.kanomiya.mcmod.seikacreativemod.util.EditUtil;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.feature.WorldGenTrees;

public class ItemWorldGen extends Item {

	public ItemWorldGen() {
		setMaxStackSize(1);
		// setHasSubtypes(true);
		setCreativeTab(SeikaCreativeMod.tabSeika);
		setUnlocalizedName("itemWorldGen");
	}

	@Override
	public boolean onItemUse(ItemStack item, EntityPlayer player, World world, BlockPos pos, EnumFacing side,
			float hitX, float hitY, float hitZ) {
		if (world.isRemote) { return false; }

		Chunk chunk = world.getChunkFromBlockCoords(pos);
		int ix = chunk.xPosition *16;
		int iz = chunk.zPosition *16;


		switch (item.getItemDamage()) {
		case 0:
			WorldGenTrees tgen = new WorldGenTrees(true);
			int rate = itemRand.nextInt(10) + 5;

			for (int i=0; i<rate; i++) {
				int gx = ix +itemRand.nextInt(15);
				int gz = iz +itemRand.nextInt(15);
				int gy = EditUtil.getYFacingTheSky(world, gx, pos.getY(), gz);

				tgen.generate(world, itemRand, new BlockPos(gx, gy, gz));
			}

			return true;

		}

		return false;
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
