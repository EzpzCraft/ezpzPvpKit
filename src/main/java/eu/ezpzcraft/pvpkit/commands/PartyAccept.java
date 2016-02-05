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

public class PartyAccept implements CommandExecutor
{
	@Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException 
    {
        if(src instanceof Player) 
        {   
        	// args <party> = teamName
        	Player source = (Player) src;
        	PvPPlayer pvpPlayer = EzpzPvpKit.getInstance().getPlayer(source.getIdentifier());
        	String party = args.<String>getOne("party").get(); 
        	Team currentTeam = EzpzPvpKit.getInstance().getTeam(pvpPlayer.getTeam());
        	Team newTeam = EzpzPvpKit.getInstance().getTeam(party);
        	
        	// Check if invitation exists
        	if( !pvpPlayer.isInvited(newTeam) )
        	{
        		Utils.sendPartyMessage(source, Text.of("You are not invited in this party"));
        		return CommandResult.success();
        	}
        	
        	// Player is alone in a team, can join a new team
        	if( currentTeam.getSize()==1 )
        	{
        		newTeam.addPlayer(source.getIdentifier());
        		pvpPlayer.setTeam(party);
        	}
        	// Player must leave his current party before...
        	else
        		Utils.sendPartyMessage(source, Text.of("You already are in party. Leave this one before."));
        }
        else if(src instanceof ConsoleSource) 
        	Utils.sendMessageC(src);
        else if(src instanceof CommandBlockSource) 
        	Utils.sendMessageCB(src);       

        return CommandResult.success();
    }
}
