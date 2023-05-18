package rngGame.main;

import java.io.FileNotFoundException;
import java.util.concurrent.atomic.AtomicReference;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.scene.image.*;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import rngGame.ui.*;

// TODO: Auto-generated Javadoc
/**
 * The Class TitleScreen.
 */
public class TitleScreen extends Pane {

	/** The iv. */
	private final ImageView iv;

	/** The scaling factor holder. */
	private final ScalingFactorHolder scalingFactorHolder;

	/** The last. */
	private long last = 0l;

	/** The frames. */
	private Image[] frames;

	/** The curr frame. */
	private int currFrame = 0;

	/** The pfail. */
	private Button ploy = null, settins = null, clous = null, pfail = null;

	/** The game panel pane. */
	private final Pane gamePanelPane;

	/** The target FPS. */
	private final double targetFPS = 60;

	/** The game panel. */
	private GamePanel gamePanel;

	/** The game panel visual. */
	private rngGame.visual.GamePanel gamePanelVisual;

	/** The pfail visual. */
	private rngGame.visual.Button ployVisual = null, settinsVisual = null, clousVisual = null, pfailVisual = null;

	/** The loading screen visual. */
	private final rngGame.visual.LoadingScreen loadingScreenVisual;

	/**
	 * Instantiates a new title screen.
	 *
	 * @param scalingFactorHolder the scaling factor holder
	 */
	public TitleScreen(ScalingFactorHolder scalingFactorHolder) {
		iv = new ImageView();

		gamePanelPane = new Pane();

		this.scalingFactorHolder = scalingFactorHolder;

		loadingScreenVisual = new rngGame.visual.LoadingScreen(LoadingScreen.getDefaultLoadingScreen(scalingFactorHolder));
		loadingScreenVisual.setDisable(true);
		loadingScreenVisual.setOpacity(0);

		clous = new Button("./res/backgrounds/Clous.png", scalingFactorHolder);
		clous.setOnPressed(e -> clous.init("./res/backgrounds/Clous2.png"));
		clous.setOnReleased(e -> {
			clous.init("./res/backgrounds/Clous.png");
			System.exit(0);
		});
		clousVisual = new rngGame.visual.Button(clous);

		ploy = new Button("./res/backgrounds/Ploy.png", scalingFactorHolder);
		ploy.setOnPressed(e -> ploy.init("./res/backgrounds/Ploy2.png"));
		ploy.setOnReleased(e -> {
			new Thread(() -> {
				loadingScreenVisual.goIntoLoadingScreen();

				try {
					gamePanel = new GamePanel(scalingFactorHolder);

					gamePanelPane.getChildren().clear();
					gamePanelPane.getChildren().add(gamePanelVisual = new rngGame.visual.GamePanel(gamePanel, scalingFactorHolder));

					Input.getInstance(scalingFactorHolder).setGamePanel(gamePanel); // pass instance of GamePanel to the Instance of Input
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}

				ploy.init("./res/backgrounds/Ploy.png");
				iv.setVisible(false);
				ployVisual.setVisible(false);
				settinsVisual.setVisible(false);
				clousVisual.setVisible(false);

				try {
					Thread.sleep(2000);
					loadingScreenVisual.goOutOfLoadingScreen();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}).start();

		});
		ployVisual = new rngGame.visual.Button(ploy);

		settins	= new Button("./res/backgrounds/Settins.png", scalingFactorHolder);
		pfail	= new Button("./res/backgrounds/Pfail.png", scalingFactorHolder);
		settinsVisual	= new rngGame.visual.Button(settins);
		pfailVisual		= new rngGame.visual.Button(pfail);
		pfailVisual.setVisible(false);
		settins.setOnPressed(e -> {
			settins.init("./res/backgrounds/Settins2.png");
		});
		settins.setOnReleased(e -> {
			settins.init("./res/backgrounds/Settins.png");
			ployVisual.setVisible(false);
			clousVisual.setVisible(false);
			settinsVisual.setVisible(false);
			pfailVisual.setVisible(true);
			pfail.setOnPressed(_e -> {
				pfail.init("./res/backgrounds/Pfail2.png");
			});
			pfail.setOnReleased(__e -> {
				pfail.init("./res/backgrounds/Pfail.png");
				pfailVisual.setVisible(false);
				ployVisual.setVisible(true);
				clousVisual.setVisible(true);
				settinsVisual.setVisible(true);
				settins.init("./res/backgrounds/Settins.png");
			});

		});

		getChildren().addAll(iv, ployVisual, settinsVisual, clousVisual, pfailVisual, gamePanelPane, loadingScreenVisual);
		new Thread(()->{
			while (true) {
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (frames != null) {
					long t = System.currentTimeMillis();
					if (t - last > 1000 / 30) {
						Platform.runLater(() -> {
							iv.setImage(frames[currFrame]);
						});
						currFrame++;
						if (currFrame >= frames.length) currFrame = 0;
						last = t;
					}
				}
				ploy.update();
				settins.update();
				clous.update();
				pfail.update();
				LoadingScreen.getDefaultLoadingScreen(scalingFactorHolder).update();
				if (gamePanel != null) gamePanel.update();
			}
		}).start();
		AtomicReference<Runnable>	runnable	= new AtomicReference<>();
		AtomicReference<Timeline>	arTl		= new AtomicReference<>();
		Timeline					tl			= new Timeline(
				new KeyFrame(Duration.millis(1000 / targetFPS),
						event -> {
							update();
							if ("true".equals(System.getProperty("alternateUpdate"))) {
								arTl.get().stop();
								Platform.runLater(runnable.get());
							}
						}));
		arTl.set(tl);
		tl.setCycleCount(Animation.INDEFINITE);
		Runnable r = () -> {
			update();
			if (!MainClass.isStopping() && "true".equals(System.getProperty("alternateUpdate")))
				Platform.runLater(runnable.get());
			else arTl.get().play();
		};
		runnable.set(r);

		if ("false".equals(System.getProperty("alternateUpdate"))) tl.play();
		else Platform.runLater(r);

	}

	/**
	 * Scale F 11.
	 */
	public void scaleF11() {
		frames = ImgUtil.getScaledImages(scalingFactorHolder, "./res/backgrounds/Main BG.gif");
		LoadingScreen.getDefaultLoadingScreen(scalingFactorHolder).scaleF11();
		iv.setImage(frames[0]);
	}

	/**
	 * Update.
	 */
	public void update() {
		ployVisual.update();
		settinsVisual.update();
		pfailVisual.update();
		clousVisual.update();
		loadingScreenVisual.update();
		if (gamePanelVisual != null) gamePanelVisual.update();
	}

}
