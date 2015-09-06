package com.datacraftcoords;

import java.io.File;

import com.datacraftcoords.commands.CommandGamemode;
import com.datacraftcoords.commands.Test;
import com.datacraftcoords.commands.Weather;
import com.datacraftcoords.config.ConfigHandler;

import net.minecraft.command.ICommandManager;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.stats.Achievement;
import net.minecraftforge.common.AchievementPage;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(modid = "JoelGodOfWars.Simple.Coordinate.Mod", name = "JoelGodOfWar's Simple Coordinate Mod", version = "1.8_1.0.2", guiFactory = Reference.CONFIG_GUI_FACTORY)
public class ForgeMod {
	@Instance(value = "JoelGodOfWars.Simple.Coordinate.Mod")
	public static ForgeMod instance;
	public static Configuration configFile;
	
	//@SidedProxy(serverSide = Reference.PROXY_SERVER, clientSide = Reference.PROXY_CLIENT)
	@SidedProxy(clientSide = "com.datacraftcoords.ClientProxy", serverSide = "com.datacraftcoords.CommonProxy")
    public static CommonProxy proxy;
	
	//private File configFile;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event){
		FMLCommonHandler.instance().bus().register(new com.datacraftcoords.KeyInputHandler());
		com.datacraftcoords.KeyBindings.init();
		// Configs
		FMLCommonHandler.instance().bus().register(new ConfigHandler());
		ConfigHandler.init(event);
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event){
		MinecraftForge.EVENT_BUS.register(new com.datacraftcoords.event.EventManager());
		MinecraftForge.EVENT_BUS.register(new com.datacraftcoords.event.HUDRenderer());
		MinecraftForge.EVENT_BUS.register(new com.datacraftcoords.ZoomHandler());
		//MinecraftForge.EVENT_BUS.register(new com.datacraftcoords.event.SafeOverlay());
		
		/** VERSION CHECKER */
		String link = "https://raw.githubusercontent.com/JoelGodOfwar/joelsimplecoords/master/versionlist.json";
		FMLInterModComms.sendRuntimeMessage("JoelGodOfWars.Simple.Coordinate.Mod", "VersionChecker", "addVersionCheck", link);
		
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event){
		
	}
	
	@EventHandler
    public void serverStart(FMLServerStartingEvent event){
		MinecraftServer server = MinecraftServer.getServer();
	    // Get's the current server instance
		
		ICommandManager command = server.getCommandManager();
		// Get's the Command manager for the server, but it's in a form we cannot use.
		
		ServerCommandManager manager = (ServerCommandManager) command;
		// Turns the useless to us ICommandManager into a now useful ServerCommandManager
		
		manager.registerCommand(new Test());
		manager.registerCommand(new Weather());
		manager.registerCommand(new CommandGamemode());
		// Registers the command
	}
	
	public static  Achievement ach;
}
