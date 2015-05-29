package fiskfille.alpaca;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import net.minecraft.client.renderer.EntityRenderer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class AlpacaReflection
{
	public static Method renderHandMethod;
	
	@SideOnly(Side.CLIENT)
	public static void client()
	{
		for (Method method : EntityRenderer.class.getDeclaredMethods())
        {
            if (method.getName().equals("renderHand") || method.getName().equals("func_78476_b"))
            {
                method.setAccessible(true);
                renderHandMethod = method;
                break;
            }
        }
	}
	
	public static void common()
	{
		
	}
	
	public static void renderHand(EntityRenderer entityRenderer, float f, int i)
	{
		try
		{
			renderHandMethod.invoke(entityRenderer, f, i);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}