package fiskfille.alpaca.common.event;

import java.util.Random;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.FoodStats;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.event.entity.player.PlayerUseItemEvent;
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

            DataManager.setEntitiesEaten(player, DataManager.getEntitiesEaten(player) + 1);
        }
    }

    @SubscribeEvent
    public void onLivingDeath(LivingDeathEvent event)
    {
        EntityLivingBase entity = event.entityLiving;

        if (entity != null && entity.worldObj != null && shouldLeaveCorpse(entity))
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

        if (shouldLeaveCorpse(entity))
        {
            entity.setLocationAndAngles(0, 0, 0, 0, 0);
        }
    }

    @SubscribeEvent
    public void onUseItem(PlayerUseItemEvent.Finish event)
    {
        if (event.item.getItem() instanceof ItemFood)
        {
            ItemFood item = (ItemFood) event.item.getItem();
            FoodStats food = event.entityPlayer.getFoodStats();
            food.addStats(-item.func_150905_g(event.item), -item.func_150906_h(event.item));
        }
    }

    @SubscribeEvent
    public void onArrowLoose(ArrowLooseEvent event)
    {
        event.setCanceled(true);
        EntityPlayer player = event.entityPlayer;
        World world = player.worldObj;
        ItemStack itemstack = event.bow;
        Random itemRand = new Random();

        int j = event.charge;
        boolean flag = player.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, itemstack) > 0;

        if (flag || player.inventory.hasItem(Items.arrow))
        {
            float f = (float) j / 20.0F;
            f = (f * f + f * 2.0F) / 3.0F;

            if ((double) f < 0.1D)
            {
                return;
            }

            if (f > 1.0F)
            {
                f = 1.0F;
            }

            EntityArrow entityarrow = new EntityArrow(world, player, f * 2.0F);

            if (f == 1.0F)
            {
                entityarrow.setIsCritical(true);
            }

            int k = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, itemstack);

            if (k > 0)
            {
                entityarrow.setDamage(entityarrow.getDamage() + (double) k * 0.5D + 0.5D);
            }

            int l = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, itemstack);

            if (l > 0)
            {
                entityarrow.setKnockbackStrength(l);
            }

            if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, itemstack) > 0)
            {
                entityarrow.setFire(100);
            }

            itemstack.damageItem(1, player);
            world.playSoundAtEntity(player, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);

            if (flag)
            {
                entityarrow.canBePickedUp = 2;
            }
            else
            {
                player.inventory.consumeInventoryItem(Items.arrow);
            }

            entityarrow.posY -= 0.5F;

            if (!world.isRemote)
            {
                world.spawnEntityInWorld(entityarrow);
            }
        }
    }

    public boolean shouldLeaveCorpse(EntityLivingBase entity)
    {
        return !(entity instanceof EntityCorpse);
    }
}