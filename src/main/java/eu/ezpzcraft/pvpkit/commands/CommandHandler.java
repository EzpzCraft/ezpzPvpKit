package eu.ezpzcraft.pvpkit.commands;

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

import eu.ezpzcraft.pvpkit.EzpzPvpKit;


public class CommandHandler 
{
	private VoteMap voteMap = new VoteMap();
	
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
		
		/* Arena Create */
		CommandSpec arenacreate = CommandSpec.builder()
				.description(Text.of("Create an arena <Name> <Type>"))
				.executor(new ArenaCreate())
		        .arguments(
		                GenericArguments.onlyOne(GenericArguments.string(Text.of("name"))),
		                GenericArguments.onlyOne(GenericArguments.string(Text.of("type"))))
				.permission("ezpzkit.command.create")
				.build();
		
		/* Arena Setpos1 */
		CommandSpec arenasetpos1 = CommandSpec.builder()
				.description(Text.of("Create an arena <Name> <Type>"))
				.executor(new ArenaSetPos1())
		        .arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("name"))))
				.permission("ezpzkit.command.create")
				.build();
		
		/* Arena Setpos2 */
		CommandSpec arenasetpos2 = CommandSpec.builder()
				.description(Text.of("Create an arena <Name> <Type>"))
				.executor(new ArenaSetPos2())
		        .arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("name"))))
				.permission("ezpzkit.command.create")
				.build();
		
		/* Arena Delete */
		CommandSpec arenadelete = CommandSpec.builder()
				.description(Text.of("Create an arena <Name> <Type>"))
				.executor(new ArenaDelete())
		        .arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("name"))))
				.permission("ezpzkit.command.create")
				.build();
		
		/* Arena Confirm Delete */
		CommandSpec arenaconfirmdelete = CommandSpec.builder()
				.description(Text.of("Create an arena <Name> <Type>"))
				.executor(new ArenaConfirmDelete())
		        .arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("name"))),
		        		GenericArguments.onlyOne(GenericArguments.bool(Text.of("bool"))))
				.permission("ezpzkit.command.create")
				.build();
		
		/* ArenaList */
		CommandSpec arenalist = CommandSpec.builder()
				.description(Text.of("Create an arena <Name> <Type>"))
				.executor(new ArenaList())
				.permission("ezpzkit.command.create")
				.build();
		
		/* Create Queue */
		CommandSpec queuecreate = CommandSpec.builder()
				.description(Text.of("Create an arena <Name> <Type>"))
				.executor(new QueueCreate())
		        .arguments(
		                GenericArguments.onlyOne(GenericArguments.string(Text.of("name"))),
		                GenericArguments.onlyOne(GenericArguments.string(Text.of("type"))),
		                GenericArguments.optionalWeak(GenericArguments.integer(Text.of("size"))),
		                GenericArguments.optionalWeak(GenericArguments.bool(Text.of("ranked"))))
				.permission("ezpzkit.command.create")
				.build();
		
		/*Queue Delete */
		CommandSpec queuedelete = CommandSpec.builder()
				.description(Text.of("Create an arena <Name> <Type>"))
				.executor(new QueueDelete())
		        .arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("name"))))
				.permission("ezpzkit.command.create")
				.build();
		
		/* Queue Confirm Delete */
		CommandSpec queueconfirmdelete = CommandSpec.builder()
				.description(Text.of("Create an arena <Name> <Type>"))
				.executor(new QueueConfirmDelete())
		        .arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("name"))),
		        		GenericArguments.onlyOne(GenericArguments.bool(Text.of("bool"))))
				.permission("ezpzkit.command.create")
				.build();
		
		/* QueueList */
		CommandSpec queuelist = CommandSpec.builder()
				.description(Text.of("Create an arena <Name> <Type>"))
				.executor(new QueueList())
				.permission("ezpzkit.command.create")
				.build();
		
		/* Ping */
		CommandSpec ping = CommandSpec.builder()
				.description(Text.of("Ping <Player>"))
				.executor(new Ping())
				.arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("player"))))
				.permission("ezpzkit.command.ping")
				.build();
		
		/* Join Queue */
		CommandSpec joinqueue = CommandSpec.builder()
				.description(Text.of("Create an arena <Name> <Type>"))
				.executor(new QueueJoin())
				.arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("name"))))
				.permission("ezpzkit.command.create")
				.build();		
		
		/* Vote */
		CommandSpec vote = CommandSpec.builder()
				.description(Text.of("Vote for the last map"))
				.executor(voteMap)
				.arguments(GenericArguments.onlyOne(GenericArguments.integer(Text.of("score"))))
				.permission("ezpzkit.vote")
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
				.child(arenacreate, "acreate", "ac")
				.child(arenasetpos1, "asetpos1", "a1")
				.child(arenasetpos2, "asetpos2", "a2")
				.child(arenadelete, "adelete", "adel", "ad")
				.child(arenaconfirmdelete, "arenacdelete", "acdel", "acd")
				.child(arenalist, "arenalist", "alist", "al")
				.child(queuecreate, "qcreate", "qc")
				.child(queuedelete, "queuedelete", "qdel", "qd")
				.child(queueconfirmdelete, "queuecdelete", "qcdel", "qcd")
				.child(queuelist, "queuelist", "qlist", "ql")
				.child(joinqueue, "joinqueue", "joinq")
				.permission("ezpzkit.command")
				.build();	
		
		/* Party invite */
		CommandSpec partyinvite = CommandSpec.builder()
				.description(Text.of(""))
				.executor(new PartyInvite())
				.arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("name"))))
				.permission("party.invite")
				.build();
		
		/* Party leave */
		CommandSpec partyleave = CommandSpec.builder()
				.description(Text.of("Leave current party"))
				.executor(new PartyLeave())
				.permission("party.leave")
				.build();
		
		/* Party accept */
		CommandSpec partyaccept = CommandSpec.builder()
				.description(Text.of("Accept an party invitation"))
				.arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("party"))))
				.executor(new PartyAccept())
				.permission("party.accept")
				.build();
		
		/* Party => help */
		CommandSpec party = CommandSpec.builder()
				.description(Text.of(""))
				.executor(new Party())
				.child(partyinvite, "invite", "inv")
				.child(partyleave, "leave")
				.child(partyaccept, "accept", "acc")
				.permission("")
				.build();
		
		Sponge.getCommandManager().register(EzpzPvpKit.getInstance(), kit, "kit", "k", "ezpzkit");
		Sponge.getCommandManager().register(EzpzPvpKit.getInstance(), party, "party", "p");
		Sponge.getCommandManager().register(EzpzPvpKit.getInstance(), ping, "ping");		
		Sponge.getCommandManager().register(EzpzPvpKit.getInstance(), vote, "vote");

	}

	public VoteMap getVoteMap()
	{
		return this.voteMap;
	}
	
}
