package rngGame.main;

import java.io.*;
import java.util.*;
import java.util.stream.Stream;

import javafx.scene.image.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import rngGame.ui.ImgUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class Text.
 */
public class Text {

	/**
	 * The Class AnimatedText.
	 */
	public class AnimatedText extends Pane {

		/** The text. */
		private final String text;

		/** The font height. */
		private final int fontHeight;

		/** The text color. */
		private final Color textColor;

		/** The show one by one. */
		private final boolean showOneByOne;

		/** The img. */
		private final WritableImage img;

		/** The iv. */
		private ImageView iv;

		/**
		 * Instantiates a new animated text.
		 *
		 * @param text         the text
		 * @param fontHeight the font height
		 * @param showOneByOne the show one by one
		 * @param textColor    the text color
		 */
		public AnimatedText(String text, int fontHeight, boolean showOneByOne, Color textColor) {
			this.text			= text;
			this.fontHeight		= fontHeight;
			this.showOneByOne	= showOneByOne;
			this.textColor		= textColor;

			animatedTexts.add(this);

			img = (WritableImage) convertText(text, fontHeight);

			if (textColor != Color.WHITE) {
				System.out.println(textColor.getBlue() + " " + textColor.getRed() + " " + textColor.getGreen());

				PixelReader pr = img.getPixelReader();

				int val = 0;

				int ii = 0, jj = 0;

				while (val == 0 || val == -16777216) {
					val = pr.getArgb(ii, jj);
					ii++;
					if (ii >= img.getWidth()) {
						jj++;
						ii = 0;
					}
				}

				System.out.println((byte) (val >> 16));
				System.out.println((byte) (val >> 8));
				System.out.println((byte) val);
				System.out.println(val);

				PixelWriter pw = img.getPixelWriter();

				for (int i = 0; i < img.getWidth(); i++) for (int j = 0; j < img.getHeight(); j++) {
					val	= pr.getArgb(i, j);
					val	= (int) ( (Math.round(Byte.toUnsignedInt((byte) (val >> 24)) * textColor.getOpacity()) << 24)
							+ (Math.round(Byte.toUnsignedInt((byte) (val >> 16)) * textColor.getRed()) << 16)
							+ (Math.round(Byte.toUnsignedInt((byte) (val >> 8)) * textColor.getGreen()) << 8)
							+ Math.round(Byte.toUnsignedInt((byte) val) * textColor.getBlue()));
					pw.setArgb(i, j, val);
				}
			}

		}

		/**
		 * Gets the img height.
		 *
		 * @return the img height
		 */
		public double getImgHeight() { return img.getHeight(); }

		/**
		 * Gets the img width.
		 *
		 * @return the img width
		 */
		public double getImgWidth() {
			return img.getWidth();
		}

		/**
		 * Methode gibt dir die RGB werte von deinem HEX code wieder um den dann als Farbe (für Schrift) benutzen zu können
		 * wennde aufrufst einfach ein hex code übergeben, ganzer hex auch mit dem # | BSP: #4f9abd.
		 *
		 * @param hexc  the given hexcode
		 * @return the RGB from hex
		 */
		public int getRGBFromHex(String hexc) {
			String rr, gg, bb;
			rr=hexc.substring(1,3); gg=hexc.substring(3,5); bb=hexc.substring(5,7);
			int valr=Integer.parseInt(rr, 16); int valg=Integer.parseInt(gg, 16); int valb=Integer.parseInt(bb, 16);
			return valr + valg + valb;
		}
		//		public void setTextColorRGB(Text HierBraucheHilfe) {
		//			HierBraucheHilfe.setFill(Color.rgb(0, 0, 0));
		//		}

		/**
		 * Update.
		 *
		 * @param lastFrameTime the last frame time
		 */
		public void update(long lastFrameTime) {
			if (iv == null) {
				iv = new ImageView();
				getChildren().add(iv);
			}

			iv.setImage(img);
		}

	}

	/** The Constant INSTANCE. */
	private static final Text INSTANCE = new Text();

	/** The charmap. */
	private final Map<Character, Image> charmap;

	/** The animated texts. */
	private final List<AnimatedText> animatedTexts = new ArrayList<>();

	/**
	 * Instantiates a new text.
	 */
	private Text() {
		charmap = new HashMap<>();
		try {
			loadCharacterFiles();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets the single instance of Text.
	 *
	 * @return single instance of Text
	 */
	public static Text getInstance() { return INSTANCE; }

	/**
	 * Gets the width for string.
	 *
	 * @param text the text
	 * @param fontHeight the font height
	 * @return the width for string
	 */
	private int getWidthForString(String text, int fontHeight) {
		if (fontHeight == -1) return (int) Stream.of(text.split("\n")).map(String::chars)
				.mapToDouble(stream -> stream.mapToObj(c -> (char) c).map(charmap::get).mapToDouble(Image::getWidth).sum()).max().orElse(0.0);
		return (int) text.chars().mapToObj(c -> (char) c).map(charmap::get).mapToDouble(Image::getWidth).map(d -> d / 32 * fontHeight)
				.map(Math::floor).sum();
	}

	/**
	 * Convert text.
	 *
	 * @param text the text
	 * @return the image
	 */
	public Image convertText(String text) {
		int lines = text.replaceAll("[^\n]", "").length() + 1;
		String	longestLine	= "";
		for (String line : text.split("\n")) longestLine = longestLine.length() > line.length() ? longestLine : line;
		WritableImage	im		= new WritableImage(getWidthForString(longestLine, -1), lines * 32);
		PixelWriter pw = im.getPixelWriter();
		char[] chars = text.toCharArray();
		int x = 0, y = 0;
		for (char c: chars) if (c == '\n') {
			x = 0;
			y++;
		} else {
			pw.setPixels(x, y * 32, (int) charmap.get(c).getWidth(), 32, charmap.get(c).getPixelReader(), 0, 0);
			x += charmap.get(c).getWidth();
		}
		return im;
	}

	/**
	 * Convert text.
	 *
	 * @param text       the text
	 * @param fontHeight the font height
	 *
	 * @return the image
	 */
	public Image convertText(String text, int fontHeight) {
		int lines = text.replaceAll("[^\n]", "").length() + 1;
		String	longestLine	= "";
		for (String line : text.split("\n")) longestLine = longestLine.length() > line.length() ? longestLine : line;
		WritableImage	im		= new WritableImage(getWidthForString(longestLine, fontHeight), lines * fontHeight);
		PixelWriter pw = im.getPixelWriter();
		char[] chars = text.toCharArray();
		int x = 0, y = 0;
		for (char c: chars) if (c == '\n') {
			x = 0;
			y++;
		} else {
			Image wi = ImgUtil.resizeImage(charmap.get(c), (int) charmap.get(c).getWidth(), 32,
					(int) (charmap.get(c).getWidth() / 32.0 * fontHeight), fontHeight);
			pw.setPixels(x, y * fontHeight, (int) wi.getWidth(), fontHeight, wi.getPixelReader(), 0, 0);
			x += wi.getWidth();
		}
		return im;
	}

	/**
	 * Convert text.
	 *
	 * @param text         the text
	 * @param fontHeight the font height
	 * @param showOneByOne the show one by one
	 * @param textColor    the text color
	 * @return the animated text
	 */
	public AnimatedText convertText(String text, int fontHeight, boolean showOneByOne, Color textColor) {
		return new AnimatedText(text, fontHeight, showOneByOne, textColor);
	}

	/**
	 * Load character files.
	 *
	 * @throws FileNotFoundException the file not found exception
	 */
	public void loadCharacterFiles() throws FileNotFoundException {
		File dir = new File("./res/letters/");

		File big = new File(dir, "Groß.png");
		File small = new File(dir, "Klein.png");
		File numbers = new File(dir, "Zahlen.png");

		Image bigI = new Image(new FileInputStream(big)),
				smallI = new Image(new FileInputStream(small)),
				numbersI = new Image(new FileInputStream(numbers));

		char[][] bigC = {
				{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I'},
				{'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R'},
				{'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'}
		}, smallC = {
				{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i'},
				{'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r'},
				{'s', 't', 'u', 'v', 'w', 'x', 'y', 'z'}
		}, numbersC = {
				{'1', '2', '3', '4', '5', '6', '7', '8', '9'},
				{'0', '?', '!', '%', '"', '#', ':', '.', ','},
				{'/'}
		};

		PixelReader bigPR = bigI.getPixelReader(),
				smallPR = smallI.getPixelReader(),
				numbersPR = numbersI.getPixelReader();

		for (int x = 0; x < bigI.getWidth() / 32; x++)
			for (int y = 0; y < bigI.getHeight() / 32; y++) if (y<bigC.length && x<bigC[y].length) {
				WritableImage cha = new WritableImage(12, 32);
				PixelWriter pw = cha.getPixelWriter();
				pw.setPixels(0, 0, 12, 32, bigPR, x * 32 + 10, y * 32);
				charmap.put(bigC[y][x], cha);
			}
		for (int x = 0; x < smallI.getWidth() / 32; x++)
			for (int y = 0; y < smallI.getHeight() / 32; y++) if (y<smallC.length && x<smallC[y].length) {
				WritableImage cha = new WritableImage(12, 32);
				PixelWriter pw = cha.getPixelWriter();
				pw.setPixels(0, 0, 12, 32, smallPR, x * 32 + 10, y * 32);
				charmap.put(smallC[y][x], cha);
			}
		for (int x = 0; x < numbersI.getWidth() / 32; x++)
			for (int y = 0; y < numbersI.getHeight() / 32; y++) if (y<numbersC.length && x<numbersC[y].length) {
				WritableImage cha = new WritableImage(12, 32);
				PixelWriter pw = cha.getPixelWriter();
				pw.setPixels(0, 0, 12, 32, numbersPR, x * 32 + 10, y * 32);
				charmap.put(numbersC[y][x], cha);
			}

		charmap.put(' ', new WritableImage(12, 32));
	}

	/**
	 * Update.
	 *
	 * @param lastFrameTime the last frame time
	 */
	public void update(long lastFrameTime) { animatedTexts.forEach(t -> t.update(lastFrameTime)); }

}
