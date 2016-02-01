package eu.ezpzcraft.pvpkit;

import org.slf4j.Logger;
import com.google.inject.Inject;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.scheduler.Scheduler;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;

import javax.xml.crypto.Data;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Plugin(id = "EzpzPvpKit", name = "EzpzPvpKit", version = "1.0")
public class EzpzPvpKit
{
    /* Variables */
    @Inject
    private Logger logger;
    private LinkedHashMap<String, DuelQueue> queues = null;
    private LinkedHashMap<String, Arena> arenas = new LinkedHashMap<String, Arena>();
    private LinkedHashMap<UUID, Arena> players = new LinkedHashMap<UUID, Arena>();
    private static EzpzPvpKit instance = null;
    private Database db = null;
	private Utils utils = null;

	///
	private StartMatchSetup startMatchSetup = new StartMatchSetup();		
    public StartMatchSetup getStartMatchSetup() 
    {
		return startMatchSetup;
	}

    public void addPlayerPlayers()
    {
    	
    }
    
    
	/* Constructor */
	public EzpzPvpKit()
    {
        this.instance = this;
        this.db = new Database();
    }

    /* Methods */
       
    @Listener
    public void onGameInit(GameInitializationEvent event)
    {      
    	/* Load Commands */
    	
    	try 
    	{
			CommandHandler launch =  new CommandHandler();
			utils = new Utils();
	    	getLogger().info(" Commands initialized");
		} 
    	catch (Exception e) 
    	{
    		getLogger().info(" Commands failed to initialize");
		}
    	
    	/*  Register events */
    	
        Sponge.getGame().getEventManager().registerListeners(this, new EventHandler());
        
        /*  Create thread */
        
        Task threadMatchStart = Sponge.getScheduler().createTaskBuilder().execute(startMatchSetup)
        	.interval(1, TimeUnit.SECONDS)
            .name("StartMatchSetup").submit(this);
        
        
        getLogger().info(" Started");
    }    
   
    

    /* Getters and Setters */
    
    public static Logger getLogger()
    {
        return EzpzPvpKit.getInstance().logger;
    }

    public static EzpzPvpKit getInstance()
    {
        synchronized(EzpzPvpKit.class)
        {
            if (EzpzPvpKit.instance == null)
                EzpzPvpKit.instance = new EzpzPvpKit();
        }

        return EzpzPvpKit.instance;
    }

    public DuelQueue getQueue(String name)
    {
        return queues.get(name);
    }

    public Arena getArena(String name)
    {
        return arenas.get(name);
    }
    
    public void addArena(Arena arena)
    {
    	arenas.put(arena.getName(), arena);
    }
    
    public void removeArena(String name)
    {
    	if(isArenaExisting(name))
    		arenas.remove(name);
    }
    
    public Boolean isArenaExisting(String name)
    {
    	return arenas.containsKey(name);
    }
    
    public LinkedList<Text> getArenaList()
    {
    	if(arenas.size()==0)
    		return null;
    	else
    	{
        	LinkedList<Text> arenalist = new LinkedList<Text>();
    		for (Map.Entry<String,Arena> entry : arenas.entrySet()) 
    		{
    			arenalist.add(Text.of(entry.getKey()));
    		}
        	return arenalist;
    	}
    }

    public Arena getPlayerArena(UUID uuid)
    {
        return players.get(uuid);
    }

    public Database getDatabase()
    {
        return this.db;
    }
    
    public Utils getUtils() 
    {
		return utils;
	}
    


}

