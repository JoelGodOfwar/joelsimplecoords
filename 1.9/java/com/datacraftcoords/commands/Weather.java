package com.datacraftcoords.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.storage.WorldInfo;

@SuppressWarnings("unused")
public class Weather extends CommandBase
{
	
	public String getName() {
		return "td";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "toggledownfall";
	}

	
	public boolean canCommandSenderUse(MinecraftServer server, ICommandSender sender)
    {
        return server.isSinglePlayer() || super.checkPermission(server, sender);
    }
	
	/**
     * Toggle rain and enable thundering.
     */
    protected void toggleDownfall(MinecraftServer server)
    {
        WorldInfo worldinfo = server.worldServers[0].getWorldInfo();
        worldinfo.setRaining(!worldinfo.isRaining());
    }

	@Override
	public String getCommandName() {
		// TODO Auto-generated method stub
		return "td";
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
					
					if(canCommandSenderUse(server, sender)){
						// Send the player entity a message
						this.toggleDownfall(server);
				        notifyOperators(sender, this, "Toggled weather downfall...", new Object[0]);
					}
					else{
						notifyOperators(sender, this, "This is not Single Player, or you are not OP.", new Object[0]);
					}
				}
		
	}

	
}
