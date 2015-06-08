package fiskfille.alpaca;

import java.lang.reflect.Method;

import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class AlpacaReflection
{
    public static Method renderHandMethod;
    public static Method renderEquippedItemsMethod;
    public static Method getColorMultiplierMethod;

    @SideOnly(Side.CLIENT)
    public static void client()
    {
        renderHandMethod = getMethod(EntityRenderer.class, "renderHand", "func_78476_b");
        renderEquippedItemsMethod = getMethod(RendererLivingEntity.class, "renderEquippedItems");
        getColorMultiplierMethod = getMethod(RendererLivingEntity.class, "getColorMultiplier", "func_77030_a");
    }
    
    public static Method getMethod(Class clazz, String... names)
    {
    	for (Method method : clazz.getDeclaredMethods())
        {
    		for (String name : names)
    		{
    			if (method.getName().equals(name))
                {
                	method.setAccessible(true);
                	return method;
                }
    		}
        }
    	
    	return null;
    }

    public static void common()
    {

    }

    public static void renderHand(EntityRenderer obj, float f, int i)
    {
        try
        {
            renderHandMethod.invoke(obj, f, i);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public static void renderEquippedItems(RendererLivingEntity obj, EntityPlayer player, float f)
    {
        try
        {
        	renderEquippedItemsMethod.invoke(obj, player, f);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public static int getColorMultiplier(RendererLivingEntity obj, EntityPlayer player, float f, float f1)
    {
        try
        {
        	return (Integer)getColorMultiplierMethod.invoke(obj, player, f, f1);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        return 0;
    }
}