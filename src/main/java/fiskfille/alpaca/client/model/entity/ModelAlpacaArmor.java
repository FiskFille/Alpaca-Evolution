package fiskfille.alpaca.client.model.entity;

import net.minecraft.client.model.ModelBase;

import org.lwjgl.opengl.GL11;

import fiskfille.alpaca.client.model.tools.FiskModelRenderer;

public class ModelAlpacaArmor extends ModelBase
{
    public FiskModelRenderer armorLeg1;
    public FiskModelRenderer armorLeg2;
    public FiskModelRenderer armorLeg3;
    public FiskModelRenderer armorLeg4;
    public FiskModelRenderer armorBody;
    public FiskModelRenderer armorHead;
    public FiskModelRenderer armorBreastplate;

    public ModelAlpacaArmor()
    {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.armorLeg2 = new FiskModelRenderer(this, 0, 16);
        this.armorLeg2.setRotationPoint(2.2F, 15.0F, -4.0F);
        this.armorLeg2.addBox(-2.0F, -0.7F, -2.0F, 4, 12, 4, 0.0F);
        this.armorLeg3 = new FiskModelRenderer(this, 0, 16);
        this.armorLeg3.setRotationPoint(-2.2F, 15.0F, 4.0F);
        this.armorLeg3.addBox(-2.0F, -0.7F, -2.0F, 4, 12, 4, 0.0F);
        this.armorHead = new FiskModelRenderer(this, 0, 0);
        this.armorHead.setRotationPoint(0.0F, 10.5F, -5.0F);
        this.armorHead.addBox(-4.0F, -13.0F, -4.0F, 8, 8, 8, 0.0F);
        this.armorLeg4 = new FiskModelRenderer(this, 0, 16);
        this.armorLeg4.setRotationPoint(2.2F, 15.0F, 4.0F);
        this.armorLeg4.addBox(-2.0F, -0.7F, -2.0F, 4, 12, 4, 0.0F);
        this.armorLeg1 = new FiskModelRenderer(this, 0, 16);
        this.armorLeg1.setRotationPoint(-2.2F, 15.0F, -4.0F);
        this.armorLeg1.addBox(-2.0F, -0.7F, -2.0F, 4, 12, 4, 0.0F);
        this.armorBody = new FiskModelRenderer(this, 16, 16);
        this.armorBody.setRotationPoint(0.0F, 16.0F, 0.0F);
        this.armorBody.addBox(-4.0F, -6.0F, 0.98F, 8, 12, 4, 0.0F);
        this.setRotateAngle(armorBody, 1.5707963267948966F, 0.0F, 0.0F);
        this.armorBreastplate = new FiskModelRenderer(this, 20, 20);
        this.armorBreastplate.setRotationPoint(0.0F, 12.5F, -6.6F);
        this.armorBreastplate.addBox(-4.0F, -6.0F, 0.0F, 8, 12, 0, 0.01F);
    }

    public void renderHelmet()
    {
        float scale = 0.75F;
        armorHead.setScale(scale, scale, scale);
        armorHead.render(0.0625F);
    }

    public void renderChestplate()
    {
        GL11.glPushMatrix();
        GL11.glTranslatef(armorBreastplate.offsetX, armorBreastplate.offsetY, armorBreastplate.offsetZ);
        GL11.glTranslatef(armorBreastplate.rotationPointX * 0.0625F, armorBreastplate.rotationPointY * 0.0625F, armorBreastplate.rotationPointZ * 0.0625F);
        GL11.glScaled(1.0D, 0.65D, 1.0D);
        GL11.glTranslatef(-armorBreastplate.offsetX, -armorBreastplate.offsetY, -armorBreastplate.offsetZ);
        GL11.glTranslatef(-armorBreastplate.rotationPointX * 0.0625F, -armorBreastplate.rotationPointY * 0.0625F, -armorBreastplate.rotationPointZ * 0.0625F);
        armorBreastplate.render(0.0625F);
        GL11.glPopMatrix();

        GL11.glPushMatrix();
        GL11.glTranslatef(armorBody.offsetX, armorBody.offsetY, armorBody.offsetZ);
        GL11.glTranslatef(armorBody.rotationPointX * 0.0625F, armorBody.rotationPointY * 0.0625F, armorBody.rotationPointZ * 0.0625F);
        GL11.glScaled(1.0D, 1.5D, 1.1D);
        GL11.glTranslatef(-armorBody.offsetX, -armorBody.offsetY, -armorBody.offsetZ);
        GL11.glTranslatef(-armorBody.rotationPointX * 0.0625F, -armorBody.rotationPointY * 0.0625F, -armorBody.rotationPointZ * 0.0625F);
        armorBody.render(0.0625F);
        GL11.glPopMatrix();
    }

    public void renderLegs(float scale)
    {
        armorLeg1.setScale(scale, 0.8F, scale);
        armorLeg2.setScale(scale, 0.8F, scale);
        armorLeg3.setScale(scale, 0.8F, scale);
        armorLeg4.setScale(scale, 0.8F, scale);
        armorLeg1.render(0.0625F);
        armorLeg2.render(0.0625F);
        armorLeg3.render(0.0625F);
        armorLeg4.render(0.0625F);
    }

    public void setRotateAngle(FiskModelRenderer FiskModelRenderer, float x, float y, float z)
    {
        FiskModelRenderer.rotateAngleX = x;
        FiskModelRenderer.rotateAngleY = y;
        FiskModelRenderer.rotateAngleZ = z;
    }

    public void syncArmorAngles(ModelAlpaca model)
    {
        FiskModelRenderer[] aFiskModelRenderer = { model.frontUpperLeg1, model.frontUpperLeg2, model.backUpperLeg1, model.backUpperLeg2, model.body, model.neck };
        FiskModelRenderer[] aFiskModelRenderer1 = { armorLeg1, armorLeg2, armorLeg3, armorLeg4, armorBody, armorHead };

        for (int i = 0; i < aFiskModelRenderer.length; ++i)
        {
            aFiskModelRenderer1[i].rotateAngleX = aFiskModelRenderer[i].rotateAngleX;
            aFiskModelRenderer1[i].rotateAngleY = aFiskModelRenderer[i].rotateAngleY;
            aFiskModelRenderer1[i].rotateAngleZ = aFiskModelRenderer[i].rotateAngleZ;
            aFiskModelRenderer1[i].rotationPointX = aFiskModelRenderer[i].getRotationPointX();
            aFiskModelRenderer1[i].rotationPointY = aFiskModelRenderer[i].getRotationPointY();
            aFiskModelRenderer1[i].rotationPointZ = aFiskModelRenderer[i].getRotationPointZ();
        }
    }
}