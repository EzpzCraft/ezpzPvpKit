package eu.ezpzcraft.pvpkit;


import org.spongepowered.api.entity.living.player.Player;

public class PvPPlayer
{
    /* Variable */
    private Player player = null;
    private int remainingRanked = 0;
    private String rank;
    private long vote[];
    private String lang = "English"; // TODO: add in DB in the future when handling languages
	private String team = null;
    private PlayerState state = null;
    private Boolean alive = false;

	/* Constructor */
    public PvPPlayer(Player player, int remainingRanked, String rank, long[] vote)
    {
        this.player = player;
        this.remainingRanked = remainingRanked;
        this.rank = rank;
        this.team = player.getName();
        
        String name = player.getName();
        EzpzPvpKit.getInstance().addTeam( new Team(name) );
        EzpzPvpKit.getInstance().getTeam(name).addPlayer(player.getIdentifier());
    }

    /* Methods */  
    
    /**
     * Save the state of the player
     */
    public void setState()
    {
        this.state = new PlayerState(this.player);
    }

    /**
     * Get the player state
     * @return the player state if it has been saved previously
     */
    public PlayerState getState()
    {
        return this.state;
    }

    /**
     * Get the player object (Sponge)
     * @return player
     */
    public Player getPlayer()
    {
        return this.player;
    }

    /**
     * Get the number of remaining ranked matches
     * @return #
     */
	public int getRemainingRanked() 
	{
		return this.remainingRanked;
	}

	/**
	 * Set number of remaining ranked matches
	 * @param remainingRanked
	 */
	public void setRemainingRanked(int remainingRanked) 
	{
		this.remainingRanked = remainingRanked;
	}

	/**
	 * Get the player rank
	 * @return the rank
	 */
	public String getRank() 
	{
		return this.rank;
	}
	
	/**
	 * Get the player language
	 * @return language
	 */
	public String getLang()
	{
		return this.lang;
	}

	
	/**
	 * Get the player league
	 * @return league
	 */
	public String getLeague() // TODO
	{
		return "LEAGUE";
	}
	
	/**
	 * Get the player score for a given queue type
	 * @return the requested score
	 */
	public int getScore(String type)
	{
		return EzpzPvpKit.getInstance().getDatabase().getPlayerScore(this.player).get(type);
	}
	
	/**
	 * Get the mean score of a player
	 * @return the mean score
	 */
	public int getMeanScore()
	{
		return 10; // TODO
	}
	
	/**
	 * Get the queue name the player is currently in
	 * @return team name
	 */
    public String getTeam() 
    {
		return team;
	}

	/**
	 * Set the queue name the player is currently in
	 * @param team
	 */
	public void setTeam(String team) 
	{
		this.team = team;
	}
	
	/**
	 * Get the timestamp of the last vote for the given website
	 * @param i, the website
	 * @return the timestamps of the last vote
	 */
	public long getVote(int i)
	{
		return this.vote[i];
	}
	
	/**
	 * Is the player alive ?
	 * @return true if alive
	 */
	public Boolean getAlive() 
	{
		return alive;
	}

	/**
	 * Set if the player is alive or not
	 * @param alive
	 */
	public void setAlive(Boolean alive) {
		this.alive = alive;
	}
}
