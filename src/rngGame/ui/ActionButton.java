package rngGame.ui;

import java.util.function.Consumer;

// TODO: Auto-generated Javadoc
/**
 * The Class AktionButton.
 */
public class ActionButton extends Button {

	/** The druck. */
	private final String nichts = "./res/gui/always/InteractionNichts.png",
			kann = "./res/gui/always/InteractionMoeglich.png",
			druck = "./res/gui/always/InteractionGedrueckt.png";

	/** The ifc. */
	private boolean ifc = false;

	/** The time the button was last set to true. */
	private long lastSetToTrue = 0l;

	/** The handler. */
	private Consumer<GamePanel> handler = null;

	private boolean visible;

	/**
	 * Instantiates a new aktion button.
	 *
	 * @param gamePanel the game panel
	 * @param windowDataHolder the window data holder
	 */
	public ActionButton(GamePanel gamePanel, WindowDataHolder windowDataHolder) {
		super("./res/gui/always/InteractionNichts.png", windowDataHolder);

		visible = true;

		setImgRequestedWidth(150);
		setImgRequestedHeight(150);

		f11Scale();

		setOnPressed(me -> {
			if (ifc) {
				init(druck);
				if (handler != null) handler.accept(gamePanel);
			}
		});
		setOnReleased(me -> {
			if (ifc) init(kann);
		});


	}

	/**
	 * F 11 scale.
	 */
	public void f11Scale() {
		init(nichts);
	}

	/**
	 * Sets the interactionbutton kann.
	 *
	 * @param ifc the new interactionbutton kann
	 * @param handler the handler
	 */
	public void setIfInteractionButtonCanBePressed(boolean ifc, Consumer<GamePanel> handler) {
		this.ifc = ifc;
		if (ifc) {
			this.handler = handler;

			if (getPath() != druck)
				init(kann);
			lastSetToTrue = System.currentTimeMillis();
		} else {
			init(nichts);
			this.handler = null;
		}
	}

	public void setVisible(boolean visible) { this.visible = visible; }

	/**
	 * Update.
	 */
	@Override
	public void update() {
		if (System.currentTimeMillis() - lastSetToTrue > 50) setIfInteractionButtonCanBePressed(false, null);
	}

}
