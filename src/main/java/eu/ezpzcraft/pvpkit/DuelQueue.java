package eu.ezpzcraft.pvpkit;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;

public class DuelQueue
{
    /* Variables */
    private String name = null;
    private ArrayList<Team> waitingTeams = new ArrayList<Team>();
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

        waitingTeams.add(team);
        return true;
    }

    public boolean leave(Team team)
    {
        if(team==null)
            return false;

        waitingTeams.remove( team.getName() );

        return true;
    }

    public ArrayList<Team> match()
    {
    	if( waitingTeams.size()<2 )
    		return null;

    	// From here we know that there is at least one available couple
    	ArrayList<Team> result = new ArrayList<Team>();
    	result.add( waitingTeams.remove(0) );
    	
    	// Let's check the three (if any) last teams
    	int size = waitingTeams.size();
    	int max = waitingTeams.size()>=3 ? 3: size;
    	double cote = result.get(0).getScore(this.type);
    	
    	double currentMin = Double.MAX_VALUE, tmpMin;
    	int currentCandidate = 0;
    	
    	for(int i=1; i<max; ++i)
    	{
    		tmpMin = waitingTeams.get(i).getScore(this.type);
    		if( Math.abs(cote - currentMin) > Math.abs(cote - tmpMin) )
    		{
    			currentMin = tmpMin;
    			currentCandidate = i;
    		}
    	}
    		
    	result.add( waitingTeams.remove(currentCandidate) );
    	
        return result;
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
