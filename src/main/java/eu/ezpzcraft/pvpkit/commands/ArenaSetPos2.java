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
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

import eu.ezpzcraft.pvpkit.Arena;
import eu.ezpzcraft.pvpkit.EzpzPvpKit;

public class ArenaSetPos2 implements CommandExecutor
{

	  @Override
	    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException 
	    {
	        String name = args.<String>getOne("name").get();
	        if(src instanceof Player && !EzpzPvpKit.getInstance().isArenaExisting(name)) 
	        {     
	        	Player player = (Player) src;
	        	//In creation or not ?
	        	if(EzpzPvpKit.getInstance().getUtils().getName() != null && EzpzPvpKit.getInstance().getUtils().getName().equals(name))
	        	{
	        		try {
						EzpzPvpKit.getInstance().getUtils().setPos2(player.getLocation());
						EzpzPvpKit.getInstance().getUtils().setRotation2(player.getRotation());
						
						Arena arena = new Arena(EzpzPvpKit.getInstance().getUtils().getName(),
								EzpzPvpKit.getInstance().getUtils().getType(),
								0, EzpzPvpKit.getInstance().getUtils().getPos1(),
								EzpzPvpKit.getInstance().getUtils().getRotation1(),
								EzpzPvpKit.getInstance().getUtils().getPos2(),
								EzpzPvpKit.getInstance().getUtils().getRotation2());
			
						EzpzPvpKit.getInstance().addArena(arena);
						EzpzPvpKit.getInstance().getDatabase().saveArena(arena); 
						
						EzpzPvpKit.getInstance().getUtils().sendKitMessage(player, Text.builder(name+" is now created")
										    					.color(TextColors.WHITE)
										    					.style(TextStyles.RESET)
										    				    .build());		
					} 
	        		catch (Exception e) 
	        		{
	    				EzpzPvpKit.getInstance().getUtils().sendKitMessage(player, Text.builder("Failed to set pos2 for "+name)
								.color(TextColors.RED).build());
					}
	        	}
	        	else
	        	{
	            	EzpzPvpKit.getInstance().getUtils().sendKitMessage(player, Text.of(name + " is not in creation"));
	        	}
	        }
	        else if(src instanceof Player && EzpzPvpKit.getInstance().isArenaExisting(name))
	        {
	        	Player player = (Player) src;
	        	try 
	        	{
					EzpzPvpKit.getInstance().getArena(name).setPos2(player.getLocation());
					EzpzPvpKit.getInstance().getArena(name).setRotation2(player.getRotation());
					
					EzpzPvpKit.getInstance().getDatabase().saveArena(EzpzPvpKit.getInstance().getArena(name));
					
					EzpzPvpKit.getInstance().getUtils().sendKitMessage(player, Text.of("pos2 redefined for "+name));
				} catch (Exception e) 
	        	{
					EzpzPvpKit.getInstance().getUtils().sendKitMessage(player, Text.builder("Failed to set pos2 for "+name)
																	.color(TextColors.RED).build());
				}
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
