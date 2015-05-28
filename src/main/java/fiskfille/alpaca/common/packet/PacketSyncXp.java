package fiskfille.alpaca.common.packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import fiskfille.alpaca.Alpaca;
import fiskfille.alpaca.common.entity.EntityCorpse;

public class PacketSyncXp implements IMessage
{
    public int id;
    public int xp;

    public PacketSyncXp()
    {

    }

    public PacketSyncXp(EntityCorpse entity, int experience)
    {
        id = entity.getEntityId();
        xp = experience;
    }

    public void fromBytes(ByteBuf buf)
    {
        id = buf.readInt();
        xp = buf.readInt();
    }

    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(id);
        buf.writeInt(xp);
    }

    public static class Handler implements IMessageHandler<PacketSyncXp, IMessage>
    {
        public IMessage onMessage(PacketSyncXp message, MessageContext ctx)
        {
            EntityPlayer player = getPlayer(ctx);

            if (player != null)
            {
                Entity entity = player.worldObj.getEntityByID(message.id);

                if (entity instanceof EntityCorpse)
                {
                    EntityCorpse corpse = (EntityCorpse) entity;
                    corpse.experienceValue = message.xp;
                }
            }

            return null;
        }

        public EntityPlayer getPlayer(MessageContext ctx)
        {
            if (ctx.side.isClient())
            {
                return Alpaca.proxy.getPlayer();
            }
            else
            {
                return ctx.getServerHandler().playerEntity;
            }
        }
    }
}