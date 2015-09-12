package com.kanomiya.mcmod.seikacreativemod.tileentity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;

import com.kanomiya.mcmod.core.tileentity.IRedstoneMachine;
import com.kanomiya.mcmod.core.tileentity.ITileEntityWithInventory;
import com.kanomiya.mcmod.seikacreativemod.SeikaCreativeMod;
import com.kanomiya.mcmod.seikacreativemod.gui.GuiHandler;
import com.kanomiya.mcmod.seikacreativemod.util.EditUtil;

public class TileEntityEditMachine extends ITileEntityWithInventory implements IRedstoneMachine {
	public static final int MODE_REPLACE = 0;
	public static final int MODE_FILL = 1;
	public static final int MODE_BOX = 2;

	public static final String MODESTR_REPLACE = "REPLACE";
	public static final String MODESTR_FILL = "FILL";
	public static final String MODESTR_BOX = "BOX";

	public static final int MAX_MODEINDEX = 2;


	protected int mode;
	protected String[] info;
	protected boolean canLaunch;
	protected boolean previousRedstoneState;


	public TileEntityEditMachine() {
		super();
	}

	public void launch() {
		if (! canLaunch) { return ; }
		// SeikaCreativeMod.logger.info("mode: " + mode + "\n");

		ItemStack targetStack = items[0];
		ItemStack putStack = items[1];
		ItemStack fromStack = items[2];
		ItemStack toStack = items[3];

		boolean targetFlag = EditUtil.hasBlockState(targetStack);
		boolean putFlag = EditUtil.hasBlockState(putStack);
		boolean fromFlag = EditUtil.hasPositon(fromStack);
		boolean toFlag = EditUtil.hasPositon(toStack);

		IBlockState target = EditUtil.getBlockState(targetStack);
		IBlockState put = EditUtil.getBlockState(putStack);
		TileEntity putTileEntity = EditUtil.getTileEntity(putStack);

		BlockPos from = EditUtil.getPositon(fromStack);
		BlockPos to = EditUtil.getPositon(toStack);

		switch (mode) {
		case MODE_REPLACE:
			if (fromFlag && toFlag && (targetFlag || putFlag)) {
				EditUtil.replace(worldObj, from, to, target, put, putTileEntity, false);
			}
			break;

		case MODE_FILL:
			if (fromFlag && putFlag) {
				EditUtil.fill(worldObj, from, put, putTileEntity, false);
			}
			break;


		case MODE_BOX:
			if (fromFlag && toFlag && putFlag) {
				EditUtil.box(worldObj, from, to, put, putTileEntity, false);
			}

			break;
		}


	}

	public void activate(EntityPlayer player) {
		player.openGui(SeikaCreativeMod.instance, GuiHandler.GUIID_EDITMACHINE, worldObj, pos.getX(), pos.getY(), pos.getZ());

	}


	@Override
	public void markDirty() {
		super.markDirty();

		canLaunch = true;
		updateInfo();
	}


	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*
	// NBT
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		mode = nbt.getInteger("editMode");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		nbt.setInteger("editMode", mode);
	}

	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*
	// Getter / Setter
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*

	public int getMode() { return mode; }

	public void setModeNext() {
		if (mode +1 > MAX_MODEINDEX) { mode = 0; }
		else { mode ++; }

		updateInfo();
	}

	public void setMode(int i) { mode = i; }

	public String getModeStr() {
		switch (mode) {
		case MODE_REPLACE: return MODESTR_REPLACE;
		case MODE_FILL: return MODESTR_FILL;
		case MODE_BOX: return MODESTR_BOX;
		}

		return "null";
	}

	public String[] getInfo() { return (info != null) ? info : new String[] { "null" }; }

	public void updateInfo() {
		String info = "";

		ItemStack targetStack = items[0];
		ItemStack putStack = items[1];
		ItemStack fromStack = items[2];
		ItemStack toStack = items[3];

		boolean targetFlag = EditUtil.hasBlockState(targetStack);
		boolean putFlag = EditUtil.hasBlockState(putStack);
		boolean fromFlag = EditUtil.hasPositon(fromStack);
		boolean toFlag = EditUtil.hasPositon(toStack);

		IBlockState target = EditUtil.getBlockState(targetStack);
		IBlockState put = EditUtil.getBlockState(putStack);
		TileEntity putTileEntity = EditUtil.getTileEntity(putStack);

		BlockPos from = EditUtil.getPositon(fromStack);
		BlockPos to = EditUtil.getPositon(toStack);

		boolean needTarget = true;
		boolean needPut = true;
		boolean needFrom = true;
		boolean needTo = true;

		int count = -1;
		switch (mode) {
		case MODE_REPLACE:
			if (fromFlag && toFlag && (targetFlag || putFlag)) {
				count = EditUtil.replace(worldObj, from, to, target, put, putTileEntity, true);
			}
			break;

		case MODE_FILL:
			needTarget = needTo = false;

			if (fromFlag && putFlag) {
				count = EditUtil.fill(worldObj, from, put, putTileEntity, true);
			}
			break;


		case MODE_BOX:
			needTarget = false;

			if (fromFlag && toFlag && putFlag) {
				count = EditUtil.box(worldObj, from, to, put, putTileEntity, true);
			}

			break;
		}

		info += "TGT: " + ((needTarget) ? "Y" : "N") + " ";
		info += "PUT: " + ((needPut) ? "Y" : "N") + "\n";
		info += "FROM: " + ((needFrom) ? "Y" : "N") + " ";
		info += "TO: " + ((needTo) ? "Y" : "N") + "\n";

		info += "COUNT: " + ((count != -1) ?  "About " + count : "ERROR") + "\n";

		this.info = info.split("\n");

		if (count == -1) { canLaunch = false; }

		/*
		String info = "";
		ItemStack targetStack = items[0];
		ItemStack putStack = items[1];
		ItemStack fromStack = items[2];
		ItemStack toStack = items[3];

		boolean targetFlag = EditUtil.hasBlockState(targetStack);
		boolean putFlag = EditUtil.hasBlockState(putStack);
		boolean fromFlag = EditUtil.hasPositon(fromStack);
		boolean toFlag = EditUtil.hasPositon(toStack);

		IBlockState target = EditUtil.getBlockState(targetStack);
		IBlockState put = EditUtil.getBlockState(putStack);
		TileEntity putTileEntity = EditUtil.getTileEntity(putStack);

		BlockPos from = EditUtil.getPositon(fromStack);
		BlockPos to = EditUtil.getPositon(toStack);


		int count = 0;

		switch (mode) {
		case MODE_FILL:
			targetFlag = ! targetFlag;
			toFlag = ! toFlag;

			count = EditUtil.fill(worldObj, from, put, putTileEntity, true);
			break;

		case MODE_REPLACE:
			targetFlag = true;

			count = EditUtil.replace(worldObj, from, to, target, put, putTileEntity, true);
			break;

		case MODE_BOX:
			targetFlag = ! targetFlag;

			count = EditUtil.box(worldObj, from, to, put, putTileEntity, true);
			break;
		}

		info += "TGT: " + ((targetFlag) ? "ok" : "xx") + " ";
		info += "PUT: " + ((putFlag) ? "ok" : "xx") + "\n";
		info += "FROM: " + ((fromFlag) ? "ok" : "xx") + " ";
		info += "TO: " + ((toFlag) ? "ok" : "xx") + "\n";
		info += "COUNT: About " + count + "\n";

		this.info = info.split("\n");

		*/
	}


	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*
	// IInventory
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*

	@Override
	public int getInventoryStackLimit() { return 1; }

	@Override
	public int getSizeInventory() { return 4; }

	@Override
	public boolean isItemValidForSlot(int id, ItemStack stack) {
		if (id == 0 || id == 1) { return (EditUtil.hasBlockState(stack)); }
		if (id == 2 || id == 3) { return (EditUtil.hasPositon(stack)); }

		return false;
	}

	@Override
	public String getName() {
		return (hasCustomName()) ? customName : "container.editmachine";
	}

	@Override
	public void openInventory(EntityPlayer player) {
		super.openInventory(player);

		updateInfo();
	}

	@Override
	public void closeInventory(EntityPlayer player) {
		super.closeInventory(player);
		// info = null;
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
