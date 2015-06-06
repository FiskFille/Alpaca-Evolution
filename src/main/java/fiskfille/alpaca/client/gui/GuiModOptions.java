package fiskfille.alpaca.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.EnumChatFormatting;
import fiskfille.alpaca.client.file.ModOptions;

public class GuiModOptions extends GuiScreen
{
	public void initGui()
	{
		int y = height / 2 - 40;
		this.buttonList.add(new GuiButton(0, width / 2 - 100, y + 25, 200, 20, "Done"));
		this.buttonList.add(new GuiButton(1, width / 2 - 100, y, 200, 20, "Force-Render: " + (ModOptions.forceRender ? EnumChatFormatting.GREEN + "On" : EnumChatFormatting.RED + "Off")));
	}
	
	protected void actionPerformed(GuiButton par1GuiButton)
	{
		int id = par1GuiButton.id;
		
		if (id == 0)
		{
			mc.thePlayer.closeScreen();
		}
		if (id == 1)
		{
			ModOptions.forceRender = !ModOptions.forceRender;
			mc.displayGuiScreen(this);
		}
	}
	
	public void onGuiClosed()
	{
		ModOptions.save();
	}
	
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		drawDefaultBackground();
		drawCenteredString(fontRendererObj, "Alpaca Evolution Mod Options", width / 2, 20, 0xFFFFFF);
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
}