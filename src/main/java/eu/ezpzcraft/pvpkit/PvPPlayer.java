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
    private String lang = "English"; // TODO: add in DB in the future when handling languages
    
    private double enderpearlCD = 0.0;
    private PlayerState state = null;
    private DuelQueue queue = null;
    private Boolean inMatch = false;
    		
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

	public String getRank() 
	{
		return this.rank;
	}
	
	public String getLang()
	{
		return this.lang;
	}

	public Boolean getInMatch() 
	{
		return this.inMatch;
	}

	public void setInMatch(Boolean inMatch) 
	{
		this.inMatch = inMatch;
	}
	
	public String getLeague()
	{
		if(score <= 1000)
			return "Coal";
		else if(score <= 1200)
			return "Iron";
		else if(score <= 1400)
			return "Gold";
		else if(score <= 1600)
			return "Emerald";
		else if(score <= 1800)
			return "Diamond";
		else if(score <= 2000)
			return "Obsidian";
		else if(score <= 2200)
			return "Bedrock";
		else
			return "error";
	}
	
	public int getScore()
	{
		return (int) this.score;
	}
}
