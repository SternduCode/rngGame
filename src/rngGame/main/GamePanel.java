package rngGame.main;

import java.io.*;
import java.util.*;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicReference;

import com.sterndu.json.*;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.*;
import javafx.scene.control.Label;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import rngGame.buildings.*;
import rngGame.entity.*;
import rngGame.tile.*;
import rngGame.tile.SelectTool;
import rngGame.tile.TileManager;
import rngGame.ui.*;
import rngGame.ui.ActionButton;
import rngGame.ui.TabMenu;
import rngGame.visual.AnimatedImage;
import rngGame.visual.Button;

// TODO: Auto-generated Javadoc
/**
 * The Class GamePanel.
 */
public class GamePanel {

	/**
	 * The Class GP.
	 */
	private class GP {

		/** The frame times. */
		private List<Long> frameTimes;

		/** The last frame. */
		private Long lastFrame;

		/** The target FPS. */
		private final int targetFPS = 80;

		/** The fps. */
		private Double fps = 0d;

		/** The block user inputs. */
		private boolean blockUserInputs;

		/** The player. */
		private final Player player;

		/** The lgp. */
		private final rngGame.main.GamePanel logic;

		/** The animated images. */
		private final List<AnimatedImage> animatedImages;

		/**
		 * Instantiates a new game panel.
		 *
		 * @param logic the logic
		 * @throws FileNotFoundException the file not found exception
		 */
		public GamePanel(rngGame.main.GamePanel logic)
				throws FileNotFoundException {
			setPrefSize(gameWidth, gameHeight);

			animatedImages = new ArrayList<>();

			b = new Button(this);

			b.init("./res/fight/Leaf.gif", 10);

			b.setOnPressed(e -> b.init("./res/fight/Stych2.png"));

			b.setOnReleased(e -> b.init("./res/fight/Leaf.gif", 10));

			lgp.setVgp(this);

			this.logic = logic;

			bubbleText = new Pane();

			overlay = new ImageView();

			getOverlay().setDisable(true);

			loadingScreen = new ImageView(new Image(new FileInputStream(new File("./res/gui/Loadingscreen.gif"))));
			getLoadingScreen().setDisable(true);

			fpsLabel = new Label(fps + "");
			fpsLabel.setBackground(new Background(new BackgroundFill(Color.color(.5, .5, .5, 1), null, null)));
			fpsLabel.setTextFill(Color.color(.1, .1, .1));
			fpsLabel.setOpacity(.6);
			fpsLabel.setDisable(true);
			fpsLabel.setVisible(false);

			pointGroup = new Group();
			getPointGroup().setDisable(true);

			layerGroup = new GroupGroup();
			getLayerGroup().getChildren().add(new Group());

			selectTool = new SelectTool(this);

			tileManager = new TileManager(this);
			tileManager.setPrefSize(gameWidth, gameHeight);

			player = new Player(this, tileManager.getCM(), tileManager.getRequestorN());

			aktionbutton = new ActionButton(this);

			lgp.setMap("./res/maps/lavaMap2.json");
			gamemenu = new TabMenu(getLgp());

			getChildren().addAll(tileManager, getLayerGroup(), getOverlay(), getPointGroup(), selectTool, aktionbutton, lgp.getBubble(), bubbleText,
					gamemenu, fpsLabel,
					getLoadingScreen());
		}

		/**
		 * Adds the animated image.
		 *
		 * @param animatedImage the animated image
		 */
		public void addAnimatedImage(AnimatedImage animatedImage) {
			animatedImages.add(animatedImage);
		}

		/**
		 * Scale textures.
		 *
		 * @param scaleFactorX the scale factor X
		 * @param scaleFactorY the scale factor Y
		 */
		public void changeScalingFactor(double scaleFactorX, double scaleFactorY) {
			player.setPosition(player.getX() * (scaleFactorX / scalingFactorX),
					player.getY() * (scaleFactorY / scalingFactorY));
			reload();
			player.getPlayerImage();
			player.generateCollisionBox();
			System.out.println(player.getX() + " " + player.getY());
		}

		/**
		 * Gets the aktionbutton.
		 *
		 * @return the aktionbutton
		 */
		public ActionButton getAktionbutton() { return aktionbutton; }

		/**
		 * Gets the block size.
		 *
		 * @return the block size
		 */
		public int getBlockSize() { return blockSize; }

		/**
		 * Gets the block size X.
		 *
		 * @return the block size X
		 */
		public int getBlockSizeX() { return blockSizeX; }

		/**
		 * Gets the block size Y.
		 *
		 * @return the block size Y
		 */
		public int getBlockSizeY() { return blockSizeY; }

		/**
		 * Gets the bubble text.
		 *
		 * @return the bubble text
		 */
		public Pane getBubbleText() {
			return bubbleText;
		}

		/**
		 * Gets the fps.
		 *
		 * @return the fps
		 */
		public Double getFps() { return fps; }

		/**
		 * Gets the game height.
		 *
		 * @return the game height
		 */
		public int getGameHeight() { return gameHeight; }

		/**
		 * Gets the gamemenu.
		 *
		 * @return the gamemenu
		 */
		public TabMenu getGamemenu() { return gamemenu; }

		/**
		 * Gets the game width.
		 *
		 * @return the game width
		 */
		public int getGameWidth() { return gameWidth; }

		/**
		 * Gets the layer group.
		 *
		 * @return the layer group
		 */
		public GroupGroup getLayerGroup() { return layerGroup; }

		/**
		 * Gets the lgp.
		 *
		 * @return the lgp
		 */
		public rngGame.main.GamePanel getLgp() { return lgp; }

		/**
		 * Gets the loading screen.
		 *
		 * @return the loading screen
		 */
		public ImageView getLoadingScreen() { return loadingScreen; }

		/**
		 * Gets the overlay.
		 *
		 * @return the overlay
		 */
		public ImageView getOverlay() { return overlay; }

		/**
		 * Gets the player.
		 *
		 * @return the player
		 */
		public Player getPlayer() { return player; }

		/**
		 * Gets the point group.
		 *
		 * @return the point group
		 */
		public Group getPointGroup() { return pointGroup; }

		/**
		 * Gets the scaling factor X.
		 *
		 * @return the scaling factor X
		 */
		public double getScalingFactorX() { return scalingFactorX; }

		/**
		 * Gets the scaling factor Y.
		 *
		 * @return the scaling factor Y
		 */
		public double getScalingFactorY() { return scalingFactorY; }

		/**
		 * Gets the select tool.
		 *
		 * @return the select tool
		 */
		public SelectTool getSelectTool() { return selectTool; }

		/**
		 * Gets the target FPS.
		 *
		 * @return the target FPS
		 */
		public int getTargetFPS() { return targetFPS; }

		/**
		 * Gets the tile M.
		 *
		 * @return the tile M
		 */
		public TileManager getTileManager() { return tileManager; }

		/**
		 * Gets the view groups.
		 *
		 * @return the view groups
		 */
		public List<Group> getViewGroups() { return getLayerGroup().getGroupChildren(); }

		/**
		 * Gets the x blocks.
		 *
		 * @return the x blocks
		 */
		public int getxBlocks() { return xBlocks; }

		/**
		 * Gets the y blocks.
		 *
		 * @return the y blocks
		 */
		public int getyBlocks() { return yBlocks; }

		/**
		 * Reload.
		 */
		public void reload() {
			getLayerGroup().getChildren().stream().map(n -> ((Group) n).getChildren()).forEach(ObservableList::clear);
			getPointGroup().getChildren().clear();

			if (!"".equals(getTileManager().getOverlay()))
				getOverlay().setImage(ImgUtil.getScaledImage(this, "./res/gui/" + getTileManager().getOverlay()));
			else getOverlay().setImage(null);

			tileManager.reload();
			tileManager.update();
			aktionbutton.f11Scale();
			if (tileManager.getBackgroundPath() != null) try {
				setBackground(new Background(
						new BackgroundImage(new Image(new FileInputStream("./res/" + tileManager.getBackgroundPath())),
								BackgroundRepeat.NO_REPEAT,
								BackgroundRepeat.NO_REPEAT, null,
								new BackgroundSize(getGameWidth(), getGameHeight() + getScalingFactorY(), false, false, false, false))));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			else setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));

			player.setLayer(tileManager.getPlayerLayer());

			getLgp().setBuildings(tileManager.getBuildingsFromMap());
			getLgp().setNpcs(tileManager.getNPCSFromMap());
			getLgp().setTest(tileManager.getMobsFromMap());

			Circle spawn = new Circle(getTileManager().getSpawnPoint().getX() * getScalingFactorX(),
					getTileManager().getSpawnPoint().getY() * getScalingFactorY(), 15,
					Color.color(0, 1, 0, .75));
			lgp.getPoints().put(tileManager.getSpawnPoint(), spawn);
			getPointGroup().getChildren().add(spawn);

			getLgp().getBuildings().forEach(b -> {
				if (b instanceof House h) {
					String map = h.getMap();
					try {
						JsonObject jo = (JsonObject) JsonParser.parse(new File("./res/maps/" + map));
						if (jo.containsKey("exit")) {
							JsonObject exit = (JsonObject) jo.get("exit");
							if (getTileManager().getPath().endsWith( ((StringValue) exit.get("map")).getValue())) {
								JsonArray	spawnPosition	= (JsonArray) exit.get("spawnPosition");
								Point2D		p				= new Point2D( ((NumberValue) spawnPosition.get(0)).getValue().longValue(),
										((NumberValue) spawnPosition.get(1)).getValue().longValue());
								Circle		respawn			= new Circle(p.getX() * getScalingFactorX(), p.getY() * getScalingFactorY(),
										15,
										Color.color(0, 1, 0, .75));
								lgp.getPoints().put(p, respawn);
								getPointGroup().getChildren().add(respawn);
							}
						}
					} catch (JsonParseException e) {
						e.printStackTrace();
					}
				}
			});

			gamemenu.f11Scale();
			if (gamemenu != null && gamemenu.getInventory().getCurrentDemon() != null && gamemenu.getInventory().getCurrentDemon().getDemon() != null)
				getLgp().getNpcs().add(gamemenu.getInventory().getCurrentDemon().getDemon());

		}

		/**
		 * Sets the block user inputs.
		 *
		 * @param blockUserInputs the new block user inputs
		 */
		public void setBlockUserInputs(boolean blockUserInputs) { this.blockUserInputs = blockUserInputs; }

		/**
		 * Sets the map.
		 *
		 * @param path the path
		 * @param position the position
		 */
		public void setMap(String path, double[] position) {

			getLayerGroup().getChildren().stream().map(n -> ((Group) n).getChildren()).forEach(ObservableList::clear);
			getPointGroup().getChildren().clear();

			tileManager.setMap(path);
			if (tileManager.getBackgroundPath() != null) try {
				setBackground(new Background(
						new BackgroundImage(new Image(new FileInputStream("./res/" + tileManager.getBackgroundPath())),
								BackgroundRepeat.NO_REPEAT,
								BackgroundRepeat.NO_REPEAT, null,
								new BackgroundSize(getGameWidth(), getGameHeight(), false, false, false, false))));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			else setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));

			if (!"".equals(tileManager.getOverlay())) getOverlay().setImage(ImgUtil.getScaledImage(this, "./res/gui/" + tileManager.getOverlay()));
			else getOverlay().setImage(null);

			lgp.setBuildings(tileManager.getBuildingsFromMap());
			lgp.setNpcs(tileManager.getNPCSFromMap());
			lgp.setTest(tileManager.getMobsFromMap());
			Circle spawn = new Circle(tileManager.getSpawnPoint().getX() * getScalingFactorX(),
					tileManager.getSpawnPoint().getY() * getScalingFactorY(), 15,
					Color.color(0, 1, 0, .75));
			lgp.getPoints().put(tileManager.getSpawnPoint(), spawn);
			getPointGroup().getChildren().add(spawn);
			lgp.getBuildings().forEach(b -> {
				if (b instanceof House h) {
					String map = h.getMap();
					try {
						JsonObject jo = (JsonObject) JsonParser.parse(new File("./res/maps/" + map));
						if (jo.containsKey("exit")) {
							JsonObject exit = (JsonObject) jo.get("exit");
							if (tileManager.getPath().endsWith( ((StringValue) exit.get("map")).getValue())) {
								JsonArray	spawnPosition	= (JsonArray) exit.get("spawnPosition");
								Point2D		p				= new Point2D(
										((NumberValue) spawnPosition.get(0)).getValue().longValue(),
										((NumberValue) spawnPosition.get(1)).getValue().longValue());
								Circle		respawn			= new Circle(p.getX(), p.getY(), 15,
										Color.color(0, 1, 0, .75));
								lgp.getPoints().put(p, respawn);
								getPointGroup().getChildren().add(respawn);
							}
						}
					} catch (JsonParseException e) {
						e.printStackTrace();
					}
				}
			});

			if (position != null)
				player.setPosition(new double[] {
						position[0] * getScalingFactorX(), position[1] * getScalingFactorY()
				});
			else {
				double[] posi = tileManager.getStartingPosition();
				player.setPosition(new double[] {
						posi[0] * getScalingFactorX(), posi[1] * getScalingFactorY()
				});
			}
			player.setLayer(tileManager.getPlayerLayer());

			if (gamemenu != null && gamemenu.getInventory().getCurrentDemon() != null && gamemenu.getInventory().getCurrentDemon().getDemon() != null)
				getLgp().getNpcs().add(gamemenu.getInventory().getCurrentDemon().getDemon());
		}

		/**
		 * Start logic thread.
		 */
		public void startLogicThread() {

			frameTimes	= new ArrayList<>();
			lastFrame	= System.currentTimeMillis();



		}

		/**
		 * Update.
		 */
		public void update() {

			long lastFrameTime = frameTimes.size() > 0 ? frameTimes.get(frameTimes.size() - 1) : 0;

			fpsLabel.setText(String.format("%.2f", 1000 / fps));
			fpsLabel.setLayoutX(gameWidth - fpsLabel.getWidth());

			try {
				player.update(lastFrameTime);
			} catch (ConcurrentModificationException e) {}

			selectTool.update();

			aktionbutton.update();

			for (Entry<Point2D, Circle> point : lgp.getPoints().entrySet()) {
				point.getValue().setCenterX(point.getKey().getX() * getScalingFactorX() - player.getX() + player.getScreenX());
				point.getValue().setCenterY(point.getKey().getY() * getScalingFactorY() - player.getY() + player.getScreenY());
			}

			tileManager.update();

			getLgp().update();

			long frameTime = System.currentTimeMillis() - lastFrame;
			lastFrame = System.currentTimeMillis();
			frameTimes.add(frameTime);
			fps = frameTimes.stream().mapToLong(l -> l).average().getAsDouble();
			while (frameTimes.size() > Math.pow(fps * 12, 1.2)) frameTimes.remove(0);

			Text.getInstance().update(lastFrameTime);

			animatedImages.forEach(AnimatedImage::update);

		}
	}

	/** The difficulty. */
	private Difficulty difficulty;

	/** The input. */
	private final Input input;

	/** The buildings. */
	private List<Building> buildings;

	/** The npcs. */
	private List<NPC> npcs;

	/** The test. */
	private List<MobRan> test;

	/** The fps. */
	private Double fps = 0d;

	/** The points. */
	private final Map<Point2D, Circle> points;

	/** The fps. */
	private final int FPS = 80;

	/** The bubble. */
	private final Pane bubble;

	/** The mp. */
	private MediaPlayer mp;

	/** The sgp. */
	private rngGame.visual.GamePanel vgp;

	/** The frame times. */
	private List<Long> frameTimes;

	/** The last frame. */
	private Long lastFrame;

	/** The clipboard. */
	private List<List<TextureHolder>> clipboard;

	/** The fps label visible. */
	private boolean fpsLabelVisible;

	/** The scaling factor holder. */
	private final WindowDataHolder windowDataHolder;

	/**
	 * Instantiates a new game panel.
	 *
	 * @param windowDataHolder the window data holder
	 * @throws FileNotFoundException the file not found exception
	 */
	public GamePanel(WindowDataHolder windowDataHolder) throws FileNotFoundException {

		this.windowDataHolder = windowDataHolder;

		fpsLabelVisible = false;

		frameTimes	= new ArrayList<>();
		lastFrame	= System.currentTimeMillis();

		bubble = new Pane();

		difficulty = Difficulty.EASY;

		input = Input.getInstance(windowDataHolder);

		points = new HashMap<>();

		clipboard = new ArrayList<>();
	}

	/**
	 * Convert layout point to world point.
	 *
	 * @param layoutPoint the layout point
	 *
	 * @return the world point
	 */
	public Point2D convertLayoutPointToWorldPoint(Point2D layoutPoint) {

	}

	/**
	 * Convert world point to layout point.
	 *
	 * @param worldPoint the world point
	 *
	 * @return the layout point
	 */
	public Point2D convertWorldPointToLayoutPoint(Point2D worldPoint) {

	}

	/**
	 * Gets the bubble.
	 *
	 * @return the bubble
	 */
	public Pane getBubble() { return bubble; }

	/**
	 * Gets the buildings.
	 *
	 * @return the buildings
	 */
	public List<Building> getBuildings() { return buildings; }

	/**
	 * Gets the clipboard.
	 *
	 * @return the clipboard
	 */
	public List<List<TextureHolder>> getClipboard() { return clipboard; }

	/**
	 * Gets the difficulty.
	 *
	 * @return the difficulty
	 */
	public Difficulty getDifficulty() {
		return difficulty;
	}

	/**
	 * Gets the fps.
	 *
	 * @return the fps
	 */
	public Double getFps() { return fps; }

	/**
	 * Gets the mob rans.
	 *
	 * @return the mob rans
	 */
	public List<MobRan> getMobRans() { return test; }

	/**
	 * Gets the npcs.
	 *
	 * @return the npcs
	 */
	public List<NPC> getNpcs() { return npcs; }

	/**
	 * Gets the points.
	 *
	 * @return the points
	 */
	public Map<Point2D, Circle> getPoints() { return points; }

	/**
	 * Gets the vgp.
	 *
	 * @return the vgp
	 */
	public rngGame.visual.GamePanel getVgp() { return vgp; }

	/**
	 * Checks if is block user inputs.
	 *
	 * @return true, if is block user inputs
	 */
	public boolean isBlockUserInputs() {
		return isInLoadingScreen() || blockUserInputs;
	}

	/**
	 * Checks if is in loading screen.
	 *
	 * @return true, if is in loading screen
	 */
	public boolean isInLoadingScreen() { return getLoadingScreen().getOpacity() > .5; }

	/**
	 * Reload.
	 */
	public void reload() {
		getPoints().clear();
	}

	/**
	 * Save map.
	 */
	public void saveMap() {
		getTileManager().save();
		System.out.println("don");
	}

	/**
	 * Sets the buildings.
	 *
	 * @param buildings the new buildings
	 */
	public void setBuildings(List<Building> buildings) { this.buildings = buildings; }

	/**
	 * Sets the clipboard.
	 *
	 * @param clipboard the new clipboard
	 */
	public void setClipboard(List<List<TextureHolder>> clipboard) {
		this.clipboard = clipboard;
	}

	/**
	 * Sets the difficulty.
	 *
	 * @param difficulty the new difficulty
	 */
	public void setDifficulty(Difficulty difficulty) {
		this.difficulty = difficulty;
	}

	/**
	 * Sets the map.
	 *
	 * @param path the new map
	 */
	public void setMap(String path) { setMap(path, null); }

	/**
	 * Sets the map.
	 *
	 * @param path     the path
	 * @param position the position
	 */
	public void setMap(String path, double[] position) {

		getVgp().goIntoLoadingScreen();

		UndoRedo.getInstance().clearActions();

		SoundHandler.getInstance().endBackgroundMusic();

		getPoints().clear();

		vgp.setMap(path, position);

		if (!"".equals(vgp.getTileManager().getBackgroundMusic()))
			SoundHandler.getInstance().setBackgroundMusic(vgp.getTileManager().getBackgroundMusic());
		else SoundHandler.getInstance().endBackgroundMusic();

		FadeTransition ft = new FadeTransition(Duration.millis(500), getVgp().getLoadingScreen());
		ft.setFromValue(1);
		ft.setToValue(0);
		ft.play();

	}

	/**
	 * Sets the npcs.
	 *
	 * @param npcs the new npcs
	 */
	public void setNpcs(List<NPC> npcs) { this.npcs = npcs; }

	/**
	 * Sets the test.
	 *
	 * @param test the new test
	 */
	public void setTest(List<MobRan> test) { this.test = test; }

	/**
	 * Sets the vgp.
	 *
	 * @param vgp the new vgp
	 */
	public void setVgp(rngGame.visual.GamePanel vgp) { this.vgp = vgp; }

	/**
	 * Sst.
	 */
	public void SST() {

		frameTimes	= new ArrayList<>();
		lastFrame	= System.currentTimeMillis();

		AtomicReference<Runnable>	runnable	= new AtomicReference<>();
		AtomicReference<Timeline>	arTl		= new AtomicReference<>();
		Timeline					tl			= new Timeline(
				new KeyFrame(Duration.millis(1000 / FPS),
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
	 * Toggle fps label visible.
	 */
	public void toggleFpsLabelVisible() {
		fpsLabelVisible = !fpsLabelVisible;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "SpielPanel ["
				+ ", input=" + input + ", buildings=" + buildings
				+ ", npcs=" + npcs
				+ "]";
	}

	/**
	 * Update.
	 */
	public void update() {

		long lastFrameTime = frameTimes.size() > 0 ? frameTimes.get(frameTimes.size() - 1) : 0;

		input.update(lastFrameTime);

		try {
			for (Building b : buildings) b.update(lastFrameTime);
			for (Entity n : npcs) n.update(lastFrameTime);
			for (Entity n : test) n.update(lastFrameTime);
		} catch (ConcurrentModificationException e) {}

		for (Node layer : getVgp().getLayerGroup().getChildren()) {
			Group		view	= (Group) layer;
			List<Node>	nodes	= new ArrayList<>(view.getChildren());

			nodes.sort((n1, n2) -> {
				if (n1 instanceof GameObject b1) if (n2 instanceof GameObject b2)
					return b1.isBackground() ^ b2.isBackground() ? b1.isBackground() ? -1 : 1
							: Double.compare(n1.getLayoutY() + ((GameObject) n1).getTextureHeight(),
									n2.getLayoutY() + ((GameObject) n2).getTextureHeight());
				else return b1.isBackground() ? -1
						: Double.compare(n1.getLayoutY() + ((GameObject) n1).getTextureHeight(),
								n2.getLayoutY() + ((GameObject) n2).getTextureHeight());
				if (n2 instanceof GameObject b21) return b21.isBackground() ? 1
						: Double.compare(n1.getLayoutY() + ((GameObject) n1).getTextureHeight(),
								n2.getLayoutY() + ((GameObject) n2).getTextureHeight());
				return Double.compare(n1.getLayoutY() + ((GameObject) n1).getTextureHeight(),
						n2.getLayoutY() + ((GameObject) n2).getTextureHeight());
			});

			view.getChildren().clear();
			view.getChildren().addAll(nodes);
		}

		if ("true".equals(System.getProperty("edit"))) getVgp().getPointGroup().setVisible(true);
		else getVgp().getPointGroup().setVisible(false);

		long frameTime = System.currentTimeMillis() - lastFrame;
		lastFrame = System.currentTimeMillis();
		frameTimes.add(frameTime);
		fps = frameTimes.stream().mapToLong(l -> l).average().getAsDouble();
		while (frameTimes.size() > Math.pow(fps * 12, 1.2)) frameTimes.remove(0);

	}

}
