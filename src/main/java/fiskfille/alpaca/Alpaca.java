package fiskfille.alpaca;

import net.minecraft.util.ResourceLocation;
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
    public static final String version = "1.2";

    @Instance(Alpaca.modid)
    public static Alpaca instance;

    @SidedProxy(clientSide = "fiskfille.alpaca.common.proxy.ClientProxy", serverSide = "fiskfille.alpaca.common.proxy.CommonProxy")
    public static CommonProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    	ModOptions.load();
        PacketManager.registerPackets();
        initSkins();
        proxy.preInit();
    }

	private void initSkins()
	{
		registerSkin("c3ed4d52-fb4f-4964-ba1b-9cda2453741e", "alienpaca"); // The Alien
		registerSkin("12139dc7-8311-49cc-82f7-58671d9cf58d", "stitchpaca"); // Haakon
		registerSkin("fef70d41-c47b-4aa1-872f-e6f9271d2803", "penguinpaca"); // Cody
		registerSkin("5ef79c19-d36a-4c05-a1f7-6f6b72fcac12", "chickenpaca"); // Joe
		registerSkin("d3193dc2-8a5b-4f75-8079-61b1b17f71ba", "lizardpaca"); // LittleLizard
		registerSkin("86bde75b-d3ef-492a-a55d-f8614f962459", "turtlepaca"); // TinyTurtle
	}
	
	private void registerSkin(String uuid, String textureName)
	{
		ResourceLocation texture = new ResourceLocation(modid, "textures/entity/alpaca/" + textureName + ".png");
		AlpacaAPI.addAlpacaTexture(uuid, texture, texture);
	}
}