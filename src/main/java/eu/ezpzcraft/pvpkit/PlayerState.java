package eu.ezpzcraft.pvpkit;

import com.flowpowered.math.vector.Vector3d;
import org.spongepowered.api.data.manipulator.mutable.PotionEffectData;
import org.spongepowered.api.data.manipulator.mutable.entity.FoodData;
import org.spongepowered.api.data.manipulator.mutable.entity.HealthData;
import org.spongepowered.api.entity.living.player.Player;

import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.Iterator;
import java.util.Optional;

/**
 * Player state representation:
 * - Location
 * - Rotation
 * - Health
 * - Food
 * - Inventory
 * - Potion effect
 */

public class PlayerState
{
    /* Variables */
    private Location<World> location = null;
    private Vector3d rotation = null;
    private HealthData health = null;
    private FoodData food = null;
    private Optional<PotionEffectData> effects = null;
    private Inventory inventory = null;

    /**
     * Constructor:
     * Save the state of a given player
     * @param player
     */
    public PlayerState(Player player)
    {   	
        this.location = player.getLocation();
        this.rotation = player.getRotation();

        this.health = player.getHealthData();
        this.food = player.getFoodData();

        this.effects = player.get( PotionEffectData.class );

        this.inventory = player.getInventory();
    }

    /**
     * Give this state to a given player
     * @param player
     */
    public void reset(Player player)
    {
        player.setLocation(location);
        player.setRotation(rotation);

        player.offer(health);
        player.offer(food);

        if( effects.isPresent() )
            player.offer(effects.get());

        player.getInventory().clear();

       /* Iterator<Inventory> it = player.getInventory().slots().iterator();
        for( Inventory slot: inventory.slots() )
        {
        	if(it.hasNext())
        		it.next().offer(slot.first());
        }*/
    }
}
