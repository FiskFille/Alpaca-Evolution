package fiskfille.alpaca.client.render.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fiskfille.alpaca.client.model.entity.ModelAlpaca;
import fiskfille.alpaca.common.entity.EntityTongue;
import fiskfille.alpaca.common.proxy.ClientProxy;

@SideOnly(Side.CLIENT)
public class RenderTongue extends Render
{
    private static final ResourceLocation field_110792_a = new ResourceLocation("textures/particle/particles.png");
    
    public void doRender(EntityTongue entity, double x, double y, double z, float f, float partialTicks)
    {
        GL11.glPushMatrix();
        Tessellator tessellator = Tessellator.instance;
        Minecraft mc = Minecraft.getMinecraft();
        ModelAlpaca model = ClientProxy.modelAlpaca;
        
        if (entity.player != null)
        {
            Vec3 vec3 = Vec3.createVectorHelper(0.0D, -0.15D, 0.0D);
            vec3.rotateAroundX(-(entity.player.prevRotationPitch + (entity.player.rotationPitch - entity.player.prevRotationPitch) * partialTicks) * (float)Math.PI / 180.0F);
            vec3.rotateAroundY(-(entity.player.prevRotationYaw + (entity.player.rotationYaw - entity.player.prevRotationYaw) * partialTicks) * (float)Math.PI / 180.0F);
            double playerPosX = entity.player.prevPosX + (entity.player.posX - entity.player.prevPosX) * (double)partialTicks + vec3.xCoord;
            double playerPosY = entity.player.prevPosY + (entity.player.posY - entity.player.prevPosY) * (double)partialTicks + vec3.yCoord;
            double playerPosZ = entity.player.prevPosZ + (entity.player.posZ - entity.player.prevPosZ) * (double)partialTicks + vec3.zCoord;
            
            if (this.renderManager.options.thirdPersonView > 0 || entity.player != Minecraft.getMinecraft().thePlayer)
            {
            	double side = 0.0D;
            	double forward = (7 + (model.neck.rotateAngleX / 1.5707964F) * 6.5D) * 0.0625D;
            	double yOffset = (entity.player == Minecraft.getMinecraft().thePlayer ? 0.0D : (double)entity.player.getEyeHeight()) + 0.1D;
            	yOffset -= (model.neck.rotateAngleX / 1.5707964F) * 5.5D * 0.0625D;
            	
            	
            	
                float renderYawOffset = (entity.player.renderYawOffset + (entity.player.prevRenderYawOffset - entity.player.prevRenderYawOffset) * partialTicks) * (float)Math.PI / 180.0F;
                double d = (double)MathHelper.sin(renderYawOffset);
                double d1 = (double)MathHelper.cos(renderYawOffset);
                playerPosX = entity.player.prevPosX + (entity.player.posX - entity.player.prevPosX) * (double)partialTicks - d1 * side - d * forward;
                playerPosY = entity.player.prevPosY + yOffset + (entity.player.posY - entity.player.prevPosY) * (double)partialTicks - 0.45D;
                playerPosZ = entity.player.prevPosZ + (entity.player.posZ - entity.player.prevPosZ) * (double)partialTicks - d * side + d1 * forward;
            }

            double entityPosX = entity.prevPosX + (entity.posX - entity.prevPosX) * (double)partialTicks;
            double entityPosY = entity.prevPosY + (entity.posY - entity.prevPosY) * (double)partialTicks + 0.25D;
            double entityPosZ = entity.prevPosZ + (entity.posZ - entity.prevPosZ) * (double)partialTicks;
            double diffPosX = (double)((float)(playerPosX - entityPosX));
            double diffPosY = (double)((float)(playerPosY - entityPosY));
            double diffPosZ = (double)((float)(playerPosZ - entityPosZ));
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glDisable(GL11.GL_LIGHTING);
            int width = 12;
            int height = 7;
            int density = 1000;
            
            for (int j = -width; j <= width; ++j)
            {
            	for (int j1 = -height; j1 <= height; ++j1)
                {
            		tessellator.startDrawing(3);
                    tessellator.setColorOpaque_I(0xDD2C2C);
                    
                    if (j == width && j1 == height || j == -width && j1 == -height || j == width && j1 == -height || j == -width && j1 == height)
                    {
                    	tessellator.setColorOpaque_I(0xAF2323);
                    }
                    
                    byte segments = 32;
                    
                	for (int i = 0; i <= segments; ++i)
                    {
                        double f1 = (float)i / (float)segments;
                        tessellator.addVertex((float)j / density + x + diffPosX * f1, (float)j1 / density + y + diffPosY * (f1 * f1 + f1) * 0.5D + 0.25D, z + diffPosZ * f1);
                    }
                	
                	tessellator.draw();
                }
            }
        	
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
        }
        
        GL11.glPopMatrix();
    }

    protected ResourceLocation getEntityTexture(EntityTongue p_110775_1_)
    {
        return field_110792_a;
    }

    protected ResourceLocation getEntityTexture(Entity p_110775_1_)
    {
        return this.getEntityTexture((EntityTongue)p_110775_1_);
    }

    public void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
    {
        this.doRender((EntityTongue)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
}