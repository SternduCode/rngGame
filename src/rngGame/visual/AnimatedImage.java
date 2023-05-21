package rngGame.visual;

import javafx.scene.image.ImageView;


// TODO: Auto-generated Javadoc
/**
 * The Class AnimatedImage.
 */
public class AnimatedImage extends ImageView {

	/** The logic. */
	protected final rngGame.ui.AnimatedImage logic;

	/**
	 * Instantiates a new animated image.
	 *
	 * @param logic the logic
	 */
	public AnimatedImage(rngGame.ui.AnimatedImage logic) { this.logic = logic; }

	/**
	 * Update.
	 */
	public void update() {
		if (logic.isInitiated() || logic.isDirty()) {
			if (logic.getFrameIndex() < logic.frameCount()) setImage(logic.getFrameAt(logic.getFrameIndex()));
			if (logic.isDirty()) logic.resetDirty();
		}

	}

}
