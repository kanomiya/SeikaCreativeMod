package com.kanomiya.mcmod.seikacreativemod;

import static com.kanomiya.mcmod.kanomiyacore.util.KCUtils.Client.*;
import static com.kanomiya.mcmod.seikacreativemod.SeikaCreativeMod.*;

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

import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;


public class SCMBlocks {
    public static BlockPreparation blockPreparation = new BlockPreparation();
    public static BlockFill blockFill = new BlockFill();
    public static BlockSuperSponge blockSuperSponge = new BlockSuperSponge();
    public static BlockBedrockLight blockBedrockLight = new BlockBedrockLight();
    public static BlockBedrockGlass blockBedrockGlass = new BlockBedrockGlass();
    public static BlockBedrockLever blockBedrockLever = new BlockBedrockLever();
    public static BlockBedrockButton blockBedrockButton = new BlockBedrockButton();
    public static BlockBedrockDoor blockBedrockDoor = new BlockBedrockDoor();
    public static BlockEditMachine blockEditMachine = new BlockEditMachine();


    public static void preInit(FMLPreInitializationEvent event)
    {
        GameRegistry.register(blockPreparation, new ResourceLocation(MODID, "blockPreparation"));
        GameRegistry.register(blockFill, new ResourceLocation(MODID, "blockFill"));
        GameRegistry.register(blockSuperSponge, new ResourceLocation(MODID, "blockSuperSponge"));
        GameRegistry.register(blockBedrockLight, new ResourceLocation(MODID, "blockBedrockLight"));
        GameRegistry.register(blockBedrockGlass, new ResourceLocation(MODID, "blockBedrockGlass"));
        GameRegistry.register(blockBedrockLever, new ResourceLocation(MODID, "blockBedrockLever"));
        GameRegistry.register(blockBedrockButton, new ResourceLocation(MODID, "blockBedrockButton"));
        GameRegistry.register(blockBedrockDoor, new ResourceLocation(MODID, "blockBedrockDoor"));
        GameRegistry.register(blockEditMachine, new ResourceLocation(MODID, "blockEditMachine"));

        GameRegistry.register(new ItemBlock(blockPreparation), blockPreparation.getRegistryName());
        GameRegistry.register(new ItemBlock(blockFill), blockFill.getRegistryName());
        GameRegistry.register(new ItemBlock(blockSuperSponge), blockSuperSponge.getRegistryName());
        GameRegistry.register(new ItemBlock(blockBedrockLight), blockBedrockLight.getRegistryName());
        GameRegistry.register(new ItemBlock(blockBedrockGlass), blockBedrockGlass.getRegistryName());
        GameRegistry.register(new ItemBlock(blockBedrockLever), blockBedrockLever.getRegistryName());
        GameRegistry.register(new ItemBlock(blockBedrockButton), blockBedrockButton.getRegistryName());
        //GameRegistry.register(new ItemBlock(blockBedrockDoor), blockBedrockDoor.getRegistryName());
        GameRegistry.register(new ItemBlock(blockEditMachine), blockEditMachine.getRegistryName());

        // TileEntity
        GameRegistry.registerTileEntity(TileEntityFill.class, MODID+":tileFill");
        GameRegistry.registerTileEntity(TileEntityPreparation.class, MODID+":tilePreparation");

        GameRegistry.registerTileEntity(TileEntityEditMachine.class, MODID+":tileEditMachine");


        if (event.getSide().isClient())
        {
            simpleRegister.accept(Item.getItemFromBlock(blockPreparation));
            simpleRegister.accept(Item.getItemFromBlock(blockFill));
            simpleRegister.accept(Item.getItemFromBlock(blockSuperSponge));
            simpleRegister.accept(Item.getItemFromBlock(blockBedrockLight));
            simpleRegister.accept(Item.getItemFromBlock(blockBedrockGlass));
            simpleRegister.accept(Item.getItemFromBlock(blockBedrockLever));
            simpleRegister.accept(Item.getItemFromBlock(blockBedrockButton));
            //simpleRegister.accept(Item.getItemFromBlock(blockBedrockDoor));
            simpleRegister.accept(Item.getItemFromBlock(blockEditMachine));

        }

    }


}
