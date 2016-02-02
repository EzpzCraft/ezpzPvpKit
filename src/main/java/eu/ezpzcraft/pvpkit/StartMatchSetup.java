package eu.ezpzcraft.pvpkit;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.title.Title;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public class StartMatchSetup implements Consumer<Task>
{
	private LinkedHashMap<String,Team> teams = new LinkedHashMap<String,Team>();
	private Team team = null;
	private int countdown;
	private int ticks = (int) (Sponge.getServer().getTicksPerSecond()/4.);

	
	@Override
    public void accept(Task task) 
    {
		// each team
		for (Map.Entry<String,Team> entryT : teams.entrySet()) 
		{
			team = teams.get(entryT.getKey());
			countdown = team.getCountdown();
			// each player of the current team
			for (PvPPlayer player : team.getPlayers()) 
			{
				sendCountDownTitle(EzpzPvpKit.getInstance().getPlayer(player.getPlayer().getIdentifier()),countdown);
			}
			if(countdown == 0)
			{
				//CREATE BOX
				for(int x=-2;x<=2;x++)
				{
					for(int z=-2;z<=2;z++)
					{
						for(int y=-3;y<=3;y++)
						{
							if((Math.abs(x)<=1)&&(Math.abs(z)<=1)&&(Math.abs(y)<=2)){}
							else
							{
								if(team.getLocation().add(x, y, z).getBlock().equals(BlockTypes.STAINED_GLASS.getDefaultState()))
									team.getLocation().add(x, y, z).setBlock(BlockTypes.AIR.getDefaultState());
							}						
						}
					}
				}
				teams.remove(entryT.getKey());
			}
			else
				team.setCountdown(countdown-1);	
		}
    }
	
	
	private void sendCountDownTitle(PvPPlayer pvpPlayer, int cd)
	{
		Player player = pvpPlayer.getPlayer();
		
		switch(cd)
		{
			case 3:
				player.sendTitle(Title.builder()
						.title(Text.builder(""+cd).color(TextColors.RED).build())
						.fadeIn(0)
						.stay(ticks)
						.fadeOut(3*ticks)
						.build());
			break;
			case 2:
				player.sendTitle(Title.builder()
						.title(Text.builder(""+cd).color(TextColors.GOLD).build())
						.fadeIn(0)
						.stay(ticks)
						.fadeOut(3*ticks)
						.build());
			break;
			case 1:
				player.sendTitle(Title.builder()
						.title(Text.builder(""+cd).color(TextColors.DARK_GREEN).build())
						.fadeIn(0)
						.stay(ticks)
						.fadeOut(3*ticks)
						.build());
			break;
			case 0:
				player.sendTitle(Title.builder()
						.title(Text.builder("GO").color(TextColors.GREEN).build())
						.fadeIn(0)
						.stay(ticks)
						.fadeOut(2)
						.build());
			break;
		}
	}
	
	
	/* Getters and Setters */
    public void addTeamStart(Team team) 
    {
    	
		teams.put(team.getName(), team);		
		team.setLocation( EzpzPvpKit.getInstance().getPlayer(team.getPlayers().iterator().next().getPlayer().getIdentifier()).getPlayer().getLocation() );

		/*CREATE BOX
		for(int x=-2;x<=2;x++)
		{
			for(int z=-2;z<=2;z++)
			{
				for(int y=-3;y<=3;y++)
				{
					if((Math.abs(x)<=1)&&(Math.abs(z)<=1)&&(Math.abs(y)<=2)){}
					else
					{
						if(team.getLocation().add(x, y, z).getBlock().equals(BlockTypes.AIR.getDefaultState()))
							team.getLocation().add(x, y, z).setBlock(BlockTypes.STAINED_GLASS.getDefaultState());
					}						
				}
			}
		}*/
		setBox(team.getLocation());
		

	}
    
    public void setBox(Location<World> location)
    {
		setBlock(location.add(2, 0, 0));
		setBlock(location.add(-2, 0, 0));
		setBlock(location.add(0, 0, 2));
		setBlock(location.add(0, 0, -2));
		setBlock(location.add(-1, 0, 2));
		setBlock(location.add(-1, 0, -2));
		setBlock(location.add(1, 0, 2));
		setBlock(location.add(1, 0, -2));
		setBlock(location.add(-2, 0, 1));
		setBlock(location.add(-2, 0, -1));
		setBlock(location.add(2, 0, 1));
		setBlock(location.add(2, 0, -1));
		
		setBlock(location.add(2, 1, 0));
		setBlock(location.add(-2, 1, 0));
		setBlock(location.add(0, 1, 2));
		setBlock(location.add(0, 1, -2));
		setBlock(location.add(-1, 1, 2));
		setBlock(location.add(-1, 1, -2));
		setBlock(location.add(1, 1, 2));
		setBlock(location.add(1, 1, -2));
		setBlock(location.add(-2, 1, 1));
		setBlock(location.add(-2, 1, -1));
		setBlock(location.add(2, 1, 1));
		setBlock(location.add(2, 1, -1));
		
		setBlock(location.add(2, -1, 0));
		setBlock(location.add(-2, -1, 0));
		setBlock(location.add(0, -1, 2));
		setBlock(location.add(0, -1, -2));
		setBlock(location.add(-1, -1, 2));
		setBlock(location.add(-1, -1, -2));
		setBlock(location.add(1, -1, 2));
		setBlock(location.add(1, -1, -2));
		setBlock(location.add(-2, -1, 1));
		setBlock(location.add(-2, -1, -1));
		setBlock(location.add(2, -1, 1));
		setBlock(location.add(2, -1, -1));
		
		setBlock(location.add(2, 2, 0));
		setBlock(location.add(-2, 2, 0));
		setBlock(location.add(0, 2, 2));
		setBlock(location.add(0, 2, -2));
		setBlock(location.add(-1, 2, 2));
		setBlock(location.add(-1, 2, -2));
		setBlock(location.add(1, 2, 2));
		setBlock(location.add(1, 2, -2));
		setBlock(location.add(-2, 2, 1));
		setBlock(location.add(-2, 2, -1));
		setBlock(location.add(2, 2, 1));
		setBlock(location.add(2, 2, -1));	
		
		setBlock(location.add(0, 3, 0));
		setBlock(location.add(0, 3, 1));
		setBlock(location.add(0, 3, -1));
		setBlock(location.add(1, 3, 0));
		setBlock(location.add(1, 3, 1));
		setBlock(location.add(1, 3, -1));
		setBlock(location.add(-1, 3, 0));
		setBlock(location.add(-1, 3, 1));
		setBlock(location.add(-1, 3, -1));
    }
    
    public void removeBox(Location<World> location)
    {
		removeBlock(location.add(2, 0, 0));
		removeBlock(location.add(-2, 0, 0));
		removeBlock(location.add(0, 0, 2));
		removeBlock(location.add(0, 0, -2));
		removeBlock(location.add(-1, 0, 2));
		removeBlock(location.add(-1, 0, -2));
		removeBlock(location.add(1, 0, 2));
		removeBlock(location.add(1, 0, -2));
		removeBlock(location.add(-2, 0, 1));
		removeBlock(location.add(-2, 0, -1));
		removeBlock(location.add(2, 0, 1));
		removeBlock(location.add(2, 0, -1));
		
		removeBlock(location.add(2, 1, 0));
		removeBlock(location.add(-2, 1, 0));
		removeBlock(location.add(0, 1, 2));
		removeBlock(location.add(0, 1, -2));
		removeBlock(location.add(-1, 1, 2));
		removeBlock(location.add(-1, 1, -2));
		removeBlock(location.add(1, 1, 2));
		removeBlock(location.add(1, 1, -2));
		removeBlock(location.add(-2, 1, 1));
		removeBlock(location.add(-2, 1, -1));
		removeBlock(location.add(2, 1, 1));
		removeBlock(location.add(2, 1, -1));
		
		removeBlock(location.add(2, -1, 0));
		removeBlock(location.add(-2, -1, 0));
		removeBlock(location.add(0, -1, 2));
		removeBlock(location.add(0, -1, -2));
		removeBlock(location.add(-1, -1, 2));
		removeBlock(location.add(-1, -1, -2));
		removeBlock(location.add(1, -1, 2));
		removeBlock(location.add(1, -1, -2));
		removeBlock(location.add(-2, -1, 1));
		removeBlock(location.add(-2, -1, -1));
		removeBlock(location.add(2, -1, 1));
		removeBlock(location.add(2, -1, -1));
		
		removeBlock(location.add(2, 2, 0));
		removeBlock(location.add(-2, 2, 0));
		removeBlock(location.add(0, 2, 2));
		removeBlock(location.add(0, 2, -2));
		removeBlock(location.add(-1, 2, 2));
		removeBlock(location.add(-1, 2, -2));
		removeBlock(location.add(1, 2, 2));
		removeBlock(location.add(1, 2, -2));
		removeBlock(location.add(-2, 2, 1));
		removeBlock(location.add(-2, 2, -1));
		removeBlock(location.add(2, 2, 1));
		removeBlock(location.add(2, 2, -1));
		
		/*removeBlock(location.add(2, -2, 0));
		removeBlock(location.add(-2, -2, 0));
		removeBlock(location.add(0, -2, 2));
		removeBlock(location.add(0, -2, -2));
		removeBlock(location.add(-1, -2, 2));
		removeBlock(location.add(-1, -2, -2));
		removeBlock(location.add(1, -2, 2));
		removeBlock(location.add(1, -2, -2));
		removeBlock(location.add(-2, -2, 1));
		removeBlock(location.add(-2, -2, -1));
		removeBlock(location.add(2, -2, 1));
		removeBlock(location.add(2, -2, -1));
		
		removeBlock(location.add(0, -3, 0));
		removeBlock(location.add(0, -3, 1));
		removeBlock(location.add(0, -3, -1));
		removeBlock(location.add(1, -3, 0));
		removeBlock(location.add(1, -3, 1));
		removeBlock(location.add(1, -3, -1));
		removeBlock(location.add(-1, -3, 0));
		removeBlock(location.add(-1, -3, 1));
		removeBlock(location.add(-1, -3, -1));*/
		
		removeBlock(location.add(0, 3, 0));
		removeBlock(location.add(0, 3, 1));
		removeBlock(location.add(0, 3, -1));
		removeBlock(location.add(1, 3, 0));
		removeBlock(location.add(1, 3, 1));
		removeBlock(location.add(1, 3, -1));
		removeBlock(location.add(-1, 3, 0));
		removeBlock(location.add(-1, 3, 1));
		removeBlock(location.add(-1, 3, -1));
    }
    
    public void removeBlock(Location<World> location)
    {
    	if(location.getBlock() == BlockTypes.STAINED_GLASS.getDefaultState())
    		location.setBlock(BlockTypes.AIR.getDefaultState());
    }
    
    public void setBlock(Location<World> location)
    {
    	if(location.getBlock() == BlockTypes.AIR.getDefaultState())
    		location.setBlock(BlockTypes.STAINED_GLASS.getDefaultState());
    }

}
