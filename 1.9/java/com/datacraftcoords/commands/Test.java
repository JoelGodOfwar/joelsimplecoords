package com.datacraftcoords.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;

import com.datacraftcoords.Do;

public class Test extends CommandBase{

	public String getName() {
		return "test";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "Displays a test message";
	}

	public void execute(ICommandSender sender, String[] args)
			throws CommandException {
		//Check if sender is a player
		if(sender instanceof EntityPlayer)
		{
			// Turn the sender into a player entity
			EntityPlayer player = (EntityPlayer) sender;
			
			// Send the player entity a message
			Do.Say(player, "I Volunterely chose not to murder you horribly, feel good! ");
			
		}
	}

	@Override
	public String getCommandName() {
		// TODO Auto-generated method stub
		return "test";
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
					
					// Send the player entity a message
					Do.Say(player, "I Volunterely chose not to murder you horribly, feel good! ");
					
				}
	}

	
}
