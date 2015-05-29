package fiskfille.alpaca.common.data;

import fiskfille.alpaca.client.model.entity.ModelAlpacaBase;
import fiskfille.alpaca.client.model.entity.ModelAlpaca;

public class AlpacaModelManager
{
	public static ModelAlpacaBase[] alpacas =
	{
		new ModelAlpaca()
	};
	
	public static String[] textureNames =
	{
		"alpaca"
	};
	
	public static int getModelID()
	{
		return 0;
	}
	
	public static String getTexture()
	{
		return textureNames[getModelID()];
	}
}