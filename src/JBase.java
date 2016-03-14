import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener; 
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class JBase extends JPanel {
	
	private Dimension size = new Dimension(231,92);
	private Image image;
	
	public JBase(){
		this.setPreferredSize(size);
		
		try {
			this.image = ImageIO.read(new File("base.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void paintComponent(Graphics g){
		
		// g.setColor(Color.white);
		// g.fillRect(0,0,this.getWidth(), this.getHeight());
		
		Graphics2D g2D = (Graphics2D) g;
		
		double angleR = Math.toRadians(0);
		AffineTransform rotate = new AffineTransform();
		rotate.translate(0,0);
		rotate.rotate(angleR,image.getWidth(this)/2,image.getHeight(this)/2);
		
		g2D.drawImage(image, rotate, this);
		
	}
	
	public int getX() {
		return 95;
	}
	
	public int getY() {
		return 603;
	}
	
}