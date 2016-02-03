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
 * Set pos1 for the specified arena
 */
public class ArenaSetPos1 implements CommandExecutor
{
	
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException 
    {
        String name = args.<String>getOne("name").get();
        if(src instanceof Player && !EzpzPvpKit.getInstance().isArenaExisting(name)) 
        {     
        	Player player = (Player) src;
        	/*
        	 * Check if arena is in creation
        	 */
        	if(EzpzPvpKit.getInstance().getUtils().getName() != null && EzpzPvpKit.getInstance().getUtils().getName().equals(name))
        	{
        		try {
					EzpzPvpKit.getInstance().getUtils().setArenaPos1(player.getLocation());
					EzpzPvpKit.getInstance().getUtils().setArenaRotation1(player.getRotation());
					Utils.sendKitMessage(player, Text.builder("Set pos2")
									    					.color(TextColors.GOLD)
									    					.style(TextStyles.RESET)
									    					.onHover(TextActions.showText(Text.builder("Click to set pos2")
									    													.color(TextColors.GOLD)
									    													.style(TextStyles.RESET)
									    													.build()))
									    					.onClick(TextActions.runCommand("/kit asetpos2 " + EzpzPvpKit.getInstance().getUtils().getName()))
									    				    .build());
				} 
        		catch (Exception e) 
        		{
    				Utils.sendKitMessage(player, Text.builder("Failed to set pos1 for "+name)
							.color(TextColors.RED).build());
				}
        	}
        	else
            	Utils.sendKitMessage(player, Text.of(name + " is not in creation"));
        }
        else if(src instanceof Player && EzpzPvpKit.getInstance().isArenaExisting(name))
        {
        	Player player = (Player) src;
        	try 
        	{
				EzpzPvpKit.getInstance().getArena(name).setPos1(player.getLocation());
				EzpzPvpKit.getInstance().getArena(name).setRotation1(player.getRotation());

				EzpzPvpKit.getInstance().getDatabase().saveArena(EzpzPvpKit.getInstance().getArena(name));
				
				Utils.sendKitMessage(player, Text.of("pos1 redefined for "+name));
			} catch (Exception e) 
        	{
				Utils.sendKitMessage(player, Text.builder("Failed to set pos1 for "+name)
																.color(TextColors.RED).build());
			}
        }
        else if(src instanceof ConsoleSource) 
        	Utils.sendMessageC(src);
        else if(src instanceof CommandBlockSource) 
        	Utils.sendMessageCB(src);
        
        return CommandResult.success();
    }
}
