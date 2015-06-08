package fiskfille.alpaca.common.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fiskfille.alpaca.common.data.AlpacaModels;

public class EntityTongue extends EntityThrowable
{
	public EntityPlayer player;
	public Entity grabbedEntity;
	
	public EntityTongue(World world)
    {
        super(world);
        ignoreFrustumCheck = true;
        setSize(0.25F, 0.25F);
    }
	
	public EntityTongue(World world, EntityPlayer entity)
    {
        super(world, entity);
        ignoreFrustumCheck = true;
        setSize(0.25F, 0.25F);
    }
    
    public EntityTongue(World world, double x, double y, double z)
    {
        super(world, x, y, z);
        ignoreFrustumCheck = true;
        setSize(0.25F, 0.25F);
    }
    
    public void onUpdate()
    {
        super.onUpdate();
        
        if (player != null)
        {
        	if (getDistanceToEntity(player) > 3 && ticksExisted > 20)
        	{
        		motionX = (player.posX - posX) / 10;
        		motionY = (player.posY - posY) / 10;
        		motionZ = (player.posZ - posZ) / 10;
        		moveEntity(motionX, motionY, motionZ);
        	}
        	
        	if (grabbedEntity != null)
        	{
        		grabbedEntity.motionX = (posX - grabbedEntity.posX) / 2;
        		grabbedEntity.motionY = (posY - grabbedEntity.posY) / 2;
        		grabbedEntity.motionZ = (posZ - grabbedEntity.posZ) / 2;
        		grabbedEntity.fallDistance = 0;
        	}
        	
        	if (!AlpacaModels.isAlpaca(player))
        	{
        		setDead();
        	}
        }
        else if (ticksExisted > 2)
        {
        	setDead();
        }
    }
    
    protected float getGravityVelocity()
    {
        return 0.01F;
    }
    
    protected float func_70182_d()
    {
        return 0.4F;
    }
	
    @SideOnly(Side.CLIENT)
    public boolean isInRangeToRenderDist(double p_70112_1_)
    {
        double d1 = boundingBox.getAverageEdgeLength() * 4.0D;
        d1 *= 64.0D;
        return p_70112_1_ < d1 * d1;
    }

    @SideOnly(Side.CLIENT)
    public float getShadowSize()
    {
        return 0.0F;
    }
    
    protected void onImpact(MovingObjectPosition mop)
    {
    	setVelocity(0, 0, 0);
    	
    	if (mop.entityHit != null && player != null && !mop.entityHit.getUniqueID().toString().equals(player.getUniqueID().toString()) && grabbedEntity == null)
    	{
    		grabbedEntity = mop.entityHit;
    		mop.entityHit.attackEntityFrom(DamageSource.causePlayerDamage(player), 0.0F);
    	}
    }
}