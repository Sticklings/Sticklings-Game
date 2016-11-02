package sticklings.ui;

import javafx.scene.Parent;

/**
 * Represents a single UI screen that can be switched out with others
 */
public abstract class Screen {
	/**
	 * Creates the javafx gui that the screen will display. This MUST return a subclass of Parent such as Pane.
	 * 
	 * @return The screens JavaFx gui components
	 */
	public abstract Parent initialize();

	/**
	 * Called when the screen is shown
	 */
	public abstract void onShow();

	/**
	 * Called when the screen is hidden
	 */
	public abstract void onHide();

	/**
	 * Updates this screen.
	 * 
	 * @param deltaTime The time in seconds between last update and current update
	 */
	public abstract void update(double deltaTime);
}
