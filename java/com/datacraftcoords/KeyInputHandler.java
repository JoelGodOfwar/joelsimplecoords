package com.datacraftcoords;

import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
//import com.datacraftcoords.event.EventManager;


import com.datacraftcoords.event.EventManager;

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
  	}
  	else {
  		 varWorld = "Nether ";
  	}
	mc.thePlayer.sendChatMessage("My " + varWorld + "Coords: " + coordinateString);//mc.fontRendererObj.drawString("Z Pressed", 2, 20, 0xffffff);
	  //System.out.println("KEY Z");
	  
	  //OPEN GUI
   
  }
  if(KeyBindings.varEnabled.isPressed()){
	  EventManager.ToggleEnabled();
  }
  if(KeyBindings.varChunk.isPressed()){
	  EventManager.chunkEnabled = !EventManager.chunkEnabled;
	  Configs.SaveConfigSettings();
  }
}
protected static Minecraft mc = Minecraft.getMinecraft();

}
