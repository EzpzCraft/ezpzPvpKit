package eu.ezpzcraft.pvpkit.thread;

import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.function.Consumer;

import org.spongepowered.api.scheduler.Task;

import eu.ezpzcraft.pvpkit.Duel;
import eu.ezpzcraft.pvpkit.DuelQueue;
import eu.ezpzcraft.pvpkit.EzpzPvpKit;

public class QueueThread implements Consumer<Task>
{
	@Override
    public void accept(Task task) 
    {
		for( Entry<String, DuelQueue> queue: EzpzPvpKit.getInstance().getQueues().entrySet() )
		{
			ArrayList<String> teams = queue.getValue().match();
			
			// 1. Find a team couple
			if( teams==null )
				continue;

			// 2. Find a free arena, create a new duel and start it			
			Duel duel = new Duel(teams.get(0), 
					 	 		 teams.get(1), 
					 	 		 EzpzPvpKit.getInstance().getFreeArena( queue.getValue().getType() ));
			
			duel.start();
		}
    }
}
