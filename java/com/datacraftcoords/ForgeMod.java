package com.datacraftcoords;

import java.io.File;

import com.datacraftcoords.event.EventManager;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = "JoelGodOfWars.Simple.Coordinate.Mod", name = "JoelGodOfWar's Simple Coordinate Mod", version = "1.8-0.2")
public class ForgeMod {
	@Instance(value = "JoelGodOfWars.Simple.Coordinate.Mod")
	public static ForgeMod instance;
	
	//@SidedProxy(serverSide = Reference.PROXY_SERVER, clientSide = Reference.PROXY_CLIENT)
	@SidedProxy(clientSide = "com.datacraftcoords.ClientProxy", serverSide = "com.datacraftcoords.CommonProxy")
    public static CommonProxy proxy;
	
	private File configFile;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event){
		FMLCommonHandler.instance().bus().register(new com.datacraftcoords.KeyInputHandler());
		com.datacraftcoords.KeyBindings.init();
		/**Configuration config = new Configuration(event.getSuggestedConfigurationFile());

        config.load();
        EventManager.Enabled = config.get(Configuration.CATEGORY_GENERAL, "Enabled", false).getBoolean(true);
		
        config.save(); */
		configFile = event.getSuggestedConfigurationFile();
		Configs.LoadConfigSettings(configFile);
        //public static daConfig = config;
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event){
		MinecraftForge.EVENT_BUS.register(new com.datacraftcoords.event.EventManager());
		/** VERSION CHECKER */
		//String link = "https://raw.githubusercontent.com/yourName/YourMod/master/src/main/resources/versionlist.json";
		//FMLInterModComms.sendRuntimeMessage("your_mod_id", "VersionChecker", "addVersionCheck", link);
		
	}
	@EventHandler
	public void postInit(FMLPostInitializationEvent event){
		
	}
}
