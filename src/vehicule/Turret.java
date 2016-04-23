package vehicule;

import joueur.*;
import component.*;
import main.*;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class Turret extends Vehicule{

	//constante du sprite
	public final int width = 64;
	public final int height = 64;
	public final int rows = 1;
	public final int cols = 1;
	

	public Turret(int a, FDamier f, Joueur jo) {
		// System.out.println("Creation mangouste");
		super(f, jo, a, TypeVec.turret);
		this.makeImage();
	}
	
	public boolean canMove(Case c) {
		return false;
	}
	
	public boolean isFlying() {
		return false;
	}
	
	public void makeImage() {
		BufferedImage bigImage = null;
		BufferedImage[] sprites = new BufferedImage[rows * cols];
		BufferedImage image = null;
		
		try {
			bigImage = ImageIO.read(new File(this.type.getLien()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.setImage(bigImage);
		
		/*
		for (int i = 0; i < rows; i++)
		{
			for (int j = 0; j < cols; j++)
			{
				sprites[(i * cols) + j] = bigImage.getSubimage(j * width,i * height,width,height);
			}
		}
		
		int ind = (this.type.getVieMax()-this.getLife())*2;
		
		if(this.getJoueur().getColor() != null) {
			//sprite impaire
			if( ind+1 < (this.rows * this.cols)) {
				image = sprites[ind+1];
			}
			else System.out.println("Erreur make image ");
		}
		else {
			//sprite paire
			if(ind < (this.rows * this.cols)) {
				image = sprites[ind];
			}
			else System.out.println("Erreur make image ");
		}
		
		//change les pixel violet en la couleur du joueur
		for (int i = 0; i < image.getWidth(); i++) {
			for (int j = 0; j < image.getHeight(); j++) {
				if(image.getRGB(i,j) == new Color(255, 0, 255, 255).getRGB()) {
					image.setRGB(i, j, this.getJoueur().getColor().getRGB());
				}
			}
		}
		
		this.setImage(image);*/
	}
}
