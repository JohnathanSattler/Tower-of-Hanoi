package game;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

public class Game extends JPanel implements Runnable {
	
	private static final long serialVersionUID = 1L;
	
	public static int width, height;
	
	private boolean running;
	private Thread thread;
	
	private Image img;
	private Graphics dbg;
	
	private TowerManager towers;
	private int nRings;
	private int minMoves;
	
	private Font font = new Font("Arial", Font.PLAIN, 20);
	private Gui gui;
	
	public static final int maxRings = 10, minRings = 3;
	private static boolean solving;
	private static String status;
	
	public Game()
	{
		resetGame(4);
		
		gui = new Gui(this);
		
		width = getWidth();
		height = getHeight();
		
		setStatus("");
		
		setFocusable(true);
		requestFocus();
		
		addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e)
			{
				int key = e.getKeyCode();
				
				if(key == KeyEvent.VK_ESCAPE)
				{
					exit();
				}
			}
		});
		
		addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e)
			{
				towers.mousePressed(e);
				gui.mousePressed(e);
			}
			public void mouseReleased(MouseEvent e)
			{
				towers.mouseReleased(e);
				gui.mouseReleased(e);
			}
		});
		addMouseMotionListener(new MouseAdapter(){
			public void mouseMoved(MouseEvent e)
			{
				gui.mouseMoved(e);
			}
			public void mouseDragged(MouseEvent e)
			{
				towers.mouseDragged(e);
				gui.mouseDragged(e);
			}
		});
	}
	
	public void exit()
	{
		stop();
		System.exit(0);
	}
	
	public void start()
	{
		if(running || thread != null)
		{
			return;
		}
		thread = new Thread(this);
		running = true;
		thread.start();
	}
	
	public void stop()
	{
		if(!running || thread == null)
		{
			return;
		}
		
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void run()
	{
		long before, after, sleep;
		
		before = System.currentTimeMillis();
		
		while(running)
		{
			update();
			render();
			
			after = System.currentTimeMillis() - before;
			sleep = 1000 / 60 - after;
			if(sleep < 1)
			{
				sleep = 1;
			}
			
			try {
				Thread.sleep(sleep);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			after = System.currentTimeMillis() - before;
			
			//System.out.println("Took " + after + "ms");
			//System.out.println("FPS: " + 1000 / after);
			
			before = System.currentTimeMillis();
		}
	}
	
	private void update()
	{
		if(width != getWidth() || height != getHeight())
		{
			width = getWidth();
			height = getHeight();
		}
		
		towers.update();
	}
	
	private void render()
	{
		if(img == null)
		{
			img = createImage(width, height);
			if(img == null)
			{
				System.err.println("Error creating image.");
			} else {
				dbg = img.getGraphics();
			}
		}
		
		if(img != null)
		{
			if(img.getWidth(null) != width || img.getHeight(null) != height)
			{
				if(width <= 0)
				{
					width++;
				}
				if(height <= 0)
				{
					height++;
				}
				
				img = createImage(width, height);
				if(img == null)
				{
					System.err.println("Error Resizing Image");
				} else {
					dbg = img.getGraphics();
				}
			}
			
			draw(dbg);
		}
	}
	
	public void paint(Graphics g)
	{
		draw(g);
	}
	
	public void resetGame(int nRings)
	{
		if(nRings > maxRings)
		{
			nRings = maxRings;
		}
		if(nRings < minRings)
		{
			nRings = minRings;
		}
		
		this.nRings = nRings;
		
		minMoves = 2;
		for(int i = 1; i < nRings; i++)
		{
			minMoves *= 2;
		}
		minMoves--;
		
		towers = new TowerManager(nRings);
	}
	
	public static boolean solving()
	{
		return solving;
	}
	
	public void solve()
	{
		solving = true;
		setStatus("");
		setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		
		resetGame(nRings);
		towers.gameSolved(true);
		
		final Thread h = new Thread(new Hanoi(nRings, this));
		h.start();
		
		new Thread(new Runnable(){
			public void run()
			{
				try {
					h.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					solving = false;
					gui.resetSolveColors();
					gui.resetCursur();
					setStatus("");
				}
			}
		}).start();
	}
	
	public TowerManager getTowers(){return towers;}
	public int getRings(){return nRings;}
	public static void setStatus(String s){status = s;}
	
	private void drawGui(Graphics g)
	{
		String str;
		
		drawText("Moves: " + towers.getMoves(), 5, 20, Color.BLACK, font, g);
		
		if(towers.gameOver() && !towers.gameSolved())
		{
			drawText("Game Over", 5, 40, Color.BLACK, font, g);
			if(towers.getMoves() == minMoves)
			{
				drawText("Perfect Game!", 5, 60, Color.BLACK, font, g);
			} else
			if(towers.getMoves() < minMoves)
			{
				drawText("You Cheated The System!", 5, 60, Color.BLACK, font, g);
			}
		} else
		if(towers.gameOver() && towers.gameSolved() && !solving())
		{
			drawText("Puzzle Solved", 5, 40, Color.BLACK, font, g);
		} else
		if(solving())
		{
			drawText(status, width / 2 - status.length() * 5, 60, Color.BLACK, font, g);
		}
		
		str = "Disks: " + nRings;
		drawText(str, width / 2 - str.length() * 5 + 5, 25, Color.BLACK, font, g);
		
		if(!solving())
		{
			gui.drawSub(g, width / 2 - str.length() * 5 - gui.getSubW(), 5);
			gui.drawAdd(g, width / 2 + str.length() * 5, 5);
			gui.drawReset(g, width / 2 - gui.getResetW() / 2, 40);
		}
		
		str = "Minimum Moves: " + minMoves;
		drawText(str, width - str.length() * 11, 20, Color.BLACK, font, g);
		gui.drawSolve(g, width - gui.getSolveW() - 10, 40);
		
		//drawText(Main.VERSION, 5, height - 5, Color.BLACK, font, g);
		
		//drawText(Main.NAME, 5, height - 5, Color.BLACK, font, g);
		//str = "Version: " + Main.VERSION;
		//drawText(str, width - str.length() * 10 + 10, height - 5, Color.BLACK, font, g);
	}
	
	private void drawText(String str, int x, int y, Color c, Font f, Graphics g)
	{
		g.setColor(c);
		g.setFont(f);
		g.drawString(str, x, y);
	}
	
	private void draw(Graphics g)
	{
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		drawGui(g);
		towers.draw(g);
		
		gamePaint();
	}
	
	private void gamePaint()
	{
		Graphics g = this.getGraphics();
		
		if(img != null && g != null)
		{
			g.drawImage(img, 0, 0, null);
			Toolkit.getDefaultToolkit().sync();
			g.dispose();
		}
	}
	
}
