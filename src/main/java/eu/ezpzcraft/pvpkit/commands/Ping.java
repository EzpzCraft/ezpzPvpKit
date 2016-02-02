package eu.ezpzcraft.pvpkit.commands;

import java.util.LinkedList;
import java.util.Optional;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.CommandBlockSource;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.pagination.PaginationService;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

import eu.ezpzcraft.pvpkit.EzpzPvpKit;
import eu.ezpzcraft.pvpkit.Utils;

public class Ping  implements CommandExecutor
{
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException 
    {
        if(src instanceof Player) 
        {   
        	Player dest, source = (Player) src;
        	
        	String arg = args.<String>getOne("player").get();     	
        	Optional<Player> optPlayer  = Sponge.getGame().getServer().getPlayer(arg);
        	
        	if( !optPlayer.isPresent() )
        	{
        		Utils.sendKitMessage(source, Text.of(TextColors.RED, "Cannot find player"));
        		return CommandResult.success();
        	} 	 
        	
        	dest = optPlayer.get();    	
        	Utils.sendPingMessage(source, dest.getConnection().getPing());
        }
        else if(src instanceof ConsoleSource) 
        {
        	EzpzPvpKit.getInstance().getUtils().sendMessageC(src);
        }
        else if(src instanceof CommandBlockSource) 
        {
        	EzpzPvpKit.getInstance().getUtils().sendMessageCB(src);
        }        

        return CommandResult.success();
    }
}
