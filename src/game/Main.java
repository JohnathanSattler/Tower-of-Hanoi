package game;

import javax.swing.JFrame;

public class Main {
	
	public static final String NAME = "Tower of Hanoi";
	public static final String VERSION = "1.0.4";
	public static final String TITLE = String.format("%s", NAME);
	
	public static int width = 800, height = 600;
	
	public static void main(String[] args)
	{
		JFrame frame = new JFrame(TITLE);
		Game game = new Game();
		
		frame.setSize(width + 6, height + 28);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.add(game);
		frame.setVisible(true);
		
		game.start();
	}
	
}
