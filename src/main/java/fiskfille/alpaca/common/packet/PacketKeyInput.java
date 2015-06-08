package fiskfille.alpaca.common.packet;

import fiskfille.alpaca.Alpaca;
import fiskfille.alpaca.client.keybinds.AlpacaKeyBinds;
import fiskfille.alpaca.common.entity.EntityTongue;
import fiskfille.alpaca.common.event.ClientEventHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketKeyInput implements IMessage
{
	public int key;

	public PacketKeyInput()
	{

	}
	
	public PacketKeyInput(int key)
	{
		this.key = key;
	}

	public void fromBytes(ByteBuf buf)
	{
		key = buf.readInt();
	}

	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(key);
	}

	public static class Handler implements IMessageHandler<PacketKeyInput, IMessage>
	{
		public IMessage onMessage(PacketKeyInput message, MessageContext ctx)
		{
			EntityPlayer player = getPlayer(ctx);

            if (player != null)
            {
            	if (message.key == AlpacaKeyBinds.keyBindingLick.getKeyCode())
            	{
            		World world = player.worldObj;
            		EntityTongue entity = new EntityTongue(world, player);
                	
            		if (ClientEventHandler.getToungeEntities(player).size() > 0)
            		{
            			ClientEventHandler.killAllToungeEntitiesForPlayer(player);
            			world.playSoundAtEntity(entity, "mob.slime.small", 1.0F, 0.6F);
            		}
            		else
            		{
            			if (!world.isRemote)
            			{
            				world.spawnEntityInWorld(entity);
            			}
            			
            			world.playSoundAtEntity(entity, "mob.slime.attack", 1.0F, 1.1F);
            		}
            		
            		PacketManager.networkWrapper.sendToServer(new PacketLick(entity, player));
        			PacketManager.networkWrapper.sendToAllAround(new PacketLick(entity, player), new TargetPoint(player.dimension, player.posX, player.posY, player.posZ, 32));
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