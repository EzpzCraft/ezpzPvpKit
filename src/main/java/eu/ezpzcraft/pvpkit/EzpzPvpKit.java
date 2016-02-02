package eu.ezpzcraft.pvpkit;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import com.google.inject.Inject;

import eu.ezpzcraft.pvpkit.events.UseItemHandler;

import org.spongepowered.api.Game;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.game.state.GameStartingServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.scheduler.Scheduler;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

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
    @Inject
    private Game game;
    
    private LinkedHashMap<String, DuelQueue> queues = new LinkedHashMap<String, DuelQueue>();
    private long startTime = 0;
    private LinkedHashMap<String, Arena> arenas = new LinkedHashMap<String, Arena>();
    private LinkedHashMap<UUID, PvPPlayer> players = new LinkedHashMap<UUID, PvPPlayer>();
    private static EzpzPvpKit instance = null;
    private Database db = null;
	private Utils utils = null;

	///
	private StartMatchSetup startMatchSetup = new StartMatchSetup();	
	private UseItemHandler useItemHandler = new UseItemHandler();
	
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
    	this.startTime = System.nanoTime();
    	
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
        Sponge.getGame().getEventManager().registerListeners(this, useItemHandler);
        
        /*  Create thread */
        
        Task threadMatchStart = Sponge.getScheduler().createTaskBuilder().execute(startMatchSetup)
        	.interval(1, TimeUnit.SECONDS)
            .name("StartMatchSetup").submit(this);
        
        getLogger().info(" Started");
    }    
    
    @Listener
    public void onServerStarting(GameStartedServerEvent event)
    {
    	/* Create DB if needed */
    	db.createDB();
    	
        /* Load queues */
        db.loadArenas();
        
        /* Load arenas */
        db.loadQueues();	
        
        double elapsedTime = (System.nanoTime() - startTime) / 1000000000.0;
        logger.info("NtRpg plugin successfully loaded in " + elapsedTime + " seconds");
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
    
    public Game getGame()
    {
    	return this.game;
    }

    public DuelQueue getQueue(String name)
    {
        return queues.get(name);
    }
    
    public void addQueue(DuelQueue queue)
    {
    	this.queues.put(queue.getName(), queue);
    }
    
    public void removeQueue(String name)
    {
    	if(isArenaExisting(name))
    		queues.remove(name);
    }
    
    public Boolean isQueueExisting(String name)
    {
    	return queues.containsKey(name);
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

    public Database getDatabase()
    {
        return this.db;
    }
    
    public Utils getUtils() 
    {
		return utils;
	}
    
    public LinkedList<String> getArenaList()
    {
    	LinkedList<String> arenalist = new LinkedList<String>();
    	for (Map.Entry<String,Arena> entry : arenas.entrySet()) 
    	{   	
    		arenalist.add(entry.getKey());
    	}
    	return arenalist;
    }
    
    public LinkedList<String> getQueueList()
    {
    	LinkedList<String> queuelist = new LinkedList<String>();
    	for (Map.Entry<String,DuelQueue> entry : queues.entrySet()) 
    	{   	
    		queuelist.add(entry.getKey());
    	}
    	return queuelist;
    }

	public PvPPlayer getPlayer(UUID uuid) 
	{
		return players.get(uuid);
	}

	public void addPlayer(PvPPlayer pvpPlayer) 
	{
		this.players.put(pvpPlayer.getPlayer().getUniqueId(), pvpPlayer);
	}

}

