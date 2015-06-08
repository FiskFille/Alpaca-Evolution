package fiskfille.alpaca.common.event;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import cpw.mods.fml.common.eventhandler.Cancelable;

@Cancelable
public class AlpacaGetColorEvent extends PlayerEvent
{
    public int color;
    
    public AlpacaGetColorEvent(EntityPlayer player, int color)
    {
        super(player);
        this.color = color;
    }
}
