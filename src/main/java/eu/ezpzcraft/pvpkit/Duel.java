package eu.ezpzcraft.pvpkit;


import org.spongepowered.api.entity.living.player.Player;


/**
 * <b> Duel, class representing a duel. </b>
 * <p>A duel is a couple of two team with an associated arena
 *
 */
public class Duel
{
	private Arena arena = null;
	private String team1 = null;
    private String team2 = null;
    
    private PvPPlayer tmp = null; // tmp var

    public Duel(String team1, String team2, Arena arena)
    {
        this.team1 = team1;
        this.team2 = team2;
        this.arena = arena;
        EzpzPvpKit.getInstance().getQueue(EzpzPvpKit.getInstance().getTeam(team1).getQueue()).addDuel(this);
    }

    /**
     * Start the duel:
     * <ul>
     * <li> -- Temporary -- </li>
     * <li> Teleport player </li>
     * <li> Start Scheduler </li>
     * </ul>
     *
     */
    public void start()
    {
    	
    	EzpzPvpKit.getInstance().getTeam(team1).setInMatch(true);
    	EzpzPvpKit.getInstance().getTeam(team2).setInMatch(true);
        // For all players from both team
    	
        for( String player : EzpzPvpKit.getInstance().getTeam(team1).getPlayers() )
        {       	
        	tmp = EzpzPvpKit.getInstance().getPlayer(player);
        	
        	tmp.setAlive(true);      	
        	// TP
        	tmp.getPlayer().setLocationAndRotation( arena.getPos1(), arena.getRotation1() );
        	
        	// DO not forget to update player state
        	// Current duel link
        	//
            // Save current state
            //entry.getValue().setState();

            // Clear inventory
            //entry.getValue().getPlayer().getInventory().clear();

            // Clear effect

            // TP (pos+orientation)
            // set health
            // set hunger
            // set inventory

            // Countdown title, immobilise ?
        	
        	// Send msg
        }
        for( String player : EzpzPvpKit.getInstance().getTeam(team2).getPlayers() )
        {
        	tmp = EzpzPvpKit.getInstance().getPlayer(player);
        	
        	tmp.setAlive(true);
        	// TP
        	tmp.getPlayer().setLocationAndRotation( arena.getPos2(), arena.getRotation2() );
        }      
        EzpzPvpKit.getInstance().getStartMatchSetup().addTeamStart(team1,team2);
    }

    /**
     * End the duel:
     * <ul>
     * <li> </li>
     * <li> </li> 
     * </ul>
     *
     */
    public void end()
    {
    	EzpzPvpKit.getInstance().getTeam(team1).setInMatch(false);
    	EzpzPvpKit.getInstance().getTeam(team2).setInMatch(false);
    	
    	EzpzPvpKit.getInstance().getQueue(EzpzPvpKit.getInstance().getTeam(team1).getQueue())
    										.removeDuel(this.getTeam1(),this.getTeam2());
        // Save score
        // TP back (pos+orientation)
        // Set health
        // Set hunger
        // Set effects
        // Set inventory
    }
    
    /**
     * Get team1
     * @return team1
     */
    public String getTeam1() 
    {
		return team1;
	}

    /**
     * Get team2
     * @return team2
     */
	public String getTeam2() 
	{
		return team2;
	}

	/**
	 * Set team1
	 * @param team1
	 */
	public void setTeam1(String team1) 
	{
		this.team1 = team1;
	}

	/**
	 * Set team2
	 * @param team2
	 */
	public void setTeam2(String team2) 
	{
		this.team2 = team2;
	}
	
	/**
	 * Get arena
	 * @return arena
	 */
    public Arena getArena() 
    {
		return arena;
	}

    /**
     * Set arena
     * @param arena
     */
	public void setArena(Arena arena) 
	{
		this.arena = arena;
	}
}
