package fiskfille.alpaca;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import fiskfille.alpaca.client.file.ModOptions;
import fiskfille.alpaca.common.packet.PacketManager;
import fiskfille.alpaca.common.proxy.CommonProxy;

@Mod(modid = Alpaca.modid, name = "Alpaca Evolution", version = Alpaca.version)
public class Alpaca
{
    public static final String modid = "alpaca";
    public static final String version = "1.0";

    @Instance(Alpaca.modid)
    public static Alpaca instance;

    @SidedProxy(clientSide = "fiskfille.alpaca.common.proxy.ClientProxy", serverSide = "fiskfille.alpaca.common.proxy.CommonProxy")
    public static CommonProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    	ModOptions.load();
        PacketManager.registerPackets();
        proxy.preInit();
    }
}