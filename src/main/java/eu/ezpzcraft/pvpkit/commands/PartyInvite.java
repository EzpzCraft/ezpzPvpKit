package eu.ezpzcraft.pvpkit.commands;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.CommandBlockSource;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

import eu.ezpzcraft.pvpkit.EzpzPvpKit;
import eu.ezpzcraft.pvpkit.Utils;

public class PartyInvite implements CommandExecutor
{
	@Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException 
    {
        if(src instanceof Player) 
        {   
        	String name = args.<String>getOne("name").get(); 
        	Player source = (Player) src;
        	//Player online ?        	
        	if(Sponge.getServer().getPlayer(name).isPresent()) 
        	{
        		Player dest = Sponge.getServer().getPlayer(name).get();       		
        		String team = EzpzPvpKit.getInstance().getPlayer(source.getIdentifier()).getTeam();
        		// ADD inv
        		EzpzPvpKit.getInstance().getPlayer(dest.getIdentifier()).addInvitations(team);
        		// Get players in the team        		
        		/*Text playerList = Text.of(TextColors.GRAY, "Party composed of :");
        		for(String it : EzpzPvpKit.getInstance().getTeam(team).getPlayers())
        		{
        			it = EzpzPvpKit.getInstance().getPlayer(it).getPlayer().getName();
        			playerList.of(TextStyles.BOLD, TextColors.YELLOW, "\n• "); 
        		}*/
        		Utils.sendPartyMessage(dest, Text.builder(source.getName()+" invite you to join his  ")
        										.append(Text.builder("party  ")
        													.onHover(TextActions.showText(Text.of("")))
        													.style(TextStyles.BOLD)
        													.style(TextStyles.ITALIC)
        													.build())
        										.append(Utils.getYesNo("/party join "+team, "Click to join the party",
        															   "/party refuse "+team, "Click to deny the invitation"))
        										.build());
        		
        	}
        	else
        		Utils.sendPartyMessage(source, Text.of("This player is not connected"));
      	
        }
        else if(src instanceof ConsoleSource) 
        	Utils.sendMessageC(src);
        else if(src instanceof CommandBlockSource) 
        	Utils.sendMessageCB(src);       

        return CommandResult.success();
    }
}
