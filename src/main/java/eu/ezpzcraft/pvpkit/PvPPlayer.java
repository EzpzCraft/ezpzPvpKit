package eu.ezpzcraft.pvpkit;


import java.util.LinkedHashMap;
import java.util.UUID;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public class PvPPlayer
{
    /* Variable */
    private Player player = null;
    private float score = 0;
    private int remainingRanked = 0;
    private String rank;
    private long vote[];
    
    private double enderpearlCD = 0.0;
    private PlayerState state = null;
    private DuelQueue queue = null;
    		
    /* Constructor */
    public PvPPlayer(Player player, float score, int remainingRanked, String rank, long[] vote)
    {
        this.player = player;
        this.score = score;
        this.remainingRanked = remainingRanked;
        this.rank = rank;
    }

    /* Methods */  
    public void setState()
    {
        this.state = new PlayerState(this.player);
    }

    public PlayerState getState()
    {
        return this.state;
    }

    public Player getPlayer()
    {
        return this.player;
    }

	public int getRemainingRanked() 
	{
		return this.remainingRanked;
	}

	public void setRemainingRanked(int remainingRanked) 
	{
		this.remainingRanked = remainingRanked;
	}
}
