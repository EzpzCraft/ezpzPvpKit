package eu.ezpzcraft.pvpkit;

import java.util.LinkedHashMap;
import java.util.LinkedList;

public class DuelQueue
{
    /* Variables */
    private String name = null;
    private LinkedHashMap<String, Team> waitingTeams = null;
    private LinkedList<Duel> duels = null;
	private Boolean isRanked = false;
    private String type = null;
    private int size;

    /* Constructor */
    public DuelQueue(String name, Boolean isRanked, String type, int size)
    {
        this.name = name;
        this.isRanked = isRanked;
        this.type = type;
        this.size = size;
    }

    /* Methods */
    public boolean join(Team team)
    {
        if(team==null)
            return false;

        waitingTeams.put(team.getName(), team);
        return true;
    }

    public boolean leave(Team team)
    {
        if(team==null)
            return false;

        waitingTeams.remove( team.getName() );

        return true;
    }

    public LinkedList<Team> match()
    {
        return null;
    }

    public void addDuel(Duel duel)
    {
        duels.add(duel);
    }
    
    public String getName()
    {
    	return this.name;
    }
    
    public Boolean isRanked()
    {
    	return this.isRanked;
    }
    
    public String getType()
    {
    	return this.type;
    }
    public Boolean getIsRanked() 
    {
		return isRanked;
	}

	public void setIsRanked(Boolean isRanked) 
	{
		this.isRanked = isRanked;
	}

	public int getSize() 
	{
		return size;
	}
}
