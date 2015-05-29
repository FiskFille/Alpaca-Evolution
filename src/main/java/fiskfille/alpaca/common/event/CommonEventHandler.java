package fiskfille.alpaca.common.event;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import fiskfille.alpaca.common.data.DataManager;
import fiskfille.alpaca.common.entity.EntityCorpse;
import fiskfille.alpaca.common.packet.PacketManager;
import fiskfille.alpaca.common.packet.PacketSetCorpseEntity;

public class CommonEventHandler
{
	@SubscribeEvent
	public void onEntityInteract(EntityInteractEvent event)
	{
		EntityPlayer player = event.entityPlayer;
		Entity target = event.target;
		
		if (target != null && target.isEntityAlive() && target instanceof EntityCorpse)
		{
			event.setCanceled(true);
			EntityCorpse corpse = (EntityCorpse)target;
			
			corpse.onDeath(DamageSource.causePlayerDamage(player));
			player.swingItem();
			player.playSound("random.eat", 1, 1);
//			player.playSound("random.burp", 1, 1);
			
			int i;

	        if (!corpse.worldObj.isRemote)
	        {
	            i = 2;
	            
	            while (i > 0)
	            {
	                int j = EntityXPOrb.getXPSplit(i);
	                i -= j;
	                corpse.worldObj.spawnEntityInWorld(new EntityXPOrb(corpse.worldObj, corpse.posX, corpse.posY, corpse.posZ, j));
	            }
	        }
			
			DataManager.setEntitiesEaten(player, DataManager.getEntitiesEaten(player) + 1);
		}
	}
	
	@SubscribeEvent
	public void onLivingDeath(LivingDeathEvent event)
	{
		EntityLivingBase entity = event.entityLiving;
		World world = entity.worldObj;
		
		if (shouldLeaveCorpse(entity))
		{
			EntityCorpse corpse = new EntityCorpse(world);
			corpse.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, entity.rotationYaw, entity.rotationPitch);
			
			if (!world.isRemote)
			{
				world.spawnEntityInWorld(corpse);
			}
			
			if (world.isRemote)
	        {
	            PacketManager.networkWrapper.sendToServer(new PacketSetCorpseEntity(corpse, entity));
	        }
	        else
	        {
	            PacketManager.networkWrapper.sendToAllAround(new PacketSetCorpseEntity(corpse, entity), new TargetPoint(corpse.dimension, corpse.posX, corpse.posY, corpse.posZ, 256));
	        }
		}
	}
	
	@SubscribeEvent
	public void onLivingDrops(LivingDropsEvent event)
	{
		EntityLivingBase entity = event.entityLiving;
		World world = entity.worldObj;
		
		if (shouldLeaveCorpse(entity))
		{
			entity.setLocationAndAngles(0, 0, 0, 0, 0);
		}
	}
	
	public boolean shouldLeaveCorpse(EntityLivingBase entity)
	{
		return !(entity instanceof EntityCorpse); 
	}
}