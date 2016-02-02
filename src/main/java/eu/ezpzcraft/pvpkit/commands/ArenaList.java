package eu.ezpzcraft.pvpkit.commands;

import java.util.LinkedList;
import java.util.Map;

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

import eu.ezpzcraft.pvpkit.Arena;
import eu.ezpzcraft.pvpkit.EzpzPvpKit;

public class ArenaList implements CommandExecutor
{

	
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException 
    {
        if(src instanceof Player) 
        {     
        	LinkedList<Text> arenalist = new LinkedList<Text>();
    		for (String it : EzpzPvpKit.getInstance().getArenaList()) 
    		{   	
    			arenalist.add(Text.builder("")   
										.append(Text.builder(" [")
												.style(TextStyles.BOLD)
												.color(TextColors.DARK_GRAY)
												.build())
    									.append(Text.builder(" ✘")
												.color(TextColors.RED)
												.style(TextStyles.BOLD)
												.onHover(TextActions.showText(Text.builder("Click to delete "+it)
														.style(TextStyles.RESET)
														.color(TextColors.RED)
														.build()))
												.onClick(TextActions.runCommand("/k del "+it))
												.build())
										.append(Text.builder("]")
												.style(TextStyles.BOLD)
												.color(TextColors.DARK_GRAY)
												.build())
										.append(Text.builder("   "+it)
												.style(TextStyles.RESET)
												.color(TextColors.WHITE)
												.onHover(TextActions.showText(Text.builder("Type  ")
														.append(Text.of(TextColors.GRAY, ":  "))
														.append(Text.of(TextColors.WHITE, EzpzPvpKit.getInstance().getArena(it).getType()))
														.append(Text.of("\n",TextColors.GRAY, "pos1  "))
														.append(Text.of(TextColors.GRAY, ":  "))
														.append(Text.of(TextColors.GRAY,"(",TextColors.GOLD,EzpzPvpKit.getInstance().getArena(it).getPos1().getBlockX(),TextColors.GRAY,",",
																TextColors.GOLD,EzpzPvpKit.getInstance().getArena(it).getPos1().getBlockY(),TextColors.GRAY,",",
																TextColors.GOLD,EzpzPvpKit.getInstance().getArena(it).getPos1().getBlockZ(),TextColors.GRAY,")"))
														.append(Text.of("\n",TextColors.GRAY, "pos2  "))
														.append(Text.of(TextColors.GRAY, ":  "))
														.append(Text.of(TextColors.GRAY,"(",TextColors.GOLD,EzpzPvpKit.getInstance().getArena(it).getPos2().getBlockX(),TextColors.GRAY,",",
																TextColors.GOLD,EzpzPvpKit.getInstance().getArena(it).getPos2().getBlockY(),TextColors.GRAY,",",
																TextColors.GOLD,EzpzPvpKit.getInstance().getArena(it).getPos2().getBlockZ(),TextColors.GRAY,")"))
														.style(TextStyles.RESET)
														.color(TextColors.GRAY)
														.build()))
												.build()).build());										
    		}
        	
        	PaginationService paginationService = Sponge.getServiceManager().provide(PaginationService.class).get();

	    	paginationService.builder()
	        .title(Text.builder("")
	        				.append(Text.builder("------------")
	        						.color(TextColors.DARK_GRAY)
	        						.style(TextStyles.STRIKETHROUGH)
	        						.style(TextStyles.BOLD)
	        						.build())
	        				.append(Text.builder("  Arena List  ")
	        						.color(TextColors.DARK_RED)
	        						.style(TextStyles.RESET)
	        						.style(TextStyles.BOLD)
	        						.build())
	        				.append(Text.builder("------------")
	        						.color(TextColors.DARK_GRAY)
	        						.style(TextStyles.STRIKETHROUGH)
	        						.style(TextStyles.BOLD)
	        						.build())
	        				.style(TextStyles.RESET)
	        				.color(TextColors.DARK_GRAY)
	        				.build())
	        .contents(arenalist)
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

