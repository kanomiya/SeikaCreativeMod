package com.kanomiya.mcmod.seikacreativemod.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLever;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.kanomiya.mcmod.seikacreativemod.SeikaCreativeMod;

public class BlockBedrockLever extends BlockLever {

	public BlockBedrockLever() {
		super();

		setBlockUnbreakable();

		setResistance(6000000.0F);
		setUnlocalizedName("blockBedrockLever");
		setCreativeTab(SeikaCreativeMod.tabSeika);
		disableStats();
	}

	@Override
	public boolean canEntityDestroy(IBlockState state, IBlockAccess world, BlockPos pos, Entity entity) {
		return (entity instanceof EntityPlayer);
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn) {  }

}
