package sticklings.scene;

import java.util.EnumSet;

import sticklings.terrain.TerrainData;
import sticklings.terrain.TerrainType;
import sticklings.util.Location;

public class MovementController {
	private final Entity entity;
	
	private EnumSet<MovementType> allowedTypes;
	
	public MovementController(Entity entity) {
		this.entity = entity;
		allowedTypes = EnumSet.noneOf(MovementType.class);
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
		
		try {
			// TODO: Terrain collisions / collisions
			
			// DEBUG: Just move 5px/s to right
			myLocation.x += 5 * deltaTime;
		} finally {
			terrain.unlockRead();
		}
	}
	
	/**
	 * Types of movement
	 */
	public enum MovementType {
		Walk,
		Swim,
		Float
	}
}
