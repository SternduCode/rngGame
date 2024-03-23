package rngGame.ui;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import rngGame.main.WindowManager;
import rngGame.stats.Demon;
import rngGame.tile.ImgUtil;
import rngGame.visual.GamePanel;

public class HealthBar extends Pane {
	
	private final ImageView healthB;
	private final GamePanel gp;
	private Canvas c;
	private Demon d;

	public HealthBar(GamePanel gp, Demon d) {
		healthB = new ImageView();
		this.gp = gp;
		this.d = d;
		scaleF11();
	}
	
	public void scaleF11() {
		healthB.setImage(ImgUtil.getScaledImage("./res/gui/HealthBar.png",256 ,64));
		c = new Canvas(healthB.getImage().getWidth(), healthB.getImage().getHeight());
		getChildren().clear();
		getChildren().addAll(c, healthB);
	}

	public void update() {
		c.getGraphicsContext2D().clearRect(0, 0, c.getWidth(), c.getHeight());
		c.getGraphicsContext2D().setFill(Color.LIMEGREEN);
		if(d.getCurrenthp() > 0) {
		c.getGraphicsContext2D().fillRect(44* WindowManager.INSTANCE.getScalingFactorX(), 16*WindowManager.INSTANCE.getScalingFactorY(), 165*WindowManager.INSTANCE.getScalingFactorX()*(d.getCurrenthp()/(double)d.getMaxHp()), 14*WindowManager.INSTANCE.getScalingFactorY());
		c.getGraphicsContext2D().fillRect(44* WindowManager.INSTANCE.getScalingFactorX() + 165*WindowManager.INSTANCE.getScalingFactorX()*(d.getCurrenthp()/(double)d.getMaxHp()), 18*WindowManager.INSTANCE.getScalingFactorY(), WindowManager.INSTANCE.getScalingFactorX(), 10*WindowManager.INSTANCE.getScalingFactorY());
		}
	}

	public void setDemon(Demon d) {
		this.d = d;
	}
}
