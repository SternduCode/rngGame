package rngGame.ui;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import rngGame.stats.Demon;
import rngGame.visual.GamePanel;

// TODO: Auto-generated Javadoc
/**
 * The Class HealthBar.
 */
public class HealthBar extends Pane {

	/** The health B. */
	private final ImageView healthB;

	/** The gp. */
	private final GamePanel gp;

	/** The c. */
	private Canvas c;

	/** The d. */
	private final Demon d;

	/** The last value. */
	private int lastValue = 0;

	/**
	 * Instantiates a new health bar.
	 *
	 * @param gp the gp
	 * @param d the d
	 */
	public HealthBar(GamePanel gp, Demon d) {
		healthB = new ImageView();
		this.gp = gp;
		this.d = d;
		scaleF11();
	}

	/**
	 * Scale F 11.
	 */
	public void scaleF11() {
		healthB.setImage(ImgUtil.getScaledImage(gp, "./res/gui/HealthBar.png",256 ,64));
		c = new Canvas(healthB.getImage().getWidth(), healthB.getImage().getHeight());
		getChildren().clear();
		getChildren().addAll(c, healthB);
	}

	/**
	 * Update.
	 */
	public void update() {
		if (d.getCurrentHp() != lastValue) {
			c.getGraphicsContext2D().setFill(Color.TRANSPARENT);
			c.getGraphicsContext2D().fillRect(44*gp.getScalingFactorX(), 16*gp.getScalingFactorY(), 165*gp.getScalingFactorX(), 14*gp.getScalingFactorY());
			c.getGraphicsContext2D().fillRect(44*gp.getScalingFactorX() + 165*gp.getScalingFactorX(), 18*gp.getScalingFactorY(), gp.getScalingFactorX(), 10*gp.getScalingFactorY());
			c.getGraphicsContext2D().setFill(Color.LIMEGREEN);
			if (d.getCurrentHp() > 0) {
				c.getGraphicsContext2D().fillRect(44 * gp.getScalingFactorX(), 16 * gp.getScalingFactorY(),
						165 * gp.getScalingFactorX() * (d.getCurrentHp() / (double) d.getMaxHp()), 14 * gp.getScalingFactorY());
				c.getGraphicsContext2D().fillRect(44 * gp.getScalingFactorX() + 165 * gp.getScalingFactorX() * (d.getCurrentHp() / (double) d.getMaxHp()),
						18 * gp.getScalingFactorY(), gp.getScalingFactorX(), 10 * gp.getScalingFactorY());
			}
			lastValue = d.getCurrentHp();
		}
	}
}