package fiskfille.alpaca.client.model.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelCow;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelAlpaca extends ModelBiped
{
    public ModelRenderer body;
    public ModelRenderer tail;
    public ModelRenderer frontUpperLeg1;
    public ModelRenderer frontUpperLeg2;
    public ModelRenderer backUpperLeg1;
    public ModelRenderer backUpperLeg2;
    public ModelRenderer neck;
    public ModelRenderer frontLowerLeg1;
    public ModelRenderer frontFoot1;
    public ModelRenderer frontLowerLeg2;
    public ModelRenderer frontFoot2;
    public ModelRenderer backLowerLeg1;
    public ModelRenderer backFoot1;
    public ModelRenderer backLowerLeg2;
    public ModelRenderer backFoot2;
    public ModelRenderer head;
    public ModelRenderer eye1;
    public ModelRenderer eye2;
    public ModelRenderer nose1;
    public ModelRenderer ear2;
    public ModelRenderer ear1;
    public ModelRenderer nose2;

    public ModelAlpaca()
    {
        textureWidth = 64;
        textureHeight = 32;
        neck = new ModelRenderer(this, 0, 19);
        neck.setRotationPoint(0.0F, -5.5F, -5.0F);
        neck.addBox(-2.0F, -8.0F, -1.5F, 4, 8, 3, 0.0F);
        frontFoot2 = new ModelRenderer(this, 26, 19);
        frontFoot2.mirror = true;
        frontFoot2.setRotationPoint(0.0F, 3.0F, 0.0F);
        frontFoot2.addBox(-1.0F, 0.0F, -2.0F, 2, 1, 3, 0.0F);
        backLowerLeg1 = new ModelRenderer(this, 14, 27);
        backLowerLeg1.setRotationPoint(0.0F, 5.0F, 0.0F);
        backLowerLeg1.addBox(-1.0F, 0.0F, -1.0F, 2, 3, 2, 0.0F);
        nose2 = new ModelRenderer(this, 5, 10);
        nose2.setRotationPoint(0.0F, -0.2F, -0.3F);
        nose2.addBox(-0.5F, -0.5F, -1.0F, 1, 1, 1, 0.0F);
        frontLowerLeg1 = new ModelRenderer(this, 14, 27);
        frontLowerLeg1.setRotationPoint(0.0F, 5.0F, 0.0F);
        frontLowerLeg1.addBox(-1.0F, 0.0F, -1.0F, 2, 3, 2, 0.0F);
        backFoot1 = new ModelRenderer(this, 26, 19);
        backFoot1.setRotationPoint(0.0F, 3.0F, 0.0F);
        backFoot1.addBox(-1.0F, 0.0F, -2.0F, 2, 1, 3, 0.0F);
        backLowerLeg2 = new ModelRenderer(this, 14, 27);
        backLowerLeg2.mirror = true;
        backLowerLeg2.setRotationPoint(0.0F, 5.0F, 0.0F);
        backLowerLeg2.addBox(-1.0F, 0.0F, -1.0F, 2, 3, 2, 0.0F);
        backFoot2 = new ModelRenderer(this, 26, 19);
        backFoot2.mirror = true;
        backFoot2.setRotationPoint(0.0F, 3.0F, 0.0F);
        backFoot2.addBox(-1.0F, 0.0F, -2.0F, 2, 1, 3, 0.0F);
        ear2 = new ModelRenderer(this, 6, 6);
        ear2.mirror = true;
        ear2.setRotationPoint(1.0F, -1.1F, 0.0F);
        ear2.addBox(0.0F, -0.5F, -0.5F, 2, 1, 1, 0.0F);
        setRotateAngle(ear2, 0.0F, 0.0F, -0.7853981633974483F);
        head = new ModelRenderer(this, 0, 0);
        head.setRotationPoint(0.0F, -6.75F, 1.0F);
        head.addBox(-1.5F, -1.5F, -3.0F, 3, 3, 3, 0.0F);
        backUpperLeg1 = new ModelRenderer(this, 14, 19);
        backUpperLeg1.setRotationPoint(-2.2F, -1.0F, 4.0F);
        backUpperLeg1.addBox(-1.5F, 0.0F, -1.5F, 3, 5, 3, 0.0F);
        frontFoot1 = new ModelRenderer(this, 26, 19);
        frontFoot1.setRotationPoint(0.0F, 3.0F, 0.0F);
        frontFoot1.addBox(-1.0F, 0.0F, -2.0F, 2, 1, 3, 0.0F);
        tail = new ModelRenderer(this, 26, 23);
        tail.setRotationPoint(0.0F, -6.6F, 6.0F);
        tail.addBox(-1.5F, -0.5F, 0.0F, 3, 1, 2, 0.0F);
        setRotateAngle(tail, 0.4363323129985824F, 0.0F, 0.0F);
        eye1 = new ModelRenderer(this, 0, 6);
        eye1.setRotationPoint(1.2F, -0.2F, -2.3F);
        eye1.addBox(0.0F, -0.5F, -0.5F, 1, 1, 1, 0.0F);
        frontLowerLeg2 = new ModelRenderer(this, 14, 27);
        frontLowerLeg2.mirror = true;
        frontLowerLeg2.setRotationPoint(0.0F, 5.0F, 0.0F);
        frontLowerLeg2.addBox(-1.0F, 0.0F, -1.0F, 2, 3, 2, 0.0F);
        nose1 = new ModelRenderer(this, 8, 8);
        nose1.setRotationPoint(0.0F, 0.4F, -2.4F);
        nose1.addBox(-0.5F, -1.0F, -1.0F, 1, 2, 1, 0.0F);
        eye2 = new ModelRenderer(this, 0, 6);
        eye2.setRotationPoint(-1.2F, -0.2F, -2.3F);
        eye2.addBox(-1.0F, -0.5F, -0.5F, 1, 1, 1, 0.0F);
        ear1 = new ModelRenderer(this, 6, 6);
        ear1.setRotationPoint(-1.0F, -1.1F, 0.0F);
        ear1.addBox(-2.0F, -0.5F, -0.5F, 2, 1, 1, 0.0F);
        setRotateAngle(ear1, 0.0F, 0.0F, 0.7853981633974483F);
        frontUpperLeg2 = new ModelRenderer(this, 14, 19);
        frontUpperLeg2.mirror = true;
        frontUpperLeg2.setRotationPoint(2.2F, -1.0F, -4.0F);
        frontUpperLeg2.addBox(-1.5F, 0.0F, -1.5F, 3, 5, 3, 0.0F);
        backUpperLeg2 = new ModelRenderer(this, 14, 19);
        backUpperLeg2.mirror = true;
        backUpperLeg2.setRotationPoint(2.2F, -1.0F, 4.0F);
        backUpperLeg2.addBox(-1.5F, 0.0F, -1.5F, 3, 5, 3, 0.0F);
        body = new ModelRenderer(this, 0, 0);
        body.setRotationPoint(0.0F, 16.0F, 0.0F);
        body.addBox(-3.5F, -7.0F, -6.0F, 7, 7, 12, 0.0F);
        frontUpperLeg1 = new ModelRenderer(this, 14, 19);
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
    
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z)
    {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
    
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
    {
    	for (ModelRenderer modelrenderer : new ModelRenderer[]{frontLowerLeg1, frontLowerLeg2, backLowerLeg1, backLowerLeg2})
    	{
    		modelrenderer.rotateAngleX = 0;
    		modelrenderer.rotationPointY = 5;
    	}
    	
    	for (ModelRenderer modelrenderer : new ModelRenderer[]{frontFoot1, frontFoot2, backFoot1, backFoot2})
    	{
    		modelrenderer.rotateAngleX = 0;
    		modelrenderer.rotationPointY = 3;
    	}
    	
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
        	float amount = 0.45F;
        	
        	for (ModelRenderer modelrenderer : new ModelRenderer[]{frontUpperLeg1, frontUpperLeg2, backUpperLeg1, backUpperLeg2})
        	{
        		modelrenderer.rotateAngleX -= amount;
        	}
        	
        	for (ModelRenderer modelrenderer : new ModelRenderer[]{frontLowerLeg1, frontLowerLeg2, backLowerLeg1, backLowerLeg2})
        	{
        		modelrenderer.rotateAngleX += amount * 2;
        		modelrenderer.rotationPointY -= 0.75F;
        	}
        	
        	for (ModelRenderer modelrenderer : new ModelRenderer[]{frontFoot1, frontFoot2, backFoot1, backFoot2})
        	{
        		modelrenderer.rotateAngleX -= amount;
        		modelrenderer.rotationPointY -= 0.35F;
        	}
        	
        	frontFoot1.rotateAngleX -= frontUpperLeg1.rotateAngleX + amount;
        	frontFoot2.rotateAngleX -= frontUpperLeg2.rotateAngleX + amount;
        	backFoot1.rotateAngleX -= backUpperLeg1.rotateAngleX + amount;
        	backFoot2.rotateAngleX -= backUpperLeg2.rotateAngleX + amount;
        }
    }
}
