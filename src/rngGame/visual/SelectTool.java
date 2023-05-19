package rngGame.visual;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


// TODO: Auto-generated Javadoc
/**
 * The Class SelectTool.
 */
public class SelectTool extends Rectangle {

	/** The logic. */
	private final rngGame.tile.SelectTool logic;

	/**
	 * Instantiates a new select tool.
	 *
	 * @param logic the logic
	 */
	public SelectTool(rngGame.tile.SelectTool logic) {
		this.logic = logic;
		setDisable(true);
		setFill(Color.TRANSPARENT);
		setStroke(Color.color(1, 1, 1, .75));
		setStrokeWidth(2.5);

	}

	/**
	 * Update.
	 */
	public void update() {

	}

}
