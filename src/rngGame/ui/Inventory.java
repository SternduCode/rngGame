package rngGame.ui;

import java.io.FileNotFoundException;
import java.util.*;

import javafx.scene.image.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import rngGame.entity.*;
import rngGame.main.*;
import rngGame.stats.*;


// TODO: Auto-generated Javadoc
/**
 * The Class Inventory.
 */
public class Inventory {

	/**
	 * The Enum Tab.
	 */
	private enum Tab {

		/** The potion. */
		POTION,
		/** The monster. */
		MONSTER,
		/** The use. */
		USE,
		/** The armor. */
		ARMOR,
		/** The key. */
		KEY;
	}

	/** The Constant currentDemonIndex. */
	private final int currentDemonIndex = 0;

	/** The potion array. */
	private final Potion[] potionArray = new Potion[40];

	/** The gear array. */
	private final Gear[] gearArray = new Gear[40];

	/** The use array. */
	private final Use[] useArray = new Use[40];

	/** The key array. */
	private final Key[] keyArray = new Key[40];

	/** The demon array. */
	private final Demon[] demonArray = new Demon[40];

	/** The current demon array. */
	private final Demon[] currentDemonArray = new Demon[6];

	/** The current tab. */
	private final Tab currentTab = Tab.POTION;

	/** The name pane. */
	private Pane namePane;

	/** The element view. */
	private ImageView nameView, textBackroundCT, elementView, eIconView, itemOverlay, transp, itemSureBackround, itemSureBackroundm1,
	itemSureBackroundm2;

	/** The status. */
	private Pane status;

	/** The dgc view. */
	private ImageView hpView, atkView, resView, dgcView;

	/** The lvl view. */
	private ImageView expBar, expText, lvlView;

	/** The gamepanel. */
	private final GamePanel gamepanel;

	/** The inv backround. */
	private ImageView invBackround,ctov,ctcomp,comingView;

	/** The aus xb. */
	private Button ausXb, backButton, applyButton, removeButton;

	/** The inv slots. */
	private final ImageView[][] invSlots = new ImageView[10][4];

	/** The Item 4 slots. */
	private final ImageView[] Item4Slots = new ImageView[4];

	/** The potionbutton. */
	private Button potionbutton;

	/** The armorbutton. */
	private Button armorbutton;

	/** The usebutton. */
	private Button usebutton;

	/** The keybutton. */
	private Button keybutton;

	/** The idkbutton. */
	private Button idkbutton;

	/** The tabm. */
	private final TabMenu tabm;

	/** The ctb 6. */
	private Button ctb1, ctb2, ctb3, ctb4, ctb5, ctb6;

	/**
	 * Instantiates a new inventory.
	 *
	 * @param gamepanel the gamepanel
	 * @param tabm      the tabm
	 *
	 * @throws FileNotFoundException the file not found exception
	 */
	public Inventory(GamePanel gamepanel, TabMenu tabm) {
		this.gamepanel	= gamepanel;
		this.tabm		= tabm;
		Input.getInstance().setKeyHandler("Demons", mod -> {
			init();
		}, KeyCode.M, false);
		init();
	}

	/**
	 * Move from array to view.
	 */
	private void moveFromArrayToView() {
		Item[] data = switch (currentTab) {
			case POTION -> potionArray;
			case ARMOR -> gearArray;
			case KEY -> keyArray;
			case MONSTER -> null;
			case USE -> useArray;
		};
		if (data == null) {
			int k = 0;
			System.out.println(demonArray + " " + Arrays.toString(demonArray));
			for (int j = 0; j < invSlots[0].length; j++) for (ImageView[] invSlot : invSlots) {
				if (demonArray[k] != null) invSlot[j].setImage(getIconM(demonArray[k].getMobName(), demonArray[k].getElement()));
				else invSlot[j].setImage(null);
				k++;
			}
		} else {
			int k = 0;
			System.out.println(data + " " + Arrays.toString(data));
			for (int j = 0; j < invSlots[0].length; j++) for (ImageView[] invSlot : invSlots) {
				if (data[k] != null) invSlot[j].setImage(data[k].getImage(gamepanel));
				else invSlot[j].setImage(null);
				k++;
			}
		}
		for (int i = 0; i < Item4Slots.length; i++)
			if (getCurrentDemon() != null && getCurrentDemon().getItem4List()[i] != null)
				Item4Slots[i].setImage(getCurrentDemon().getItem4List()[i].getImage(gamepanel));
			else Item4Slots[i].setImage(null);

	}

	/**
	 * Adds the demon 2 current.
	 *
	 * @param m1 the m 1
	 * @return the int
	 */
	public int addDemon2current(Demon m1) {
		int i = findFirstNull(currentDemonArray);
		if(i!=-1) {
			currentDemonArray[i] = m1;
			switch (i) {
				case 0 -> ctb1.setImage(getIconM(m1.getMobName(), m1.getElement()));
				case 1 -> ctb2.setImage(getIconM(m1.getMobName(), m1.getElement()));
				case 2 -> ctb3.setImage(getIconM(m1.getMobName(), m1.getElement()));
				case 3 -> ctb4.setImage(getIconM(m1.getMobName(), m1.getElement()));
				case 4 -> ctb5.setImage(getIconM(m1.getMobName(), m1.getElement()));
				case 5 -> ctb6.setImage(getIconM(m1.getMobName(), m1.getElement()));
				default ->
				throw new IllegalArgumentException("Unexpected value: " + i);
			}
		} else {
			int j = findFirstNull(demonArray);
			if (j != -1)
				demonArray[j] = m1;
			i = -j;
		}
		return i;
	}

	/**
	 * Change demon.
	 *
	 * @param d the d
	 * @param i the i
	 * @param _j the j
	 * @param _i the i
	 */
	public void changeDemon(Demon d , int i,int _j,int _i) {
		Demon d2 = currentDemonArray[i];
		currentDemonArray[i] = d;
		demonArray[_j / 62 * 10 + _i / 62] = null;
		addDemon2current(d2);
		switch (i) {
			case 0 -> ctb1.setImage(getIconM(d.getMobName(), d.getElement()));
			case 1 -> ctb2.setImage(getIconM(d.getMobName(), d.getElement()));
			case 2 -> ctb3.setImage(getIconM(d.getMobName(), d.getElement()));
			case 3 -> ctb4.setImage(getIconM(d.getMobName(), d.getElement()));
			case 4 -> ctb5.setImage(getIconM(d.getMobName(), d.getElement()));
			case 5 -> ctb6.setImage(getIconM(d.getMobName(), d.getElement()));
			default ->
			throw new IllegalArgumentException("Unexpected value: " + i);
		}
		moveFromArrayToView();
	}

	/**
	 * End show.
	 */
	public void endShow() {
		setVisible(false);
		setDisable(true);

	}

	/**
	 * Find first null.
	 *
	 * @param itemArray the item array
	 *
	 * @return the int
	 */
	public int findFirstNull(Object[] itemArray) {
		for (int i = 0; i < itemArray.length; i++) if (itemArray[i] == null) return i;
		return -1;
	}

	/**
	 * Gets the current demon.
	 *
	 * @return the current demon
	 */
	public Demon getCurrentDemon() { return currentDemonArray[currentDemonIndex]; }

	/**
	 * Gets the demons.
	 *
	 * @return the demons
	 */
	public Demon[] getDemons() {
		return currentDemonArray;
	}



	/**
	 * Gets the icon M.
	 *
	 * @param name the name
	 * @param e the e
	 * @return the icon M
	 */
	public Image getIconM(String name ,Element e) {
		return ImgUtil.getScaledImage(gamepanel, "./res/icons/" + e.toString().toLowerCase() +"/"+name+".png");
	}

	/**
	 * Give item 2 monster.
	 *
	 * @param idx the idx
	 */
	public void giveItem2Monster(int idx) {
		int oldhp = getCurrentDemon().getMaxHp();
		Gear g = gearArray[idx];
		if (gearArray[idx] instanceof Helmet) {
			gearArray[idx]											= getCurrentDemon().getItem4List()[0];
			getCurrentDemon().getItem4List()[0]	= g;
		} else if (gearArray[idx] instanceof Harnish) {
			gearArray[idx]											= getCurrentDemon().getItem4List()[1];
			getCurrentDemon().getItem4List()[1]	= g;
		} else if (gearArray[idx] instanceof Pants) {
			gearArray[idx]											= getCurrentDemon().getItem4List()[2];
			getCurrentDemon().getItem4List()[2]	= g;
		} else if (gearArray[idx] instanceof Sword) {
			gearArray[idx]											= getCurrentDemon().getItem4List()[3];
			getCurrentDemon().getItem4List()[3]	= g;
		}
		System.out.println(getCurrentDemon().getCurrenthp()+" "+oldhp);
		if(getCurrentDemon().getCurrenthp() == oldhp) getCurrentDemon().changeCurrenthp(g.getHp());
		else getCurrentDemon().changeCurrenthp(0);

		int i = findFirstNull(gearArray);

		loop: while (i < gearArray.length) {
			for (int j = i; j < gearArray.length; j++) if (gearArray[j] != null) {
				gearArray[i]	= gearArray[j];
				gearArray[j]	= null;
				i				= j;
				continue loop;
			}
			break loop;
		}

		moveFromArrayToView();

		statsImages(currentDemonIndex);

	}

	/**
	 * Inits the.
	 */
	public void init() {
		getChildren().clear();

		status		= new Pane();
		namePane	= new Pane();

		// invBackround
		invBackround		= new ImageView(ImgUtil.getScaledImage(gamepanel, "./res/gui/InvBackround.png"));
		textBackroundCT		= new ImageView(ImgUtil.getScaledImage(gamepanel, "./res/gui/invNameTitle.png"));
		elementView			= new ImageView(ImgUtil.getScaledImage(gamepanel, "./res/gui/invElementFire.png"));
		itemOverlay			= new ImageView(ImgUtil.getScaledImage(gamepanel, "./res/gui/ItemAuswahlOverlay.png"));
		transp				= new ImageView(ImgUtil.getScaledImage(gamepanel, "./res/gui/blackTransparent.png"));
		itemSureBackround	= new ImageView(ImgUtil.getScaledImage(gamepanel, "./res/gui/itemSureBackround.png"));
		itemSureBackroundm1	= new ImageView(ImgUtil.getScaledImage(gamepanel, "./res/gui/itemSureBackroundm1.png"));
		itemSureBackroundm2	= new ImageView(ImgUtil.getScaledImage(gamepanel, "./res/gui/itemSureBackroundm2.png"));
		comingView 			= new ImageView(ImgUtil.getScaledImage(gamepanel, "./res/gui/InvComingsoon.png"));

		/////////////

		/////////////


		String ctt = "./res/gui/Temp.png";
		ctb1	= new Button(gamepanel.getVgp());
		ctb2	= new Button(gamepanel.getVgp());
		ctb3	= new Button(gamepanel.getVgp());
		ctb4	= new Button(gamepanel.getVgp());
		ctb5	= new Button(gamepanel.getVgp());
		ctb6	= new Button(gamepanel.getVgp());
		ctb1.setImage(ImgUtil.getScaledImage(gamepanel, ctt));
		ctb2.setImage(ImgUtil.getScaledImage(gamepanel, ctt));
		ctb3.setImage(ImgUtil.getScaledImage(gamepanel, ctt));
		ctb4.setImage(ImgUtil.getScaledImage(gamepanel, ctt));
		ctb5.setImage(ImgUtil.getScaledImage(gamepanel, ctt));
		ctb6.setImage(ImgUtil.getScaledImage(gamepanel, ctt));
		ctov = new ImageView(ImgUtil.getScaledImage(gamepanel, "./res/gui/tempOV.png"));


		Demon m1 = null;
		Random r = new Random();
		int zuz = r.nextInt(2)+1;
		switch (zuz) {
			case 1: {m1 = MobRan.makeMob(gamepanel.getVgp(), Element.Fire, "Booky");}
			case 2: {m1 = MobRan.makeMob(gamepanel.getVgp(), Element.Water, "Booky");}
			case 3: {m1 = MobRan.makeMob(gamepanel.getVgp(), Element.Plant, "Booky");}
		}

		addDemon2current(m1);

		// Xbutton
		String	ausX	= "./res/Contractstuff/Xbutton.png";
		String	ausX2	= "./res/Contractstuff/XbuttonC.png";
		ausXb = new Button(ausX, gamepanel.getVgp());

		// backbutton
		String	back1	= "./res/Contractstuff/MonsterBackbutton.png";
		String	back2	= "./res/Contractstuff/MonsterBackbuttonC.png";
		backButton = new Button(back1, gamepanel.getVgp());

		String	remove1	= "./res/Contractstuff/Removeb1.png";
		String	remove2	= "./res/Contractstuff/Removeb2.png";
		removeButton = new Button(remove1, gamepanel.getVgp());

		// backbutton
		String	apply1	= "./res/Contractstuff/ApplyButton1.png";
		String	apply2	= "./res/Contractstuff/ApplyButton2.png";
		applyButton = new Button(apply1, gamepanel.getVgp());

		// PotionButtonFeld
		String	potionButton2	= "./res/gui/InvButtonFolder/PotionButtonClosed.png";
		String	potionButton1	= "./res/gui/InvButtonFolder/PotionButtonOpen.png";
		potionbutton = new Button(potionButton1, gamepanel.getVgp());

		// ArmorButtonFeld
		String	armorButton2	= "./res/gui/InvButtonFolder/ArmorButtonClosed.png";
		String	armorButton1	= "./res/gui/InvButtonFolder/ArmorButtonOpen.png";
		armorbutton = new Button(armorButton2, gamepanel.getVgp());

		// UseButtonFeld
		String	useButton2	= "./res/gui/InvButtonFolder/UseButtonClosed.png";
		String	useButton1	= "./res/gui/InvButtonFolder/UseButtonOpen.png";
		usebutton = new Button(useButton2, gamepanel.getVgp());

		// KeyButtonFeld
		String	keyButton2	= "./res/gui/InvButtonFolder/KeyButtonClosed.png";
		String	keyButton1	= "./res/gui/InvButtonFolder/KeyButtonOpen.png";
		keybutton = new Button(keyButton2, gamepanel.getVgp());

		// IdkButtonFeld
		String	idkButton2	= "./res/gui/InvButtonFolder/IdkButtonClosed.png";
		String	idkButton1	= "./res/gui/InvButtonFolder/IdkButtonOpen.png";
		idkbutton = new Button(idkButton2, gamepanel.getVgp());

		Image stage1 = ImgUtil.getScaledImage(gamepanel, "./res/gui/lineaov1.png");
		Image stage2 = ImgUtil.getScaledImage(gamepanel, "./res/gui/lineaov2.png");
		Image stage3 = ImgUtil.getScaledImage(gamepanel, "./res/gui/lineaov3.png");
		Image stage4 = ImgUtil.getScaledImage(gamepanel, "./res/gui/lineaov4.png");
		Image stage5 = ImgUtil.getScaledImage(gamepanel, "./res/gui/lineaov5.png");
		Image stage6 = ImgUtil.getScaledImage(gamepanel, "./res/gui/lineaov6.png");
		ctcomp = new ImageView(stage1);

		Pane	p			= new Pane();
		Pane	p2			= new Pane();
		Pane	itemStuff	= new Pane();

		p.setLayoutX(8 * gamepanel.getWindowDataHolder().scalingFactorX());
		p.setLayoutY(268 * gamepanel.getWindowDataHolder().scalingFactorY());
		for (int i = 0; i < 620; i += 62)
			for (int j = 0; j < 247; j += 62) {
				ImageView iv = new ImageView();
				iv.setLayoutX(i * gamepanel.getWindowDataHolder().scalingFactorX());
				iv.setLayoutY(j * gamepanel.getWindowDataHolder().scalingFactorY());
				invSlots[i / 62][j / 62] = iv;
				int _i = i;
				int _j = j;

				iv.setOnMousePressed(me -> {
					itemOverlay.setLayoutX( (_i + 8) * gamepanel.getWindowDataHolder().scalingFactorX());
					itemOverlay.setLayoutY( (_j + 268) * gamepanel.getWindowDataHolder().scalingFactorY());
					itemOverlay.setVisible(true);
				});

				iv.setOnMouseReleased(me -> {
					ImageView itemShowcase = new ImageView(iv.getImage());

					itemShowcase.setLayoutX(329 * gamepanel.getWindowDataHolder().scalingFactorX());
					itemShowcase.setLayoutY(45 * gamepanel.getWindowDataHolder().scalingFactorY());
					//TODO					backButton.setLayoutX(302 * gamepanel.getWindowDataHolder().scalingFactorX());
					//TODO					backButton.setLayoutY( (125 + 64) * gamepanel.getWindowDataHolder().scalingFactorY());
					//TODO					applyButton.setLayoutX(302 * gamepanel.getWindowDataHolder().scalingFactorX());
					//TODO					applyButton.setLayoutY(125 * gamepanel.getWindowDataHolder().scalingFactorY());

					itemStuff.getChildren().clear();
					itemStuff.getChildren().add(itemShowcase);
					//TODO itemStuff.getChildren().addAll(backButton, applyButton);
					transp.setVisible(true);
					itemSureBackround.setVisible(true);
					itemStuff.setVisible(true);

					Object[] itemTestArray = switch (currentTab) {
						case POTION -> potionArray;
						case ARMOR -> gearArray;
						case KEY -> keyArray;
						case MONSTER -> demonArray;
						case USE -> useArray;
					};
					if (itemTestArray[_j / 62 * 10 + _i / 62] instanceof Potion pp) {
						ImageView hpView2, rarityView2;

						Image itemhpText = Text.getInstance().convertText("HP:" + pp.getHp(), 48);
						itemhpText = ImgUtil.resizeImage(
								itemhpText, (int) itemhpText.getWidth(), (int) itemhpText.getHeight(),
								(int) (itemhpText.getWidth() * gamepanel.getWindowDataHolder().scalingFactorX()),
								(int) (itemhpText.getHeight() * gamepanel.getWindowDataHolder().scalingFactorY()));

						Image itemRarityText = Text.getInstance().convertText("Rarity:" + pp.getRarity(), 48);
						itemRarityText = ImgUtil.resizeImage(
								itemRarityText, (int) itemRarityText.getWidth(), (int) itemRarityText.getHeight(),
								(int) (itemRarityText.getWidth() * gamepanel.getWindowDataHolder().scalingFactorX()),
								(int) (itemRarityText.getHeight() * gamepanel.getWindowDataHolder().scalingFactorY()));

						hpView2 = new ImageView(itemhpText);

						rarityView2 = new ImageView(itemRarityText);

						applyButton.setOnReleased(me2 -> {
							applyButton.init(apply1);
							hpView2.setVisible(false);
							rarityView2.setVisible(false);
							//TODO
							Button ctbi1, ctbi2, ctbi3, ctbi4, ctbi5, ctbi6;

							ctbi1 = new Button(gamepanel.getWindowDataHolder());
							ctbi2 = new Button(gamepanel.getWindowDataHolder());
							ctbi3 = new Button(gamepanel.getWindowDataHolder());
							ctbi4 = new Button(gamepanel.getWindowDataHolder());
							ctbi5 = new Button(gamepanel.getWindowDataHolder());
							ctbi6 = new Button(gamepanel.getWindowDataHolder());
							//							TODO ctbi1.setImage(getIconM(currentDemonArray[0].getMobName(), currentDemonArray[0].getElement()));
							//							TODO ctbi2.setImage(getIconM(currentDemonArray[1].getMobName(), currentDemonArray[1].getElement()));
							//							TODO ctbi3.setImage(getIconM(currentDemonArray[2].getMobName(), currentDemonArray[2].getElement()));
							//							TODO ctbi4.setImage(getIconM(currentDemonArray[3].getMobName(), currentDemonArray[3].getElement()));
							//							TODO ctbi5.setImage(getIconM(currentDemonArray[4].getMobName(), currentDemonArray[4].getElement()));
							//							TODO ctbi6.setImage(getIconM(currentDemonArray[5].getMobName(), currentDemonArray[5].getElement()));
							//							TODO ctbi1.setLayoutX(254);
							//							TODO ctbi1.setLayoutY(319);
							//							TODO ctbi2.setLayoutX(324);
							//							TODO ctbi2.setLayoutY(319);
							//							TODO ctbi3.setLayoutX(394);
							//							TODO ctbi3.setLayoutY(319);
							//							TODO ctbi4.setLayoutX(503);
							//							TODO ctbi4.setLayoutY(319);
							//							TODO ctbi5.setLayoutX(573);
							//							TODO ctbi5.setLayoutY(319);
							//							TODO ctbi6.setLayoutX(643);
							//							TODO ctbi6.setLayoutY(319);

							//TODO getChildren().addAll(ctbi1,ctbi2,ctbi3,ctbi4,ctbi5,ctbi6);


							//					TODO		ctbi1.setOnReleased(mee -> {
							//								transp.setVisible(false);
							//								currentDemonArray[0].changeCurrentHp(pp.getHp());
							//								potionArray[_j / 62 * 10 + _i / 62] = null;
							//								moveFromArrayToView();
							//								itemSureBackround.setVisible(false);
							//								itemSureBackroundm2.setVisible(false);
							//								ctbi1.setVisible(false);
							//								ctbi2.setVisible(false);
							//								ctbi3.setVisible(false);
							//								ctbi4.setVisible(false);
							//								ctbi5.setVisible(false);
							//								ctbi6.setVisible(false);
							//								itemStuff.setVisible(false);
							//								statsImages(currentDemonIndex);
							//							});
							//							ctbi2.setOnReleased(mee -> {
							//								transp.setVisible(false);
							//								currentDemonArray[1].changeCurrentHp(pp.getHp());
							//								potionArray[_j / 62 * 10 + _i / 62] = null;
							//								moveFromArrayToView();
							//								itemSureBackround.setVisible(false);
							//								itemSureBackroundm2.setVisible(false);
							//								ctbi1.setVisible(false);
							//								ctbi2.setVisible(false);
							//								ctbi3.setVisible(false);
							//								ctbi4.setVisible(false);
							//								ctbi5.setVisible(false);
							//								ctbi6.setVisible(false);
							//								itemStuff.setVisible(false);
							//								statsImages(currentDemonIndex);
							//
							//							});
							//							ctbi3.setOnReleased(mee -> {
							//								transp.setVisible(false);
							//								currentDemonArray[2].changeCurrentHp(pp.getHp());
							//								potionArray[_j / 62 * 10 + _i / 62] = null;
							//								moveFromArrayToView();
							//								itemSureBackround.setVisible(false);
							//								itemSureBackroundm2.setVisible(false);
							//								ctbi1.setVisible(false);
							//								ctbi2.setVisible(false);
							//								ctbi3.setVisible(false);
							//								ctbi4.setVisible(false);
							//								ctbi5.setVisible(false);
							//								ctbi6.setVisible(false);
							//								itemStuff.setVisible(false);
							//								statsImages(currentDemonIndex);
							//							});
							//							ctbi4.setOnReleased(mee -> {
							//								transp.setVisible(false);
							//								currentDemonArray[3].changeCurrentHp(pp.getHp());
							//								potionArray[_j / 62 * 10 + _i / 62] = null;
							//								moveFromArrayToView();
							//								itemSureBackround.setVisible(false);
							//								itemSureBackroundm2.setVisible(false);
							//								ctbi1.setVisible(false);
							//								ctbi2.setVisible(false);
							//								ctbi3.setVisible(false);
							//								ctbi4.setVisible(false);
							//								ctbi5.setVisible(false);
							//								ctbi6.setVisible(false);
							//								itemStuff.setVisible(false);
							//								statsImages(currentDemonIndex);
							//							});
							//							ctbi5.setOnReleased(mee -> {
							//								transp.setVisible(false);
							//								currentDemonArray[4].changeCurrentHp(pp.getHp());
							//								potionArray[_j / 62 * 10 + _i / 62] = null;
							//								moveFromArrayToView();
							//								itemSureBackround.setVisible(false);
							//								itemSureBackroundm2.setVisible(false);
							//								ctbi1.setVisible(false);
							//								ctbi2.setVisible(false);
							//								ctbi3.setVisible(false);
							//								ctbi4.setVisible(false);
							//								ctbi5.setVisible(false);
							//								ctbi6.setVisible(false);
							//								itemStuff.setVisible(false);
							//								statsImages(currentDemonIndex);
							//							});
							//							ctbi6.setOnReleased(mee -> {
							//								transp.setVisible(false);
							//								currentDemonArray[5].changeCurrentHp(pp.getHp());
							//								potionArray[_j / 62 * 10 + _i / 62] = null;
							//								moveFromArrayToView();
							//								itemSureBackround.setVisible(false);
							//								itemSureBackroundm2.setVisible(false);
							//								ctbi1.setVisible(false);
							//								ctbi2.setVisible(false);
							//								ctbi3.setVisible(false);
							//								ctbi4.setVisible(false);
							//								ctbi5.setVisible(false);
							//								ctbi6.setVisible(false);
							//								itemStuff.setVisible(false);
							//								statsImages(currentDemonIndex);
							//
							//							});
							//							applyButton.setVisible(false);
							//							backButton.setVisible(false);
							// TODO bis hier itemSureBackroundm2.setVisible(true);
						});



						hpView2.setLayoutX(25 * gamepanel.getWindowDataHolder().scalingFactorX());
						hpView2.setLayoutY( (16 + 48) * gamepanel.getWindowDataHolder().scalingFactorY());
						rarityView2.setLayoutX(25 * gamepanel.getWindowDataHolder().scalingFactorX());
						rarityView2.setLayoutY(16 * gamepanel.getWindowDataHolder().scalingFactorY());

						itemStuff.getChildren().addAll(hpView2, rarityView2);

						// TODO applyButton.setVisible(true);
						// TODO backButton.setVisible(true);

					} else if (itemTestArray[_j / 62 * 10 + _i / 62] instanceof Gear g) {
						ImageView hpView2, atkView2, resView2, dgcView2, rarityView2;

						//Gear g			= (Gear) itemTestArray[_j / 62 * 10 + _i / 62];
						Image itemhpText = Text.getInstance().convertText("HP:" + g.getHp(), 48);
						itemhpText = ImgUtil.resizeImage(
								itemhpText, (int) itemhpText.getWidth(), (int) itemhpText.getHeight(),
								(int) (itemhpText.getWidth() * gamepanel.getWindowDataHolder().scalingFactorX()),
								(int) (itemhpText.getHeight() * gamepanel.getWindowDataHolder().scalingFactorY()));

						Image itematkText = Text.getInstance().convertText("ATK:" + g.getAtk(), 48);
						itematkText = ImgUtil.resizeImage(
								itematkText, (int) itematkText.getWidth(), (int) itematkText.getHeight(),
								(int) (itematkText.getWidth() * gamepanel.getWindowDataHolder().scalingFactorX()),
								(int) (itematkText.getHeight() * gamepanel.getWindowDataHolder().scalingFactorY()));

						Image itemresText = Text.getInstance().convertText(String.format("RES:%.2f%%", g.getRes()), 48);
						itemresText = ImgUtil.resizeImage(
								itemresText, (int) itemresText.getWidth(), (int) itemresText.getHeight(),
								(int) (itemresText.getWidth() * gamepanel.getWindowDataHolder().scalingFactorX()),
								(int) (itemresText.getHeight() * gamepanel.getWindowDataHolder().scalingFactorY()));

						Image itemdgcText = Text.getInstance().convertText(String.format("DGC:%.2f%%", g.getDgc()), 48);
						itemdgcText = ImgUtil.resizeImage(
								itemdgcText, (int) itemdgcText.getWidth(), (int) itemdgcText.getHeight(),
								(int) (itemdgcText.getWidth() * gamepanel.getWindowDataHolder().scalingFactorX()),
								(int) (itemdgcText.getHeight() * gamepanel.getWindowDataHolder().scalingFactorY()));

						Image itemRarityText = Text.getInstance().convertText("Rarity:" + g.getRarity().toString().replace('_', ' '), 48);
						itemRarityText = ImgUtil.resizeImage(
								itemRarityText, (int) itemRarityText.getWidth(), (int) itemRarityText.getHeight(),
								(int) (itemRarityText.getWidth() * gamepanel.getWindowDataHolder().scalingFactorX()),
								(int) (itemRarityText.getHeight() * gamepanel.getWindowDataHolder().scalingFactorY()));

						applyButton.setOnReleased(me2 -> {
							applyButton.init(apply1);
							giveItem2Monster(_j / 62 * 10 + _i / 62);
							transp.setVisible(false);
							itemSureBackround.setVisible(false);
							itemStuff.setVisible(false);
						});

						hpView2	= new ImageView(itemhpText);
						atkView2 = new ImageView(itematkText);
						resView2 = new ImageView(itemresText);
						dgcView2 = new ImageView(itemdgcText);
						rarityView2 = new ImageView(itemRarityText);

						rarityView2.setLayoutX(25 * gamepanel.getWindowDataHolder().scalingFactorX());
						rarityView2.setLayoutY(16 * gamepanel.getWindowDataHolder().scalingFactorY());

						hpView2.setLayoutX(25 * gamepanel.getWindowDataHolder().scalingFactorX());
						hpView2.setLayoutY( (16 + 48) * gamepanel.getWindowDataHolder().scalingFactorY());

						atkView2.setLayoutX(25 * gamepanel.getWindowDataHolder().scalingFactorX());
						atkView2.setLayoutY( (16 + 96) * gamepanel.getWindowDataHolder().scalingFactorY());

						resView2.setLayoutX(25 * gamepanel.getWindowDataHolder().scalingFactorX());
						resView2.setLayoutY( (16 + 144) * gamepanel.getWindowDataHolder().scalingFactorY());

						dgcView2.setLayoutX(25 * gamepanel.getWindowDataHolder().scalingFactorX());
						dgcView2.setLayoutY( (16 + 192) * gamepanel.getWindowDataHolder().scalingFactorY());

						itemStuff.getChildren().addAll(hpView2, atkView2, resView2, dgcView2, rarityView2);

						// TODO applyButton.setVisible(true);
						// TODO backButton.setVisible(true);

					} else if (itemTestArray[_j / 62 * 10 + _i / 62] instanceof Demon d) {

						ImageView hpView3, atkView3, resView3, dgcView3, elementView3, nameView;
						//Demon d			= (Demon) itemTestArray[_j / 62 * 10 + _i / 62];
						Image itemhpText = Text.getInstance().convertText("HP:" + d.getCurrentHp()+"/"+d.getMaxHp(), 48);
						itemhpText = ImgUtil.resizeImage(
								itemhpText, (int) itemhpText.getWidth(), (int) itemhpText.getHeight(),
								(int) (itemhpText.getWidth() * gamepanel.getWindowDataHolder().scalingFactorX()),
								(int) (itemhpText.getHeight() * gamepanel.getWindowDataHolder().scalingFactorY()));

						Image itematkText = Text.getInstance().convertText("ATK:" + d.getAtk(), 48);
						itematkText = ImgUtil.resizeImage(
								itematkText, (int) itematkText.getWidth(), (int) itematkText.getHeight(),
								(int) (itematkText.getWidth() * gamepanel.getWindowDataHolder().scalingFactorX()),
								(int) (itematkText.getHeight() * gamepanel.getWindowDataHolder().scalingFactorY()));

						Image itemresText = Text.getInstance().convertText(String.format("RES:%.2f%%", d.getRes()), 48);
						itemresText = ImgUtil.resizeImage(
								itemresText, (int) itemresText.getWidth(), (int) itemresText.getHeight(),
								(int) (itemresText.getWidth() * gamepanel.getWindowDataHolder().scalingFactorX()),
								(int) (itemresText.getHeight() * gamepanel.getWindowDataHolder().scalingFactorY()));

						Image itemdgcText = Text.getInstance().convertText(String.format("DGC:%.2f%%", d.getDgc()), 48);
						itemdgcText = ImgUtil.resizeImage(
								itemdgcText, (int) itemdgcText.getWidth(), (int) itemdgcText.getHeight(),
								(int) (itemdgcText.getWidth() * gamepanel.getWindowDataHolder().scalingFactorX()),
								(int) (itemdgcText.getHeight() * gamepanel.getWindowDataHolder().scalingFactorY()));

						Image itemElementText = Text.getInstance().convertText("Element:" + d.getElement(), 48);
						itemElementText = ImgUtil.resizeImage(
								itemElementText, (int) itemElementText.getWidth(), (int) itemElementText.getHeight(),
								(int) (itemElementText.getWidth() * gamepanel.getWindowDataHolder().scalingFactorX()),
								(int) (itemElementText.getHeight() * gamepanel.getWindowDataHolder().scalingFactorY()));

						Image itemNameText = Text.getInstance().convertText("Name: "+d.getMobName(), 48);
						itemNameText = ImgUtil.resizeImage(
								itemNameText, (int) itemNameText.getWidth(), (int) itemNameText.getHeight(),
								(int) (itemNameText.getWidth() * gamepanel.getWindowDataHolder().scalingFactorX()),
								(int) (itemNameText.getHeight() * gamepanel.getWindowDataHolder().scalingFactorY()));

						hpView3	= new ImageView(itemhpText);
						atkView3 = new ImageView(itematkText);
						resView3 = new ImageView(itemresText);
						dgcView3 = new ImageView(itemdgcText);
						elementView3 = new ImageView(itemElementText);
						nameView = new ImageView(itemNameText);

						nameView.setLayoutX(25 * gamepanel.getWindowDataHolder().scalingFactorX());
						nameView.setLayoutY(20 * gamepanel.getWindowDataHolder().scalingFactorY());

						elementView3.setLayoutX(25 * gamepanel.getWindowDataHolder().scalingFactorX());
						elementView3.setLayoutY(20+32 * gamepanel.getWindowDataHolder().scalingFactorY());

						hpView3.setLayoutX(25 * gamepanel.getWindowDataHolder().scalingFactorX());
						hpView3.setLayoutY(20+ (32 + 32) * gamepanel.getWindowDataHolder().scalingFactorY());

						atkView3.setLayoutX(25 * gamepanel.getWindowDataHolder().scalingFactorX());
						atkView3.setLayoutY(20+ (32 + 64) * gamepanel.getWindowDataHolder().scalingFactorY());

						resView3.setLayoutX(25 * gamepanel.getWindowDataHolder().scalingFactorX());
						resView3.setLayoutY(20+ (32 + 96) * gamepanel.getWindowDataHolder().scalingFactorY());

						dgcView3.setLayoutX(25 * gamepanel.getWindowDataHolder().scalingFactorX());
						dgcView3.setLayoutY(20+ (32 + 128) * gamepanel.getWindowDataHolder().scalingFactorY());

						itemStuff.getChildren().addAll(hpView3, atkView3, resView3, dgcView3, elementView3, nameView);

						// TODO applyButton.setVisible(true);
						// TODO backButton.setVisible(true);

						applyButton.setOnReleased(me2 -> {
							applyButton.init(apply1);

							elementView3.setVisible(false);
							atkView3.setVisible(false);
							resView3.setVisible(false);
							dgcView3.setVisible(false);
							hpView3.setVisible(false);

							Button ctbi1, ctbi2, ctbi3, ctbi4, ctbi5, ctbi6;
							//TODO tiles add water
							ctbi1 = new Button(gamepanel.getWindowDataHolder());
							ctbi2 = new Button(gamepanel.getWindowDataHolder());
							ctbi3 = new Button(gamepanel.getWindowDataHolder());
							ctbi4 = new Button(gamepanel.getWindowDataHolder());
							ctbi5 = new Button(gamepanel.getWindowDataHolder());
							ctbi6 = new Button(gamepanel.getWindowDataHolder());
							// TODO ctbi1.setImage(getIconM(currentDemonArray[0].getMobName(), currentDemonArray[0].getElement()));
							// TODO ctbi2.setImage(getIconM(currentDemonArray[1].getMobName(), currentDemonArray[1].getElement()));
							// TODO ctbi3.setImage(getIconM(currentDemonArray[2].getMobName(), currentDemonArray[2].getElement()));
							// TODO ctbi4.setImage(getIconM(currentDemonArray[3].getMobName(), currentDemonArray[3].getElement()));
							// TODO ctbi5.setImage(getIconM(currentDemonArray[4].getMobName(), currentDemonArray[4].getElement()));
							// TODO ctbi6.setImage(getIconM(currentDemonArray[5].getMobName(), currentDemonArray[5].getElement()));
							// TODO ctbi1.setLayoutX(254);
							// TODO ctbi1.setLayoutY(319);
							// TODO ctbi2.setLayoutX(324);
							// TODO ctbi2.setLayoutY(319);
							// TODO ctbi3.setLayoutX(394);
							// TODO ctbi3.setLayoutY(319);
							// TODO ctbi4.setLayoutX(503);
							// TODO ctbi4.setLayoutY(319);
							// TODO ctbi5.setLayoutX(573);
							// TODO ctbi5.setLayoutY(319);
							// TODO ctbi6.setLayoutX(643);
							// TODO ctbi6.setLayoutY(319);

							// TODO getChildren().addAll(ctbi1,ctbi2,ctbi3,ctbi4,ctbi5,ctbi6);


							//			TODO				ctbi1.setOnReleased(mee -> {
							//								changeDemon(d,0,_j,_i);
							//								transp.setVisible(false);
							//								itemSureBackround.setVisible(false);
							//								itemSureBackroundm1.setVisible(false);
							//								ctbi1.setVisible(false);
							//								ctbi2.setVisible(false);
							//								ctbi3.setVisible(false);
							//								ctbi4.setVisible(false);
							//								ctbi5.setVisible(false);
							//								ctbi6.setVisible(false);
							//								itemStuff.setVisible(false);
							//								statsImages(currentDemonIndex);
							//							});
							//							ctbi2.setOnReleased(mee -> {
							//								changeDemon(d,1,_j,_i);
							//								transp.setVisible(false);
							//								itemSureBackround.setVisible(false);
							//								itemSureBackroundm1.setVisible(false);
							//								ctbi1.setVisible(false);
							//								ctbi2.setVisible(false);
							//								ctbi3.setVisible(false);
							//								ctbi4.setVisible(false);
							//								ctbi5.setVisible(false);
							//								ctbi6.setVisible(false);
							//								itemStuff.setVisible(false);
							//								statsImages(currentDemonIndex);
							//							});
							//							ctbi3.setOnReleased(mee -> {
							//								changeDemon(d,2,_j,_i);
							//								transp.setVisible(false);
							//								itemSureBackround.setVisible(false);
							//								itemSureBackroundm1.setVisible(false);
							//								ctbi1.setVisible(false);
							//								ctbi2.setVisible(false);
							//								ctbi3.setVisible(false);
							//								ctbi4.setVisible(false);
							//								ctbi5.setVisible(false);
							//								ctbi6.setVisible(false);
							//								itemStuff.setVisible(false);
							//								statsImages(currentDemonIndex);
							//							});
							//							ctbi4.setOnReleased(mee -> {
							//								changeDemon(d,3,_j,_i);
							//								transp.setVisible(false);
							//								itemSureBackround.setVisible(false);
							//								itemSureBackroundm1.setVisible(false);
							//								ctbi1.setVisible(false);
							//								ctbi2.setVisible(false);
							//								ctbi3.setVisible(false);
							//								ctbi4.setVisible(false);
							//								ctbi5.setVisible(false);
							//								ctbi6.setVisible(false);
							//								itemStuff.setVisible(false);
							//								statsImages(currentDemonIndex);
							//							});
							//							ctbi5.setOnReleased(mee -> {
							//								changeDemon(d,4,_j,_i);
							//								transp.setVisible(false);
							//								itemSureBackround.setVisible(false);
							//								itemSureBackroundm1.setVisible(false);
							//								ctbi1.setVisible(false);
							//								ctbi2.setVisible(false);
							//								ctbi3.setVisible(false);
							//								ctbi4.setVisible(false);
							//								ctbi5.setVisible(false);
							//								ctbi6.setVisible(false);
							//								itemStuff.setVisible(false);
							//								statsImages(currentDemonIndex);
							//							});
							//							ctbi6.setOnReleased(mee -> {
							//								changeDemon(d,5,_j,_i);
							//								transp.setVisible(false);
							//								itemSureBackround.setVisible(false);
							//								itemSureBackroundm1.setVisible(false);
							//								ctbi1.setVisible(false);
							//								ctbi2.setVisible(false);
							//								ctbi3.setVisible(false);
							//								ctbi4.setVisible(false);
							//								ctbi5.setVisible(false);
							//								ctbi6.setVisible(false);
							//								itemStuff.setVisible(false);
							//								statsImages(currentDemonIndex);
							//
							//							});
							//							applyButton.setVisible(false);
							//		TODO bis hier					backButton.setVisible(false);
							itemSureBackroundm1.setVisible(true);
						});

					}

					backButton.setOnPressed(me2 -> {
						backButton.init(back2);
					});

					backButton.setOnReleased(me2 -> {
						backButton.init(back1);
						transp.setVisible(false);
						itemSureBackround.setVisible(false);
						itemStuff.setVisible(false);
					});

					applyButton.setOnPressed(me2 -> {
						applyButton.init(apply2);
					});
				});
				p.getChildren().add(iv);
			}

		for (int i = 0; i < Item4Slots.length; i++) {
			int _i = i;
			ImageView iv = new ImageView();
			if (getCurrentDemon() != null && getCurrentDemon().getItem4List()[i] != null) {
				Image iv2 = getCurrentDemon().getItem4List()[i].getImage(gamepanel);
				iv.setImage(iv2);
			}
			Item4Slots[i] = iv;
			iv.setOnMousePressed(me -> {
				itemOverlay.setLayoutX(6 * gamepanel.getWindowDataHolder().scalingFactorX());
				itemOverlay.setLayoutY(iv.getLayoutY() + 6 * gamepanel.getWindowDataHolder().scalingFactorY());
				itemOverlay.setVisible(true);
			});

			iv.setOnMouseReleased(me -> {
				ImageView itemShowcase = new ImageView(iv.getImage());

				itemShowcase.setLayoutX(329 * gamepanel.getWindowDataHolder().scalingFactorX());
				itemShowcase.setLayoutY(45 * gamepanel.getWindowDataHolder().scalingFactorY());
				backButton.setLayoutX(302 * gamepanel.getWindowDataHolder().scalingFactorX());
				backButton.setLayoutY( (125 + 64) * gamepanel.getWindowDataHolder().scalingFactorY());
				removeButton.setLayoutX(302 * gamepanel.getWindowDataHolder().scalingFactorX());
				removeButton.setLayoutY(125 * gamepanel.getWindowDataHolder().scalingFactorY());

				itemStuff.getChildren().clear();
				itemStuff.getChildren().add(itemShowcase);
				itemStuff.getChildren().addAll(backButton,removeButton);
				transp.setVisible(true);
				itemSureBackround.setVisible(true);
				itemStuff.setVisible(true);


				ImageView hpView2, atkView2, resView2, dgcView2, rarityView2;

				Gear g	= getCurrentDemon().getItem4List()[_i];
				Image itemhpText = Text.getInstance().convertText("HP:" + g.getHp(), 48);
				itemhpText = ImgUtil.resizeImage(
						itemhpText, (int) itemhpText.getWidth(), (int) itemhpText.getHeight(),
						(int) (itemhpText.getWidth() * gamepanel.getWindowDataHolder().scalingFactorX()),
						(int) (itemhpText.getHeight() * gamepanel.getWindowDataHolder().scalingFactorY()));

				Image itematkText = Text.getInstance().convertText("ATK:" + g.getAtk(), 48);
				itematkText = ImgUtil.resizeImage(
						itematkText, (int) itematkText.getWidth(), (int) itematkText.getHeight(),
						(int) (itematkText.getWidth() * gamepanel.getWindowDataHolder().scalingFactorX()),
						(int) (itematkText.getHeight() * gamepanel.getWindowDataHolder().scalingFactorY()));

				Image itemresText = Text.getInstance().convertText(String.format("RES:%.2f%%", g.getRes()), 48);
				itemresText = ImgUtil.resizeImage(
						itemresText, (int) itemresText.getWidth(), (int) itemresText.getHeight(),
						(int) (itemresText.getWidth() * gamepanel.getWindowDataHolder().scalingFactorX()),
						(int) (itemresText.getHeight() * gamepanel.getWindowDataHolder().scalingFactorY()));

				Image itemdgcText = Text.getInstance().convertText(String.format("DGC:%.2f%%", g.getDgc()), 48);
				itemdgcText = ImgUtil.resizeImage(
						itemdgcText, (int) itemdgcText.getWidth(), (int) itemdgcText.getHeight(),
						(int) (itemdgcText.getWidth() * gamepanel.getWindowDataHolder().scalingFactorX()),
						(int) (itemdgcText.getHeight() * gamepanel.getWindowDataHolder().scalingFactorY()));

				Image itemRarityText = Text.getInstance().convertText("Rarity:" + g.getRarity().toString().replace('_', ' '), 48);
				itemRarityText = ImgUtil.resizeImage(
						itemRarityText, (int) itemRarityText.getWidth(), (int) itemRarityText.getHeight(),
						(int) (itemRarityText.getWidth() * gamepanel.getWindowDataHolder().scalingFactorX()),
						(int) (itemRarityText.getHeight() * gamepanel.getWindowDataHolder().scalingFactorY()));

				hpView2	= new ImageView(itemhpText);
				atkView2 = new ImageView(itematkText);
				resView2 = new ImageView(itemresText);
				dgcView2 = new ImageView(itemdgcText);
				rarityView2 = new ImageView(itemRarityText);

				rarityView2.setLayoutX(25 * gamepanel.getWindowDataHolder().scalingFactorX());
				rarityView2.setLayoutY(16 * gamepanel.getWindowDataHolder().scalingFactorY());

				hpView2.setLayoutX(25 * gamepanel.getWindowDataHolder().scalingFactorX());
				hpView2.setLayoutY( (16 + 48) * gamepanel.getWindowDataHolder().scalingFactorY());

				atkView2.setLayoutX(25 * gamepanel.getWindowDataHolder().scalingFactorX());
				atkView2.setLayoutY( (16 + 96) * gamepanel.getWindowDataHolder().scalingFactorY());

				resView2.setLayoutX(25 * gamepanel.getWindowDataHolder().scalingFactorX());
				resView2.setLayoutY( (16 + 144) * gamepanel.getWindowDataHolder().scalingFactorY());

				dgcView2.setLayoutX(25 * gamepanel.getWindowDataHolder().scalingFactorX());
				dgcView2.setLayoutY( (16 + 192) * gamepanel.getWindowDataHolder().scalingFactorY());

				itemStuff.getChildren().addAll(hpView2, atkView2, resView2, dgcView2, rarityView2);

				backButton.setVisible(true);
				removeButton.setVisible(true);

				backButton.setOnPressed(me2 -> {
					backButton.init(back2);
				});

				backButton.setOnReleased(me2 -> {
					backButton.init(back1);
					transp.setVisible(false);
					itemSureBackround.setVisible(false);
					itemStuff.setVisible(false);
				});

				removeButton.setOnPressed(me2 -> {
					removeButton.init(remove2);
				});

				removeButton.setOnReleased(me2 -> {
					removeButton.init(remove1);
					int z = findFirstNull(gearArray);
					if(z != -1) {
						gearArray[z] = getCurrentDemon().getItem4List()[_i];
						getCurrentDemon().getItem4List()[_i] = null;
						getCurrentDemon().changeCurrenthp(0);
						moveFromArrayToView();
						statsImages(currentDemonIndex);
					}
					transp.setVisible(false);
					itemSureBackround.setVisible(false);
					itemStuff.setVisible(false);
				});

			});

			p2.getChildren().add(iv);
		}

		Item4Slots[1].setLayoutY( (61 + 3) * gamepanel.getWindowDataHolder().scalingFactorY());
		Item4Slots[2].setLayoutY( (122 + 6) * gamepanel.getWindowDataHolder().scalingFactorY());
		Item4Slots[3].setLayoutY( (183 + 8) * gamepanel.getWindowDataHolder().scalingFactorY());

		// System.out.println(getCurrentDemon().getItem4List()[3].toString());




		p2.setLayoutX(6 * gamepanel.getWindowDataHolder().scalingFactorX());
		p2.setLayoutY(6 * gamepanel.getWindowDataHolder().scalingFactorY());

		getChildren().add(invBackround);

		elementView	= new ImageView(showElementbr(getCurrentDemon().getElement()));
		eIconView	= new ImageView(showElementIcon(getCurrentDemon().getElement()));
		eIconView.setLayoutX(785 * gamepanel.getWindowDataHolder().scalingFactorX());
		eIconView.setLayoutY(100 * gamepanel.getWindowDataHolder().scalingFactorY());
		getChildren().add(eIconView);
		getChildren().add(elementView);


		nameView = new ImageView();
		nameView.setLayoutX(100 * gamepanel.getWindowDataHolder().scalingFactorX());
		nameView.setLayoutY(9 * gamepanel.getWindowDataHolder().scalingFactorY());
		namePane.getChildren().addAll(textBackroundCT, nameView);

		getChildren().add(namePane);

		hpView	= new ImageView();
		atkView	= new ImageView();
		resView	= new ImageView();
		dgcView	= new ImageView();
		lvlView	= new ImageView();
		expText	= new ImageView();
		expBar	= new ImageView();

		statsImages(0);

		expBar.setLayoutX(-30 * gamepanel.getWindowDataHolder().scalingFactorX());
		// expBar.setLayoutY(5*gamepanel.getWindowDataHolder().scalingFactorY());
		expText.setLayoutX(290 * gamepanel.getWindowDataHolder().scalingFactorX());
		expText.setLayoutY(17 * gamepanel.getWindowDataHolder().scalingFactorY());

		getChildren().add(status);
		status.getChildren().addAll(hpView, atkView, resView, dgcView, expBar, expText, lvlView);

		lvlView.setLayoutX( (10 + 32) * gamepanel.getWindowDataHolder().scalingFactorX());
		lvlView.setLayoutY( (15 + 32) * gamepanel.getWindowDataHolder().scalingFactorY());
		hpView.setLayoutX( (10 + 50) * gamepanel.getWindowDataHolder().scalingFactorX());
		hpView.setLayoutY( (15 + 64) * gamepanel.getWindowDataHolder().scalingFactorY());
		atkView.setLayoutX( (10 + 32) * gamepanel.getWindowDataHolder().scalingFactorX());
		atkView.setLayoutY( (15 + 64 + 32) * gamepanel.getWindowDataHolder().scalingFactorY());
		resView.setLayoutX( (10 + 32) * gamepanel.getWindowDataHolder().scalingFactorX());
		resView.setLayoutY( (15 + 64 + 32 * 2) * gamepanel.getWindowDataHolder().scalingFactorY());
		dgcView.setLayoutX( (10 + 32) * gamepanel.getWindowDataHolder().scalingFactorX());
		dgcView.setLayoutY( (15 + 64 + 32 * 3) * gamepanel.getWindowDataHolder().scalingFactorY());

		status.setLayoutX( (gamepanel.getVgp().getGameWidth() / 2 + 10) * gamepanel.getWindowDataHolder().scalingFactorX());
		status.setLayoutY(10 * gamepanel.getWindowDataHolder().scalingFactorY());

		getChildren().add(ausXb);

		// TODO fix f11

		getChildren().addAll(potionbutton, armorbutton, usebutton, keybutton, idkbutton, comingView);
		comingView.setVisible(false);
		getChildren().addAll(ctcomp,ctb1,ctb2,ctb3,ctb4,ctb5,ctb6,ctov);

		ctov.setLayoutX(768);
		ctov.setLayoutY(283);
		ctb1.setLayoutX(768);
		ctb1.setLayoutY(283);
		ctb2.setLayoutX(844);
		ctb2.setLayoutY(283);
		ctb3.setLayoutX(735);
		ctb3.setLayoutY(362);
		ctb4.setLayoutX(877);
		ctb4.setLayoutY(362);
		ctb5.setLayoutX(768);
		ctb5.setLayoutY(441);
		ctb6.setLayoutX(844);
		ctb6.setLayoutY(441);


		getChildren().add(itemOverlay);
		itemOverlay.setVisible(false);

		getChildren().addAll(p, p2);

		getChildren().addAll(transp, itemSureBackround, itemSureBackroundm1, itemSureBackroundm2, itemStuff);
		transp.setVisible(false);
		itemSureBackround.setVisible(false);
		itemSureBackroundm1.setVisible(false);
		itemSureBackroundm2.setVisible(false);

		itemStuff.setLayoutX(240 * gamepanel.getWindowDataHolder().scalingFactorX());
		itemStuff.setLayoutY(130 * gamepanel.getWindowDataHolder().scalingFactorY());
		itemStuff.setVisible(false);
		setVisible(false);

		ausXb.setOnPressed(me -> {
			ausXb.init(ausX2);
		});

		ausXb.setOnReleased(me -> {
			ausXb.init(ausX);
			tabm.closeTabm(true);
			new Thread(() -> {
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				endShow();
			}).start();

		});

		potionbutton.setOnReleased(me -> {
			potionbutton.init(potionButton1);
			armorbutton.init(armorButton2);
			usebutton.init(useButton2);
			keybutton.init(keyButton2);
			idkbutton.init(idkButton2);
			comingView.setVisible(false);
			currentTab = Tab.POTION;
			moveFromArrayToView();
		});

		armorbutton.setOnReleased(me -> {
			potionbutton.init(potionButton2);
			armorbutton.init(armorButton1);
			usebutton.init(useButton2);
			keybutton.init(keyButton2);
			idkbutton.init(idkButton2);
			comingView.setVisible(false);
			currentTab = Tab.ARMOR;
			moveFromArrayToView();
		});

		usebutton.setOnReleased(me -> {
			potionbutton.init(potionButton2);
			armorbutton.init(armorButton2);
			usebutton.init(useButton1);
			keybutton.init(keyButton2);
			idkbutton.init(idkButton2);
			comingView.setVisible(true);
			currentTab = Tab.USE;
			moveFromArrayToView();
		});

		keybutton.setOnReleased(me -> {
			potionbutton.init(potionButton2);
			armorbutton.init(armorButton2);
			usebutton.init(useButton2);
			keybutton.init(keyButton1);
			idkbutton.init(idkButton2);
			comingView.setVisible(true);
			currentTab = Tab.KEY;
			moveFromArrayToView();
		});

		idkbutton.setOnReleased(me -> {
			potionbutton.init(potionButton2);
			armorbutton.init(armorButton2);
			usebutton.init(useButton2);
			keybutton.init(keyButton2);
			idkbutton.init(idkButton1);
			comingView.setVisible(false);
			currentTab = Tab.MONSTER;
			moveFromArrayToView();
		});

		ctb1.setOnReleased(me -> {
			ctov.setLayoutX(ctb1.getLayoutX());
			ctov.setLayoutY(ctb1.getLayoutY());
			ctcomp.setImage(stage1);
			statsImages(0);
		});


		ctb2.setOnReleased(me -> {
			if(currentDemonArray[1]!=null) {
				ctov.setLayoutX(ctb2.getLayoutX());
				ctov.setLayoutY(ctb2.getLayoutY());
				ctcomp.setImage(stage2);
				statsImages(1);
			}
		});

		ctb3.setOnReleased(me -> {
			if(currentDemonArray[2]!=null) {
				ctov.setLayoutX(ctb3.getLayoutX());
				ctov.setLayoutY(ctb3.getLayoutY());
				ctcomp.setImage(stage3);
				statsImages(2);
			}
		});

		ctb4.setOnReleased(me -> {
			if(currentDemonArray[3]!=null) {
				ctov.setLayoutX(ctb4.getLayoutX());
				ctov.setLayoutY(ctb4.getLayoutY());
				ctcomp.setImage(stage4);
				statsImages(3);
			}
		});

		ctb5.setOnReleased(me -> {
			if(currentDemonArray[4]!=null) {
				ctov.setLayoutX(ctb5.getLayoutX());
				ctov.setLayoutY(ctb5.getLayoutY());
				ctcomp.setImage(stage5);
				statsImages(4);
			}
		});

		ctb6.setOnReleased(me -> {
			if(currentDemonArray[5]!=null) {
				ctov.setLayoutX(ctb6.getLayoutX());
				ctov.setLayoutY(ctb6.getLayoutY());
				ctcomp.setImage(stage6);
				statsImages(5);
			}
		});

	}

	/**
	 * Item to inventory.
	 *
	 * @param item the item
	 */
	public void itemToInventory(Item item) {
		System.out.println(item);
		if (item instanceof Potion p1) {
			int x = findFirstNull(potionArray);
			if (x != -1) potionArray[x] = p1;

		} else if (item instanceof Gear gear) {
			int x = findFirstNull(gearArray);
			if (x != -1) gearArray[x] = gear;

		} else if (item instanceof Use u1) {
			int x = findFirstNull(useArray);
			if (x != -1) useArray[x] = u1;

		} else if (item instanceof Key k1) {
			int x = findFirstNull(keyArray);
			if (x != -1) keyArray[x] = k1;

		} else System.err.println("UNEXPECTED TYPE OF ITEM ABTREIBUNG FEHLGESCHLAGEN!!!");

	}

	/**
	 * Scale F 11.
	 */
	public void scaleF11() { init(); }

	/**
	 * Show.
	 */
	public void show() {
		moveFromArrayToView();
		setVisible(true);
		setDisable(false);
	}

	/**
	 * Show elementbr.
	 *
	 * @param e the e
	 *
	 * @return the image
	 */
	public Image showElementbr(Element e) {
		Image test = ImgUtil.getScaledImage(gamepanel, "./res/gui/invElementFire.png");
		if (e == Element.Fire) {
			Image firebr = ImgUtil.getScaledImage(gamepanel, "./res/gui/invElementFire.png");
			test = firebr;
		} else if (e == Element.Water) {
			Image waterbr = ImgUtil.getScaledImage(gamepanel, "./res/gui/invElementWater.png");
			test = waterbr;
		} else if (e == Element.Plant) {
			Image plantbr = ImgUtil.getScaledImage(gamepanel, "./res/gui/invElementPlant.png");
			test = plantbr;
		} else if (e == Element.Shadow) {
			Image shadowbr = ImgUtil.getScaledImage(gamepanel, "./res/gui/invElementShadow.png");
			test = shadowbr;
		} else if (e == Element.Light) {
			Image lightbr = ImgUtil.getScaledImage(gamepanel, "./res/gui/invElementLight.png");
			test = lightbr;
		} else if (e == Element.DimensionMaster) {
			Image lightbr = ImgUtil.getScaledImage(gamepanel, "./res/gui/invElementWorld_Ender.png");
			test = lightbr;
		} else {
			Image voidbr = ImgUtil.getScaledImage(gamepanel, "./res/gui/invElementVoid.png");
			test = voidbr;
		}
		return test;

	}

	/**
	 * Show element icon.
	 *
	 * @param e the e
	 *
	 * @return the image
	 */
	public Image showElementIcon(Element e) {
		Image test = ImgUtil.getScaledImage(gamepanel, "./res/gui/IconFire.png");
		if (e == Element.Fire) {
			Image firebr = ImgUtil.getScaledImage(gamepanel, "./res/gui/IconFire.png");
			test = firebr;
		} else if (e == Element.Water) {
			Image waterbr = ImgUtil.getScaledImage(gamepanel, "./res/gui/IconWater.png");
			test = waterbr;
		} else if (e == Element.Plant) {
			Image plantbr = ImgUtil.getScaledImage(gamepanel, "./res/gui/IconPlant.png");
			test = plantbr;
		} else if (e == Element.Shadow) {
			Image shadowbr = ImgUtil.getScaledImage(gamepanel, "./res/gui/IconShadow.png");
			test = shadowbr;
		} else if (e == Element.Light) {
			Image lightbr = ImgUtil.getScaledImage(gamepanel, "./res/gui/IconLight.png");
			test = lightbr;
		} else if (e == Element.DimensionMaster) {
			Image lightbr = ImgUtil.getScaledImage(gamepanel, "./res/gui/IconWorld_Ender.png");
			test = lightbr;
		} else {
			Image voidbr = ImgUtil.getScaledImage(gamepanel, "./res/gui/IconVoid.png");
			test = voidbr;
		}
		return test;

	}

	/**
	 * Show X pbr.
	 *
	 * @return the image
	 */
	public Image showXPbr() {
		int		crExp		= getCurrentDemon().getCurrentExp();
		double	_1prozent	= getCurrentDemon().getMaxExp() / 100.0;
		Image	test;

		if (crExp <= _1prozent * 10) {
			Image img = ImgUtil.getScaledImage(gamepanel, "./res/gui/XPBar1.1.png");
			test = img;
		} else if (crExp <= _1prozent * 20) {
			Image img = ImgUtil.getScaledImage(gamepanel, "./res/gui/XPBar1.2.png");
			test = img;
		} else if (crExp <= _1prozent * 30) {
			Image img = ImgUtil.getScaledImage(gamepanel, "./res/gui/XPBar1.3.png");
			test = img;
		} else if (crExp <= _1prozent * 40) {
			Image img = ImgUtil.getScaledImage(gamepanel, "./res/gui/XPBar2.1.png");
			test = img;
		} else if (crExp <= _1prozent * 50) {
			Image img = ImgUtil.getScaledImage(gamepanel, "./res/gui/XPBar2.2.png");
			test = img;
		} else if (crExp <= _1prozent * 60) {
			Image img = ImgUtil.getScaledImage(gamepanel, "./res/gui/XPBar2.3.png");
			test = img;
		} else if (crExp <= _1prozent * 70) {
			Image img = ImgUtil.getScaledImage(gamepanel, "./res/gui/XPBar3.1.png");
			test = img;
		} else if (crExp <= _1prozent * 80) {
			Image img = ImgUtil.getScaledImage(gamepanel, "./res/gui/XPBar3.2.png");
			test = img;
		} else {
			Image img = ImgUtil.getScaledImage(gamepanel, "./res/gui/XPBar3.3.png");
			test = img;
		}
		return test;

	}

	/**
	 * Stats images.
	 *
	 * @param demonIndex the demon index
	 */
	public void statsImages(int demonIndex) {

		int idx = getChildren().indexOf(getCurrentDemon().getDemon());

		currentDemonIndex = demonIndex;

		if (idx == -1) {
			idx = getChildren().indexOf(getChildren().stream().filter(e -> e instanceof MonsterNPC).findFirst().orElseGet(() -> null));
			if (idx != -1) getChildren().set(idx, getCurrentDemon().getDemon());
			else getChildren().add(getCurrentDemon().getDemon());

		} else getChildren().set(idx, getCurrentDemon().getDemon());

		getCurrentDemon().getDemon().setReqHeight(192);
		getCurrentDemon().getDemon().setReqWidth(192);
		getCurrentDemon().getDemon().reloadTextures();
		getCurrentDemon().getDemon().setLayoutX(180 * gamepanel.getWindowDataHolder().scalingFactorX());
		getCurrentDemon().getDemon().setLayoutY(50 * gamepanel.getWindowDataHolder().scalingFactorX());

		Image hpText1 = Text.getInstance().convertText("HP:" + getCurrentDemon().getCurrentHp()+"/"+getCurrentDemon().getMaxHp(), 48);
		hpText1 = ImgUtil.resizeImage(
				hpText1, (int) hpText1.getWidth(), (int) hpText1.getHeight(),
				(int) (hpText1.getWidth() * gamepanel.getWindowDataHolder().scalingFactorX()),
				(int) (hpText1.getHeight() * gamepanel.getWindowDataHolder().scalingFactorY()));

		Image atkText1 = Text.getInstance().convertText("ATK:" + getCurrentDemon().getAtk(), 48);
		atkText1 = ImgUtil.resizeImage(
				atkText1, (int) atkText1.getWidth(), (int) atkText1.getHeight(),
				(int) (atkText1.getWidth() * gamepanel.getWindowDataHolder().scalingFactorX()),
				(int) (atkText1.getHeight() * gamepanel.getWindowDataHolder().scalingFactorY()));

		Image resText1 = Text.getInstance().convertText(String.format("RES:%.2f%%", getCurrentDemon().getRes()),
				48);
		resText1 = ImgUtil.resizeImage(
				resText1, (int) resText1.getWidth(), (int) resText1.getHeight(),
				(int) (resText1.getWidth() * gamepanel.getWindowDataHolder().scalingFactorX()),
				(int) (resText1.getHeight() * gamepanel.getWindowDataHolder().scalingFactorY()));

		Image dgcText1 = Text.getInstance().convertText(String.format("DGC:%.2f%%", getCurrentDemon().getDgc()),
				48);
		dgcText1 = ImgUtil.resizeImage(
				dgcText1, (int) dgcText1.getWidth(), (int) dgcText1.getHeight(),
				(int) (dgcText1.getWidth() * gamepanel.getWindowDataHolder().scalingFactorX()),
				(int) (dgcText1.getHeight() * gamepanel.getWindowDataHolder().scalingFactorY()));

		Image nameText1 = Text.getInstance().convertText("" + getCurrentDemon().getMobName(), 48);
		nameText1 = ImgUtil.resizeImage(
				nameText1, (int) nameText1.getWidth(), (int) nameText1.getHeight(),
				(int) (nameText1.getWidth() * gamepanel.getWindowDataHolder().scalingFactorX()),
				(int) (nameText1.getHeight() * gamepanel.getWindowDataHolder().scalingFactorY()));

		Image expMaxText1 = Text.getInstance()
				.convertText(
						getCurrentDemon().getCurrentExp() + ":" + getCurrentDemon().getMaxExp(),
						32);
		expMaxText1 = ImgUtil.resizeImage(
				expMaxText1, (int) expMaxText1.getWidth(), (int) expMaxText1.getHeight(),
				(int) (expMaxText1.getWidth() * gamepanel.getWindowDataHolder().scalingFactorX()),
				(int) (expMaxText1.getHeight() * gamepanel.getWindowDataHolder().scalingFactorY()));

		Image lvlText1 = Text.getInstance().convertText("lvl:" + getCurrentDemon().getLvl(), 48);
		lvlText1 = ImgUtil.resizeImage(
				lvlText1, (int) lvlText1.getWidth(), (int) lvlText1.getHeight(),
				(int) (lvlText1.getWidth() * gamepanel.getWindowDataHolder().scalingFactorX()),
				(int) (lvlText1.getHeight() * gamepanel.getWindowDataHolder().scalingFactorY()));

		moveFromArrayToView();

		hpView.setImage(hpText1);
		atkView.setImage(atkText1);
		resView.setImage(resText1);
		dgcView.setImage(dgcText1);
		nameView.setImage(nameText1);
		expText.setImage(expMaxText1);
		lvlView.setImage(lvlText1);
		expBar.setImage(showXPbr());
		elementView.setImage(showElementbr(getCurrentDemon().getElement()));
		eIconView.setImage(showElementIcon(getCurrentDemon().getElement()));

		if (!gamepanel.getNpcs().contains(getCurrentDemon().getDemon()))
			gamepanel.getNpcs().add(getCurrentDemon().getDemon());

		Button[] ctbs = {
				ctb1, ctb2, ctb3, ctb4, ctb5, ctb6
		};

		for (int i = 0; i < ctbs.length; i++) if(currentDemonArray[i] != null) ctbs[i].setImage(getIconM(currentDemonArray[i].getMobName(), currentDemonArray[i].getElement()));
	}
}

