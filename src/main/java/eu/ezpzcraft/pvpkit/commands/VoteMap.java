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
import eu.ezpzcraft.pvpkit.Team;
import eu.ezpzcraft.pvpkit.Utils;

public class VoteMap  implements CommandExecutor
{
	// <uuid, <mapName,date> >
	private HashMap<String, HashMap<String, Long>> votes = new HashMap<String, HashMap<String, Long>>();
	
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException 
    {
        if(src instanceof Player) 
        {   
        	Player source = (Player) src;
        	
        	int score = args.<Integer>getOne("score").get();          	        	
        	String teamName = EzpzPvpKit.getInstance().getPlayer(source.getIdentifier()).getTeam();
        	Team team = EzpzPvpKit.getInstance().getTeam(teamName);
        	
        	if(team.getLastArena()==null)
        		Utils.sendKitMessage(source, Text.of(TextColors.RED, "Vote is allowed only after a duel"));
        	
        	if( canVote(source.getIdentifier(),team.getName()) )
        	{
        		saveVote(source.getIdentifier(),team.getName());
        		EzpzPvpKit.getInstance().getDatabase().saveVote(team.getLastArena(), score);
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
     * Save a player rating of the map
     * @param uuid of the player
     * @param map to rate
     * Vote if saved only if "canVote" returns true
     */
    private void saveVote(String uuid, String map)
    {
    	// Already voted
    	if( votes.containsKey(uuid) )
    	{
    		// Already voted for this specific map
    		if( votes.get(uuid).containsKey(map) )
    		{
    			long time = votes.get(uuid).get(map);
    			
    			// Must be 15min ago, otherwise vote is not saved
    			if( System.nanoTime()-time > 15e9 )
    				votes.get(uuid).put(map, System.nanoTime());
    			else
    				return;
    		}
    		// First vote for this map
    		else
    			votes.get(uuid).put(map, System.nanoTime());
    	}
    	// First vote for this player
    	else
    	{
    		votes.put(uuid, new HashMap<String, Long>());
    		votes.get(uuid).put(map, System.nanoTime());
    	}
    }
    
    /**
     * Check if a player can rate a map
     * @param uuid of the player
     * @param map to rate
     * @return true if the vote has been saved, i.e. if the player has not vote for this
     * map in the last 15minutes. False otherwise.
     */
    public boolean canVote(String uuid, String map)
    {
    	// Already voted
    	if( votes.containsKey(uuid) )
    	{
    		// Already voted for this specific map
    		if( votes.get(uuid).containsKey(map) )
    		{      		
    			long time = votes.get(uuid).get(map);
    			
    			// Must be 15min ago, otherwise vote is not saved
    			if( System.nanoTime()-time > 15e9 )
      				return true;
    			else
    				return false;
    		}
    		// First vote for this map
    		else
    			return true;
    	}
    	// First vote for this player
    	else
    		return true;
    }
}