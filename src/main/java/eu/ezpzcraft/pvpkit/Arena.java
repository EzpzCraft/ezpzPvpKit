package eu.ezpzcraft.pvpkit;


import java.util.LinkedHashMap;
import java.util.Map;

import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.flowpowered.math.vector.Vector3d;


/**
 * <b> Arena, class representing an in-game pvp arena. </b>
 * <p> An arena must be a closed space composed of:
 * <ul>
 * <li> one name (String) </li>
 * <li> two respawn positions (Location and Rotation) </li>
 * <li> one type (String) </li>
 * <li> one appreciation score (float) </li>
 * </ul>
 * 
 */

public class Arena
{
    private String name = null;
    private Location<World> pos1 = null;
    private Location<World> pos2 = null; 
    private Vector3d rotation1 = null;
    private Vector3d rotation2 = null;
    private String type = null;
    private float voteScore = 0;
    // Modified block in order to reset arena: <pos, block>
    private LinkedHashMap<Location<World>, BlockState> 
    modifiedBlocks = new LinkedHashMap<Location<World>, BlockState>();

    public Arena(String name, String type, float voteScore,
    				Location<World> pos1, Vector3d rotation1, 
    				Location<World> pos2, Vector3d rotation2)
    {
        this.name = name;
        this.pos1 = pos1;
        this.pos2 = pos2;
        this.type = type;
        this.rotation1 = rotation1;
        this.rotation2 = rotation2;
        this.voteScore = voteScore;
    }
    
    
    /**
     * Return the name of the arena
     * @return name of the arena
     */
    public String getName()
    {
        return name;
    }
    
    /**
     * Return the first position of the arena
     * @return pos1 of the arena
     */
	public Location<World> getPos1() {
		return pos1;
	}
	
    /**
     * Change the first position of the arena
     * @param pos1 new position1
     */
	public void setPos1(Location<World> pos1) {
		this.pos1 = pos1;
	}

    /**
     * Return the second position of the arena
     * @return pos2 of the arena
     */
	public Location<World> getPos2() {
		return pos2;
	}

    /**
     * Change the second position of the arena
     * @param pos2 new position2
     */
	public void setPos2(Location<World> pos2) {
		this.pos2 = pos2;
	}

    /**
     * Return the first rotation of the arena
     * @return rotation1 of the arena
     */
	public Vector3d getRotation1() 
	{
		return rotation1;
	}

    /**
     * Change the first rotation of the arena
     * @param rotation1 new Rotation1
     */
	public void setRotation1(Vector3d rotation1) 
	{
		this.rotation1 = rotation1;
	}

    /**
     * Return the second rotation of the arena
     * @return rotation2 of the arena
     */
	public Vector3d getRotation2() {
		return rotation2;
	}

    /**
     * Change the second rotation of the arena
     * @param rotation2 new Rotation1
     */
	public void setRotation2(Vector3d rotation2) {
		this.rotation2 = rotation2;
	}

    /**
     * Return the type of the arena
     * @return type of the arena
     */
	public String getType() {
		return type;
	}

    /**
     * Change the type of the arena
     * @param type new type
     */
	public void setType(String type) {
		this.type = type;
	}

    /**
     * Change the name of the arena
     * @param name new name
     */
	public void setName(String name) {
		this.name = name;
	}

    /**
     * Return the voteScore of the arena
     * @return voteScore of the arena
     */
    public Float getVoteScore()
    {
        return voteScore;
    }

    public void updateVoteScore()
    {
        // DB
    }
    
    public void reset()
    {
        // Remove all blocks that have been modified (placed or removed)
        for (Map.Entry<Location<World>, BlockState> entry : modifiedBlocks.entrySet())
        {
            entry.getKey().setBlock( entry.getValue() );
        }
    }
}
