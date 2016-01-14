package eu.ezpzcraft.pvpkit;

import org.spongepowered.api.effect.particle.ParticleType;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.LinkedList;

public class Arena
{
    private String name = null;
    private Location<World> pos1 = null;
    private Location<World> pos2 = null;
    private String kitName = null;
    private float voteScore = 0;
    private LinkedList<ParticleType.Block> modifiedBlocks = null; // TODO block

    public Arena(String name, Location<World> pos1, Location<World> pos2, String kitName)
    {
        this.name = name;
        this.pos1 = pos1;
        this.pos2 = pos2;
        this.kitName = kitName;

        // Fetch vote score from DB
        this.voteScore = 0;

        this.modifiedBlocks = new LinkedList<ParticleType.Block>();
    }

    public void reset()
    {
        // Remove all blocks that have been modified (placed or removed)
    }
}
