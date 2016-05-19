package com.datacraftcoords.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;

@SuppressWarnings("unused")
public class CommandDay extends CommandBase
{
	
	/**
     * Gets the name of the command
     */
	@Override
    public String getCommandName()
    {
        return "day";
    }

	/**
     * Return the required permission level for this command.
     */
    public int getRequiredPermissionLevel()
    {
        return 2;
    }
	
	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "Sets Time to Day";
	}

	
	public boolean canCommandSenderUse(MinecraftServer server, ICommandSender sender)
    {
        return server.isSinglePlayer() || super.checkPermission(server, sender);
    }

    /**
     * Set the time in the server object.
     */
	protected void setAllWorldTimes(MinecraftServer server, int time)
    {
        for (int i = 0; i < server.worldServers.length; ++i)
        {
            server.worldServers[i].setWorldTime((long)time);
        }
    }

	@Override
	public void execute(MinecraftServer server, ICommandSender sender,
			String[] args) throws CommandException {
		// TODO Auto-generated method stub
		if(sender instanceof EntityPlayer)
		{
			// Turn the sender into a player entity
			EntityPlayer player = (EntityPlayer) sender;
			
			if(canCommandSenderUse(server, sender)){
				// Send the player entity a message
				this.setAllWorldTimes(server, 1000);
                notifyOperators(sender, this, "Time Set to 1000", new Object[] {Integer.valueOf(1000)});
                return;
		        
			}
			else{
				notifyOperators(sender, this, "This is not Single Player, or you are not OP.", new Object[0]);
			}
		}
	}

}
