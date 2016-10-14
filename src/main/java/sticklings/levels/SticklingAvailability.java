package sticklings.levels;

import java.util.Arrays;

import com.google.common.base.Preconditions;

import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import sticklings.scene.sticklings.SticklingType;

/**
 * Represents how many sticklings of each type can be used
 */
public class SticklingAvailability implements Cloneable {
	private final int[] used;
	private final int[] total;
	
	private final ReadOnlyIntegerWrapper[] remainingProperties;
	
	public SticklingAvailability() {
		total = new int[SticklingType.values().length-1];
		used = new int[SticklingType.values().length-1];
		remainingProperties = new ReadOnlyIntegerWrapper[SticklingType.values().length-1];
	}
	
	private SticklingAvailability(int[] total, int[] used) {
		this.total = total;
		this.used = used;
		remainingProperties = new ReadOnlyIntegerWrapper[SticklingType.values().length-1];
	}
	
	/**
	 * Gets the number of sticklings remaining for the given type
	 * @param type the type
	 * @return The number that can be used
	 */
	public int getRemaining(SticklingType type) {
		if (type == SticklingType.Basic) {
			return Integer.MAX_VALUE;
		}
		
		return total[type.ordinal()-1] - used[type.ordinal()-1];
	}
	
	public ReadOnlyIntegerProperty remainingProperty(SticklingType type) {
		if (type == SticklingType.Basic) {
			throw new IllegalArgumentException();
		}
		
		if (remainingProperties[type.ordinal()-1] == null) {
			ReadOnlyIntegerWrapper wrapper = new ReadOnlyIntegerWrapper();
			wrapper.set(getRemaining(type));
			remainingProperties[type.ordinal()-1] = wrapper;
		}
		
		return remainingProperties[type.ordinal()-1].getReadOnlyProperty();
	}
	
	/**
	 * Gets the number of sticklings that have been used for the given type
	 * @param type the type
	 * @return The number that has been used
	 */
	public int getUsed(SticklingType type) {
		if (type == SticklingType.Basic) {
			return 0;
		}
		
		return used[type.ordinal()-1];
	}
	
	/**
	 * Adjusts the number of used sticklings of the given type.
	 * @param type The Stickling type to adjust. Must not be Basic
	 * @param adjust The amount to adjust
	 * @return True if the amount was adjusted
	 * @throws IllegalArgumentException Thrown if the type is Basic
	 */
	public boolean adjustUsed(SticklingType type, int adjust) {
		Preconditions.checkArgument(type != SticklingType.Basic);
		
		int original = used[type.ordinal()-1]; 
		used[type.ordinal()-1] += adjust;
		
		// Cap the value
		if (used[type.ordinal()-1] < 0) {
			used[type.ordinal()-1] = 0;
		}
		
		if (used[type.ordinal()-1] > total[type.ordinal()-1]) {
			used[type.ordinal()-1] = total[type.ordinal()-1];
		}
		
		if (remainingProperties[type.ordinal()-1] != null) {
			remainingProperties[type.ordinal()-1].set(getRemaining(type));
		}
		
		return used[type.ordinal()-1] != original;
	}
	
	/**
	 * Gets the total number of sticklings that can be used for a given type
	 * @param type The type to check
	 * @return The number that can be used
	 */
	public int getTotal(SticklingType type) {
		if (type == SticklingType.Basic) {
			return Integer.MAX_VALUE;
		}
		
		return total[type.ordinal()-1];
	}
	
	/**
	 * Sets the total number of the given stickling type that can be used
	 * @param type The type of stickling to set. Must not be Basic
	 * @param amount The number of this type that can be used. Must not be negative
	 * @return this for chaining
	 * @throws IllegalArgumentException Thrown if the type is Basic, or the amount is negative
	 */
	public SticklingAvailability setTotal(SticklingType type, int amount) {
		Preconditions.checkArgument(type != SticklingType.Basic);
		Preconditions.checkArgument(amount >= 0);
		
		total[type.ordinal()-1] = amount;
		return this;
	}
	
	@Override
	public SticklingAvailability clone() {
		return new SticklingAvailability(Arrays.copyOf(total, total.length), Arrays.copyOf(used, used.length));
	}
}
