package com.datacraftcoords;

import java.io.File;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = "JoelGodOfWars.Simple.Coordinate.Mod", name = "JoelGodOfWar's Simple Coordinate Mod", version = "1.8_0.7")
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
		configFile = event.getSuggestedConfigurationFile();
		Configs.LoadConfigSettings(configFile);
        //public static daConfig = config;
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event){
		MinecraftForge.EVENT_BUS.register(new com.datacraftcoords.event.EventManager());
		MinecraftForge.EVENT_BUS.register(new com.datacraftcoords.event.HUDRenderer());
		
		/** VERSION CHECKER */
		String link = "https://raw.githubusercontent.com/JoelGodOfwar/joelsimplecoords/master/versionlist.json";
		FMLInterModComms.sendRuntimeMessage("JoelGodOfWars.Simple.Coordinate.Mod", "VersionChecker", "addVersionCheck", link);
		
	}
	@EventHandler
	public void postInit(FMLPostInitializationEvent event){
		
	}
}
