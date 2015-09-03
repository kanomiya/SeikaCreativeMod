package com.kanomiya.mcmod.seikacreativemod.render;

import com.kanomiya.mcmod.seikacreativemod.SeikaCreativeMod;
import com.kanomiya.mcmod.seikacreativemod.model.ModelSandBag;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderSandBag extends RenderLiving {
	private static final ResourceLocation textureLocation = new ResourceLocation(SeikaCreativeMod.MODID + ":textures/entity/entitySandBag.png");

	public RenderSandBag(RenderManager manager) {
		super(manager, new ModelSandBag(), 0.5f);
	}


	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return textureLocation;
	}



}

