package com.datacraftcoords;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import org.lwjgl.input.Keyboard;

public class ZoomHandler {
	boolean previousDoZoom = false;
	public static double zoomMultiplier = -5.0F;
	public static double zoomDefault = 1.0F;

	   @SubscribeEvent
	   public void updateFOV(FOVUpdateEvent event) {//public void onPreRender(TickEvent.RenderTickEvent event) {
	      //int key = ReflectionHelper.getPrivateValue(KeyBinding.class, ZoomIt.keyBinding, "keyCode", "field_74512_d");
	      boolean doZoom = false;
	      //System.out.println("Event fired......................................................................................................");

	      //if(KeyBindings.varZoom.getKeyCode()){
	      if (Keyboard.isKeyDown(KeyBindings.varZoom.getKeyCode())){
	          if(MyMessage.plgZOOMenabled){
	        	  doZoom = true;
	          }
	            //System.out.println("varZoom key pressed......................................................................................................");
	      }

	      if (mc.currentScreen != null)
	         doZoom = false;

	      if (doZoom){// && !previousDoZoom) {
	         //ReflectionHelper.setPrivateValue(EntityRenderer.class, mc.entityRenderer, 2.0d, "cameraZoom", "field_78503_V");
	         //mc.gameSettings.smoothCamera = true;
	    	  //EntityRenderer entityRenderer = mc.entityRenderer;
	    	  //EntityRendererAccessor.setCameraZoom(entityRenderer, zoomMultiplier);
	    	  event.setNewfov((float) zoomMultiplier);
	         //System.out.println("doZoom true " + event.fov + "......................................................................................................");
	      } else if (!doZoom && previousDoZoom) {
	         //ReflectionHelper.setPrivateValue(EntityRenderer.class, mc.entityRenderer, 1.0d, "cameraZoom", "field_78503_V");
	         //mc.gameSettings.smoothCamera = false;
	    	  //EntityRenderer entityRenderer = mc.entityRenderer;
	    	  //EntityRendererAccessor.setCameraZoom(entityRenderer, zoomDefault);
	    	  event.setNewfov((float) zoomDefault);
	         //System.out.println("doZoom false " + event.fov + "......................................................................................................");
	      }

	      previousDoZoom = doZoom;
	   }
	   protected static Minecraft mc = Minecraft.getMinecraft();
	   
	}

