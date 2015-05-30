package fiskfille.alpaca;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.EntityRenderer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class AlpacaReflection
{
    public static Method renderHandMethod;
    public static Field textureOffsetXField;
    public static Field textureOffsetYField;

    @SideOnly(Side.CLIENT)
    public static void client()
    {
        for (Method method : EntityRenderer.class.getDeclaredMethods())
        {
            if (method.getName().equals("renderHand") || method.getName().equals("func_78476_b"))
            {
                method.setAccessible(true);
                renderHandMethod = method;
            }
        }

        for (Field field : ModelRenderer.class.getFields())
        {
            if (field.getName().equals("textureOffsetX") || field.getName().equals("field_78803_o"))
            {
                field.setAccessible(true);
                textureOffsetXField = field;
            }
            else if (field.getName().equals("textureOffsetY") || field.getName().equals("field_78813_p"))
            {
                field.setAccessible(true);
                textureOffsetYField = field;
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

    public static int getTextureOffsetX(ModelRenderer modelrenderer)
    {
        try
        {
            return textureOffsetXField.getInt(modelrenderer);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return 0;
    }

    public static int getTextureOffsetY(ModelRenderer modelrenderer)
    {
        try
        {
            return textureOffsetYField.getInt(modelrenderer);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return 0;
    }
}