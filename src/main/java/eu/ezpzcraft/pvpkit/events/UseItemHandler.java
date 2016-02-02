package eu.ezpzcraft.pvpkit.events;

import java.util.LinkedHashMap;
import java.util.Optional;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.entity.DisplaceEntityEvent;
import org.spongepowered.api.event.entity.projectile.LaunchProjectileEvent;
import org.spongepowered.api.event.entity.projectile.TargetProjectileEvent;
import org.spongepowered.api.event.item.inventory.UseItemStackEvent;

import eu.ezpzcraft.pvpkit.EzpzPvpKit;

public class UseItemHandler
{
	private LinkedHashMap<String, Integer> enderpearlCD = new LinkedHashMap<String, Integer>();
	protected static final int defaultCD = 15000; // 15sec
	
	// Enderpearl CD => ChatTypes....
	// Enderpearl prevent on 3,2,1,go 
	// Arrow prevent on 3,2,1,go
	// Arrow cannot be picked up if target is missed
	
    @Listener
    public void useItemHandler(UseItemStackEvent.Finish event)
    {
        Cause cause = event.getCause();
        Optional<Player> player = cause.first(Player.class);

        if(!player.isPresent())
            return;
        
        Player p = player.get();

        EzpzPvpKit.getLogger().info("|USE| used by " + p.getName());
        //EzpzPvpKit.getLogger().info("|USE| item " + event.getItemStackInUse().getDefault());
    }
    
    @Listener
    public void test1(LaunchProjectileEvent event)
    {
    	EzpzPvpKit.getLogger().info("|USE| launched ");
    }
    
    @Listener
    public void test3(TargetProjectileEvent event)
    {
    	EzpzPvpKit.getLogger().info("|USE| target");
    }
    
    public void addCD(String playerName)
    {
    	enderpearlCD.put(playerName, 0);
    }
    
    private int getCD(String playerName)
    {
    	if(enderpearlCD.containsKey(playerName) )
    		return enderpearlCD.get(playerName);
    	
    	return 0;
    }
}
