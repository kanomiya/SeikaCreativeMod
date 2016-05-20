package com.kanomiya.mcmod.seikacreativemod.block;

import net.minecraft.block.BlockGlass;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import com.kanomiya.mcmod.seikacreativemod.SeikaCreativeMod;

public class BlockBedrockGlass extends BlockGlass {

	public BlockBedrockGlass() {
		super(Material.ROCK, false);

		setBlockUnbreakable();

		setResistance(6000000.0F);
		setUnlocalizedName("blockBedrockGlass");
		setCreativeTab(SeikaCreativeMod.tabSeika);
		disableStats();
	}


	@Override
	public boolean canEntityDestroy(IBlockState state, IBlockAccess world, BlockPos pos, Entity entity) {
		return (entity instanceof EntityPlayer);
	}

}
