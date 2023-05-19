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
	 * @param path the path
	 * @param windowDataHolder the window data holder
	 */
	public Button(String path, WindowDataHolder windowDataHolder) { super(path, windowDataHolder); }

	/**
	 * Instantiates a new button.
	 *
	 * @param windowDataHolder the window data holder
	 */
	public Button(WindowDataHolder windowDataHolder) { super(windowDataHolder); }

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
