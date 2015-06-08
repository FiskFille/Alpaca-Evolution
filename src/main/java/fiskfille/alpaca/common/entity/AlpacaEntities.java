package fiskfille.alpaca.common.entity;

public class AlpacaEntities
{
    public static void load()
    {
        EntityHelper.registerEntity("Corpse", EntityCorpse.class);
        EntityHelper.registerEntity("Tounge", EntityTongue.class);
    }
}