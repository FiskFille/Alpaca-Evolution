package fiskfille.alpaca.common.data;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;

public class DataManager
{
	public static Map<String, Integer> entitiesEaten = new HashMap<String, Integer>();
	
	public static int getEntitiesEaten(EntityPlayer player)
	{
		return entitiesEaten.get(player.getUniqueID().toString()) == null ? 0 : entitiesEaten.get(player.getUniqueID().toString());
	}
	
	public static void setEntitiesEaten(EntityPlayer player, int i)
	{
		entitiesEaten.put(player.getUniqueID().toString(), i);
	}
}