package component;

import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class ScrollingState implements Drawable{
	
	private ArrayList<Drawable> contains;
	private int containIndex;
	
	private int posX,posY;
	private int scrolX, scrolY;
	private int width, height;
	
	public ScrollingState(int x, int y, int w, int h) {
		posX = x;
		posY = y;
		scrolX = 0;
		scrolY = 0;
		width = w;
		height = h;
		
	}
	
	public ScrollingState(int x, int y, int w, int h,ArrayList<Drawable> contains) {
		this(x,y,w,h);
		
		this.contains = contains;
		containIndex = 0;
	}
	
	public void addDrawable(Drawable d) {
		contains.add(d);
	}
	
	public boolean removeDrawable(Drawable d) {
		return contains.remove(d);
	}
	
	public void removeAllDrawable() {
		contains.clear();
	}
	
	public void draw(Graphics g) {
		
		BufferedImage img = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
		Graphics g2 = img.getGraphics();
		
		for(Drawable d : contains) {
			d.draw(g2);
		}
		
		g.drawImage(img,posX,posY,null);
	}
	
	public int getX() {
		return posX;
	}
	
	public void setX(int x) {
		posX = x;
	}
	
	public int getY() {
		return posY;
	}
	
	public void setY(int y) {
		posY = y;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void mousePressed(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		
		if(x>= posX && x < posX+width && y >= posY && y < posY+height) {
			e.translatePoint(-(posX),-(posY));
			System.out.println(e.paramString());
			for(Drawable d : contains) {
				d.mousePressed(e);
			}
		}
	}
	
	private void scrollContains(int i) {
		for(Drawable d : contains) {
			d.setY(d.getY()+i);
		}
	}
	
	public void	mouseWheelMoved(MouseWheelEvent e) {
		int wheel = e.getWheelRotation();
		// System.out.println(e.paramString());
		if(wheel > 0 && scrolY > -300) {
			scrolY -=20;
			scrollContains(-20);
		}
		else if(wheel < 0 && scrolY<500) {
			scrolY+=20;
			scrollContains(20);
		}
	}
}