package eu.ezpzcraft.pvpkit;

import java.util.ArrayList;
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
	// List of waiting teams (name)
    private ArrayList<String> waitingTeams = new ArrayList<String>();
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
        waitingTeams.add(team.getName());
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
     * Match two teams who joined this queue:
     * Matching is done by comparing the three first teams (team2,3,4) 
     * score with the first team (team1). The pair <team1, teamX> is then
     * created according to:
     * - the smallest score difference
     * - a probability of 0.6
     * 
     * @return ArrayList<String> List of two team
     */
    public ArrayList<String> match()
    {
    	if( waitingTeams.size()<2 )
    		return null;

    	// From here we know that there is at least one available couple
    	ArrayList<String> result = new ArrayList<String>();
    	result.add( waitingTeams.remove(0) );
    	
    	// Let's check the three (if any) last teams
    	int size = waitingTeams.size();
    	int max = waitingTeams.size()>=3 ? 3: size;
    	double cote = EzpzPvpKit.getInstance().getTeam( result.get(0) ).getStats().getScore(this.type);
    	
    	double currentMin = Double.MAX_VALUE, tmpMin;
    	int currentCandidate = 0;
    	
    	for(int i=1; i<max; ++i)
    	{
    		tmpMin = EzpzPvpKit.getInstance().getTeam(waitingTeams.get(i)).getStats().getScore(this.type);
    		if( Math.abs(cote - currentMin) > Math.abs(cote - tmpMin) )
    		{
    			currentMin = tmpMin;
    			currentCandidate = i;
    		}
    	}
    		
    	// Add randomness to avoid repetition
    	double random = Math.random();    	
    	switch(currentCandidate)
    	{
	    	case 0:
	        	if( random <= 0.6 )
	        		currentCandidate = 0;
	        	else if( random <= 0.8 )
	        		currentCandidate = 1;
	        	else
	        		currentCandidate = 2;
	    		break;
	    	case 1:
	        	if( random <= 0.6 )
	        		currentCandidate = 1;
	        	else if( random <= 0.8 )
	        		currentCandidate = 0;
	        	else
	        		currentCandidate = 2;
	    		break;
	    	default:
	        	if( random <= 0.6 )
	        		currentCandidate = 2;
	        	else if( random <= 0.8 )
	        		currentCandidate = 0;
	        	else
	        		currentCandidate = 1;
	    		break;    	
    	}    	
    	
    	result.add( waitingTeams.remove(currentCandidate) );
    	
        return result;
    }

    /**
     * Add the duel to the queue
     * @param duel
     */
    public void addDuel(Duel duel)
    {
        duels.add(duel);
    }
    
    /**
     * Get the queue name
     * @return name
     */
    public String getName()
    {
    	return this.name;
    }
    
    /**
     * Determine if this is a ranked queue
     * @return true if the queue is ranked false otherwise
     */
    public Boolean isRanked()
    {
    	return this.isRanked;
    }
    
    /**
     * Get the queue type
     * @return type as a String
     */
    public String getType()
    {
    	return this.type;
    }

    /**
     * Define if the queue is ranked or not
     * @param isRanked
     */
	public void setIsRanked(Boolean isRanked) 
	{
		this.isRanked = isRanked;
	}

	/**
	 * Get the queue size
	 * @return the size
	 */
	public int getSize() 
	{
		return size;
	}
}
