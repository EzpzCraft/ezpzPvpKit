package eu.ezpzcraft.pvpkit;

import org.slf4j.Logger;
import com.google.inject.Inject;

import eu.ezpzcraft.pvpkit.commands.CommandHandler;
import eu.ezpzcraft.pvpkit.events.EventHandler;
import eu.ezpzcraft.pvpkit.events.JoinEventHandler;
import eu.ezpzcraft.pvpkit.events.UseItemHandler;
import eu.ezpzcraft.pvpkit.thread.QueueThread;
import eu.ezpzcraft.pvpkit.thread.ScoreboardThread;
import eu.ezpzcraft.pvpkit.thread.StartMatchThread;

import org.spongepowered.api.Game;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
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
	private ScoreboardThread scorebardThread = new ScoreboardThread();
	private QueueThread queueThread = new QueueThread();
	
    
	/* Constructor */
	public EzpzPvpKit()
    {
        instance = this;
    }

    /* Methods */       
    @Listener
    public void onServerStarting(GameStartedServerEvent event)
    {
    	long startTime = 0;
    	
    	/* Database */
    	db.createDB();
        db.loadArenas();
        db.loadQueues();	
        
        /* Create free arena list */        
        // Find all type of arenas available
        LinkedHashSet<String> types = new LinkedHashSet<String>();
        for(Map.Entry<String, Arena> entry : arenas.entrySet())
        {
        	types.add( entry.getValue().getType() );
        }
        
        for(String type: types)
        {
        	this.freeArenas.put(type, new LinkedList<String>());
        	this.usedArenas.put(type, new LinkedList<String>());
        }
        
        /* Commands */
        CommandHandler cmd = new CommandHandler();
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
        Sponge.getScheduler().createTaskBuilder().execute(scorebardThread) // useless
            .interval(1, TimeUnit.SECONDS)
            .name("Scoreboard").submit(this);
        getLogger().info("Threads launched");
        
        double elapsedTime = (System.nanoTime() - startTime) / 1000000000.0;
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
    	if(!freeArenas.containsKey(arena.getType()))
    	{
    		freeArenas.put(arena.getType(), new LinkedList<String>());
    		usedArenas.put(arena.getType(), new LinkedList<String>());
    	}
    	freeArenas.get(arena.getType()).add(arena.getName());
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

    public LinkedHashMap<String, Arena> getArenas()
    {
    	return this.arenas;
    }
    
    public LinkedHashMap<String, DuelQueue> getQueues()
    {
    	return this.queues;
    }

	public PvPPlayer getPlayer(String uuid) 
	{
		return players.get(uuid);
	}

	public void addPlayer(PvPPlayer pvpPlayer) 
	{
		this.players.put(pvpPlayer.getPlayer().getIdentifier(), pvpPlayer);
	}
	
	public Arena getFreeArena(String type)
	{
		LinkedList<String> list = freeArenas.get(type);
		
		if( list.size()<=0 )
				return null;
		Arena arena = arenas.get( list.poll() );
		usedArenas.get(arena.getType()).add(arena.getName());
		
		return arena;
	}
	
	public Team getTeam(String name)
	{
		return this.teams.get(name);
	}

	public void addTeam(Team team)
	{
		this.teams.put(team.getName(), team);
	}
	
	public void removeTeam(String name)
	{
		this.teams.remove(name);
	}
	
    public StartMatchThread getStartMatchSetup() 
    {
		return startMatchThread;
	}  
}

