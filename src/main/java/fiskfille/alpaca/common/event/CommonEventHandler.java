package fiskfille.alpaca.common.event;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.FoodStats;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
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
        
        if (entity instanceof EntityVillager)
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
    		// TODO: Wall-jumping
    		
        	if (player.isSprinting())
        	{
        		DataManager.setMomentum(player, ++momentum);
        		
        		if (player.isCollidedHorizontally)
        		{
//        			player.motionY += 1;
//        			player.rotationPitch = -70;
        		}
        	}
        	else 
        	{
        		DataManager.setMomentum(player, 0);
        	}
        	
//        	System.out.println(momentum + "");
    	}
    }

    public static boolean shouldLeaveCorpse(EntityLivingBase entity)
    {
    	return !(entity instanceof EntityCorpse || entity instanceof IBossDisplayData);
    }
}