package com.kanomiya.mcmod.seikacreativemod.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockButton;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.kanomiya.mcmod.seikacreativemod.SeikaCreativeMod;

public class BlockBedrockButton extends BlockButton {

	public BlockBedrockButton() {
		super(false);

		setBlockUnbreakable();

		setResistance(6000000.0F);
		setStepSound(SoundType.STONE);
		setUnlocalizedName("blockButtonBedrock");
		setCreativeTab(SeikaCreativeMod.tabSeika);
		disableStats();
	}

	@Override
	public boolean canEntityDestroy(IBlockState state, IBlockAccess world, BlockPos pos, Entity entity) {
		return (entity instanceof EntityPlayer);
	}

	@Override
	public void onNeighborBlockChange(World world, BlockPos pos, IBlockState state, Block neighbor) { }

	/**
	* @inheritDoc
	*/
	@Override
	protected void func_185615_a(EntityPlayer p_185615_1_, World player, BlockPos pos) {
		// TODO 自動生成されたメソッド・スタブ

	}

	/**
	* @inheritDoc
	*/
	@Override
	protected void func_185617_b(World worldIn, BlockPos pos) {
		// TODO 自動生成されたメソッド・スタブ

	}

}
