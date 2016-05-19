package com.datacraftcoords.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldSettings.GameType;

import com.datacraftcoords.Do;

public class CommandGamemode extends CommandBase
{
	
	@Override
	public String getCommandName() {
		// TODO Auto-generated method stub
		return "gm";
	}
	
	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "Changes Game Mode";
	}
	
	public boolean canCommandSenderUse(MinecraftServer server, ICommandSender sender)
    {
        return server.isSinglePlayer() || super.checkPermission(server, sender);
    }

	@Override
	public void execute(MinecraftServer server, ICommandSender sender,
			String[] args) throws CommandException {
		// TODO Auto-generated method stub
		//Check if sender is a player
		if(sender instanceof EntityPlayer)
		{
			// Turn the sender into a player entity
			EntityPlayer player = (EntityPlayer) sender;
			
			GameType gametype = null;
			if(canCommandSenderUse(server, sender)){
				if("0".equals(args[0])){
					gametype = GameType.SURVIVAL;
				}
				else if("1".equals(args[0])){
					gametype = GameType.CREATIVE;
				}
				else if("2".equals(args[0])){
					gametype = GameType.ADVENTURE;
				}
				else if("3".equals(args[0])){
					gametype = GameType.SPECTATOR;
				}
			
				player.setGameType(gametype);
			
				Do.Say(player, "Gamemode set to " + gametype);
			}
			else{
				Do.Say(player, "This is not Single Player, or you are not OP.");
			}
			
			// Send the player entity a message
			//Do.Say(player, "I Volunterely chose not to murder you horribly, feel good! ");
			
		}
	}

	


	

}
