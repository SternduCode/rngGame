package rngGame.ui;

// TODO: Auto-generated Javadoc
/**
 * The Class LoadingScreen.
 */
public class LoadingScreen extends AnimatedImage {

	/** The default loading screen. */
	private static LoadingScreen defaultLoadingScreen;

	/**
	 * Instantiates a new loading screen.
	 *
	 * @param path the path
	 * @param windowDataHolder the window data holder
	 */
	public LoadingScreen(String path, WindowDataHolder windowDataHolder) { super(path, windowDataHolder); }

	/**
	 * Instantiates a new loading screen.
	 *
	 * @param path                the path
	 * @param windowDataHolder the window data holder
	 * @param fps                 the fps
	 */
	public LoadingScreen(String path, WindowDataHolder windowDataHolder, int fps) { super(path, windowDataHolder, fps); }

	/**
	 * Gets the default loading screen.
	 *
	 * @param windowDataHolder the window data holder
	 * @return the default loading screen
	 */
	public static LoadingScreen getDefaultLoadingScreen(WindowDataHolder windowDataHolder) {
		return defaultLoadingScreen != null ? defaultLoadingScreen
				: (defaultLoadingScreen = new LoadingScreen("./res/gui/Loadingscreen.gif", windowDataHolder, 10));
	}


}
