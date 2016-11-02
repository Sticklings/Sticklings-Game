package sticklings.render;

/**
 * Provides the settings for animated textures based on sprite sheets.
 */
public class AnimationSettings {
	int tilesX = 1;
	int tilesY = 1;
	boolean isVertical = true;

	long frameTime = 16;

	public AnimationSettings() {
	}

	/**
	 * Sets the number of rows and columns in the sprite sheet
	 * 
	 * @param rows The number of rows
	 * @param cols The number of columns
	 * @return This settings object for chaining
	 */
	public AnimationSettings tiles(int rows, int cols) {
		tilesX = cols;
		tilesY = rows;
		return this;
	}

	/**
	 * Sets the number of milliseconds each frame is displayed for
	 * 
	 * @param time The time in milliseconds
	 * @return This settings object for chaining
	 */
	public AnimationSettings frameTime(long time) {
		frameTime = time;
		return this;
	}

	/**
	 * Sets the order of frames to be column first, top to bottom
	 * 
	 * @return This settings object for chaining
	 */
	public AnimationSettings vertical() {
		isVertical = true;
		return this;
	}

	/**
	 * Sets the order of frames to be row first, left to right
	 * 
	 * @return This settings object for chaining
	 */
	public AnimationSettings horizontal() {
		isVertical = false;
		return this;
	}
}