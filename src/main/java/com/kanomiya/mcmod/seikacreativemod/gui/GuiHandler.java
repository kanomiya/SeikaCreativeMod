package com.kanomiya.mcmod.seikacreativemod.gui;

import com.kanomiya.mcmod.seikacreativemod.tileentity.TileEntityEditMachine;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {
	public static final int GUIID_EDITMACHINE = 0;

	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		if (id == GUIID_EDITMACHINE) {
			TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));
			if (tileEntity instanceof TileEntityEditMachine) {
				return new ContainerEditMachine(player.inventory, (TileEntityEditMachine) tileEntity);
			}
		}

		return null;
	}


	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		if (id == GUIID_EDITMACHINE) {
			TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));
			if (tileEntity instanceof TileEntityEditMachine) {
				return new GuiEditMachine(player.inventory, (TileEntityEditMachine) tileEntity);
			}
		}

		return null;
	}

}
