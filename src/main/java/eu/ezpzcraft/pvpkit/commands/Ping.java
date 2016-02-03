package eu.ezpzcraft.pvpkit.commands;

import java.util.Optional;

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
import org.spongepowered.api.text.format.TextColors;

import eu.ezpzcraft.pvpkit.Utils;

/**
 * Get the ping of the specified player
 */
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
        	Utils.sendMessageC(src);
        else if(src instanceof CommandBlockSource) 
        	Utils.sendMessageCB(src);       

        return CommandResult.success();
    }
}
