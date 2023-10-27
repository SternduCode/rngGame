package rngGame.visual;

import rngGame.main.GamePanel;
import rngGame.main.WindowManager;

public enum Positions {
	
	Topleft			(0			,		  0),
	Topmiddle		(1920 /2		,		  0),
	Topright		(1920		,		  0),
	Middleleft		(0			,	 1080/2),
	Mcenter			(1920/2		,	 1080/2),
	Middleright 	(1920		,	 1080/2),
	Bottomleft		(0			,		1080),
	Bottommiddle	(1920/2		,		1080),
	Bottomright 	(1920		,		1080);
	
	final int x, y;
	
	Positions(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
}
