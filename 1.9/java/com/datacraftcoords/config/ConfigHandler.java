package com.datacraftcoords.config;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import client.gui.GuiItemDurability;

import com.datacraftcoords.Reference;
import com.datacraftcoords.ZoomHandler;
import com.datacraftcoords.event.EventManager;
import com.datacraftcoords.event.PotionTimers;
import client.gui.EnumCorner;

public class ConfigHandler 
{
	public static Configuration configFile;

    public static void init(FMLPreInitializationEvent event)
    {
    	MinecraftForge.EVENT_BUS.register(new ConfigHandler());
        configFile = new Configuration(event.getSuggestedConfigurationFile());
        syncConfig();
    }

    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs) {
        if(eventArgs.getModID().equals(Reference.MOD_ID))
            ConfigHandler.syncConfig();
    }

    public static void syncConfig()
    {
    	
    	EventManager.Enabled = getConfiguration("Enabled", false, "Enable or Disable the display of Coordinates.");
    	EventManager.chunkEnabled = getConfiguration("ChunkEnabled", false, "Enable or Disable the display of Chunk Info.");
        PotionTimers.Enabled = getConfiguration("PotionTimerEnabled", false, "Enable or Disable the display of Potion Timers.");
        ZoomHandler.zoomMultiplier = getConfiguration("zoomMultiplier", -15.0D, "Zooms while key is pressed.");
        //SafeOverlay.Enabled = getConfiguration("SafeEnabled", false, "");
        EventManager.LightEnabled = getConfiguration("LightEnabled", false, "Enable or Disable the display of Light Levels.");
        EventManager.FirstRun = getConfiguration("FirstRun", true, "Checks if this is the first time mod is loaded.");
        
		GuiItemDurability.setCorner(EnumCorner.values()[configFile.getInt("Position", "general", 0, 0, 3, "0 - Bottom Right\n1 - Bottom Left\n2 - Top Right\n3 - Top Left")]);

        if (configFile.hasChanged())
            configFile.save();
    }

    @SuppressWarnings("unused")
	private static int getConfiguration(String setting, int defaultSetting, String comment)
    {
        return configFile.get(Configuration.CATEGORY_GENERAL, setting, defaultSetting, comment).getInt(defaultSetting);
    }

    private static boolean getConfiguration(String setting, boolean defaultSetting, String comment)
    {
        return configFile.get(Configuration.CATEGORY_GENERAL, setting, defaultSetting, comment).getBoolean(defaultSetting);
    }
    
    private static double getConfiguration(String setting, double defaultSetting, String comment)
    {
        return configFile.get(Configuration.CATEGORY_GENERAL, setting, defaultSetting, comment).getDouble(defaultSetting);
    }
    public static void saveConfig()
    {
    	Property p;
    	p = configFile.get(Configuration.CATEGORY_GENERAL, "FirstRun", true);
        p.setComment("Enable/Disable improving the middle click functionality to work with blocks in your inventory.");
        p.set(false);
        configFile.save();
    	
    }
}
