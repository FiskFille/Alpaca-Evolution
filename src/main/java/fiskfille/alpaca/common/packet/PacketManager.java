package fiskfille.alpaca.common.packet;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import fiskfille.alpaca.Alpaca;

public class PacketManager
{
    public static SimpleNetworkWrapper networkWrapper;
    private static int packetId = 0;
    
    public static void registerPackets()
    {
        networkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel(Alpaca.modid);
        
        registerPacket(PacketSetCorpseEntity.Handler.class, PacketSetCorpseEntity.class);
        registerPacket(PacketSyncXp.Handler.class, PacketSyncXp.class);
    }
    
    private static <REQ extends IMessage, REPLY extends IMessage> void registerPacket(Class<? extends IMessageHandler<REQ, REPLY>> messageHandler, Class<REQ> requestMessageType)
    {
        networkWrapper.registerMessage(messageHandler, requestMessageType, packetId++, Side.CLIENT);
        networkWrapper.registerMessage(messageHandler, requestMessageType, packetId++, Side.SERVER);
    }
}