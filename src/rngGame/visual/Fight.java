package rngGame.visual;

import javafx.animation.TranslateTransition;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import rngGame.ui.WindowDataHolder;


// TODO: Auto-generated Javadoc
/**
 * The Class Fight.
 */
public class Fight extends Pane {

	/** The battlebackgroundvisual. */
	private final AnimatedImage auswahlBackground, battlebackground;

	/** The hh. */
	private final HealthBar eigenMobHealth, demonMobHealth;

	/** The stych. */
	private final Button leaf, majyc, stych;

	private final Group buttongroup;

	/**
	 * Instantiates a new fight.
	 *
	 * @param gamepanel the gamepanel
	 * @param mob       the mob
	 */
	public Fight(rngGame.ui.Fight logic, GamePanel gamePanel, WindowDataHolder windowDataHolder) {

		auswahlBackground	= new AnimatedImage(logic.getAuswahlBackground());
		battlebackground	= new AnimatedImage(logic.getBattleBackground());
		leaf				= new Button(logic.getLeaf());
		majyc				= new Button(logic.getMajyc());
		stych				= new Button(logic.getStych());

		buttongroup = new Group();

		demonMobHealth	= new HealthBar(logic.getDemonMobHealthBar(), windowDataHolder);
		eigenMobHealth	= new HealthBar(logic.getEigenMobHealthBar(), windowDataHolder);

		GameObject	demonMobVisual	= new GameObject(logic.getDemonMob().getDemon(), windowDataHolder, gamePanel.getContextMenu(), gamePanel);
		GameObject	eigenMobVisual	= new GameObject(logic.getEigenMob().getDemon(), windowDataHolder, gamePanel.getContextMenu(), gamePanel);

		getChildren().addAll(eigenMobVisual, demonMobVisual, demonMobHealth, eigenMobHealth);

		buttongroup.getChildren().addAll(leaf, majyc, stych);

		TranslateTransition	ft	= new TranslateTransition(Duration.millis(150), auswahlBackground);
		TranslateTransition	ib1	= new TranslateTransition(Duration.millis(150), buttongroup);

		stych.setOnReleased(e -> {// TODO maybe only scale clored stuff
			majyc.setDisable(true);
			leaf.setDisable(true);

			// stych.setDisable(true);
			ft.setToY(windowDataHolder.gameHeight() / 2);
			// ib1.setToY(gamepanel.getGameHeight() / 2);
			ft.play();
			// ib1.play();
		});

		majyc.setOnReleased(e -> {
			majyc.setDisable(true);
			leaf.setDisable(true);
			stych.setDisable(true);

			ft.setToY(windowDataHolder.gameHeight() / 2);
			ib1.setToY(windowDataHolder.gameHeight() / 2);
			ft.play();
			ib1.play();
		});

		getChildren().addAll(battlebackground, auswahlBackground, buttongroup);

	}

	/**
	 * Update.
	 */
	public void update() {
		demonMobHealth.update();
		eigenMobHealth.update();

	}

}
