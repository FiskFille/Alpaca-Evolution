package fiskfille.alpaca.common.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.client.registry.RenderingRegistry;
import fiskfille.alpaca.client.model.entity.ModelAlpaca;
import fiskfille.alpaca.client.render.entity.RenderCorpse;
import fiskfille.alpaca.common.entity.EntityCorpse;
import fiskfille.alpaca.common.event.ClientEventHandler;

public class ClientProxy extends CommonProxy
{
    public static ModelAlpaca modelAlpaca = new ModelAlpaca();

    public void preInit()
    {
        super.preInit();
        registerEventHandler(new ClientEventHandler());
        RenderingRegistry.registerEntityRenderingHandler(EntityCorpse.class, new RenderCorpse());
    }

    public EntityPlayer getPlayer()
    {
        return Minecraft.getMinecraft().thePlayer;
    }
}