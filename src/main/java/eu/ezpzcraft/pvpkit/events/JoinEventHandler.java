package eu.ezpzcraft.pvpkit.events;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.mutable.PotionEffectData;
import org.spongepowered.api.data.type.DyeColors;
import org.spongepowered.api.effect.potion.PotionEffect;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.entity.Hotbar;
import org.spongepowered.api.item.inventory.property.SlotIndex;
import org.spongepowered.api.scoreboard.Scoreboard;
import org.spongepowered.api.scoreboard.critieria.Criteria;
import org.spongepowered.api.scoreboard.displayslot.DisplaySlots;
import org.spongepowered.api.scoreboard.objective.Objective;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.chat.ChatTypes;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

import eu.ezpzcraft.pvpkit.Config;
import eu.ezpzcraft.pvpkit.EzpzPvpKit;
import eu.ezpzcraft.pvpkit.PvPPlayer;
import eu.ezpzcraft.pvpkit.Team;

public class JoinEventHandler 
{	
	Scoreboard scoreboard = null;
	Objective objective = null;
	
	public JoinEventHandler()
	{
		this.scoreboard = Scoreboard.builder().build();
		this.objective = Objective.builder().name("Score")
						 .displayName( Text.of(TextColors.GOLD, "score") )
						 .criterion(Criteria.DUMMY)
						 .build();
		
		scoreboard.addObjective(objective);
		scoreboard.updateDisplaySlot(objective, DisplaySlots.BELOW_NAME);
	}
	
    @Listener
    public void handleConnection(ClientConnectionEvent.Join event)
    {
        Cause cause = event.getCause();
        Optional<Player> OptPlayer = cause.first(Player.class);

        if( !OptPlayer.isPresent() )
            return;

        Player player = OptPlayer.get();

        // Save connection statistics into DB
        EzpzPvpKit.getInstance().getDatabase().updateJoinPlayer(player);
        
        // Load player informations
        EzpzPvpKit.getInstance().getDatabase().loadPlayer(player);
        PvPPlayer pvpPlayer = EzpzPvpKit.getInstance().getPlayer(player.getIdentifier());
        pvpPlayer.setTeam( new Team(pvpPlayer.getPlayer().getName()) ); // Set cote, transfer score
        pvpPlayer.getTeam().addPlayer(pvpPlayer.getPlayer());
        
        // TP to spawn
        player.setLocation( player.getWorld().getSpawnLocation() );
        
        // Reset player
        player.getInventory().clear();  
        player.remove(Keys.POTION_EFFECTS);
        
        // Give items
        giveItem(pvpPlayer);
        
        // Send MOTD
        sendMOTD(pvpPlayer);
        
        // Set scoreboard
        setScoreboard(pvpPlayer);
    }
    
    private static void sendMOTD(PvPPlayer pvpPlayer)
    {
    	String welcome = pvpPlayer.getPlayer().get(Keys.FIRST_DATE_PLAYED)
    			                  .equals( pvpPlayer.getPlayer().get(Keys.LAST_DATE_PLAYED) ) 
    			                  ? "     Welcome " : "     Welcome back ";
        Text motd;        
		try 
		{
			motd = Text.builder().append(Text.of(TextColors.DARK_GRAY,TextStyles.STRIKETHROUGH, "---------------" ))
				                 .append(Text.of(TextColors.DARK_RED,TextStyles.BOLD, " E", TextStyles.RESET,TextColors.DARK_RED,"zpz",
				                		         TextColors.DARK_RED,TextStyles.BOLD, "C",TextStyles.RESET,TextColors.DARK_RED,"raft " ))
				                 .append(Text.of(TextColors.DARK_GRAY,TextStyles.STRIKETHROUGH, "---------------\n\n" ))
				                 .append(Text.of(TextColors.DARK_GRAY, welcome, TextColors.GOLD,TextStyles.ITALIC,pvpPlayer.getPlayer().getName() + " \n\n" ))
				                 .append(Text.of(TextActions.showText(Text.of("Visit our website ! \n", TextColors.GRAY, "Store, Forum, Vote")),
				                		 "     ", TextColors.GOLD, "• ", TextColors.DARK_GRAY, "Website: " ))
				                 .append(Text.of(TextActions.showText(Text.of("Visit our website ! \n", TextColors.GRAY, "Store, Forum, Vote")),
				                		 Text.of(TextActions.openUrl(new URL(Config.WEBSITE)), TextColors.GRAY,Config.WEBSITE + "\n" )))
				                 .append(Text.of("     ", TextColors.GOLD, "• ", TextColors.DARK_GRAY, "Remaining ranked: " ))
				                 .append(Text.of(TextActions.openUrl(new URL(Config.WEBSITE)), TextColors.GRAY,pvpPlayer.getRemainingRanked()+ "\n\n" ))
				                 .append(Text.of(TextColors.DARK_GRAY,TextStyles.STRIKETHROUGH, "-------------------------------------------\n" )) 
				                 .build();
			
			pvpPlayer.getPlayer().sendMessage(motd);
		} 
		catch (MalformedURLException e) 
		{
			EzpzPvpKit.getLogger().info("Error while creating MOTD url");
		} 
    }
    
    public static void giveItem(PvPPlayer pvpPlayer)
    {
        Hotbar hotbar = pvpPlayer.getPlayer().getInventory().query(Hotbar.class);

        // Ranked
        ItemStack ranked = ItemStack.builder().itemType(ItemTypes.DIAMOND_SWORD).quantity(1).build();
        Text title = Text.builder("Ranked").color(TextColors.AQUA).style(TextStyles.BOLD).build();
        List<Text> lore = new ArrayList<Text>();
        lore.add( Text.of(TextColors.GOLD,"Remaining ranked: ",
        		          TextColors.YELLOW, pvpPlayer.getRemainingRanked()) );
        lore.add( Text.builder("Click to play").color(TextColors.GRAY).build() );
        ranked.offer(Keys.DISPLAY_NAME, title);
        ranked.offer(Keys.ITEM_LORE, lore);

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
        lore.add( Text.of(TextColors.GOLD, "Player: ", TextColors.YELLOW, pvpPlayer.getPlayer().getName()) );
        lore.add( Text.of(TextColors.GOLD, "Rank: ", TextColors.YELLOW, pvpPlayer.getRank()) );
        lore.add( Text.builder("Click to see").color(TextColors.GRAY).build() );
        stats.offer(Keys.DISPLAY_NAME, title);
        stats.offer(Keys.ITEM_LORE, lore);

        // Lang
        ItemStack lang = ItemStack.builder().itemType(ItemTypes.BANNER).quantity(1).build();
        title = Text.builder("Lang").color(TextColors.AQUA).style(TextStyles.BOLD).build();
        lore = new ArrayList<Text>();
        lore.add( Text.builder("Choose your laguage").color(TextColors.GOLD).build() );
        lore.add( Text.of(TextColors.GRAY, "Choice: ", TextColors.YELLOW, pvpPlayer.getLang()) );
        lang.offer(Keys.DISPLAY_NAME, title);
        lang.offer(Keys.ITEM_LORE, lore);

        // Spectator
        ItemStack spectator = ItemStack.builder().itemType(ItemTypes.COMPASS).quantity(1).build();
        title = Text.builder("Spectator").color(TextColors.AQUA).style(TextStyles.BOLD).build();
        lore = new ArrayList<Text>();
        lore.add( Text.builder("Click to see another player").color(TextColors.GOLD).build() );
        spectator.offer(Keys.DISPLAY_NAME, title);
        spectator.offer(Keys.ITEM_LORE, lore);

        // Finally place items in the hotbar
        hotbar.offer(ranked);
        hotbar.offer(unranked);
        hotbar.offer(stats);
        hotbar.offer(lang);
        hotbar.offer(spectator);
    }
    
    public void setScoreboard(PvPPlayer pvpPlayer)
    {
		objective.getOrCreateScore(pvpPlayer.getPlayer().getTeamRepresentation()).setScore( pvpPlayer.getScore() );
		pvpPlayer.getPlayer().setScoreboard(scoreboard);
    }
    
    // The scoreboard of the given player will no more be visible
    public static void removeScoreboard(PvPPlayer pvpPlayer)
    {
    	pvpPlayer.getPlayer().setScoreboard( Scoreboard.builder().build() );
    }
    
    public static void setBossBar(PvPPlayer pvpPlayer)
    {
    	// TODO
    }
}
