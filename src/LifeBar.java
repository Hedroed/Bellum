import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener; 
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.BorderFactory;
import java.awt.geom.AffineTransform;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class LifeBar extends JPanel {
	
	private Dimension size = new Dimension(186,20);
	private int max;
	private int current;
	
	public LifeBar(){
		this.setPreferredSize(size);
		this.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		
	}
	
	public void paintComponent(Graphics g){
		
		g.setColor(Color.white);
		g.fillRect(0,0,this.getWidth(), this.getHeight());
		
		g.setColor(Color.red);
		double d = this.getWidth()*((double)this.current/(double)this.max);
		g.fillRect(0,0,(int) d, this.getHeight());
		
		g.setColor(Color.black);
		String s = this.current+"/"+this.max+" PV";
		g.drawString(s,(this.getWidth()-(s.length()*6))/2, (this.getHeight()+9)/2);
	}
	
	public void setMax(int m) {
		this.max = m;
	}
	
	public int getMax() {
		return this.max;
	}

	public void setCurrent(int c) {
		this.current = c;
	}
	
	public int getCurrent() {
		return this.current;
	}	
}