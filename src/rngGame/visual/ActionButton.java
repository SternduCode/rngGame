package rngGame.visual;

// TODO: Auto-generated Javadoc
/**
 * The Class ActionButton.
 */
public class ActionButton extends Button {

	/**
	 * Instantiates a new action button.
	 *
	 * @param logic the logic
	 */
	public ActionButton(rngGame.ui.ActionButton logic) { super(logic); }

	/**
	 * Update.
	 */
	@Override
	public void update() {
		super.update();
		setLayoutX(logic.getWindowDataHolder().gameWidth() - 220 * logic.getWindowDataHolder().scalingFactorX());
		setLayoutY(logic.getWindowDataHolder().gameHeight() - 220 * logic.getWindowDataHolder().scalingFactorY());

	}

}
