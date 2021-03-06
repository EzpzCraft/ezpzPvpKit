package eu.ezpzcraft.pvpkit.thread;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.function.Consumer;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.title.Title;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import eu.ezpzcraft.pvpkit.EzpzPvpKit;
import eu.ezpzcraft.pvpkit.PvPPlayer;
import eu.ezpzcraft.pvpkit.Team;

public class StartMatchThread implements Consumer<Task>
{
	private LinkedHashSet<String> teams = new LinkedHashSet<String>();
	private LinkedHashMap<String,Integer> countdowns = new LinkedHashMap<String,Integer>();
	private LinkedHashMap<String,Location<World>> locations = new LinkedHashMap<String,Location<World>>();
	private LinkedList<String> teamsToRemove; 
	
	private int ticks = (int) (Sponge.getServer().getTicksPerSecond()/4.);
	
	// Temporary variables
	private int _countdown;
	private PvPPlayer _player = null;
	private Team _team = null;
	private String team = null;
	private Iterator<String> it = null;
	
	/**
	 * For each team joining a duel, it prevents the players to move and send them
	 * a title (3,2,1,go!).
	 */
	@Override
    public void accept(Task task) 
    {		
		teamsToRemove = new LinkedList<String>();
		it = teams.iterator();
		// each team
		while (it.hasNext()) 
		{
			team = it.next();
			_team = EzpzPvpKit.getInstance().getTeam(team);
			_countdown = getCD(team);
			
			// For each player of the current team			
			for (String player : _team.getPlayers()) 
			{
				_player = EzpzPvpKit.getInstance().getPlayer(player);
				sendCountDownTitle(_player,_countdown);				
			}

			if(_countdown == 0)
			{
				teamsToRemove.add(team);
				EzpzPvpKit.getInstance().getQueue(_team.getQueue()).leave(_team);								
			}
			else
				setCD(team, _countdown-1);
		}
		for(String s: teamsToRemove)
		{
			removeBox( locations.get(s) );
			teams.remove(s);
			locations.remove(s);
			countdowns.remove(s);			
		}
    }
	
	/**
	 * Send the countdown title
	 * @param pvpPlayer
	 * @param cd, value of the countdown
	 */
	private void sendCountDownTitle(PvPPlayer pvpPlayer, int cd)
	{
		Player player = pvpPlayer.getPlayer();
		
		switch(cd)
		{
			case 3:
				player.sendTitle(Title.builder()
						.title(Text.builder(""+cd).color(TextColors.RED).build())
						.fadeIn(0)
						.stay(ticks)
						.fadeOut(3*ticks)
						.build());
			break;
			case 2:
				player.sendTitle(Title.builder()
						.title(Text.builder(""+cd).color(TextColors.GOLD).build())
						.fadeIn(0)
						.stay(ticks)
						.fadeOut(3*ticks)
						.build());
			break;
			case 1:
				player.sendTitle(Title.builder()
						.title(Text.builder(""+cd).color(TextColors.DARK_GREEN).build())
						.fadeIn(0)
						.stay(ticks)
						.fadeOut(3*ticks)
						.build());
			break;
			case 0:
				player.sendTitle(Title.builder()
						.title(Text.builder("GO").color(TextColors.GREEN).build())
						.fadeIn(0)
						.stay(ticks)
						.fadeOut(2)
						.build());
			break;
		}
	}
	
	
	/**
	 * Add the two teams of one duel to the list of team to manage
	 * @param teamName
	 */
    public void addTeamStart(String teamName, String teamName2) 
    {
    	Team team = EzpzPvpKit.getInstance().getTeam(teamName);	
    	Team team2 = EzpzPvpKit.getInstance().getTeam(teamName2);	
		teams.add(teamName);
		teams.add(teamName2);
		locations.put(teamName, EzpzPvpKit.getInstance()
										  .getPlayer(team.getPlayers().iterator().next())
										  .getPlayer().getLocation() );
		locations.put(teamName2, EzpzPvpKit.getInstance()
				  .getPlayer(team2.getPlayers().iterator().next())
				  .getPlayer().getLocation() );
		
		this.countdowns.put(teamName, 3);
		this.countdowns.put(teamName2, 3);
		setBox( locations.get(teamName) );	
		setBox( locations.get(teamName2) );
	}
    
    /**
     * Create the box around the given location
     * @param location
     */
    public void setBox(Location<World> location)
    {
		setBlock(location.add(2, 0, 0));
		setBlock(location.add(-2, 0, 0));
		setBlock(location.add(0, 0, 2));
		setBlock(location.add(0, 0, -2));
		setBlock(location.add(-1, 0, 2));
		setBlock(location.add(-1, 0, -2));
		setBlock(location.add(1, 0, 2));
		setBlock(location.add(1, 0, -2));
		setBlock(location.add(-2, 0, 1));
		setBlock(location.add(-2, 0, -1));
		setBlock(location.add(2, 0, 1));
		setBlock(location.add(2, 0, -1));
		
		setBlock(location.add(2, 1, 0));
		setBlock(location.add(-2, 1, 0));
		setBlock(location.add(0, 1, 2));
		setBlock(location.add(0, 1, -2));
		setBlock(location.add(-1, 1, 2));
		setBlock(location.add(-1, 1, -2));
		setBlock(location.add(1, 1, 2));
		setBlock(location.add(1, 1, -2));
		setBlock(location.add(-2, 1, 1));
		setBlock(location.add(-2, 1, -1));
		setBlock(location.add(2, 1, 1));
		setBlock(location.add(2, 1, -1));
		
		setBlock(location.add(2, -1, 0));
		setBlock(location.add(-2, -1, 0));
		setBlock(location.add(0, -1, 2));
		setBlock(location.add(0, -1, -2));
		setBlock(location.add(-1, -1, 2));
		setBlock(location.add(-1, -1, -2));
		setBlock(location.add(1, -1, 2));
		setBlock(location.add(1, -1, -2));
		setBlock(location.add(-2, -1, 1));
		setBlock(location.add(-2, -1, -1));
		setBlock(location.add(2, -1, 1));
		setBlock(location.add(2, -1, -1));
		
		setBlock(location.add(2, 2, 0));
		setBlock(location.add(-2, 2, 0));
		setBlock(location.add(0, 2, 2));
		setBlock(location.add(0, 2, -2));
		setBlock(location.add(-1, 2, 2));
		setBlock(location.add(-1, 2, -2));
		setBlock(location.add(1, 2, 2));
		setBlock(location.add(1, 2, -2));
		setBlock(location.add(-2, 2, 1));
		setBlock(location.add(-2, 2, -1));
		setBlock(location.add(2, 2, 1));
		setBlock(location.add(2, 2, -1));	
		
		setBlock(location.add(0, 3, 0));
		setBlock(location.add(0, 3, 1));
		setBlock(location.add(0, 3, -1));
		setBlock(location.add(1, 3, 0));
		setBlock(location.add(1, 3, 1));
		setBlock(location.add(1, 3, -1));
		setBlock(location.add(-1, 3, 0));
		setBlock(location.add(-1, 3, 1));
		setBlock(location.add(-1, 3, -1));
    }
    
    /**
     * Remove the box at a given location
     * @param location
     */
    public void removeBox(Location<World> location)
    {
		removeBlock(location.add(2, 0, 0));
		removeBlock(location.add(-2, 0, 0));
		removeBlock(location.add(0, 0, 2));
		removeBlock(location.add(0, 0, -2));
		removeBlock(location.add(-1, 0, 2));
		removeBlock(location.add(-1, 0, -2));
		removeBlock(location.add(1, 0, 2));
		removeBlock(location.add(1, 0, -2));
		removeBlock(location.add(-2, 0, 1));
		removeBlock(location.add(-2, 0, -1));
		removeBlock(location.add(2, 0, 1));
		removeBlock(location.add(2, 0, -1));
		
		removeBlock(location.add(2, 1, 0));
		removeBlock(location.add(-2, 1, 0));
		removeBlock(location.add(0, 1, 2));
		removeBlock(location.add(0, 1, -2));
		removeBlock(location.add(-1, 1, 2));
		removeBlock(location.add(-1, 1, -2));
		removeBlock(location.add(1, 1, 2));
		removeBlock(location.add(1, 1, -2));
		removeBlock(location.add(-2, 1, 1));
		removeBlock(location.add(-2, 1, -1));
		removeBlock(location.add(2, 1, 1));
		removeBlock(location.add(2, 1, -1));
		
		removeBlock(location.add(2, -1, 0));
		removeBlock(location.add(-2, -1, 0));
		removeBlock(location.add(0, -1, 2));
		removeBlock(location.add(0, -1, -2));
		removeBlock(location.add(-1, -1, 2));
		removeBlock(location.add(-1, -1, -2));
		removeBlock(location.add(1, -1, 2));
		removeBlock(location.add(1, -1, -2));
		removeBlock(location.add(-2, -1, 1));
		removeBlock(location.add(-2, -1, -1));
		removeBlock(location.add(2, -1, 1));			
		
		
		removeBlock(location.add(2, -1, -1));
		
		removeBlock(location.add(2, 2, 0));
		removeBlock(location.add(-2, 2, 0));
		removeBlock(location.add(0, 2, 2));
		removeBlock(location.add(0, 2, -2));
		removeBlock(location.add(-1, 2, 2));
		removeBlock(location.add(-1, 2, -2));
		removeBlock(location.add(1, 2, 2));
		removeBlock(location.add(1, 2, -2));
		removeBlock(location.add(-2, 2, 1));
		removeBlock(location.add(-2, 2, -1));
		removeBlock(location.add(2, 2, 1));
		removeBlock(location.add(2, 2, -1));
		
		/* Get a floor ?
		removeBlock(location.add(2, -2, 0));
		removeBlock(location.add(-2, -2, 0));
		removeBlock(location.add(0, -2, 2));
		removeBlock(location.add(0, -2, -2));
		removeBlock(location.add(-1, -2, 2));
		removeBlock(location.add(-1, -2, -2));
		removeBlock(location.add(1, -2, 2));
		removeBlock(location.add(1, -2, -2));
		removeBlock(location.add(-2, -2, 1));
		removeBlock(location.add(-2, -2, -1));
		removeBlock(location.add(2, -2, 1));
		removeBlock(location.add(2, -2, -1));
		
		removeBlock(location.add(0, -3, 0));
		removeBlock(location.add(0, -3, 1));
		removeBlock(location.add(0, -3, -1));
		removeBlock(location.add(1, -3, 0));
		removeBlock(location.add(1, -3, 1));
		removeBlock(location.add(1, -3, -1));
		removeBlock(location.add(-1, -3, 0));
		removeBlock(location.add(-1, -3, 1));
		removeBlock(location.add(-1, -3, -1));*/
		
		removeBlock(location.add(0, 3, 0));
		removeBlock(location.add(0, 3, 1));
		removeBlock(location.add(0, 3, -1));
		removeBlock(location.add(1, 3, 0));
		removeBlock(location.add(1, 3, 1));
		removeBlock(location.add(1, 3, -1));
		removeBlock(location.add(-1, 3, 0));
		removeBlock(location.add(-1, 3, 1));
		removeBlock(location.add(-1, 3, -1));
    }
    
    /**
     * Remove a block of box
     * @param location
     */
    private void removeBlock(Location<World> location)
    {
    	if(location.getBlock() == BlockTypes.STAINED_GLASS.getDefaultState())
    		location.setBlock(BlockTypes.AIR.getDefaultState());
    }
    
    /**
     * Set a block of box
     * @param location
     */
    private void setBlock(Location<World> location)
    {
    	if(location.getBlock() == BlockTypes.AIR.getDefaultState())
    		location.setBlock(BlockTypes.STAINED_GLASS.getDefaultState());
    }
    
    /**
     * Get the countdown of a given team
     * @param team
     * @return the countdown value
     */
    private int getCD(String team)
    {
    	Integer result = this.countdowns.get(team);
    	return result==null ? 0 : result;    
    }    
    
    /**
     * Set the countdown value of a team
     * @param team
     * @param value
     */
    private void setCD(String team, int value)
    {
    	this.countdowns.put(team, value);
    }
}
