package game;

import javax.swing.JApplet;

public class Applet extends JApplet {
	
	private static final long serialVersionUID = 1L;

	public void init()
	{
		Game game = new Game();
		
		setSize(Main.width, Main.height);
		setContentPane(game);
		
		game.start();
	}
	
}
