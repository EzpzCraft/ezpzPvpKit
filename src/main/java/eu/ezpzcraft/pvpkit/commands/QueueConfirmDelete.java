package eu.ezpzcraft.pvpkit.commands;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.CommandBlockSource;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import eu.ezpzcraft.pvpkit.EzpzPvpKit;
import eu.ezpzcraft.pvpkit.Utils;

/**
 * Confirm the delete of the queue
 */
public class QueueConfirmDelete implements CommandExecutor
{

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException 
    {
    	String name = args.<String>getOne("name").get();
    	Boolean bool = args.<Boolean>getOne("bool").get();
        if(src instanceof Player && EzpzPvpKit.getInstance().isQueueExisting(name)) 
        {      
        	Player player = (Player) src;
        	if(bool)
        	{
	        	try 
	        	{
					EzpzPvpKit.getInstance().getDatabase().deleteQueue(EzpzPvpKit.getInstance().getQueue(name));
					EzpzPvpKit.getInstance().removeQueue(name);
					Utils.sendKitMessage(player, Text.of(name + " is now deleted"));
				} 
	        	catch (Exception e) 
	        	{
	        		Utils.sendKitMessage(player, Text.builder("Failed to delete "+name)
	        																.color(TextColors.RED)
	        																.build());
	        		e.printStackTrace();
				}
        	}
        	else
        		Utils.sendKitMessage(player, Text.of("Delete canceled"));
        }
        else if(src instanceof Player && !EzpzPvpKit.getInstance().isQueueExisting(name))
        	Utils.sendKitMessage((Player) src, Text.of(name + " doesn't exist"));
        else if(src instanceof ConsoleSource) 
        	Utils.sendMessageC(src);
        else if(src instanceof CommandBlockSource) 
        	Utils.sendMessageCB(src);
        
        return CommandResult.success();
    }
	
	
}

