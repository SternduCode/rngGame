package rngGame.ui;

import rngGame.stats.Demon;

// TODO: Auto-generated Javadoc
/**
 * The Class HealthBar.
 */
public class HealthBar {

	/** The health B. */
	private final AnimatedImage healthBar;

	/** The d. */
	private final Demon demon;

	private boolean scaleF11;

	private int layoutX;

	/**
	 * Instantiates a new health bar.
	 *
	 * @param gp the gp
	 * @param d the d
	 */
	public HealthBar(WindowDataHolder windowDataHolder, Demon demon) {
		healthBar	= new AnimatedImage(windowDataHolder);
		this.demon		= demon;

		scaleF11();
	}

	public Demon getDemon() { return demon; }

	public AnimatedImage getHealthBar() { return healthBar; }

	public int getLayoutX() { return layoutX; }

	public boolean isScaleF11() { return scaleF11; }

	public void resetScaleF11() { scaleF11 = false; }

	/**
	 * Scale F 11.
	 */
	public void scaleF11() {
		healthBar.init("./res/gui/HealthBar.png");
		healthBar.setImgRequestedWidth(256);
		healthBar.setImgRequestedHeight(64);
		healthBar.update();
		scaleF11 = true;

	}

	public void setLayoutX(int layoutX) { this.layoutX = layoutX; }
}