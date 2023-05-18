package rngGame.ui;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

// TODO: Auto-generated Javadoc
/**
 * The Class Button.
 */
public class Button extends AnimatedImage {

	/** The on released. */
	private EventHandler<MouseEvent> onPressed, onReleased;

	/**
	 * Instantiates a new button.
	 *
	 * @param scalingFactorHolder the scaling factor holder
	 */
	public Button(ScalingFactorHolder scalingFactorHolder) { super(scalingFactorHolder); }

	/**
	 * Instantiates a new button.
	 *
	 * @param path the path
	 * @param scalingFactorHolder the scaling factor holder
	 */
	public Button(String path, ScalingFactorHolder scalingFactorHolder) { super(path, scalingFactorHolder); }

	/**
	 * Gets the on pressed.
	 *
	 * @return the on pressed
	 */
	public EventHandler<MouseEvent> getOnPressed() { return onPressed; }

	/**
	 * Gets the on released.
	 *
	 * @return the on released
	 */
	public EventHandler<MouseEvent> getOnReleased() { return onReleased; }

	/**
	 * Sets the on pressed.
	 *
	 * @param onPressed the new on pressed
	 */
	public void setOnPressed(EventHandler<MouseEvent> onPressed) {
		this.onPressed = onPressed;
	}

	/**
	 * Sets the on released.
	 *
	 * @param onReleased the new on released
	 */
	public void setOnReleased(EventHandler<MouseEvent> onReleased) {
		this.onReleased = mouseEvent -> {
			SoundHandler.getInstance().makeSound("click.wav");
			onReleased.handle(mouseEvent);
		};

	}

}
