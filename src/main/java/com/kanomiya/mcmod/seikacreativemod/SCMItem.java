package com.kanomiya.mcmod.seikacreativemod;

import java.util.List;

import com.kanomiya.mcmod.seikacreativemod.item.ItemCommandItem;
import com.kanomiya.mcmod.seikacreativemod.item.ItemEntityKiller;
import com.kanomiya.mcmod.seikacreativemod.item.ItemEntityPicker;
import com.kanomiya.mcmod.seikacreativemod.item.ItemPipette;
import com.kanomiya.mcmod.seikacreativemod.item.ItemPosWand;
import com.kanomiya.mcmod.seikacreativemod.item.ItemWorldGen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDoor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public enum SCMItem {
	// sample(new SampleItem(), "itemSample", "itemSample0"),

	commandItem(new ItemCommandItem(), "itemCommandItem", "itemCommandItem", "minecraft:book"),
	entityKiller(new ItemEntityKiller(), "itemEntityKiller"),
	entityPicker(new ItemEntityPicker(), "itemEntityPicker"),
	worldGen(new ItemWorldGen(), "itemWorldGen"),
	pipette(new ItemPipette(), "itemPipette"),
	bedrockDoor(new ItemDoor(SCMBlock.bedrockdoor.getBlock()) {
		@Override
		@SideOnly(Side.CLIENT)
		public void addInformation(ItemStack stack, EntityPlayer player, List tooltip, boolean advanced) {
			tooltip.add("(Texture: Acacia)");
		}
	}.setUnlocalizedName("itemBedrockDoor").setCreativeTab(SeikaCreativeMod.tabSeika), "itemBedrockDoor"),

	posWand(new ItemPosWand(), "itemPosWand"),


	;

	private String itemname;
	private String[] modelnames;
	private Item item;

	private SCMItem(Item item, String itemname, String... modelnames) {
		this.item = item;
		this.itemname = itemname;

		if (modelnames != null && modelnames.length != 0) { this.modelnames = modelnames; }
		else { this.modelnames = new String[] { itemname }; }

		for (int i=0; i<this.modelnames.length; i++) {
			if (! this.modelnames[i].contains(":")) {
				this.modelnames[i] = SeikaCreativeMod.MODID + ":" + this.modelnames[i];
			}
		}

	}

	public String getItemName() { return itemname; }
	public String[] getModelNames() { return modelnames; }
	public Item getItem() { return item; }



	public static void registerItems() {
		for (SCMItem sb: SCMItem.values()) {
			GameRegistry.registerItem(sb.getItem(), sb.getItemName());
		}
	}

	public static void registerModels() {
		ItemModelMesher mesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();

		for (SCMItem sb: SCMItem.values()) {
			ModelBakery.addVariantName(sb.getItem(), sb.getModelNames());

			for (int i=0; i<sb.getModelNames().length; i++) {
				mesher.register(sb.getItem(), i, new ModelResourceLocation(sb.getModelNames()[i], "inventory"));
			}
		}
	}

}
