package fiskfille.alpaca.client.render.entity;

import net.minecraft.client.model.ModelChicken;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fiskfille.alpaca.common.entity.EntityCorpse;

@SideOnly(Side.CLIENT)
public class RenderCorpse extends RenderLiving
{
    public RenderCorpse()
    {
        super(new ModelChicken(), 0.5F);
    }
    
    protected ResourceLocation getEntityTexture(Entity entity)
    {
        return new ResourceLocation("missingno");
    }
    
    public void doRender(EntityCorpse entity, double d, double d1, double d2, float f, float f1)
    {
    	RenderManager rm = RenderManager.instance;
    	
    	if (entity.entity != null)
    	{
    		Render render = rm.getEntityRenderObject(entity.entity);
        	
        	if (render != null)
        	{
        		GL11.glPushMatrix();
        		
        		if (!(entity.entity instanceof EntitySquid))
        		{
        			GL11.glTranslatef(-entity.entity.height / 4, entity.entity.width / 2, -entity.entity.height / 4);
        		}
        		
        		render.doRender(entity.entity, d, d1, d2, f, f1);
        		GL11.glPopMatrix();
        	}
    	}
    }
    
    public void doRender(Entity entity, double d, double d1, double d2, float f, float f1)
    {
    	doRender((EntityCorpse)entity, d, d1, d2, f, f1);
    }
}