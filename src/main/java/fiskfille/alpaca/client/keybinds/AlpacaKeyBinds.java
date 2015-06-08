package fiskfille.alpaca.client.keybinds;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.registry.ClientRegistry;

public class AlpacaKeyBinds
{
	public static AlpacaKeyBinding keyBindingOptions = new AlpacaKeyBinding("Mod Options", Keyboard.KEY_O);
	public static AlpacaKeyBinding keyBindingLick = new AlpacaKeyBinding("Lick", Keyboard.KEY_C);

	public static void load()
	{
		ClientRegistry.registerKeyBinding(keyBindingOptions);
		ClientRegistry.registerKeyBinding(keyBindingLick);
	}
}
