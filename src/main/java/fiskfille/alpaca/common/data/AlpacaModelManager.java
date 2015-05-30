package fiskfille.alpaca.common.data;

import net.minecraft.entity.player.EntityPlayer;
import fiskfille.alpaca.client.model.entity.ModelAlpaca;
import fiskfille.alpaca.client.model.entity.ModelAlpacaBase;

public class AlpacaModelManager
{
    public static ModelAlpacaBase[] alpacas = { new ModelAlpaca() , new ModelAlpaca() };

    public static String[] textureNames = { "alpaca", "alienpaca"};

    public static int getModelID(EntityPlayer player)
    {
        return player.getUniqueID().toString().equals("c3ed4d52-fb4f-4964-ba1b-9cda2453741e") ? 1 : 0;
    }

    public static String getTexture(EntityPlayer player)
    {
        return textureNames[getModelID(player)];
    }
}