package eu.ezpzcraft.pvpkit.commands;

import java.util.ArrayList;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.service.pagination.PaginationService;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

public class Party implements CommandExecutor
{
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException 
    {
    	/* Create party help
    	 *  TODO  */
		ArrayList<Text> contents = new ArrayList<>();
		contents.add(Text.of("Item 1"));
		contents.add(Text.of("Item 2"));
		contents.add(Text.of("Item 3"));
		contents.add(Text.of("Item 4"));
    	
    	PaginationService paginationService = Sponge.getServiceManager().provide(PaginationService.class).get();

    	paginationService.builder()
        .title(Text.builder("").color(TextColors.DARK_GRAY).style(TextStyles.BOLD)
        				.append(Text.builder("Party Help").color(TextColors.DARK_RED).style(TextStyles.BOLD)
        						.append(Text.builder("").color(TextColors.DARK_GRAY).style(TextStyles.BOLD).build())
        				.build())
        			.build())
        .contents(contents)
        .paddingString("=")
        .sendTo(src);

        return CommandResult.success();
    }
	
}
