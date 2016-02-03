package eu.ezpzcraft.pvpkit;

import java.util.LinkedHashMap;
import java.util.Map;

public class TeamStats
{
	// <type, score>
	private LinkedHashMap<String,Integer> scores = new LinkedHashMap<String,Integer>();
	private LinkedHashMap<String,Integer> victories = new LinkedHashMap<String,Integer>();
	private LinkedHashMap<String,Integer> defeats = new LinkedHashMap<String,Integer>();
	
	public TeamStats()
	{
		// Fetch from DB
	}

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

    /**
     * Get the score of the team for a specific queue type
     * @param type
     * @return the score for this type
     */
    public int getScore(String type)
    {
       // return scores.get(type);
    	return 1000;
    }
    
    /**
     * Get the MEAN score of the team
     * @param type
     * @return the mean score
     */
    public int getMeanScore()
    {
    	int total = 0, cnt = 0;
    	
    	for(Map.Entry<String, Integer> entry: scores.entrySet())
    		total += entry.getValue().intValue();
    	
    	if(cnt==0)
    		return 0;
    	
    	return (int) total/cnt;
    }

    /**
     * Get the number of defeats of the team for a specific queue type
     * @param type
     * @return the number of defeats for this type
     */
    public int getDefeats(String type)
    {
        return this.defeats.get(type);
    }
    
    /**
     * Get the TOTAL number of defeats of the team 
     * @param type
     * @return the total number of defeats
     */
    public int getTotalDefeats()
    {
    	int total = 0, cnt = 0;
    	
    	for(Map.Entry<String, Integer> entry: defeats.entrySet())
    		total += entry.getValue().intValue();
    	
    	if(cnt==0)
    		return 0;
    	
    	return (int) total/cnt;
    }
    
    /**
     * Get the number of victories of the team for a specific queue type
     * @param type
     * @return the number of victories for this type
     */
    public int getVictories(String type)
    {
        return this.victories.get(type);
    }
    
    /**
     * Get the TOTAL number of victories of the team 
     * @param type
     * @return the total number of victories
     */
    public int getTotalVictories()
    {
    	int total = 0, cnt = 0;
    	
    	for(Map.Entry<String, Integer> entry: defeats.entrySet())
    		total += entry.getValue().intValue();
    	
    	if(cnt==0)
    		return 0;
    	
    	return (int) total/cnt;
    }

    /**
     * Get the number of duels played by the team
     * @return the number of duels played
     */
    public int getPlayed()
    {
        return getTotalDefeats()+getTotalVictories();
    }
}
