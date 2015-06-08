package fiskfille.alpaca.common.potion;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import net.minecraft.potion.Potion;

public class AlpacaPotions
{
	public static final int MAX_SIZE = 128;
	public static Potion potionAlpaca;
	
	public static void load()
	{
		extendPotionListLength();
		
		potionAlpaca = new PotionCustom(58, false, 0x2223BFC2).setIconIndex(-1, -1).setPotionName("potion.alpaca");
	}

	private static void extendPotionListLength()
	{
		Potion[] potionTypes = null;

        for (Field f : Potion.class.getDeclaredFields())
        {
            f.setAccessible(true);

            try
            {
                if (f.getName().equals("potionTypes") || f.getName().equals("field_76425_a"))
                {
                    Field modfield = Field.class.getDeclaredField("modifiers");
                    modfield.setAccessible(true);
                    modfield.setInt(f, f.getModifiers() & ~Modifier.FINAL);

                    potionTypes = (Potion[]) f.get(null);
                    final Potion[] newPotionTypes = new Potion[MAX_SIZE];
                    System.arraycopy(potionTypes, 0, newPotionTypes, 0, potionTypes.length);
                    f.set(null, newPotionTypes);
                }
            }
            catch (Exception e)
            {
                System.err.println("Severe error, please report this to the mod author:");
                System.err.println(e);
            }
        }
	}
}