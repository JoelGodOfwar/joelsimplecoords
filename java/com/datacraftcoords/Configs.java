package com.datacraftcoords;
import java.io.File;

import com.datacraftcoords.event.EventManager;

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
    	}
        
        Property p;
        config.addCustomCategoryComment(CATEGORY_GENERAL, "Allows you to Enable or Disable the display of Coordinates.");
		p = config.get(CATEGORY_GENERAL, "Enabled", true);
	    p.comment = "Enable/Disable display of coordinates";
	    
	    p.set(EventManager.Enabled);
	    config.save();
    }
}
