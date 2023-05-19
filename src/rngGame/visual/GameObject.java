package rngGame.visual;

// TODO: Auto-generated Javadoc
/**
 * The Class GameObject.
 */
public class GameObject extends AnimatedImage {

	/** The logic. */
	protected rngGame.main.GameObject logic;

	/**
	 * Instantiates a new game object.
	 *
	 * @param logic the logic
	 */
	public GameObject(rngGame.main.GameObject logic) {
		super(logic);
		this.logic = logic;

	}

	/**
	 * Gets the layer.
	 *
	 * @return the layer
	 */
	public int getLayer() { return logic.getLayer(); }

	/**
	 * Update.
	 */
	@Override
	public void update() {
		super.update();

	}

}
