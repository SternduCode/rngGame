package rngGAME;

import java.util.List;
import buildings.Building;
import entity.Player;
import javafx.animation.*;
import javafx.scene.Group;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import tile.TileManager;

public class SpielPanel extends Pane {

	public final int Bg = 48;
	public final int BildS = 20;
	public final int BildH = 11;
	public final int SpielLaenge = Bg * BildS;
	public final int SpielHoehe = Bg * BildH;


	public final int maxWorldCol = 50;
	public final int maxWorldRow = 50;
	public final int worldWidth = Bg * maxWorldCol;
	public final int worldHeight = Bg * maxWorldRow;



	private final int FPS = 60;


	private final ImageView inv;

	private final Input keyH;
	private final Player player;
	private final TileManager tileM;
	private final Group buildingsGroup;
	private final List<Building> buildings;




	public SpielPanel(Input keyH) {
		setPrefSize(SpielLaenge, SpielHoehe);
		setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));

		this.keyH = keyH;

		player = new Player(this, getKeyH());

		tileM = new TileManager(this);

		buildings = tileM.getBuildingsFromMap();

		inv = new ImageView(new Image (getClass().getResourceAsStream("/res/GUI/Inv.png")));
		inv.setX(player.screenX - inv.getImage().getWidth() / 2 + 20);
		inv.setY(player.screenY - inv.getImage().getHeight() / 2);


		buildingsGroup = new Group();

		addBuildings();

		getChildren().addAll(tileM, buildingsGroup, player, inv);


	}


	public void addBuildings() {
		buildingsGroup.getChildren().addAll(buildings);
	}

	public Input getKeyH() {
		return keyH;
	}


	public Player getPlayer() { return player; }



	public void run() {
		update();

	}


	public void SST() {
		Timeline tl = new Timeline(
				new KeyFrame(Duration.millis(1000 / FPS),
						event -> {
							run();
						}));
		tl.setCycleCount(Animation.INDEFINITE);
		tl.play();
	}


	public void update() {

		player.update();

		tileM.update();

		for (Building b: buildings) b.update(player, this);

		if (keyH.tabPressed) inv.setVisible(true);
		else inv.setVisible(false);

	}

}
