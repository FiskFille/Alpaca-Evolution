package fiskfille.alpaca;

import java.awt.Color;
import java.util.HashMap;

import org.apache.http.conn.ssl.AbstractVerifier;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.common.MinecraftForge;

import com.google.common.collect.Maps;

import fiskfille.alpaca.client.file.ModOptions;
import fiskfille.alpaca.common.event.AlpacaGetColorEvent;
import fiskfille.alpaca.common.potion.AlpacaPotions;

public class AlpacaAPI
{
	public static final ResourceLocation defaultAlpacaTexture = new ResourceLocation(Alpaca.modid, "textures/entity/alpaca/alpaca.png");
	public static final ResourceLocation defaultAlpacaTextureOverlay = new ResourceLocation(Alpaca.modid, "textures/entity/alpaca/alpaca_overlay.png");
    
	public static final HashMap<String, ResourceLocation> alpacaTextures = Maps.newHashMap();
    public static final HashMap<String, ResourceLocation> alpacaTextureOverlays = Maps.newHashMap();
    
    public static ResourceLocation getAlpacaTexture(EntityPlayer player)
    {
    	String s = player.getUniqueID().toString();
        return alpacaTextures.containsKey(s) ? alpacaTextures.get(s) : defaultAlpacaTexture;
    }
    
    public static ResourceLocation getAlpacaTextureOverlay(EntityPlayer player)
    {
    	String s = player.getUniqueID().toString();
        return alpacaTextureOverlays.containsKey(s) ? alpacaTextureOverlays.get(s) : defaultAlpacaTextureOverlay;
    }
    
    public static void addAlpacaTexture(String uuid, ResourceLocation texture, ResourceLocation overlay)
    {
    	alpacaTextures.put(uuid, texture);
    	alpacaTextureOverlays.put(uuid, overlay);
    }
	
	public static int getAlpacaColor(EntityPlayer player)
    {
        AlpacaGetColorEvent event = new AlpacaGetColorEvent(player, player.getCommandSenderName().hashCode());
        MinecraftForge.EVENT_BUS.post(event);
        
        if (!event.isCanceled())
        {
        	String s = player.getUniqueID().toString();
        	
        	if (s.equals("853c80ef-3c37-49fd-aa49-938b674adae6") || player.getCommandSenderName().toLowerCase().contains("jeb")) // Jeb
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
            else if (s.equals("03a42a75-223a-4307-99c1-b69162ad6a6f") || s.equals("c46c08f3-f004-443d-b8ce-340d2223a332")) //CoolSquid
            {
                return 0x000099;
            }
            else if (s.equals("cf9fa23f-205e-4eed-aba3-9f2848cd6a4d")) // OnyxDarkKnight
            {
            	return 0x007000;
            }
            else if (alpacaTextures.containsKey(s))
            {
            	return 0xFFFFFF;
            }
        }
        
        return event.color;
    }
	
	public static boolean isAlpaca(EntityPlayer player)
	{
		return player.isPotionActive(AlpacaPotions.potionAlpaca);
	}
	
	public static boolean isAlpacaClient(EntityPlayer player)
	{
		return isAlpaca(player) || ModOptions.forceRender;
	}
}
