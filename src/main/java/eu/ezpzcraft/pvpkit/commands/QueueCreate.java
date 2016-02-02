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
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

import eu.ezpzcraft.pvpkit.DuelQueue;
import eu.ezpzcraft.pvpkit.EzpzPvpKit;

public class QueueCreate implements CommandExecutor
{

	
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException 
    {
    	
    	String name = args.<String>getOne("name").get();
    	String type = args.<String>getOne("type").get();
    	Boolean ranked = false;
    	if( args.<Boolean>getOne("ranked").isPresent()) 
    		ranked = args.<Boolean>getOne("ranked").get();
        if(src instanceof Player && !EzpzPvpKit.getInstance().isQueueExisting(name)) 
        {            
        	Player player = (Player) src;
        	try
        	{
        		EzpzPvpKit.getInstance().addQueue(new DuelQueue(name,ranked,type));
        		EzpzPvpKit.getInstance().getDatabase().saveQueue(EzpzPvpKit.getInstance().getQueue(name));
        		EzpzPvpKit.getInstance().getUtils().sendKitMessage(player, Text.of("Queue created"));
        	}
        	catch(Exception e)
        	{
        		EzpzPvpKit.getInstance().getUtils().sendKitMessage(player, Text.of(TextColors.RED, "Failed to create the queue : "+name));
        	}
            
        }
        else if(src instanceof Player && EzpzPvpKit.getInstance().isQueueExisting(name))
        {
        	Player player = (Player) src;
        	EzpzPvpKit.getInstance().getUtils().sendQueueExist(player);
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

