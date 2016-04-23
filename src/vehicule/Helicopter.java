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
import java.awt.Graphics;
import java.awt.event.MouseEvent;

public class Helicopter extends Vehicule{

	//constante du sprite
	public final int width = 64;
	public final int height = 64;
	public final int rows = 1;
	public final int cols = 2;
	
	private boolean turretRelease;
	
	public Helicopter(int a, FDamier f, Joueur jo) {
		// System.out.println("Creation mangouste");
		super(f, jo, a, TypeVec.helicopter);
		weapon  = new CanonWeapon(3);
		turretRelease = false;
		this.makeImage();
	}
	
	public boolean canMove(Case c) {
		boolean ret = false;
		
		if(c.getFlying() == this) {
			ret = false;
		}
		else if(c.isBase()) {
			ret = false;
		}
		else if(c.getFlying() != null) {
			ret = false;
		}
		else if(c.isRiver() && !c.haveBridge()) {
			ret = true;
		}
		else if(c.isEmpty()){
			ret = true;
		}
		else if(c.getVehicule() != null) { //is other vehicule
			if(c.getVehicule().getJoueur() != this.getJoueur()) {
				ret = false;
			}
			else {
				ret = true;
			}
		}
		
		return ret;
	}
	
	public boolean isFlying() {
		return true;
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
	
	public void drawSpecial(Graphics g) {
		
		g.setColor(new Color(128,0,0));
		
		if(!turretRelease) {
			
			g.drawString("Turret can be launch",10,20);
			if(getTirRestant() >= 1) {
				g.fillRoundRect(35,30,120,50,10,10);
				g.setColor(new Color(255,235,175));
				g.drawString("Launch",67,62);
			}
		}
		else {
			g.drawString("No more Turret",10,20);
		}
		
	}
	
	public void pressSpecial(MouseEvent e) {
		
		if(!turretRelease && getTirRestant() >= 1) {
			Case c = damier.getCaseCoord(coordX,coordY);
			System.out.println("Case pour tourelle "+c);
			if(!c.isRiver() && !c.isBase() && !c.isObstacle() && c.getVehicule() == null) {
				Turret t = new Turret(getAngle(),damier,getJoueur());
				t.moveTo(coordX,coordY);
				c.addVehicule(t);
				tir();
				damier.calculeZones();
				turretRelease = true;
			}
		}
	}
}
