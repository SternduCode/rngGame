package rngGame.entity;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import com.sterndu.json.*;

import javafx.beans.property.ObjectProperty;
import javafx.scene.control.ContextMenu;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import rngGame.main.*;

// TODO: Auto-generated Javadoc
/**
 * The Class NPC.
 */
public class NPC extends Entity implements JsonValue {

	/** The h. */
	private final AtomicBoolean h = new AtomicBoolean(false);

	/**
	 * Instantiates a new npc.
	 *
	 * @param npc the npc
	 * @param gp the gp
	 * @param speed the speed
	 * @param npcs the npcs
	 * @param cm the cm
	 * @param requestorN the requestor N
	 */
	public NPC(JsonObject npc, GamePanel gp, double speed, List<? extends NPC> npcs, ContextMenu cm,
			ObjectProperty<? extends NPC> requestorN) {
		super(npc, speed, gp, "npc", npcs, cm, requestorN);
		init();
	}

	/**
	 * Instantiates a new npc.
	 *
	 * @param npc the npc
	 * @param gp the gp
	 * @param speed the speed
	 * @param directory the directory
	 * @param npcs the npcs
	 * @param cm the cm
	 * @param requestorN the requestor N
	 */
	public NPC(JsonObject npc, GamePanel gp, double speed, String directory, List<? extends NPC> npcs, ContextMenu cm,
			ObjectProperty<? extends NPC> requestorN) {
		super(npc, speed, gp, directory, npcs, cm, requestorN);
		init();
	}

	/**
	 * Instantiates a new npc.
	 *
	 * @param npc the npc
	 * @param gp the gp
	 * @param npcs the npcs
	 * @param cm the cm
	 * @param requestorN the requestor N
	 */
	public NPC(JsonObject npc, GamePanel gp, List<? extends NPC> npcs, ContextMenu cm,
			ObjectProperty<? extends NPC> requestorN) {
		super(npc, 0, gp, "npc", npcs, cm, requestorN);
		init();
	}

	/**
	 * Instantiates a new npc.
	 *
	 * @param npc the npc
	 * @param gp the gp
	 * @param directory the directory
	 * @param npcs the npcs
	 * @param cm the cm
	 * @param requestorN the requestor N
	 */
	public NPC(JsonObject npc, GamePanel gp, String directory, List<? extends NPC> npcs, ContextMenu cm,
			ObjectProperty<? extends NPC> requestorN) {
		super(npc, 0, gp, directory, npcs, cm, requestorN);
		init();
	}

	/**
	 * Instantiates a new npc.
	 *
	 * @param npc the npc
	 * @param npcs the npcs
	 * @param cm the cm
	 * @param requestorN the requestor N
	 */
	public NPC(NPC npc, List<? extends NPC> npcs, ContextMenu cm, ObjectProperty<? extends NPC> requestorN) {
		super(npc, npcs, cm, requestorN);
		init();
	}

	/**
	 * Inits the.
	 */
	protected void init() {
		collisionBoxes.entrySet().parallelStream()
		.forEach(s -> {
			if (s.getValue().getPoints().size() == 0) s.getValue().getPoints().addAll(0d, 0d, 0d,
					images.get(s.getKey()).get(0).getHeight(),
					images.get(s.getKey()).get(0).getWidth(),
					images.get(s.getKey()).get(0).getHeight(), images.get(s.getKey()).get(0).getWidth(),
					0d);
		});
		getMiscBoxHandler().put("talk", (gpt,self)->{
			gpt.getAktionbutton().setInteractionbuttonKann(true, gp2 -> {
				try {
                    gp2.setBlockUserInputs(true);
                	Image img = new Image(new FileInputStream(new File("./res/gui/bubble/SpeakBubble2.png")));
                    gamepanel.getBubble().getChildren().add(new ImageView(img));
                    Random r = new Random();
                    BufferedReader flr = new BufferedReader(new FileReader(new File("./res/texts/Guenther.txt")));
                                       
                    String line = "";
                    int uff = r.nextInt(5)+1;
                    try {
                        switch (uff) {
                            case 5:
                                flr.readLine();
                            case 4:
                                flr.readLine();
                            case 3:
                                flr.readLine();
                            case 2:
                                flr.readLine();
                            case 1:
                                line=flr.readLine();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                	}
                    gp2.getBubbleText().setImage(Text.getInstance().convertText(line));
                    gp2.getBubbleText().setLayoutX((gp2.SpielLaenge/10));
                    gp2.getBubbleText().setLayoutY((gp2.SpielHoehe/1.4)-(gp2.getBubbleText().getImage().getHeight()/2.0));
                    System.out.println(gp2.getBubbleText().getImage().getWidth());
                    System.out.println(line);
                    System.out.println(uff);                    
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
			});
		});


		collisionBoxes.forEach((key, poly) -> poly.setFill(Color.color(0, 1, 1, 0.75)));
		Input.getInstance().setKeyHandler("h" + hashCode(), mod -> {
			h.set(!h.get());
		}, KeyCode.H, false);
	}

	/**
	 * Update.
	 *
	 * @param milis the milis
	 */
	@Override
	public void update(long milis) {
		super.update(milis);

		if (isVisible() && h.get()) setVisible(false);
	}

}
