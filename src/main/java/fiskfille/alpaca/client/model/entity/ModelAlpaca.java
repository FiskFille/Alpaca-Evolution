package fiskfille.alpaca.client.model.entity;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import fiskfille.alpaca.client.model.tools.FiskModelRenderer;

public class ModelAlpaca extends ModelAlpacaBase
{
    public FiskModelRenderer body;
    public FiskModelRenderer tail;
    public FiskModelRenderer frontUpperLeg1;
    public FiskModelRenderer frontUpperLeg2;
    public FiskModelRenderer backUpperLeg1;
    public FiskModelRenderer backUpperLeg2;
    public FiskModelRenderer neck;
    public FiskModelRenderer frontLowerLeg1;
    public FiskModelRenderer frontFoot1;
    public FiskModelRenderer frontLowerLeg2;
    public FiskModelRenderer frontFoot2;
    public FiskModelRenderer backLowerLeg1;
    public FiskModelRenderer backFoot1;
    public FiskModelRenderer backLowerLeg2;
    public FiskModelRenderer backFoot2;
    public FiskModelRenderer head;
    public FiskModelRenderer eye1;
    public FiskModelRenderer eye2;
    public FiskModelRenderer nose1;
    public FiskModelRenderer ear2;
    public FiskModelRenderer ear1;
    public FiskModelRenderer nose2;

    public ModelAlpaca()
    {
        textureWidth = 64;
        textureHeight = 32;
        neck = new FiskModelRenderer(this, 0, 19);
        neck.setRotationPoint(0.0F, -5.5F, -5.0F);
        neck.addBox(-2.0F, -8.0F, -1.5F, 4, 8, 3, 0.0F);
        frontFoot2 = new FiskModelRenderer(this, 26, 19);
        frontFoot2.mirror = true;
        frontFoot2.setRotationPoint(0.0F, 3.0F, 0.0F);
        frontFoot2.addBox(-1.0F, 0.0F, -2.0F, 2, 1, 3, 0.0F);
        backLowerLeg1 = new FiskModelRenderer(this, 14, 27);
        backLowerLeg1.setRotationPoint(0.0F, 5.0F, 0.0F);
        backLowerLeg1.addBox(-1.0F, 0.0F, -1.0F, 2, 3, 2, 0.0F);
        nose2 = new FiskModelRenderer(this, 5, 10);
        nose2.setRotationPoint(0.0F, -0.2F, -0.3F);
        nose2.addBox(-0.5F, -0.5F, -1.0F, 1, 1, 1, 0.0F);
        frontLowerLeg1 = new FiskModelRenderer(this, 14, 27);
        frontLowerLeg1.setRotationPoint(0.0F, 5.0F, 0.0F);
        frontLowerLeg1.addBox(-1.0F, 0.0F, -1.0F, 2, 3, 2, 0.0F);
        backFoot1 = new FiskModelRenderer(this, 26, 19);
        backFoot1.setRotationPoint(0.0F, 3.0F, 0.0F);
        backFoot1.addBox(-1.0F, 0.0F, -2.0F, 2, 1, 3, 0.0F);
        backLowerLeg2 = new FiskModelRenderer(this, 14, 27);
        backLowerLeg2.mirror = true;
        backLowerLeg2.setRotationPoint(0.0F, 5.0F, 0.0F);
        backLowerLeg2.addBox(-1.0F, 0.0F, -1.0F, 2, 3, 2, 0.0F);
        backFoot2 = new FiskModelRenderer(this, 26, 19);
        backFoot2.mirror = true;
        backFoot2.setRotationPoint(0.0F, 3.0F, 0.0F);
        backFoot2.addBox(-1.0F, 0.0F, -2.0F, 2, 1, 3, 0.0F);
        ear2 = new FiskModelRenderer(this, 6, 6);
        ear2.mirror = true;
        ear2.setRotationPoint(1.0F, -1.1F, 0.0F);
        ear2.addBox(0.0F, -0.5F, -0.5F, 2, 1, 1, 0.0F);
        setRotateAngle(ear2, 0.0F, 0.0F, -0.7853981633974483F);
        head = new FiskModelRenderer(this, 0, 0);
        head.setRotationPoint(0.0F, -6.75F, 1.0F);
        head.addBox(-1.5F, -1.5F, -3.0F, 3, 3, 3, 0.0F);
        backUpperLeg1 = new FiskModelRenderer(this, 14, 19);
        backUpperLeg1.setRotationPoint(-2.2F, -1.0F, 4.0F);
        backUpperLeg1.addBox(-1.5F, 0.0F, -1.5F, 3, 5, 3, 0.0F);
        frontFoot1 = new FiskModelRenderer(this, 26, 19);
        frontFoot1.setRotationPoint(0.0F, 3.0F, 0.0F);
        frontFoot1.addBox(-1.0F, 0.0F, -2.0F, 2, 1, 3, 0.0F);
        tail = new FiskModelRenderer(this, 26, 23);
        tail.setRotationPoint(0.0F, -6.6F, 6.0F);
        tail.addBox(-1.5F, -0.5F, 0.0F, 3, 1, 2, 0.0F);
        setRotateAngle(tail, 0.4363323129985824F, 0.0F, 0.0F);
        eye1 = new FiskModelRenderer(this, 0, 6);
        eye1.setRotationPoint(1.2F, -0.2F, -2.3F);
        eye1.addBox(0.0F, -0.5F, -0.5F, 1, 1, 1, 0.0F);
        frontLowerLeg2 = new FiskModelRenderer(this, 14, 27);
        frontLowerLeg2.mirror = true;
        frontLowerLeg2.setRotationPoint(0.0F, 5.0F, 0.0F);
        frontLowerLeg2.addBox(-1.0F, 0.0F, -1.0F, 2, 3, 2, 0.0F);
        nose1 = new FiskModelRenderer(this, 8, 8);
        nose1.setRotationPoint(0.0F, 0.4F, -2.4F);
        nose1.addBox(-0.5F, -1.0F, -1.0F, 1, 2, 1, 0.0F);
        eye2 = new FiskModelRenderer(this, 0, 6);
        eye2.setRotationPoint(-1.2F, -0.2F, -2.3F);
        eye2.addBox(-1.0F, -0.5F, -0.5F, 1, 1, 1, 0.0F);
        ear1 = new FiskModelRenderer(this, 6, 6);
        ear1.setRotationPoint(-1.0F, -1.1F, 0.0F);
        ear1.addBox(-2.0F, -0.5F, -0.5F, 2, 1, 1, 0.0F);
        setRotateAngle(ear1, 0.0F, 0.0F, 0.7853981633974483F);
        frontUpperLeg2 = new FiskModelRenderer(this, 14, 19);
        frontUpperLeg2.mirror = true;
        frontUpperLeg2.setRotationPoint(2.2F, -1.0F, -4.0F);
        frontUpperLeg2.addBox(-1.5F, 0.0F, -1.5F, 3, 5, 3, 0.0F);
        backUpperLeg2 = new FiskModelRenderer(this, 14, 19);
        backUpperLeg2.mirror = true;
        backUpperLeg2.setRotationPoint(2.2F, -1.0F, 4.0F);
        backUpperLeg2.addBox(-1.5F, 0.0F, -1.5F, 3, 5, 3, 0.0F);
        body = new FiskModelRenderer(this, 0, 0);
        body.setRotationPoint(0.0F, 16.0F, 0.0F);
        body.addBox(-3.5F, -7.0F, -6.0F, 7, 7, 12, 0.0F);
        frontUpperLeg1 = new FiskModelRenderer(this, 14, 19);
        frontUpperLeg1.setRotationPoint(-2.2F, -1.0F, -4.0F);
        frontUpperLeg1.addBox(-1.5F, 0.0F, -1.5F, 3, 5, 3, 0.0F);
        body.addChild(neck);
        frontLowerLeg2.addChild(frontFoot2);
        backUpperLeg1.addChild(backLowerLeg1);
        nose1.addChild(nose2);
        frontUpperLeg1.addChild(frontLowerLeg1);
        backLowerLeg1.addChild(backFoot1);
        backUpperLeg2.addChild(backLowerLeg2);
        backLowerLeg2.addChild(backFoot2);
        head.addChild(ear2);
        neck.addChild(head);
        body.addChild(backUpperLeg1);
        frontLowerLeg1.addChild(frontFoot1);
        body.addChild(tail);
        head.addChild(eye1);
        frontUpperLeg2.addChild(frontLowerLeg2);
        head.addChild(nose1);
        head.addChild(eye2);
        head.addChild(ear1);
        body.addChild(frontUpperLeg2);
        body.addChild(backUpperLeg2);
        body.addChild(frontUpperLeg1);
    }
    
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
    	setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        body.render(f5);
    }
    
    public void setRotateAngle(FiskModelRenderer FiskModelRenderer, float x, float y, float z)
    {
        FiskModelRenderer.rotateAngleX = x;
        FiskModelRenderer.rotateAngleY = y;
        FiskModelRenderer.rotateAngleZ = z;
    }
    
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
    {
        neck.setRotationPoint(0.0F, -5.5F, -5.0F);
        frontFoot2.setRotationPoint(0.0F, 3.0F, 0.0F);
        backLowerLeg1.setRotationPoint(0.0F, 5.0F, 0.0F);
        nose2.setRotationPoint(0.0F, -0.2F, -0.3F);
        frontLowerLeg1.setRotationPoint(0.0F, 5.0F, 0.0F);
        backFoot1.setRotationPoint(0.0F, 3.0F, 0.0F);
        backLowerLeg2.setRotationPoint(0.0F, 5.0F, 0.0F);
        backFoot2.setRotationPoint(0.0F, 3.0F, 0.0F);
        ear2.setRotationPoint(1.0F, -1.1F, 0.0F);
        setRotateAngle(ear2, 0.0F, 0.0F, -0.7853981633974483F);
        head.setRotationPoint(0.0F, -6.75F, 1.0F);
        backUpperLeg1.setRotationPoint(-2.2F, -1.0F, 4.0F);
        frontFoot1.setRotationPoint(0.0F, 3.0F, 0.0F);
        tail.setRotationPoint(0.0F, -6.6F, 6.0F);
        setRotateAngle(tail, 0.4363323129985824F, 0.0F, 0.0F);
        eye1.setRotationPoint(1.2F, -0.2F, -2.3F);
        frontLowerLeg2.setRotationPoint(0.0F, 5.0F, 0.0F);
        nose1.setRotationPoint(0.0F, 0.4F, -2.4F);
        eye2.setRotationPoint(-1.2F, -0.2F, -2.3F);
        ear1.setRotationPoint(-1.0F, -1.1F, 0.0F);
        setRotateAngle(ear1, 0.0F, 0.0F, 0.7853981633974483F);
        frontUpperLeg2.setRotationPoint(2.2F, -1.0F, -4.0F);
        backUpperLeg2.setRotationPoint(2.2F, -1.0F, 4.0F);
        body.setRotationPoint(0.0F, 16.0F, 0.0F);
        frontUpperLeg1.setRotationPoint(-2.2F, -1.0F, -4.0F);
    	
    	float f6 = (180F / (float)Math.PI);
        neck.rotateAngleX = f4 / (180F / (float)Math.PI);
        neck.rotateAngleY = f3 / (180F / (float)Math.PI);
        body.rotateAngleX = ((float)Math.PI / 2F);
        frontUpperLeg1.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
        frontUpperLeg2.rotateAngleX = MathHelper.cos(f * 0.6662F + (float)Math.PI) * 1.4F * f1;
        backUpperLeg1.rotateAngleX = MathHelper.cos(f * 0.6662F + (float)Math.PI) * 1.4F * f1;
        backUpperLeg2.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
        
        float f7;
        
        if (onGround > 0)
        {
        	f6 = onGround;
            f6 = 1.0F - onGround;
            f6 *= f6;
            f6 *= f6;
            f6 = 1.0F - f6;
            f7 = MathHelper.sin(f6 * (float)Math.PI);
            float f8 = MathHelper.sin(onGround * (float)Math.PI) * -(bipedHead.rotateAngleX - 0.7F) * 0.75F;
            neck.rotateAngleX = (float)((double)f7 * 1.2D + (double)f8) * 1.5F;
        }
        
        if (isSneak)
        {
        	float amount = -1.25F;
        	frontUpperLeg1.rotationPointY += amount;
        	frontUpperLeg2.rotationPointY += amount;
        	backUpperLeg1.rotationPointY += amount;
        	backUpperLeg2.rotationPointY += amount;
        	neck.rotationPointY -= amount;
        }
    }
    
    public ModelRenderer getArm()
    {
    	return frontUpperLeg1;
    }
    
    public ModelRenderer getNeck()
    {
    	return neck;
    }
}
