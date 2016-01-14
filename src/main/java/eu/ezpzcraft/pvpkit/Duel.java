package eu.ezpzcraft.pvpkit;

public class Duel
{
    private Arena arena = null;
    private Team team1 = null;
    private Team team2 = null;

    public Duel(Team team1, Team team2, Arena arena)
    {
        this.team1 = team1;
        this.team2 = team2;
        this.arena = arena;
    }

    public void start()
    {
        // Save pos+orientation
        // Save health
        // Save hunger
        // Save effects

        // clear inventory
        // clear effect

        // TP (pos+orientation)
        // set health
        // set hunger
        // set inventory
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
}
