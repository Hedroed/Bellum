package component;

import joueur.Joueur;

import java.awt.Image;
import java.awt.Color;

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
		int x = 4+(posX*46);
		int y = 5+(posY*46);
		
		g.drawImage(img,x,y,null);
		
		g.setColor(Color.red);
		for(int i=0; i< vie; i++) {
			g.fillRect((x+3)+(15*i),y+3,7,7);
		}
	}
}