package sticklings.terrain;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.google.common.base.Preconditions;

import javafx.geometry.BoundingBox;

/**
 * Represents terrain.
 */
public class TerrainData {
	private ReadWriteLock lock = new ReentrantReadWriteLock(true);
	private TerrainType[] terrainType;
	private int width;
	private int height;
	
	private BoundingBox dirtyRegion;
	
	/**
	 * Creates a new terrain data object with the given data and size
	 * @param terrainType The terrain data itself. The size must match the given with and height
	 * @param width The width of the terrain data
	 * @param height The height of the terrain data
	 * @throws IllegalArgumentException Thrown if the size of the terrain
	 * 			data array does not match the given width and height
	 */
	public TerrainData(TerrainType[] terrainType, int width, int height) throws IllegalArgumentException {
		Preconditions.checkArgument(terrainType.length == width*height, "Size doesnt match");
		
		this.terrainType = terrainType;
		this.width = width;
		this.height = height;
	}
	
	/**
	 * Gets the width of the terrain data
	 * @return The width
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * Gets the height of the terrain data
	 * @return The height
	 */
	public int getHeight() {
		return height;
	}
	
	/**
	 * Locks the terrain data for read access.
	 * This does not protect against writing, please ensure you
	 * do not modify the contents. This does not have a unique lock.
	 * Make sure you call {@link #unlockRead()} when you are done
	 * @return The terrain data array
	 * @see #unlockRead()
	 */
	public TerrainType[] lockRead() {
		lock.readLock().lock();
		return terrainType;
	}
	
	/**
	 * Unlocks the terrain data after being accessed
	 * @see #lockRead()
	 */
	public void unlockRead() {
		lock.readLock().unlock();
	}
	
	/**
	 * Marks a section of the terrain data as having been updated.
	 * This allows quick and efficient rendering of terrain
	 * @param bounds The area that was modified
	 */
	private void markDirty(BoundingBox bounds) {
		if (dirtyRegion == null) {
			dirtyRegion = bounds;
		} else {
			dirtyRegion = new BoundingBox(
				Math.min(dirtyRegion.getMinX(), bounds.getMinX()),
				Math.min(dirtyRegion.getMinY(), bounds.getMinY()),
				Math.max(dirtyRegion.getMaxX(), bounds.getMaxX()),
				Math.max(dirtyRegion.getMaxY(), bounds.getMaxY())
			);
		}
	}
	
	/**
	 * Gets and clears the dirty region of the terrain.
	 * This allows you to get the area that needs re-rendering
	 * @return The area that is dirty, or null
	 */
	public BoundingBox getAndClearDirtyRegion() {
		lock.readLock().lock();
		
		BoundingBox dirty = dirtyRegion;
		dirtyRegion = null;
		
		lock.readLock().unlock();
		
		return dirty;
	}
	
	/**
	 * Replaces ground with air in the given rectangle
	 * @param x1 The minimum x coord
	 * @param y1 The minimum y coord
	 * @param x2 The maximum x coord
	 * @param y2 The maximum y coord
	 */
	public void clearRectAt(int x1, int y1, int x2, int y2) {
		replaceRectAt(x1, y1, x2, y2, TerrainType.GROUND, TerrainType.AIR);
	}
	
	/**
	 * Replaces one terrain type with another within a rectangle
	 * @param x1 The minimum x coord
	 * @param y1 The minimum y coord
	 * @param x2 The maximum x coord
	 * @param y2 The maximum y coord
	 * @param from The type to replace
	 * @param to The type to use as replacement
	 */
	public void replaceRectAt(int x1, int y1, int x2, int y2, TerrainType from, TerrainType to) {
		lock.writeLock().lock();
		for (int x = Math.max(x1, 0); x <= Math.min(x2, width-1); ++x) {
			for (int y = Math.max(y1, 0); y <= Math.min(y2, height-1); ++y) {
				if (terrainType[x + y * width] == from) {
					terrainType[x + y * width] = to;
				}
			}
		}
		
		lock.writeLock().unlock();
	}
	
	/**
	 * Replaces a circle of ground with air
	 * @param x The x coord The x coord to replace at 
	 * @param y The y coord The y coord to replace at
	 * @param radius The radius of the circle
	 */
	public void clearCircleAt(int x, int y, int radius) {
		replaceCircleAt(x, y, radius, TerrainType.GROUND, TerrainType.AIR);
	}
	
	/**
	 * Replaces a circle of terrain with another type
	 * @param x The x coord
	 * @param y The y coord
	 * @param radius The radius of the circle
	 * @param from The type to replace
	 * @param to The type to use as the replacement
	 */
	public void replaceCircleAt(int x, int y, int radius, TerrainType from, TerrainType to) {
		lock.writeLock().lock();
		
		for (int xx = Math.max(x - radius, 0); xx <= Math.min(x + radius, width-1); ++xx) {
			for (int yy = Math.max(y - radius, 0); yy <= Math.min(y + radius, height-1); ++yy) {
				double dx = (xx - x) / (double)radius;
				double dy = (yy - y) / (double)radius;
				
				dx = dx*dx;
				dy = dy*dy;
				
				if (dx + dy < 1) {
					if (terrainType[xx + yy * width] == from) {
						terrainType[xx + yy * width] = to;
					}
				}
			}
		}
		
		markDirty(new BoundingBox(x-radius, y-radius, 2*radius, 2*radius));
		lock.writeLock().unlock();
	}
}
