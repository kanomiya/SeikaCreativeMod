package com.kanomiya.mcmod.seikacreativemod.gui;

import com.kanomiya.mcmod.seikacreativemod.tileentity.TileEntityEditMachine;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;


public class ContainerEditMachine extends Container {
	TileEntityEditMachine editMachine;

	public ContainerEditMachine(InventoryPlayer invPlayer, TileEntityEditMachine editMachine) {
		this.editMachine = editMachine;

		addSlotToContainer(new Slot(editMachine, 0, 110, 16));
		addSlotToContainer(new Slot(editMachine, 1, 161, 16));
		addSlotToContainer(new Slot(editMachine, 2, 110, 48));
		addSlotToContainer(new Slot(editMachine, 3, 161, 48));
		bindPlayerInventory(invPlayer);

		editMachine.openInventory(invPlayer.player);
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return editMachine.isUseableByPlayer(player);
	}


	protected void bindPlayerInventory(InventoryPlayer inventoryPlayer) {

		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 17 + j * 18, 84 + i * 18));
			}
		}

		for (int i = 0; i < 9; ++i) {
			addSlotToContainer(new Slot(inventoryPlayer, i, 17 + i * 18, 142));
		}
	}


	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int slotNum) {
		ItemStack stack = null;
		Slot slot = (Slot) inventorySlots.get(slotNum);

		if (slot != null && slot.getHasStack()) {
			ItemStack slotStack = slot.getStack();
			stack = slotStack.copy();

			if (0 <= slotNum && slotNum <= 3) {
				// Put into player inv
				if (! mergeItemStack(slotStack, 1, inventorySlots.size(), true)) {
					return null;
				}

				slot.onSlotChange(slotStack, stack);
			} else {

				if (! mergeItemStack(slotStack, 0, 0, false)) {
					return null;
				}

				if (((Slot)inventorySlots.get(slotNum)).getHasStack() || !((Slot)inventorySlots.get(slotNum)).isItemValid(slotStack))
				{
					if (slotStack.stackSize == 0) {
						slot.putStack((ItemStack) null);
					} else {
						slot.onSlotChanged();
					}

					return null;
				}

				if (slotStack.hasTagCompound() && slotStack.stackSize == 1)
				{
					((Slot)inventorySlots.get(slotNum)).putStack(slotStack.copy());
					slotStack.stackSize = 0;
				}
				else if (slotStack.stackSize >= 1)
				{
					((Slot)inventorySlots.get(slotNum)).putStack(new ItemStack(slotStack.getItem(), 1, slotStack.getMetadata()));
					--slotStack.stackSize;
				}
			}


			if (slotStack.stackSize == 0) {
				slot.putStack((ItemStack) null);
			} else {
				slot.onSlotChanged();
			}

			if (slotStack.stackSize == stack.stackSize) {
				return null;
			}

			slot.onPickupFromSlot(par1EntityPlayer, slotStack);
		}

		return stack;
	}


	@Override
	protected boolean mergeItemStack(ItemStack par1ItemStack, int startSlot, int endSlot, boolean reverse) {
		boolean success = false;
		int thisStartSlot;

		Slot currentSlot;
		ItemStack currentStack;

		if (par1ItemStack.stackSize > 0) {
			if (reverse) {
				thisStartSlot = endSlot - 1;
			} else {
				thisStartSlot = startSlot;
			}

			while (!reverse && thisStartSlot < endSlot || reverse && thisStartSlot >= startSlot) {
				currentSlot = (Slot) inventorySlots.get(thisStartSlot);
				currentStack = currentSlot.getStack();

				if (currentStack == null) {
					// XXX: HAS TO TEST
					currentSlot.putStack(par1ItemStack.copy());
					// currentSlot.getStack().stackSize = 1;
					currentSlot.onSlotChanged();
					// par1ItemStack.stackSize -= 1;
					par1ItemStack.stackSize = 0;
					success = true;
					break;
				}

				if (reverse) {
					--thisStartSlot;
				} else {
					++thisStartSlot;
				}
			}
		}

		return success;
	}

	@Override
	public void onContainerClosed(EntityPlayer p_75134_1_) {
		super.onContainerClosed(p_75134_1_);
		editMachine.closeInventory(p_75134_1_);
	}

}