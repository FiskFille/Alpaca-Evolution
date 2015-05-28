package fiskfille.alpaca.common.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.FMLCommonHandler;
import fiskfille.alpaca.common.entity.AlpacaEntities;
import fiskfille.alpaca.common.event.CommonEventHandler;

public class CommonProxy
{
	public void preInit()
	{
		AlpacaEntities.load();
		
		registerEventHandler(new CommonEventHandler());
	}
	
	public void registerEventHandler(Object obj)
	{
		MinecraftForge.EVENT_BUS.register(obj);
        FMLCommonHandler.instance().bus().register(obj);
	}

	public EntityPlayer getPlayer()
	{
		return null;
	}
}