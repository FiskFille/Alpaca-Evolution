package fiskfille.alpaca.common.data;

import java.util.HashMap;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import com.google.common.collect.Maps;

import fiskfille.alpaca.Alpaca;

public class AlpacaSkins
{    
	public static final ResourceLocation defaultAlpacaTexture = new ResourceLocation(Alpaca.modid, "textures/entity/alpaca/alpaca.png");
	public static final ResourceLocation defaultAlpacaTextureOverlay = new ResourceLocation(Alpaca.modid, "textures/entity/alpaca/alpaca_overlay.png");
    public static HashMap<String, String> alpacaSkins = Maps.newHashMap();
    
    public static String getSkin(EntityPlayer player)
    {
    	if (alpacaSkins.isEmpty())
    	{
    		initAlpacaSkins();
    	}
    	
    	String s = player.getUniqueID().toString();
        return alpacaSkins.containsKey(s) ? alpacaSkins.get(s) : "alpaca";
    }
    
    public static ResourceLocation getTexture(EntityPlayer player)
    {
    	return new ResourceLocation(Alpaca.modid, "textures/entity/alpaca/" + getSkin(player) + ".png");
    }
    
    public static ResourceLocation getTextureOverlay(EntityPlayer player)
    {
    	return new ResourceLocation(Alpaca.modid, "textures/entity/alpaca/" + getSkin(player) + "_overlay.png");
    }
    
    public static void initAlpacaSkins()
    {
    	alpacaSkins.put("c3ed4d52-fb4f-4964-ba1b-9cda2453741e", "alienpaca"); // The Alien
    	alpacaSkins.put("12139dc7-8311-49cc-82f7-58671d9cf58d", "stitchpaca"); // Haakon
        alpacaSkins.put("fef70d41-c47b-4aa1-872f-e6f9271d2803", "penguinpaca"); // Cody
        alpacaSkins.put("5ef79c19-d36a-4c05-a1f7-6f6b72fcac12", "chickenpaca"); // Joe
        alpacaSkins.put("d3193dc2-8a5b-4f75-8079-61b1b17f71ba", "lizardpaca"); // LittleLizard
        alpacaSkins.put("86bde75b-d3ef-492a-a55d-f8614f962459", "turtlepaca"); // TinyTurtle
    }
}