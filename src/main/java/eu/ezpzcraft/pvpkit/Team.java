package eu.ezpzcraft.pvpkit;

import org.spongepowered.api.entity.living.player.Player;

import java.util.LinkedHashMap;
import java.util.UUID;

public abstract class Team
{
    private String name = null;
    private TeamStats stats = null;
    private LinkedHashMap<UUID,PvPPlayer> players = null;
    private int maxSize;

    public Team(String name, int maxSize) throws Exception
    {
        if(name==null)
            throw new Exception("A team cannot have a null name.");

        this.name = name;
        // TODO: fetch stats from DB
        this.players = new LinkedHashMap<UUID,PvPPlayer>();
        this.maxSize = maxSize;
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
        return getSize()>0 && players.containsKey( player.getUniqueId() );
    }

    public boolean addPlayer(Player player)
    {
        if( getSize()<maxSize )
        {
            players.put( player.getUniqueId(), new PvPPlayer(player) );
            return true;
        }

        return false;
    }

    public void removePlayer(Player player)
    {
        if( containsPlayer(player) )
            players.remove( player.getUniqueId() );
    }

    public LinkedHashMap<UUID,PvPPlayer> getPlayers()
    {
        return players;
    }
}
