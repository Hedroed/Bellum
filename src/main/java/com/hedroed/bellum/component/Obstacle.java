package com.hedroed.bellum.component;

import com.hedroed.bellum.main.*;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.geom.AffineTransform;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Dimension;

import java.awt.image.BufferedImage;


public class Obstacle{

	public static final int ROCHER = 0;
	public static final int ARBRE = 1;

	private BufferedImage image;
	private int angle;
	private int posX = 1;
	private int posY = 1;
	// private JPanel pan;

	public Obstacle(int type) {
		// System.out.println("Creation obstacle");
		try {
			if(type == ROCHER) {
				this.image = ImageIO.read(new File("ressources/rocher.png"));
			}
			else {
				this.image = ImageIO.read(new File("ressources/tree.png"));
			}
			
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

	public void draw(Graphics g, int x, int y){
		// System.out.println("paint obstacle");
		
		Graphics2D g2 = (Graphics2D) g;
		
		int tileSize = FDamier.tileSize;
		AffineTransform scale = new AffineTransform();
		scale.translate(x,y);
		scale.scale((double) (tileSize-3)/image.getWidth(null),(double) (tileSize-3)/image.getHeight(null));
		
		g2.drawImage(image, scale, null);
	}

	//accesseur et modifieur

	public int getX(){
		return this.posX;
	}

	public int getY(){
		return this.posY;
	}
}
