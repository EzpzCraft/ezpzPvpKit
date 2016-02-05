package eu.ezpzcraft.pvpkit;

import org.slf4j.Logger;
import com.google.inject.Inject;

import eu.ezpzcraft.pvpkit.commands.CommandHandler;
import eu.ezpzcraft.pvpkit.events.EventHandler;
import eu.ezpzcraft.pvpkit.events.JoinEventHandler;
import eu.ezpzcraft.pvpkit.events.UseItemHandler;
import eu.ezpzcraft.pvpkit.thread.QueueThread;
import eu.ezpzcraft.pvpkit.thread.StartMatchThread;

import org.spongepowered.api.Game;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.plugin.Plugin;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;

import java.util.concurrent.TimeUnit;

@Plugin(id = "EzpzPvpKit", name = "EzpzPvpKit", version = "1.0")
public class EzpzPvpKit
{
    /* Variables */
    @Inject
    private Logger logger;
    @Inject
    private Game game;
    
    // <type, queue>
    private LinkedHashMap<String, DuelQueue> queues = new LinkedHashMap<String, DuelQueue>();
    // <name, arena>
    private LinkedHashMap<String, Arena> arenas = new LinkedHashMap<String, Arena>();
    // <UUID, PvPPlayer>
    private LinkedHashMap<String, PvPPlayer> players = new LinkedHashMap<String, PvPPlayer>();
    // <name, Team>
    private LinkedHashMap<String, Team> teams = new LinkedHashMap<String, Team>();
    // <type, Arena[]>
    private LinkedHashMap<String, LinkedList<String>> usedArenas = new LinkedHashMap<String, LinkedList<String>>();
    private LinkedHashMap<String, LinkedList<String>> freeArenas = new LinkedHashMap<String, LinkedList<String>>();
    
    private static EzpzPvpKit instance = null; // Singleton
    private Database db = new Database();;
	private Utils utils = null;

	// Threads
	private StartMatchThread startMatchThread = new StartMatchThread();	
	private QueueThread queueThread = new QueueThread();
	
	// Command
	private CommandHandler cmd = null;
    
	/* Constructor */
	public EzpzPvpKit()
    {
        instance = this;
    }

    /* Methods */       
    @Listener
    public void onServerStarting(GameStartedServerEvent event)
    {
    	long startTime = System.nanoTime();
    	
    	/* Database */
    	db.createDB();
        db.loadArenas();
        db.loadQueues();	       
        
        /* Commands */
        this.cmd = new CommandHandler();
		utils = new Utils();
		getLogger().info("Commands initialized");
    	
    	/*  Events */    	
        Sponge.getGame().getEventManager().registerListeners(this, new EventHandler());
        Sponge.getGame().getEventManager().registerListeners(this, new UseItemHandler());
        Sponge.getGame().getEventManager().registerListeners(this, new JoinEventHandler());
        getLogger().info("Event handlers registrated");
        
        /*  Threads */
        Sponge.getScheduler().createTaskBuilder().execute(startMatchThread)
        	.interval(1, TimeUnit.SECONDS)
            .name("StartMatchSetup").submit(this);
        Sponge.getScheduler().createTaskBuilder().execute(queueThread)
            .interval(1, TimeUnit.SECONDS)
            .name("QueueDispatcher").submit(this);
        getLogger().info("Threads launched");
        
        double elapsedTime = (System.nanoTime() - startTime) / 1000000000.;
        logger.info("EzpzPvpKit plugin successfully loaded in " + elapsedTime + " seconds");
    }

    /**
     * Logger getter    
     * @return logger
     */
    public static Logger getLogger()
    {
        return EzpzPvpKit.getInstance().logger;
    }

    /**
     * Get singleton instance
     * @return EzpzPvpKit
     */
    public static EzpzPvpKit getInstance()
    {
        synchronized(EzpzPvpKit.class)
        {
            if (EzpzPvpKit.instance == null)
                EzpzPvpKit.instance = new EzpzPvpKit();
        }

        return EzpzPvpKit.instance;
    }
    
    /**
     * Game getter
     * @return Game
     */
    public Game getGame()
    {
    	return this.game;
    }

    /**
     * Queue getter
     * @param name
     * @return
     */
    public DuelQueue getQueue(String name)
    {
        return queues.get(name);
    }
    
    /**
     * Add a queue
     * @param queue
     */
    public void addQueue(DuelQueue queue)
    {
    	this.queues.put(queue.getName(), queue);
    }
    
    /**
     * Remove queue
     * @param name
     */
    public void removeQueue(String name)
    {
    	if(isArenaExisting(name))
    		queues.remove(name);   	
    }
    
    /**
     * Check if a queue exists
     * @param name
     * @return true if it exists, false otherwise
     */
    public Boolean isQueueExisting(String name)
    {
    	return queues.containsKey(name);
    }

    /**
     * Get the arena
     * @param name
     * @return Arena
     */
    public Arena getArena(String name)
    {
        return arenas.get(name);
    }
    
    /**
     * Add an arena
     * @param arena
     */
    public void addArena(Arena arena)
    {
    	arenas.put(arena.getName(), arena);
    	if(!freeArenas.containsKey(arena.getType()))
    		freeArenas.put(arena.getType(), new LinkedList<String>());
    	if(!usedArenas.containsKey(arena.getType()))
    		usedArenas.put(arena.getType(), new LinkedList<String>());
    	freeArenas.get(arena.getType()).add(arena.getName());
    }
    
    /**
     * Remove an arena
     * @param name
     */
    public void removeArena(String name)
    {
    	if(isArenaExisting(name))
    	{
    		// Remove also the arena type
    		String type = arenas.get(name).getType();
    		freeArenas.get(type).remove(name);
    		usedArenas.get(type).remove(name);
    		arenas.remove(name);
    	}
    }
    
    /**
     * Check if an arena exists
     * @param name
     * @return true if the arena exists, false otherwise
     */
    public Boolean isArenaExisting(String name)
    {
    	return arenas.containsKey(name);
    }

    /**
     * Database object getter
     * @return database
     */
    public Database getDatabase()
    {
        return this.db;
    }
    
    /**
     * Utils object getter
     * @return Utils
     */
    public Utils getUtils() 
    {
		return utils;
	}

    /**
     * Raw arenas list getter
     * @return LinkedHashMap<String, Arena>
     */
    public LinkedHashMap<String, Arena> getArenas()
    {
    	return this.arenas;
    }
    
    /**
     * Raw queues getter
     * @return LinkedHashMap<String, DuelQueue>
     */
    public LinkedHashMap<String, DuelQueue> getQueues()
    {
    	return this.queues;
    }

    /**
     * Get the PvPPlayer associated to a given UUID
     * @param uuid
     * @return pvpPlayer
     */
	public PvPPlayer getPlayer(String uuid) 
	{
		return players.get(uuid);
	}

	/**
	 * Add a new PvPPlayer
	 * @param pvpPlayer
	 */
	public void addPlayer(PvPPlayer pvpPlayer) 
	{
		this.players.put(pvpPlayer.getPlayer().getIdentifier(), pvpPlayer);
	}
	
	/**
	 * Get a free Arena
	 * @param type
	 * @return arena
	 */
	public Arena getFreeArena(String type)
	{
		LinkedList<String> list = freeArenas.get(type);
		logger.info("---- ARENAS :  " + arenas.size());
		logger.info("---- FREEARENAS :  " + freeArenas.size());
		logger.info("---- USEDARENAS :  " + usedArenas.size());
		logger.info("---- FREEARENA<a> :  " + freeArenas.get("a").size());
		if( list.size()<=0 )
				return null;
		Arena arena = arenas.get( list.poll() );
		usedArenas.get(arena.getType()).add(arena.getName());
		
		return arena;
	}
	
	/**
	 * Transert the arena from used to free list
	 * @param arena
	 */
	public void fromUsedToFree(Arena arena)
	{
		this.usedArenas.get(arena.getType()).remove(arena.getName());
		this.freeArenas.get(arena.getType()).add(arena.getName());
	}
	
	/**
	 * Get a team from its name
	 * @param name
	 * @return team
	 */
	public Team getTeam(String name)
	{
		return this.teams.get(name);
	}

	/**
	 * Add a team
	 * @param team
	 */
	public void addTeam(Team team)
	{
		this.teams.put(team.getName(), team);
	}
	
	/**
	 * Remove a team
	 * @param name
	 */
	public void removeTeam(String name)
	{
		this.teams.remove(name);
	}
	
	/**
	 * Command handler getter
	 * @return the command handler
	 */
	public CommandHandler getCommandHandler()
	{
		return this.cmd;
	}
	
	// TODO REMOVE
    public StartMatchThread getStartMatchSetup() 
    {
		return startMatchThread;
	}  
}

