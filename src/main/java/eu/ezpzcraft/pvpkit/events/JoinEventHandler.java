package eu.ezpzcraft.pvpkit.events;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.mutable.PotionEffectData;
import org.spongepowered.api.effect.potion.PotionEffect;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

import eu.ezpzcraft.pvpkit.Config;
import eu.ezpzcraft.pvpkit.EzpzPvpKit;
import eu.ezpzcraft.pvpkit.PvPPlayer;

public class JoinEventHandler 
{	
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
        
        // TP to spawn
        player.setLocation( player.getWorld().getSpawnLocation() );
        
        // Reset player
        player.getInventory().clear();        
        //player.get(Keys.POTION_EFFECTS)
        // Give items
        
        // Send MOTD
        sendMOTD(pvpPlayer);
    }
    
    private void sendMOTD(PvPPlayer pvpPlayer)
    {
    	String welcome = pvpPlayer.getPlayer().get(Keys.FIRST_DATE_PLAYED)
    			                  .equals( pvpPlayer.getPlayer().get(Keys.LAST_DATE_PLAYED) ) 
    			                  ? "     Welcome" : "     Welcome back";
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
}
