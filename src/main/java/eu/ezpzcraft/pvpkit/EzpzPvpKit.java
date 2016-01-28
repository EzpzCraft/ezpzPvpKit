package eu.ezpzcraft.pvpkit;

import org.slf4j.Logger;
import com.google.inject.Inject;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.plugin.Plugin;

import javax.xml.crypto.Data;
import java.util.LinkedHashMap;
import java.util.UUID;

@Plugin(id = "EzpzPvpKit", name = "EzpzPvpKit", version = "1.0")
public class EzpzPvpKit
{
    /* Variables */
    @Inject
    private Logger logger;
    private LinkedHashMap<String, DuelQueue> queues = null;
    private LinkedHashMap<String, Arena> arenas = null;
    private LinkedHashMap<UUID, Arena> players = null;
    private static EzpzPvpKit instance = null;
    private Database db = null;

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
        getLogger().info("[EzpzKit] Started");
        Sponge.getGame().getEventManager().registerListeners(this, new EventHandler());
    }

    public Logger logger()
    {
        return this.logger;
    }

    public static Logger getLogger()
    {
        return EzpzPvpKit.getInstance().logger();
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

    public Arena getPlayerArena(UUID uuid)
    {
        return players.get(uuid);
    }

    public Database getDatabase()
    {
        return this.db;
    }
}
