package rngGame.visual;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;


// TODO: Auto-generated Javadoc
/**
 * The Class Button.
 */
public class Button extends AnimatedImage {

	/** The logic. */
	private final rngGame.ui.Button logic;

	/**
	 * Instantiates a new button.
	 *
	 * @param logic the logic
	 */
	public Button(rngGame.ui.Button logic) {
		super(logic);
		this.logic = logic;
		setOnPressed(mouseEvent -> {});
		setOnReleased(mouseEvent -> {});
	}

	/**
	 * Sets the on pressed.
	 *
	 * @param onPressed the new on pressed
	 */
	public void setOnPressed(EventHandler<MouseEvent> onPressed) {
		setOnMousePressed(mouseEvent -> {
			onPressed.handle(mouseEvent);
			logic.getOnPressed().handle(mouseEvent);
		});

	}

	/**
	 * Sets the on released.
	 *
	 * @param onReleased the new on released
	 */
	public void setOnReleased(EventHandler<MouseEvent> onReleased) {
		setOnMouseReleased(mouseEvent -> {
			onReleased.handle(mouseEvent);
			logic.getOnReleased().handle(mouseEvent);
		});

	}

}
