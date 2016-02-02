package eu.ezpzcraft.pvpkit;

import com.flowpowered.math.vector.Vector2i;
import com.flowpowered.math.vector.Vector3d;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.mutable.PotionEffectData;
import org.spongepowered.api.data.manipulator.mutable.entity.FoodData;
import org.spongepowered.api.data.manipulator.mutable.entity.HealthData;
import org.spongepowered.api.effect.particle.ParticleType;
import org.spongepowered.api.effect.potion.PotionEffect;
import org.spongepowered.api.effect.potion.PotionEffectTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.Slot;
import org.spongepowered.api.item.inventory.entity.Hotbar;
import org.spongepowered.api.item.inventory.property.SlotPos;
import org.spongepowered.api.item.inventory.type.GridInventory;
import org.spongepowered.api.item.inventory.type.Inventory2D;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Vector;

public class PlayerState
{
    /* Variables */
    private Location<World> location = null;
    private Vector3d rotation = null;
    private HealthData health = null;
    private FoodData food = null;
    private Optional<PotionEffectData> effects = null;

    private Inventory inventory = null;
    private ItemStack helmet = null;
    private ItemStack chestplate = null;
    private ItemStack leggings = null;
    private ItemStack boots = null;

    /* Constructor */
    public PlayerState(Player player)
    {
        // TODO: check if get() exsits
        this.location = player.getLocation();
        this.rotation = player.getRotation();

        this.health = player.getHealthData();
        this.food = player.getFoodData();

        this.effects = player.get( PotionEffectData.class );

        this.inventory = player.getInventory();
        // Inventory slot: player.getInventory().query(Hotbar.class).slots()

        /*
        if( player.getHelmet().isPresent() )
            this.helmet = player.getHelmet().get();
        if( player.getChestplate().isPresent() )
            this.chestplate = player.getChestplate().get();
        if( player.getLeggings().isPresent() )
            this.leggings = player.getLeggings().get();
        if( player.getBoots().isPresent() )
            this.boots = player.getBoots().get();
        */
    }

    public void reset(Player player)
    {
        player.setLocation(location);
        player.setRotation(rotation);

        player.offer(health); // player.get().offer(Keys.FOOD_LEVEL, 1);
        player.offer(food); // player.get().offer(Keys.HEALTH, 1.0);

        if( effects.isPresent() )
            player.offer(effects.get());

        // Inventory TODO
        player.getInventory().clear();

        GridInventory gridInventory = inventory.query(GridInventory.class);
        Vector2i dim = gridInventory.getDimensions();

        for(int i=0; i<dim.getX(); ++i)
        {
            for(int j=0; j<dim.getY(); ++j)
            {
                //EzpzPvpKit.getLogger().info("Item: " + i + "," + j);
                GridInventory playerGridInventory = player.getInventory().query(GridInventory.class);

                playerGridInventory.set(i,j, ItemStack.builder().itemType(ItemTypes.DIAMOND_SWORD).quantity(1).build() );
            }
        }
        
        //Inventory2D inv = player.getInventory().query(Inventory2D.class);
        //inv.set(new SlotPos(1,1), ItemStack.builder().itemType(ItemTypes.APPLE).quantity(1).build());
        /*
        if(helmet!=null)
            player.setHelmet(helmet);
        if(chestplate!=null)
            player.setChestplate(chestplate);
        if(leggings!=null)
            player.setLeggings(leggings);
        if(boots!=null)
            player.setBoots(boots);
            */
    }
}
