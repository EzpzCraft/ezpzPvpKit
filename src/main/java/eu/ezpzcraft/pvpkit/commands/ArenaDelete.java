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

public class ArenaDelete implements CommandExecutor
{

	
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException 
    {
    	String name = args.<String>getOne("name").get();
        if(src instanceof Player && EzpzPvpKit.getInstance().isArenaExisting(name)) 
        {     
        	Player player = (Player) src;
            EzpzPvpKit.getInstance().getUtils().sendKitMessage(player, Text.builder("Delete "+name+"?")
            													.append(Text.builder("\n                 ")
            													.append(Text.builder("---------")
            																.style(TextStyles.STRIKETHROUGH)
            																.style(TextStyles.BOLD)
            																.color(TextColors.DARK_GRAY)
            													.append(Text.builder("\n                 ")
            																.style(TextStyles.RESET)
            													.append(Text.builder("   ✔ ")
	        																.style(TextStyles.BOLD)
	        																.color(TextColors.DARK_GREEN)
	        																.onHover(TextActions.showText(Text.builder("Click to delete "+name)
							        																		.style(TextStyles.RESET)
							    	        																.color(TextColors.DARK_GREEN)
							    	        																.build()))
	        																.onClick(TextActions.runCommand("/kit confirmdelete "+name))
	        																.build())
            													.append(Text.builder("  ┃")
            																.style(TextStyles.BOLD)
            																.color(TextColors.DARK_GRAY)
            													.append(Text.builder("   ✘ ")
            																.color(TextColors.RED)
	        																.onHover(TextActions.showText(Text.builder("Click to cancel the delete")
	        																		.style(TextStyles.RESET)
	    	        																.color(TextColors.RED)
	    	        																.build()))
	        																.build())
            													.append(Text.builder("\n               ")
            													.append(Text.builder("---------")
            																.style(TextStyles.STRIKETHROUGH)
            																.style(TextStyles.BOLD)
            																.color(TextColors.DARK_GRAY)			
            													.build()).build()).build()).build()).build()).build()).build());
        }
        else if(src instanceof Player && !EzpzPvpKit.getInstance().isArenaExisting(name))
        {
        	Player player = (Player) src;
        	EzpzPvpKit.getInstance().getUtils().sendKitMessage(player, Text.of(name + " doesn't exist"));
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

