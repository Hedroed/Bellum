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
import java.awt.geom.AffineTransform;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.AlphaComposite;
import java.awt.Dimension;
import java.awt.image.BufferedImage;


public abstract class Vehicule {
	private Image image;
	private BufferedImage[] explosions;
	private boolean mort = false;
	private int ind;

	private int angle;
	private int posX = 1;
	private int posY = 1;
	protected FDamier pan;
	protected TypeVec type;
	private Joueur joueur;

	private int life;
	private int tirRestant;
	private int depRestant;
	private boolean actif = true;

	public Vehicule(FDamier f, Joueur jo, int a, TypeVec t) {
		// System.out.println("Creation vehicule "+t.name()+" ["+a+"]");
		this.pan = f;
		this.joueur = jo;
		this.angle = a;
		this.type = t;
		// this.setPreferredSize(new Dimension(ImageSprite.tileSize-1,ImageSprite.tileSize-1));

		this.life = t.getVieMax();
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
		int tileSize = ImageSprite.tileSize;
		AffineTransform rotate = new AffineTransform();
		rotate.translate(x+this.posX,y+this.posY);
		rotate.scale((double) (tileSize-3)/64,(double) (tileSize-3)/64);
		rotate.rotate(angleR,image.getWidth(null)/2,image.getHeight(null)/2);

		if(this.ind < 30) {
			g2D.drawImage(image, rotate, null);
		}
		//explosion si mort
		if(this.mort) {
			if(this.ind <= 81) {
				AffineTransform scale = new AffineTransform();
				scale.translate(x,y);
				scale.scale((double) (tileSize-3)/45,(double) (tileSize-3)/45);
				// scale.scale(1.2,1.2);
				g2D.drawImage(ImageSprite.explosion[this.ind],scale,null);
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
				g2D.fillRect(x+(38-(6*nb)),y+2,3,3);
				nb++;
			}
			
			g2D.setColor(Color.blue);
			for(int i = 0; i < this.depRestant; i++){
				g2D.fillRect(x+(38-(6*nb)),y+2,3,3);
				nb++;
			}

		}
		
		g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
	}
	
	public void moveTo(int x, int y){

		this.posX = x;
		this.posY = y;
	}

	//accesseur et modifieur
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
		return this.actif;
	}
	
	public void setActif(boolean b) {
		this.actif = b;
	}
	
	public Image getImage() {
		return this.image;
	}

	public void setImage(Image i) {
		this.image = i;
	}
	
	public void attaque() {
		this.life--;
		if(this.life <= 0) {
			this.mort = true;
			System.out.println("Vehicule "+this.type.name()+" mort");

			//animation mort
			PlaySound ps = new PlaySound("ressources/explosion.wav");
			ps.play();
			Thread t = new Thread(new AnimeExplosion(this.pan,this));
			t.start();
		}
		else {
			this.makeImage();
		}
	}
	
	public void deplacement(Case cD, Case cA) {
		int xx = cA.getXCoord() - cD.getXCoord(); 
		int yy = cA.getYCoord() - cD.getYCoord(); 
		
		int dep = (int) Math.sqrt((xx*xx)+(yy*yy));
		this.depRestant = this.depRestant - dep;
	}
	
	public int getDepRestant() {
		return this.depRestant;
	}
	
	public void tir() {
		this.tirRestant--;
	}
	
	public int getTirRestant() {
		return this.tirRestant;
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
	
	public void newCase(Case c) {}
	
	public class AnimeExplosion implements Runnable {
		private FDamier f;
		private Vehicule v;

		public AnimeExplosion(FDamier f, Vehicule v) {
			this.f = f;
			this.v = v;
		}

		public void run() {

			for(this.v.ind = 0;this.v.ind<81;this.v.ind++) {
				this.f.repaint();
				try {
				  Thread.sleep(10);
				} catch (InterruptedException e) {
				  e.printStackTrace();
				}
			}
			ind = 80;
			this.f.repaint();
			this.f.killMe(this.v);
		}
	}

	public int getLife() {
		return this.life;
	}

	public void enter() {
		
		
		Thread t = new Thread(new Runnable() {
			public void run() {
				int yDep = 0;
				int tileSize = ImageSprite.tileSize;
				
				if(angle == 0) {
					for(posY = tileSize; posY > 1; posY--) {
						pan.repaint();
						try {
						  Thread.sleep(15);
						} catch (InterruptedException e) {
						  e.printStackTrace();
						}
					}
				}
				else if(angle == 270) {
					for(posX = tileSize; posX > 1; posX--) {
						pan.repaint();
						try {
						  Thread.sleep(15);
						} catch (InterruptedException e) {
						  e.printStackTrace();
						}
					}
				}
				else if(angle == 90) {
					for(posX = -tileSize; posX < 1; posX++) {
						pan.repaint();
						try {
						  Thread.sleep(15);
						} catch (InterruptedException e) {
						  e.printStackTrace();
						}
					}
				}
				else {
					for(posY = -tileSize; posY < 1; posY++) {
						pan.repaint();
						try {
						  Thread.sleep(15);
						} catch (InterruptedException e) {
						  e.printStackTrace();
						}
					}
				}
				pan.repaint();

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
		
		return ret;
	}
}
