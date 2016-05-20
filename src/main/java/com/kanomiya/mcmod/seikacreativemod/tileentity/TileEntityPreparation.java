package com.kanomiya.mcmod.seikacreativemod.tileentity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;

public class TileEntityPreparation extends TileEntityFill {
	private IBlockState tgtState;
	public int sidelength;
	public boolean setmode;

	public TileEntityPreparation() {
		super();
		setTgtState(Blocks.AIR.getDefaultState());
		sidelength = 0;
		setmode = true;
	}



	public void setTgtState(IBlockState state) {
		tgtState = state;
	}

	public IBlockState getTgtState() {
		return tgtState;
	}

}
