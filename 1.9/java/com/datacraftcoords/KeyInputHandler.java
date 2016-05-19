package com.datacraftcoords;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import client.gui.EnumGuiState;
import client.gui.GuiItemDurability;

import com.datacraftcoords.config.ConfigHandler;
import com.datacraftcoords.event.DistanceMeasurer;
import com.datacraftcoords.event.EventManager;
import com.datacraftcoords.event.PotionTimers;

import api.utils.PotionHelper;

//import com.datacraftcoords.event.EventManager;import net.minecraft.client.Minecraft;

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
  		Do.Say(mc.thePlayer, "Your nether portal should be built at " + TextFormatting.LIGHT_PURPLE + (EventManager.GetXCoordinate() / 8) + ", " + EventManager.GetYCoordinate() + ", " + (EventManager.GetZCoordinate() / 8) + TextFormatting.WHITE + " in the " + TextFormatting.LIGHT_PURPLE + "Nether.");
  	}
  	else {
  		 varWorld = "Nether ";
  		Do.Say(mc.thePlayer, "Your nether portal should be built at " + TextFormatting.YELLOW + (EventManager.GetXCoordinate() * 8) + ", " + EventManager.GetYCoordinate() + ", " + (EventManager.GetZCoordinate() * 8) + TextFormatting.WHITE + " in the " + TextFormatting.YELLOW + "Overworld.");
  	}
	/**
	 *  Check if Enabled by Server Plugin
	 */
  	if(MyMessage.plgCCenabled){
  		mc.thePlayer.sendChatMessage("My " + varWorld + "Coords: " + coordinateString);//mc.fontRendererObj.drawString("Z Pressed", 2, 20, 0xffffff);
  	}
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
  /**if (mc.currentScreen != null)
  {
      return;    //don't activate if the user is looking at a GUI
  }*/
  

  //if "Ctrl" and "Alt" is pressed
  if (KeyBindings.varLeft.isPressed())
  {
      //display the GUI
      EventManager.alignLeft = true;
      EventManager.alignRight = false;
      EventManager.alignTop = false;
  }
  if (KeyBindings.varRight.isPressed())
  {
      //display the GUI
	  EventManager.alignLeft = false;
      EventManager.alignRight = true;
      EventManager.alignTop = false;
  }
  if (KeyBindings.varTop.isPressed())
  {
      //display the GUI
	  EventManager.alignLeft = false;
      EventManager.alignRight = false;
      EventManager.alignTop = true;
  }
  if (KeyBindings.varDist.isPressed())
  {
      //display the GUI
	  DistanceMeasurer.Mode = DistanceMeasurer.Modes.SIMPLE;
	  DistanceMeasurer.Enabled = !DistanceMeasurer.Enabled;
	  
  }
  if (KeyBindings.varNight.isPressed()){
      EntityPlayerSP p = Minecraft.getMinecraft().thePlayer;
      for (int i = 0; i < p.worldObj.loadedEntityList.size(); ++i)
      {
          try
          {
              Entity e = (Entity)p.worldObj.loadedEntityList.get(i);
              if (e instanceof EntityMob || e instanceof IMob)
              {
            	  EntityLivingBase living = (EntityLivingBase) e;
            	  String poteff = living.getActivePotionEffects().toString();
            	  Do.Trace("" + poteff);
            	  if(poteff.contains("effect.glowing")){
            	  	//e.setGlowing(true);
            		  living.removePotionEffect(Potion.getPotionById(PotionHelper.glowing));
            	  }else{
            		  living.addPotionEffect(new PotionEffect(Potion.getPotionById(PotionHelper.glowing), 5000, 1000));
            		  
            	  }
              }
                  
          }catch (Exception e) {
              // empty catch block
          }
      
      }
      
  
	  String poteff = mc.thePlayer.getActivePotionEffects().toString();
	  if(poteff.contains("effect.nightVision")){
		  mc.thePlayer.removePotionEffect(Potion.getPotionById(PotionHelper.night_vision));
	  }else{
		  mc.thePlayer.addPotionEffect(new PotionEffect(Potion.getPotionById(PotionHelper.night_vision), 5000, 1000));
	  }
	  
	  
	  //ForgeMod.network.sendToServer(new MyMessage("12345678901234567890123456789012345678901234567890"));
	  //System.out.println("Server Connection");
  }
  if (KeyBindings.varArmor.isPressed()) {
	  if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)||Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
	      GuiItemDurability.setRenderChararcter(!GuiItemDurability.getRenderCharacter());
	      //mc.theWorld.playRecord(mc.thePlayer.getPosition(), "Minecraft:record.13");
	      //Do.Trace("Key pressed ..................................................................");
	  } else {
	      if (GuiItemDurability.getGuiState() == EnumGuiState.CLOSED || GuiItemDurability.getGuiState() == EnumGuiState.CLOSING) {
	          GuiItemDurability.setGuiState(EnumGuiState.OPENING);
	          //Do.Trace("opening ..................................................................");
	          return;
	      }
	      if (GuiItemDurability.getGuiState() == EnumGuiState.OPEN || GuiItemDurability.getGuiState() == EnumGuiState.OPENING) {
	          GuiItemDurability.setGuiState(EnumGuiState.CLOSING);
	          //Do.Trace("closing ..................................................................");
	          return;
	      }
	  }
  }
  
}

public void onMouseEvent(InputEvent.MouseInputEvent event){
	if (KeyBindings.varDist.isPressed())
	  {
	      //display the GUI
		  /**DistanceMeasurer.Mode = DistanceMeasurer.Modes.SIMPLE;
		  DistanceMeasurer.Enabled = !DistanceMeasurer.Enabled;
		  String ofCapeUrl = "http://s.optifine.net/capes/Larson.png";
          String mptHash = FilenameUtils.getBaseName((String)ofCapeUrl);
          final kk rl = new kk("capeof/" + mptHash);
          bvi textureManager = bcf.z().N();
          bvj tex = textureManager.b(rl);
		  mc.thePlayer.setLocationOfCape(rl);*/
		  
	  }
}

protected static Minecraft mc = Minecraft.getMinecraft();

}
