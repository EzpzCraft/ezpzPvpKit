package eu.ezpzcraft.pvpkit;

import java.util.ArrayList;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.service.pagination.PaginationService;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

import eu.ezpzcraft.pvpkit.commands.ArenaConfirmDelete;
import eu.ezpzcraft.pvpkit.commands.ArenaCreate;
import eu.ezpzcraft.pvpkit.commands.ArenaDelete;
import eu.ezpzcraft.pvpkit.commands.ArenaList;
import eu.ezpzcraft.pvpkit.commands.ArenaSetPos1;
import eu.ezpzcraft.pvpkit.commands.ArenaSetPos2;


public class CommandHandler 
{
	
	
	public CommandHandler()
	{
		ArrayList<Text> contents = new ArrayList<>();
		contents.add(Text.of("Item 1"));
		contents.add(Text.of("Item 2"));
		contents.add(Text.of("Item 3"));
		contents.add(Text.of("Item 4"));
		
		/* help */
		CommandSpec help = CommandSpec.builder()
				.description(Text.of("Display help"))
				.executor(new CommandExecutor()
				{
				    @Override
				    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException 
				    {
				    	/* Create help 
				    	 *  TODO  */
				    	PaginationService paginationService = Sponge.getServiceManager().provide(PaginationService.class).get();

				    	paginationService.builder()
				        .title(Text.builder("").color(TextColors.DARK_GRAY).style(TextStyles.BOLD)
				        				.append(Text.builder("Ezpzkit").color(TextColors.DARK_RED).style(TextStyles.BOLD)
				        						.append(Text.builder("").color(TextColors.DARK_GRAY).style(TextStyles.BOLD).build())
				        				.build())
				        			.build())
				        .contents(contents)
				        .paddingString("=")
				        .sendTo(src);

				        return CommandResult.success();
				    }
				})
				.permission("ezpzkit.command.help")
				.build();
		
		/* Create */
		CommandSpec arenacreate = CommandSpec.builder()
				.description(Text.of("Create an arena <Name> <Type>"))
				.executor(new ArenaCreate())
		        .arguments(
		                GenericArguments.onlyOne(GenericArguments.string(Text.of("name"))),
		                GenericArguments.onlyOne(GenericArguments.string(Text.of("type"))))
				.permission("ezpzkit.command.create")
				.build();
		
		/* Setpos1 */
		CommandSpec arenasetpos1 = CommandSpec.builder()
				.description(Text.of("Create an arena <Name> <Type>"))
				.executor(new ArenaSetPos1())
		        .arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("name"))))
				.permission("ezpzkit.command.create")
				.build();
		
		/* Setpos2 */
		CommandSpec arenasetpos2 = CommandSpec.builder()
				.description(Text.of("Create an arena <Name> <Type>"))
				.executor(new ArenaSetPos2())
		        .arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("name"))))
				.permission("ezpzkit.command.create")
				.build();
		
		/* Delete */
		CommandSpec arenadelete = CommandSpec.builder()
				.description(Text.of("Create an arena <Name> <Type>"))
				.executor(new ArenaDelete())
		        .arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("name"))))
				.permission("ezpzkit.command.create")
				.build();
		
		/* Confirm Delete */
		CommandSpec arenaconfirmdelete = CommandSpec.builder()
				.description(Text.of("Create an arena <Name> <Type>"))
				.executor(new ArenaConfirmDelete())
		        .arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("name"))))
				.permission("ezpzkit.command.create")
				.build();
		
		/* Confirm Delete */
		CommandSpec arenalist = CommandSpec.builder()
				.description(Text.of("Create an arena <Name> <Type>"))
				.executor(new ArenaList())
				.permission("ezpzkit.command.create")
				.build();
		
		/*  kit => kit help */
		CommandSpec kit = CommandSpec.builder()
				.description(Text.of("Display help"))
				.executor(new CommandExecutor()
				{
				    @Override
				    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException 
				    {
				    	/* Create help 
				    	 *  TODO  */
				    	PaginationService paginationService = Sponge.getServiceManager().provide(PaginationService.class).get();

				    	paginationService.builder()
				        .title(Text.builder("").color(TextColors.DARK_GRAY).style(TextStyles.BOLD)
				        				.append(Text.builder("Ezpzkit").color(TextColors.DARK_RED).style(TextStyles.BOLD)
				        						.append(Text.builder("").color(TextColors.DARK_GRAY).style(TextStyles.BOLD).build())
				        				.build())
				        			.build())
				        .contents(contents)
				        .paddingString("=")
				        .sendTo(src);

				        return CommandResult.success();
				    }
				})
				.child(help, "help", "h")
				.child(arenacreate, "create", "c")
				.child(arenasetpos1, "setpos1")
				.child(arenasetpos2, "setpos2")
				.child(arenadelete, "delete", "del", "d")
				.child(arenaconfirmdelete, "cdelete", "confirmdelete", "cdel")
				.child(arenalist, "arenalist", "alist")
				.permission("ezpzkit.command")
				.build();
		
		Sponge.getCommandManager().register(EzpzPvpKit.getInstance(), kit, "kit", "k", "ezpzkit");
		
		
	}

}
