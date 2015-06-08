package fiskfille.alpaca.common.packet;

import fiskfille.alpaca.Alpaca;
import fiskfille.alpaca.common.entity.EntityTongue;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketLick implements IMessage
{
	public int entityId;
	public int playerId;

	public PacketLick()
	{

	}

	public PacketLick(EntityTongue entity, EntityPlayer player)
	{
		entityId = entity.getEntityId();
		playerId = player.getEntityId();
	}

	public void fromBytes(ByteBuf buf)
	{
		entityId = buf.readInt();
		playerId = buf.readInt();
	}

	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(entityId);
		buf.writeInt(playerId);
	}

	public static class Handler implements IMessageHandler<PacketLick, IMessage>
	{
		public IMessage onMessage(PacketLick message, MessageContext ctx)
		{
			EntityPlayer player = getPlayer(ctx);

            if (player != null)
            {
                EntityTongue from = (EntityTongue)player.worldObj.getEntityByID(message.entityId);
                EntityPlayer target = (EntityPlayer)player.worldObj.getEntityByID(message.playerId);
                
                if (from != null)
                {
                	from.player = target;
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