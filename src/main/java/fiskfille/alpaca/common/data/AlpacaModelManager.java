package fiskfille.alpaca.common.data;

import java.util.HashMap;

import com.google.common.collect.Maps;

import net.minecraft.entity.player.EntityPlayer;
import fiskfille.alpaca.client.model.entity.ModelAlpaca;
import fiskfille.alpaca.client.model.entity.ModelAlpacaBase;
import fiskfille.alpaca.common.color.ColorHelper;

public class AlpacaModelManager
{
    public static ModelAlpacaBase[] alpacas = { new ModelAlpaca(), new ModelAlpaca(), new ModelAlpaca(), new ModelAlpaca(), new ModelAlpaca(), new ModelAlpaca(), new ModelAlpaca() };

    public static String[] textureNames = { "alpaca", "alienpaca", "stitchpaca", "penguinpaca", "chickenpaca", "lizardpaca", "turtlepaca" };
    
    public static HashMap<String, Integer> alpacaSkins = Maps.newHashMap();
    
    public static int getModelID(EntityPlayer player)
    {
    	initAlpacaSkins();
    	String s = player.getUniqueID().toString();
        return alpacaSkins.containsKey(s) ? alpacaSkins.get(s) : 0;
    }

    public static String getTexture(EntityPlayer player)
    {
        return textureNames[getModelID(player)];
    }
    
    public static void initAlpacaSkins()
    {
    	alpacaSkins.clear();
    	alpacaSkins.put("c3ed4d52-fb4f-4964-ba1b-9cda2453741e", 1); // The Alien
    	alpacaSkins.put("12139dc7-8311-49cc-82f7-58671d9cf58d", 2); // Haakon
        alpacaSkins.put("fef70d41-c47b-4aa1-872f-e6f9271d2803", 3); // Cody
        alpacaSkins.put("5ef79c19-d36a-4c05-a1f7-6f6b72fcac12", 4); // Joe
        alpacaSkins.put("d3193dc2-8a5b-4f75-8079-61b1b17f71ba", 5); // LittleLizard
        alpacaSkins.put("86bde75b-d3ef-492a-a55d-f8614f962459", 6); // TinyTurtle
    }
}