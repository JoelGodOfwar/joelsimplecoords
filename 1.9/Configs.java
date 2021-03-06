package com.datacraftcoords;
import java.io.File;

import com.datacraftcoords.event.EventManager;
import com.datacraftcoords.event.PotionTimers;
import com.datacraftcoords.event.SafeOverlay;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;


public class Configs {
	public static final String CATEGORY_GENERAL = "general";
	public static Configuration config = null;
	public static void LoadConfigSettings(File configFile)
    {
    	ReadConfigSettings(configFile, true);
    }
	public static void SaveConfigSettings()
    {
    	ReadConfigSettings(null, false);
    }
    
    /**
     * Creates the config file if it doesn't already exist.
     * It loads/saves config values from/to the config file.
     * @param configFile Standard Forge configuration file
     * @param loadSettings set to true to load the settings from the config file, 
     * or false to save the settings to the config file
     */
    private static void ReadConfigSettings(File configFile, boolean loadSettings)
    {
    	//NOTE: doing config.save() multiple times will bug out and add additional quotes to
    	//categories with more than 1 word
    	//Configuration config = null;
    	if(loadSettings)
    	{
            config = new Configuration(configFile);
            config.load();
            EventManager.Enabled = config.getBoolean("Enabled", CATEGORY_GENERAL, false, "");
            EventManager.chunkEnabled = config.getBoolean("ChunkEnabled", CATEGORY_GENERAL, false, "");
            PotionTimers.Enabled = config.getBoolean("PotionTimerEnabled", CATEGORY_GENERAL, false, "");
            ZoomHandler.zoomMultiplier = config.get(CATEGORY_GENERAL, "zoomMultiplier", -5.0f).getDouble();
            SafeOverlay.Enabled = config.getBoolean("SafeEnabled", CATEGORY_GENERAL, false, "");
            EventManager.LightEnabled = config.getBoolean("LightEnabled", CATEGORY_GENERAL, false, "");
    	}
        
        Property p;
        config.addCustomCategoryComment(CATEGORY_GENERAL, "Allows you to Enable or Disable the display of Coordinates.");
		p = config.get(CATEGORY_GENERAL, "Enabled", true);
	    p.comment = "Enable/Disable display of coordinates";
	    
	    p.set(EventManager.Enabled);
	    
	    p = config.get(CATEGORY_GENERAL, "PotionTimerEnabled", true);
	    p.comment = "Enable/Disable display of coordinates";
	    
	    p.set(PotionTimers.Enabled);
	    
	    p = config.get(CATEGORY_GENERAL, "ChunkEnabled", true);
	    p.comment = "Enable/Disable display of Chunk coordinates";
	    
	    p.set(EventManager.chunkEnabled);
	    
	    p = config.get(CATEGORY_GENERAL, "zoomMultiplier", -15.0f);
	    p.comment = "Amount of zoom, must be negative";
	    
	    p.set(ZoomHandler.zoomMultiplier);
	    
	    config.addCustomCategoryComment(CATEGORY_GENERAL, "Allows you to Enable or Disable the display of Safe Overlay.");
		p = config.get(CATEGORY_GENERAL, "SafeEnabled", true);
	    p.comment = "Enable/Disable display of Safe Overlay";
	    p.set(SafeOverlay.Enabled);
	    
	    config.addCustomCategoryComment(CATEGORY_GENERAL, "Allows you to Enable or Disable the display of Safe Overlay.");
		p = config.get(CATEGORY_GENERAL, "LightEnabled", true);
	    p.comment = "Enable/Disable display of Safe Overlay";
	    p.set(EventManager.LightEnabled);
	    
	    config.save();
    }
}
