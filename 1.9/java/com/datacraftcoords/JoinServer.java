package com.datacraftcoords;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ClientConnectedToServerEvent;

public class JoinServer {
	
	@SubscribeEvent
	//public void onInteract ( PlayerInteractEvent event ){
	public void onJoin(ClientConnectedToServerEvent event) {
		
    	EntityPlayer player = (EntityPlayer) Minecraft.getMinecraft().thePlayer;
    	String StandOut = " *****************************************************************************************************************************************************************************";
    	System.out.println("onJoin Fired" + StandOut);
    	//if(event.world.isRemote){
    	if(event.isLocal()) {
			// Local Connection
			System.out.println("Local Connection" + StandOut);
			Do.Say(player, "Local Connection");
		}else {
			// Server Connection
			System.out.println("Server Connection" + StandOut);
			ForgeMod.network.sendToServer(new MyMessage("12345678901234567890123456789012345678901234567890"));
			
			Do.Say(player, "Server Connection");
			//PacketDispatcher.sendToServer("JSCM-JoelGodOfWar's Simple Coordinates Mod-IsLoaded");
		}
	}

}
