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

    /* Constructor */
    public DuelQueue(String name)
    {
        this.name = name;
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
}
