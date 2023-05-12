package rngGame.ui;

import java.util.function.Consumer;

import javafx.event.*;
import javafx.scene.input.MouseEvent;
import rngGame.visual.*;

// TODO: Auto-generated Javadoc
/**
 * The Class Button.
 */
public class Button extends AnimatedImage {

	/**
	 * Instantiates a new button.
	 *
	 * @param gp the gp
	 */
	public Button(GamePanel gp) { super(gp); }

	/**
	 * Instantiates a new button.
	 *
	 * @param path the path
	 * @param gp   the gp
	 */
	public Button(String path, GamePanel gp) { super(path, gp); }


	/**
	 * Sets the on action.
	 *
	 * @param ev the new on action
	 */
	public void setOnAction(EventHandler<ActionEvent> ev) {
		setOnReleased(me -> ev.handle(new ActionEvent(me.getSource(), me.getTarget())));
	}


	/**
	 * Sets the on pressed.
	 *
	 * @param mv the new on pressed
	 */
	public void setOnPressed(EventHandler<MouseEvent> mv) {
		setOnMousePressed(mv);
	}

	/**
	 * Sets the on released.
	 *
	 * @param mv the new on released
	 */
	public void setOnReleased(EventHandler<MouseEvent> mv) {
		setOnMouseReleased(i -> ((Consumer<MouseEvent>) e -> gamepanel.getLgp().makeSound("click.wav")).andThen(e -> mv.handle(e)).accept(i));

	}

}
