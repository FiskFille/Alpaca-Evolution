package fiskfille.alpaca.common.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import fiskfille.alpaca.common.potion.AlpacaPotions;

public class ItemFace extends Item
{
    public ItemFace()
    {
        setCreativeTab(CreativeTabs.tabFood);
    }

    public ItemStack onEaten(ItemStack itemstack, World world, EntityPlayer player)
    {
        if (!player.capabilities.isCreativeMode)
        {
            --itemstack.stackSize;
        }

        if (!world.isRemote)
        {
        	int seconds = 30;
        	int minutes = 7;
        	player.addPotionEffect(new PotionEffect(AlpacaPotions.potionAlpaca.id, 20 * seconds + 20 * 60 * minutes, 0));
        }

        return itemstack;
    }
    
    public int getMaxItemUseDuration(ItemStack itemstack)
    {
        return 32;
    }
    
    public EnumAction getItemUseAction(ItemStack itemstack)
    {
        return EnumAction.eat;
    }
    
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player)
    {
        player.setItemInUse(itemstack, getMaxItemUseDuration(itemstack));
        return itemstack;
    }
}