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
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

import eu.ezpzcraft.pvpkit.EzpzPvpKit;
import eu.ezpzcraft.pvpkit.Utils;

/**
 * Delete arena
 */
public class ArenaDelete implements CommandExecutor
{
	
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException 
    {
    	String name = args.<String>getOne("name").get();
        if(src instanceof Player && EzpzPvpKit.getInstance().isArenaExisting(name)) 
        {     
            Utils.sendKitMessage((Player) src, Text.builder("Delete "+name+"?   ")           		
            										.append(Text.builder("┃  ")     
            											.style(TextStyles.BOLD)
            											.color(TextColors.DARK_GRAY)
            										.build())         										
            										.append(Text.builder("✔")
            											.style(TextStyles.BOLD)
	        											.color(TextColors.DARK_GREEN)
	        											.onHover(TextActions.showText(Text.of(TextStyles.RESET,
	        													TextColors.DARK_GREEN,"Click to delete "+name)))
	        											.onClick(TextActions.runCommand("/kit acdel "+name+" true"))
	        										.build())
            										.append(Text.builder("  ┃   ")
            											.style(TextStyles.BOLD)
            											.color(TextColors.DARK_GRAY)
            										.build())
            										.append(Text.builder("✘")
                										.onHover(TextActions.showText(Text.of(TextStyles.RESET,
    	        													TextColors.RED,"Click to cancel the delete")))
            											.color(TextColors.RED)
            											.style(TextStyles.BOLD)
	        											.onClick(TextActions.runCommand("/kit acdel "+name+" false"))
	        										.build())
            										.append(Text.builder("  ┃\n ")
            											.style(TextStyles.BOLD)
            											.color(TextColors.DARK_GRAY)			
            										.build())
            									.build());
        }
        else if(src instanceof Player && !EzpzPvpKit.getInstance().isArenaExisting(name))
        	Utils.sendKitMessage((Player) src, Text.of(name + " doesn't exist"));
        else if(src instanceof ConsoleSource) 
        	Utils.sendMessageC(src);
        else if(src instanceof CommandBlockSource) 
        	Utils.sendMessageCB(src);
        
        return CommandResult.success();
    }
	
	
}

