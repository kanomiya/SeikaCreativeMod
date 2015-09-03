package com.kanomiya.mcmod.seikacreativemod.tileentity;

import net.minecraft.tileentity.TileEntity;

public abstract class TileEntityRSMachine extends TileEntity {
	private boolean previousRedstoneState;

	public boolean isPrevPowered() {
		return previousRedstoneState;
	}

	public void setPrevRSState(boolean bool) {
		previousRedstoneState = bool;
	}

}
