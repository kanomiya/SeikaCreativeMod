package com.kanomiya.mcmod.seikacreativemod.tileentity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;

import com.kanomiya.mcmod.kanomiyacore.tileentity.IRedstoneMachine;


public class TileEntityFill extends TileEntity implements IRedstoneMachine {
	private IBlockState putState;
	public int sidelength;
	protected boolean previousRedstoneState;

	public TileEntityFill() {
		super();
		setPutState(Blocks.AIR.getDefaultState());
		sidelength = -1;
	}

	public void setPutState(IBlockState state) {
		putState = state;
	}

	public IBlockState getPutState() {
		return putState;
	}



	/**
	* @inheritDoc
	*/
	@Override public boolean isPrevPowered() { return previousRedstoneState; }

	/**
	* @inheritDoc
	*/
	@Override public void setPrevRSState(boolean powered) {
		previousRedstoneState = powered;
	}


}
