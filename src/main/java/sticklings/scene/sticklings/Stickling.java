package sticklings.scene.sticklings;

import sticklings.scene.Entity;
import sticklings.scene.MovementController;
import sticklings.scene.MovementController.MovementDir;
import sticklings.terrain.TerrainType;
import sticklings.util.Location;

/**
 * The base stickling class. All types of sticklings should extend this.
 * The operate() method is provided for subtypes to perform some every OPERATE_TICK_LENGTH seconds.
 */
public abstract class Stickling extends Entity {
	private static final double OPERATE_TICK_LENGTH = 0.05;
	private static final double MAX_FALL_DISTANCE = 60;

	protected final MovementController locomotor;
	private double timeSinceOperate;
	private MovementDir facing;
	private boolean killFlag;

	private double distanceFallen;

	public Stickling() {
		locomotor = new MovementController(this);
		facing = MovementDir.Right;
		setBounds(20, 20);
	}

	@Override
	public void update(double deltaTime) {
		timeSinceOperate += deltaTime;

		// Handle operations
		if (timeSinceOperate > OPERATE_TICK_LENGTH) {
			timeSinceOperate -= OPERATE_TICK_LENGTH;

			Location pos = getLocation();
			if (getScene().getTerrain().isType(TerrainType.WATER, (int) pos.x, (int) pos.y - 8)) {
				// Drown
				remove();
			}

			// Checking if Sticklings are falling from maximum falling distance
			if (getDistanceFallen() >= MAX_FALL_DISTANCE) {
				killFlag = true;
			}
			// if Sticklings fall from max_fall_distance, they will die
			// when they touch the ground
			if (killFlag && getDistanceFallen() <= 0) {
				// pretending floater sticklings not to be died
				if (!(this instanceof FloaterStickling))
					remove();
			}
			operate();
		}

		locomotor.doMove(deltaTime);
	}

	/**
	 * Gets the sticklings forward direction
	 * 
	 * @return The forward direction
	 */
	public MovementDir getFacing() {
		return facing;
	}

	/**
	 * Sets the sticklings forward direction
	 * 
	 * @param facing The new forward direction
	 */
	public void setFacing(MovementDir facing) {
		this.facing = facing;
	}

	/**
	 * Gets the distance the stickling has fallen in units
	 * 
	 * @return The distance fallen
	 */
	public double getDistanceFallen() {
		return distanceFallen;
	}

	/**
	 * Sets the distance the stickling has fallen in units
	 * 
	 * @param distanceFallen The new distance fallen
	 */
	public void setDistanceFallen(double distanceFallen) {
		this.distanceFallen = distanceFallen;
	}

	/**
	 * Copies properties such as facing, movement types, and distance fallen from the other stickling.
	 * 
	 * @param other The stickling to copy from
	 */
	public void copyFrom(Stickling other) {
		setLocation(other.getLocation().copy());
		this.locomotor.setAllowedMovement(locomotor.getAllowedMovement());
		this.facing = other.facing;
		this.distanceFallen = other.distanceFallen;
	}

	/**
	 * Called every operate tick
	 */
	protected abstract void operate();
}
