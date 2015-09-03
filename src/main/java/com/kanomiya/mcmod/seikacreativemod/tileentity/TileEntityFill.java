package com.kanomiya.mcmod.seikacreativemod.tileentity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;

public class TileEntityFill extends TileEntityRedstoneMachine {
	private IBlockState putState;
	public int sidelength;

	public TileEntityFill() {
		super();
		setPutState(Blocks.air.getDefaultState());
		sidelength = -1;
	}

	public void setPutState(IBlockState state) {
		putState = state;
	}

	public IBlockState getPutState() {
		return putState;
	}

}
