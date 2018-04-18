package com.hedroed.bellum.vehicule;

import com.hedroed.bellum.joueur.*;
import com.hedroed.bellum.component.*;
import com.hedroed.bellum.main.*;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class Walker extends Vehicule{

	//constante du sprite
	public final int width = 64;
	public final int height = 64;
	public final int rows = 1;
	public final int cols = 2;
	

	public Walker(int a, FDamier f, Joueur jo) {
		// System.out.println("Creation mangouste");
		super(f, jo, a, TypeVec.walker);
		weapon = new ShotgunWeapon(1);
		explosionScale = -4;
		this.makeImage();
	}
	
	public boolean canMove(Case c) {
		boolean ret = false;
		
		if(c.getVehicule() != null){
			if(c.getVehicule().getJoueur() == this.getJoueur() && c.getVehicule().getType() == TypeVec.turret) {
				ret = true;
			}
			else {
				ret = false;
			}
		}
		else if(c.isRiver() && !c.haveBridge()) {
			ret = c.isRiverRamp();
		}
		else if(c.isBase()) {
			if(c.getBase().getJoueur() != this.getJoueur()) {
				ret = true;
			}
		}
		else if(c.getFlying() != null && c.getFlying().getJoueur() == this.getJoueur()) {
			ret = true;
		}
		else if(c.isEmpty()){
			ret = true;
		}
		
		return ret;
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
		
		this.setImage(image);
	}
	
	public boolean canShoot(Case c) {
		boolean ret = super.canShoot(c);
		
		if(ret && c.isBase()) {
			if(getDepRestant() > 0) {
				ret = false;
			}
		}
		
		return ret;
	}
	
	public void deplacement(Case cD, Case cA) {
		boolean ret = true;
		
		if(cA.getVehicule() != null && cA.getVehicule().getType() == TypeVec.turret) {
			
			System.out.println("active tourelle");
			int life = cA.getVehicule().getLife();
			
			coordX = cA.getXCoord();
			coordY = cA.getYCoord();
			
			ActiveTurret newTurret = new ActiveTurret(this,life);
			newTurret.moveTo(coordX,coordY);
			newTurret.debutTour();
			getJoueur().addVec(newTurret);
			
			cA.addVehicule(newTurret);
			cD.removeVehicule(this);
			
			
			this.damier.select(newTurret);
		}
		else if(cA.isBase()) {
			
			System.out.println("attaque de base");
			cA.getBase().attack(0);
			cA.getBase().attack(0);
			
			cD.removeVehicule(this);
			this.damier.unselect();
		}
		else {
			super.deplacement(cD,cA);
		}
	}
}
