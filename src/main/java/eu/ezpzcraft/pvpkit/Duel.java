package eu.ezpzcraft.pvpkit;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.gamemode.GameModes;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColor;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

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
    private Player tmp2 = null;

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
        	
        	tmp.setLastArena(arena.getName());
        	
        	tmp.setState();
        	
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
        	
        	tmp.setLastArena(arena.getName());
        	
        	tmp.setState();
        	
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
    public void end(Team winner)
    {
    	Team _team1 = EzpzPvpKit.getInstance().getTeam(team1);
    	Team _team2 = EzpzPvpKit.getInstance().getTeam(team2);
    	_team1.setInMatch(false);
    	_team2.setInMatch(false);
    	
    	EzpzPvpKit.getInstance().getQueue(EzpzPvpKit.getInstance().getTeam(team1).getQueue())
    										.removeDuel(this.getTeam1(),this.getTeam2());
    	
        for( String player : _team1.getPlayers() )
        {
        	tmp = EzpzPvpKit.getInstance().getPlayer(player);      
        	tmp2 = tmp.getPlayer();
        	
        	tmp.setAlive(false);
        	tmp.getState().reset(tmp.getPlayer());  	
        	tmp2.offer(Keys.CAN_FLY, false);
            tmp2.gameMode().set(GameModes.SURVIVAL);
            
            // Vote ?
            if( EzpzPvpKit.getInstance().getCommandHandler().getVoteMap().canVote(tmp) )
            {
            	sendVoteMsg(tmp2);
            }
            
            // TODO save match in DB
            // winner parameter
            
            // End of cbt msg
            
        }  
        
        for( String player : _team2.getPlayers() )
        {
        	tmp = EzpzPvpKit.getInstance().getPlayer(player);      
        	tmp2 = tmp.getPlayer();
        	
        	tmp.setAlive(false);
        	tmp.getState().reset(tmp.getPlayer());  	
        	tmp2.offer(Keys.CAN_FLY, false);
            tmp2.gameMode().set(GameModes.SURVIVAL);
            
            // Vote ?
            if( EzpzPvpKit.getInstance().getCommandHandler().getVoteMap().canVote(tmp) )
            {
            	sendVoteMsg(tmp2);
            }
            
            // TODO save match in DB
            // winner parameter
            
            // End of cbt msg
        } 
    }
    
    /**
     * Send a map ranking message to the given player
     * @param player
     */
    private void sendVoteMsg(Player player)
    {
    	Text msg = Text.builder("Rate the map: ").color(TextColors.GRAY)    			
    	.append(Text.builder("[1]").color(TextColors.DARK_RED)
    			    .onClick(TextActions.runCommand("/vote 1"))
    			    .onHover(TextActions.showText(Text.of(TextColors.DARK_RED,"Awful")))
    			    .build())
    	.append(Text.builder("[2]").color(TextColors.RED)
			    .onClick(TextActions.runCommand("/vote 2"))
			    .onHover(TextActions.showText(Text.of(TextColors.RED,"Bad")))
			    .build())
    	.append(Text.builder("[3]").color(TextColors.YELLOW)
			    .onClick(TextActions.runCommand("/vote 3"))
			    .onHover(TextActions.showText(Text.of(TextColors.YELLOW,"Okay")))
			    .build())
    	.append(Text.builder("[4]").color(TextColors.GREEN)
			    .onClick(TextActions.runCommand("/vote 4"))
			    .onHover(TextActions.showText(Text.of(TextColors.GREEN,"Good")))
			    .build())
    	.append(Text.builder("[5]").color(TextColors.DARK_GREEN)
			    .onClick(TextActions.runCommand("/vote 5"))
			    .onHover(TextActions.showText(Text.of(TextColors.DARK_GREEN,"Amazing")))
			    .build())
    	.build();
    	player.sendMessage(msg);
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
