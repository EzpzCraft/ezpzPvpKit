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

public class QueueConfirmDelete implements CommandExecutor
{

	
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException 
    {
    	String name = args.<String>getOne("name").get();
        if(src instanceof Player && EzpzPvpKit.getInstance().isQueueExisting(name)) 
        {      
        	Player player = (Player) src;
        	try 
        	{
				EzpzPvpKit.getInstance().getDatabase().deleteQueue(EzpzPvpKit.getInstance().getQueue(name));
				EzpzPvpKit.getInstance().removeQueue(name);
				EzpzPvpKit.getInstance().getUtils().sendKitMessage(player, Text.of(name + " is now deleted"));
			} 
        	catch (Exception e) 
        	{
        		EzpzPvpKit.getInstance().getUtils().sendKitMessage(player, Text.builder("Failed to delete "+name)
        																.color(TextColors.RED)
        																.build());
        		e.printStackTrace();
			}
        }
        else if(src instanceof Player && !EzpzPvpKit.getInstance().isQueueExisting(name))
        {
        	Player player = (Player) src;
        	EzpzPvpKit.getInstance().getUtils().sendKitMessage(player, Text.of(name + " doesn't exist"));
        }
        else if(src instanceof ConsoleSource) 
        {
        	EzpzPvpKit.getInstance().getUtils().sendMessageC(src);
        }
        else if(src instanceof CommandBlockSource) 
        {
        	EzpzPvpKit.getInstance().getUtils().sendMessageCB(src);
        }
        

        return CommandResult.success();
    }
	
	
}

