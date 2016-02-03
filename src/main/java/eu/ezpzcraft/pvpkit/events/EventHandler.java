package eu.ezpzcraft.pvpkit.events;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.data.Transaction;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.type.DyeColors;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.projectile.Arrow;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.entity.CollideEntityEvent;
import org.spongepowered.api.event.entity.DestructEntityEvent;
import org.spongepowered.api.event.entity.item.TargetItemEvent;
import org.spongepowered.api.event.item.inventory.ClickInventoryEvent;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.entity.Hotbar;
import org.spongepowered.api.item.inventory.property.SlotIndex;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

import eu.ezpzcraft.pvpkit.Database;
import eu.ezpzcraft.pvpkit.EzpzPvpKit;
import eu.ezpzcraft.pvpkit.PlayerState;
import eu.ezpzcraft.pvpkit.Team;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class EventHandler
{
    private HashMap<String,PlayerState> states = new HashMap<String,PlayerState>();
    private Database db = EzpzPvpKit.getInstance().getDatabase();

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
            
            //TODO REMOVE
            else if ( tr.getOriginal().getState().getType().equals(BlockTypes.SPONGE) )
            {
                Cause cause = event.getCause();
                Optional<Player> player = cause.first(Player.class);

                if(player.isPresent())
                {          
                	Team team;
					try 
					{
						team = new Team("MonEquipe");
	                	for(Player player2 : Sponge.getServer().getOnlinePlayers())
	                	{
	                		team.addPlayer(player2);
	                	}
	                    EzpzPvpKit.getInstance().getStartMatchSetup().addTeamStart(team.getName());
					} catch (Exception e) 
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

                }
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
            
            else if ( tr.getOriginal().getState().getType().equals(BlockTypes.EMERALD_BLOCK) )
            {
                EzpzPvpKit.getLogger().info("Emerald break");
                Cause cause = event.getCause();
                Optional<Player> player = cause.first(Player.class);

                if(player.isPresent())
                {
                	JoinEventHandler.removeScoreboard( EzpzPvpKit.getInstance().getPlayer(player.get().getIdentifier()));
                }
            }
            
            else if ( tr.getOriginal().getState().getType().equals(BlockTypes.COBBLESTONE) )
            {
                EzpzPvpKit.getLogger().info("Cobblestone break - reset");
                Cause cause = event.getCause();
                Optional<Player> player = cause.first(Player.class);

                // TODO: packet client
                // https://bukkit.org/threads/util-edit-itemstack-attributes-adding-speed-damage-or-health-bonuses.158316/
                if(player.isPresent())
                {
                    Hotbar hotbar = player.get().getInventory().query(Hotbar.class);

                    // Ranked
                    ItemStack ranked = ItemStack.builder().itemType(ItemTypes.DIAMOND_SWORD).quantity(1).build();
                    Text title = Text.builder("Ranked").color(TextColors.AQUA).style(TextStyles.BOLD).build();
                    List<Text> lore = new ArrayList<Text>();
                    lore.add( Text.builder("Remaining ranked: ").color(TextColors.GOLD)
                                  .append(Text.builder("5").color(TextColors.YELLOW).build()).build() );
                    lore.add( Text.builder("Click to play").color(TextColors.GRAY).build() );

                    ranked.offer(Keys.DISPLAY_NAME, title);
                    ranked.offer(Keys.ITEM_LORE, lore);
                    //ranked.offer(Keys.ATTACK_DAMAGE, 8.5); // TODO remove
                    //ranked.offer(Keys)

                    // Unranked
                    ItemStack unranked = ItemStack.builder().itemType(ItemTypes.IRON_SWORD).quantity(1).build();
                    title = Text.builder("Unranked").color(TextColors.AQUA).style(TextStyles.BOLD).build();
                    lore = new ArrayList<Text>();
                    lore.add( Text.builder("Unlimited").color(TextColors.GOLD).build() );
                    lore.add( Text.builder("Click to play").color(TextColors.GRAY).build() );

                    unranked.offer(Keys.DISPLAY_NAME, title);
                    unranked.offer(Keys.ITEM_LORE, lore);

                    // PlayerHead TODO: player head
                    ItemStack stats = ItemStack.builder().itemType(ItemTypes.SKULL).quantity(1).build();
                    title = Text.builder("Statistics").color(TextColors.AQUA).style(TextStyles.BOLD).build();
                    lore = new ArrayList<Text>();
                    lore.add( Text.builder("Player: ").color(TextColors.GOLD)
                              .append(Text.builder("Nomeho").color(TextColors.YELLOW).build() ).build() );
                    lore.add( Text.builder("Click to see").color(TextColors.GRAY).build() );

                    stats.offer(Keys.DISPLAY_NAME, title);
                    stats.offer(Keys.ITEM_LORE, lore);


                    // Lang
                    ItemStack lang = ItemStack.builder().itemType(ItemTypes.BANNER).quantity(1).build();
                    title = Text.builder("Lang").color(TextColors.AQUA).style(TextStyles.BOLD).build();
                    lore = new ArrayList<Text>();
                    lore.add( Text.builder("Choose your laguage").color(TextColors.GOLD).build() );
                    lore.add( Text.builder("Choice: ").color(TextColors.GRAY)
                             .append(Text.builder("English").color(TextColors.YELLOW).build() ).build() );

                    lang.offer(Keys.DISPLAY_NAME, title);
                    lang.offer(Keys.ITEM_LORE, lore);

                    lang.offer(Keys.DYE_COLOR, DyeColors.RED);
                    lang.offer(Keys.BANNER_BASE_COLOR, DyeColors.GREEN);
                    
                    //PatternListValue patterns = lang.get(Keys.BANNER_PATTERNS).get();
                    //patterns.add(BannerPatternShapes.CREEPER, DyeColors.BLUE);                    
                    
                    //lang.offer(Keys.BANNER_PATTERNS, patterns);                    
                    //DataContainer data = lang.toContainer();
                    //lang.setRawData(data);
                    //data.set();
                    //Banner banner = (Banner) lang.getItem();
                    //banner.patternsList().add(BannerPatternShapes.DIAGONAL_LEFT, DyeColors.BLUE);
                    //lang.offer(Keys.BANNER_BASE_COLOR, DyeColors.WHITE);

                    // Spectator
                    ItemStack spectator = ItemStack.builder().itemType(ItemTypes.COMPASS).quantity(1).build();
                    title = Text.builder("Spectator").color(TextColors.AQUA).style(TextStyles.BOLD).build();
                    lore = new ArrayList<Text>();
                    lore.add( Text.builder("Click to see another player").color(TextColors.GOLD).build() );

                    spectator.offer(Keys.DISPLAY_NAME, title);
                    spectator.offer(Keys.ITEM_LORE, lore);

                    // Finally place items in hotbar
                    // TODO good placement
                    hotbar.set(new SlotIndex(8), stats);

                    hotbar.offer(ranked);
                    hotbar.offer(unranked);
                    hotbar.offer(stats);
                    hotbar.offer(lang);
                    hotbar.offer(spectator);
                    
                    //final DisplayNameData itemName = Sponge.getGame().getDataManager().getManipulatorBuilder(DisplayNameData.class).get().create();
                    //itemName.set(Keys.DISPLAY_NAME, Text.of(TextColors.DARK_GREEN, "lol"));
                    //ItemStack.Builder i = ItemStack.builder();
                    //hotbar.offer( i.itemType(ItemTypes.STONE_AXE).itemData(itemName).quantity(1).build() );
                    
                    
                    // Broadcaster
                }
            }
        }
    }

    // No drop, no deplace
    @Listener
    public void handleClick2(TargetItemEvent event)
    {
        Cause cause = event.getCause();
        Optional<Player> player = cause.first(Player.class);

        if(player.isPresent())
        {
            EzpzPvpKit.getLogger().info("target " + player.get().getName() );
        }

    }

    @Listener
    public void handleClick4(ClickInventoryEvent.Primary event)
    {
        EzpzPvpKit.getLogger().info("Primary"); // Left click inside inventary
    }
    
    // Death screen remover ?
    @Listener
    public void handleDeath(DestructEntityEvent.Death event)
    {        
        Living living = event.getTargetEntity();
        
        if( !(living instanceof Player) )       
        	return;

        Player player = (Player) living;
        player.offer(Keys.HEALTH, player.get(Keys.MAX_HEALTH).get());        
        player.setLocationSafely( player.getWorld().getSpawnLocation() );
    }    

    // TODO supprimer toute interaction avec les blocks dans le spawn
    
    // Death screen remover
    @Listener
    public void test5(CollideEntityEvent event)
    {        
        List<Entity> entities = event.getEntities();
        
        for(Entity entity: entities)
        {
        	if( entity instanceof Arrow) {
        	EzpzPvpKit.getLogger().info( "COLLISION" );
        	EzpzPvpKit.getLogger().info( entity.getType().getName() ); }
        }
    }  
}
