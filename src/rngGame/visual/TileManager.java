package rngGame.visual;

import javafx.scene.layout.Pane;
import rngGame.ui.ScalingFactorHolder;


// TODO: Auto-generated Javadoc
/**
 * The Class TileManager.
 */
public class TileManager extends Pane {

	/** The logic. */
	private final rngGame.tile.TileManager logic;

	/** The scaling factor holder. */
	private final ScalingFactorHolder scalingFactorHolder;

	/**
	 * Instantiates a new tile manager.
	 *
	 * @param logic the logic
	 * @param scalingFactorHolder the scaling factor holder
	 */
	public TileManager(rngGame.tile.TileManager logic, ScalingFactorHolder scalingFactorHolder) {
		this.logic = logic;

		this.scalingFactorHolder = scalingFactorHolder;

	}

}
