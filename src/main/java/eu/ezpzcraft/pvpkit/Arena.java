package eu.ezpzcraft.pvpkit;

import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.util.rotation.Rotation;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.LinkedHashMap;
import java.util.Map;

public class Arena
{
    private String name = null;
    private Location<World> pos1 = null; // Lower left
    private Location<World> pos2 = null; // Upper right
    private Location<World> respawn1 = null;
    private Rotation rotation1 = null;
    private Rotation rotation2 = null;
    private Location<World> respawn2 = null;
    private String kitName = null;
    private float voteScore = 0;
    private LinkedHashMap<Location<World>, BlockState> modifiedBlocks = null; // TODO block

    public Arena(String name, Location<World> pos1, Location<World> pos2,
                 String kitName, Location<World> respawn1, Location<World> respawn2) throws Exception
    {
        this.name = name;

        if( pos1.getExtent() != pos2.getExtent() )
            throw new Exception("Cannot create Arena " + name + " because positions" +
                                "don't belong to the same world.");

        this.pos1 = new Location<World>(pos1.getExtent(),
                pos1.getBlockX() < pos2.getBlockX() ? pos1.getBlockX() : pos2.getBlockX(),
                pos1.getBlockY() < pos2.getBlockY() ? pos1.getBlockY() : pos2.getBlockY(),
                pos1.getBlockZ() < pos2.getBlockZ() ? pos1.getBlockZ() : pos2.getBlockZ() );

        this.respawn1 = respawn1;
        this.respawn2 = respawn2;
        this.kitName = kitName;

        // Fetch vote score from DB
        this.voteScore = 0;

        this.modifiedBlocks = new LinkedHashMap<Location<World>, BlockState>();
    }

    public String getName()
    {
        return name;
    }

    public void reset()
    {
        // Remove all blocks that have been modified (placed or removed)
        for (Map.Entry<Location<World>, BlockState> entry : modifiedBlocks.entrySet())
        {
            entry.getKey().setBlock( entry.getValue() );
        }
    }

    public boolean isInside(Location<World> location)
    {
        return  location.getBlockX() >= pos1.getX() &&
                location.getBlockY() >= pos1.getY() &&
                location.getBlockZ() >= pos1.getZ() &&
                location.getBlockX() < pos2.getX() &&
                location.getBlockX() < pos2.getX() &&
                location.getBlockX() < pos2.getX();
    }

    public Location<World> getRespawn1()
    {
        return respawn1;
    }

    public Rotation getRotation1()
    {
        return rotation1;
    }

    public Rotation getRotation2()
    {
        return rotation2;
    }

    public Location<World> getRespawn2()
    {
        return respawn2;
    }

    public String getKitName()
    {
        return kitName;
    }

    public Float getVoteScore()
    {
        return voteScore;
    }

    public void updateVoteScore()
    {
        // DB
    }
}
