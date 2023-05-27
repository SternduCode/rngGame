package rngGame.main;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.*;
import java.nio.file.Path;
import java.util.*;
import java.util.Map.Entry;
import java.util.function.*;

import com.sterndu.json.*;

import javafx.beans.property.ObjectProperty;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.*;
import javafx.stage.FileChooser.ExtensionFilter;
import rngGame.entity.Player;
import rngGame.ui.*;
import rngGame.ui.GamePanel;

// TODO: Auto-generated Javadoc
/**
 * The Class GameObject.
 */
public class GameObject implements JsonValue, Collidable {

	/** The fps. */
	protected double x = 0d, y = 0d, fps = 7.5;

	/** The layout Y. */
	private double layoutX, layoutY, width, height;

	/** The collision boxes. */
	protected Map<String, Polygon> collisionBoxes;

	/** The visible. */
	private boolean visible;

	/** The misc boxes. */
	private final Map<String, Shape> miscBoxes;

	/** The misc box handler. */
	private final Map<String, BiConsumer<GamePanel, GameObject>> miscBoxHandler;

	/** The remove callbacks. */
	protected List<Runnable> removeCallbacks;

	/** The update req dim. */
	private Runnable updateXY, updateReqDim;

	/** The images M. */
	private final Menu menu, imagesM;

	/** The current key. */
	private String currentKey;

	/** The image. */
	private AnimatedImage image;

	/** The directory. */
	protected String directory;

	/** The layer. */
	protected int layer;

	/** The extra data. */
	protected JsonObject extraData;

	/** The slave. */
	protected boolean slave = false;

	/** The slaves. */
	protected List<GameObject> slaves;

	/** The master. */
	protected GameObject master;

	/** The texture files. */
	protected Map<String, String> textureFiles;

	/** The orig height. */
	protected int reqWidth, reqHeight, origWidth, origHeight;

	/** The gamepanel. */
	protected GamePanel gamepanel;

	/** The collision box view group. */
	protected Group collisionBoxViewGroup;

	/** The animation counter. */
	protected long animationCounter = 0;

	/** The animation num. */
	protected int animationNum = 0;

	/** The last key. */
	private String lastKey;

	/** The background. */
	protected boolean background;

	/** The fix to screen. */
	protected boolean fixToScreen;

	/** The flip textures. */
	protected boolean flipTextures = false;

	/** The remove. */
	private final MenuItem position, fpsI, currentKeyI, directoryI, origDim, reqDim, backgroundI, layerI,
	reloadTextures,
	removeMI;

	/** The remove. */
	private boolean remove;

	/** The border. */
	private Border border;

	/** The requestor. */
	private ObjectProperty<? extends GameObject> requestor;

	/**
	 * Instantiates a new game object.
	 *
	 * @param gameObject the game object
	 * @param gameObjects the game objects
	 * @param requestor the requestor
	 * @param windowDataHolder the window data holder
	 */
	@SuppressWarnings("unchecked")
	public GameObject(GameObject gameObject, List<? extends GameObject> gameObjects,
			ObjectProperty<? extends GameObject> requestor, WindowDataHolder windowDataHolder) {
		visible			= true;
		remove			= false;
		this.requestor	= requestor;
		removeCallbacks = new ArrayList<>();
		fixToScreen = false;
		textureFiles = new HashMap<>();
		collisionBoxes = new HashMap<>();
		miscBoxes = new HashMap<>();
		miscBoxHandler = new HashMap<>();
		currentKey = "default";
		lastKey = currentKey;
		collisionBoxes.put(currentKey, new Polygon());
		collisionBoxViewGroup = new Group(collisionBoxes.get(currentKey));

		image = new AnimatedImage(windowDataHolder);

		x = gameObject.x;
		y = gameObject.y;
		origWidth = gameObject.origWidth;
		origHeight = gameObject.origHeight;
		reqWidth = gameObject.reqWidth;
		reqHeight = gameObject.reqHeight;
		fps = gameObject.fps;
		layer = gameObject.layer;

		gameObject.miscBoxes.forEach((k, v) -> {
			Shape p;
			if (v instanceof Ellipse circ)
				p = new Ellipse(circ.getCenterX(), circ.getCenterY(), circ.getRadiusX(), circ.getRadiusY());
			else if (v instanceof Rectangle rect)
				p = new Rectangle(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
			else if (v instanceof Polygon poly) {
				p = new Polygon();
				((Polygon) p).getPoints().addAll(poly.getPoints());
			} else if (v instanceof Ring ring) p = new Ring(ring.getX(), ring.getY(), ring.getRadiusX(),
					ring.getRadiusY(), ring.getInnerRadiusX(), ring.getInnerRadiusY());
			else return;
			p.setStroke(Color.color(1, 1, 0, .75));
			p.setFill(Color.TRANSPARENT);
			p.setStrokeWidth(2.5);
			miscBoxes.put(k, p);
		});
		gameObject.miscBoxHandler.forEach((k, v) -> {
			miscBoxHandler.put(k, v);
		});

		gamepanel = gameObject.gamepanel;
		directory = gameObject.directory;
		background = gameObject.background;
		currentKey = gameObject.currentKey;
		textureFiles = gameObject.textureFiles;

		extraData = gameObject.extraData;

		menu = new Menu("GameObject");
		menu.setStyle("-fx-font-size: 20;");
		imagesM = new Menu("Images");
		imagesM.setStyle("-fx-font-size: 20;");
		position = new MenuItem();
		fpsI = new MenuItem();
		currentKeyI = new MenuItem();
		directoryI = new MenuItem();
		origDim = new MenuItem();
		reqDim = new MenuItem();
		backgroundI = new MenuItem();
		layerI = new MenuItem();
		reloadTextures = new MenuItem("Reload Textures");
		reloadTextures.setStyle("-fx-font-size: 20;");
		removeMI = new MenuItem("Remove");
		removeMI.setStyle("-fx-font-size: 20;");
		position.setOnAction(this::handleContextMenu);
		fpsI.setOnAction(this::handleContextMenu);
		currentKeyI.setOnAction(this::handleContextMenu);
		directoryI.setOnAction(this::handleContextMenu);
		origDim.setOnAction(this::handleContextMenu);
		reqDim.setOnAction(this::handleContextMenu);
		backgroundI.setOnAction(this::handleContextMenu);
		layerI.setOnAction(this::handleContextMenu);
		reloadTextures.setOnAction(this::handleContextMenu);
		removeMI.setOnAction(this::handleContextMenu);
		menu.getItems().addAll(position, fpsI, imagesM, currentKeyI, directoryI, origDim, reqDim, backgroundI, layerI,
				reloadTextures, removeMI);

		master = gameObject;
		slave = true;
		if (master.slaves == null) {
			Runnable[] r = new Runnable[1];
			master.removeCallbacks.add(r[0] = () -> {
				GameObject m = master.slaves.remove(0);
				m.slave = false;
				if (master.slaves.size() > 0) {
					m.slaves = master.slaves;
					for (GameObject s: m.slaves)
						s.master = m;
					m.removeCallbacks.add(r[0]);
				}
			});
			master.slaves = new ArrayList<>();
		}
		master.slaves.add(this);
		removeCallbacks.add(() -> {
			if (slave) master.slaves.remove(this);
		});
		if (gameObjects != null) {
			((List<GameObject>) gameObjects).add(this);
			removeCallbacks.add(() -> {
				gameObjects.remove(this);
			});
		}

	}

	/**
	 * Instantiates a new game object.
	 *
	 * @param gameObject the game object
	 * @param gp the gp
	 * @param directory the directory
	 * @param gameObjects the game objects
	 * @param requestor the requestor
	 * @param windowDataHolder the window data holder
	 */
	@SuppressWarnings("unchecked")
	public GameObject(JsonObject gameObject, GamePanel gp, String directory, List<? extends GameObject> gameObjects,
			ObjectProperty<? extends GameObject> requestor, WindowDataHolder windowDataHolder) {
		visible			= true;
		remove			= false;
		this.requestor	= requestor;
		gamepanel		= gp;
		this.directory	= directory;
		fixToScreen		= false;
		textureFiles = new HashMap<>();
		collisionBoxes = new HashMap<>();
		miscBoxes = new HashMap<>();
		miscBoxHandler = new HashMap<>();
		currentKey = "default";
		lastKey = currentKey;
		collisionBoxes.put(currentKey, new Polygon());
		collisionBoxViewGroup = new Group(collisionBoxes.get(currentKey));

		image = new AnimatedImage(windowDataHolder);

		removeCallbacks = new ArrayList<>();

		if (gameObject != null) {
			origWidth = ((NumberValue) ((JsonArray) gameObject.get("originalSize")).get(0)).getValue().intValue();
			origHeight = ((NumberValue) ((JsonArray) gameObject.get("originalSize")).get(1)).getValue().intValue();

			if (gameObject.containsKey("miscBoxes")) {
				JsonArray miscBoxes = (JsonArray) gameObject.get("miscBoxes");
				for (Object box: miscBoxes) {
					JsonObject joBox = (JsonObject) box;
					String name = ((StringValue) joBox.get("name")).getValue();
					String type = ((StringValue) joBox.get("type")).getValue();
					switch (type) {
						case "rectangle":
						{
							long x, y, width, height;
							x		= (long) ( ((NumberValue) joBox.get("x")).getValue().longValue() * gp.getWindowDataHolder().scalingFactorX());
							y		= (long) ( ((NumberValue) joBox.get("y")).getValue().longValue() * gp.getWindowDataHolder().scalingFactorY());
							width = (long) (((NumberValue) joBox.get("width")).getValue().longValue()
									* gp.getWindowDataHolder().scalingFactorX());
							height = (long) (((NumberValue) joBox.get("height")).getValue().longValue()
									* gp.getWindowDataHolder().scalingFactorY());
							Rectangle shape = new Rectangle(x, y, width, height);
							shape.setStroke(Color.color(1, 1, 0, .75));
							shape.setFill(Color.TRANSPARENT);
							shape.setStrokeWidth(2.5);
							this.miscBoxes.put(name, shape);
							miscBoxHandler.put(name, (o, t) -> {});
						}
						break;
						case "circle":
						{
							long x, y, radiusX, radiusY, innerRadiusX = 0, innerRadiusY = 0;
							x		= (long) ( ((NumberValue) joBox.get("x")).getValue().longValue() * gp.getWindowDataHolder().scalingFactorX());
							y		= (long) ( ((NumberValue) joBox.get("y")).getValue().longValue() * gp.getWindowDataHolder().scalingFactorY());
							radiusX = (long) (((NumberValue) joBox.get("radius")).getValue().longValue()
									* gp.getWindowDataHolder().scalingFactorX());
							radiusY = (long) (((NumberValue) joBox.get("radius")).getValue().longValue()
									* gp.getWindowDataHolder().scalingFactorY());
							if (joBox.containsKey("innerRadius")) {
								innerRadiusX = (long) (((NumberValue) joBox.get("innerRadius")).getValue().longValue()
										* gp.getWindowDataHolder().scalingFactorX());
								innerRadiusY = (long) (((NumberValue) joBox.get("innerRadius")).getValue().longValue()
										* gp.getWindowDataHolder().scalingFactorY());
							}
							Shape shape;
							if (innerRadiusX != 0) shape = Shape.subtract(new Ellipse(x, y, radiusX, radiusY),
									new Ellipse(x, y, innerRadiusX, innerRadiusY));
							else shape = new Ellipse(x, y, radiusX, radiusY);
							shape.setStroke(Color.color(1, 1, 0, .75));
							shape.setFill(Color.TRANSPARENT);
							shape.setStrokeWidth(2.5);
							this.miscBoxes.put(name, shape);
							miscBoxHandler.put(name, (o, t) -> {});
						}
						break;
						case "polygon":
							System.err.println("Type: polygon is not yet defined for misc boxes");
							break;
						default:
							System.err.println("Type: "+type+" is not yet defined for misc boxes");
							break;
					}
				}
			}

			if (((JsonArray) gameObject.get("position")).get(0) instanceof JsonArray ja) {
				boolean secondMultiPlexer = ((JsonArray) gameObject.get("requestedSize")).get(0) instanceof JsonArray;
				try {
					slaves = new ArrayList<>();
					for (int i = 1; i < ((JsonArray) gameObject.get("position")).size(); i++) {
						GameObject b = this
								.getClass().getDeclaredConstructor(this.getClass(), List.class,
										ObjectProperty.class, WindowDataHolder.class)
								.newInstance(this, gameObjects, requestor, gp.getWindowDataHolder());
						b.setPosition(
								(long) (((NumberValue) ((JsonArray) ((JsonArray) gameObject.get("position")).get(i))
										.get(0))
										.getValue()
										.doubleValue()
										* gp.getWindowDataHolder().scalingFactorX()),
								(long) (((NumberValue) ((JsonArray) ((JsonArray) gameObject.get("position")).get(i))
										.get(1))
										.getValue()
										.doubleValue()
										* gp.getWindowDataHolder().scalingFactorY()));
						if (!secondMultiPlexer) {
							b.reqWidth = ((NumberValue) ((JsonArray) gameObject.get("requestedSize")).get(0))
									.getValue()
									.intValue();
							b.reqHeight = ((NumberValue) ((JsonArray) gameObject.get("requestedSize")).get(1))
									.getValue().intValue();
						} else {
							JsonArray reqSize = (JsonArray) ((JsonArray) gameObject.get("requestedSize")).get(i);
							b.reqWidth = ((NumberValue) reqSize.get(0)).getValue().intValue();
							b.reqHeight = ((NumberValue) reqSize.get(1)).getValue().intValue();
						}
						if (gameObject.containsKey("fps"))
							if (secondMultiPlexer)
								b.fps = ((NumberValue) ((JsonArray) gameObject.get("fps")).get(i)).getValue()
								.intValue();
							else b.fps = ((NumberValue) gameObject.get("fps")).getValue().doubleValue();
						else b.fps = 7;
						if (gameObject.containsKey("layer"))
							if (secondMultiPlexer)
								b.setLayer(((NumberValue) ((JsonArray) gameObject.get("layer")).get(i)).getValue()
										.intValue());
							else
								b.setLayer(((NumberValue) gameObject.get("layer")).getValue().intValue());
						else b.setLayer(0);
						if (gameObject.containsKey("background"))
							if (secondMultiPlexer)
								b.background = ((BoolValue) ((JsonArray) gameObject.get("background")).get(i))
								.getValue();
							else
								b.background = ((BoolValue) gameObject.get("background")).getValue();

						if (gameObject.containsKey("extraData"))
							if (secondMultiPlexer)
								b.extraData = (JsonObject) ((JsonArray) gameObject.get("extraData")).get(i);
							else
								b.extraData = (JsonObject) gameObject.get("extraData");
						else b.extraData = new JsonObject();
						((JsonObject) gameObject.get("textures")).entrySet().parallelStream()
						.forEach(s -> {
							try {
								b.getAnimatedImages(s.getKey(), ((StringValue) s.getValue()).getValue());
							} catch (FileNotFoundException e1) {
								e1.printStackTrace();
							}
						});
					}
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException | NoSuchMethodException | SecurityException e) {
					e.printStackTrace();
				}
				x = (long) (((NumberValue) ((JsonArray) ((JsonArray) gameObject.get("position")).get(0)).get(0))
						.getValue()
						.doubleValue() * gp.getWindowDataHolder().scalingFactorX());
				y = (long) (((NumberValue) ((JsonArray) ((JsonArray) gameObject.get("position")).get(0)).get(1))
						.getValue()
						.doubleValue() * gp.getWindowDataHolder().scalingFactorY());
				if (!secondMultiPlexer) {
					reqWidth = ((NumberValue) ((JsonArray) gameObject.get("requestedSize")).get(0)).getValue()
							.intValue();
					reqHeight = ((NumberValue) ((JsonArray) gameObject.get("requestedSize")).get(1)).getValue()
							.intValue();
				} else {
					JsonArray reqSize = (JsonArray) ((JsonArray) gameObject.get("requestedSize")).get(0);
					reqWidth = ((NumberValue) reqSize.get(0)).getValue().intValue();
					reqHeight = ((NumberValue) reqSize.get(1)).getValue().intValue();
				}
				if (gameObject.containsKey("fps"))
					if (secondMultiPlexer)
						fps = ((NumberValue) ((JsonArray) gameObject.get("fps")).get(0)).getValue().intValue();
					else fps = ((NumberValue) gameObject.get("fps")).getValue().doubleValue();
				else fps = 7;
				if (gameObject.containsKey("layer"))
					if (secondMultiPlexer)
						layer = ((NumberValue) ((JsonArray) gameObject.get("layer")).get(0)).getValue().intValue();
					else
						layer = ((NumberValue) gameObject.get("layer")).getValue().intValue();
				else layer = 0;
				if (gameObject.containsKey("background"))
					if (secondMultiPlexer)
						background = ((BoolValue) ((JsonArray) gameObject.get("background")).get(0)).getValue();
					else
						background = ((BoolValue) gameObject.get("background")).getValue();

				if (gameObject.containsKey("extraData"))
					if (secondMultiPlexer)
						extraData = (JsonObject) ((JsonArray) gameObject.get("extraData")).get(0);
					else
						extraData = (JsonObject) gameObject.get("extraData");
				else extraData = new JsonObject();
			} else {
				x = (long) (((NumberValue) ((JsonArray) gameObject.get("position")).get(0)).getValue().doubleValue()
						* gp.getWindowDataHolder().scalingFactorX());
				y = (long) (((NumberValue) ((JsonArray) gameObject.get("position")).get(1)).getValue().doubleValue()
						* gp.getWindowDataHolder().scalingFactorY());
				if (((JsonArray) gameObject.get("requestedSize")).get(0) instanceof NumberValue nv) {
					reqWidth = nv.getValue().intValue();
					reqHeight = ((NumberValue) ((JsonArray) gameObject.get("requestedSize")).get(1)).getValue()
							.intValue();
				} else {
					JsonArray reqSize =(JsonArray) ((JsonArray) gameObject.get("requestedSize")).get(0);
					reqWidth = ((NumberValue) reqSize.get(0)).getValue().intValue();
					reqHeight = ((NumberValue) reqSize.get(1)).getValue().intValue();
				}
				if (gameObject.containsKey("fps"))
					if (gameObject.get("fps") instanceof JsonArray ja)
						fps = ((NumberValue) ja.get(0)).getValue().intValue();
					else fps = ((NumberValue) gameObject.get("fps")).getValue().doubleValue();
				else fps = 7;
				if (gameObject.containsKey("layer"))
					if (gameObject.get("layer") instanceof JsonArray ja)
						layer = ((NumberValue) ja.get(0)).getValue().intValue();
					else
						layer = ((NumberValue) gameObject.get("layer")).getValue().intValue();
				else layer = 0;
				if (gameObject.containsKey("background"))
					if (gameObject.get("background") instanceof JsonArray ja)
						background = ((BoolValue) ja.get(0)).getValue();
					else
						background = ((BoolValue) gameObject.get("background")).getValue();

				if (gameObject.containsKey("extraData"))
					if (gameObject.get("extraData") instanceof JsonArray ja)
						extraData = (JsonObject) ja.get(0);
					else
						extraData = (JsonObject) gameObject.get("extraData");
				else extraData = new JsonObject();
			}

			((JsonObject) gameObject.get("textures")).entrySet().parallelStream()
			.forEach(s -> {
				try {
					getAnimatedImages(s.getKey(), ((StringValue) s.getValue()).getValue());
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
			});

			List<Image> li = new ArrayList<>();

			if (textureFiles.entrySet().iterator().next().getValue().toLowerCase().endsWith("gif")) {
				Collections.addAll(li,
						ImgUtil.getScaledImages(gamepanel.getWindowDataHolder(),
								"./res/" + directory + "/" + textureFiles.entrySet().iterator().next().getValue(), reqWidth,
								reqHeight, flipTextures));

				fps = 10;
				// li.add(img);
			} else try {
				Image img = new Image(new FileInputStream("./res/" + directory + "/" + textureFiles.entrySet().iterator().next().getValue()));
				for (int i = 0; i < img.getWidth(); i += origWidth) {
					WritableImage wi = new WritableImage(img.getPixelReader(), i, 0, origWidth, origHeight);
					li.add(ImgUtil.resizeImage(wi,
							(int) wi.getWidth(), (int) wi.getHeight(), (int) (reqWidth * gamepanel.getWindowDataHolder().scalingFactorX()),
							(int) (reqHeight * gamepanel.getWindowDataHolder().scalingFactorY()), flipTextures));
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			image.init(li.toArray(new Image[0]), (int) fps);

		}

		menu = new Menu("GameObject");
		menu.setStyle("-fx-font-size: 20;");
		imagesM = new Menu("Images");
		imagesM.setStyle("-fx-font-size: 20;");
		position = new MenuItem();
		fpsI = new MenuItem();
		currentKeyI = new MenuItem();
		directoryI = new MenuItem();
		origDim = new MenuItem();
		reqDim = new MenuItem();
		backgroundI = new MenuItem();
		layerI = new MenuItem();
		reloadTextures = new MenuItem("Reload Textures");
		reloadTextures.setStyle("-fx-font-size: 20;");
		removeMI = new MenuItem("Remove");
		removeMI.setStyle("-fx-font-size: 20;");
		position.setOnAction(this::handleContextMenu);
		fpsI.setOnAction(this::handleContextMenu);
		currentKeyI.setOnAction(this::handleContextMenu);
		directoryI.setOnAction(this::handleContextMenu);
		origDim.setOnAction(this::handleContextMenu);
		reqDim.setOnAction(this::handleContextMenu);
		backgroundI.setOnAction(this::handleContextMenu);
		layerI.setOnAction(this::handleContextMenu);
		reloadTextures.setOnAction(this::handleContextMenu);
		removeMI.setOnAction(this::handleContextMenu);
		menu.getItems().addAll(position, fpsI, imagesM, currentKeyI, directoryI, origDim, reqDim, backgroundI, layerI,
				reloadTextures, removeMI);

		if (gameObjects != null) {
			((List<GameObject>) gameObjects).add(this);
			removeCallbacks.add(() -> {
				gameObjects.remove(this);
			});
		}

	}

	/**
	 * Handle context menu.
	 *
	 * @param e the e
	 */
	private void handleContextMenu(ActionEvent e) {
		MenuItem	source	= (MenuItem) e.getSource();
		ContextMenu	cm		= source.getParentMenu().getParentPopup();
		if (source == position) {
			TwoTextInputDialog dialog = new TwoTextInputDialog(x + "", "X", y + "", "Y");

			updateXY = () -> {
				if (! (x + "").equals(dialog.getTextField1().getText()))
					dialog.getTextField1().setText(x + "");
				if (! (y + "").equals(dialog.getTextField2().getText()))
					dialog.getTextField2().setText(y + "");
			};

			dialog.initModality(Modality.NONE);
			dialog.setTitle("Position");
			Input.getInstance(null).moveGameObject(this);
			Optional<List<String>> result = dialog.showAndWait();
			if (result.isPresent()) {
				try {
					x = Double.parseDouble(result.get().get(0));
				} catch (NumberFormatException e2) {}
				try {
					y = Double.parseDouble(result.get().get(1));
				} catch (NumberFormatException e2) {}
			}
			Input.getInstance(null).stopMoveingGameObject(this);
		} else if (source == fpsI) {
			TextInputDialog dialog = new TextInputDialog("" + fps);
			dialog.setHeaderText("");
			dialog.getDialogPane().getStyleClass().remove("text-input-dialog");
			dialog.setTitle("FPS");
			dialog.setContentText("Please enter the new FPS value:");

			Optional<String> result = dialog.showAndWait();
			if (result.isPresent()) try {
				fps = Double.parseDouble(result.get());
			} catch (NumberFormatException e2) {}
		} else if (source == currentKeyI) {
			TextInputDialog dialog = new TextInputDialog(currentKey);
			dialog.setHeaderText("");
			dialog.getDialogPane().getStyleClass().remove("text-input-dialog");
			dialog.setTitle("Current Key");
			dialog.setContentText("Please enter the new Key:");

			Optional<String> result = dialog.showAndWait();
			if (result.isPresent()) if (textureFiles.containsKey(result.get())) currentKey = result.get();

		} else if (source == directoryI) {
			TextInputDialog dialog = new TextInputDialog(directory);
			dialog.setHeaderText("");
			dialog.getDialogPane().getStyleClass().remove("text-input-dialog");
			dialog.setTitle("Directory");
			dialog.setContentText("Please enter the name of an Directory located in res/ :");

			Optional<String> result = dialog.showAndWait();

			if (result.isPresent()) if (new File("./res/" + result.get()).exists()) directory = result.get();

		} else if (source == origDim) {
			TwoTextInputDialog dialog = new TwoTextInputDialog(origWidth + "", "Width", origHeight + "", "Height");
			dialog.setTitle("Original Dimension");
			Optional<List<String>> result = dialog.showAndWait();
			if (result.isPresent()) {
				try {
					origWidth = Integer.parseInt(result.get().get(0));
				} catch (NumberFormatException e2) {}
				try {
					origHeight = Integer.parseInt(result.get().get(1));
				} catch (NumberFormatException e2) {}
			}
		} else if (source == reqDim) {
			TwoTextInputDialog dialog = new TwoTextInputDialog(reqWidth + "", "Width", reqHeight + "", "Height");

			updateReqDim = () -> {
				if (! (reqWidth + "").equals(dialog.getTextField1().getText()))
					dialog.getTextField1().setText(reqWidth + "");
				if (! (reqHeight + "").equals(dialog.getTextField2().getText()))
					dialog.getTextField2().setText(reqHeight + "");
			};

			dialog.setTitle("Requested Dimension");
			dialog.initModality(Modality.NONE);
			Input.getInstance(null).resizeGameObject(this);
			Optional<List<String>> result = dialog.showAndWait();
			if (result.isPresent()) {
				try {
					reqWidth = Integer.parseInt(result.get().get(0));
					reloadTextures();
				} catch (NumberFormatException e2) {}
				try {
					reqHeight = Integer.parseInt(result.get().get(1));
					reloadTextures();
				} catch (NumberFormatException e2) {}
			}
			Input.getInstance(null).stopResizeingGameObject(this);
		} else if (source == backgroundI) {
			Alert alert = new Alert(Alert.AlertType.NONE);
			alert.setTitle("Background");
			alert.setContentText("Set the value for Background");
			ButtonType	okButton		= new ButtonType("true", ButtonBar.ButtonData.YES);
			ButtonType	noButton		= new ButtonType("false", ButtonBar.ButtonData.NO);
			ButtonType	cancelButton	= new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
			alert.getButtonTypes().setAll(okButton, noButton, cancelButton);
			Optional<ButtonType> res = alert.showAndWait();
			if (res.isPresent()) if (res.get() == okButton) background = true;
			else if (res.get() == noButton) background = false;
			System.out.println(background);
		} else if (source == layerI) {
			int					origLayer	= layer;
			LayerInputDialog	lid			= new LayerInputDialog(this::getLayer, this::setLayer);

			lid.setTitle("Layer");
			Optional<Boolean> result = lid.showAndWait();
			if (result.isPresent() && !result.get() || !result.isPresent()) layer = origLayer;

		} else if (source == reloadTextures) reloadTextures();
		else if (source == removeMI) remove();
		else {
			cm = source.getParentMenu().getParentMenu().getParentPopup();
			if ("add Texture".equals(source.getText())) {

				FileChooser fc = new FileChooser();
				fc.setInitialDirectory(new File("."));
				fc.setTitle("Select a texture file");
				fc.getExtensionFilters().add(new ExtensionFilter(
						"A file containing an Image", "*.png", "*.gif"));
				File f = fc.showOpenDialog(cm.getScene().getWindow());
				if (f == null || !f.exists()) return;
				TextInputDialog dialog = new TextInputDialog("");
				dialog.setHeaderText("");
				dialog.getDialogPane().getStyleClass().remove("text-input-dialog");
				dialog.setTitle("Naming a new State");
				dialog.setContentText("Please enter a name for that State:");

				Optional<String> result = dialog.showAndWait();
				result.ifPresent(name -> {

					try {
						Path	p1	= f.toPath();
						Path	p2	= new File("./res/" + directory + "/" + f.getName()).toPath();
						if (Files.exists(p2)) {
							Alert alert = new Alert(Alert.AlertType.NONE);
							alert.setTitle("The file already exists");
							alert.setContentText("Do you want to Override it?");
							ButtonType	okButton		= new ButtonType("Yes", ButtonBar.ButtonData.YES);
							ButtonType	noButton		= new ButtonType("No", ButtonBar.ButtonData.NO);
							ButtonType	cancelButton	= new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
							alert.getButtonTypes().setAll(okButton, noButton, cancelButton);
							Optional<ButtonType> res = alert.showAndWait();
							if (!res.isPresent()) return;
							if (res.get() == okButton) try {
								Files.copy(p1, p2, StandardCopyOption.COPY_ATTRIBUTES,
										StandardCopyOption.REPLACE_EXISTING);
							} catch (IOException e1) {
								e1.printStackTrace();
							}
							else if (res.get() == cancelButton) return;

						} else
							Files.copy(p1, p2, StandardCopyOption.COPY_ATTRIBUTES, StandardCopyOption.REPLACE_EXISTING);
						System.out.println(f);
						System.out.println(p2);
						getAnimatedImages(name, f.getName());
						if (!collisionBoxes.containsKey(name)) collisionBoxes.put(name, new Polygon());

					} catch (IOException e1) {
						e1.printStackTrace();
					}
				});
			} else {
				FileChooser fc = new FileChooser();
				fc.setInitialDirectory(new File("."));
				fc.setTitle("Select a texture file");
				fc.getExtensionFilters().add(new ExtensionFilter(
						"A file containing an Image", "*.png", "*.gif"));
				File f = fc.showOpenDialog(cm.getScene().getWindow());
				if (f == null || !f.exists()) return;
				try {
					Path	p1	= f.toPath();
					Path	p2	= new File("./res/" + directory + "/" + f.getName()).toPath();
					if (Files.exists(p2)) {
						Alert alert = new Alert(Alert.AlertType.NONE);
						alert.setTitle("The file already exists");
						alert.setContentText("Do you want to Override it?");
						ButtonType	okButton		= new ButtonType("Yes", ButtonBar.ButtonData.YES);
						ButtonType	noButton		= new ButtonType("No", ButtonBar.ButtonData.NO);
						ButtonType	cancelButton	= new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
						alert.getButtonTypes().setAll(okButton, noButton, cancelButton);
						Optional<ButtonType> res = alert.showAndWait();
						if (!res.isPresent()) return;
						if (res.get() == okButton) try {
							Files.copy(p1, p2, StandardCopyOption.COPY_ATTRIBUTES,
									StandardCopyOption.REPLACE_EXISTING);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						else if (res.get() == cancelButton) return;

					} else Files.copy(p1, p2, StandardCopyOption.COPY_ATTRIBUTES, StandardCopyOption.REPLACE_EXISTING);
					System.out.println(f);
					System.out.println(p2);
					String name = source.getText().split(":")[0];
					name = name.substring(0, name.length() - 1);
					getAnimatedImages(name, f.getName());
					if (!collisionBoxes.containsKey(name)) collisionBoxes.put(name, new Polygon());

				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}

	}

	/**
	 * Removes the.
	 */
	private void remove() {
		remove = true;
		gamepanel.getNpcs().remove(this);
		gamepanel.getBuildings().remove(this);
		gamepanel.getMobRans().remove(this);
		removeCallbacks.forEach(Runnable::run);

	}

	/**
	 * Gets the animated images.
	 *
	 * @param key the key
	 * @param path the path
	 * @return the animated images
	 * @throws FileNotFoundException the file not found exception
	 */
	protected List<Image> getAnimatedImages(String key, String path) throws FileNotFoundException {
		List<Image> li = new ArrayList<>();
		String[]	sp				= path.split("[.]");
		Polygon		collisionBox	= collisionBoxes.get(key);
		if (collisionBox == null) collisionBoxes.put(key, collisionBox = new Polygon());
		if (new File("./res/collisions/" + directory + "/" + String.join(".", Arrays.copyOf(sp, sp.length - 1))
		+ ".collisionbox").exists())
			try {
				RandomAccessFile raf = new RandomAccessFile(new File("./res/collisions/" + directory + "/"
						+ String.join(".", Arrays.copyOf(sp, sp.length - 1))
						+ ".collisionbox"), "rws");
				raf.seek(0l);
				int	length	= raf.readInt();
				boolean s	= false;
				for (int i = 0; i < length; i++) collisionBox.getPoints()
				.add((double) (long) (raf.readDouble()
						* ( (s = !s) ? gamepanel.getWindowDataHolder().scalingFactorX()
								: gamepanel.getWindowDataHolder().scalingFactorY())));
			} catch (IOException e) {
				e.printStackTrace();
			}
		textureFiles.put(key, path);
		return li;
	}

	/**
	 * Gets the misc box handler.
	 *
	 * @return the misc box handler
	 */
	protected Map<String, BiConsumer<GamePanel, GameObject>> getMiscBoxHandler() { return miscBoxHandler; }

	/**
	 * Adds the misc box.
	 *
	 * @param key the key
	 * @param box the box
	 * @param handler the handler
	 */
	public void addMiscBox(String key, Shape box, BiConsumer<GamePanel, GameObject> handler) {
		box.setStroke(Color.color(1, 1, 0, .75));
		box.setFill(Color.TRANSPARENT);
		box.setStrokeWidth(2.5);
		miscBoxes.put(key, box);
		miscBoxHandler.put(key, handler);
		if (slaves != null) for (GameObject slave:slaves) {
			Shape p;
			if (box instanceof Ellipse circ)
				p = new Ellipse(circ.getCenterX(), circ.getCenterY(), circ.getRadiusX(), circ.getRadiusY());
			else if (box instanceof Rectangle rect)
				p = new Rectangle(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
			else if (box instanceof Polygon poly) {
				p = new Polygon();
				((Polygon) p).getPoints().addAll(poly.getPoints());
			} else if (box instanceof Ring ring) p = new Ring(ring.getX(), ring.getY(), ring.getRadiusX(),
					ring.getRadiusY(), ring.getInnerRadiusX(), ring.getInnerRadiusY());
			else return;
			p.setStroke(Color.color(1, 1, 0, .75));
			p.setFill(Color.TRANSPARENT);
			p.setStrokeWidth(2.5);
			slave.getMiscBoxes().put(key, p);
			slave.getMiscBoxHandler().put(key, handler);
		}
	}

	/**
	 * Collides.
	 *
	 * @param collidable the collidable
	 * @return true, if successful
	 */
	public boolean collides(Collidable collidable) {
		if (getCollisionBox() != null && getCollisionBox().getPoints().size() > 0 && !collidable.getCollisionBox().getLayoutBounds().isEmpty()) {
			Shape intersect = Shape.intersect(collidable.getCollisionBox(), getCollisionBox());
			return !intersect.getBoundsInLocal().isEmpty();
		}
		return false;
	}

	/**
	 * Flip textures.
	 */
	public void flipTextures() {
		flipTextures = !flipTextures;
		reloadTextures();
	}

	/**
	 * Gets the border.
	 *
	 * @return the border
	 */
	public Border getBorder() { return border; }

	/**
	 * Gets the collision box.
	 *
	 * @return the collision box
	 */
	@Override
	public Polygon getCollisionBox() {
		return collisionBoxes.get(currentKey);
	}

	/**
	 * Gets the current key.
	 *
	 * @return the current key
	 */
	public String getCurrentKey() { return currentKey; }

	/**
	 * Gets the height.
	 *
	 * @return the height
	 */
	public double getHeight() { return height; }

	/**
	 * Gets the image.
	 *
	 * @return the image
	 */
	public AnimatedImage getImage() { return image; }

	/**
	 * Gets the layer.
	 *
	 * @return the layer
	 */
	public int getLayer() { return layer; }

	/**
	 * Gets the layout X.
	 *
	 * @return the layout X
	 */
	public double getLayoutX() { return layoutX; }

	/**
	 * Gets the layout Y.
	 *
	 * @return the layout Y
	 */
	public double getLayoutY() { return layoutY; }

	/**
	 * Gets the menus.
	 *
	 * @return the menus
	 */
	public List<Menu> getMenus() {
		position.setText("Position: " + x + " " + y);
		position.setStyle("-fx-font-size: 20;");
		fpsI.setText("FPS: " + fps);
		fpsI.setStyle("-fx-font-size: 20;");
		currentKeyI.setText("Current Key: " + currentKey);
		currentKeyI.setStyle("-fx-font-size: 20;");
		directoryI.setText("Directory: " + directory);
		directoryI.setStyle("-fx-font-size: 20;");
		origDim.setText("Original Dimensions: " + origWidth + " " + origHeight);
		origDim.setStyle("-fx-font-size: 20;");
		reqDim.setText("Requested Dimensions: " + reqWidth + " " + reqHeight);
		reqDim.setStyle("-fx-font-size: 20;");
		backgroundI.setText("Background: " + background);
		backgroundI.setStyle("-fx-font-size: 20;");
		layerI.setText("Layer: " + layer);
		layerI.setStyle("-fx-font-size: 20;");
		imagesM.getItems().clear();
		for (Entry<String, String> en: textureFiles.entrySet()) {
			ImageView	lIV	= new ImageView(ImgUtil.resizeImage(image.getFrameAt(image.getFrameIndex()),
					(int) image.getFrameAt(image.getFrameIndex()).getWidth(),
					(int) image.getFrameAt(image.getFrameIndex()).getHeight(), 48, 48));
			MenuItem img = new MenuItem(en.getKey() + " : " + en.getValue(),
					lIV);
			img.setStyle("-fx-font-size: 20;");
			img.setOnAction(this::handleContextMenu);
			imagesM.getItems().add(img);
		}
		MenuItem img = new MenuItem("add Texture");
		img.setStyle("-fx-font-size: 20;");
		img.setOnAction(this::handleContextMenu);
		imagesM.getItems().add(img);
		List<Menu> li = new ArrayList<>();
		li.add(menu);
		return li;
	}


	/**
	 * Gets the misc boxes.
	 *
	 * @return the misc boxes
	 */
	public Map<String, Shape> getMiscBoxes() {
		return miscBoxes;
	}
	/**
	 * Gets the orig height.
	 *
	 * @return the orig height
	 */
	public int getOrigHeight() { return origHeight; }

	/**
	 * Gets the orig width.
	 *
	 * @return the orig width
	 */
	public int getOrigWidth() { return origWidth; }

	/**
	 * Gets the req height.
	 *
	 * @return the req height
	 */
	public int getReqHeight() { return reqHeight; }

	/**
	 * Gets the requestor.
	 *
	 * @return the requestor
	 */
	@SuppressWarnings("unchecked")
	public ObjectProperty<GameObject> getRequestor() { return (ObjectProperty<GameObject>) requestor; }

	/**
	 * Gets the req width.
	 *
	 * @return the req width
	 */
	public int getReqWidth() { return reqWidth; }

	/**
	 * Gets the texture files.
	 *
	 * @return the texture files
	 */
	public Map<String, String> getTextureFiles() {
		return textureFiles;
	}

	/**
	 * Gets the texture height.
	 *
	 * @return the texture height
	 */
	public double getTextureHeight() {
		return image.getFrameAt(image.getFrameIndex()) != null ? image.getFrameAt(image.getFrameIndex()).getHeight() : getHeight();
	}

	/**
	 * Gets the texture width.
	 *
	 * @return the texture width
	 */
	public double getTextureWidth() {
		return image.getFrameAt(image.getFrameIndex()) != null ? image.getFrameAt(image.getFrameIndex()).getWidth() : getWidth();
	}

	/**
	 * Gets the width.
	 *
	 * @return the width
	 */
	public double getWidth() { return width; }

	/**
	 * Gets the x.
	 *
	 * @return the x
	 */
	public double getX() { return x; }

	/**
	 * Gets the y.
	 *
	 * @return the y
	 */
	public double getY() { return y; }

	/**
	 * Checks if is background.
	 *
	 * @return true, if is background
	 */
	public boolean isBackground() { return background; }

	/**
	 * Checks if is fix to screen.
	 *
	 * @return true, if is fix to screen
	 */
	public boolean isFixToScreen() {
		return fixToScreen;
	}

	/**
	 * Checks if is master.
	 *
	 * @return true, if is master
	 */
	public boolean isMaster() { return !slave; }

	/**
	 * Checks if is removes the.
	 *
	 * @return true, if is removes the
	 */
	public boolean isRemove() { return remove; }

	/**
	 * Checks if is slave.
	 *
	 * @return true, if is slave
	 */
	public boolean isSlave() { return slave; }

	/**
	 * Checks if is visible.
	 *
	 * @return true, if is visible
	 */
	public boolean isVisible() { return visible; }

	/**
	 * Reload textures.
	 */
	public void reloadTextures() {
		List<Entry<String, String>> textures = new ArrayList<>(textureFiles.entrySet());
		for (Entry<String, String> en: textures) try {
			getAnimatedImages(en.getKey(), en.getValue());
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Reset remove.
	 */
	public void resetRemove() {
		remove = false;
	}

	/**
	 * Sets the border.
	 *
	 * @param border the new border
	 */
	public void setBorder(Border border) { this.border = border; }

	/**
	 * Sets the current key.
	 *
	 * @param currentKey the new current key
	 */
	public void setCurrentKey(String currentKey) {
		if (textureFiles.containsKey(currentKey))
			this.currentKey = currentKey;
	}

	/**
	 * Sets the fix to screen.
	 *
	 * @param fixToScreen the new fix to screen
	 */
	public void setFixToScreen(boolean fixToScreen) {
		this.fixToScreen = fixToScreen;
	}

	/**
	 * Sets the height.
	 *
	 * @param height the new height
	 */
	public void setHeight(double height) { this.height = height; }

	/**
	 * Sets the layer.
	 *
	 * @param layer the new layer
	 */
	public void setLayer(int layer) {
		this.layer = layer;
	}

	/**
	 * Sets the layout X.
	 *
	 * @param layoutX the new layout X
	 */
	public void setLayoutX(double layoutX) { this.layoutX = layoutX; }

	/**
	 * Sets the layout Y.
	 *
	 * @param layoutY the new layout Y
	 */
	public void setLayoutY(double layoutY) { this.layoutY = layoutY; }

	/**
	 * Sets the orig height.
	 *
	 * @param origHeight the new orig height
	 */
	public void setOrigHeight(int origHeight) { this.origHeight = origHeight; }

	/**
	 * Sets the orig width.
	 *
	 * @param origWidth the new orig width
	 */
	public void setOrigWidth(int origWidth) { this.origWidth = origWidth; }

	/**
	 * Sets the position.
	 *
	 * @param x the x
	 * @param y the y
	 */
	public void setPosition(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Sets the req height.
	 *
	 * @param reqHeight the new req height
	 */
	public void setReqHeight(int reqHeight) {
		this.reqHeight = reqHeight;
		if (updateReqDim != null)
			updateReqDim.run();
	}

	/**
	 * Sets the req width.
	 *
	 * @param reqWidth the new req width
	 */
	public void setReqWidth(int reqWidth) {
		this.reqWidth = reqWidth;
		if (updateReqDim != null)
			updateReqDim.run();
	}

	/**
	 * Sets the visible.
	 *
	 * @param visible the new visible
	 */
	public void setVisible(boolean visible) { this.visible = visible; }

	/**
	 * Sets the width.
	 *
	 * @param width the new width
	 */
	public void setWidth(double width) { this.width = width; }

	/**
	 * Sets the x.
	 *
	 * @param x the new x
	 */
	public void setX(double x) {
		this.x = x;
		if (updateXY != null)
			updateXY.run();
	}

	/**
	 * Sets the y.
	 *
	 * @param y the new y
	 */
	public void setY(double y) {
		this.y = y;
		if (updateXY != null)
			updateXY.run();
	}

	/**
	 * To json value.
	 *
	 * @return the json value
	 */
	@Override
	public JsonValue toJsonValue() {
		if (slave) return new StringValue("");
		JsonObject jo = new JsonObject();
		jo.put("type", getClass().getSimpleName());
		jo.put("textures", textureFiles);
		JsonArray originalSize = new JsonArray();
		originalSize.add(origWidth);
		originalSize.add(origHeight);
		jo.put("originalSize", originalSize);
		JsonArray	position		= new JsonArray();
		JsonArray	extraDatas		= new JsonArray();
		JsonArray	fpss			= new JsonArray();
		JsonArray	requestedSize	= new JsonArray();
		JsonArray	backgrounds		= new JsonArray();
		JsonArray	layers			= new JsonArray();
		JsonArray	miscBoxes		= new JsonArray();
		for (Entry<String, Shape> box : this.miscBoxes.entrySet()) {
			JsonObject jBox = new JsonObject();
			jBox.put("name", box.getKey());
			if (box.getValue() instanceof Ellipse circ) {
				jBox.put("type", "circle");
				jBox.put("x", (long) circ.getCenterX() / gamepanel.getWindowDataHolder().scalingFactorX());
				jBox.put("y", (long) circ.getCenterY() / gamepanel.getWindowDataHolder().scalingFactorY());
				jBox.put("radius", (long) ( (circ.getRadiusX() / gamepanel.getWindowDataHolder().scalingFactorX()
						+ circ.getRadiusY() / gamepanel.getWindowDataHolder().scalingFactorY()) / 2.0));
			} else if (box.getValue() instanceof Rectangle rect) {
				jBox.put("type", "rectangle");
				jBox.put("x", (long) rect.getX() / gamepanel.getWindowDataHolder().scalingFactorX());
				jBox.put("y", (long) rect.getY() / gamepanel.getWindowDataHolder().scalingFactorY());
				jBox.put("width", (long) rect.getWidth() / gamepanel.getWindowDataHolder().scalingFactorX());
				jBox.put("height", (long) rect.getHeight() / gamepanel.getWindowDataHolder().scalingFactorY());
			} else if (box.getValue() instanceof Polygon poly || box.getValue() instanceof Ring ring) {
				// TODO
			} else continue;
			miscBoxes.add(jBox);
		}
		if (slaves == null || slaves.size() == 0) {
			position.add(x / gamepanel.getWindowDataHolder().scalingFactorX());
			position.add(y / gamepanel.getWindowDataHolder().scalingFactorY());
			extraDatas.add(extraData);
			fpss.add(fps);
			requestedSize.add(reqWidth);
			requestedSize.add(reqHeight);
			backgrounds.add(background);
			layers.add(layer);
		} else {
			JsonArray pos = new JsonArray();
			pos.add(x / gamepanel.getWindowDataHolder().scalingFactorX());
			pos.add(y / gamepanel.getWindowDataHolder().scalingFactorY());
			position.add(pos);
			extraDatas.add(extraData);
			fpss.add(fps);
			JsonArray reqSize = new JsonArray();
			reqSize.add(reqWidth);
			reqSize.add(reqHeight);
			requestedSize.add(reqSize);
			backgrounds.add(background);
			layers.add(layer);
			for (GameObject b : slaves) {
				pos = new JsonArray();
				pos.add(b.x / gamepanel.getWindowDataHolder().scalingFactorX());
				pos.add(b.y / gamepanel.getWindowDataHolder().scalingFactorY());
				position.add(pos);
				extraDatas.add(b.extraData);
				fpss.add(b.fps);
				reqSize = new JsonArray();
				reqSize.add(b.reqWidth);
				reqSize.add(b.reqHeight);
				requestedSize.add(reqSize);
				backgrounds.add(b.background);
				layers.add(b.layer);
			}
		}
		jo.put("position", position);
		jo.put("extraData", extraDatas);
		jo.put("fps", fpss);
		jo.put("requestedSize", requestedSize);
		jo.put("background", backgrounds);
		jo.put("layer", layers);
		if (!this.miscBoxes.isEmpty())
			jo.put("miscBoxes", miscBoxes);
		return jo;

	}

	/**
	 * To json value.
	 *
	 * @param function the function
	 *
	 * @return the json value
	 */
	@Override
	public JsonValue toJsonValue(Function<Object, String> function) { return toJsonValue(); }

	/**
	 * Update.
	 *
	 * @param milis the milis
	 */
	public void update(long milis) {

		Player p = gamepanel.getPlayer();

		for (Entry<String, Shape> box : miscBoxes.entrySet())
			if (p.collides(() -> box.getValue())) miscBoxHandler.get(box.getKey()).accept(gamepanel, this);

		if ("true".equals(System.getProperty("coll")))
			collisionBoxViewGroup.setVisible(true);
		else
			collisionBoxViewGroup.setVisible(false);

		if (!lastKey.equals(currentKey)) {
			lastKey = currentKey;
			List<Image> li = new ArrayList<>();

			if (textureFiles.get(currentKey).toLowerCase().endsWith("gif")) {
				Collections.addAll(li,
						ImgUtil.getScaledImages(gamepanel.getWindowDataHolder(), "./res/" + directory + "/" + textureFiles.get(currentKey), reqWidth,
								reqHeight, flipTextures));

				fps = 10;
				// li.add(img);
			} else try {
				Image img = new Image(new FileInputStream("./res/" + directory + "/" + textureFiles.get(currentKey)));
				for (int i = 0; i < img.getWidth(); i += origWidth) {
					WritableImage wi = new WritableImage(img.getPixelReader(), i, 0, origWidth, origHeight);
					li.add(ImgUtil.resizeImage(wi,
							(int) wi.getWidth(), (int) wi.getHeight(), (int) (reqWidth * gamepanel.getWindowDataHolder().scalingFactorX()),
							(int) (reqHeight * gamepanel.getWindowDataHolder().scalingFactorY()), flipTextures));
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			image.init(li.toArray(new Image[0]), (int) fps);

			collisionBoxViewGroup.getChildren().clear();
			if (getCollisionBox() != null)
				collisionBoxViewGroup.getChildren().add(getCollisionBox());
		}

		if ("true".equals(System.getProperty("edit"))) {
			if (getBorder() == null)
				setBorder(new Border(
						new BorderStroke(Color.color(0, 0, 0, .75), BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
								BorderWidths.DEFAULT)));
			for (Shape box : miscBoxes.values()) if (!box.isVisible()) box.setVisible(true);
		} else {
			for (Shape box : miscBoxes.values()) if (box.isVisible()) box.setVisible(false);
			if (getBorder() != null)
				setBorder(null);
		}

		if (!fixToScreen) {
			double	screenX	= x - p.getX() + p.getScreenX();
			double	screenY	= y - p.getY() + p.getScreenY();
			setLayoutX(screenX);
			setLayoutY(screenY);
		}

		if (x + getWidth() > p.x - p.getScreenX()
				&& x < p.x + p.getScreenX() + p.getWidth()
				&& y + getHeight() > p.y - p.getScreenY()
				&& y < p.y + p.getScreenY() + p.getHeight() || fixToScreen)
			setVisible(true);
		else setVisible(false);

		image.update();

	}

}
