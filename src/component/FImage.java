package component;

import vehicule.Vehicule;

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

public class FImage extends JPanel {
	
	private Dimension size = new Dimension(186,200);
	private Vehicule vec;
	private Image image;
	
	private double scale = 2;
	
	public FImage(){
		this.setPreferredSize(size);
		this.setBorder(BorderFactory.createLoweredBevelBorder());
		
	}
	
	public void paintComponent(Graphics g){
		
		Graphics2D g2D = (Graphics2D) g;
		
		if(ImageSprite.mapSprite != null && vec != null) {
			AffineTransform scale = new AffineTransform();
			scale.scale(getWidth()/45d,getHeight()/45d);
			scale.translate(0,0);
			g2D.drawImage(ImageSprite.mapSprite[0], scale, this);
		}
		else {
			g.setColor(Color.white);
			g.fillRect(0,0,this.getWidth(), this.getHeight());
		}
		
		if(this.vec != null) {
			double angleR = Math.toRadians(this.vec.getAngle());
			AffineTransform rotate = new AffineTransform();
			rotate.scale(scale,scale);
			rotate.translate((this.getWidth()-this.image.getWidth(null)*scale*1.25)/2, (this.getHeight()-this.image.getHeight(null)*scale*1.25)/2);
			rotate.rotate(angleR,image.getWidth(this)/2,image.getHeight(this)/2);
			
			g2D.drawImage(image, rotate, this);
		}
	}
	
	public void setVec(Vehicule v){
		this.vec = v;
		if(v != null) {
			this.image = v.getImage();
		}
		else {
			this.image = null;
		}
	}
	
}