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

import eu.ezpzcraft.pvpkit.DuelQueue;
import eu.ezpzcraft.pvpkit.EzpzPvpKit;
import eu.ezpzcraft.pvpkit.PvPPlayer;
import eu.ezpzcraft.pvpkit.Team;
import eu.ezpzcraft.pvpkit.Utils;

/**
 * Player and his team join the specified queue
 *
 */
public class QueueJoin implements CommandExecutor
{

	
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException 
    {
    	

    	String name = args.<String>getOne("name").get();
        if(src instanceof Player && EzpzPvpKit.getInstance().isQueueExisting(name)) 
        {            
        	Player player = (Player) src;
        	PvPPlayer pvpPlayer = EzpzPvpKit.getInstance().getPlayer(player.getIdentifier());
        	Team team = EzpzPvpKit.getInstance().getTeam( pvpPlayer.getTeam() );
        	DuelQueue queue = EzpzPvpKit.getInstance().getQueue(name);
        	
        	if( team.getSize() != queue.getSize() )
        		Utils.sendKitMessage(player, Text.of(TextColors.RED, "Party size doesn't match queue size"));
        	else if( pvpPlayer.getInMatch() )
        		Utils.sendKitMessage(player, Text.of(TextColors.RED, "Cannot join queue in duel"));
        	// Match XvX
        	else
        	{        		
        		queue.join(team);      		
        		for(String it : team.getPlayers())
        			Utils.sendKitMessage(EzpzPvpKit.getInstance().getPlayer(it).getPlayer(), 
        								 Text.of("You joined queue : "+name));
        	}
        	
        }
        else if(src instanceof Player && !EzpzPvpKit.getInstance().isQueueExisting(name))
        	Utils.sendKitMessage((Player) src, Text.of(TextColors.RED, "this queue doesn't exist"));
        else if(src instanceof ConsoleSource) 
        	Utils.sendMessageC(src);
        else if(src instanceof CommandBlockSource) 
        	Utils.sendMessageCB(src);       

        return CommandResult.success();
    }
	
	
}

