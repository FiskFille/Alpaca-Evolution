package fiskfille.alpaca.common.event;

import java.util.List;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.FoodStats;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.event.entity.player.PlayerUseItemEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import fiskfille.alpaca.common.data.AlpacaModels;
import fiskfille.alpaca.common.data.DataManager;
import fiskfille.alpaca.common.entity.EntityCorpse;
import fiskfille.alpaca.common.item.AlpacaItems;
import fiskfille.alpaca.common.packet.PacketManager;
import fiskfille.alpaca.common.packet.PacketSetCorpseEntity;

public class CommonEventHandler
{
    @SubscribeEvent
    public void onEntityInteract(EntityInteractEvent event)
    {
        EntityPlayer player = event.entityPlayer;
        Entity target = event.target;

        if (AlpacaModels.isAlpaca(player) && target != null && target.isEntityAlive() && target instanceof EntityCorpse)
        {
            event.setCanceled(true);
            target.setDead();
            EntityCorpse corpse = (EntityCorpse) target;

            corpse.onDeath(DamageSource.causePlayerDamage(player));
            player.swingItem();
            player.playSound("random.eat", 1, 1);
            player.playSound("random.burp", 1, 1);
            player.getFoodStats().addStats(4, 1.0F);

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
        }
    }

    @SubscribeEvent
    public void onLivingDeath(LivingDeathEvent event)
    {
        EntityLivingBase entity = event.entityLiving;

        if (entity != null && entity.worldObj != null && shouldLeaveCorpse(entity) && event.source.getEntity() instanceof EntityPlayer && AlpacaModels.isAlpaca((EntityPlayer)event.source.getEntity()))
        {
            World world = entity.worldObj;
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
        
        if (entity instanceof EntityVillager || entity instanceof EntityPlayer)
        {
        	event.drops.add(new EntityItem(entity.worldObj, entity.posX, entity.posY, entity.posZ, new ItemStack(AlpacaItems.face)));
        }
        
        if (shouldLeaveCorpse(entity) && event.source.getEntity() instanceof EntityPlayer && AlpacaModels.isAlpaca((EntityPlayer)event.source.getEntity()))
        {
            entity.setLocationAndAngles(0, 0, 0, 0, 0);
        }
    }

    @SubscribeEvent
    public void onUseItem(PlayerUseItemEvent.Finish event)
    {
        if (AlpacaModels.isAlpaca(event.entityPlayer) && event.item.getItem() instanceof ItemFood)
        {
            ItemFood item = (ItemFood) event.item.getItem();
            FoodStats food = event.entityPlayer.getFoodStats();
            food.addStats(-item.func_150905_g(event.item), -item.func_150906_h(event.item));
        }
    }
    
    @SubscribeEvent
    public void onPlayerUpdate(PlayerTickEvent event)
    {
    	EntityPlayer player = event.player;
    	int momentum = DataManager.getMomentum(player);
    	
    	if (player.ticksExisted % 2 == 0)
    	{
        	if (player.isSprinting())
        	{
        		DataManager.setMomentum(player, ++momentum);
        	}
        	else 
        	{
        		DataManager.setMomentum(player, 0);
        	}
        	
        	if (!AlpacaModels.isAlpaca(player))
        	{
        		DataManager.setMomentum(player, 0);
        		player.stepHeight = 0.5F;
        	}
        	else
        	{
        		player.stepHeight = 1.0F;
        	}
    	}
    }
    
    @SubscribeEvent
    public void onLivingJump(LivingJumpEvent event)
    {
    	if (event.entity instanceof EntityPlayer)
    	{
    		EntityPlayer player = (EntityPlayer)event.entity;
    		
    		float f = (float)DataManager.getMomentum(player) / 200;
    		f = Math.min(f, 0.5F);
    		player.motionY += f;
    		DataManager.setMomentum(player, 0);
    	}
    }
    
    @SubscribeEvent
    public void onLivingFall(LivingFallEvent event)
    {
    	if (event.entity instanceof EntityPlayer)
    	{
    		EntityPlayer player = (EntityPlayer)event.entity;
    		event.distance /= 1.75F;
    	}
    }
    
    @SubscribeEvent
    public void onLivingHurt(LivingHurtEvent event)
    {
    	if (event.source.getEntity() instanceof EntityPlayer)
    	{
    		EntityPlayer player = (EntityPlayer)event.source.getEntity();
    		EntityLivingBase entity = event.entityLiving;
    		
    		Vec3 frontCoords = getFrontCoords(entity, player, (float)DataManager.getMomentum(player) / 40, true);
			entity.motionX = (frontCoords.xCoord - entity.posX);
			entity.motionY = (frontCoords.yCoord - entity.posY);
			entity.motionZ = (frontCoords.zCoord - entity.posZ);
			event.ammount += (float)DataManager.getMomentum(player) / 20;
			
			DataManager.setMomentum(player, 0);
			player.swingItem();
    	}
    }
    
    public static List<EntityLivingBase> getEntitiesNear(World world, double x, double y, double z, float radius)
	{
		List<EntityLivingBase> list = world.selectEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(x - radius, y - radius, z - radius, x + radius, y + radius, z + radius), IEntitySelector.selectAnything);
		return list;
	}

	public static Vec3 getFrontCoords(EntityLivingBase living, EntityPlayer player, double amount, boolean pitch)
	{
		float f = 1.0F;
		float f1 = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * f;
		float f2 = player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw) * f;

		if (!pitch)
		{
			f1 = 0;
		}

		double d0 = living.prevPosX + (living.posX - living.prevPosX) * (double)f;
		double d1 = living.prevPosY + (living.posY - living.prevPosY) * (double)f;
		double d2 = living.prevPosZ + (living.posZ - living.prevPosZ) * (double)f;
		Vec3 vec3 = Vec3.createVectorHelper(d0, d1, d2);
		float f3 = MathHelper.cos(-f2 * 0.017453292F - (float)Math.PI);
		float f4 = MathHelper.sin(-f2 * 0.017453292F - (float)Math.PI);
		float f5 = -MathHelper.cos(-f1 * 0.017453292F);
		float f6 = MathHelper.sin(-f1 * 0.017453292F);
		float f7 = f4 * f5;
		float f8 = f3 * f5;
		Vec3 vec31 = vec3.addVector(f7 * amount, f6 * amount, f8 * amount);
		return vec31;
	}

    public static boolean shouldLeaveCorpse(EntityLivingBase entity)
    {
    	return !(entity instanceof EntityCorpse || entity instanceof IBossDisplayData);
    }
}