package eu.ezpzcraft.pvpkit;

import org.slf4j.Logger;
import com.google.inject.Inject;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.plugin.Plugin;

@Plugin(id = "example", name = "EzpzPvpKit", version = "1.0")
public class EzpzPvpKit
{
    @Inject
    private Logger logger;

    @Listener
    public void onServerStart(GameStartedServerEvent event)
    {
        getLogger().info("test");
    }

    public Logger getLogger() {
        return logger;
    }
}
