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
  *  Start creation of an arena from :
  *  <ul>
  *  <li>name </li>
  *  <li>type </li>
  *  </ul>
  */
public class ArenaCreate implements CommandExecutor
{	
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException 
    {
    	EzpzPvpKit.getInstance().getUtils().setArenaName(args.<String>getOne("name").get());
    	EzpzPvpKit.getInstance().getUtils().setArenaType(args.<String>getOne("type").get());
        if(src instanceof Player && !EzpzPvpKit.getInstance().isArenaExisting(EzpzPvpKit.getInstance().getUtils().getName())) 
        {                   	
        	Utils.sendKitMessage((Player) src,Text.builder("Set pos1")
	            					.color(TextColors.GOLD)
	            					.style(TextStyles.RESET)
	            					.onHover(TextActions.showText(Text.builder("Click to set pos1")
	            													.color(TextColors.GOLD)
	            													.style(TextStyles.RESET)
	            													.build()))
	            					.onClick(TextActions.runCommand("/kit asetpos1 " + EzpzPvpKit.getInstance().getUtils().getName()))
	            				    .build());
            
        }
        else if(src instanceof Player && 
        		EzpzPvpKit.getInstance().isArenaExisting(EzpzPvpKit.getInstance().getUtils().getName()))
        	Utils.sendArenaExist((Player) src);
        else if(src instanceof ConsoleSource) 
        	Utils.sendMessageC(src);
        else if(src instanceof CommandBlockSource) 
        	Utils.sendMessageCB(src);
        
       return CommandResult.success();
    }
	
	
}
