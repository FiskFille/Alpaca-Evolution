package fiskfille.alpaca.common.item;

import net.minecraft.item.Item;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.registry.GameRegistry;

public class ItemRegistry
{
	public static void registerItem(Item item, String name, String modId)
    {
        String unlocalizedName = name.toLowerCase().replaceAll(" ", "_").replaceAll("'", "");       
        item.setUnlocalizedName(unlocalizedName);
        item.setTextureName(modId + ":" + unlocalizedName);
        GameRegistry.registerItem(item, unlocalizedName);
    }
	
    public static void registerIngot(Item item, String name, String modId, String oreDictName)
    {
        registerItem(item, name, modId);
        OreDictionary.registerOre(oreDictName, item);
    }
}