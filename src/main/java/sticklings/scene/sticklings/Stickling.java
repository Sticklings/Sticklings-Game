package sticklings.scene.sticklings;

import sticklings.scene.Entity;
import sticklings.scene.MovementController;
import sticklings.scene.MovementController.MovementDir;

public abstract class Stickling extends Entity {
	private static final double OPERATE_TICK_LENGTH = 0.05;
	
	protected final MovementController locomotor;
	private double timeSinceOperate;
	private MovementDir facing;
	
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
			operate();
		}
		
		locomotor.doMove(deltaTime);
	}
	
	public MovementDir getFacing() {
		return facing;
	}
	
	public void setFacing(MovementDir facing) {
		this.facing = facing;
	}
	
	public double getDistanceFallen() {
		return distanceFallen;
	}
	
	public void setDistanceFallen(double distanceFallen) {
		this.distanceFallen = distanceFallen;
	}
	
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
