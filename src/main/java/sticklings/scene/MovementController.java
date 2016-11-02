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

/**
 * Controls movement of sticklings based on the terrain and
 * movement rules of the sticklings.
 */
public class MovementController {
	private final Stickling entity;

	private double fallSpeed;
	private double floatSpeed;
	private double walkSpeed;
	private double swimSpeed;
	private double maxHillClimb;

	private EnumSet<MovementType> allowedTypes;

	private Set<Entity> collidingWith;

	/**
	 * Creates a new MovementController for the given stickling
	 * 
	 * @param entity The stickling
	 */
	public MovementController(Stickling entity) {
		this.entity = entity;
		allowedTypes = EnumSet.noneOf(MovementType.class);
		collidingWith = Sets.newIdentityHashSet();

		// Defaults
		fallSpeed = 60;
		floatSpeed = 30;
		walkSpeed = 15;
		swimSpeed = 3;

		maxHillClimb = 5;
	}

	/**
	 * Sets the types of movement allowed by this controller
	 * 
	 * @param types An enum set of MovementType
	 */
	public void setAllowedMovement(EnumSet<MovementType> types) {
		allowedTypes = types;
	}

	/**
	 * Allows this controller to perform a specific type of movement
	 * 
	 * @param type The movement type
	 */
	public void allowMovement(MovementType type) {
		allowedTypes.add(type);
	}

	/**
	 * Disallows this controller from performing a specific type of movement
	 * 
	 * @param type The movement type
	 */
	public void blockMovement(MovementType type) {
		allowedTypes.remove(type);
	}

	/**
	 * Gets the allowed movement types on this controller
	 * 
	 * @return An EnumSet of MovementType
	 */
	public EnumSet<MovementType> getAllowedMovement() {
		return allowedTypes;
	}

	/**
	 * Performs movement on the entity
	 * 
	 * @param deltaTime The update time in seconds
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
			int groundDepth = getGroundDepth(TerrainType.GROUND, bounds, terrainData, width);
			if (allowedTypes.contains(MovementType.Swim)) {
				int waterDepth = getGroundDepth(TerrainType.WATER, bounds, terrainData, width);
				waterDepth += bounds.getHeight() / 2;

				groundDepth = Math.min(groundDepth, waterDepth);
			}

			if (groundDepth > 0) {
				// In air
				double fallDepth = (allowedTypes.contains(MovementType.Float) ? floatSpeed : fallSpeed) * deltaTime;
				if (groundDepth > fallDepth) {
					myLocation.y += fallDepth;
					entity.setDistanceFallen(entity.getDistanceFallen() + fallDepth);
				} else {
					myLocation.y += groundDepth;
					entity.setDistanceFallen(entity.getDistanceFallen() + groundDepth);
				}
			} else if (allowedTypes.contains(MovementType.Walk)) {
				// On ground
				int dir = entity.getFacing().getDir();
				entity.setDistanceFallen(0);

				double moveDist = walkSpeed * deltaTime * dir;

				BoundingBox desired = new BoundingBox(bounds.getMinX() + moveDist, bounds.getMinY(), bounds.getWidth(), bounds.getHeight());

				int forwardGroundDepth = getGroundDepth(TerrainType.GROUND, desired, terrainData, width);
				if (allowedTypes.contains(MovementType.Swim)) {
					int waterDepth = getGroundDepth(TerrainType.WATER, desired, terrainData, width);
					waterDepth += bounds.getHeight() / 2;

					forwardGroundDepth = Math.min(forwardGroundDepth, waterDepth);
				}

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

				Collideable collideable = (Collideable) entity;
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

	private int getGroundDepth(TerrainType checkType, BoundingBox bounds, TerrainType[] data, int width) {
		int minDepth = Integer.MAX_VALUE;
		int middleX = (int) ((bounds.getMinX() + bounds.getMaxX()) / 2);
		for (int x = middleX - 2; x <= middleX + 2; ++x) {
			// Check bounds
			if (x < 0 || x >= width)
				continue;

			int depth = 0;
			for (int y = (int) bounds.getMinY(); y <= (int) (bounds.getMaxY() + bounds.getHeight()); ++y) {
				// Check bounds
				if (x + y * width > data.length) {
					// Off screen
					depth = Integer.MAX_VALUE;
					break;
				}

				if (y < 0) {
					continue;
				}

				TerrainType type = data[x + y * width];
				if ((checkType == null && type != TerrainType.AIR) || type == checkType) {
					break;
				}

				++depth;
			}

			if (minDepth < 0 || depth < minDepth) {
				minDepth = depth;
			}
		}

		// 0 would be at top of bounds, offset it
		minDepth -= (int) bounds.getHeight();

		return minDepth;
	}

	/**
	 * Types of movement
	 */
	public enum MovementType {
		Walk,
		Swim,
		Float
	}

	/**
	 * Direction of movement
	 */
	public enum MovementDir {
		Right,
		Left;

		/**
		 * Gets the direction as a signed integer.
		 * 
		 * @return -1 for Left, 1 for Right
		 */
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

		/**
		 * Gets the opposite movement direction
		 * 
		 * @return The opposite
		 */
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
