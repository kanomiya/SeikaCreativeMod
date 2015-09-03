package com.kanomiya.mcmod.seikacreativemod.block;

import com.kanomiya.mcmod.seikacreativemod.SeikaCreativeMod;

import net.minecraft.block.BlockGlass;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockBedrockGlass extends BlockGlass {

	public BlockBedrockGlass() {
		super(Material.rock, false);

		setBlockUnbreakable();

		setResistance(6000000.0F);
		setUnlocalizedName("blockBedrockGlass");
		setCreativeTab(SeikaCreativeMod.tabSeika);
		disableStats();
	}


	@Override
	public boolean canEntityDestroy(IBlockAccess world, BlockPos pos, Entity entity) {
		return (entity instanceof EntityPlayer);
	}

}