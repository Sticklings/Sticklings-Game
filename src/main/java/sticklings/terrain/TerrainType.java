package sticklings.terrain;

/**
 * Represents the type of terrain at a point
 */
public enum TerrainType {
	AIR,
	GROUND,
	WATER;
	
	@Override
	public String toString() {
		return name().substring(0, 1);
	};
}
