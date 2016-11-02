package sticklings.levels;

import java.net.URL;
import com.google.common.base.Preconditions;

import sticklings.util.Location;

/**
 * Represents the requirements and information about a level
 */
public class Level {
	private final String levelName;
	private final URL terrainMask;
	private final URL terrainForeground;
	private final URL terrainBackground;

	private final int totalSticklings;
	private final int requiredSticklings;

	private final SticklingAvailability typeAvailability;

	private final Location startLocation;
	private final Location endLocation;

	/**
	 * Constructs a new level
	 * 
	 * @param name The name of the level
	 * @param terrainMask Terrain mask
	 * @param terrainForeground the foreground texture for the terrain
	 * @param terrainBackground the background texture for the terrain
	 * @param totalSticklings The number of sticklings that will be spawned
	 * @param requiredSticklings The number of sticklings required to complete the level
	 * @param typeAvailability The number of each type that is available for use
	 */
	public Level(String name, URL terrainMask, URL terrainForeground, URL terrainBackground, int totalSticklings, int requiredSticklings, SticklingAvailability typeAvailability, Location startLocation, Location endLocation) {
		Preconditions.checkArgument(requiredSticklings <= totalSticklings);
		Preconditions.checkArgument(requiredSticklings > 0);
		Preconditions.checkNotNull(typeAvailability);
		Preconditions.checkNotNull(terrainMask);
		Preconditions.checkNotNull(terrainForeground);
		Preconditions.checkNotNull(terrainBackground);

		this.levelName = name;
		this.terrainMask = terrainMask;
		this.terrainForeground = terrainForeground;
		this.terrainBackground = terrainBackground;
		this.totalSticklings = totalSticklings;
		this.requiredSticklings = requiredSticklings;
		this.typeAvailability = typeAvailability;
		this.startLocation = startLocation;
		this.endLocation = endLocation;
	}

	/**
	 * Gets the name of the level.
	 * 
	 * @return The name
	 */
	public String getName() {
		return levelName;
	}

	/**
	 * Gets the URL where the terrain mask image is
	 * 
	 * @return The URL
	 */
	public URL getTerrainMaskURL() {
		return terrainMask;
	}

	/**
	 * Gets the URL where the terrain foreground image is
	 * 
	 * @return The URL
	 */
	public URL getTerrainForegroundURL() {
		return terrainForeground;
	}

	/**
	 * Gets the URL where the terrain background image is
	 * 
	 * @return The URL
	 */
	public URL getTerrainBackgroundURL() {
		return terrainBackground;
	}

	/**
	 * Gets the total number of sticklings that will be spawned in the level
	 * 
	 * @return The total
	 */
	public int getTotalSticklings() {
		return totalSticklings;
	}

	/**
	 * Gets the required number of sticklings to complete the level
	 * 
	 * @return The number
	 */
	public int getRequiredSticklings() {
		return requiredSticklings;
	}

	/**
	 * Gets the number of each type that is available to be used in the level
	 * 
	 * @return A SticklingAvailabilty object
	 */
	public SticklingAvailability getTypeAvailability() {
		return typeAvailability;
	}

	/**
	 * Gets the location of the start gate in the world
	 * 
	 * @return A Location
	 */
	public Location getStartLocation() {
		return startLocation;
	}

	/**
	 * Gets the location of the end gate in the world
	 * 
	 * @return A Location
	 */
	public Location getEndLocation() {
		return endLocation;
	}
}
