package eu.ezpzcraft.pvpkit.commands;

import java.util.HashMap;

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
import eu.ezpzcraft.pvpkit.PvPPlayer;
import eu.ezpzcraft.pvpkit.Team;
import eu.ezpzcraft.pvpkit.Utils;

public class VoteMap  implements CommandExecutor
{	
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException 
    {
        if(src instanceof Player) 
        {   
        	Player source = (Player) src;
        	PvPPlayer pvpPlayer = EzpzPvpKit.getInstance().getPlayer(source.getIdentifier());
        	int score = args.<Integer>getOne("score").get();          	        	
        	String teamName = EzpzPvpKit.getInstance().getPlayer(source.getIdentifier()).getTeam();
        	Team team = EzpzPvpKit.getInstance().getTeam(teamName);
        	
        	if(pvpPlayer.getLastArena()==null)
        		Utils.sendKitMessage(source, Text.of(TextColors.RED, "Vote is allowed only after a duel"));	
        	else if( canVote(pvpPlayer) )
        	{
        		EzpzPvpKit.getInstance().getDatabase().saveVote(pvpPlayer.getLastArena(), score);
        		Utils.sendKitMessage(source, Text.of(TextColors.GRAY, "Vote saved, thank you !"));
        	}
        	else        	
        		Utils.sendKitMessage(source, Text.of(TextColors.RED, "Cannot vote for this map now"));
        }
        else if(src instanceof ConsoleSource) 
        	Utils.sendMessageC(src);
        else if(src instanceof CommandBlockSource) 
        	Utils.sendMessageCB(src);       

        return CommandResult.success();
    }       
    
    /**
     * Check if a player can rate a map
     * @param pvpPlayer
     * @return true if the player can vote, false otherwise
     */
    public boolean canVote(PvPPlayer pvpPlayer)
    {
    	return pvpPlayer.getLastArena()==null;
    }
}