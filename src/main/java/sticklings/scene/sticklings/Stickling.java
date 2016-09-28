package sticklings.scene.sticklings;

import sticklings.scene.Entity;
import sticklings.scene.MovementController;

public abstract class Stickling extends Entity {
	private static final double OPERATE_TICK_LENGTH = 0.20;
	
	protected final MovementController locomotor;
	private double timeSinceOperate;
	
	public Stickling() {
		locomotor = new MovementController(this);
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
	
	/**
	 * Called every operate tick
	 */
	protected abstract void operate();
}
