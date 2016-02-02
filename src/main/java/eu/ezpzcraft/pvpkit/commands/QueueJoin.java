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

import eu.ezpzcraft.pvpkit.EzpzPvpKit;
import eu.ezpzcraft.pvpkit.Team;

public class QueueJoin implements CommandExecutor
{

	
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException 
    {
    	

    	String name = args.<String>getOne("queue").get();
        if(src instanceof Player && !EzpzPvpKit.getInstance().isQueueExisting(name)) 
        {            
        	Player player = (Player) src;
        	// Match XvX
        	if(EzpzPvpKit.getInstance().getPlayer(player.getIdentifier()).getTeam().getSize() ==
        				EzpzPvpKit.getInstance().getQueue(name).getSize())
        	{
        		EzpzPvpKit.getInstance().getQueue(name).join(EzpzPvpKit.getInstance().getPlayer(player.getIdentifier()).getTeam());      		
        	}
        	// Not match
        	else
        	{
        		EzpzPvpKit.getInstance().getUtils().sendKitMessage(player, Text.of(TextColors.RED, "Party size doesn't match queue size"));
        	}
        	
        }
        else if(src instanceof Player && EzpzPvpKit.getInstance().isQueueExisting(name))
        {
        	Player player = (Player) src;
        	EzpzPvpKit.getInstance().getUtils().sendKitMessage(player, Text.of(TextColors.RED, "this queue doesn't exist"));
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

