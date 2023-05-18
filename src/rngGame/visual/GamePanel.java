package rngGame.visual;

import javafx.scene.layout.Pane;
import rngGame.ui.ScalingFactorHolder;


// TODO: Auto-generated Javadoc
/**
 * The Class GamePanel.
 */
public class GamePanel extends Pane {

	/** The logic. */
	private final rngGame.main.GamePanel logic;

	/** The tile manager. */
	private final TileManager tileManager;

	/** The layer group. */
	private final LayerGroup layerGroup;

	/**
	 * Instantiates a new game panel.
	 *
	 * @param logic the logic
	 * @param scalingFactorHolder the scaling factor holder
	 */
	public GamePanel(rngGame.main.GamePanel logic, ScalingFactorHolder scalingFactorHolder) {

		this.logic = logic;

		tileManager = new TileManager(scalingFactorHolder);

		layerGroup = new LayerGroup();

	}

	/**
	 * Update.
	 */
	public void update() {

	}

}
