package component;

import main.*;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.awt.event.MouseEvent;

public class MyColorChooser implements Drawable{
	
	private Color[] colors = new Color[8];
	private Color currentColor = null;
	private ArrayList<Color> blockColors;
	
	private ColorChooseEvent colorEvent;
	
	private int width;
	private int height;
	private int posX;
	private int posY;
	
	public MyColorChooser(int x, int y, int width, int height) {
		
		this.width = width;
		this.height = height;
		this.posX = x;
		this.posY = y;
		
		blockColors = new ArrayList<Color>();
		
		colors[0] = Color.white;
		colors[1] = Color.black;
		colors[2] = Color.red;
		colors[3] = Color.blue;
		colors[4] = Color.magenta;
		colors[5] = Color.orange;
		colors[6] = Color.cyan;
		colors[7] = Color.gray;
		
	}
	
	public MyColorChooser(int x, int y, int width, int height, ColorChooseEvent cce) {
		this(x,y,width,height);
		
		colorEvent = cce;
	}
	
	public Color getColor() {
		return currentColor;
	}
	
	public void setColorChooseEvent(ColorChooseEvent cce) {
		colorEvent = cce;
	}
	
	public void draw(Graphics g) {
		int xCoord = ((width-5)/4);
		int yCoord = ((height-5)/2);
		
		for(int i=0; i<4; i++) {
			for(int j=0; j<2; j++) {
				Color c = colors[(i*2)+j];
				if(blockColors.indexOf(c) != -1) {
					c = c.darker();
					c = c.darker();
					c = c.darker();
				}
				g.setColor(c);
				g.fillRect(posX + 5+(i*xCoord),posY + 5+(j*yCoord),xCoord-5,yCoord-5);
				
				if(c.equals(currentColor)) {
					g.setColor(new Color(128,0,0));
				}
				else { 
					g.setColor(Color.black);
				}
				
				g.drawRect(posX + 5+(i*xCoord),posY + 5+(j*yCoord),xCoord-5,yCoord-5);
			}
		}
	}
	
	public void addBlockColor(Color c) {
		boolean trouve = false;
		for(Color color : this.colors) {
			if(color.equals(c)) {
				for(Color color2 : blockColors) {
					if(color2.equals(c)) {
						trouve = true;
					}
				}
				if(!trouve) {
					blockColors.add(color);
				}
				return;
			}
		}
	}
	
	public void removeBlockColor(Color c) {
		for(Color color : this.colors) {
			if(color.equals(c)) {
				for(Color color2 : blockColors) {
					if(color2.equals(c)) {
						blockColors.remove(color2);
					}
				}
				return;
			}
		}
	}
	
	public void removeAllBlockColor() {
		blockColors.clear();
	}
	
	public void setX(int x) {
		posX = x;
	}
	
	public void setY(int y) {
		posY = y;
	}
	
	public int getX() {
		return posX;
	}
	
	public int getY() {
		return posY;
	}
	
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) {
			int iColor = (int) ((e.getX()-posX)-5)/((width-5)/4);
			int jColor = (int) ((e.getY()-posY)-5)/((height-5)/2);
			
			System.out.println("x:"+iColor+" y:"+jColor);
			
			Color c = colors[(iColor*2)+jColor];
			
			if(blockColors.indexOf(c) == -1) {
				currentColor = c;
				if(colorEvent != null) {
					colorEvent.colorChoose(c);
				}
			}
		}
	}
}