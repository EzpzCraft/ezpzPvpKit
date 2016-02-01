package eu.ezpzcraft.pvpkit;

import java.util.LinkedHashMap;
import java.util.LinkedList;

public class DuelQueue
{
    /* Variables */
    private String name = null;
    private LinkedHashMap<String, Arena> freeArenas = null;
    private LinkedHashMap<String, Arena> usedArenas = null;
    private LinkedHashMap<String, Team> waitingTeams = null;
    private LinkedList<Duel> duels = null;
    private Boolean isRanked = false;
    private String type = null;

    /* Constructor */
    public DuelQueue(String name, Boolean isRanked, String type)
    {
        this.name = name;
        this.isRanked = isRanked;
        this.type = type;
        this.freeArenas = new LinkedHashMap<String, Arena>();
        this.usedArenas = new LinkedHashMap<String, Arena>();
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
}
