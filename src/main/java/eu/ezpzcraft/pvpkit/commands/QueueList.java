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

import eu.ezpzcraft.pvpkit.DuelQueue;
import eu.ezpzcraft.pvpkit.EzpzPvpKit;
import eu.ezpzcraft.pvpkit.Utils;

/**
 * Display the current list of queue
 */
public class QueueList implements CommandExecutor
{	
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException 
    {
        if(src instanceof Player) 
        {     
        	LinkedList<Text> arenalist = new LinkedList<Text>();
        	if(EzpzPvpKit.getInstance().getQueues().size()==0)
        	{
        		arenalist.add(Text.of("No queue created"));
        	}
        	else
        	{
	    		for (Map.Entry<String, DuelQueue> it : EzpzPvpKit.getInstance().getQueues().entrySet()) 
	    		{   	
	    			arenalist.add(Text.builder("")   
											.append(Text.builder(" [")
													.style(TextStyles.BOLD)
													.color(TextColors.DARK_GRAY)													
													.append(Text.builder(" ✘")
													.color(TextColors.RED)
													.style(TextStyles.BOLD)
													.build())
													.onHover(TextActions.showText(Text.builder("Click to delete "+it.getKey())
															.style(TextStyles.RESET)
															.color(TextColors.RED)
															.build()))
													.onClick(TextActions.runCommand("/k qdel "+it.getKey()))
													.build())
											.append(Text.builder("]")
													.style(TextStyles.BOLD)
													.color(TextColors.DARK_GRAY)
													.build())
											.append(Text.builder("   "+it.getKey())
													.style(TextStyles.RESET)
													.color(TextColors.WHITE)
													.onHover(TextActions.showText(Text.builder("Type  ")
															.append(Text.of(TextColors.GRAY, ":  "))
															.append(Text.of(TextColors.WHITE, it.getValue().getType()))
															.append(Text.of("\n",TextColors.GRAY, "Ranked  :  "))
															.append(Text.of(TextColors.WHITE, it.getValue().isRanked()))
															.append(Text.of("\n",TextColors.GRAY, "Size  :  "))
															.append(Text.of(TextColors.WHITE, it.getValue().getSize()+"vs"+it.getValue().getSize()))
															.style(TextStyles.RESET)
															.color(TextColors.GRAY)
															.build()))
													.build()).build());										
	    		}
        	}
        	PaginationService paginationService = Sponge.getServiceManager().provide(PaginationService.class).get();
	    	paginationService.builder()
	        .title(Text.builder("")
	        				.append(Text.builder("------------")
	        						.color(TextColors.DARK_GRAY)
	        						.style(TextStyles.STRIKETHROUGH)
	        						.style(TextStyles.BOLD)
	        						.build())
	        				.append(Text.builder("  Queue List  ")
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
        	Utils.sendMessageC(src);
        else if(src instanceof CommandBlockSource) 
        	Utils.sendMessageCB(src);
        
        return CommandResult.success();
    }
	
	
}

