package component;

import joueur.Joueur;
import component.*;

import java.awt.Image;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

public class Base {

	private Image img;
	private Joueur joueur;
	private int posX;
	private int posY;
	
	private int baseTileX = 5, baseTileY = 2;
	
	private int vie = 4;	
	private int indExplose;
	
	public Base(int x, int y, Joueur jo, Image i) {
		this.joueur = jo;
		this.img = i;
		this.posX = x;
		this.posY = y;
	}
	
	public Joueur getJoueur() {
		return this.joueur;
	}
	
	public void attack() {
		if(this.vie > 0) {
			this.vie--;
		}
		System.out.println("Vie de la base : "+this.vie);
	}
	
	public int getVie() {
		return this.vie;
	}
	
	public void update() {
		indExplose++;
		if(indExplose >= 48) {
			indExplose = 47;
		}
	}
	
	public void setBaseTile(int x, int y) {
		this.baseTileX = x;
		this.baseTileY = y;
	}
	
	public void draw(java.awt.Graphics g) {
		int tileSize = ImageSprite.tileSize;
		
		int x = 2+(posX*tileSize);
		int y = 2+(posY*tileSize);
		
		Graphics2D g2 = (Graphics2D) g;
		
		AffineTransform scale = new AffineTransform();
		scale.translate(x,y);
		scale.scale((double) (tileSize-1)/(img.getWidth(null)/baseTileX),(double) (tileSize-1)/(img.getHeight(null)/baseTileY));
		
		if(indExplose < 5) {
			g2.drawImage(img,scale,null);
		}
		
		g.setColor(Color.red);
		for(int i=0; i< vie; i++) {
			g.fillRect((x+3)+(15*i),y+3,7,7);
		}
		
		if(vie <= 0) {
			g.drawImage(ImageSprite.baseExplosion[indExplose],(x-128)+(tileSize*baseTileX)/2,(y-128)+(tileSize*baseTileY)/2,null);
		}
	}
}