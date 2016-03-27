package component;

import main.*;

import javax.swing.*;
import java.awt.*;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MyColorChooser extends JPanel implements MouseListener {
	
	private Color[] colors = new Color[8];
	private Color currentColor = null;
	private Color blockColor = null;
	
	private PlayerSelecterState pan;
	
	private int command;
	
	private int xMax;
	private int yMax;
	private int xCoord;
	private int yCoord;
	
	public MyColorChooser(PlayerSelecterState p) {
		
		setBackground(Color.red);
		addMouseListener(this);
		
		this.pan = p;
		
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
	
	public void paintComponent(Graphics g) {
		
		xMax = this.getWidth();
		yMax = this.getHeight();
		
		xCoord = ((xMax-5)/4);
		yCoord = ((yMax-5)/2);
		
		for(int i=0; i<4; i++) {
			for(int j=0; j<2; j++) {
				Color c = colors[(i*2)+j];
				if(c.equals(blockColor)) {
					c = c.darker();
					c = c.darker();
					c = c.darker();
				}
				g.setColor(c);
				g.fillRect(5+(i*xCoord),5+(j*yCoord),xCoord-5,yCoord-5);
				
				if(c.equals(currentColor)) {
					g.setColor(Color.green);
				}
				else { 
					g.setColor(Color.black);
				}
				
				g.drawRect(5+(i*xCoord),5+(j*yCoord),xCoord-5,yCoord-5);
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
	
	//MouseListener
	public void mouseClicked(MouseEvent e) {}

	public void mouseReleased(MouseEvent e) {}

	public void mouseEntered(MouseEvent e) {}

	public void mouseExited(MouseEvent e) {}

	public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) {
			int iColor = (int) (e.getX()-5)/xCoord;
			int jColor = (int) (e.getY()-5)/yCoord;
			
			// System.out.println("x:"+iColor+" y:"+jColor);
			
			Color c = colors[(iColor*2)+jColor];
			if(!c.equals(blockColor)) {
				currentColor = c;
				pan.chooseColor(currentColor,command);
			}
		}
	}
}