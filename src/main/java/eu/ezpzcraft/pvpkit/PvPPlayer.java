package eu.ezpzcraft.pvpkit;


import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public class PvPPlayer
{
    /* Variable */
    private Player player = null;
    private float score = 0;
    private PlayerState state = null;




    /* Constructor */
    public PvPPlayer(Player player)
    {
        this.player = player;
        // fetch score from DB
    }

    /* Methods */
    
	

	
    public void setState()
    {
        state = new PlayerState(this.player);
    }

    public PlayerState getState()
    {
        return state;
    }

    public Player getPlayer()
    {
        return player;
    }
}
