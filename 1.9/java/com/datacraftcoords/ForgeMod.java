package com.datacraftcoords;

import java.io.IOException;

import org.mcstats.MetricsLite;

import net.minecraft.client.Minecraft;
import net.minecraft.command.ICommandManager;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.stats.Achievement;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ClientConnectedToServerEvent;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import client.gui.GuiItemDurability;

import com.datacraftcoords.commands.CommandDay;
import com.datacraftcoords.commands.CommandGamemode;
import com.datacraftcoords.commands.CommandNight;
import com.datacraftcoords.commands.Test;
import com.datacraftcoords.commands.Weather;
import com.datacraftcoords.config.ConfigHandler;
import com.datacraftcoords.event.InterfaceHandler;
import com.datacraftcoords.event.OnTickEvent;


@SuppressWarnings("unused")
@Mod(modid = Reference.MOD_ID, name = Reference.NAME, version = Reference.VERSION, guiFactory = Reference.CONFIG_GUI_FACTORY)
public class ForgeMod {
	@Instance(value = Reference.MOD_ID)
	public static ForgeMod instance;
	public static Configuration configFile;
	
	//@SidedProxy(serverSide = Reference.PROXY_SERVER, clientSide = Reference.PROXY_CLIENT)
	@SidedProxy(clientSide = "com.datacraftcoords.ClientProxy", serverSide = "com.datacraftcoords.CommonProxy")
    public static CommonProxy proxy;
	
	//private File configFile;
	public static SimpleNetworkWrapper network;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event){
		MinecraftForge.EVENT_BUS.register(new com.datacraftcoords.KeyInputHandler());
		com.datacraftcoords.KeyBindings.init();
		// Configs
		MinecraftForge.EVENT_BUS.register(new ConfigHandler());
		ConfigHandler.init(event);
		network = NetworkRegistry.INSTANCE.newSimpleChannel("JSCM|INIT");
		//network = NetworkRegistry.INSTANCE.newSimpleChannel("JSCM|CONTROL");
	    network.registerMessage(MyMessage.Handler.class, MyMessage.class, 0, Side.CLIENT);
	   
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event){
		MinecraftForge.EVENT_BUS.register(new com.datacraftcoords.event.EventManager());
		MinecraftForge.EVENT_BUS.register(new com.datacraftcoords.event.HUDRenderer());
		MinecraftForge.EVENT_BUS.register(new com.datacraftcoords.event.ChatListener());
		MinecraftForge.EVENT_BUS.register(new com.datacraftcoords.ZoomHandler());
		MinecraftForge.EVENT_BUS.register(new com.datacraftcoords.JoinServer());
		MinecraftForge.EVENT_BUS.register(new OnTickEvent());
		MinecraftForge.EVENT_BUS.register(new GuiItemDurability(Minecraft.getMinecraft()));
		//MinecraftForge.EVENT_BUS.register(new com.datacraftcoords.JoinServer());
		//MinecraftForge.EVENT_BUS.register(new com.datacraftcoords.event.DistanceMeasurer());
		//MinecraftForge.EVENT_BUS.register(new com.datacraftcoords.event.SafeOverlay());
		
		/** VERSION CHECKER */
		String link = "https://raw.githubusercontent.com/JoelGodOfwar/joelsimplecoords/master/versionlist.json";
		FMLInterModComms.sendRuntimeMessage("JoelGodOfWars.Simple.Coordinate.Mod", "VersionChecker", "addVersionCheck", link);
		
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event){
		 try {
		        MetricsLite metrics = new MetricsLite("JoelGodOfWars Simple Coordinates Mod", Reference.VERSION );
		        metrics.start();
		        //Do.Trace("Metrics Try......................................................................");
		    } catch (IOException e) {
		        // Failed to submit the stats :-(
		    	e.printStackTrace(System.err);
		    }
	}
	@EventHandler void load(FMLPostInitializationEvent event){
		try {
			InterfaceHandler.loadSettings();
			Do.Trace("Loadsettings happened................................");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@EventHandler
	public void modsLoaded(FMLPostInitializationEvent event)  {
	     if (Loader.isModLoaded("VersionChecker")) {
	            try {
	                      //LogHandler.infoBox("Version Checker Loaded", "Is loaded?");
	                                

	            LogHandler.info("Loaded VersionChecker");
	            }
	            catch (Exception e) {
	            	LogHandler.warning("Could not load Versionchecker");
	                e.printStackTrace(System.err);
	                }
	      	}
	     else if (!Loader.isModLoaded("VersionChecker")) {
	    	 try {
	    		 LogHandler.infoBox("Version Checker NOT Loaded\nWould you like to download it?", "WARNING!");
	    	 }
	    	 catch (Exception e) {
	            	LogHandler.warning("Exception checking for VersionChecker");
	                e.printStackTrace(System.err);
	                }
	     }
	     
	}
	
	@EventHandler
    public void serverStart(FMLServerStartingEvent event){
		MinecraftServer server = event.getServer();
	    // Get's the current server instance
		
		ICommandManager command = server.getCommandManager();
		// Get's the Command manager for the server, but it's in a form we cannot use.
		
		ServerCommandManager manager = (ServerCommandManager) command;
		// Turns the useless to us ICommandManager into a now useful ServerCommandManager
		
		manager.registerCommand(new Test());
		manager.registerCommand(new Weather());
		manager.registerCommand(new CommandGamemode());
		manager.registerCommand(new CommandDay());
		manager.registerCommand(new CommandNight());
		// test server commands. TODO:
		event.registerServerCommand(new Weather());
		event.registerServerCommand(new CommandGamemode());
		event.registerServerCommand(new CommandDay());
		event.registerServerCommand(new CommandNight());
		// Registers the command
		
		//ClientCommandHandler.instance.registerCommand(dacommand);
	}
	
	 
	
	public static  Achievement ach;
	
}
