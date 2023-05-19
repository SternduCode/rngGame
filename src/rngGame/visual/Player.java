package rngGame.visual;

// TODO: Auto-generated Javadoc
/**
 * The Class Player.
 */
public class Player extends GameObject {

	/**
	 * Instantiates a new player.
	 *
	 * @param logic the logic
	 */
	public Player(rngGame.entity.Player logic) { super(logic); }

	/**
	 * Update.
	 */
	@Override
	public void update() {
		super.update();
		setLayoutX(BASELINE_OFFSET_SAME_AS_HEIGHT);
		setLayoutY(BASELINE_OFFSET_SAME_AS_HEIGHT);

	}

}
