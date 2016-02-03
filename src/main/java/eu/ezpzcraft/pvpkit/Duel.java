package eu.ezpzcraft.pvpkit;

import java.util.Map;
import java.util.UUID;

import org.spongepowered.api.entity.living.player.Player;


/**
 * <b> Duel, class representing a duel. </b>
 * <p>A duel is a couple of two team with an associated arena
 *
 */
public class Duel
{
    private Arena arena = null;
    private Team team1 = null;
    private Team team2 = null;
    
    private Player tmp = null; // tmp var

    public Duel(Team team1, Team team2, Arena arena)
    {
        this.team1 = team1;
        this.team2 = team2;
        this.arena = arena;
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
        // For all players from both team
        for( String player : team1.getPlayers() )
        {       	
        	tmp = EzpzPvpKit.getInstance().getPlayer(player).getPlayer();
        	
        	// TP
        	tmp.setLocationAndRotation( arena.getPos1(), arena.getRotation1() );
        	
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
        for( String player : team2.getPlayers()  )
        {
        	tmp = EzpzPvpKit.getInstance().getPlayer(player).getPlayer();
        	
        	// TP
        	tmp.setLocationAndRotation( arena.getPos2(), arena.getRotation2() );
        }
        
        EzpzPvpKit.getInstance().getStartMatchSetup().addTeamStart(team1);
        EzpzPvpKit.getInstance().getStartMatchSetup().addTeamStart(team2);
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
        // Save score
        // TP back (pos+orientation)
        // Set health
        // Set hunger
        // Set effects
        // Set inventory
    }
}
