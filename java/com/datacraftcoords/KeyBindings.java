package com.datacraftcoords;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;

import org.lwjgl.input.Keyboard;
public class KeyBindings {
    public static KeyBinding varChat;
    public static KeyBinding varEnabled;
    public static KeyBinding varChunk;
    public static KeyBinding varPotions;
    public static KeyBinding varZoom;
    public static void init() {
    	varChat = new KeyBinding("key.varChat", Keyboard.KEY_B, "key.categories.myCoordMod");//"key.categories.yourMod");
	    ClientRegistry.registerKeyBinding(varChat);
	    varEnabled = new KeyBinding("key.varEnabled", Keyboard.KEY_N, "key.categories.myCoordMod");
	    ClientRegistry.registerKeyBinding(varEnabled);
	    varChunk = new KeyBinding("key.varChunk", Keyboard.KEY_M, "key.categories.myCoordMod");
	    ClientRegistry.registerKeyBinding(varChunk);
	    varPotions = new KeyBinding("key.varPotions", Keyboard.KEY_L, "key.categories.myCoordMod");
	    ClientRegistry.registerKeyBinding(varPotions);
	    varZoom = new KeyBinding("key.varZoom", Keyboard.KEY_F, "key.categories.myCoordMod");
	    ClientRegistry.registerKeyBinding(varZoom);
	    
    }
}