package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

public class Ring {
	
	private int ringN;
	private int width = 75, height = 25;
	private int x, y;
	private boolean clicked;
	private boolean topRing;
	
	public Ring()
	{
		this(5);
	}
	
	public Ring(int num)
	{
		ringN = num;
		width += ringN * 20;
	}
	
	public int getX(){return x;}
	public int getY(){return y;}
	public int getW(){return width;}
	public int getH(){return height;}
	public int getNum(){return ringN;}
	
	public boolean getClicked(){return clicked;}
	
	public int getCenterX(){return x + width / 2;}
	public int getCenterY(){return y + height / 2;}
	
	public void setTop(boolean top){topRing = top;}
	
	public void draw(Graphics g, int x, int y)
	{
		switch(ringN)
		{
			case 0:
				g.setColor(Color.BLUE);
				break;
				
			case 1:
				g.setColor(Color.RED);
				break;
				
			case 2:
				g.setColor(Color.GREEN);
				break;
				
			case 3:
				g.setColor(Color.YELLOW);
				break;
				
			case 4:
				g.setColor(Color.CYAN);
				break;
				
			case 5:
				g.setColor(Color.MAGENTA);
				break;
				
			case 6:
				g.setColor(Color.pink);
				break;
				
			case 7:
				g.setColor(Color.LIGHT_GRAY);
				break;
				
			case 8:
				g.setColor(Color.DARK_GRAY);
				break;
				
			case 9:
				g.setColor(Color.BLACK);
				break;
				
			default:
				g.setColor(Color.ORANGE);
				break;
		}
		
		if(!clicked)
		{
			this.x = x - width / 2;
			this.y = y;
		}
		
		g.fillRect(this.x, this.y, width, height);
		
		g.setColor(Color.BLACK);
		g.drawRect(this.x, this.y, width, height);
	}
	
	public void mousePressed(MouseEvent e)
	{
		int key = e.getButton();
		int mx = e.getX();
		int my = e.getY();
		
		if(key == MouseEvent.BUTTON1 && topRing)
		{
			if(mx >= x && mx < x + width && my >= y && my < y + height)
			{				
				clicked = true;
			}
		}
	}
	
	public void mouseReleased(MouseEvent e)
	{
		clicked = false;
	}
	
	public void mouseDragged(MouseEvent e)
	{
		int mx = e.getX();
		int my = e.getY();
		
		if(clicked)
		{
			this.x = mx - width / 2;
			this.y = my - height / 2;
		}
	}
	
}
