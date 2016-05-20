package com.kanomiya.mcmod.seikacreativemod.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import com.kanomiya.mcmod.seikacreativemod.SeikaCreativeMod;

public class BlockBedrockLight extends Block {

	public BlockBedrockLight() {
		super(Material.ROCK);

		setBlockUnbreakable();

		setResistance(6000000.0F);
		setLightLevel(1.0f);
		setSoundType(SoundType.STONE);
		setUnlocalizedName("blockBedrockLight");
		setCreativeTab(SeikaCreativeMod.tabSeika);
		disableStats();

	}


	@Override
	public boolean canEntityDestroy(IBlockState state, IBlockAccess world, BlockPos pos, Entity entity) {
		return (entity instanceof EntityPlayer);
	}

}
