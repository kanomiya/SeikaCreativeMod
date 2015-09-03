package com.kanomiya.mcmod.seikacreativemod.block;

import com.kanomiya.mcmod.seikacreativemod.SeikaCreativeMod;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockBedrockLight extends Block {

	public BlockBedrockLight() {
		super(Material.rock);

		setBlockUnbreakable();

		setResistance(6000000.0F);
		setLightLevel(1.0f);
		setStepSound(soundTypePiston);
		setUnlocalizedName("blockBedrockLight");
		setCreativeTab(SeikaCreativeMod.tabSeika);
		disableStats();

	}


	@Override
	public boolean canEntityDestroy(IBlockAccess world, BlockPos pos, Entity entity) {
		return (entity instanceof EntityPlayer);
	}

}
