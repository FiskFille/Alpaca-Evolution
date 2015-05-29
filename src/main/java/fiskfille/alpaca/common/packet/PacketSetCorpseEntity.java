package fiskfille.alpaca.common.packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import fiskfille.alpaca.Alpaca;
import fiskfille.alpaca.common.entity.EntityCorpse;
import fiskfille.alpaca.common.entity.EntityHelper;

public class PacketSetCorpseEntity implements IMessage
{
	public int corpseId;
	public int entityId;

	public PacketSetCorpseEntity()
	{
		
	}

	public PacketSetCorpseEntity(EntityCorpse corpse, EntityLivingBase entity)
	{
		corpseId = corpse.getEntityId();
		entityId = entity.getEntityId();
	}

    public void fromBytes(ByteBuf buf)
    {
        corpseId = buf.readInt();
        entityId = buf.readInt();
    }

    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(corpseId);
        buf.writeInt(entityId);
    }

    public static class Handler implements IMessageHandler<PacketSetCorpseEntity, IMessage>
    {
        public IMessage onMessage(PacketSetCorpseEntity message, MessageContext ctx)
        {
        	EntityPlayer player = getPlayer(ctx);
            
            if (player != null)
            {
                EntityLivingBase entity = (EntityLivingBase)player.worldObj.getEntityByID(message.entityId);
                Entity entity1 = player.worldObj.getEntityByID(message.corpseId);
                
                if (entity1 instanceof EntityCorpse)
                {
                	EntityCorpse corpse = (EntityCorpse)entity1;
//                	corpse.entity = (EntityLivingBase)EntityHelper.getEntityFromClass(entity.getClass(), corpse.worldObj);
                	corpse.entity = entity;
                	corpse.entity.deathTime = 0;
                	corpse.setVelocity(entity.motionX, entity.motionY, entity.motionZ);
//                	corpse.entity.setLocationAndAngles(0, 0, 0, entity.rotationYaw, entity.rotationPitch);
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