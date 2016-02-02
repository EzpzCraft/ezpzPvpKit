package eu.ezpzcraft.pvpkit;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class Team
{
    private String name = null;
    private TeamStats stats = null;
    private LinkedHashSet<String> players = null;
    private HashMap<String, Double> scores = null;
    private int maxSize = 5;
    private Location<World> location;
	private int countdown = 3;

    public Team(String name)
    {
        this.name = name;
        // TODO: fetch stats from DB
        this.players = new LinkedHashSet<String>();
        this.scores = new HashMap<String, Double>(); // What on queue creation ? Must be updated
        
        for(String type:  EzpzPvpKit.getInstance().getQueueList() )
        	this.scores.put(type, 1000.0); // TODO 
        
        
    }

    public int getSize()
    {
        return players.size();
    }

    public String getName()
    {
        return name;
    }

    public boolean containsPlayer(Player player)
    {
        return players.contains(player.getIdentifier());
    }

    public boolean addPlayer(Player player)
    {
        if( getSize()<maxSize )
        {
            players.add( player.getIdentifier() );
            return true;
        }

        return false;
    }

    public void removePlayer(Player player)
    {
        if( containsPlayer(player) )
            players.remove( player.getIdentifier() );
    }

    public LinkedHashSet<String> getPlayers()
    {
        return players;
    }
    
	public Location<World> getLocation() 
	{
		return location;
	}

	public void setLocation(Location<World> location) 
	{
		this.location = location;
	}
	
    public int getCountdown() 
    {
		return countdown;
	}

	public void setCountdown(int countdown) 
	{
		this.countdown = countdown;
	}
	
	public double getScore(String type)
	{
		if( !this.scores.containsKey(type) )
			return 1000; // TODO
		
		return this.scores.get(type);
	}
}
