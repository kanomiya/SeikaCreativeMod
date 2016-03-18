package com.kanomiya.mcmod.seikacreativemod.block;

import net.minecraft.block.BlockDoor;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockBedrockDoor extends BlockDoor {

	public BlockBedrockDoor() {
		super(Material.rock);

		setBlockUnbreakable();

		setResistance(6000000.0F);
		setStepSound(SoundType.STONE);
		setUnlocalizedName("blockBedrockDoor");
		disableStats();
	}

	@Override
	public boolean canEntityDestroy(IBlockState state, IBlockAccess world, BlockPos pos, Entity entity) {
		return (entity instanceof EntityPlayer);
	}

}
