package com.datacraftcoords.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.WorldSettings.GameType;

import com.datacraftcoords.Do;

public class CommandGamemode extends CommandBase{

	@Override
	public String getName() {
		return "gm";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "Changes Game Mode";
	}

	@Override
	public void execute(ICommandSender sender, String[] args)
			throws CommandException {
		//Check if sender is a player
		if(sender instanceof EntityPlayer)
		{
			// Turn the sender into a player entity
			EntityPlayer player = (EntityPlayer) sender;
			
			GameType gametype = null;
			if(canCommandSenderUse(sender)){
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
