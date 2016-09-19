package sticklings.util;

/**
 * Represents a location in 2D space
 */
public class Location {
	public double x;
	public double y;
	
	/**
	 * Creates a new locatoin at 0,0
	 */
	public Location() {
		this(0, 0);
	}
	
	/**
	 * Creates a new location at the given coords
	 * @param x The x coord
	 * @param y The y coord
	 */
	public Location(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public String toString() {
		return String.format("Location{%.2f,%.2f}", x, y);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Location) {
			Location other = (Location)obj;
			return x == other.x && y == other.y;
		} else {
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		int hash = 7;
		hash = hash * 31 + Double.hashCode(x);
		hash = hash * 31 + Double.hashCode(y);

		return hash;
	}
}
