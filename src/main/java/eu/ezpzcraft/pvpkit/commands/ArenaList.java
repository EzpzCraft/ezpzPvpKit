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
import org.spongepowered.api.service.pagination.PaginationService;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

import eu.ezpzcraft.pvpkit.EzpzPvpKit;

public class ArenaList implements CommandExecutor
{

	
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException 
    {
        if(src instanceof Player) 
        {     
        	PaginationService paginationService = Sponge.getServiceManager().provide(PaginationService.class).get();

	    	paginationService.builder()
	        .title(Text.builder("")
	        				.append(Text.builder("------")
	        						.color(TextColors.DARK_GRAY)
	        						.style(TextStyles.STRIKETHROUGH)
	        						.style(TextStyles.BOLD)
	        						.build())
	        				.append(Text.builder("  Arena List  ")
	        						.color(TextColors.DARK_RED)
	        						.style(TextStyles.RESET)
	        						.style(TextStyles.BOLD)
	        						.build())
	        				.append(Text.builder("------")
	        						.color(TextColors.DARK_GRAY)
	        						.style(TextStyles.STRIKETHROUGH)
	        						.style(TextStyles.BOLD)
	        						.build())
	        				.style(TextStyles.RESET)
	        				.color(TextColors.DARK_GRAY)
	        				.build())
	        .contents(EzpzPvpKit.getInstance().getArenaList())
	        .paddingString("")
	        .sendTo(src);

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

