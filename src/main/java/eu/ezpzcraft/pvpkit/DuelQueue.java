package eu.ezpzcraft.pvpkit;

import java.util.LinkedList;

public class DuelQueue
{
    private String name = null;
    private LinkedList<Arena> freeArenas = null;
    private LinkedList<Arena> usedArenas = null;
    private LinkedList<Team> waitingTeams = null;
    private LinkedList<Duel> duels = null;

    public boolean join(Team team)
    {
        return true;
    }

    public boolean leave(Team team)
    {
        return true;
    }

    public LinkedList<Team> match()
    {
        return null;
    }

    public void addDuel()
    {

    }
}
