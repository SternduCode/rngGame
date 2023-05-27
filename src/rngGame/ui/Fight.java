package rngGame.ui;

import java.util.Random;

import rngGame.entity.MobRan;
import rngGame.stats.*;


public class Fight {

	/** The demon mob. */
	private Demon eigenMob;

	/** The demon mob. */
	private final Demon demonMob;

	private final AnimatedImage auswahlBackground;

	private final AnimatedImage battleBackground;

	private final Button leaf;

	private final Button majyc;

	private final Button stych;

	private final GamePanel gamePanel;

	private boolean deathHasBeenTriggered;

	private final HealthBar eigenMobHealth, demonMobHealth;

	public Fight(GamePanel gamePanel) {

		this.gamePanel	= gamePanel;

		deathHasBeenTriggered = false;

		demonMob			= MobRan.MobGen(gamePanel);

		Demon[] demonArray = gamePanel.getTabMenu().getInventory().getDemons();
		eigenMob = demonArray[0];

		demonMobHealth	= new HealthBar(gamePanel.getWindowDataHolder(), demonMob);
		eigenMobHealth	= new HealthBar(gamePanel.getWindowDataHolder(), eigenMob);

		auswahlBackground	= new AnimatedImage(gamePanel.getWindowDataHolder());
		battleBackground	= new AnimatedImage(gamePanel.getWindowDataHolder());

		leaf	= new Button(gamePanel.getWindowDataHolder());
		majyc	= new Button(gamePanel.getWindowDataHolder());
		stych	= new Button(gamePanel.getWindowDataHolder());

		Random r = new Random();

		leaf.setOnPressed(e -> leaf.init("./res/fight/Leaf2.png"));
		leaf.setOnReleased(e -> {
			leaf.init("./res/fight/Leaf.gif");
			gamePanel.goIntoLoadingScreen();
			new Thread(() -> {
				try {
					gamePanel.getActionButton().setVisible(true);
					Thread.sleep(2000);
					gamePanel.setBlockUserInputs(false);
					gamePanel.goOutOfLoadingScreen();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}).start();
		});

		stych.setOnPressed(e -> stych.init("./res/fight/Stych2.png"));
		stych.setOnReleased(e -> {// TODO maybe only scale clored stuff
			stych.init("./res/fight/Stych.gif");
			demonMob.changeCurrentHp(-eigenMob.getAtk());

			int rr = r.nextInt(2) + 1;
			new Thread(() -> {
				try {
					Thread.sleep(5000);
					if (rr == 1) eigenMob.changeCurrentHp(-demonMob.getAtk());
					else sheeesh(demonMob, eigenMob);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}).start();
		});

		majyc.setOnPressed(e -> majyc.init("./res/fight/Majyc2.png"));
		majyc.setOnReleased(e -> {
			majyc.init("./res/fight/Majyc.png");

			int rr = r.nextInt(2) + 1;
			new Thread(() -> {
				try {
					Thread.sleep(5000);
					if (rr == 1) eigenMob.changeCurrentHp(-demonMob.getAtk());
					else sheeesh(demonMob, eigenMob);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}).start();
			sheeesh(eigenMob, demonMob);
		});

		scaleF11();

	}

	public void demonDead() {
		gamePanel.goIntoLoadingScreen();
		gamePanel.getTabMenu().getInventory().addDemon2current(demonMob);
		try {
			gamePanel.getActionButton().setVisible(true);
			Thread.sleep(2000);
			gamePanel.setBlockUserInputs(false);
			gamePanel.goOutOfLoadingScreen();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

	}

	public AnimatedImage getAuswahlBackground() { return auswahlBackground; }

	public AnimatedImage getBattleBackground() { return battleBackground; }

	public Demon getDemonMob() { return demonMob; }

	public HealthBar getDemonMobHealthBar() { return demonMobHealth; }

	public Demon getEigenMob() { return eigenMob; }

	public HealthBar getEigenMobHealthBar() { return eigenMobHealth; }

	public Button getLeaf() { return leaf; }

	public Button getMajyc() { return majyc; }

	public Button getStych() { return stych; }

	/**
	 * Scale F 11.
	 */
	public void scaleF11() {
		auswahlBackground.init("./res/fight/Auswahl.png");
		battleBackground.init("./res/fight/Fight.png");
		battleBackground.setImgRequestedWidth(gamePanel.getWindowDataHolder().gameWidth());
		battleBackground.setImgRequestedHeight(gamePanel.getWindowDataHolder().gameHeight());
		demonMob.getDemon().setReqWidth(256);
		demonMob.getDemon().setReqHeight(256);
		demonMob.getDemon().setLayoutX(gamePanel.getWindowDataHolder().gameWidth() / 1.5);
		demonMob.getDemon().setLayoutY(gamePanel.getWindowDataHolder().gameHeight() / 6.4);
		demonMob.getDemon().reloadTextures();

		eigenMob.getDemon().setReqWidth(256);
		eigenMob.getDemon().setReqHeight(256);
		eigenMob.getDemon().setLayoutX(gamePanel.getWindowDataHolder().gameWidth() / 13);
		eigenMob.getDemon().setLayoutY(gamePanel.getWindowDataHolder().gameHeight() / 6.4);
		eigenMob.getDemon().flipTextures();
		eigenMob.getDemon().reloadTextures();

		demonMobHealth.setLayoutX(gamePanel.getWindowDataHolder().gameWidth() / 2);
		eigenMobHealth.setLayoutX(gamePanel.getWindowDataHolder().gameWidth() / 4);

		leaf.init("./res/fight/Leaf.gif", 10);
		majyc.init("./res/fight/Majyc.gif", 10);
		stych.init("./res/fight/Stych.gif", 10);

	}

	/**
	 * Sheeesh.
	 */
	public void sheeesh(Demon eigenMob, Demon demonMob) {
		switch (eigenMob.getElement()) {
			case Fire -> {
				if (demonMob.getElement() == Element.Plant) demonMob.changeCurrentHp(-eigenMob.getAtk() * 2);
				else if (demonMob.getElement() == Element.Water || demonMob.getElement() == Element.Void
						|| demonMob.getElement() == Element.DimensionMaster)
					demonMob.changeCurrentHp((int) (-eigenMob.getAtk() * 0.5));
				else demonMob.changeCurrentHp(-eigenMob.getAtk());
			}
			case Water -> {
				if (demonMob.getElement() == Element.Fire) demonMob.changeCurrentHp(-eigenMob.getAtk() * 2);
				else if (demonMob.getElement() == Element.Plant || demonMob.getElement() == Element.Void
						|| demonMob.getElement() == Element.DimensionMaster)
					demonMob.changeCurrentHp((int) (-eigenMob.getAtk() * 0.5));
				else demonMob.changeCurrentHp(-eigenMob.getAtk());
			}
			case Plant -> {
				if (demonMob.getElement() == Element.Water) demonMob.changeCurrentHp(-eigenMob.getAtk() * 2);
				else if (demonMob.getElement() == Element.Fire || demonMob.getElement() == Element.Void
						|| demonMob.getElement() == Element.DimensionMaster)
					demonMob.changeCurrentHp((int) (-eigenMob.getAtk() * 0.5));
				else demonMob.changeCurrentHp(-eigenMob.getAtk());
			}
			case Light -> {
				if (demonMob.getElement() == Element.Fire || demonMob.getElement() == Element.Plant || demonMob.getElement() == Element.Water)
					demonMob.changeCurrentHp(-eigenMob.getAtk() * 2);
				else if (demonMob.getElement() == Element.Void || demonMob.getElement() == Element.DimensionMaster)
					demonMob.changeCurrentHp((int) (-eigenMob.getAtk() * 0.5));
				else if (demonMob.getElement() == Element.Shadow) demonMob.changeCurrentHp((int) (-eigenMob.getAtk() * 1.5));
				else demonMob.changeCurrentHp(-eigenMob.getAtk());
			}
			case Shadow -> {
				if (demonMob.getElement() == Element.Fire || demonMob.getElement() == Element.Plant || demonMob.getElement() == Element.Water)
					demonMob.changeCurrentHp(-eigenMob.getAtk() * 2);
				else if (demonMob.getElement() == Element.Void || demonMob.getElement() == Element.DimensionMaster)
					demonMob.changeCurrentHp((int) (-eigenMob.getAtk() * 0.5));
				else if (demonMob.getElement() == Element.Light) demonMob.changeCurrentHp((int) (-eigenMob.getAtk() * 1.5));
				else demonMob.changeCurrentHp(-eigenMob.getAtk());
			}
			case Void -> {
				if (demonMob.getElement() == Element.DimensionMaster) demonMob.changeCurrentHp((int) (-eigenMob.getAtk() * 0.5));
				else if (demonMob.getElement() == Element.Void) demonMob.changeCurrentHp(-eigenMob.getAtk());
				else demonMob.changeCurrentHp(-eigenMob.getAtk() * 2);
			}
			case DimensionMaster -> {
				if (demonMob.getElement() == Element.DimensionMaster) demonMob.changeCurrentHp(-eigenMob.getAtk());
				else if (demonMob.getElement() == Element.Fire || demonMob.getElement() == Element.Water || demonMob.getElement() == Element.Plant)
					demonMob.changeCurrentHp(-eigenMob.getAtk() * 3);
				else demonMob.changeCurrentHp(-eigenMob.getAtk() * 2);
			}
		}

	}

	public void update() {
		Demon[] demonArray = gamePanel.getTabMenu().getInventory().getDemons();

		if (demonMob.getCurrentHp() <= 0 && !deathHasBeenTriggered) {
			demonDead();
			deathHasBeenTriggered = true;
		}
		if (eigenMob.getCurrentHp() == 0) {
			for (int i = 0; i <= demonArray.length; i++) if (demonArray[i] != null && demonArray[i].getCurrentHp() != 0) {
				eigenMob = demonArray[i];
				break;
			}
			if (eigenMob.getCurrentHp() == 0) {
				System.out.println("You lost!!");
				gamePanel.goIntoLoadingScreen();
				try {
					gamePanel.getActionButton().setVisible(true);
					Thread.sleep(2000);
					gamePanel.setBlockUserInputs(false);
					gamePanel.goOutOfLoadingScreen();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

}
