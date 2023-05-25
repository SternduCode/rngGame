package rngGame.main;

import javafx.scene.image.*;
import javafx.embed.swing.SwingFXUtils;
import javax.imageio.ImageIO;
import java.io.*;
import java.util.*;
import java.awt.image.*;

public class TextToPng {

	public static void main(String[] args) {
		Image img = Text.getInstance().convertText("Coming Soon");
		BufferedImage bi = SwingFXUtils.fromFXImage(img, null);
		try {
			ImageIO.write(bi, "png", new File("./pngs/cs.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}