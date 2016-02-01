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

public class ArenaCreate implements CommandExecutor
{

	
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException 
    {
    	

    	EzpzPvpKit.getInstance().getUtils().setName(args.<String>getOne("name").get());
    	EzpzPvpKit.getInstance().getUtils().setType(args.<String>getOne("type").get());
        if(src instanceof Player && !EzpzPvpKit.getInstance().isArenaExisting(EzpzPvpKit.getInstance().getUtils().getName())) 
        {            
        	Player player = (Player) src;
        	EzpzPvpKit.getInstance().getUtils().sendKitMessage(player,Text.builder("Set pos1")
	            					.color(TextColors.GOLD)
	            					.style(TextStyles.RESET)
	            					.onHover(TextActions.showText(Text.builder("Click to set pos1")
	            													.color(TextColors.GOLD)
	            													.style(TextStyles.RESET)
	            													.build()))
	            					.onClick(TextActions.runCommand("/kit setpos1 " + EzpzPvpKit.getInstance().getUtils().getName()))
	            				    .build());
            
        }
        else if(src instanceof Player && EzpzPvpKit.getInstance().isArenaExisting(EzpzPvpKit.getInstance().getUtils().getName()))
        {
        	Player player = (Player) src;
        	EzpzPvpKit.getInstance().getUtils().sendArenaExist(player);
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
