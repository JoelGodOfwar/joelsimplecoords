package com.datacraftcoords;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;

public class Do {

	   /**
	   * Displays text message in chat to the player, if parameter player is not null.
	   * @param player
	   * @param theMessage
	   */
	    public static void Say(EntityPlayer player, String theMessage) { 
	      if (player != null) { 
	        player.addChatComponentMessage(new TextComponentString(theMessage)); // mc1.7.10 and mc1.7.2 and mc1.8
	        
	        /**
	        ((EntityPlayerMP)player).addChatComponentMessage(new ChatComponentText("2. "+theMessage));
	        
	        EntityPlayerMP thisPlayer = ((EntityPlayerMP)player).mcServer.getConfigurationManager().getPlayerByUsername(player.getDisplayNameString());
	        thisPlayer.addChatComponentMessage(new ChatComponentText("3. "+theMessage));
	        
	        player.addChatMessage(new ChatComponentText("4. "+theMessage)); // does nothing ever
	        
	        String name5 = player.getDisplayNameString();
	        EntityPlayer thisPlayer5 = ((EntityPlayerMP)player).mcServer.getConfigurationManager().getPlayerByUsername(name5);
	        thisPlayer5.addChatComponentMessage(new ChatComponentText("5. "+theMessage));
	        /**/
	        //player.addChatMessage(theMessage); // mc1.6.4
	      } else { Do.Trace("Do.Say: player is null, message is:"+theMessage); return; }
	    }
		
		public static boolean tracing = true;
		
		/**
		* Outputs a string with mod name and "TRACE" in the console.
		* @param s
		*/
		public static void Trace(String s) {
		if (!tracing) { return; }
			System.out.println("TRACE [" + "JoelGodOfWars.Simple.Coordinate.Mod" + "] " + s);
			//System.err.println("TRACE [" + ModInfo.ID + "] " + s); // using the standard error output because it shows up in red in my console *grin*
		}
		
		
	}
