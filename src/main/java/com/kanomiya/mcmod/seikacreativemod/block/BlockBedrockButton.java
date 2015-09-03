package com.kanomiya.mcmod.seikacreativemod.block;

import com.kanomiya.mcmod.seikacreativemod.SeikaCreativeMod;

import net.minecraft.block.Block;
import net.minecraft.block.BlockButton;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockBedrockButton extends BlockButton {

	public BlockBedrockButton() {
		super(false);

		setBlockUnbreakable();

		setResistance(6000000.0F);
		setStepSound(soundTypePiston);
		setUnlocalizedName("blockButtonBedrock");
		setCreativeTab(SeikaCreativeMod.tabSeika);
		disableStats();
	}

	@Override
	public boolean canEntityDestroy(IBlockAccess world, BlockPos pos, Entity entity) {
		return (entity instanceof EntityPlayer);
	}

	@Override
	public void onNeighborBlockChange(World world, BlockPos pos, IBlockState state, Block neighbor) { }

}
