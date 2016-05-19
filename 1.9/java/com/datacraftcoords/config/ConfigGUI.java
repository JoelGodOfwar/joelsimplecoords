package com.datacraftcoords.config;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.GuiConfig;

import com.datacraftcoords.Reference;

public class ConfigGUI extends GuiConfig {
    public ConfigGUI(GuiScreen parent) {
        super(parent,
                new ConfigElement(ConfigHandler.configFile.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(),
                Reference.MOD_ID, false, false, GuiConfig.getAbridgedConfigPath(ConfigHandler.configFile.toString()));
    }
}