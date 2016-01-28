package eu.ezpzcraft.pvpkit;

import com.sun.xml.internal.ws.api.message.Messages;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.data.Transaction;
import org.spongepowered.api.data.manipulator.mutable.DisplayNameData;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.entity.health.HealthModifier;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.entity.Hotbar;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Optional;

public class EventHandler
{
    private HashMap<String,PlayerState> states = new HashMap<String,PlayerState>();
    private Database db = EzpzPvpKit.getInstance().getDatabase();

    // Connection
    @Listener
    public void handleConnection(ClientConnectionEvent.Join event)
    {
        Cause cause = event.getCause();
        Optional<Player> OptPlayer = cause.first(Player.class);

        if( !OptPlayer.isPresent() )
            return;

        Player player = OptPlayer.get();

        // Spawn TP

        try
        {
            db.createDB();
            db.addQueue("1");
            db.addQueue("2");

            db.updateJoinPlayer(player);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    // Disconnection
    @Listener
    public void handleDeconnection(ClientConnectionEvent.Disconnect event)
    {
        Cause cause = event.getCause();
        Optional<Player> OptPlayer = cause.first(Player.class);

        if( !OptPlayer.isPresent() )
            return;

        Player player = OptPlayer.get();

        try
        {
            db.updateLeavePlayer(player);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    // Block modification
    @Listener
    public void handleBlocks(ChangeBlockEvent.Break event)
    {
        if (event.isCancelled()) return;

        for (Transaction<BlockSnapshot> tr : event.getTransactions())
        {
            if ( tr.getOriginal().getState().getType().equals(BlockTypes.DIRT) )
            {
                EzpzPvpKit.getLogger().info("Dirt break - save");
                Cause cause = event.getCause();
                Optional<Player> player = cause.first(Player.class);

                if(player.isPresent())
                    states.put( player.get().getName(), new PlayerState(player.get()) );
            }
            else if ( tr.getOriginal().getState().getType().equals(BlockTypes.STONE) )
            {
                EzpzPvpKit.getLogger().info("Stone break - reset");
                Cause cause = event.getCause();
                Optional<Player> player = cause.first(Player.class);

                if(player.isPresent())
                {
                    if( states.containsKey(player.get().getName()) )
                        states.get(player.get().getName()).reset(player.get());
                }
            }
            else if ( tr.getOriginal().getState().getType().equals(BlockTypes.COBBLESTONE) )
            {
                EzpzPvpKit.getLogger().info("Cobblestone break - reset");
                Cause cause = event.getCause();
                Optional<Player> player = cause.first(Player.class);

                if(player.isPresent())
                {
                    // Ranked
                    ItemStack ranked = ItemStack.builder().itemType(ItemTypes.DIAMOND_SWORD).quantity(1).build();
                    ranked.get(DisplayNameData.class).get().displayName();

                    /*
                    ItemStack exitDoor = ItemStack.builder().itemType(ItemTypes.WOODEN_DOOR).quantity(1).build();
                    DisplayNameData exitDoorDisp = exitDoor.get(DisplayNameData.class).get();
                    exitDoorDisp.displayName().set(Text.builder("Exit to Hub").color(TextColors.DARK_BLUE).build());
                    */

                    if( ranked.get(DisplayNameData.class).isPresent() )
                        EzpzPvpKit.getLogger().info("TEST " + ranked.get(DisplayNameData.class).get());
                    //ranked.get(DisplayNameData.class).get().displayName().set(Text.builder("test").color(TextColors.AQUA).build() );

                    player.get().getInventory().query(Hotbar.class).offer(ranked);

                    // Unranked
                    ItemStack unranked = ItemStack.builder().itemType(ItemTypes.IRON_SWORD).quantity(1).build();
                    player.get().getInventory().query(Hotbar.class).offer(unranked);

                    // PlayerHead
                    ItemStack head = ItemStack.builder().itemType(ItemTypes.SKULL).quantity(1).build();
                    player.get().getInventory().query(Hotbar.class).offer(head);

                    // Spectator
                    ItemStack spectator = ItemStack.builder().itemType(ItemTypes.COMPASS).quantity(1).build();
                    player.get().getInventory().query(Hotbar.class).offer(spectator);
                }
            }
        }
    }
}
