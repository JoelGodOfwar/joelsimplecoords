package com.datacraftcoords.event;

import com.datacraftcoords.Do;
import com.datacraftcoords.ForgeMod;
import com.datacraftcoords.MyMessage;
import com.datacraftcoords.Reference;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ChatListener {

	@SuppressWarnings("unused")
	@SubscribeEvent
	//public void onInteract ( PlayerInteractEvent event ){
	public void onChatRecieved(ClientChatReceivedEvent event) {
	
		String StandOut = " *****************************************************************************************************************************************************************************";
		if(event.getMessage().getUnformattedText().contains("joined the game")){
			if(event.getMessage().getUnformattedText().contains(mc.thePlayer.getDisplayNameString())){
				ForgeMod.network.sendToServer(new MyMessage("12345678901234567890123456789012345678901234567890"));
				Do.Trace("" + Reference.NAME + " " + Reference.VERSION + "....................................................");
			}
			//System.out.println("Chat contains JSCM" + StandOut);
		}else{
			//System.out.println("" + event.message.getUnformattedText() + StandOut);
		}
		
	}
	Minecraft mc = Minecraft.getMinecraft();
}
