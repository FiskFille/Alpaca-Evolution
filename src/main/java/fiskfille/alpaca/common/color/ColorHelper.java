package fiskfille.alpaca.common.color;

import java.awt.Color;

import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;

import org.lwjgl.opengl.GL11;

import fiskfille.alpaca.Alpaca;

public class ColorHelper
{
    public static void setColorFromInt(int color, float alpha)
    {
        float r = (float) (color >> 16 & 255) / 255.0F;
        float g = (float) (color >> 8 & 255) / 255.0F;
        float b = (float) (color & 255) / 255.0F;
        GL11.glColor4f(r, g, b, alpha);
    }

    public static int getAlpacaColor(EntityPlayer player)
    {
        String s = player.getUniqueID().toString();

        if (s.equals("853c80ef-3c37-49fd-aa49-938b674adae6")) // Jeb
        {
            int k = player.ticksExisted / 25 + player.getEntityId();
            int l = k % EntitySheep.fleeceColorTable.length;
            int i1 = (k + 1) % EntitySheep.fleeceColorTable.length;
            float f1 = ((float) (player.ticksExisted % 25) + Alpaca.proxy.clientEventHandler.renderTick) / 25.0F;
            float r = EntitySheep.fleeceColorTable[l][0] * (1.0F - f1) + EntitySheep.fleeceColorTable[i1][0] * f1;
            float b = EntitySheep.fleeceColorTable[l][1] * (1.0F - f1) + EntitySheep.fleeceColorTable[i1][1] * f1;
            float g = EntitySheep.fleeceColorTable[l][2] * (1.0F - f1) + EntitySheep.fleeceColorTable[i1][2] * f1;
            return new Color(r, g, b).getRGB();
        }
        else if (s.equals("52d1e4a0-062a-4623-8ac9-4f9ee790f40d")) // Fisk
        {
            return 0xE00000;
        }
        else if (s.equals("3a0dba0d-857a-4563-9654-80e45914e990")) // Kirk
        {
            return 0x88B0B4;
        }
        else if (s.equals("40e85e42-21f6-46b6-b5b3-6aeb07f3e3fd")) // Lax
        {
            return 0x3D3D3D;
        }
        else if (s.equals("e63a1d61-adf1-4d47-b5f8-43efc5c84908")) // Phase
        {
            return 0xF261BF;
        }
        else if (s.equals("c3ed4d52-fb4f-4964-ba1b-9cda2453741e")) // The Alien
        {
            return 0xFFFFFF;
        }
        else if (s.equals("03a42a75-223a-4307-99c1-b69162ad6a6f") || s.equals("c46c08f3-f004-443d-b8ce-340d2223a332")) //CoolSquid
        {
            return 0x000099;
        }

        return player.getCommandSenderName().hashCode();
    }
}
