import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.geom.AffineTransform;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Dimension;

import java.awt.image.BufferedImage;


public class Obstacle extends JPanel{
	private BufferedImage image;
	private int angle;
	private int posX = 1;
	private int posY = 1;
	// private JPanel pan;

	public Obstacle() {
		// System.out.println("Creation obstacle");
		// this.pan = jp;
		this.setPreferredSize(new Dimension(43,43));
		try {
			this.image = ImageIO.read(new File("ressources/rocher.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//change les pixel noir en rouge
		// for (int i = 0; i < this.image.getWidth(); i++) {
			// for (int j = 0; j < this.image.getHeight(); j++) {
				//  if(this.image.getRGB(i,j) == Color.black.getRGB()) {
					// this.image.setRGB(i, j, new Color(255, 0, 0, 255).getRGB());
				// }
			// }
		// }
	}

	public void paintComponent(Graphics g){
		// System.out.println("paint obstacle");

		// g.clearRect(0, 0, this.getWidth(), this.getHeight());

		g.drawImage(image, 0, 0, this);
	}

	//accesseur et modifieur
	// public void setX(int x){
		// this.posX = x;
	// }

	// public void setY(int y){
		// this.posY = y;
	// }

	public int getX(){
		return this.posX;
	}

	public int getY(){
		return this.posY;
	}
}
