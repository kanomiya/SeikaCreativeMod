package com.kanomiya.mcmod.seikacreativemod.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockButton;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.kanomiya.mcmod.seikacreativemod.SeikaCreativeMod;

public class BlockBedrockButton extends BlockButton {

	public BlockBedrockButton() {
		super(false);

		setBlockUnbreakable();

		setResistance(6000000.0F);
		setSoundType(SoundType.STONE);
		setUnlocalizedName("blockButtonBedrock");
		setCreativeTab(SeikaCreativeMod.tabSeika);
		disableStats();
	}

	@Override
	public boolean canEntityDestroy(IBlockState state, IBlockAccess world, BlockPos pos, Entity entity) {
		return (entity instanceof EntityPlayer);
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn) {  }

	/**
	* @inheritDoc
	*/
	@Override
	protected void playClickSound(EntityPlayer player, World worldIn, BlockPos pos)
	{
		worldIn.playSound(player, pos, SoundEvents.BLOCK_STONE_BUTTON_CLICK_ON, SoundCategory.BLOCKS, 0.3F, 0.6F);
	}

	/**
	* @inheritDoc
	*/
	@Override
	protected void playReleaseSound(World worldIn, BlockPos pos)
	{
		worldIn.playSound(null, pos, SoundEvents.BLOCK_STONE_BUTTON_CLICK_OFF, SoundCategory.BLOCKS, 0.3F, 0.5F);
	}

}
