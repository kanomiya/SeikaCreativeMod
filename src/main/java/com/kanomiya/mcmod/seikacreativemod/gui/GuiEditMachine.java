package com.kanomiya.mcmod.seikacreativemod.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.lwjgl.opengl.GL11;

import com.kanomiya.mcmod.seikacreativemod.network.MessageEditMachineMode;
import com.kanomiya.mcmod.seikacreativemod.network.PacketHandler;
import com.kanomiya.mcmod.seikacreativemod.tileentity.TileEntityEditMachine;


@SideOnly(Side.CLIENT)
public class GuiEditMachine extends GuiContainer {
	private static ResourceLocation background = new ResourceLocation("seikacreativemod:textures/gui/guiEditMachine.png");
	private static String SELECTER_STR = "MODE: ";

	protected GuiButton modeSelecter;
	protected EntityPlayer player;
	protected TileEntityEditMachine editMachine;
	// GuiContainer


	public GuiEditMachine(InventoryPlayer invPlayer, TileEntityEditMachine editMachine) {
		super(new ContainerEditMachine(invPlayer, editMachine));

		this.editMachine = editMachine;
		player = invPlayer.player;

		xSize = 196;
		ySize = 166;
	}

	@Override
	public void initGui() {
		super.initGui();

		mc.thePlayer.openContainer = inventorySlots;
		buttonList.clear();

		modeSelecter = new GuiButton(0, 125, 52, 90, 20, "-");
		modeSelecter.displayString = SELECTER_STR + editMachine.getModeStr();


		buttonList.add(modeSelecter);
	}


	@Override
	public void actionPerformed(GuiButton button) {
		// isShiftKeyDown()

		switch (button.id) {
		case 0:
			editMachine.setModeNext();
			modeSelecter.displayString = SELECTER_STR + editMachine.getModeStr();

			PacketHandler.INSTANCE.sendToServer(new MessageEditMachineMode(editMachine.getPos(), editMachine.getMode()));
			break;

		case 1:
			editMachine.launch();
			break;

		default:
			break;
		}
	}



	/**
	 * Draw the foreground layer for the GuiContainer (everything in front of the items)
	 */
	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		fontRendererObj.drawString("EditMachine", 8, 6, 4210752);
		fontRendererObj.drawString(I18n.format("container.inventory"), 16, ySize - 94, 4210752);

		fontRendererObj.drawString("Target", 105, 6, 4210752);
		fontRendererObj.drawString("Put", 155, 6, 4210752);
		fontRendererObj.drawString("From", 105, 38, 4210752);
		fontRendererObj.drawString("To", 155, 38, 4210752);

		String[] info = editMachine.getInfo();
		for (int i=0; i<info.length; i++) {
			fontRendererObj.drawString(info[i], 10, 38 +10*i, 4210752);
		}
	}

	/**
	 * Draw the background layer for the GuiContainer (everything behind the items)
	 */
	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(background);
		int k = (width - xSize) / 2;
		int l = (height - ySize) / 2;
		this.drawTexturedModalRect(k, l, 0, 0, xSize, ySize);
	}
}

