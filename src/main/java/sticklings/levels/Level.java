package sticklings.levels;

/**
 * Represents the requirements and information about a level
 */
public class Level {
	private final String levelName;
	
	/**
	 * Constructs a new level
	 * @param name The name of the level
	 */
	public Level(String name) {
		this.levelName = name;
	}
	
	/**
	 * Gets the name of the level.
	 * @return The name
	 */
	public String getName() {
		return levelName;
	}
	
	/**
	 * Gets the width of the level in pixels
	 * @return The width
	 */
	public int getWidth() {
		return 500; // TODO: This
	}
	
	/**
	 * Gets the height of the level in pixels
	 * @return The height
	 */
	public int getHeight() {
		return 500; // TODO: This
	}
}
