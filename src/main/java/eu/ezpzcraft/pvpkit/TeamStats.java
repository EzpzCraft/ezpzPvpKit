package eu.ezpzcraft.pvpkit;

public class TeamStats
{
    private float score;
    private int victories;
    private int defeats;

    public void addDefeat(Team team1, Team team2)
    {
        // TODO:
        // - Compute new score via ELO
        // - Save it in DB
    }

    public void addVictory(Team team1, Team team2)
    {
        // TODO:
        // - Compute new score via ELO
        // - Save it in DB
    }

    public float getScore()
    {
        return  this.score;
    }

    public int getDefeats()
    {
        return this.defeats;
    }

    public int getVictories()
    {
        return this.victories;
    }

    public int getPlayed()
    {
        return getDefeats()+getVictories();
    }
}
