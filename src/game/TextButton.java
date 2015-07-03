package game;

import java.awt.Font;
import java.awt.Graphics;

public class TextButton extends Button {
	
	private String str;
	private Font font;
	
	public TextButton(String title)
	{
		str = title;
		font = new Font("Arial", Font.BOLD, 20);
		
		setW(str.length() * 20);
		setH(25);
	}
	
	public void draw(Graphics g, int x, int y)
	{		
		super.draw(g, x, y);
		
		g.setColor(getSymbolColor());
		g.setFont(font);
		g.drawString(str, x + getW() / 2 - str.length() * 5, y + getH() / 2 + 8);
	}
	
}
