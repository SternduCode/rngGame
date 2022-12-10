package rngGame.buildings;

import java.util.List;
import com.sterndu.json.JsonObject;
import javafx.animation.*;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Group;
import javafx.scene.control.ContextMenu;
import javafx.scene.image.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import rngGame.main.*;
import rngGame.tile.ImgUtil;
import rngGame.ui.MonsterSelction;

public class ContractsTable extends Building {

	private final Pane p1 = new Pane();
	private final Pane p2 = new Pane();
	private final Pane allPanes = new Pane();
	private final Pane infos = new Pane();
	private final Group ugSachen = new Group();
	private final Group ausGroup = new Group();
	private ImageView contractBackground;
	private ImageView contractSaturn;
	private ImageView contractNebel;
	private ImageView contractGalactus;
	private ImageView contractNova;

	private MonsterSelction ms;

	private ImageView hud;
	private ImageView hud2;
	private ImageView hud3;
	private ImageView hud4;

	private ImageView titlebanner;
	private ImageView titlebanner2;
	private ImageView titlebanner3;
	private ImageView titlebanner4;

	private ImageView text;

	private ImageView button_R;
	private ImageView button_L;

	private ImageView ausBackground;

	private ImageView ugBackground;
	private ImageView startButton;
	private ImageView lvlBorder;

	private ImageView ausXb;
	private int index = 0;
	private boolean iftest;
	private boolean inkreis;

	public ContractsTable(Building building, List<Building> buildings, ContextMenu cm,
			ObjectProperty<Building> requestorB) {
		super(building, buildings, cm, requestorB);
		init();
	}

	public ContractsTable(JsonObject building, GamePanel gp, List<Building> buildings, ContextMenu cm,
			ObjectProperty<Building> requestorB) {
		super(building, gp, buildings, cm, requestorB);
		init();
	}

	public ContractsTable(JsonObject building, GamePanel gp, String directory, List<Building> buildings, ContextMenu cm,
			ObjectProperty<Building> requestorB) {
		super(building, gp, directory, buildings, cm, requestorB);
		init();
	}

	private void init() {
		Image wi = ImgUtil.getScaledImage(gamepanel, "./res/Contractstuff/Mainbackground.png");
		Image saturn = ImgUtil.getScaledImage(gamepanel, "./res/Contractstuff/Saturn.png");
		Image nebel = ImgUtil.getScaledImage(gamepanel, "./res/Contractstuff/Nebel.png");
		Image galactus = ImgUtil.getScaledImage(gamepanel, "./res/Contractstuff/Galactus.png");
		Image nova = ImgUtil.getScaledImage(gamepanel, "./res/Contractstuff/nova.png");

		Image hudp = ImgUtil.getScaledImage(gamepanel, "./res/Contractstuff/Ping.gif");
		Image titlep = ImgUtil.getScaledImage(gamepanel, "./res/Contractstuff/title.png");
		Image ka = Text.getInstance().convertText("Underwater Caverns", 48);
		ka = ImgUtil.resizeImage(
				ka, (int) ka.getWidth(), (int) ka.getHeight(),
				(int) (ka.getWidth() * gamepanel.getScalingFactorX()),
				(int) (ka.getHeight() * gamepanel.getScalingFactorY()));

		Image ausbc = ImgUtil.getScaledImage(gamepanel, "./res/Contractstuff/MapAuswahlBackground.png");

		Image UGbc = ImgUtil.getScaledImage(gamepanel, "./res/Contractstuff/UGbackground.png");
		Image UGbc2 = ImgUtil.getScaledImage(gamepanel, "./res/Contractstuff/UGbackground2.png");
		Image sButton = ImgUtil.getScaledImage(gamepanel, "./res/Contractstuff/Startbutton.png");
		Image sButton2 = ImgUtil.getScaledImage(gamepanel, "./res/Contractstuff/Startbutton2.png");

		Image lvlb = ImgUtil.getScaledImage(gamepanel, "./res/lvl/richtig/LvLBorder.png");
		lvlb = ImgUtil.resizeImage(
				lvlb, (int) lvlb.getWidth(), (int) lvlb.getHeight(),
				(int) (96 * gamepanel.getScalingFactorX()),
				(int) (96 * gamepanel.getScalingFactorY()));

		Image ausX = ImgUtil.getScaledImage(gamepanel, "./res/Contractstuff/Xbutton.png");
		Image ausX2 = ImgUtil.getScaledImage(gamepanel, "./res/Contractstuff/XbuttonC.png");

		Image buttonR = ImgUtil.getScaledImage(gamepanel, "./res/Contractstuff/pfeilR.png");
		Image buttonL = ImgUtil.getScaledImage(gamepanel, "./res/Contractstuff/pfeilL.png");
		Image buttonRL = ImgUtil.getScaledImage(gamepanel, "./res/Contractstuff/pfeilRLeu.png");
		Image buttonLL = ImgUtil.getScaledImage(gamepanel, "./res/Contractstuff/pfeilLLeu.png");

		contractBackground = new ImageView(wi);
		contractBackground.setTranslateY(-1);
		contractBackground.setTranslateX(gamepanel.SpielLaenge / 2 * 3);

		contractSaturn = new ImageView(saturn);
		contractNebel = new ImageView(nebel);
		contractGalactus = new ImageView(galactus);
		contractNova = new ImageView(nova);

		text = new ImageView(ka);

		hud = new ImageView(hudp);
		hud.setScaleX(gamepanel.getScalingFactorX());
		hud.setScaleY(gamepanel.getScalingFactorY());
		hud.setOpacity(0);

		hud2 = new ImageView(hudp);
		hud2.setScaleX(gamepanel.getScalingFactorX());
		hud2.setScaleY(gamepanel.getScalingFactorY());
		hud2.setOpacity(0);

		hud3 = new ImageView(hudp);
		hud3.setScaleX(gamepanel.getScalingFactorX());
		hud3.setScaleY(gamepanel.getScalingFactorY());
		hud3.setOpacity(0);

		hud4 = new ImageView(hudp);
		hud4.setScaleX(gamepanel.getScalingFactorX());
		hud4.setScaleY(gamepanel.getScalingFactorY());
		hud4.setOpacity(0);

		titlebanner = new ImageView(titlep);
		titlebanner.setOpacity(0);
		titlebanner2 = new ImageView(titlep);
		titlebanner2.setOpacity(0);
		titlebanner3 = new ImageView(titlep);
		titlebanner3.setOpacity(0);
		titlebanner4 = new ImageView(titlep);
		titlebanner4.setOpacity(0);

		ausBackground = new ImageView(ausbc);

		ugBackground = new ImageView(UGbc);
		startButton = new ImageView(sButton);
		lvlBorder = new ImageView(lvlb);

		Image UG_1 = UGbc;
		Image UG_2 = UGbc2;

		ausXb = new ImageView(ausX);

		button_R = new ImageView(buttonR);
		button_L = new ImageView(buttonL);
		Image x1 = ausX;
		Image x2 = ausX2;
		Image brl = buttonRL;
		Image bll = buttonLL;
		Image br = buttonR;
		Image bl = buttonL;

		button_R.setOnMouseReleased(me -> {
			button_R.setImage(brl);
			TranslateTransition tt = new TranslateTransition(Duration.millis(1000), p1);
			TranslateTransition tth = new TranslateTransition(Duration.millis(1000), contractBackground);

			// tt.setFromX(gamepanel.SpielLaenge*-index);
			tt.setToX(-gamepanel.SpielLaenge - gamepanel.SpielLaenge * index);

			// tth.setFromX(gamepanel.SpielLaenge / 2 * -index + gamepanel.SpielLaenge);
			tth.setToX(gamepanel.SpielLaenge / 2 * -(index + 1) + gamepanel.SpielLaenge
					+ gamepanel.getScalingFactorX() * 24);

			new Thread(() -> {
				try {
					Thread.sleep(125);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				button_R.setImage(br);
			}).start();
			tt.play();
			tth.play();
			FadeTransition pin1 = new FadeTransition(Duration.millis(500), hud);
			FadeTransition pin2 = new FadeTransition(Duration.millis(500), hud2);
			FadeTransition pin3 = new FadeTransition(Duration.millis(500), hud3);
			FadeTransition pin4 = new FadeTransition(Duration.millis(500), hud4);
			FadeTransition tit1 = new FadeTransition(Duration.millis(1250), titlebanner);
			FadeTransition tit2 = new FadeTransition(Duration.millis(1250), titlebanner2);
			FadeTransition tit3 = new FadeTransition(Duration.millis(1250), titlebanner3);
			FadeTransition tit4 = new FadeTransition(Duration.millis(1250), titlebanner4);
			FadeTransition _tiw = new FadeTransition(Duration.millis(750), text);

			if (index == 0) {
				_tiw.setToValue(0);
				pin1.setToValue(0);
				tit1.setToValue(0);
				tit2.setToValue(1);
				pin2.setToValue(1);

				_tiw.play();
				pin1.play();
				tit1.play();
				tit2.play();
				pin2.play();
			}
			if (index == 1) {
				pin2.setToValue(0);
				tit2.setToValue(0);
				tit3.setToValue(1);
				pin3.setToValue(1);

				pin2.play();
				tit2.play();
				tit3.play();
				pin3.play();
			}
			if (index == 2) {
				pin3.setToValue(0);
				tit3.setToValue(0);
				tit4.setToValue(1);
				pin4.setToValue(1);

				pin3.play();
				tit3.play();
				tit4.play();
				pin4.play();
			}

			index++;
			button_L.setVisible(true);
			if (index >= 4) button_R.setVisible(false);
		});
		button_L.setOnMouseReleased(me -> {
			button_L.setImage(bll);
			TranslateTransition tt = new TranslateTransition(Duration.millis(1000), p1);
			TranslateTransition tth = new TranslateTransition(Duration.millis(1000), contractBackground);

			// tt.setFromX(gamepanel.SpielLaenge*-index);
			tt.setToX(gamepanel.SpielLaenge + gamepanel.SpielLaenge * -index);

			// tth.setFromX(gamepanel.SpielLaenge / 2 * -index + gamepanel.SpielLaenge);
			tth.setToX(gamepanel.SpielLaenge / 2 * -(index - 1) + gamepanel.SpielLaenge);

			new Thread(() -> {
				try {
					Thread.sleep(125);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				button_L.setImage(bl);
			}).start();
			tt.play();
			tth.play();
			FadeTransition pin1 = new FadeTransition(Duration.millis(500), hud);
			FadeTransition pin2 = new FadeTransition(Duration.millis(500), hud2);
			FadeTransition pin3 = new FadeTransition(Duration.millis(500), hud3);
			FadeTransition pin4 = new FadeTransition(Duration.millis(500), hud4);
			FadeTransition tit1 = new FadeTransition(Duration.millis(1250), titlebanner);
			FadeTransition tit2 = new FadeTransition(Duration.millis(1250), titlebanner2);
			FadeTransition tit3 = new FadeTransition(Duration.millis(1250), titlebanner3);
			FadeTransition tit4 = new FadeTransition(Duration.millis(1250), titlebanner4);
			FadeTransition _tiw = new FadeTransition(Duration.millis(750), text);

			if (index == 1) {
				pin2.setToValue(0);
				tit2.setToValue(0);
				tit1.setToValue(1);
				pin1.setToValue(1);
				_tiw.setToValue(1);

				_tiw.play();
				pin2.play();
				tit2.play();
				tit1.play();
				pin1.play();
			}
			if (index == 2) {
				pin3.setToValue(0);
				tit3.setToValue(0);
				tit2.setToValue(1);
				pin2.setToValue(1);

				pin3.play();
				tit3.play();
				tit2.play();
				pin2.play();
			}
			if (index == 3) {
				pin4.setToValue(0);
				tit4.setToValue(0);
				tit3.setToValue(1);
				pin3.setToValue(1);

				pin4.play();
				tit4.play();
				tit3.play();
				pin3.play();
			}
			index--;
			button_R.setVisible(true);
			if (index <= 0) button_L.setVisible(false);
		});

		ugBackground.setOnMouseReleased(me -> {
			ugBackground.setImage(UG_2);

			FadeTransition st = new FadeTransition(Duration.millis(100), startButton);
			FadeTransition st2 = new FadeTransition(Duration.millis(100), infos);
			st.setFromValue(0);
			st.setToValue(1);
			st.play();
			st2.setFromValue(0);
			st2.setToValue(1);
			st2.play();
			startButton.setVisible(true);
			infos.setVisible(true);

		});

		startButton.setOnMousePressed(me -> {
			startButton.setImage(sButton2);
		});
		startButton.setOnMouseReleased(me -> {
			startButton.setImage(sButton);
			ms = new MonsterSelction();// TODO add needed parameters
			allPanes.getChildren().add(ms);

		});

		///////////////////////
		contractSaturn.setOnMouseReleased(me -> {
			TranslateTransition tt = new TranslateTransition(Duration.millis(750), p1);
			TranslateTransition tth = new TranslateTransition(Duration.millis(750), contractBackground);

			tt.setToX(-gamepanel.SpielLaenge / 4 - gamepanel.SpielLaenge * index);
			tth.setToX(gamepanel.SpielLaenge / 2 * -(index + 1) + gamepanel.SpielLaenge / 1.5
					+ gamepanel.getScalingFactorX() * 24);

			ugBackground.setImage(UG_1);

			FadeTransition UG = new FadeTransition(Duration.millis(350), allPanes);
			UG.setFromValue(0);
			UG.setToValue(1);
			UG.play();

			FadeTransition _tiw = new FadeTransition(Duration.millis(1250), text);
			_tiw.setFromValue(0);
			_tiw.setToValue(1);

			allPanes.setVisible(true);
			infos.setVisible(false);
			startButton.setVisible(false);
			text.setVisible(true);
			button_R.setVisible(false);
			button_L.setVisible(false);
			_tiw.play();
			tt.play();
			tth.play();

		});
		///////////////////////
		contractNebel.setOnMouseReleased(me -> {
			TranslateTransition tt = new TranslateTransition(Duration.millis(750), p1);
			TranslateTransition tth = new TranslateTransition(Duration.millis(750), contractBackground);

			tt.setToX(-gamepanel.SpielLaenge / 4 - gamepanel.SpielLaenge * index);
			tth.setToX(gamepanel.SpielLaenge / 2 * -(index + 1) + gamepanel.SpielLaenge / 1.5
					+ gamepanel.getScalingFactorX() * 24);

			FadeTransition ft5 = new FadeTransition(Duration.millis(350), allPanes);
			ft5.setFromValue(0);
			ft5.setToValue(1);
			ft5.play();

			allPanes.setVisible(true);
			button_R.setVisible(false);
			button_L.setVisible(false);
			tt.play();
			tth.play();

		});
		///////////////////////
		contractGalactus.setOnMouseReleased(me -> {
			TranslateTransition tt = new TranslateTransition(Duration.millis(750), p1);
			TranslateTransition tth = new TranslateTransition(Duration.millis(750), contractBackground);

			tt.setToX(-gamepanel.SpielLaenge / 4 - gamepanel.SpielLaenge * index);
			tth.setToX(gamepanel.SpielLaenge / 2 * -(index + 1) + gamepanel.SpielLaenge / 1.5
					+ gamepanel.getScalingFactorX() * 24);

			FadeTransition ft5 = new FadeTransition(Duration.millis(350), allPanes);
			ft5.setFromValue(0);
			ft5.setToValue(1);
			ft5.play();

			allPanes.setVisible(true);
			button_R.setVisible(false);
			button_L.setVisible(false);

			tt.play();
			tth.play();

		});
		///////////////////////
		contractNova.setOnMouseReleased(me -> {
			TranslateTransition tt = new TranslateTransition(Duration.millis(750), p1);
			TranslateTransition tth = new TranslateTransition(Duration.millis(750), contractBackground);

			tt.setToX(-gamepanel.SpielLaenge / 4 - gamepanel.SpielLaenge * index);
			tth.setToX(gamepanel.SpielLaenge / 2 * -(index + 1) + gamepanel.SpielLaenge / 1.5
					+ gamepanel.getScalingFactorX() * 24);

			FadeTransition ft5 = new FadeTransition(Duration.millis(350), allPanes);
			ft5.setFromValue(0);
			ft5.setToValue(1);
			ft5.play();

			allPanes.setVisible(true);
			button_R.setVisible(false);
			button_L.setVisible(false);
			tt.play();
			tth.play();

		});
		///////////////////////
		ausXb.setOnMousePressed(me -> {
			ausXb.setImage(x2);
		});

		ausXb.setOnMouseReleased(me -> {

			ugBackground.setImage(UG_1);
			TranslateTransition tt = new TranslateTransition(Duration.millis(750), p1);
			TranslateTransition tth = new TranslateTransition(Duration.millis(750), contractBackground);

			tt.setToX(gamepanel.SpielLaenge + gamepanel.SpielLaenge * (-index - 1));
			tth.setToX(gamepanel.SpielLaenge / 2 * -index + gamepanel.SpielLaenge);

			FadeTransition UG = new FadeTransition(Duration.millis(200), allPanes);
			UG.setFromValue(1);
			UG.setToValue(0);
			UG.play();

			new Thread(() -> {
				try {
					Thread.sleep(350);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				allPanes.setVisible(false);
				ausXb.setImage(x1);
			}).start();

			button_R.setVisible(true);
			button_L.setVisible(true);

			FadeTransition pin1 = new FadeTransition(Duration.millis(0), hud);
			FadeTransition pin2 = new FadeTransition(Duration.millis(0), hud2);
			FadeTransition pin3 = new FadeTransition(Duration.millis(0), hud3);
			FadeTransition pin4 = new FadeTransition(Duration.millis(0), hud4);
			FadeTransition tit1 = new FadeTransition(Duration.millis(2000), titlebanner);
			FadeTransition tit2 = new FadeTransition(Duration.millis(2000), titlebanner2);
			FadeTransition tit3 = new FadeTransition(Duration.millis(2000), titlebanner3);
			FadeTransition tit4 = new FadeTransition(Duration.millis(2000), titlebanner4);

			pin1.setFromValue(0);
			pin1.setToValue(1);
			tit1.setFromValue(0);
			tit1.setToValue(1);
			tit2.setFromValue(0);
			tit2.setToValue(1);
			tit3.setFromValue(0);
			tit3.setToValue(1);
			tit4.setFromValue(0);
			tit4.setToValue(1);

			if (index == 0) {
				pin1.play();
				tit2.play();
				hud.setVisible(true);
				titlebanner.setVisible(true);
				titlebanner2.setVisible(true);

			}
			if (index == 1) {
				pin2.play();
				tit3.play();
				hud2.setVisible(true);
				titlebanner2.setVisible(true);
				titlebanner3.setVisible(true);

			}
			if (index == 2) {
				pin3.play();
				tit4.play();
				hud3.setVisible(true);
				titlebanner3.setVisible(true);
				titlebanner4.setVisible(true);
			}
			if (index == 4) {
				pin4.play();
				hud4.setVisible(true);
				titlebanner4.setVisible(true);
			}
			tt.play();
			tth.play();
		});
		/////////////////////
		// Hintergrund
		contractBackground.setX(gamepanel.getPlayer().getScreenX() - contractBackground.getImage().getWidth() / 2 + 48);
		contractBackground.setY(gamepanel.getPlayer().getScreenY() - contractBackground.getImage().getHeight() / 2 + 32);
		contractBackground.setVisible(false);
		gamepanel.getChildren().add(contractBackground);
		gamepanel.getChildren().add(p1);
		gamepanel.getChildren().add(button_R);
		gamepanel.getChildren().add(button_L);

		ausGroup.getChildren().addAll(ausBackground, ausXb);

		// contractNebel.setY(gamepanel.getPlayer().getScreenY() -
		// contractNebel.getImage().getHeight() / 2 + 32);

		contractNebel.setX(gamepanel.SpielLaenge);
		contractGalactus.setX(gamepanel.SpielLaenge * 2);
		contractNova.setX(gamepanel.SpielLaenge * 3);

		titlebanner2.setX(gamepanel.SpielLaenge);
		titlebanner3.setX(gamepanel.SpielLaenge * 2);
		titlebanner4.setX(gamepanel.SpielLaenge * 3);

		hud.setX(hud.getImage().getWidth() / 2 * gamepanel.getScalingFactorX() - hud.getImage().getWidth() / 2);
		hud2.setX(gamepanel.SpielLaenge + hud2.getImage().getWidth() / 2 * gamepanel.getScalingFactorX()
				- hud2.getImage().getWidth() / 2);
		hud3.setX(gamepanel.SpielLaenge * 2 + hud3.getImage().getWidth() / 2 * gamepanel.getScalingFactorX()
				- hud3.getImage().getWidth() / 2);
		hud4.setX(gamepanel.SpielLaenge * 3 + hud4.getImage().getWidth() / 2 * gamepanel.getScalingFactorX()
				- hud4.getImage().getWidth() / 2);
		hud.setY(hud.getImage().getHeight() / 2 * gamepanel.getScalingFactorY() - hud.getImage().getHeight() / 2);
		hud2.setY(hud2.getImage().getHeight() / 2 * gamepanel.getScalingFactorY() - hud2.getImage().getHeight() / 2);
		hud3.setY(hud3.getImage().getHeight() / 2 * gamepanel.getScalingFactorY() - hud3.getImage().getHeight() / 2);
		hud4.setY(hud4.getImage().getHeight() / 2 * gamepanel.getScalingFactorY() - hud4.getImage().getHeight() / 2);

		// add
		p1.setVisible(false);
		p1.getChildren().addAll(contractSaturn, contractNebel, contractGalactus, contractNova, hud, hud2, hud3, hud4,
				titlebanner, titlebanner2, titlebanner3, titlebanner4, text);

		text.setX(gamepanel.SpielLaenge / 2 - 164 * gamepanel.getScalingFactorX());
		text.setY(gamepanel.SpielHoehe / 4 + gamepanel.SpielHoehe / 2 + 20 * gamepanel.getScalingFactorY());

		text.setVisible(false);

		// buttons
		button_R.setVisible(false);
		button_L.setVisible(false);

		// P2
		ugSachen.getChildren().addAll(ugBackground, startButton);
		startButton.setX(383 * gamepanel.getScalingFactorX());
		startButton.setY(4 * gamepanel.getScalingFactorY());
		p2.getChildren().addAll(ugSachen);

		// Infos
		infos.setLayoutX(gamepanel.SpielLaenge / 2 + 11 * gamepanel.getScalingFactorX());
		infos.getChildren().add(lvlBorder);

		allPanes.setVisible(false);
		allPanes.getChildren().addAll(ausGroup, p2, infos);

		Input.getInstance().setKeyHandler("contractBackground", mod -> {

			if (inkreis) if (iftest = !iftest) {
				contractBackground.setTranslateX(gamepanel.SpielLaenge);
				gamepanel.setBlockUserInputs(true);
				contractBackground.setVisible(true);
				p1.setVisible(true);
				text.setVisible(true);
				hud.setVisible(true);
				titlebanner.setVisible(true);
				button_R.setVisible(true);
				titlebanner.setOpacity(1);
				hud.setOpacity(1);

				FadeTransition ft = new FadeTransition(Duration.millis(500), contractBackground);
				ft.setFromValue(0);
				ft.setToValue(1);
				ft.play();

				FadeTransition ft2 = new FadeTransition(Duration.millis(1000), p1);
				ft2.setFromValue(0);
				ft2.setToValue(1);
				ft2.play();

				FadeTransition ft3 = new FadeTransition(Duration.millis(1000), button_L);
				ft3.setFromValue(0);
				ft3.setToValue(1);
				ft3.play();

				FadeTransition ft4 = new FadeTransition(Duration.millis(1000), button_R);
				ft4.setFromValue(0);
				ft4.setToValue(1);
				ft4.play();

			} else {
				contractBackground.setVisible(false);
				p1.setVisible(false);
				text.setVisible(false);
				button_R.setVisible(false);
				button_L.setVisible(false);
				gamepanel.setBlockUserInputs(false);
				index = 0;
				p1.setTranslateX(0);
				contractBackground.setTranslateX(gamepanel.SpielLaenge);
			}
		}, KeyCode.ENTER, false);

		getMiscBoxHandler().put("table", (gpt, self) -> {
			inkreis = true;
			gamepanel.getBuildings().parallelStream().filter(b -> b.getTextureFiles().containsValue("CTischCircle.png"))
			.forEach(b -> {
				b.setCurrentKey("open");
			});
		});
	}

	@Override
	public void update(long milis) {
		inkreis = false;
		super.update(milis);
		if (!inkreis) {
			iftest = false;
			gamepanel.getBuildings().parallelStream().filter(b -> b.getTextureFiles().containsValue("CTischCircle.png"))
			.forEach(b -> {
				b.setCurrentKey("default");
			});
			contractBackground.setVisible(false);
			gamepanel.getChildren().remove(contractBackground);
			gamepanel.getChildren().remove(p1);
			gamepanel.getChildren().remove(allPanes);
			gamepanel.getChildren().remove(button_R);
			gamepanel.getChildren().remove(button_L);

		} else {
			contractBackground.setY(gamepanel.getPlayer().getScreenY() - contractBackground.getImage().getHeight() / 2
					+ gamepanel.getPlayer().getHeight() / 2);
			if (!gamepanel.getChildren().contains(contractBackground))
				gamepanel.getChildren().add(contractBackground);

			p1.setLayoutX(gamepanel.getPlayer().getScreenX() - gamepanel.SpielLaenge / 2
					+ gamepanel.getPlayer().getWidth() / 2);
			p1.setLayoutY(
					gamepanel.getPlayer().getScreenY() - p1.getHeight() / 2 + gamepanel.getPlayer().getHeight() / 2);
			if (!gamepanel.getChildren().contains(p1))
				gamepanel.getChildren().add(p1);
			///////////////////////////////////////////////
			if (!gamepanel.getChildren().contains(allPanes))
				gamepanel.getChildren().add(allPanes);

			p2.setLayoutX(gamepanel.SpielLaenge / 2 + 11 * gamepanel.getScalingFactorX());
			p2.setLayoutY(108 * gamepanel.getScalingFactorY());
			//////////////////////////////////////////////
			button_R.setLayoutX(gamepanel.SpielLaenge - button_R.getImage().getWidth());
			button_R.setLayoutY(gamepanel.getPlayer().getScreenY() - button_R.getImage().getHeight() / 2
					+ gamepanel.getPlayer().getHeight() / 2);
			if (!gamepanel.getChildren().contains(button_R))
				gamepanel.getChildren().add(button_R);

			button_L.setLayoutY(gamepanel.getPlayer().getScreenY() - button_L.getImage().getHeight() / 2
					+ gamepanel.getPlayer().getHeight() / 2);
			if (!gamepanel.getChildren().contains(button_L))
				gamepanel.getChildren().add(button_L);
		}
		if (ms != null) ms.update(milis);
	}
}
