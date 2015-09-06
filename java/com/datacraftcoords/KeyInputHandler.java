package com.datacraftcoords;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraftforge.common.AchievementPage;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraft.util.ChatComponentText;
import com.datacraftcoords.config.ConfigHandler;

//import com.datacraftcoords.event.EventManager;

import net.minecraft.util.EnumChatFormatting;

import com.datacraftcoords.event.EventManager;
import com.datacraftcoords.event.PotionTimers;
import com.datacraftcoords.event.SafeOverlay;

public class KeyInputHandler {
@SubscribeEvent
public void onKeyInput(InputEvent.KeyInputEvent event) {
  if(KeyBindings.varChat.isPressed()){
	
	String coordinateString = "{x}, {y}, {z}";
  	coordinateString = coordinateString.replace("{x}", Integer.toString(com.datacraftcoords.event.EventManager.GetXCoordinate()));
  	coordinateString = coordinateString.replace("{y}", Integer.toString(com.datacraftcoords.event.EventManager.GetYCoordinate()));
  	coordinateString = coordinateString.replace("{z}", Integer.toString(com.datacraftcoords.event.EventManager.GetZCoordinate()));
  	String varBiome = com.datacraftcoords.event.EventManager.getBiome();
  	String varWorld = "";
  	if(!varBiome.equals("Hell")){
  		 varWorld = "Overworld ";
  		Do.Say(mc.thePlayer, "Your nether portal should be built at " + EnumChatFormatting.LIGHT_PURPLE + (EventManager.GetXCoordinate() / 8) + ", " + EventManager.GetYCoordinate() + ", " + (EventManager.GetZCoordinate() / 8) + EnumChatFormatting.WHITE + " in the " + EnumChatFormatting.LIGHT_PURPLE + "Nether.");
  	}
  	else {
  		 varWorld = "Nether ";
  		Do.Say(mc.thePlayer, "Your nether portal should be built at " + EnumChatFormatting.YELLOW + (EventManager.GetXCoordinate() * 8) + ", " + EventManager.GetYCoordinate() + ", " + (EventManager.GetZCoordinate() * 8) + EnumChatFormatting.WHITE + " in the " + EnumChatFormatting.YELLOW + "Overworld.");
  	}
	mc.thePlayer.sendChatMessage("My " + varWorld + "Coords: " + coordinateString);//mc.fontRendererObj.drawString("Z Pressed", 2, 20, 0xffffff);
	//Do.Say(mc.thePlayer, "testing");
	//System.out.println("KEY Z");
	
	  //OPEN GUI
   
  }
  if(KeyBindings.varEnabled.isPressed()){
	  EventManager.ToggleEnabled();
  }
  if(KeyBindings.varPotions.isPressed()){
	  PotionTimers.ToggleEnabled();
  }
  if(KeyBindings.varChunk.isPressed()){
	  EventManager.chunkEnabled = !EventManager.chunkEnabled;
	  ConfigHandler.configFile.save();
  }
  if(KeyBindings.varLight.isPressed()){
	  EventManager.LightEnabled = !EventManager.LightEnabled;
  }
}
protected static Minecraft mc = Minecraft.getMinecraft();

}
