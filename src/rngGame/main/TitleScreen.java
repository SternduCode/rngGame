package rngGame.main;

import java.io.FileNotFoundException;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.scene.image.*;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import rngGame.tile.ImgUtil;
import rngGame.ui.Button;
import rngGame.ui.SoundHandler;
import rngGame.visual.AnimatedImage;

/**
 * The Class TitleScreen.
 */
public class TitleScreen extends Pane{

	private boolean destroy;

	/** The iv. */
	private final ImageView iv;
	
	private AnimatedImage storyView;

	/** The last. */
	private long last = 0l;

	/** The frames. */
	private Image[] frames;

	/** The curr frame. */
	private int currFrame = 0;
	
	private int index = 0;

	/** The pfail. */
	private Button ploy = null, settins = null, clous = null, pfail = null;

	/**
	 * Instantiates a new title screen.
	 */
	public TitleScreen() {
		iv = new ImageView();

		WindowManager.getInstance().startLogicThread();

		storyView = new AnimatedImage("./res/story/Story0.gif", 7);
		
		storyView.setOnMouseReleased(me -> {
			SoundHandler.getInstance().makeSound("click.wav");
			if(index < 6) {
			index++;
			storyView.init("./res/story/Story"+ index +".gif");
			} else {
				storyView.setVisible(false);
			}
		});
		
		clous = new Button("./res/backgrounds/Clous.png");
		clous.setOnPressed(e -> clous.init("./res/backgrounds/Clous2.png"));
		clous.setOnReleased(e -> {
			clous.init("./res/backgrounds/Clous.png");
			System.exit(0);
		});

		ploy = new Button("./res/backgrounds/Ploy.png");
		ploy.setOnMousePressed(e -> ploy.init("./res/backgrounds/Ploy2.png"));
		ploy.setOnMouseReleased(e -> {
			SoundHandler.getInstance().makeSound("click.wav");
			LoadingScreen.INSTANCE.goIntoLoadingScreen();
			GamePanel gp = null;
			try {
				gp = new GamePanel();
				Input.getInstance().setGamePanel(gp.getVgp()); // pass instance of GamePanel to the Instance of Input
				WindowManager.getInstance().setGamePanel(gp.getVgp()); // pass instance of GamePanel to WindowManager
				getChildren().clear();
				getChildren().addAll(gp.getVgp(), LoadingScreen.INSTANCE);
				gp.getVgp().setBlockUserInputs(false);
			} catch (FileNotFoundException ex) {
				ex.printStackTrace();
			}
			destroy();
			Input.getInstance().setBlockInputs(false);

			new Thread(() -> {
				try {
					Thread.sleep(200);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}

				Platform.runLater(() -> {
					if (ploy != null) {
						ploy.init("./res/backgrounds/Ploy.png");
						ploy.setVisible(false);
					}
					iv.setVisible(false);
					if (settins!= null) {
						settins.setVisible(false);
					}
					if (clous!= null) {
						clous.setVisible(false);
					}
				});

				try {
					Thread.sleep(2000);
					LoadingScreen.INSTANCE.goOutOfLoadingScreen();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}).start();

		});

		settins	= new Button("./res/backgrounds/Settins.png");
		pfail	= new Button("./res/backgrounds/Pfail.png");
		pfail.setVisible(false);
		settins.setOnPressed(e -> {
			settins.init("./res/backgrounds/Settins2.png");
		});
		settins.setOnReleased(e -> {
			settins.init("./res/backgrounds/Settins.png");
			ploy.setVisible(false);
			clous.setVisible(false);
			settins.setVisible(false);
			pfail.setVisible(true);
			pfail.setOnPressed(_e -> {
				pfail.init("./res/backgrounds/Pfail2.png");
			});
			pfail.setOnReleased(__e -> {
				pfail.init("./res/backgrounds/Pfail.png");
				pfail.setVisible(false);
				ploy.setVisible(true);
				clous.setVisible(true);
				settins.setVisible(true);
				settins.init("./res/backgrounds/Settins.png");
			});

		});

		getChildren().addAll(iv, ploy, settins, clous, pfail, storyView, LoadingScreen.INSTANCE);
		new Thread(()->{
			while (!destroy) {
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
							if (frames != null) {
								iv.setImage(frames[currFrame]);
							}
						});
						currFrame++;
						if (currFrame >= frames.length) currFrame = 0;
						last = t;
					}
				}
			}
		}).start();
	}

	private void destroy() {

        destroy = true;
		frames = null;
		iv.setImage(null);
		WindowManager.getInstance().removeAnimatedImage(storyView);
		storyView = null;
		ploy = null;
		settins = null;
		clous = null;
		pfail = null;

	}

	/**
	 * Scale F 11.
	 */
	public void scaleF11() {
		frames = ImgUtil.getScaledImages("./res/backgrounds/Main BG.gif", WindowManager.getInstance().getBlockSize()*WindowManager.getInstance().getxBlocks(), WindowManager.getInstance().getBlockSize()*WindowManager.getInstance().getyBlocks());
		iv.setImage(frames[0]);
		Input.getInstance().setBlockInputs(true);
	}

}
