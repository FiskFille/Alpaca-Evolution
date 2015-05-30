package fiskfille.alpaca.client.model.entity;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;

public abstract class ModelAlpacaBase extends ModelBiped
{
    public abstract ModelRenderer getArm();

    public abstract ModelRenderer getNeck();
}