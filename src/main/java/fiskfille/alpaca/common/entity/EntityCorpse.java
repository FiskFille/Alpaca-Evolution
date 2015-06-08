package fiskfille.alpaca.common.entity;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fiskfille.alpaca.common.packet.PacketManager;
import fiskfille.alpaca.common.packet.PacketSyncXp;

public class EntityCorpse extends EntityLiving
{
    public EntityLivingBase entity;
    public int experienceValue;

    public EntityCorpse(World world)
    {
        super(world);
    }

    public boolean isAIEnabled()
    {
        return false;
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(1.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.0D);
    }

    public boolean isEntityInvulnerable()
    {
        return ticksExisted <= 20;
    }
    
    @SideOnly(Side.CLIENT)
    public float getShadowSize()
    {
    	return 0.0F;
    }

    public void onUpdate()
    {
        if (entity == null && worldObj.isRemote)
        {
            setDead();
        }

        super.onUpdate();

        if (entity != null)
        {
            setSize(entity.height, entity.width);

            if (entity.deathTime < 20)
            {
                entity.deathTime += 2;
            }
        }

        if (ticksExisted > 2400)
        {
            setDead();
        }
    }

    protected float getSoundPitch()
    {
        return super.getSoundPitch() * 0.75F;
    }

    protected int getExperiencePoints(EntityPlayer p_70693_1_)
    {
        if (this.experienceValue > 0)
        {
            int i = this.experienceValue;
            ItemStack[] aitemstack = this.getLastActiveItems();

            for (int j = 0; j < aitemstack.length; ++j)
            {
                if (aitemstack[j] != null && this.equipmentDropChances[j] <= 1.0F)
                {
                    i += 1 + this.rand.nextInt(3);
                }
            }

            return i;
        }
        else
        {
            return this.experienceValue;
        }
    }

    public void onDeath(DamageSource damagesource)
    {
        super.onDeath(damagesource);

        if (entity instanceof EntityLiving)
        {
            entity.onDeath(damagesource);
            int xp = (Integer) ObfuscationReflectionHelper.getPrivateValue(EntityLiving.class, (EntityLiving) entity, "experienceValue", "field_70728_aV");
            xp = Math.max(xp, 2);

            if (entity.worldObj.isRemote)
            {
                PacketManager.networkWrapper.sendToServer(new PacketSyncXp(this, xp));
            }
            else
            {
                PacketManager.networkWrapper.sendToAllAround(new PacketSyncXp(this, xp), new TargetPoint(dimension, posX, posY, posZ, 256));
            }

            experienceValue = xp;
        }

        int i;

        if (!this.worldObj.isRemote && (this.recentlyHit > 0 || this.isPlayer()) && this.func_146066_aG() && this.worldObj.getGameRules().getGameRuleBooleanValue("doMobLoot"))
        {
            i = this.getExperiencePoints(this.attackingPlayer);

            while (i > 0)
            {
                int j = EntityXPOrb.getXPSplit(i);
                i -= j;
                this.worldObj.spawnEntityInWorld(new EntityXPOrb(this.worldObj, this.posX, this.posY, this.posZ, j));
            }
        }
    }

    public void setDead()
    {
        super.setDead();

        if (entity != null)
        {
            entity.setDead();

            if (entity.deathTime < 20)
            {
                entity.deathTime += 2;
            }
            if (entity.deathTime > 20)
            {
                entity.deathTime = 20;
            }
        }
    }

    public void writeEntityToNBT(NBTTagCompound nbt)
    {
        super.writeEntityToNBT(nbt);
        NBTTagCompound nbttagcompound = new NBTTagCompound();

        if (entity != null)
        {
            entity.writeEntityToNBT(nbttagcompound);
        }

        nbt.setTag("Entity", nbttagcompound);
    }

    public void readEntityFromNBT(NBTTagCompound nbt)
    {
        super.readEntityFromNBT(nbt);
        entity = new EntityLiving(null)
        {
        };
        entity.readEntityFromNBT(nbt.getCompoundTag("Entity"));
    }
}