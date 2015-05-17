package com.datacraftcoords;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;

import org.lwjgl.input.Keyboard;
public class KeyBindings {
    public static KeyBinding varChat;
    public static KeyBinding varEnabled;
    public static void init() {
    	varChat = new KeyBinding("key.varChat", Keyboard.KEY_X, "key.categories.myCoordMod");//"key.categories.yourMod");
	    ClientRegistry.registerKeyBinding(varChat);
	    varEnabled = new KeyBinding("key.varEnabled", Keyboard.KEY_L, "key.categories.myCoordMod");
	    ClientRegistry.registerKeyBinding(varEnabled);
    }
}