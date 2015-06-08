package fiskfille.alpaca.common.item;

import fiskfille.alpaca.Alpaca;
import net.minecraft.item.Item;

public class AlpacaItems
{
	public static Item face;
	
	public static void load()
	{
		face = new ItemFace();
		ItemRegistry.registerItem(face, "Face", Alpaca.modid);
	}
}
