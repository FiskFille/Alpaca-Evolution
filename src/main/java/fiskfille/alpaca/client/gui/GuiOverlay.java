package fiskfille.alpaca.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fiskfille.alpaca.common.data.DataManager;

@SideOnly(Side.CLIENT)
public class GuiOverlay extends Gui
{
    // private ResourceLocation freddyHeadTexture = new ResourceLocation(FNAF.modid, "textures/misc/freddy_blur.png");

    private Minecraft mc;
    private RenderItem itemRenderer;

    public GuiOverlay(Minecraft mc)
    {
        super();
        this.mc = mc;
        this.itemRenderer = new RenderItem();
    }

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent.Pre event)
    {
        int width = event.resolution.getScaledWidth();
        int height = event.resolution.getScaledHeight();
        EntityPlayer player = mc.thePlayer;

        renderEntitiesEaten(event, width, height, player);

        mc.getTextureManager().bindTexture(icons);
    }

    public void renderEntitiesEaten(RenderGameOverlayEvent.Pre event, int width, int height, EntityPlayer player)
    {
        int i = DataManager.getEntitiesEaten(player);

        // mc.getTextureManager().bindTexture(DataManager.currentAnimatronic.getTexture(null));
    }
}