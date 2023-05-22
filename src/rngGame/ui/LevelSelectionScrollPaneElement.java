package rngGame.ui;

import java.io.*;
import java.util.stream.*;

import javafx.animation.FadeTransition;
import javafx.scene.image.*;
import javafx.util.Duration;
import rngGame.buildings.ContractsTable;
import rngGame.main.Text;
import rngGame.tile.Difficulty;

// TODO: Auto-generated Javadoc
/**
 * The Class LevelSelectionScrollPaneElement.
 */
public class LevelSelectionScrollPaneElement extends ScrollPaneElement {

	/** The lvls. */
	private static Image[] lvls = Stream.of(new File("./res/lvl/vorgeschlagen").listFiles())
			.sorted((a, b) -> Integer.compare(Integer.parseInt(a.getName().substring(3, a.getName().length() - 4)),
					Integer.parseInt(b.getName().substring(3, b.getName().length() - 4))))
			.map(t -> {
				try {
					return new FileInputStream(t);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				return null;
			}).filter(s -> s != null).map(Image::new).collect(Collectors.toList()).toArray(new Image[0]);

	/** The start button. */
	private final Button background, startButton;

	/** The U gbc. */
	private final Image UGbc;

	/** The floor 1. */
	private final ImageView floor1;

	/** The vlvl. */
	private final ImageView vlvl;

	/** The picture. */
	private final ImageView picture;

	/** The difficul. */
	private final ImageView difficul;







	/**
	 * Instantiates a new level selection scroll pane element.
	 *
	 * @param gamepanel the gamepanel
	 * @param ct the ct
	 * @param floor the floor
	 * @param lvlneu the lvlneu
	 * @param pic the pic
	 * @param dif the dif
	 */
	public LevelSelectionScrollPaneElement(GamePanel gamepanel, ContractsTable ct, String floor, int lvlneu, String pic, Difficulty dif) {
		super(new Button(gamepanel));
		background = getBackgroundElement();
		UGbc = ImgUtil.getScaledImage(gamepanel, "./res/Contractstuff/UGbackground.png");
		Image UGbc2 = ImgUtil.getScaledImage(gamepanel, "./res/Contractstuff/UGbackground2.png");
		setBackgroundImageToDefaullt();



		Image difE = ImgUtil.getScaledImage(gamepanel, "./res/Contractstuff/difE.png");
		Image difM = ImgUtil.getScaledImage(gamepanel, "./res/Contractstuff/difM.png");
		Image difH = ImgUtil.getScaledImage(gamepanel, "./res/Contractstuff/difH.png");

		difficul = new ImageView(difE);
		difficul.setVisible(false);

		vlvl = new ImageView(ImgUtil.resizeImage(lvls[lvlneu / 5], (int) lvls[lvlneu / 5].getWidth(), (int) lvls[lvlneu / 5].getHeight(),
				(int) (48 * gamepanel.getWindowDataHolder().scalingFactorX()), (int) (48 * gamepanel.getWindowDataHolder().scalingFactorY())));
		vlvl.setVisible(false);
		vlvl.setLayoutX(24 * gamepanel.getWindowDataHolder().scalingFactorX());
		vlvl.setLayoutY(30 * gamepanel.getWindowDataHolder().scalingFactorY());

		picture = new ImageView(ImgUtil.getScaledImage(gamepanel, "./res/Contractstuff/" + pic + ".png", 64, 64));
		picture.setDisable(true);

		floor1 = new ImageView();
		setFloor(gamepanel,floor);
		floor1.setDisable(true);



		// Start Button
		String	sButton		= "./res/Contractstuff/Startbutton.png";
		String	sButton2	= "./res/Contractstuff/Startbutton2.png";
		System.out.println(gamepanel + " " + gamepanel);
		startButton = new Button(sButton, gamepanel.getWindowDataHolder());
		startButton.setLayoutX(383 * gamepanel.getWindowDataHolder().scalingFactorX());
		startButton.setLayoutY(4 * gamepanel.getWindowDataHolder().scalingFactorY());
		startButton.setOnPressed(me -> {
			startButton.init(sButton2);
		});
		startButton.setOnReleased(me -> {
			startButton.init(sButton);
			ct.removeStuffs();
			gamepanel.setMap("./res/maps/Dungeon.json");
		});

		background.setOnReleased(me -> {
			ct.reloadUGtexture();
			vlvl.setVisible(true);
			background.setImage(UGbc2);
			FadeTransition st = new FadeTransition(Duration.millis(100), startButton);
			FadeTransition st2 = new FadeTransition(Duration.millis(100), ct.getInfos());
			st.setFromValue(0);
			st.setToValue(1);
			st.play();
			st2.setFromValue(0);
			st2.setToValue(1);
			st2.play();
			startButton.setVisible(true);
			ct.getInfos().setVisible(true);

			if(dif == Difficulty.MIDDLE) difficul.setImage(difM);
			if(dif == Difficulty.HARD) difficul.setImage(difH);
			if(dif == Difficulty.EASY) difficul.setImage(difE);

			gamepanel.getWindowDataHolder().setDifficulty(dif);
			difficul.setVisible(true);

		});

		// TODO make other stuffs

		getChildren().addAll(startButton, floor1, picture);

		floor1.setLayoutX(54 * gamepanel.getWindowDataHolder().scalingFactorX());
		floor1.setLayoutY(5 * gamepanel.getWindowDataHolder().scalingFactorY());
		picture.setLayoutX(7 * gamepanel.getWindowDataHolder().scalingFactorX());
		picture.setLayoutY(7 * gamepanel.getWindowDataHolder().scalingFactorY());

		difficul.setLayoutX(360 * gamepanel.getWindowDataHolder().scalingFactorX());
		difficul.setLayoutY(65 * gamepanel.getWindowDataHolder().scalingFactorY());
	}

	/**
	 * Gets the difficul.
	 *
	 * @return the difficul
	 */
	public ImageView getDifficul() {
		return difficul;
	}

	/**
	 * Gets the start button.
	 *
	 * @return the start button
	 */
	public Button getStartButton() { return startButton; }

	/**
	 * Gets the vlvl.
	 *
	 * @return the vlvl
	 */
	public ImageView getVlvl() { return vlvl; }

	/**
	 * Sets the background image to defaullt.
	 */
	public void setBackgroundImageToDefaullt() {
		background.setImage(UGbc);
	}

	/**
	 * Sets the floor.
	 *
	 * @param gamepanel the gamepanel
	 * @param floor the floor
	 */
	public void setFloor(GamePanel gamepanel,String floor) {
		Image floor1 = Text.getInstance().convertText(floor, 64);
		floor1 = ImgUtil.resizeImage(
				floor1, (int) floor1.getWidth(), (int) floor1.getHeight(),
				(int) (floor1.getWidth() * gamepanel.getWindowDataHolder().scalingFactorX()),
				(int) (floor1.getHeight() * gamepanel.getWindowDataHolder().scalingFactorY()));
		this.floor1.setImage(floor1);
	}

	/**
	 * Sets the lvlfalse.
	 */
	public void setLvlfalse() {
		vlvl.setVisible(false);
		difficul.setVisible(false);
	}

	/**
	 * Start bvisible false.
	 */
	public void startBvisibleFalse() {
		startButton.setVisible(false);
	}
}
