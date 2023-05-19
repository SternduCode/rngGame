package rngGame.visual;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.*;
import java.util.*;
import java.util.Map.Entry;

import com.sterndu.json.*;

import javafx.beans.property.ObjectProperty;
import javafx.event.ActionEvent;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import rngGame.buildings.Building;
import rngGame.entity.*;
import rngGame.main.UndoRedo;
import rngGame.main.UndoRedo.UndoRedoActionBase;
import rngGame.tile.*;
import rngGame.ui.*;


// TODO: Auto-generated Javadoc
/**
 * The Class TileManager.
 */
public class TileManager extends Pane {

	/** The logic. */
	private final rngGame.tile.TileManager logic;

	/** The scaling factor holder. */
	private final WindowDataHolder windowDataHolder;

	/** The context menu. */
	private final ContextMenu cm;

	/** The menus maps, insel_k, insel_m and insel_g. */
	private final Menu maps, insel_k, insel_m, insel_g;

	/** The menus for tiles, npcs, buildings, mobs and extras. */
	private final Menu mtiles, mnpcs, mbuildings, mextra, mmobs;

	/** The ui group for the map. */
	private final Group group;

	/**
	 * Instantiates a new tile manager.
	 *
	 * @param logic the logic
	 * @param windowDataHolder the window data holder
	 */
	public TileManager(rngGame.tile.TileManager logic, WindowDataHolder windowDataHolder) {
		this.logic = logic;

		this.windowDataHolder = windowDataHolder;

		group = new Group();
		getChildren().add(group);

		cm			= new ContextMenu();
		mtiles		= new Menu("Tiles");
		mnpcs		= new Menu("NPCs");
		mbuildings	= new Menu("Buildings");
		mextra		= new Menu("Extras");
		mmobs		= new Menu("Mob Test");
		mtiles.setStyle("-fx-font-size: 20;");
		mnpcs.setStyle("-fx-font-size: 20;");
		mbuildings.setStyle("-fx-font-size: 20;");
		mextra.setStyle("-fx-font-size: 20;");
		mmobs.setStyle("-fx-font-size: 20;");
		MenuItem save = new MenuItem("save");
		save.setStyle("-fx-font-size: 20;");
		save.setOnAction(logic::handleSave);
		mextra.getItems().add(save);

		MenuItem paste = new MenuItem("paste");
		paste.setStyle("-fx-font-size: 20;");
		paste.setOnAction(logic::paste);
		mextra.getItems().add(paste);

		MenuItem backToSpawn = new MenuItem("Go back to Spawn");
		backToSpawn.setStyle("-fx-font-size: 20;");
		backToSpawn.setOnAction(ae -> {
			double[] posi = getStartingPosition();
			gamePanel.getPlayer()
			.setPosition(new double[] {
					posi[0] * windowDataHolder.scalingFactorX(), posi[1] * windowDataHolder.scalingFactorY()
			});
		});
		mextra.getItems().add(backToSpawn);

		maps	= new Menu("Maps");
		insel_k	= new Menu("Insel_K");
		insel_m	= new Menu("Insel_M");
		insel_g	= new Menu("Insel_G");
		maps.setStyle("-fx-font-size: 20;");
		insel_k.setStyle("-fx-font-size: 20;");
		insel_m.setStyle("-fx-font-size: 20;");
		insel_g.setStyle("-fx-font-size: 20;");
		mextra.getItems().addAll(maps, insel_k, insel_m, insel_g);

		setOnContextMenuRequested(e -> {
			if ("true".equals(System.getProperty("edit")) && !cm.isShowing()) {
				System.out.println("dg");
				System.out.println();
				logic.setOnContextMenuRequested(e);
				cm.getItems().clear();
				cm.getItems().addAll(getMenus());
				cm.show(this, e.getScreenX(), e.getScreenY());
			}
		});

	}

	/**
	 * handles the context menus.
	 *
	 * @param e the event
	 */
	public void contextMenu(ActionEvent e) {
		try {
			if (requester.get() instanceof FakeTextureHolder fth && e.getSource() instanceof MenuItemWTile miw) {
				int	blockPosX	= (int) fth.getLayoutX() / windowDataHolder.blockSizeX() - (fth.getLayoutX() < 0 ? 1 : 0);
				int	blockPosY	= (int) fth.getLayoutY() / windowDataHolder.blockSizeY() - (fth.getLayoutY() < 0 ? 1 : 0);
				System.out.println(blockPosY);
				if (blockPosY < 0) for (int j = blockPosY; j < 0; j++) {
					logic.getMapTileNum()().add(0, new ArrayList<>());
					List<Integer> li = logic.getMapTileNum().get(0);
					for (int i = 0; i < logic.getMapTileNum().get(1).size(); i++) li.add(0);

					map.add(0, new ArrayList<>());

					for (Building b : buildings) b.setY(b.getY() + windowDataHolder.blockSizeY());

					for (NPC b : npcs) b.setY(b.getY() + windowDataHolder.blockSizeY());

					for (MobRan b : mobs) b.setY(b.getY() + windowDataHolder.blockSizeY());

					gamePanel.getPlayer().setPosition(gamePanel.getPlayer().getX(), gamePanel.getPlayer().getY() + windowDataHolder.blockSizeY());

					for (Entry<Point2D, Circle> en : gamePanel.getPoints().entrySet())
						en.getValue().setLayoutY(en.getValue().getLayoutY() + windowDataHolder.blockSizeY());

					startingPosition[1] = startingPosition[1] + windowDataHolder.blockSize();
				}
				if (blockPosX < 0) for (int i = blockPosX; i < 0; i++) {
					for (List<Integer> row : logic.getMapTileNum()) row.add(0, 0);

					for (List<TextureHolder> row : map) row.add(0, null);

					for (Building b : buildings) b.setX(b.getX() + windowDataHolder.blockSizeX());

					for (NPC b : npcs) b.setX(b.getX() + windowDataHolder.blockSizeX());

					for (MobRan b : mobs) b.setX(b.getX() + windowDataHolder.blockSizeX());

					gamePanel.getPlayer().setPosition(gamePanel.getPlayer().getX() + windowDataHolder.blockSizeX(), gamePanel.getPlayer().getY());

					for (Entry<Point2D, Circle> en : gamePanel.getPoints().entrySet())
						en.getValue().setLayoutX(en.getValue().getLayoutX() + windowDataHolder.blockSizeX());

					startingPosition[0] = startingPosition[0] + windowDataHolder.blockSize();
				}
				if (blockPosY >= logic.getMapTileNum().size()) for(int j = blockPosY - logic.getMapTileNum().size(); j >= 0; j--) {

					List<Integer> li = new ArrayList<>();

					for (int i = 0; i < logic.getMapTileNum().get(logic.getMapTileNum().size() - 1).size(); i++) li.add(0);

					logic.getMapTileNum().add(li);

					map.add(new ArrayList<>());
				}
				if (blockPosY < 0) blockPosY = 0;
				if (blockPosX < 0) blockPosX = 0;
				if (blockPosX >= logic.getMapTileNum().get(blockPosY).size()) for (int i = blockPosX - logic.getMapTileNum().get(blockPosY).size(); i >= 0; i--) {
					for (List<Integer> row : logic.getMapTileNum()) row.add(0, 0);

					for (List<TextureHolder> row : map) row.add(0, null);
				}

				logic.getMapTileNum().get(blockPosY).set(blockPosX, tiles.indexOf(miw.getTile()));

				System.out.println("Yo " + blockPosX + " " + blockPosY);
			}
			if (e.getSource() instanceof MenuItemWTile miwt) {
				Tile			t	= requester.get().getTile();
				TextureHolder	th	= requester.get();
				UndoRedo.getInstance().addAction(new UndoRedoActionBase(() -> {
					th.setTile(t);
				}, () -> {
					th.setTile(miwt.getTile());
				}));
			} else if (e.getSource() instanceof MenuItemWBuilding miwb)
				miwb.getBuilding().getClass()
				.getDeclaredConstructor(miwb.getBuilding().getClass(), List.class,
						ContextMenu.class, ObjectProperty.class)
				.newInstance(miwb.getBuilding(), buildings, cm, requesterB)
				.setPosition(requester.get().getLayoutX() - gamePanel.getPlayer().getScreenX() + gamePanel.getPlayer().getX(),
						requester.get().getLayoutY() - gamePanel.getPlayer().getScreenY() + gamePanel.getPlayer().getY());
			else if (e.getSource() instanceof MenuItemWNPC miwn)
				miwn.getNPC().getClass()
				.getDeclaredConstructor(miwn.getNPC().getClass(), List.class, ContextMenu.class,
						ObjectProperty.class)
				.newInstance(miwn.getNPC(), npcs, cm, requestorN)
				.setPosition(requester.get().getLayoutX() - gamePanel.getPlayer().getScreenX() + gamePanel.getPlayer().getX(),
						requester.get().getLayoutY() - gamePanel.getPlayer().getScreenY() + gamePanel.getPlayer().getY());
			else if (e.getSource() instanceof MenuItemWMOB miwn)
				miwn.getMob().getClass()
				.getDeclaredConstructor(miwn.getMob().getClass(), List.class, ContextMenu.class,
						ObjectProperty.class)
				.newInstance(miwn.getMob(), mobs, cm, requestorN)
				.setPosition(requester.get().getLayoutX() - gamePanel.getPlayer().getScreenX() + gamePanel.getPlayer().getX(),
						requester.get().getLayoutY() - gamePanel.getPlayer().getScreenY() + gamePanel.getPlayer().getY());
			else if (e.getSource() instanceof MenuItem mi && "add Texture".equals(mi.getText())) if (mi.getParentMenu() == mnpcs) {
				FileChooser fc = new FileChooser();
				fc.setInitialDirectory(new File("."));
				fc.getExtensionFilters().add(new ExtensionFilter(
						"A file containing an Image", "*.png", "*.gif"));
				File f = fc.showOpenDialog(cm.getScene().getWindow());
				if (f == null || !f.exists()) return;
				try {
					Path	p1	= f.toPath();
					Path	p2	= new File("./res/npc/" + f.getName()).toPath();
					Files.copy(p1, p2, StandardCopyOption.COPY_ATTRIBUTES, StandardCopyOption.REPLACE_EXISTING);
					System.out.println(p2);
					Image		img				= new Image(new FileInputStream(p2.toFile()));
					JsonObject	joN				= new JsonObject();
					JsonArray	requestedSize	= new JsonArray();
					requestedSize.add(new DoubleValue(img.getWidth()));
					requestedSize.add(new DoubleValue(img.getHeight()));
					joN.put("requestedSize", requestedSize);
					JsonObject textures = new JsonObject();
					textures.put("default", new StringValue(f.getName()));
					joN.put("textures", textures);
					JsonObject npcData = new JsonObject();
					joN.put("npcData", npcData);
					joN.put("type", new StringValue("NPC"));
					joN.put("fps", new DoubleValue(7d));
					JsonArray position = new JsonArray();
					position.add(new DoubleValue(
							requester.get().getLayoutX() - gamePanel.getPlayer().getScreenX() + gamePanel.getPlayer().getX()));
					position.add(new DoubleValue(
							requester.get().getLayoutY() - gamePanel.getPlayer().getScreenY() + gamePanel.getPlayer().getY()));
					joN.put("position", position);
					JsonArray originalSize = new JsonArray();
					originalSize.add(new DoubleValue(img.getWidth()));
					originalSize.add(new DoubleValue(img.getHeight()));
					joN.put("originalSize", originalSize);

					NPC n = new NPC(joN, gamePanel, npcs, cm, requestorN);
					mnpcs.getItems().remove(mi);
					ImageView lIV;
					if (n.isGif(n.getCurrentKey())) {
						lIV = new ImageView(n.getImages().get(n.getCurrentKey()).get(0));
						lIV.setFitWidth(16);
						lIV.setFitHeight(16);
					} else lIV = new ImageView(ImgUtil.resizeImage(n.getImages().get(n.getCurrentKey()).get(0),
							(int) n.getImages().get(n.getCurrentKey()).get(0).getWidth(),
							(int) n.getImages().get(n.getCurrentKey()).get(0).getHeight(), 48, 48));
					mnpcs.getItems()
					.add(new MenuItemWNPC(f.getName(),
							lIV,
							n));
					mnpcs.getItems().get(mnpcs.getItems().size() - 1).setOnAction(this::contextMenu);
					mnpcs.getItems().add(mi);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				System.out.println(f);
			} else if (mi.getParentMenu() == getMbuildings()) {
				FileChooser fc = new FileChooser();
				fc.setInitialDirectory(new File("."));
				fc.getExtensionFilters().add(new ExtensionFilter(
						"A file containing an Image", "*.png", "*.gif"));
				File f = fc.showOpenDialog(cm.getScene().getWindow());
				if (f == null || !f.exists()) return;
				try {
					Path	p1	= f.toPath();
					Path	p2	= new File("./res/building/" + f.getName()).toPath();
					Files.copy(p1, p2, StandardCopyOption.COPY_ATTRIBUTES, StandardCopyOption.REPLACE_EXISTING);
					System.out.println(p2);
					Image		img				= new Image(new FileInputStream(p2.toFile()));
					JsonObject	joB				= new JsonObject();
					JsonArray	requestedSize	= new JsonArray();
					requestedSize.add(new DoubleValue(img.getWidth()));
					requestedSize.add(new DoubleValue(img.getHeight()));
					joB.put("requestedSize", requestedSize);
					JsonObject textures = new JsonObject();
					textures.put("default", new StringValue(f.getName()));
					joB.put("textures", textures);
					JsonObject buildingData = new JsonObject();
					joB.put("buildingData", buildingData);
					joB.put("type", new StringValue("Building"));
					JsonArray position = new JsonArray();
					position.add(new DoubleValue(
							requester.get().getLayoutX() - gamePanel.getPlayer().getScreenX() + gamePanel.getPlayer().getX()));
					position.add(new DoubleValue(
							requester.get().getLayoutY() - gamePanel.getPlayer().getScreenY() + gamePanel.getPlayer().getY()));
					joB.put("position", position);
					JsonArray originalSize = new JsonArray();
					originalSize.add(new DoubleValue(img.getWidth()));
					originalSize.add(new DoubleValue(img.getHeight()));
					joB.put("originalSize", originalSize);

					Building b = new Building(joB, gamePanel, buildings, cm, requesterB);
					getMbuildings().getItems().remove(mi);
					ImageView lIV;
					if (b.isGif(b.getCurrentKey())) {
						lIV = new ImageView(b.getImages().get(b.getCurrentKey()).get(0));
						lIV.setFitWidth(16);
						lIV.setFitHeight(16);
					} else lIV = new ImageView(ImgUtil.resizeImage(b.getImages().get(b.getCurrentKey()).get(0),
							(int) b.getImages().get(b.getCurrentKey()).get(0).getWidth(),
							(int) b.getImages().get(b.getCurrentKey()).get(0).getHeight(), 48, 48));
					getMbuildings().getItems()
					.add(new MenuItemWBuilding(f.getName(),
							lIV,
							b));
					getMbuildings().getItems().get(getMbuildings().getItems().size() - 1).setOnAction(this::contextMenu);
					getMbuildings().getItems().add(mi);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				System.out.println(f);
			} else if (mi.getParentMenu() == mmobs) {
				FileChooser fc = new FileChooser();
				fc.setInitialDirectory(new File("."));
				fc.getExtensionFilters().add(new ExtensionFilter(
						"A file containing an Image", "*.png", "*.gif"));
				File f = fc.showOpenDialog(cm.getScene().getWindow());
				if (f == null || !f.exists()) return;
				try {
					Path	p1	= f.toPath();
					Path	p2	= new File("./res/npc/" + f.getName()).toPath();
					Files.copy(p1, p2, StandardCopyOption.COPY_ATTRIBUTES, StandardCopyOption.REPLACE_EXISTING);
					System.out.println(p2);
					Image		img				= new Image(new FileInputStream(p2.toFile()));
					JsonObject	joN				= new JsonObject();
					JsonArray	requestedSize	= new JsonArray();
					requestedSize.add(new DoubleValue(img.getWidth()));
					requestedSize.add(new DoubleValue(img.getHeight()));
					joN.put("requestedSize", requestedSize);
					JsonObject textures = new JsonObject();
					textures.put("default", new StringValue(f.getName()));
					joN.put("textures", textures);
					JsonObject npcData = new JsonObject();
					joN.put("npcData", npcData);
					joN.put("type", new StringValue("NPC"));
					joN.put("fps", new DoubleValue(7d));
					JsonArray position = new JsonArray();
					position.add(new DoubleValue(
							requester.get().getLayoutX() - gamePanel.getPlayer().getScreenX() + gamePanel.getPlayer().getX()));
					position.add(new DoubleValue(
							requester.get().getLayoutY() - gamePanel.getPlayer().getScreenY() + gamePanel.getPlayer().getY()));
					joN.put("position", position);
					JsonArray originalSize = new JsonArray();
					originalSize.add(new DoubleValue(img.getWidth()));
					originalSize.add(new DoubleValue(img.getHeight()));
					joN.put("originalSize", originalSize);

					MobRan n = new MobRan(joN, gamePanel, mobs, cm, requestorM);
					mmobs.getItems().remove(mi);
					ImageView lIV;
					if (n.isGif(n.getCurrentKey())) {
						lIV = new ImageView(n.getImages().get(n.getCurrentKey()).get(0));
						lIV.setFitWidth(16);
						lIV.setFitHeight(16);
					} else lIV = new ImageView(ImgUtil.resizeImage(n.getImages().get(n.getCurrentKey()).get(0),
							(int) n.getImages().get(n.getCurrentKey()).get(0).getWidth(),
							(int) n.getImages().get(n.getCurrentKey()).get(0).getHeight(), 16, 16));
					mmobs.getItems()
					.add(new MenuItemWMOB(f.getName(),
							new ImageView(ImgUtil.resizeImage(n.getImages().get(n.getCurrentKey()).get(0),
									(int) n.getImages().get(n.getCurrentKey()).get(0).getWidth(),
									(int) n.getImages().get(n.getCurrentKey()).get(0).getHeight(), 48, 48)),
							n));
					mmobs.getItems().get(mmobs.getItems().size() - 1).setOnAction(this::contextMenu);
					mmobs.getItems().add(mi);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				System.out.println(f);
			} else if (mi.getParentMenu() == mtiles) {
				FileChooser fc = new FileChooser();
				fc.setInitialDirectory(new File("."));
				fc.getExtensionFilters().add(new ExtensionFilter(
						"A file containing an Image", "*.png", "*.gif"));
				File f = fc.showOpenDialog(cm.getScene().getWindow());
				if (f == null || !f.exists()) return;
				try {
					Path	p1	= f.toPath();
					Path	p2	= new File("./res/" + getDir() + "/" + f.getName()).toPath();
					Files.copy(p1, p2, StandardCopyOption.COPY_ATTRIBUTES, StandardCopyOption.REPLACE_EXISTING);
					System.out.println(p2);
					Tile t = new Tile(f.getName(),
							"./res/" + getDir() + "/" + f.getName(),
							gamePanel);
					tiles.add(t);
					mtiles.getItems().remove(mi);
					mtiles.getItems()
					.add(new MenuItemWTile(f.getName(), new ImageView(ImgUtil.resizeImage(t.images[0],
							(int) t.images[0].getWidth(), (int) t.images[0].getHeight(), 48, 48)), t));
					mtiles.getItems().get(mtiles.getItems().size() - 1).setOnAction(this::contextMenu);
					mtiles.getItems().add(mi);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				System.out.println(f);
			}
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException ex) {
			ex.printStackTrace();
		}

	}

	/**
	 * Gets the context menu menus.
	 *
	 * @return the menus
	 */
	public Menu[] getMenus() {
		maps.getItems().clear();
		insel_k.getItems().clear();
		insel_m.getItems().clear();
		insel_g.getItems().clear();
		for (File f : new File("./res/maps").listFiles((dir, f) -> f.endsWith(".json"))) {
			String[]	sp	= f.getName().split("[.]");
			MenuItem	map	= new MenuItem(String.join(".", Arrays.copyOf(sp, sp.length - 1)));
			map.setStyle("-fx-font-size: 20;");
			map.setOnAction(ae -> gamePanel.setMap("./res/maps/" + map.getText() + ".json"));
			maps.getItems().add(map);
		}
		for (File f : new File("./res/maps/insel_k").listFiles((dir, f) -> f.endsWith(".json"))) {
			String[]	sp	= f.getName().split("[.]");
			MenuItem	map	= new MenuItem(String.join(".", Arrays.copyOf(sp, sp.length - 1)));
			map.setStyle("-fx-font-size: 20;");
			map.setOnAction(ae -> gamePanel.setMap("./res/maps/insel_k/" + map.getText() + ".json"));
			insel_k.getItems().add(map);
		}
		for (File f : new File("./res/maps/insel_m").listFiles((dir, f) -> f.endsWith(".json"))) {
			String[]	sp	= f.getName().split("[.]");
			MenuItem	map	= new MenuItem(String.join(".", Arrays.copyOf(sp, sp.length - 1)));
			map.setStyle("-fx-font-size: 20;");
			map.setOnAction(ae -> gamePanel.setMap("./res/maps/insel_m/" + map.getText() + ".json"));
			insel_m.getItems().add(map);
		}
		for (File f : new File("./res/maps/insel_g").listFiles((dir, f) -> f.endsWith(".json"))) {
			String[]	sp	= f.getName().split("[.]");
			MenuItem	map	= new MenuItem(String.join(".", Arrays.copyOf(sp, sp.length - 1)));
			map.setStyle("-fx-font-size: 20;");
			map.setOnAction(ae -> gamePanel.setMap("./res/maps/insel_g/" + map.getText() + ".json"));
			insel_g.getItems().add(map);
		}
		mtiles.getItems().sort((item1, item2) -> {
			if (item1 instanceof MenuItemWTile mi1) {
				if (item2 instanceof MenuItemWTile mi2) return mi1.getText().compareTo(mi2.getText());
				return -1;
			}
			return 1;
		});
		return new Menu[] {
				mtiles, mnpcs, getMbuildings(), mmobs, mextra
		};

	}

	/**
	 * Sets the map.
	 */
	public void setMap() {
		mtiles.getItems().clear();
		mnpcs.getItems().clear();
		mmobs.getItems().clear();
		getMbuildings().getItems().clear();
		mmobs.getItems().clear();

	}

	/**
	 * Gets the context menu.
	 *
	 * @return the cm
	 */
	public ContextMenu getCM() { return cm; }

	/**
	 * Gets the mbuildings.
	 *
	 * @return the mbuildings
	 */
	public Menu getMbuildings() { return mbuildings; }

	/**
	 * Update.
	 */
	public void update() {
		logic.setContextMenuShowing(cm.isShowing());

	}

}
