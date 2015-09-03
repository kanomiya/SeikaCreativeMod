package com.kanomiya.mcmod.seikacreativemod.block;

import com.kanomiya.mcmod.seikacreativemod.SeikaCreativeMod;
import com.kanomiya.mcmod.seikacreativemod.tileentity.TileEntityEditMachine;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BlockEditMachine extends BlockContainer {

	public BlockEditMachine() {
		super(Material.ground);
		setCreativeTab(SeikaCreativeMod.tabSeika);
		setUnlocalizedName("blockEditMachine");

		setHardness(0.5f);
		setResistance(1.0f);
		setStepSound(Block.soundTypeStone);

	}

	// RS切断
	@Override
	public boolean isOpaqueCube() { return false; }

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ) {
		TileEntityEditMachine te = (TileEntityEditMachine) world.getTileEntity(pos);
		if (te == null) { return false; }

		te.activate(player);

		return true;
	}



	@Override
	public void onNeighborBlockChange(World world, BlockPos pos, IBlockState state, Block neighborBlock) {
		onBlockAdded(world, pos, state);
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
	public int getRenderType() {
		return 3;
	}
}
