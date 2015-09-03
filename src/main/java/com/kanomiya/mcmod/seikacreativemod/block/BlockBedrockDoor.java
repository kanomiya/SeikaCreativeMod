package com.kanomiya.mcmod.seikacreativemod.block;

import net.minecraft.block.BlockDoor;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockBedrockDoor extends BlockDoor {

	public BlockBedrockDoor() {
		super(Material.rock);

		setBlockUnbreakable();

		setResistance(6000000.0F);
		setStepSound(soundTypePiston);
		setUnlocalizedName("blockBedrockDoor");
		disableStats();
	}

	@Override
	public boolean canEntityDestroy(IBlockAccess world, BlockPos pos, Entity entity) {
		return (entity instanceof EntityPlayer);
	}

}