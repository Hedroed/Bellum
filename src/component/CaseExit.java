package component;

import joueur.Joueur;
import main.*;

import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.Graphics;

public class CaseExit extends Case {
	
	private Exit exit;
	
	private Joueur joueur;
	private boolean canExitVehicule = false;
	
	public CaseExit(int x, int y, FDamier fd, Exit s, Joueur jo) {
		super(x, y, fd);
		this.exit = s;
		this.joueur = jo;
		
	}
	
	public CaseExit(int x, int y, FDamier fd) {
		super(x, y, fd);
		this.exit = null;
		this.joueur = null;
		
	}
	
	public boolean isExit() {
		return true;
	}
	
	public void setExit(int x, int y, int a) {
		this.exit = new Exit(x,y,a);
	}
	
	public Exit getExit() {
		return this.exit;
	}
	
	public void setJoueur(Joueur jo) {
		this.joueur = jo;
	}
	
	public Joueur getJoueur() {
		return this.joueur;
	}
	
	public void setCanExitVehicule(boolean b) {
		this.canExitVehicule = b;
	}
	
	public boolean isWhite() {
		return canExitVehicule;
	}
	
	public void draw(Graphics g){ 
		super.draw(g);
		
		// int tileSize = FDamier.tileSize;
		// int x = FDamier.posX+(xCoord*(tileSize));
		// int y = FDamier.posY+((tileSize)*yCoord);
	}
}