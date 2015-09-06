package com.datacraftcoords.commands;

import com.datacraftcoords.Do;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;

public class Test extends CommandBase{

	@Override
	public String getName() {
		return "test";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "Displays a test message";
	}

	@Override
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

}
