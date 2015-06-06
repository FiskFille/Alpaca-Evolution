package fiskfille.alpaca.client.keybinds;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.registry.ClientRegistry;

public class AlpacaKeyBinds
{
	public static AlpacaKeyBinding keyBindingOptions = new AlpacaKeyBinding("Mod Options", Keyboard.KEY_O);

	public static void register()
	{
		ClientRegistry.registerKeyBinding(keyBindingOptions);
	}
}