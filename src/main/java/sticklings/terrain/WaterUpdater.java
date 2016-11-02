package sticklings.terrain;

import java.util.Random;

import javafx.geometry.BoundingBox;

/**
 * Updates water pixels within terrain data
 */
public class WaterUpdater {
	private final TerrainData terrain;

	private int updateLeft;
	private int updateRight;
	private int updateTop;
	private int updateBottom;
	private boolean updated = false;

	private int bottom;
	private int width;

	private int[] xValues;

	private Random rand;

	/**
	 * Contructs a WaterUpdater that updates water in the given TerrainData
	 * 
	 * @param terrain The terrain to update
	 */
	public WaterUpdater(TerrainData terrain) {
		this.terrain = terrain;
		rand = new Random();

		xValues = new int[terrain.getWidth()];
		for (int i = 0; i < terrain.getWidth(); ++i) {
			xValues[i] = i;
		}

		bottom = terrain.getHeight() - 1;
		width = terrain.getWidth();
	}

	/**
	 * Provides a random ordering of x values to eliminate water flow bias
	 */
	private void shuffleX() {
		for (int i = xValues.length - 1; i > 0; i--) {
			int index = rand.nextInt(i + 1);
			// Simple swap
			int a = xValues[index];
			xValues[index] = xValues[i];
			xValues[i] = a;
		}
	}

	/**
	 * Updates the water within the terrain
	 * 
	 * @param deltaTime The delta time in seconds
	 */
	public void update(double deltaTime) {
		TerrainType[] data = terrain.lockWrite();

		// If we loop on x from 0 to width, then water will flow left
		// If we loop on x from width-1 to 0, then water will flow right
		// Randomness seems to be the only solution where water will
		// flow to both sides
		shuffleX();

		updated = false;

		// Go from the bottom to the top as water falls down
		for (int y = bottom; y >= 0; --y) {
			for (int x : xValues) {
				if (data[g(x, y)] == TerrainType.WATER) {
					// Flow down
					if (y == bottom) {
						data[g(x, y)] = TerrainType.AIR;
						recordUpdateArea(x, y);
					} else {
						boolean solidBelow = data[g(x, y + 1)] != TerrainType.AIR;

						int range = (solidBelow ? 30 : 10);
						range = rand.nextInt(range) + 1;

						int left = 0;
						int leftDown = 0;
						int leftDist = 0;
						while (leftDist < range) {
							// See if we can go down and to the side
							boolean canDown = (y + leftDown + 1 < bottom && data[g(x - left, y + leftDown + 1)] == TerrainType.AIR);
							boolean canSide = (x - left - 1 >= 0 && data[g(x - left - 1, y + leftDown)] == TerrainType.AIR);

							if (canDown && canSide) {
								// Both, choose one of them
								if (rand.nextBoolean()) {
									canDown = false;
								} else {
									canSide = false;
								}
							} else if (!canDown && !canSide) {
								// None, break
								break;
							}

							if (canDown) {
								++leftDown;
							} else {
								++left;
							}

							++leftDist;
						}

						int right = 0;
						int rightDown = 0;
						int rightDist = 0;
						while (rightDist < range) {
							// See if we can go down and to the side
							boolean canDown = (y + rightDown + 1 < bottom && data[g(x + right, y + rightDown + 1)] == TerrainType.AIR);
							boolean canSide = (x + right + 1 < width && data[g(x + right + 1, y + rightDown)] == TerrainType.AIR);

							if (canDown && canSide) {
								// Both, choose one of them
								if (rand.nextFloat() < 0.1f) {
									canDown = false;
								} else {
									canSide = false;
								}
							} else if (!canDown && !canSide) {
								// None, break
								break;
							}

							if (canDown) {
								++rightDown;
							} else {
								++right;
							}

							++rightDist;
						}

						if (leftDist != 0 && rightDist != 0) {
							if (rand.nextFloat() < 0.1f) {
								rightDist = 0;
							} else {
								leftDist = 0;
							}
						}

						int destX;
						int destY;
						if (rightDist != 0) {
							destX = x + right;
							destY = y + rightDown;
						} else if (leftDist != 0) {
							destX = x - left;
							destY = y + leftDown;
						} else if (!solidBelow) {
							destX = x;
							destY = y + 1;
						} else {
							continue;
						}

						// destY = y;

						// Move it
						data[g(destX, destY)] = TerrainType.WATER;
						data[g(x, y)] = TerrainType.AIR;

						recordUpdateArea(x, y);
						recordUpdateArea(destX, destY);
					}
				}
			}
		}

		if (updated) {
			terrain.markDirty(new BoundingBox(updateLeft, updateTop, updateRight - updateLeft, updateBottom - updateTop));
		}

		terrain.unlockWrite();
	}

	/**
	 * Gets the index in the terrain array given the coords
	 * 
	 * @param x The x coord
	 * @param y The y coord
	 * @return The array index
	 */
	private int g(int x, int y) {
		return x + y * width;
	}

	/**
	 * Tracks what area of the terrain has been modified
	 * 
	 * @param x The updated x coord
	 * @param y The updated y coord
	 */
	private void recordUpdateArea(int x, int y) {
		if (!updated) {
			updateLeft = updateRight = x;
			updateTop = updateBottom = y;
			updated = true;
		} else {
			updateLeft = (x < updateLeft ? x : updateLeft);
			updateTop = (y < updateTop ? y : updateTop);
			updateRight = (x > updateRight ? x : updateRight);
			updateBottom = (y > updateBottom ? y : updateBottom);
		}
	}
}
