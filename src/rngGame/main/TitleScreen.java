package rngGame.main;

import java.util.concurrent.atomic.AtomicReference;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.scene.image.*;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import rngGame.ui.*;
import rngGame.ui.GamePanel;

// TODO: Auto-generated Javadoc
/**
 * The Class TitleScreen.
 */
public class TitleScreen extends Pane {

	/** The iv. */
	private final ImageView background;

	/** The scaling factor holder. */
	private final WindowDataHolder windowDataHolder;

	private final AnimatedImage storyView;

	private final rngGame.visual.AnimatedImage storyViewVisual;

	/** The last. */
	private long last = 0l;

	/** The frames. */
	private Image[] frames;

	/** The curr frame. */
	private int currFrame = 0;

	private int index = 0;

	/** The pfail. */
	private Button ploy = null;

	private Button settins = null;

	private Button clous = null;

	private Button pfail = null;

	/** The game panel pane. */
	private final Pane gamePanelPane;

	/** The target FPS. */
	private final double targetFPS = 60;

	/** The game panel. */
	private GamePanel gamePanel;

	/** The game panel visual. */
	private rngGame.visual.GamePanel gamePanelVisual;

	/** The pfail visual. */
	private rngGame.visual.Button ployVisual = null;

	private rngGame.visual.Button settinsVisual = null;

	private rngGame.visual.Button clousVisual = null;

	private rngGame.visual.Button pfailVisual = null;

	/** The loading screen visual. */
	private final rngGame.visual.LoadingScreen loadingScreenVisual;

	/**
	 * Instantiates a new title screen.
	 *
	 * @param windowDataHolder the window data holder
	 */
	public TitleScreen(WindowDataHolder windowDataHolder) {
		background = new ImageView();

		gamePanelPane = new Pane();

		this.windowDataHolder = windowDataHolder;

		loadingScreenVisual = new rngGame.visual.LoadingScreen(LoadingScreen.getDefaultLoadingScreen(windowDataHolder));
		loadingScreenVisual.setDisable(true);
		loadingScreenVisual.setOpacity(0);

		clous = new Button("./res/backgrounds/Clous.png", windowDataHolder);
		storyView	= new AnimatedImage("./res/story/Story0.gif", windowDataHolder, 7);

		storyViewVisual = new rngGame.visual.AnimatedImage(storyView);

		storyViewVisual.setOnMouseReleased(me -> {
			if(index < 6) {
				index++;
				storyView.init("./res/story/Story"+ index +".gif");
			} else storyViewVisual.setVisible(false);
		});

		clous.setOnPressed(e -> clous.init("./res/backgrounds/Clous2.png"));
		clous.setOnReleased(e -> {
			clous.init("./res/backgrounds/Clous.png");
			System.exit(0);
		});
		clousVisual = new rngGame.visual.Button(clous);

		ploy = new Button("./res/backgrounds/Ploy.png", windowDataHolder);
		ploy.setOnPressed(e -> ploy.init("./res/backgrounds/Ploy2.png"));
		ploy.setOnReleased(e -> {

			loadingScreenVisual.goIntoLoadingScreen();

			gamePanelPane.getChildren().clear();

			new Thread(() -> {

				try {
					Thread.sleep(500);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}

				gamePanel = new GamePanel(windowDataHolder);

				Platform.runLater(() -> gamePanelPane.getChildren().add(gamePanelVisual = new rngGame.visual.GamePanel(gamePanel, windowDataHolder)));

				while (gamePanelVisual == null) try {
					Thread.sleep(1);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}

				Input.getInstance(windowDataHolder).setGamePanel(gamePanel); // pass instance of GamePanel to the Instance of Input

				ploy.init("./res/backgrounds/Ploy.png");
				background.setVisible(false);
				ployVisual.setVisible(false);
				settinsVisual.setVisible(false);
				clousVisual.setVisible(false);

				gamePanel.setMap("./res/maps/lavaMap2.json");

				loadingScreenVisual.goOutOfLoadingScreen();
			}).start();

		});
		ployVisual = new rngGame.visual.Button(ploy);

		settins			= new Button("./res/backgrounds/Settins.png", windowDataHolder);
		pfail			= new Button("./res/backgrounds/Pfail.png", windowDataHolder);
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

		getChildren().addAll(background, ployVisual, settinsVisual, clousVisual, pfailVisual, gamePanelPane, loadingScreenVisual, storyViewVisual);

		new Thread(()->{
			while (true) {
				try {
					storyView.update();
					Thread.sleep(20);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (frames != null) {
					long t = System.currentTimeMillis();
					if (t - last > 1000 / 30) {
						Platform.runLater(() -> {
							background.setImage(frames[currFrame]);
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
				LoadingScreen.getDefaultLoadingScreen(windowDataHolder).update();
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
		frames = ImgUtil.getScaledImages(windowDataHolder, "./res/backgrounds/Main BG.gif");
		LoadingScreen.getDefaultLoadingScreen(windowDataHolder).scaleF11();
		background.setImage(frames[0]);
	}

	/**
	 * Update.
	 */
	public void update() {
		storyViewVisual.update();
		ployVisual.update();
		settinsVisual.update();
		pfailVisual.update();
		clousVisual.update();
		loadingScreenVisual.update();
		if (gamePanelVisual != null) gamePanelVisual.update();
	}

}
