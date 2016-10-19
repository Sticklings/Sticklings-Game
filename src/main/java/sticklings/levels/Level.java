package sticklings.levels;

import java.net.URL;
import com.google.common.base.Preconditions;

/**
 * Represents the requirements and information about a level
 */
public class Level {
	private final String levelName;
	private final URL terrainMask;
	
	private final int totalSticklings;
	private final int requiredSticklings;
	
	private final SticklingAvailability typeAvailability;
	
	/**
	 * Constructs a new level
	 * @param name The name of the level
	 * @param terrainMask Terrain mask
	 * @param totalSticklings The number of sticklings that will be spawned
	 * @param requiredSticklings The number of sticklings required to complete the level
	 * @param typeAvailability The number of each type that is available for use
	 */
	public Level(String name, URL terrainMask, int totalSticklings, int requiredSticklings, SticklingAvailability typeAvailability) {
		Preconditions.checkArgument(requiredSticklings <= totalSticklings);
		Preconditions.checkArgument(requiredSticklings > 0);
		Preconditions.checkNotNull(typeAvailability);
		
		this.levelName = name;
		this.terrainMask = terrainMask;
		this.totalSticklings = totalSticklings;
		this.requiredSticklings = requiredSticklings;
		this.typeAvailability = typeAvailability;
	}
	
	/**
	 * Gets the name of the level.
	 * @return The name
	 */
	public String getName() {
		return levelName;
	}
	
	/**
	 * Gets the URL where the terrain mask image is
	 * @return The URL
	 */
	public URL getTerrainMaskURL() {
		return terrainMask;
	}
	
	/**
	 * Gets the total number of sticklings that will be spawned in the level
	 * @return The total
	 */
	public int getTotalSticklings() {
		return totalSticklings;
	}
	
	/**
	 * Gets the required number of sticklings to complete the level
	 * @return The number
	 */
	public int getRequiredSticklings() {
		return requiredSticklings;
	}
	
	public SticklingAvailability getTypeAvailability() {
		return typeAvailability;
	}
}
