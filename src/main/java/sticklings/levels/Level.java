package sticklings.levels;

import java.net.URL;

/**
 * Represents the requirements and information about a level
 */
public class Level {
	private final String levelName;
	private final URL terrainMask;
	
	/**
	 * Constructs a new level
	 * @param name The name of the level
	 * @param terrainMask Terrain mask
	 */
	public Level(String name, URL terrainMask) {
		this.levelName = name;
		this.terrainMask = terrainMask;
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
}
