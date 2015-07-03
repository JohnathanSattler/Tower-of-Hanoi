package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Tower {
	
	private int width = 235, height = 400;
	private int x, y;
	
	private ArrayList<Ring> rings;
	private int nRings;
	
	private boolean clicked, released;
	private boolean topTower;
	
	public Tower()
	{
		this(0);
	}
	
	public Tower(int nRings)
	{
		rings = new ArrayList<Ring>();
		this.nRings = nRings;
		x = 0;
		y = 0;
		
		for(int i = 0; i < this.nRings; i++)
		{
			addRing(nRings - i - 1);
		}
	}
	
	public synchronized Ring removeRing()
	{
		if(rings.size() > 0)
		{
			Ring r = rings.get(rings.size() - 1);
			
			rings.remove(rings.size() - 1);
			
			return r;
		}
		
		return null;
	}
	
	public int getTopRing()
	{
		if(rings.size() > 0)
		{
			return rings.get(rings.size() - 1).getNum();
		}
		
		return Game.maxRings;
	}
	
	public void addRing(int ringN)
	{
		addRing(new Ring(ringN));
	}
	
	public synchronized void addRing(Ring r)
	{
		rings.add(r);
	}
	
	public int getW(){return width;}
	public int getH(){return height;}
	public void setX(int x){this.x = x;}
	public int getX(){return x;}
	public void setY(int y){this.y = y;}
	public int getY(){return y;}
	
	public int getRings(){return rings.size();}
	public boolean getClicked(){return clicked;}
	public boolean getReleased(){return released;}
	public void resetClicked(){clicked = false;}
	public void resetReleased(){released = false;}
	public boolean getTopTower(){return topTower;}
	
	public synchronized void update()
	{
		for(int i = 0; i < rings.size(); i++)
		{
			if(i == rings.size() - 1)
			{
				rings.get(i).setTop(true);
			} else {
				rings.get(i).setTop(false);
			}
		}
	}
	
	public void draw(Graphics g)
	{
		g.setColor(Color.ORANGE);
		g.fillRect(x + width / 2 - 5, y, 10, height);
		g.setColor(Color.BLACK);
		g.drawRect(x + width / 2 - 5, y, 10, height);
		
		g.setColor(Color.ORANGE);
		g.fillRect(x + width / 2 - 50, height + y - 10, 100, 10);
		g.setColor(Color.BLACK);
		g.drawRect(x + width / 2 - 50, height + y - 10, 100, 10);
		
		g.drawRect(x, y, width, height);
	}
	
	public synchronized void drawRing(Graphics g)
	{
		if(rings.size() > 0)
		{
			for(int i = 0; i < rings.size(); i++)
			{
				Ring r = rings.get(i);
				
				r.draw(g, x + width / 2, y + height - r.getH() - r.getH() * i - 10);
			}
		}
	}
	
	public void mousePressed(MouseEvent e)
	{
		int key = e.getButton();
		int mx = e.getX();
		int my = e.getY();
		
		if(key == MouseEvent.BUTTON1)
		{
			if(rings.size() > 0 && mx >= rings.get(rings.size() - 1).getX() && mx < rings.get(rings.size() - 1).getX() + rings.get(rings.size() - 1).getW() && my >= rings.get(rings.size() - 1).getY() && my < rings.get(rings.size() - 1).getY() + rings.get(rings.size() - 1).getH())
			{
				clicked = true;
				topTower = true;
			} else {
				clicked = false;
			}
		}
		
		for(Ring r: rings)
		{
			r.mousePressed(e);
		}
	}
	
	public void mouseReleased(MouseEvent e)
	{
		int key = e.getButton();
		int mx = e.getX();
		int my = e.getY();
		
		int rx = mx, ry = my;
		
		topTower = false;
		
		for(Ring r: rings)
		{
			if(r.getClicked())
			{
				rx = r.getCenterX();
				ry = r.getCenterY();
			}
		}
		
		if(key == MouseEvent.BUTTON1)
		{
			if(rx >= x && rx < x + width && ry >= y && ry < y + height)
			{
				released = true;
			}
		}
		
		for(Ring r: rings)
		{
			r.mouseReleased(e);
		}
	}
	
	public void mouseDragged(MouseEvent e)
	{
		for(Ring r: rings)
		{
			r.mouseDragged(e);
		}
	}
	
}
