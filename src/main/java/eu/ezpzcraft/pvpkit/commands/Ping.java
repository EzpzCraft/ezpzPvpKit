package eu.ezpzcraft.pvpkit.commands;

import java.util.LinkedList;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.CommandBlockSource;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.pagination.PaginationService;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

import eu.ezpzcraft.pvpkit.EzpzPvpKit;

public class Ping  implements CommandExecutor
{
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException 
    {
        if(src instanceof Player) 
        {   
        	Player dst = args.<Player>getOne("player").get();        	
        	Player player = (Player) src;

        	
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
