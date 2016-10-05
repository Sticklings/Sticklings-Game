package sticklings.scene;

import java.util.EnumSet;
import java.util.Set;

import com.google.common.collect.Sets;

import javafx.geometry.BoundingBox;
import sticklings.scene.Collideable.Action;
import sticklings.scene.sticklings.Stickling;
import sticklings.terrain.TerrainData;
import sticklings.terrain.TerrainType;
import sticklings.util.Location;

public class MovementController {
	private final Stickling entity;
	
	private double fallSpeed;
	private double floatSpeed;
	private double walkSpeed;
	private double swimSpeed;
	private double maxHillClimb;
	
	private EnumSet<MovementType> allowedTypes;
	
	private Set<Entity> collidingWith;
	
	public MovementController(Stickling entity) {
		this.entity = entity;
		allowedTypes = EnumSet.noneOf(MovementType.class);
		collidingWith = Sets.newIdentityHashSet();
		
		// Defaults
		fallSpeed = 60;
		floatSpeed = 5;
		walkSpeed = 15;
		swimSpeed = 3;
		
		maxHillClimb = 5;
	}
	
	public void setAllowedMovement(EnumSet<MovementType> types) {
		allowedTypes = types;
	}
	
	public void allowMovement(MovementType type) {
		allowedTypes.add(type);
	}
	
	public void blockMovement(MovementType type) {
		allowedTypes.remove(type);
	}
	
	/**
	 * Performs movement on the entity
	 * @param deltaTime
	 */
	public void doMove(double deltaTime) {
		Scene scene = entity.getScene();
		// Prepare terrain data for terrain collisions
		TerrainData terrain = scene.getTerrain();
		TerrainType[] terrainData = terrain.lockRead();
		int width = terrain.getWidth();
		
		Location myLocation = entity.getLocation();
		BoundingBox bounds = entity.getBounds();
		
		try {
			int groundDepth = getGroundDepth(bounds, terrainData, width);
			
			if (groundDepth > 0) {
				// In air
				double fallDepth = (allowedTypes.contains(MovementType.Float) ? floatSpeed : fallSpeed) * deltaTime;
				if (groundDepth > fallDepth) {
					myLocation.y += fallDepth;
				} else {
					myLocation.y += groundDepth;
				}
			} else if (allowedTypes.contains(MovementType.Walk)) {
				// On ground
				int dir = entity.getFacing().getDir();
				
				double moveDist = walkSpeed * deltaTime * dir;
				
				BoundingBox desired = new BoundingBox(bounds.getMinX() + moveDist, bounds.getMinY(), bounds.getWidth(), bounds.getHeight());
				
				int forwardGroundDepth = getGroundDepth(desired, terrainData, width);
				
				// Move up or down
				if (forwardGroundDepth < 0) {
					// Go up
					if (forwardGroundDepth > -maxHillClimb) { 
						myLocation.y += forwardGroundDepth;
					} else {
						entity.setFacing(entity.getFacing().getOpposite());
						moveDist = 0;
					}
				} else if (forwardGroundDepth > 0) {
					// Go down
					double fallDepth = (allowedTypes.contains(MovementType.Float) ? floatSpeed : fallSpeed) * deltaTime;
					if (forwardGroundDepth > fallDepth) {
						myLocation.y += fallDepth;
					} else {
						myLocation.y += forwardGroundDepth;
					}
				}
				
				myLocation.x += moveDist;
			}
			
			// Handle collisions
			for (Entity entity : scene.getAllEntities()) {
				if (!(entity instanceof Collideable) || entity == this.entity) {
					continue;
				}
				
				Collideable collideable = (Collideable)entity;
				BoundingBox targetBounds = collideable.getBounds();
				if (bounds.intersects(targetBounds)) {
					if (collidingWith.add(entity)) {
						// New collision
						if (collideable.onCollide(this.entity) == Action.BLOCK) {
							// TODO: Not sure if this will be enough
							this.entity.setFacing(this.entity.getFacing().getOpposite());
						}
					}
				} else {
					if (collidingWith.remove(entity)) {
						// No longer colliding
					}
				}
			}
			// TODO: Terrain collisions / collisions
			
			// DEBUG: Just move 5px/s to right
		} finally {
			terrain.unlockRead();
		}
	}
	
	private int getGroundDepth(BoundingBox bounds, TerrainType[] data, int width) {
		int minDepth = Integer.MAX_VALUE;
		int middleX = (int)((bounds.getMinX() + bounds.getMaxX()) / 2);
		for (int x = middleX - 2; x <= middleX + 2; ++x) {
			// Check bounds
			if (x < 0 || x >= width)
				continue;
			
			int depth = 0;
			for (int y = (int)bounds.getMinY(); y <= (int)(bounds.getMaxY() + bounds.getHeight()); ++y) {
				// Check bounds
				if (x + y * width > data.length) 
				{
					// Off screen
					depth = Integer.MAX_VALUE;
					break;
				}
				
				TerrainType type = data[x + y * width];
				if (type != TerrainType.AIR) {
					break;
				}
				
				++depth;
			}
			
			if (minDepth < 0 || depth < minDepth) {
				minDepth = depth;
			}
		}
		
		// 0 would be at top of bounds, offset it
		minDepth -= (int)bounds.getHeight();
		
		
		return minDepth;
	}
	
	private int getFront(Location pos, BoundingBox bounds, TerrainType[] data, int width) {
		return 0;
	}
	
	/**
	 * Types of movement
	 */
	public enum MovementType {
		Walk,
		Swim,
		Float
	}
	
	public enum MovementDir {
		Right,
		Left;
		
		public int getDir() {
			switch (this) {
			case Right:
				return 1;
			case Left:
				return -1;
			default:
				throw new AssertionError();
			}
		}
		
		public MovementDir getOpposite() {
			switch (this) {
			case Right:
				return Left;
			case Left:
				return Right;
			default:
				throw new AssertionError();
			}
		}
		
	}
}
