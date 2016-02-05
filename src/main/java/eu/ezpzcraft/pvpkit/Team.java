package eu.ezpzcraft.pvpkit;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.LinkedList;

public class Team
{
    private String name = null;
    private TeamStats stats = null;
    // Player UUID 
    // First is the leader  
    private LinkedList<String> players = null; 
    private int maxSize = 5;
    private String queue = null;
    private Boolean inMatch = false;

    public Team(String name)
    {
        this.name = name;
        this.stats = new TeamStats();
        this.players = new LinkedList<String>();
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
    public LinkedList<String> getPlayers()
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
	 * Get the leader of the team
	 * @return leader of the team
	 */
	public String getLeader()
	{
		return this.players.get(0);
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
}
