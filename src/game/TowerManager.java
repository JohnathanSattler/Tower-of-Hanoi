package game;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

public class TowerManager {
	
	private Tower[] towers;
	private int towerSpace;
	private int x, width;
	
	private boolean canPlace;
	private int clicked, released;
	private int moves;
	
	private int rings;
	private boolean gameOver;
	private boolean gameSolved;
	
	public TowerManager(int rings)
	{
		this.rings = rings;
		moves = 0;
		
		towerSpace = 25;
		
		towers = new Tower[3];
		
		towers[0] = new Tower(rings);
		towers[1] = new Tower();
		towers[2] = new Tower();
		
		for(int i = 0; i < towers.length; i++)
		{
			width += towers[i].getW();
		}
		width += towerSpace * (towers.length - 1);
		
		setDim();
	}
	
	private void setDim()
	{		
		x = Game.width / 2 - width / 2;
		
		towers[0].setX(x);
		towers[1].setX(towers[0].getX() + towers[0].getW() + towerSpace);
		towers[2].setX(towers[1].getX() + towers[1].getW() + towerSpace);
		
		towers[0].setY(Main.height / 2 - towers[0].getH() / 2);
		towers[1].setY(Main.height / 2 - towers[1].getH() / 2);
		towers[2].setY(Main.height / 2 - towers[2].getH() / 2);
	}
	
	public int getMoves(){return moves;}
	public void addMoves(){moves++;}
	public boolean gameOver(){return gameOver;}
	public void gameOver(boolean go){gameOver = go;}
	public boolean gameSolved(){return gameSolved;}
	public void gameSolved(boolean gs){gameSolved = gs;}
	
	public void moveRing(int start, int finish)
	{
		Ring r = towers[start].removeRing();
		towers[finish].addRing(r);
	}
	
	public void update()
	{
		setDim();
		
		for(Tower t: towers)
		{
			t.update();
		}
		
		if(towers[towers.length - 1].getRings() >= rings)
		{
			gameOver = true;
		}
	}
	
	public void draw(Graphics g)
	{
		for(Tower t: towers)
		{
			t.draw(g);
		}
		
		for(Tower t: towers)
		{
			t.drawRing(g);
		}
		
		for(Tower t: towers)
		{
			if(t.getTopTower())
			{
				t.drawRing(g);
			}
		}
	}
	
	public void mousePressed(MouseEvent e)
	{
		if(!gameOver && !Game.solving())
		{
			for(Tower t: towers)
			{
				t.mousePressed(e);
			}
			
			for(int i = 0; i < towers.length; i++)
			{
				Tower t = towers[i];
				
				if(t.getClicked())
				{
					clicked = i;
					canPlace = true;
					t.resetClicked();
				}
			}
		}
	}
	
	public void mouseReleased(MouseEvent e)
	{
		if(!gameOver && !Game.solving())
		{
			for(Tower t: towers)
			{
				t.mouseReleased(e);
			}
			
			for(int i = 0; i < towers.length; i++)
			{
				Tower t = towers[i];
				
				if(t.getReleased())
				{
					released = i;
					t.resetReleased();
					
					if(clicked != released && canPlace)
					{
						if(towers[clicked].getTopRing() < towers[released].getTopRing())
						{						
							moveRing(clicked, released);
							
							moves++;
						}
					}
				}
			}
			
			canPlace = false;
		}
	}
	
	public void mouseDragged(MouseEvent e)
	{
		if(!gameOver && !Game.solving())
		{			
			for(Tower t: towers)
			{
				t.mouseDragged(e);
			}
		}
	}
	
}
