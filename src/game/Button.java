package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

public class Button {
	
	private int width, height;
	private int x, y;
	private Color back, symbol;
	
	private boolean mouseOver;
	private boolean clicked;
	
	public Button()
	{				
		back = Color.RED;
		symbol = Color.ORANGE;
		
		width = 25;
		height = 25;
	}
	
	public int getW(){return width;}
	public void setW(int w){width = w;}
	public int getH(){return height;}
	public void setH(int h){height = h;}
	
	public int getX(){return x;}
	public void setX(int x){this.x = x;}
	public int getY(){return y;}
	public void setY(int y){this.y = y;}
	
	public Color getSymbolColor(){return symbol;}
	public boolean mouseOver(){return mouseOver;}
	public boolean clicked(){return clicked;}
	
	public void resetColors()
	{
		back = Color.RED;
		symbol = Color.ORANGE;
	}
	
	public void draw(Graphics g, int x, int y)
	{
		this.x = x;
		this.y = y;
		
		if(Game.solving())
		{
			back = Color.BLUE;
			symbol = Color.CYAN;
		}
		g.setColor(back);
		g.fillRect(x, y, width, height);
		
		g.setColor(Color.BLACK);
		g.drawRect(x, y, width, height);
	}
	
	public void mouseMoved(MouseEvent e)
	{
		int mx = e.getX();
		int my = e.getY();
		
		if(mx >= x && mx < x + width && my >= y && my < y + height)
		{
			back = Color.BLUE;
			symbol = Color.CYAN;
			mouseOver = true;
		} else {
			back = Color.RED;
			symbol = Color.ORANGE;
			mouseOver = false;
		}
	}
	
	public void mouseDragged(MouseEvent e)
	{
		int mx = e.getX();
		int my = e.getY();
		
		if(mx < x || mx >= x + width || my < y || my >= y + height)
		{
			back = Color.RED;
			symbol = Color.ORANGE;
			mouseOver = false;
		}
	}
	
	public void mousePressed(MouseEvent e)
	{
		int mx = e.getX();
		int my = e.getY();
		
		if(mx >= x && mx < x + width && my >= y && my < y + height)
		{
			if(!Game.solving())
			{
				clicked = true;
			} else {
				clicked = false;
			}
		}
	}
	
	public void mouseReleased(MouseEvent e)
	{
		int mx = e.getX();
		int my = e.getY();
		
		if(mx >= x && mx < x + width && my >= y && my < y + height)
		{
			back = Color.BLUE;
			symbol = Color.CYAN;
			mouseOver = true;
		}
		
		clicked = false;
	}
	
}
