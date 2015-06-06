package fiskfille.alpaca.common.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.FMLCommonHandler;
import fiskfille.alpaca.AlpacaReflection;
import fiskfille.alpaca.common.entity.AlpacaEntities;
import fiskfille.alpaca.common.event.ClientEventHandler;
import fiskfille.alpaca.common.event.CommonEventHandler;
import fiskfille.alpaca.common.potion.AlpacaPotions;

public class CommonProxy
{
    public ClientEventHandler clientEventHandler;

    public void preInit()
    {
        AlpacaReflection.common();
        AlpacaEntities.load();
        AlpacaPotions.load();

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