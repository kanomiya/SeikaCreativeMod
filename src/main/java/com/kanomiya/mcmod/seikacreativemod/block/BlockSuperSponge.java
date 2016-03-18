package com.kanomiya.mcmod.seikacreativemod.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.kanomiya.mcmod.seikacreativemod.SeikaCreativeMod;

public class BlockSuperSponge extends Block {
	public static int EDIT_LIMIT;

	public BlockSuperSponge() {
		super(Material.sponge);
		setCreativeTab(SeikaCreativeMod.tabSeika);
		setUnlocalizedName("blockSuperSponge");
	}


	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
		if (! world.isRemote) { drain(world, pos, 0); }

	}

	private void drain(World world, BlockPos pos, int count) {
		if (count >= EDIT_LIMIT) { return ; }

		if (world.getBlockState(pos).getBlock() instanceof BlockLiquid) {
			world.setBlockToAir(pos);
			count ++;
		}

		if (world.getBlockState(pos.up()).getBlock() instanceof BlockLiquid) {
			drain(world, pos.up(), count);
		}
		if (world.getBlockState(pos.down()).getBlock() instanceof BlockLiquid) {
			drain(world, pos.down(), count);
		}
		if (world.getBlockState(pos.add(0, 0, -1)).getBlock() instanceof BlockLiquid) {
			drain(world, pos.add(0, 0, -1), count);
		}
		if (world.getBlockState(pos.add(0, 0, +1)).getBlock() instanceof BlockLiquid) {
			drain(world, pos.add(0, 0, +1), count);
		}
		if (world.getBlockState(pos.add(-1, 0, 0)).getBlock() instanceof BlockLiquid) {
			drain(world, pos.add(-1, 0, 0), count);
		}
		if (world.getBlockState(pos.add(+1, 0, 0)).getBlock() instanceof BlockLiquid) {
			drain(world, pos.add(+1, 0, 0), count);
		}
	}

}
