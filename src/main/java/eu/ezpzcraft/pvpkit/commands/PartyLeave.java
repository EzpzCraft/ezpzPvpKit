package eu.ezpzcraft.pvpkit.commands;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.CommandBlockSource;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import eu.ezpzcraft.pvpkit.EzpzPvpKit;
import eu.ezpzcraft.pvpkit.PvPPlayer;
import eu.ezpzcraft.pvpkit.Team;
import eu.ezpzcraft.pvpkit.Utils;

public class PartyLeave implements CommandExecutor
{
	@Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException 
    {
        if(src instanceof Player) 
        {   
        	Player source = (Player) src;
        	PvPPlayer pvpPlayer = EzpzPvpKit.getInstance().getPlayer(source.getIdentifier());
        	Team team = EzpzPvpKit.getInstance().getTeam(pvpPlayer.getTeam());
        	
        	// Player is alone in a team
        	if( team.getSize()==1 )
        		Utils.sendPartyMessage(source, Text.of("You are not in a party"));
        	// Handle remaining team of size 1
        	else
        	{
        		team.removePlayer(source.getIdentifier());
        		
        		// Reset the name of the team
        		if( team.getSize()==1 )
        			team.setName( EzpzPvpKit.getInstance().getPlayer(team.getPlayers().get(0)).getPlayer().getName() );
        	
        		Utils.sendPartyMessage(source, Text.of("You left the party"));
        	}
        }
        else if(src instanceof ConsoleSource) 
        	Utils.sendMessageC(src);
        else if(src instanceof CommandBlockSource) 
        	Utils.sendMessageCB(src);       

        return CommandResult.success();
    }
}
