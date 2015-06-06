package fiskfille.alpaca.common.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.client.registry.RenderingRegistry;
import fiskfille.alpaca.AlpacaReflection;
import fiskfille.alpaca.client.file.ModOptions;
import fiskfille.alpaca.client.keybinds.AlpacaKeyBinding;
import fiskfille.alpaca.client.keybinds.AlpacaKeyBinds;
import fiskfille.alpaca.client.model.entity.ModelAlpaca;
import fiskfille.alpaca.client.model.entity.ModelAlpacaArmor;
import fiskfille.alpaca.client.model.entity.ModelAlpacaBase;
import fiskfille.alpaca.client.render.entity.RenderCorpse;
import fiskfille.alpaca.common.data.AlpacaSkins;
import fiskfille.alpaca.common.entity.EntityCorpse;
import fiskfille.alpaca.common.event.ClientEventHandler;

public class ClientProxy extends CommonProxy
{
	public static ModelAlpaca modelAlpaca = new ModelAlpaca();
    public static ModelAlpacaArmor modelAlpacaArmor = new ModelAlpacaArmor();

    public void preInit()
    {
        super.preInit();
        ModOptions.load();
        AlpacaKeyBinds.register();
        AlpacaReflection.client();

        clientEventHandler = new ClientEventHandler();
        registerEventHandler(clientEventHandler);
        RenderingRegistry.registerEntityRenderingHandler(EntityCorpse.class, new RenderCorpse());
    }

    public EntityPlayer getPlayer()
    {
        return Minecraft.getMinecraft().thePlayer;
    }

    public static ModelAlpacaBase getModelAlpaca(EntityPlayer player)
    {
        return modelAlpaca;
    }
}