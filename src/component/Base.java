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
	
	private int vie = 4;
	
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
		System.out.println("Vie de la base de "+this.joueur.getName()+" : "+this.vie);
	}
	
	public int getVie() {
		return this.vie;
	}
	
	public void draw(java.awt.Graphics g) {
		int tileSize = ImageSprite.tileSize;
		
		int x = 2+(posX*tileSize);
		int y = 2+(posY*tileSize);
		
		Graphics2D g2 = (Graphics2D) g;
		
		AffineTransform scale = new AffineTransform();
		scale.translate(x,y);
		scale.scale((double) (tileSize-1)/(img.getWidth(null)/5),(double) (tileSize-1)/(img.getHeight(null)/2));
		
		if(vie > 0) {
			g2.drawImage(img,scale,null);
		}
		
		g.setColor(Color.red);
		for(int i=0; i< vie; i++) {
			g.fillRect((x+3)+(15*i),y+3,7,7);
		}
	}
}