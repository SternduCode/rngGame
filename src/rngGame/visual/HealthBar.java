package rngGame.visual;

import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import rngGame.ui.WindowDataHolder;


public class HealthBar extends Pane {

	/** The health B. */
	private final AnimatedImage healthBar;

	/** The c. */
	private Canvas c;

	/** The last value. */
	private int lastValue = 0;

	private final rngGame.ui.HealthBar logic;

	private final WindowDataHolder windowDataHolder;

	public HealthBar(rngGame.ui.HealthBar logic, WindowDataHolder windowDataHolder) {

		this.logic = logic;

		this.windowDataHolder = windowDataHolder;

		healthBar = new AnimatedImage(logic.getHealthBar());

	}

	public void scaleF11() {
		c = new Canvas(healthBar.getImage().getWidth(), healthBar.getImage().getHeight());
		getChildren().clear();
		getChildren().addAll(c, healthBar);

	}

	public void update() {
		healthBar.update();
		if (logic.isScaleF11()) {
			scaleF11();
			logic.resetScaleF11();
		}
		if (logic.getDemon().getCurrentHp() != lastValue) {
			c.getGraphicsContext2D().setFill(Color.TRANSPARENT);
			c.getGraphicsContext2D().fillRect(44 * windowDataHolder.scalingFactorX(), 16 * windowDataHolder.scalingFactorY(),
					165 * windowDataHolder.scalingFactorX(), 14 * windowDataHolder.scalingFactorY());
			c.getGraphicsContext2D().fillRect(44 * windowDataHolder.scalingFactorX() + 165 * windowDataHolder.scalingFactorX(),
					18 * windowDataHolder.scalingFactorY(), windowDataHolder.scalingFactorX(), 10 * windowDataHolder.scalingFactorY());
			c.getGraphicsContext2D().setFill(Color.LIMEGREEN);
			if (logic.getDemon().getCurrentHp() > 0) {
				c.getGraphicsContext2D().fillRect(44 * windowDataHolder.scalingFactorX(), 16 * windowDataHolder.scalingFactorY(),
						165 * windowDataHolder.scalingFactorX() * (logic.getDemon().getCurrentHp() / (double) logic.getDemon().getMaxHp()),
						14 * windowDataHolder.scalingFactorY());
				c.getGraphicsContext2D().fillRect(
						44 * windowDataHolder.scalingFactorX()
						+ 165 * windowDataHolder.scalingFactorX() * (logic.getDemon().getCurrentHp() / (double) logic.getDemon().getMaxHp()),
						18 * windowDataHolder.scalingFactorY(), windowDataHolder.scalingFactorX(), 10 * windowDataHolder.scalingFactorY());
			}
			lastValue = logic.getDemon().getCurrentHp();
		}

		setLayoutX(logic.getLayoutX());

	}

}
