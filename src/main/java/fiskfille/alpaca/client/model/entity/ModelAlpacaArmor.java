package fiskfille.alpaca.client.model.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

import org.lwjgl.opengl.GL11;

public class ModelAlpacaArmor extends ModelBase
{
	public ModelRenderer armorLeg1;
	public ModelRenderer armorLeg2;
	public ModelRenderer armorLeg3;
	public ModelRenderer armorLeg4;
	public ModelRenderer armorBody;
	public ModelRenderer armorHead;
	public ModelRenderer armorBreastplate;

	public ModelAlpacaArmor()
	{
		this.textureWidth = 64;
		this.textureHeight = 32;
		this.armorLeg2 = new ModelRenderer(this, 0, 16);
		this.armorLeg2.setRotationPoint(2.2F, 15.0F, -4.0F);
		this.armorLeg2.addBox(-2.0F, -0.7F, -2.0F, 4, 12, 4, 0.0F);
		this.armorLeg3 = new ModelRenderer(this, 0, 16);
		this.armorLeg3.setRotationPoint(-2.2F, 15.0F, 4.0F);
		this.armorLeg3.addBox(-2.0F, -0.7F, -2.0F, 4, 12, 4, 0.0F);
		this.armorHead = new ModelRenderer(this, 0, 0);
		this.armorHead.setRotationPoint(0.0F, 10.5F, -5.0F);
		this.armorHead.addBox(-4.0F, -13.0F, -4.0F, 8, 8, 8, 0.0F);
		this.armorLeg4 = new ModelRenderer(this, 0, 16);
		this.armorLeg4.setRotationPoint(2.2F, 15.0F, 4.0F);
		this.armorLeg4.addBox(-2.0F, -0.7F, -2.0F, 4, 12, 4, 0.0F);
		this.armorLeg1 = new ModelRenderer(this, 0, 16);
		this.armorLeg1.setRotationPoint(-2.2F, 15.0F, -4.0F);
		this.armorLeg1.addBox(-2.0F, -0.7F, -2.0F, 4, 12, 4, 0.0F);
		this.armorBody = new ModelRenderer(this, 16, 16);
        this.armorBody.setRotationPoint(0.0F, 12.5F, 6.0F);
        this.armorBody.addBox(-4.0F, -11.5F, -1.3F, 8, 12, 4, 0.0F);
        this.setRotateAngle(armorBody, 1.5707963267948966F, 0.0F, 0.0F);
        this.armorBreastplate = new ModelRenderer(this, 20, 20);
        this.armorBreastplate.setRotationPoint(0.0F, 12.5F, -6.7F);
        this.armorBreastplate.addBox(-4.0F, -6.0F, 0.0F, 8, 12, 0, 0.1F);
	}
	
	public void renderHelmet(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		double scale = 0.75F;
		GL11.glPushMatrix();
		GL11.glTranslatef(this.armorHead.offsetX, this.armorHead.offsetY, this.armorHead.offsetZ);
		GL11.glTranslatef(this.armorHead.rotationPointX * f5, this.armorHead.rotationPointY * f5, this.armorHead.rotationPointZ * f5);
		GL11.glScaled(scale, scale, scale);
		GL11.glTranslatef(-this.armorHead.offsetX, -this.armorHead.offsetY, -this.armorHead.offsetZ);
		GL11.glTranslatef(-this.armorHead.rotationPointX * f5, -this.armorHead.rotationPointY * f5, -this.armorHead.rotationPointZ * f5);
		this.armorHead.render(f5);
		GL11.glPopMatrix();
	}
	
	public void renderChestplate(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		GL11.glPushMatrix();
        GL11.glTranslatef(this.armorBody.offsetX, this.armorBody.offsetY, this.armorBody.offsetZ);
        GL11.glTranslatef(this.armorBody.rotationPointX * f5, this.armorBody.rotationPointY * f5, this.armorBody.rotationPointZ * f5);
        GL11.glScaled(1.0D, 1.5D, 1.1D);
        GL11.glTranslatef(-this.armorBody.offsetX, -this.armorBody.offsetY, -this.armorBody.offsetZ);
        GL11.glTranslatef(-this.armorBody.rotationPointX * f5, -this.armorBody.rotationPointY * f5, -this.armorBody.rotationPointZ * f5);
        this.armorBody.render(f5);
        GL11.glPopMatrix();
		
		GL11.glPushMatrix();
        GL11.glTranslatef(this.armorBreastplate.offsetX, this.armorBreastplate.offsetY, this.armorBreastplate.offsetZ);
        GL11.glTranslatef(this.armorBreastplate.rotationPointX * f5, this.armorBreastplate.rotationPointY * f5, this.armorBreastplate.rotationPointZ * f5);
        GL11.glScaled(1.0D, 0.65D, 1.0D);
        GL11.glTranslatef(-this.armorBreastplate.offsetX, -this.armorBreastplate.offsetY, -this.armorBreastplate.offsetZ);
        GL11.glTranslatef(-this.armorBreastplate.rotationPointX * f5, -this.armorBreastplate.rotationPointY * f5, -this.armorBreastplate.rotationPointZ * f5);
        this.armorBreastplate.render(f5);
        GL11.glPopMatrix();
	}
	
	public void renderLegs(Entity entity, float f, float f1, float f2, float f3, float f4, float f5, double scale)
	{
		GL11.glPushMatrix();
		GL11.glTranslatef(this.armorLeg1.offsetX, this.armorLeg1.offsetY, this.armorLeg1.offsetZ);
		GL11.glTranslatef(this.armorLeg1.rotationPointX * f5, this.armorLeg1.rotationPointY * f5, this.armorLeg1.rotationPointZ * f5);
		GL11.glScaled(scale, 0.8D, scale);
		GL11.glTranslatef(-this.armorLeg1.offsetX, -this.armorLeg1.offsetY, -this.armorLeg1.offsetZ);
		GL11.glTranslatef(-this.armorLeg1.rotationPointX * f5, -this.armorLeg1.rotationPointY * f5, -this.armorLeg1.rotationPointZ * f5);
		this.armorLeg1.render(f5);
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		GL11.glTranslatef(this.armorLeg2.offsetX, this.armorLeg2.offsetY, this.armorLeg2.offsetZ);
		GL11.glTranslatef(this.armorLeg2.rotationPointX * f5, this.armorLeg2.rotationPointY * f5, this.armorLeg2.rotationPointZ * f5);
		GL11.glScaled(scale, 0.8D, scale);
		GL11.glTranslatef(-this.armorLeg2.offsetX, -this.armorLeg2.offsetY, -this.armorLeg2.offsetZ);
		GL11.glTranslatef(-this.armorLeg2.rotationPointX * f5, -this.armorLeg2.rotationPointY * f5, -this.armorLeg2.rotationPointZ * f5);
		this.armorLeg2.render(f5);
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		GL11.glTranslatef(this.armorLeg3.offsetX, this.armorLeg3.offsetY, this.armorLeg3.offsetZ);
		GL11.glTranslatef(this.armorLeg3.rotationPointX * f5, this.armorLeg3.rotationPointY * f5, this.armorLeg3.rotationPointZ * f5);
		GL11.glScaled(scale, 0.8D, scale);
		GL11.glTranslatef(-this.armorLeg3.offsetX, -this.armorLeg3.offsetY, -this.armorLeg3.offsetZ);
		GL11.glTranslatef(-this.armorLeg3.rotationPointX * f5, -this.armorLeg3.rotationPointY * f5, -this.armorLeg3.rotationPointZ * f5);
		this.armorLeg3.render(f5);
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		GL11.glTranslatef(this.armorLeg4.offsetX, this.armorLeg4.offsetY, this.armorLeg4.offsetZ);
		GL11.glTranslatef(this.armorLeg4.rotationPointX * f5, this.armorLeg4.rotationPointY * f5, this.armorLeg4.rotationPointZ * f5);
		GL11.glScaled(scale, 0.8D, scale);
		GL11.glTranslatef(-this.armorLeg4.offsetX, -this.armorLeg4.offsetY, -this.armorLeg4.offsetZ);
		GL11.glTranslatef(-this.armorLeg4.rotationPointX * f5, -this.armorLeg4.rotationPointY * f5, -this.armorLeg4.rotationPointZ * f5);
		this.armorLeg4.render(f5);
		GL11.glPopMatrix();
	}

	public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z)
	{
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
	
	public void syncArmorAngles(ModelAlpaca model)
    {
		ModelRenderer[] amodelrenderer = {model.frontUpperLeg1, model.frontUpperLeg2, model.backUpperLeg1, model.backUpperLeg2, model.body, model.neck};
		ModelRenderer[] amodelrenderer1 = {armorLeg1, armorLeg2, armorLeg3, armorLeg4, armorBody, armorHead};
		
		for (int i = 0; i < amodelrenderer.length; ++i)
		{
			amodelrenderer1[i].rotateAngleX = amodelrenderer[i].rotateAngleX;
			amodelrenderer1[i].rotateAngleY = amodelrenderer[i].rotateAngleY;
			amodelrenderer1[i].rotateAngleZ = amodelrenderer[i].rotateAngleZ;
		}
    }
}