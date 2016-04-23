package component;

import joueur.Joueur;
import component.*;
import main.FDamier;

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
	private double indExplose;
	
	public Base(int x, int y, Joueur jo, Image i) {
		this.joueur = jo;
		this.img = i;
		this.posX = x;
		this.posY = y;
	}
	
	public Joueur getJoueur() {
		return this.joueur;
	}
	
	public void attack(int n) {
		this.vie--;
		if(this.vie <= 0) {
			if(n != -1) {
				indExplose = -n;
			}
			else {
				indExplose = 0;
			}
		}
		System.out.println("Vie de la base : "+this.vie);
	}
	
	public void kill() {
		vie = 0;
		indExplose = 0;
	}
	
	public int getVie() {
		return this.vie;
	}
	
	public boolean isDead() {
		return vie <= 0;
	}
	
	public void setBaseTile(int x, int y) {
		this.baseTileX = x;
		this.baseTileY = y;
	}
	
	public void draw(java.awt.Graphics g) {
		int tileSize = FDamier.tileSize;
		int x = posX*tileSize;
		int y = posY*tileSize;
		
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
			indExplose+=0.5;
			if(indExplose < 48 && indExplose >= 0) {
				g.drawImage(ImageSprite.baseExplosion[(int)indExplose],(x-128)+(tileSize*baseTileX)/2,(y-128)+(tileSize*baseTileY)/2,null);
			}
		}
	}
}