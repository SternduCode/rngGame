package entity;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import com.sterndu.json.*;
import javafx.scene.image.*;
import rngGAME.SpielPanel;
import tile.ImgUtil;

public class NPC extends Entity {

	protected Map<String, List<Image>> images;
	protected String currentKey;
	protected double x, y;
	protected int reqWidth, reqHeight, origWidth, origHeight;
	protected JsonObject npcData;

	public NPC(JsonObject npc) {
		init(npc);
	}

	protected List<Image> getAnimatedImages(String path) {
		List<Image> li = new ArrayList<>();
		Image img = new Image(getClass().getResourceAsStream("/res/npc/" + path));
		for (int i = 0; i < img.getWidth(); i += origWidth) {
			WritableImage wi = new WritableImage(img.getPixelReader(), i, 0, origWidth, origHeight);
			li.add(ImgUtil.resizeImage(wi,
					(int) wi.getWidth(), (int) wi.getHeight(), reqWidth, reqHeight));
		}
		return li;
	}

	protected void init(JsonObject npc) {
		x = ((NumberValue) ((JsonArray) npc.get("position")).get(0)).getValue().doubleValue();
		y = ((NumberValue) ((JsonArray) npc.get("position")).get(1)).getValue().doubleValue();
		origWidth = ((NumberValue) ((JsonArray) npc.get("originalSize")).get(0)).getValue().intValue();
		origHeight = ((NumberValue) ((JsonArray) npc.get("originalSize")).get(1)).getValue().intValue();
		reqWidth = ((NumberValue) ((JsonArray) npc.get("requestedSize")).get(0)).getValue().intValue();
		reqHeight = ((NumberValue) ((JsonArray) npc.get("requestedSize")).get(1)).getValue().intValue();
		images = ((JsonObject) npc.get("textures")).entrySet().parallelStream()
				.map(s -> Map.entry(s.getKey(), getAnimatedImages(((StringValue) s.getValue()).getValue())))
				.collect(Collectors.toMap(Entry::getKey, Entry::getValue));
		setImage(images.values().stream().findFirst().get().get(0));
		npcData = (JsonObject) npc.get("npcData");
		currentKey = "idle";
	}

	public void update(Player p, SpielPanel gp) {
		double screenX = x - p.worldX + p.screenX;
		double screenY = y - p.worldY + p.screenY;
		if (x + reqWidth > p.worldX - p.screenX
				&& x - reqWidth < p.worldX + p.screenX
				&& y + reqHeight > p.worldY - p.screenY
				&& y - reqHeight < p.worldY + p.screenY) {
			setVisible(true);
			setX(screenX);
			setY(screenY);
			List<Image> frames = images.get(currentKey);
			spriteCounter++;
			if (spriteCounter > 30 / frames.size()) {
				spriteNum++;
				spriteCounter = 0;
			}
			Image image = null;
			if (spriteNum >= frames.size()) spriteNum = 0;
			image = frames.get(spriteNum);
			setImage(image);
		} else setVisible(false);
	}

}
