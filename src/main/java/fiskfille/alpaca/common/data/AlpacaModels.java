package fiskfille.alpaca.common.data;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import fiskfille.alpaca.client.file.ModOptions;
import fiskfille.alpaca.common.potion.AlpacaPotions;

public class AlpacaModels
{
	public static boolean isAlpaca(EntityPlayer player)
	{
		return player.isPotionActive(AlpacaPotions.potionAlpaca);
	}
	
	public static boolean isAlpacaClient(EntityPlayer player)
	{
		return isAlpaca(player) || ModOptions.forceRender;
	}
}