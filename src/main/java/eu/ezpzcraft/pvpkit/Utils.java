package eu.ezpzcraft.pvpkit;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.flowpowered.math.vector.Vector3d;

public class Utils {
	

	private String name = null;
	private Location<World> pos1 = null;
    private Location<World> pos2 = null; 
    private Vector3d rotation1 = null;
    private Vector3d rotation2 = null;
    private String type = null;	
    
    /* Send Message with [EzpzKit] before */
    public void sendKitMessage(Player player, Text text)
    {
    	player.sendMessage(Text.builder("[").color(TextColors.DARK_GRAY).style(TextStyles.BOLD)
	            				.append(Text.builder("EzpzKit").color(TextColors.DARK_RED).style(TextStyles.BOLD)
	            				.append(Text.builder("]").color(TextColors.DARK_GRAY).style(TextStyles.BOLD)
	            				.append(Text.builder(" ").style(TextStyles.RESET).color(TextColors.WHITE)
	            				.append(text)
	            				.build()).build()).build()).build());
    }
    
    /* Send Message arena not created */
    public void sendArenaExist(Player player)
    {
        player.sendMessage(Text.builder("[").color(TextColors.DARK_GRAY).style(TextStyles.BOLD)
				.append(Text.builder("EzpzKit").color(TextColors.DARK_RED).style(TextStyles.BOLD)
				.append(Text.builder("]").color(TextColors.DARK_GRAY).style(TextStyles.BOLD)
				.append(Text.builder(" This arena name is already used.").color(TextColors.WHITE).style(TextStyles.RESET)
				.append(Text.builder("\n               Choose an other name ?").color(TextColors.GOLD).style(TextStyles.BOLD)
    					.onHover(TextActions.showText(Text.builder("Change it by clicking here")
								.color(TextColors.GOLD)
								.style(TextStyles.RESET)
								.build()))
    					.onClick(TextActions.suggestCommand("/kit create "))
				.build()).build()).build()).build()).build());
    }
    
    /* CommandBlock */
    public void sendMessageCB(CommandSource src)
    {
    	src.sendMessage(Text.of("[EzpzKit] Cannot execute this command from a commandblock."));
    }
    
    /* Console */
    public void sendMessageC(CommandSource src)
    {
    	src.sendMessage(Text.of("[EzpzKit] Cannot execute this command from console."));
    }
           
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}   
	public Location<World> getPos1() {
		return pos1;
	}
	public Location<World> getPos2() {
		return pos2;
	}
	public Vector3d getRotation1() {
		return rotation1;
	}
	public Vector3d getRotation2() {
		return rotation2;
	}
	public String getType() {
		return type;
	}
	public void setPos1(Location<World> pos1) {
		this.pos1 = pos1;
	}
	public void setPos2(Location<World> pos2) {
		this.pos2 = pos2;
	}
	public void setRotation1(Vector3d rotation1) {
		this.rotation1 = rotation1;
	}
	public void setRotation2(Vector3d rotation2) {
		this.rotation2 = rotation2;
	}
	public void setType(String type) {
		this.type = type;
	}
}
