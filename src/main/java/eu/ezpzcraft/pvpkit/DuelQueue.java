package eu.ezpzcraft.pvpkit;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;

/**
 * <b> Duelqueue, class representing a duelqueue. </b>
 * <p> A Duelqueue is a queue with a specific type and a specific size
 * allowing teams with the same size to join it 
 * and be added to the waitingTeams for this queue. 
 *
 */

public class DuelQueue
{
    private String name = null;
	// List of waiting teams
    private ArrayList<Team> waitingTeams = new ArrayList<Team>();
    // List of duels in this queue
    private LinkedList<Duel> duels = new LinkedList<Duel>();
    // DEFAULT NOTRANKED
	private Boolean isRanked = false; 
    private String type = null;
    // DEFAULT SIZE
    private int size = 1; 

    /* Constructor */
    public DuelQueue(String name, Boolean isRanked, String type, int size)
    {
        this.name = name;
        this.isRanked = isRanked;
        this.type = type;
        this.size = size;
    }

    /**
     * Add the specified team to the queue if sizes matched
     * @param team team to be added to the queue
     * @return <ul><li>True - team has been added to the queue</li>
     * 			<li>False - team is null or the team size doesn't match the queue size</li></ul>
     */
    public boolean join(Team team)
    {
    	//Avoid null
        if(team==null)
            return false;
    	//Check size
        if(team.getSize() != this.size)
            return false;       
        waitingTeams.add(team);
        return true;
    }

    /**
     * Remove the specified team of the queue
     * @param team team to be removed 
     * @return <ul><li>True - team has been removed</li>
     * 			<li>False - team is null or not in the queue</li></ul>
     */
    public boolean leave(Team team)
    {
        if(team==null)
            return false;
        // Is team in the queue
        if(waitingTeams.contains(team))
        	return false;
        waitingTeams.remove( team.getName() );
        return true;
    }

    /**
     * Match two teams who joined this queue.
     * 
     * @return ArrayList<Team> List of two team
     */
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
