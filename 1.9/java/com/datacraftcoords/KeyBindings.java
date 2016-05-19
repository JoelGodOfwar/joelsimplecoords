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
    public static KeyBinding varSafe;
    public static KeyBinding varLight;
    public static KeyBinding varDist;
    public static KeyBinding varLeft;
    public static KeyBinding varTop;
    public static KeyBinding varRight;
    public static KeyBinding varArmor;
    public static KeyBinding varNight;
    
    public static void init() {
    	varChat = new KeyBinding("key.varChat", Keyboard.KEY_B, "key.categories.myCoordMod");//"key.categories.yourMod");
	    ClientRegistry.registerKeyBinding(varChat);
	    varEnabled = new KeyBinding("key.varEnabled", Keyboard.KEY_N, "key.categories.myCoordMod");
	    ClientRegistry.registerKeyBinding(varEnabled);
	    varChunk = new KeyBinding("key.varChunk", Keyboard.KEY_M, "key.categories.myCoordMod");
	    ClientRegistry.registerKeyBinding(varChunk);
	    varPotions = new KeyBinding("key.varPotions", Keyboard.KEY_P, "key.categories.myCoordMod");
	    ClientRegistry.registerKeyBinding(varPotions);
	    varZoom = new KeyBinding("key.varZoom", Keyboard.KEY_C, "key.categories.myCoordMod");
	    ClientRegistry.registerKeyBinding(varZoom);
	    varLight = new KeyBinding("key.varLight", Keyboard.KEY_L, "key.categories.myCoordMod");
	    ClientRegistry.registerKeyBinding(varLight);
	    varDist = new KeyBinding("key.varDist", Keyboard.KEY_PERIOD, "key.categories.myCoordMod");
	    ClientRegistry.registerKeyBinding(varDist);
	    varLeft = new KeyBinding("key.varLeft", Keyboard.KEY_NUMPAD4, "key.categories.myCoordMod");
	    ClientRegistry.registerKeyBinding(varLeft);
	    varTop = new KeyBinding("key.varTop", Keyboard.KEY_NUMPAD8, "key.categories.myCoordMod");
	    ClientRegistry.registerKeyBinding(varTop);
	    varRight = new KeyBinding("key.varRight", Keyboard.KEY_NUMPAD6, "key.categories.myCoordMod");
	    ClientRegistry.registerKeyBinding(varRight);
	    varArmor = new KeyBinding("key.varArmor", Keyboard.KEY_K, "key.categories.myCoordMod");
	    ClientRegistry.registerKeyBinding(varArmor);
	    varNight = new KeyBinding("key.varNight", Keyboard.KEY_DOWN, "key.categories.myCoordMod");
	    ClientRegistry.registerKeyBinding(varNight);
    }
}