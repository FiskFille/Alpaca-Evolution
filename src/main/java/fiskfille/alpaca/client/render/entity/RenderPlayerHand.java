package fiskfille.alpaca.client.render.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import fiskfille.alpaca.common.color.ColorHelper;
import fiskfille.alpaca.common.data.AlpacaModelManager;

public class RenderPlayerHand extends RenderPlayer
{
    public RenderPlayer parent;
    public ModelBiped biped;
    public ModelRenderer replacement;

    private Minecraft mc = Minecraft.getMinecraft();

    @Override
    public void renderFirstPersonArm(EntityPlayer player)
    {
        if (replacement != null)
        {
            GL11.glColor4f(1, 1, 1, 1);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

            GL11.glTranslatef(0, -0.25F, 0.0F);

            ModelRenderer arm = biped.bipedRightArm;
            biped.bipedRightArm = replacement;

            // player arms are 12 blocks long
            int heightDiff = 12 - getModelHeight(replacement);
            float rotX = replacement.rotationPointX;
            float rotY = replacement.rotationPointY;
            float rotZ = replacement.rotationPointZ;
            float angX = replacement.rotateAngleX;
            float angY = replacement.rotateAngleY;
            float angZ = replacement.rotateAngleZ;

            replacement.rotationPointX = arm.rotationPointX;
            replacement.rotationPointY = arm.rotationPointY + heightDiff;
            replacement.rotationPointZ = arm.rotationPointZ;
            biped.onGround = 0.0F;
            biped.isSneak = false;
            biped.setRotationAngles(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, player);

            TextureManager textureManager = mc.getTextureManager();

            String texture = AlpacaModelManager.getTexture(player);
            
            textureManager.bindTexture(new ResourceLocation("textures/entity/alpaca/" + texture + ".png"));
            biped.bipedRightArm.render(0.0625F);

            textureManager.bindTexture(new ResourceLocation("textures/entity/alpaca/" + texture + "_overlay.png"));

            ColorHelper.setColorFromInt(ColorHelper.getAlpacaColor(player), 1.0F);
            biped.bipedRightArm.render(0.0625F);
            biped.bipedRightArm = arm;

            replacement.rotationPointX = rotX;
            replacement.rotationPointY = rotY;
            replacement.rotationPointZ = rotZ;
            replacement.rotateAngleX = angX;
            replacement.rotateAngleY = angY;
            replacement.rotateAngleZ = angZ;

            GL11.glDisable(GL11.GL_BLEND);
            GL11.glColor4f(1, 1, 1, 1);
        }
    }

    public void setParent(RenderPlayer render)
    {
        if (parent != render)
        {
            biped = render.modelBipedMain;
        }
        parent = render;
    }

    public static int getModelHeight(ModelRenderer model)
    {
        int height = 0;// Y1 lower than Y2

        for (int i = 0; i < model.cubeList.size(); i++)
        {
            ModelBox box = (ModelBox) model.cubeList.get(i);

            if ((int) Math.abs(box.posY2 - box.posY1) > height)
            {
                height = (int) Math.abs(box.posY2 - box.posY1);
            }
        }
        return height;
    }
}