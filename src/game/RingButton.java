package game;

import java.awt.Graphics;

public class RingButton extends Button {
	
	private boolean add;
	
	public RingButton(boolean add)
	{		
		this.add = add;
		
		setW(25);
		setH(25);
	}
	
	public boolean getType(){return add;}
	
	public void draw(Graphics g, int x, int y)
	{
		super.draw(g, x, y);
		
		g.setColor(getSymbolColor());
		g.fillRect(x + getW() / 2 - (getW() - 10) / 2, y + getH() / 2 - 5 / 2, getW() - 10, 5);
		if(add)
		{
			g.fillRect(x + getW() / 2 - 5 / 2, y + getH() / 2 - (getH() - 10) / 2, 5, getH() - 10);
		}
	}
	
}
