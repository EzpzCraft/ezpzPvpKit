package eu.ezpzcraft.pvpkit;

import java.util.Map;
import java.util.UUID;

public class Duel
{
    private Arena arena = null;
    private Team team1 = null;
    private Team team2 = null;
    private float priority = 0;

    public Duel(Team team1, Team team2, Arena arena)
    {
        this.team1 = team1;
        this.team2 = team2;
        this.arena = arena;
        this.priority = 0;
    }

    public void start()
    {
        // For all players from both team
        for( PvPPlayer player : team1.getPlayers() )
        {
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
        }
        for( PvPPlayer player : team2.getPlayers()  )
        {
            // Save current state
            //entry.getValue().setState();
        }
    }

    public void end()
    {
        // Save score
        // TP back (pos+orientation)
        // Set health
        // Set hunger
        // Set effects
        // Set inventory
    }

    public float getPriority()
    {
        return priority;
    }

    public void updatePriority(float value)
    {
        priority = value;
    }
}
