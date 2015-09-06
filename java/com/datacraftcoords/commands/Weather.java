package com.datacraftcoords.commands;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.storage.WorldInfo;

import com.datacraftcoords.Do;

public class Weather extends CommandBase{
	@Override
	public String getName() {
		return "td";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "toggledownfall";
	}

	@Override
	public void execute(ICommandSender sender, String[] args)
			throws CommandException {
		//Check if sender is a player
		if(sender instanceof EntityPlayer)
		{
			// Turn the sender into a player entity
			EntityPlayer player = (EntityPlayer) sender;
			
			if(canCommandSenderUse(sender)){
				// Send the player entity a message
				this.toggleDownfall();
		        notifyOperators(sender, this, "Toggled weather downfall...", new Object[0]);
			}
			else{
				notifyOperators(sender, this, "This is not Single Player, or you are not OP.", new Object[0]);
			}
		}
	}
	
	public boolean canCommandSenderUse(ICommandSender sender)
    {
        return MinecraftServer.getServer().isSinglePlayer() || super.canCommandSenderUse(sender);
    }
	
	/**
     * Toggle rain and enable thundering.
     */
    protected void toggleDownfall()
    {
        WorldInfo worldinfo = MinecraftServer.getServer().worldServers[0].getWorldInfo();
        worldinfo.setRaining(!worldinfo.isRaining());
    }
}
