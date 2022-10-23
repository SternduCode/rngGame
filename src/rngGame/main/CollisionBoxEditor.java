package rngGame.main;

import java.io.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.stage.*;
import javafx.stage.FileChooser.ExtensionFilter;
import rngGame.tile.ImgUtil;

public class CollisionBoxEditor extends Application {

	private File collboxFile;
	private double posX, posY, movX, movY;
	private final double xf=1;
	private final double yf=1;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("CollisionBoxEditor");

		Pane p = new Pane();

		p.setPrefWidth(1280);
		p.setPrefHeight(720);

		Pane sidebar = new Pane();
		sidebar.setPrefWidth(60);
		sidebar.setPrefHeight(720);
		sidebar.setBackground(new Background(new BackgroundFill(Color.GREY.darker(), null, null)));
		Pane mainView = new Pane();
		mainView.setLayoutX(60);
		mainView.setPrefWidth(1280 - 60);
		mainView.setPrefHeight(720);

		ImageView iv = new ImageView();

		Button save = new Button("Save");
		save.setLayoutY(25);
		save.setPrefWidth(60);
		Button redo = new Button("Redo");
		redo.setLayoutY(50);
		redo.setPrefWidth(60);
		Button undo = new Button("Undo");
		undo.setLayoutY(75);
		undo.setPrefWidth(60);

		Polygon ply = new Polygon();
		ply.getPoints().addAll(10d, 10d, 15d, 15d, 20d, 30d, 50d, 40d);

		Polygon plyV = new Polygon();
		plyV.getPoints().addAll(ply.getPoints());
		plyV.setFill(Color.color(1, 0, 0, .35));

		Button open = new Button("Open");
		open.setPrefWidth(60);
		open.setOnAction(ae -> {
			FileChooser fc = new FileChooser();
			fc.setInitialDirectory(new File("./res/collisions"));
			fc.getExtensionFilters().add(new ExtensionFilter(
					"A file containing the collision box of something", "*.collisionbox"));
			File f = fc.showOpenDialog(primaryStage);
			if (f != null && f.getPath().contains("res" + File.separator + "collisions")) {
				int idx = f.getPath().lastIndexOf("res" + File.separator + "collisions");
				String subPath = f.getPath().substring(idx + ("res" + File.separator + "collisions").length() + 1,
						f.getPath().length() - ".collisionbox".length());

				System.out.println(subPath);
				File img = new File("./res/" + subPath + ".png");
				try {
					iv.setImage(new Image(new FileInputStream(img)));
					posX = 0;
					posY = 0;
					iv.setLayoutX(mainView.getWidth() / 2 - iv.getImage().getWidth() / 2);
					iv.setLayoutY(mainView.getHeight() / 2 - iv.getImage().getHeight() / 2);
					plyV.setLayoutX(iv.getLayoutX());
					plyV.setLayoutY(iv.getLayoutY());
					collboxFile = f;
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		});

		primaryStage.addEventFilter(ScrollEvent.SCROLL, e -> {
			if (e.isControlDown()) {
				int idx = collboxFile.getPath().lastIndexOf("res" + File.separator + "collisions");
				String subPath = collboxFile.getPath().substring(
						idx + ("res" + File.separator + "collisions").length() + 1,
						collboxFile.getPath().length() - ".collisionbox".length());
				File img = new File("./res/" + subPath + ".png");
				try {
					Image imgi = new Image(new FileInputStream(img));
					double xf = (int) (iv.getImage().getWidth() + iv.getImage().getWidth() * .005 * e.getDeltaY())
							/ imgi.getWidth(),
							yf = (int) (iv.getImage().getHeight() + iv.getImage().getHeight() * .005 * e.getDeltaY())
							/ imgi.getHeight();
					iv.setImage(ImgUtil.resizeImage(imgi, (int) imgi.getWidth(),
							(int) imgi.getHeight(),
							(int) (iv.getImage().getWidth() + iv.getImage().getWidth() * .005 * e.getDeltaY()),
							(int) (iv.getImage().getHeight() + iv.getImage().getHeight() * .005 * e.getDeltaY())));
					iv.setLayoutX(mainView.getWidth() / 2 - iv.getImage().getWidth() / 2 + movX);
					iv.setLayoutY(mainView.getHeight() / 2 - iv.getImage().getHeight() / 2 + movY);
					plyV.setLayoutX(iv.getLayoutX());
					plyV.setLayoutY(iv.getLayoutY());
					for (int i = 0; i < ply.getPoints().size(); i += 2) {
						plyV.getPoints().set(i, ply.getPoints().get(i) * xf);
						plyV.getPoints().set(i + 1, ply.getPoints().get(i + 1) * yf);
					}
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
			}
		});

		primaryStage.addEventHandler(MouseEvent.MOUSE_DRAGGED, e -> {
			if (e.isMiddleButtonDown()) {
				if (Math.abs(e.getSceneX() - posX) > 20) posX = e.getSceneX();
				if (Math.abs(e.getSceneY() - posY) > 20) posY = e.getSceneY();
				movX += e.getSceneX() - posX;
				System.out.println(e.getSceneX() - posX);
				posX = e.getSceneX();
				movY += e.getSceneY() - posY;
				System.out.println(e.getSceneY() - posY);
				posY = e.getSceneY();
				iv.setLayoutX(mainView.getWidth() / 2 - iv.getImage().getWidth() / 2 + movX);
				iv.setLayoutY(mainView.getHeight() / 2 - iv.getImage().getHeight() / 2 + movY);
				plyV.setLayoutX(iv.getLayoutX());
				plyV.setLayoutY(iv.getLayoutY());
			}
		});

		sidebar.getChildren().addAll(open, save, redo, undo);

		mainView.getChildren().addAll(iv, plyV);

		p.getChildren().addAll(sidebar, mainView);

		primaryStage.setScene(new Scene(p));

		primaryStage.show();

		p.getScene().heightProperty().addListener((obV, oldV, newV) -> {
			sidebar.setPrefHeight(newV.doubleValue());
			mainView.setPrefHeight(newV.doubleValue());
		});

		p.getScene().widthProperty().addListener((obV, oldV, newV) -> {
			mainView.setPrefWidth(newV.doubleValue() - 60);

		});
	}

}
