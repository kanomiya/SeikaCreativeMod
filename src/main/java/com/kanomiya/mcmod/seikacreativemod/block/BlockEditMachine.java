package com.kanomiya.mcmod.seikacreativemod.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.kanomiya.mcmod.seikacreativemod.SeikaCreativeMod;
import com.kanomiya.mcmod.seikacreativemod.tileentity.TileEntityEditMachine;

public class BlockEditMachine extends BlockContainer {

	public BlockEditMachine() {
		super(Material.GROUND);
		setCreativeTab(SeikaCreativeMod.tabSeika);
		setUnlocalizedName("blockEditMachine");

		setHardness(0.5f);
		setResistance(1.0f);
		setSoundType(SoundType.STONE);

	}

	// RS切断
	@Override
	public boolean isOpaqueCube(IBlockState state) { return false; }

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		TileEntityEditMachine te = (TileEntityEditMachine) worldIn.getTileEntity(pos);
		if (te == null) { return false; }

		te.activate(playerIn);

		return true;
	}



	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn)
	{
		onBlockAdded(worldIn, pos, state);
	}

	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
		if (world.isRemote) { return ; }

		TileEntityEditMachine te = (TileEntityEditMachine) world.getTileEntity(pos);
		if (te == null) { return ; }

		if (world.isBlockPowered(pos)) {
			if (! te.isPrevPowered()) {
				te.launch();
			}

			te.setPrevRSState(true);
		} else {
			te.setPrevRSState(false);
		}

	}


	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityEditMachine();
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state)
	{
		return EnumBlockRenderType.MODEL;
	}
}
