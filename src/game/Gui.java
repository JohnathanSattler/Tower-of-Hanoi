package game;

import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

public class Gui {
	
	private RingButton[] buttons;
	private TextButton resetButton, solveButton;
	
	private boolean mouseOver;
	private Game game;
	
	public Gui(Game game)
	{
		this.game = game;
		
		buttons = new RingButton[]{new RingButton(false), new RingButton(true)};
		resetButton = new TextButton("Reset");
		solveButton = new TextButton("Solve");
	}
	
	public int getSubW(){return buttons[0].getW();}
	public int getResetW(){return resetButton.getW();}
	public int getSolveW(){return solveButton.getW();}
	
	public void resetSolveColors(){solveButton.resetColors();}
	
	public void resetCursur()
	{
		if(mouseOver)
		{
			game.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		}
	}
	
	public void drawSub(Graphics g, int x, int y)
	{
		buttons[0].draw(g, x, y);
	}
	
	public void drawAdd(Graphics g, int x, int y)
	{
		buttons[1].draw(g, x, y);
	}
	
	public void drawReset(Graphics g, int x, int y)
	{
		resetButton.draw(g, x, y);
	}
	
	public void drawSolve(Graphics g, int x, int y)
	{
		solveButton.draw(g, x, y);
	}
	
	public void mouseMoved(MouseEvent e)
	{
		mouseOver = false;
		
		for(RingButton b: buttons)
		{
			b.mouseMoved(e);
			
			if(b.mouseOver())
			{
				mouseOver = true;
			}
		}
		
		resetButton.mouseMoved(e);
		if(resetButton.mouseOver())
		{
			mouseOver = true;
		}
		
		solveButton.mouseMoved(e);
		if(solveButton.mouseOver())
		{
			mouseOver = true;
		}
		
		if(!Game.solving())
		{
			if(mouseOver)
			{
				game.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			} else {
				game.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		}
	}
	
	public void mouseDragged(MouseEvent e)
	{
		mouseOver = false;
		
		for(RingButton b: buttons)
		{
			b.mouseDragged(e);
			
			if(b.mouseOver())
			{
				mouseOver = true;
			}
		}
		
		resetButton.mouseDragged(e);
		if(resetButton.mouseOver())
		{
			mouseOver = true;
		}
		
		solveButton.mouseDragged(e);
		if(solveButton.mouseOver())
		{
			mouseOver = true;
		}
		
		if(!Game.solving())
		{
			if(!mouseOver)
			{
				game.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		}
	}
	
	public void mousePressed(MouseEvent e)
	{
		for(RingButton b: buttons)
		{
			b.mousePressed(e);
			
			if(b.clicked())
			{
				int rings = game.getRings();
				boolean canReset = false;
				
				if(b.getType())
				{
					if(rings < Game.maxRings)
					{
						canReset = true;
					}
					rings++;
				} else {
					if(rings > Game.minRings)
					{
						canReset = true;
					}
					rings--;
				}
				
				if(canReset)
				{
					game.resetGame(rings);
				}
			}
		}
		
		resetButton.mousePressed(e);
		if(resetButton.clicked())
		{
			game.resetGame(game.getRings());
		}
		
		solveButton.mousePressed(e);
		if(solveButton.clicked())
		{
			if(!Game.solving())
			{
				game.solve();
			}
		}
	}
	
	public void mouseReleased(MouseEvent e)
	{
		mouseOver = false;
		
		for(RingButton b: buttons)
		{
			b.mouseReleased(e);
			
			if(b.mouseOver())
			{
				mouseOver = true;
			}
		}
		
		resetButton.mouseReleased(e);
		if(resetButton.mouseOver())
		{
			mouseOver = true;
		}
		
		solveButton.mouseReleased(e);
		if(solveButton.mouseOver())
		{
			mouseOver = true;
		}
		
		if(!Game.solving())
		{
			if(mouseOver)
			{
				game.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
		}
	}
	
}
