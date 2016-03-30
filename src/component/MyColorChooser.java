package component;

import main.*;

import javax.swing.*;
import java.awt.*;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MyColorChooser {
	
	private Color[] colors = new Color[8];
	private Color currentColor = null;
	private Color blockColor = null;
	
	private int command;
	
	private int width;
	private int height;
	private int posX;
	private int posY;
	
	public MyColorChooser(int x, int y, int width, int height) {
		
		this.width = width;
		this.height = height;
		this.posX = x;
		this.posY = y;
		
		colors[0] = Color.white;
		colors[1] = Color.black;
		colors[2] = Color.red;
		colors[3] = Color.blue;
		colors[4] = Color.magenta;
		colors[5] = Color.orange;
		colors[6] = Color.cyan;
		colors[7] = Color.gray;
		
	}
	
	public void setCommand(int c) {
		this.command = c;
	}
	
	public int getCommand() {
		return this.command;
	}
	
	public Color getColor() {
		return currentColor;
	}
	
	public void draw(Graphics g) {
		int xCoord = ((width-5)/4);
		int yCoord = ((height-5)/2);
		
		for(int i=0; i<4; i++) {
			for(int j=0; j<2; j++) {
				Color c = colors[(i*2)+j];
				if(c.equals(blockColor)) {
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
	
	public void block(Color c) {
		for(Color color : this.colors) {
			if(color.equals(c)) {
				blockColor = color;
			}
		}
	}

	public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) {
			int iColor = (int) ((e.getX()-posX)-5)/((width-5)/4);
			int jColor = (int) ((e.getY()-posY)-5)/((height-5)/2);
			
			System.out.println("x:"+iColor+" y:"+jColor);
			
			Color c = colors[(iColor*2)+jColor];
			if(!c.equals(blockColor)) {
				currentColor = c;
			}
		}
	}
}