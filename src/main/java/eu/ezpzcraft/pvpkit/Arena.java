package eu.ezpzcraft.pvpkit;


import java.util.LinkedHashMap;
import java.util.Map;

import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.flowpowered.math.vector.Vector3d;


public class Arena
{
    private String name = null;
    private Location<World> pos1 = null;
    private Location<World> pos2 = null; 
    private Vector3d rotation1 = null;
    private Vector3d rotation2 = null;
    private String type = null;
    private float voteScore = 0;
    private LinkedHashMap<Location<World>, BlockState> modifiedBlocks = null; // TODO block

    public Arena(String name, String type, 
    				Location<World> pos1, Vector3d rotation1, 
    				Location<World> pos2, Vector3d rotation2) throws Exception
    {
        this.name = name;
        this.pos1 = pos1;
        this.pos2 = pos2;
        this.type = type;
        this.rotation1 = rotation1;
        this.rotation2 = rotation2;
        /* Fetch vote score from DB
        this.voteScore = 0;
        this.modifiedBlocks = new LinkedHashMap<Location<World>, BlockState>();*/
    }

    public String getName()
    {
        return name;
    }

	public Location<World> getPos1() {
		return pos1;
	}

	public void setPos1(Location<World> pos1) {
		this.pos1 = pos1;
	}

	public Location<World> getPos2() {
		return pos2;
	}

	public void setPos2(Location<World> pos2) {
		this.pos2 = pos2;
	}

	public Vector3d getRotation1() {
		return rotation1;
	}

	public void setRotation1(Vector3d rotation1) {
		this.rotation1 = rotation1;
	}

	public Vector3d getRotation2() {
		return rotation2;
	}

	public void setRotation2(Vector3d rotation2) {
		this.rotation2 = rotation2;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setName(String name) {
		this.name = name;
	}

  
    public void reset()
    {
        // Remove all blocks that have been modified (placed or removed)
        for (Map.Entry<Location<World>, BlockState> entry : modifiedBlocks.entrySet())
        {
            entry.getKey().setBlock( entry.getValue() );
        }
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
