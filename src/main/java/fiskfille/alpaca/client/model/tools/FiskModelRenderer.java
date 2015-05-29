package fiskfille.alpaca.client.model.tools;

import java.util.ArrayList;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.Tessellator;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class FiskModelRenderer extends ModelRenderer
{
    private boolean compiled;
    /** The GL display list rendered by the Tessellator for this model */
    private int displayList;
    private ModelBase baseModel;
    
    public float scaleX = 1.0F;
    public float scaleY = 1.0F;
    public float scaleZ = 1.0F;
    
    public ModelRenderer parent;

    public FiskModelRenderer(ModelBase modelBase, String string)
    {
    	super(modelBase, string);
        this.textureWidth = 64.0F;
        this.textureHeight = 32.0F;
        this.showModel = true;
        this.cubeList = new ArrayList();
        this.baseModel = modelBase;
        modelBase.boxList.add(this);
        this.setTextureSize(modelBase.textureWidth, modelBase.textureHeight);
    }

    public FiskModelRenderer(ModelBase modelBase)
    {
        this(modelBase, (String)null);
    }

    public FiskModelRenderer(ModelBase modelBase, int textureX, int textureY)
    {
        this(modelBase);
        this.setTextureOffset(textureX, textureY);
    }
    
    public void addChild(ModelRenderer renderer)
    {
        super.addChild(renderer);
        
        if (renderer instanceof FiskModelRenderer)
        {
            ((FiskModelRenderer)renderer).setParent(this);
        }
    }
    
    public void setParent(ModelRenderer renderer)
    {
    	parent = renderer;
    }
    
    public boolean hasParent(FiskModelRenderer renderer)
    {
    	return renderer.parent != null;
    }

    @SideOnly(Side.CLIENT)
    public void render(float f)
    {
    	GL11.glPushMatrix();
    	
        if (!this.isHidden)
        {
            if (this.showModel)
            {
            	if (!this.compiled)
                {
                    this.compileDisplayList(f);
                }
            	
            	
                int i;
                
                if (this.rotateAngleX == 0.0F && this.rotateAngleY == 0.0F && this.rotateAngleZ == 0.0F)
                {
                    float f5 = 0.0625F;
                	GL11.glTranslatef(this.offsetX, this.offsetY, this.offsetZ);
                	GL11.glTranslatef(this.rotationPointX * f5, this.rotationPointY * f5, this.rotationPointZ * f5);
                	GL11.glScalef(scaleX, scaleY, scaleZ);
                	GL11.glTranslatef(-this.rotationPointX * f5, -this.rotationPointY * f5, -this.rotationPointZ * f5);
                	
                    if (this.rotationPointX == 0.0F && this.rotationPointY == 0.0F && this.rotationPointZ == 0.0F)
                    {
                        GL11.glCallList(this.displayList);

                        if (this.childModels != null)
                        {
                            for (i = 0; i < this.childModels.size(); ++i)
                            {
                                ((FiskModelRenderer)this.childModels.get(i)).render(f);
                            }
                        }
                    }
                    else
                    {
                        GL11.glTranslatef(this.rotationPointX * f, this.rotationPointY * f, this.rotationPointZ * f);
                        GL11.glCallList(this.displayList);

                        if (this.childModels != null)
                        {
                            for (i = 0; i < this.childModels.size(); ++i)
                            {
                                ((FiskModelRenderer)this.childModels.get(i)).render(f);
                            }
                        }

                        GL11.glTranslatef(-this.rotationPointX * f, -this.rotationPointY * f, -this.rotationPointZ * f);
                    }
                }
                else
                {
                    GL11.glPushMatrix();
                    GL11.glTranslatef(this.rotationPointX * f, this.rotationPointY * f, this.rotationPointZ * f);

                    if (this.rotateAngleZ != 0.0F)
                    {
                        GL11.glRotatef(this.rotateAngleZ * (180F / (float)Math.PI), 0.0F, 0.0F, 1.0F);
                    }

                    if (this.rotateAngleY != 0.0F)
                    {
                        GL11.glRotatef(this.rotateAngleY * (180F / (float)Math.PI), 0.0F, 1.0F, 0.0F);
                    }

                    if (this.rotateAngleX != 0.0F)
                    {
                        GL11.glRotatef(this.rotateAngleX * (180F / (float)Math.PI), 1.0F, 0.0F, 0.0F);
                    }
                    
                	GL11.glScalef(scaleX, scaleY, scaleZ);
                    GL11.glCallList(this.displayList);
                    
                    if (this.childModels != null)
                    {
                        for (i = 0; i < this.childModels.size(); ++i)
                        {
                            ((FiskModelRenderer)this.childModels.get(i)).render(f);
                        }
                    }

                    GL11.glPopMatrix();
                }
                
                GL11.glTranslatef(-this.offsetX, -this.offsetY, -this.offsetZ);
            }
        }
        
        GL11.glPopMatrix();
    }
    
    @SideOnly(Side.CLIENT)
    private void compileDisplayList(float p_78788_1_)
    {
        this.displayList = GLAllocation.generateDisplayLists(1);
        GL11.glNewList(this.displayList, GL11.GL_COMPILE);
        Tessellator tessellator = Tessellator.instance;

        for (int i = 0; i < this.cubeList.size(); ++i)
        {
            ((ModelBox)this.cubeList.get(i)).render(tessellator, p_78788_1_);
        }

        GL11.glEndList();
        this.compiled = true;
    }
    
    public void setScale(float x, float y, float z)
    {
    	this.scaleX = x;
    	this.scaleY = y;
    	this.scaleZ = z;
    }
    
    public float getRotationPointX()
    {
    	float f = rotationPointX;
    	
    	if (parent instanceof FiskModelRenderer)
    	{
    		f += ((FiskModelRenderer)parent).getRotationPointX();
    	}
    	
    	return f;
    }
    
    public float getRotationPointY()
    {
    	float f = rotationPointY;
    	
    	if (parent instanceof FiskModelRenderer)
    	{
    		f += ((FiskModelRenderer)parent).getRotationPointY();
    	}
    	
    	return f;
    }
    
    public float getRotationPointZ()
    {
    	float f = rotationPointZ;
    	
    	if (parent instanceof FiskModelRenderer)
    	{
    		f += ((FiskModelRenderer)parent).getRotationPointZ();
    	}
    	
    	return f;
    }
    
    public float getRotateAngleX()
    {
    	float f = rotateAngleX;
    	
    	if (parent instanceof FiskModelRenderer)
    	{
    		f += ((FiskModelRenderer)parent).getRotateAngleX();
    	}
    	
    	return f;
    }
    
    public float getRotateAngleY()
    {
    	float f = rotateAngleY;
    	
    	if (parent instanceof FiskModelRenderer)
    	{
    		f += ((FiskModelRenderer)parent).getRotateAngleY();
    	}
    	
    	return f;
    }
    
    public float getRotateAngleZ()
    {
    	float f = rotateAngleZ;
    	
    	if (parent instanceof FiskModelRenderer)
    	{
    		f += ((FiskModelRenderer)parent).getRotateAngleZ();
    	}
    	
    	return f;
    }
}