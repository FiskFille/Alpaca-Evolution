package fiskfille.alpaca.common.potion;

import net.minecraft.potion.Potion;

public class PotionCustom extends Potion
{
	public PotionCustom(int id, boolean isBadEffect, int liquidColor)
	{
		super(id, isBadEffect, liquidColor);
	}
	
	public Potion setIconIndex(int x, int y)
	{
		return super.setIconIndex(x, y);
	}
}