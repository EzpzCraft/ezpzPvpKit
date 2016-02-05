package eu.ezpzcraft.pvpkit;

import java.util.LinkedHashSet;

public class Team
{
    private String name = null;
    private TeamStats stats = null;
    // Player UUID
    private LinkedHashSet<String> players = null; 
    private int maxSize = 5;
    private String queue = null;
    private Boolean inMatch = false;
    private String lastArena = null;

    public Team(String name)
    {
        this.name = name;
        this.stats = new TeamStats();
        this.players = new LinkedHashSet<String>();
    }

    public int getSize()
    {
        return players.size();
    }

    /**
     * Get the team name
     * @return name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Check if the team contains a given player
     * @param player UUID
     * @return true if yes, false otherwise
     */
    public boolean containsPlayer(String UUID)
    {
        return players.contains(name);
    }

    /**
     * Add a player to the team
     * @param player UUID
     * @return true if the player has been added, false otherwise
     */
    public boolean addPlayer(String UUID)
    {
        if( getSize()<maxSize )
        {
            players.add(UUID);
            return true;
        }

        return false;
    }

    /**
     * Remove the player from the team (if he was part of...)
     * @param UUID
     */
    public void removePlayer(String UUID)
    {
        if( containsPlayer(UUID) )
            players.remove(UUID);
    }

    /**
     * Get the raw player list
     * @return LinkedHashSet<String> of UUID 
     */
    public LinkedHashSet<String> getPlayers()
    {
        return players;
    }
	
    /**
     * Get the team statistics
     * @return statistics of the team
     */
	public TeamStats getStats()
	{
		return this.stats;
	}
	
	/**
	 * Check if the player is in a duel
	 * @return true if the player is in a duel, false otherwise
	 */
	public Boolean getInMatch() 
	{
		return this.inMatch;
	}

	/**
	 * Define the player as in a duel
	 * @param inMatch
	 */
	public void setInMatch(Boolean inMatch) 
	{
		this.inMatch = inMatch;
	}
	
	/**
	 * Get the queue the player is currently in
	 * @return the queue name
	 */
    public String getQueue() 
    {
		return this.queue;
	}

    /**
     * Set the queue name the player is currently in
     * @param queue
     */
	public void setQueue(String queue) 
	{
		this.queue = queue;
	}
	
	/**
	 * Last arena getter
	 * @return the name of the last arena
	 */
	public String getLastArena()
	{
		return this.lastArena;
	}
	
	/**
	 * Last arena setter
	 * @param arena
	 */
	public void setLastArena(String arena)
	{
		this.lastArena = arena;
	}
}
