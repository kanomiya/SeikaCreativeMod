// Date: 2014/09/13 19:59:02
// Template version 1.1
// Java generated by Techne
// Keep in mind that you still need to fill in some blanks
// - ZeuX

package com.kanomiya.mcmod.seikacreativemod.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelSandBag extends ModelBase {
	// fields
	ModelRenderer Base1;
	ModelRenderer Base2;
	ModelRenderer Base3;
	ModelRenderer eye1;
	ModelRenderer eye2;
	ModelRenderer nose;
	ModelRenderer mouse;
	ModelRenderer sk1;
	ModelRenderer sk2;
	ModelRenderer arm1;
	ModelRenderer arm2;
	ModelRenderer arm1l;
	ModelRenderer arm2l;

	public ModelSandBag() {
		textureWidth = 64;
		textureHeight = 64;

		Base1 = new ModelRenderer(this, 0, 0);
		Base1.addBox(0F, 0F, 0F, 7, 18, 6);
		Base1.setRotationPoint(-4F, -6F, -3.966667F);
		Base1.setTextureSize(64, 64);
		Base1.mirror = true;
		setRotation(Base1, 0F, 0F, 0F);
		Base2 = new ModelRenderer(this, 0, 30);
		Base2.addBox(0F, 0F, 0F, 9, 2, 8);
		Base2.setRotationPoint(-5F, 13F, -4.966667F);
		Base2.setTextureSize(64, 64);
		Base2.mirror = true;
		setRotation(Base2, 0F, 0F, 0F);
		Base3 = new ModelRenderer(this, 0, 40);
		Base3.addBox(0F, 0F, 0F, 9, 10, 8);
		Base3.setRotationPoint(-5F, 16F, -5F);
		Base3.setTextureSize(64, 64);
		Base3.mirror = true;
		setRotation(Base3, 0F, 0F, 0F);
		eye1 = new ModelRenderer(this, 0, 0);
		eye1.addBox(0F, 0F, 0F, 1, 1, 0);
		eye1.setRotationPoint(1F, -3F, -3.966667F);
		eye1.setTextureSize(64, 64);
		eye1.mirror = true;
		setRotation(eye1, 0F, 0F, 0F);
		eye2 = new ModelRenderer(this, 0, 0);
		eye2.addBox(0F, 0F, 0F, 1, 1, 0);
		eye2.setRotationPoint(-3F, -3F, -4F);
		eye2.setTextureSize(64, 64);
		eye2.mirror = true;
		setRotation(eye2, 0F, 0F, 0F);
		nose = new ModelRenderer(this, 2, 0);
		nose.addBox(0F, 0F, 0F, 1, 5, 1);
		nose.setRotationPoint(-1F, -3F, -4.966667F);
		nose.setTextureSize(64, 64);
		nose.mirror = true;
		setRotation(nose, 0F, 0F, -0.0174533F);
		mouse = new ModelRenderer(this, 1, 0);
		mouse.addBox(0F, 0F, 0F, 1, 1, 0);
		mouse.setRotationPoint(-1F, 3F, -3.966667F);
		mouse.setTextureSize(64, 64);
		mouse.mirror = true;
		setRotation(mouse, 0F, 0F, 0F);
		sk1 = new ModelRenderer(this, 0, 19);
		sk1.addBox(0F, 0F, 0F, 11, 1, 10);
		sk1.setRotationPoint(-6F, 12F, -5.966667F);
		sk1.setTextureSize(64, 64);
		sk1.mirror = true;
		setRotation(sk1, 0F, 0F, 0F);
		sk2 = new ModelRenderer(this, 0, 19);
		sk2.addBox(0F, 0F, 0F, 11, 1, 10);
		sk2.setRotationPoint(-6F, 15F, -6F);
		sk2.setTextureSize(64, 64);
		sk2.mirror = true;
		setRotation(sk2, 0F, 0F, 0F);
		arm1 = new ModelRenderer(this, 20, 0);
		arm1.addBox(0F, 0F, 0F, 2, 1, 1);
		arm1.setRotationPoint(3F, 4F, -1F);
		arm1.setTextureSize(64, 64);
		arm1.mirror = true;
		setRotation(arm1, 0F, 0F, 0F);
		arm2 = new ModelRenderer(this, 20, 0);
		arm2.addBox(0F, 0F, 0F, 2, 1, 1);
		arm2.setRotationPoint(-6F, 4F, -1F);
		arm2.setTextureSize(64, 64);
		arm2.mirror = true;
		setRotation(arm2, 0F, 0F, 0F);
		arm1l = new ModelRenderer(this, 0, 19);
		arm1l.addBox(0F, 0F, 0F, 1, 4, 1);
		arm1l.setRotationPoint(-7F, 4F, -1F);
		arm1l.setTextureSize(64, 64);
		arm1l.mirror = true;
		setRotation(arm1l, 0F, 0F, 0F);
		arm2l = new ModelRenderer(this, 0, 19);
		arm2l.addBox(0F, 0F, 0F, 1, 5, 1);
		arm2l.setRotationPoint(5F, 0F, -1F);
		arm2l.setTextureSize(64, 64);
		arm2l.mirror = true;
		setRotation(arm2l, 0F, 0F, 0F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3,
			float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		Base1.render(f5);
		Base2.render(f5);
		Base3.render(f5);
		eye1.render(f5);
		eye2.render(f5);
		nose.render(f5);
		mouse.render(f5);
		sk1.render(f5);
		sk2.render(f5);
		arm1.render(f5);
		arm2.render(f5);
		arm1l.render(f5);
		arm2l.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3,
			float f4, float f5, Entity entity) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
	}

}
