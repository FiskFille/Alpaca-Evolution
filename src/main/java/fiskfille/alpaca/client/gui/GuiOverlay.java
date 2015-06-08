package fiskfille.alpaca.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fiskfille.alpaca.Alpaca;
import fiskfille.alpaca.AlpacaAPI;
import fiskfille.alpaca.common.data.DataManager;

public class GuiOverlay extends Gui
{
    private Minecraft mc;
    private RenderItem itemRenderer;
    public static final ResourceLocation texture = new ResourceLocation(Alpaca.modid, "textures/gui/icons.png");
    
    public static double speed;
    
    public GuiOverlay(Minecraft mc)
    {
        super();
        this.mc = mc;
        this.itemRenderer = new RenderItem();
    }
    
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onRender(RenderGameOverlayEvent.Pre event)
    {
        if (!event.isCanceled())
        {
            int width = event.resolution.getScaledWidth();
            int height = event.resolution.getScaledHeight();
            EntityPlayer player = mc.thePlayer;
            
            if (event.type == ElementType.HOTBAR)
            {
                renderMomentum(event, width, height, player);
            }
        }
    }
    
    public void renderMomentum(RenderGameOverlayEvent.Pre event, int width, int height, EntityPlayer player)
    {
    	if (AlpacaAPI.isAlpaca(player))
        {
    		int momentum = DataManager.getMomentum(player);
    		int x = 6;
    		int y = height - 16;
    		int i = Math.min(momentum / 2, 100);
    		String s = (float)momentum / 40 + "";
    		s = s.substring(0, s.indexOf(".") + 2);
            
    		mc.getTextureManager().bindTexture(texture);
    		drawTexturedModalRect(x, y, 0, 10, 100, 10);
    		drawTexturedModalRect(x, y, 0, 0, i, 10);    		
    		
            drawCenteredString(mc.fontRenderer, "Momentum: " + s + "s", x + 50, y + 1, 0xffffff);
        }
    }
}