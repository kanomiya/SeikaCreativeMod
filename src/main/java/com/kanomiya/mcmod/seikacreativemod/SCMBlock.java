package com.kanomiya.mcmod.seikacreativemod;

import com.kanomiya.mcmod.seikacreativemod.block.BlockBedrockButton;
import com.kanomiya.mcmod.seikacreativemod.block.BlockBedrockDoor;
import com.kanomiya.mcmod.seikacreativemod.block.BlockBedrockGlass;
import com.kanomiya.mcmod.seikacreativemod.block.BlockBedrockLever;
import com.kanomiya.mcmod.seikacreativemod.block.BlockBedrockLight;
import com.kanomiya.mcmod.seikacreativemod.block.BlockEditMachine;
import com.kanomiya.mcmod.seikacreativemod.block.BlockFill;
import com.kanomiya.mcmod.seikacreativemod.block.BlockPreparation;
import com.kanomiya.mcmod.seikacreativemod.block.BlockSuperSponge;
import com.kanomiya.mcmod.seikacreativemod.tileentity.TileEntityEditMachine;
import com.kanomiya.mcmod.seikacreativemod.tileentity.TileEntityFill;
import com.kanomiya.mcmod.seikacreativemod.tileentity.TileEntityPreparation;
import com.kanomiya.mcmod.seikacreativemod.tileentity.TileEntityRSMachine;
import com.kanomiya.mcmod.seikacreativemod.tileentity.TileEntityRedstoneMachine;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public enum SCMBlock {
	// sample(new SampleBlock(), "blockSample", "blockSample0"),
	preparation(new BlockPreparation(), "blockPreparation"),
	fill(new BlockFill(), "blockFill"),
	supersponge(new BlockSuperSponge(), "blockSuperSponge"),
	bedrocklight(new BlockBedrockLight(), "blockBedrockLight"),
	bedrockglass(new BlockBedrockGlass(), "blockBedrockGlass"),
	bedrocklever(new BlockBedrockLever(), "blockBedrockLever"),
	bedrockbutton(new BlockBedrockButton(), "blockBedrockButton"),
	bedrockdoor(new BlockBedrockDoor(), "blockBedrockDoor"),

	editMachine(new BlockEditMachine(), "blockEditMachine"),
	;

	private String blockname;
	private String[] modelnames;
	private Block block;

	private SCMBlock(Block block, String blockname, String... modelnames) {
		this.block = block;
		this.blockname = blockname;

		if (modelnames != null && modelnames.length != 0) { this.modelnames = modelnames; }
		else { this.modelnames = new String[] { blockname }; }

		for (int i=0; i<this.modelnames.length; i++) {
			if (! this.modelnames[i].contains(":")) {
				this.modelnames[i] = SeikaCreativeMod.MODID + ":" + this.modelnames[i];
			}
		}

	}

	public String getBlockName() { return blockname; }
	public String[] getModelNames() { return modelnames; }
	public Block getBlock() { return block; }



	public static void registerBlocks() {
		for (SCMBlock sb: SCMBlock.values()) {
			GameRegistry.registerBlock(sb.getBlock(), sb.getBlockName());
		}
	}

	public static void registerModels() {
		ItemModelMesher mesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();

		for (SCMBlock sb: SCMBlock.values()) {
			ModelBakery.addVariantName(Item.getItemFromBlock(sb.getBlock()), sb.getModelNames());

			for (int i=0; i<sb.getModelNames().length; i++) {
				mesher.register(Item.getItemFromBlock(sb.getBlock()), i, new ModelResourceLocation(sb.getModelNames()[i], "inventory"));
			}
		}
	}

	public static void registerTileEntities() {
		GameRegistry.registerTileEntity(TileEntityRedstoneMachine.class, "tileentityRedstoneMachine");
		GameRegistry.registerTileEntity(TileEntityFill.class, "tileentityFill");
		GameRegistry.registerTileEntity(TileEntityPreparation.class, "tileentityPreparation");

		GameRegistry.registerTileEntity(TileEntityRSMachine.class, "tileentityRSMachine");
		GameRegistry.registerTileEntity(TileEntityEditMachine.class, "tileentityEditMachine");
	}
}
