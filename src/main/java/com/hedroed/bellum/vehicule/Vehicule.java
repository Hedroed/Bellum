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
import java.awt.geom.AffineTransform;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.AlphaComposite;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.awt.event.MouseEvent;

public abstract class Vehicule {
	
	private static PlaySound soundExplose = new PlaySound("ressources/explosion.wav",OptionState.soundVolume);
	
	private Image image;
	private boolean mort = false;
	private int ind;

	private int angle;
	private int posX = 1;
	private int posY = 1;
	protected int coordX, coordY;
	protected FDamier damier;
	protected TypeVec type;
	private Joueur joueur;

	protected int life;
	private int tirRestant;
	private int depRestant;
	private boolean actif = true;
	
	protected Weapon weapon;
	protected int explosionScale;
	
	public Vehicule(FDamier damier, Joueur jo, int a, TypeVec t) {
		// System.out.println("Creation vehicule "+t.name()+" ["+a+"]");
		this.damier = damier;
		this.joueur = jo;
		this.angle = a;
		this.type = t;

		this.life = t.getVieMax();
		
		explosionScale = 8;
	}

	public void draw(Graphics g, int x, int y){
		// System.out.println("repaint vehicule");
		
		Graphics2D g2D = (Graphics2D) g;
		
		if(!this.actif) {
			g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
			// g2D.setColor(new Color(50,50,50));
			// g2D.fillRect(0, 0, this.getWidth(), this.getHeight());
		}
		
		double angleR = Math.toRadians(this.angle);
		int tileSize = FDamier.tileSize;
		AffineTransform rotate = new AffineTransform();
		rotate.translate(x+this.posX,y+this.posY);
		rotate.scale((double) (tileSize-3)/64,(double) (tileSize-3)/64);
		rotate.rotate(angleR,image.getWidth(null)/2,image.getHeight(null)/2);

		
		g2D.drawImage(image, rotate, null);
		
		//explosion si mort
		if(this.mort) {
			if(ind < 30) {
				ind++;
			}
			if(ind >= 30) {
				dead();
			}
		}
		else {
			
			//dessin de la vie
			g2D.setColor(new Color(127,0,0));
			for(int i = 0; i < this.life; i++){
				g2D.fillRect(x+(2+6*i),y+(tileSize-5),3,3);
			}
			
			//pixel utilisation
			int nb = 0;
			
			g2D.setColor(Color.red);
			for(int i = 0; i < this.tirRestant; i++){
				g2D.fillRect(x+((tileSize-8)-(6*nb)),y+2,3,3);
				nb++;
			}
			
			g2D.setColor(Color.blue);
			for(int i = 0; i < this.depRestant; i++){
				g2D.fillRect(x+((tileSize-8)-(6*nb)),y+2,3,3);
				nb++;
			}

		}
		
		g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
	}
	
	public void drawEtat(Graphics g) {
		
		if(weapon != null) {
			weapon.draw(g);
		}
		g.translate(0,50);
		drawSpecial(g);
		
	}
	
	public void drawSpecial(Graphics g) {
		
	}
	
	public void pressSpecial(MouseEvent e) {
	}
	
	public void moveTo(int x, int y){
		
		this.coordX = x;
		this.coordY = y;
	}
	
	//accesseur et modifieur
	public int getCoordX() {
		return coordX;
	}
	
	public int getCoordY() {
		return coordY;
	}

	public void setX(int x){
		this.posX = x;
	}

	public void setY(int y){
		this.posY = y;
	}

	public int getX(){
		return this.posX;
	}

	public int getY(){
		return this.posY;
	}
	
	public void setAngle(int a){
		this.angle = a;
	}

	public int getAngle(){
		return this.angle;
	}
	
	public Joueur getJoueur() {
		return this.joueur;
	}
	
	public TypeVec getType() {
		return this.type;
	}

	public boolean isDead() {
		return this.mort;
	}

	public boolean isActif() {
		return this.actif && !isDead();
	}
	
	public Image getImage() {
		return this.image;
	}

	protected void setImage(Image i) {
		this.image = i;
	}
	
	public Weapon getWeapon() {
		return weapon;
	}
	
	public void attaque(int n) {
		this.life--;
		if(this.life <= 0) {
			this.mort = true;
			joueur.removeVec(this);
			System.out.println("Vehicule "+this.type.name()+" mort sur la case "+damier.getCaseCoord(coordX,coordY));
			
			//animation mort
			if(n != -1) {
				ind = -n;
				damier.addAnimation(new AnimationExplosion(coordX,coordY,explosionScale,n));
			}
			else {
				ind = 0;
				damier.addAnimation(new AnimationExplosion(coordX,coordY,explosionScale,0));
			}
			
		}
		else {
			this.makeImage();
		}
	}
	
	public void depDecount(Case cD, Case cA) {
		int xx = cA.getXCoord() - cD.getXCoord(); 
		int yy = cA.getYCoord() - cD.getYCoord(); 
		
		int dep = (int) Math.sqrt((xx*xx)+(yy*yy));
		this.depRestant = this.depRestant - dep;
	}
	
	public int getDepRestant() {
		return this.depRestant;
	}
	
	public int shoot(Case c) {
		int ret = 0;
		
		if(weapon != null) {
			Animation a = weapon.getAnimation(damier.getCaseCoord(coordX,coordY),c);
			if(a != null) {
				damier.addAnimation(a);
				ret = a.getTime();
			}
		}
		
		
		
		return ret;
	}
	
	public void tir() {
		this.tirRestant--;
	}
	
	public int getTirRestant() {
		return this.tirRestant;
	}
	
	public void calculeTir(Case center) {
		
		if(weapon != null && center != null) {
			if(actif && tirRestant > 0) {
				weapon.placeShooting(damier,center,this);
			}
			else if(!actif){
				weapon.placeFake(damier,center,this);
			}
		}
		else {
			System.out.println("calculeTir vehicule weapon ou center null");
		}
		
	}
	
	public void debutTour() {
		if(this.type.getPortee() > 0) {
			this.tirRestant = 1;
		}
		else {
			this.tirRestant = 0;
		}
		this.depRestant = this.type.getDep();
		this.actif = true;
	}
	
	public void deactive() {
		this.depRestant = 0;
		this.tirRestant = 0;
		this.actif = false;
	}
	
	public abstract void makeImage();
	
	public abstract boolean isFlying();

	public int getLife() {
		return this.life;
	}

	public void enter() {
		Thread t = new Thread(new Runnable() {
			public void run() {
				int yDep = 0;
				int tileSize = FDamier.tileSize;
				
				if(angle == 0) {
					for(posY = tileSize; posY > 1; posY--) {
						try {
						  Thread.sleep(15);
						} catch (InterruptedException e) {
						  e.printStackTrace();
						}
					}
				}
				else if(angle == 270) {
					for(posX = tileSize; posX > 1; posX--) {
						try {
						  Thread.sleep(15);
						} catch (InterruptedException e) {
						  e.printStackTrace();
						}
					}
				}
				else if(angle == 90) {
					for(posX = -tileSize; posX < 1; posX++) {
						try {
						  Thread.sleep(15);
						} catch (InterruptedException e) {
						  e.printStackTrace();
						}
					}
				}
				else {
					for(posY = -tileSize; posY < 1; posY++) {
						try {
						  Thread.sleep(15);
						} catch (InterruptedException e) {
						  e.printStackTrace();
						}
					}
				}

			}
		});
		t.start();
	}
	
	public boolean canMove(Case c) {
		boolean ret = false;
		
		if(c.isEmpty() && !c.isRiver()) {
			ret = true;
		}
		
		return ret;
	}
	
	public boolean canShoot(Case c) {
		boolean ret = false;
		
		if(c.isBase()) {
			if(c.getBase().getJoueur() != this.joueur) {
				ret = true;
			}
		}
		else if(c.getVehicule() != null) {
			if(c.getVehicule().getJoueur() != this.joueur) {
				ret = true;
			}
		}
		else if(c.getFlying() != null ) {
			if(c.getFlying().getJoueur() != this.joueur) {
				ret = true;
			}
		}
		
		return ret;
	}
	
	public void deplacement(Case cD, Case cA) {
		cA.addVehicule(this);
		cD.removeVehicule(this);
		coordX = cA.getXCoord();
		coordY = cA.getYCoord();
	}
	
	public void dead() {
		damier.getCaseCoord(coordX,coordY).removeVehicule(this);
		damier.calculeZones();
	}
}
