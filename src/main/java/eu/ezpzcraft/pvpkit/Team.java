package eu.ezpzcraft.pvpkit;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class Team
{
    private String name = null;
    private TeamStats stats = null;
    private LinkedHashSet<PvPPlayer> players = null;
    private int maxSize =5;
    private Location<World> location;
	private int countdown = 3;

    public Team(String name)
    {
        this.name = name;
        // TODO: fetch stats from DB
        this.players = new LinkedHashSet<PvPPlayer>();
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
            players.add(EzpzPvpKit.getInstance().getPlayer(player.getIdentifier()));
            return true;
        }

        return false;
    }

    public void removePlayer(Player player)
    {
        if( containsPlayer(player) )
            players.remove( player.getIdentifier() );
    }

    public LinkedHashSet<PvPPlayer> getPlayers()
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
}
