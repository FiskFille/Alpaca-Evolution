package fiskfille.alpaca.common.data;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.entity.player.EntityPlayer;

public class DataManager
{
    public static Map<String, Integer> momentum = new HashMap<String, Integer>();
    
    public static int getMomentum(EntityPlayer player)
    {
        return momentum.get(player.getUniqueID().toString()) == null ? 0 : momentum.get(player.getUniqueID().toString());
    }

    public static void setMomentum(EntityPlayer player, int i)
    {
        momentum.put(player.getUniqueID().toString(), i);
    }
}