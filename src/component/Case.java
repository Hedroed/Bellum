package component;

import joueur.Joueur;
import vehicule.*;
import main.FDamier;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.geom.AffineTransform;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import java.awt.AlphaComposite;
// import java.awt.Composite;
import java.awt.Graphics2D;

public class Case extends JPanel{
	
	protected int xCoord, yCoord;
	// private int posX, posY;
	private boolean isRiver;
	private int idRiver;
	private int idEmpty;
	private boolean isRiverRamp;
	private boolean isBridge;
	private Vehicule vehicule;
	private Base base;
	private Vehicule flying;
	private Vehicule mainVec;
	private Obstacle obstacle;
	protected FDamier fDamier;
	
	private boolean cible = false;
	private boolean tir = false;
	private boolean select = false;
	private boolean deplacement = false;
	
	private boolean hover = false;
	
	public Case(int x, int y, FDamier fd) {
		this.setPreferredSize(new Dimension(FDamier.tileSize-1,FDamier.tileSize-1));
		this.xCoord = x;
		this.yCoord = y;
		this.fDamier = fd;
		this.idEmpty = (int)(Math.random()*2);
		
		this.setOpaque(false);
	}
	
	public void drawBack(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		
		int tileSize = FDamier.tileSize;
		int x = FDamier.posX+(xCoord*(tileSize));
		int y = FDamier.posY+((tileSize)*yCoord);
		
		AffineTransform scale = new AffineTransform();
		scale.translate(x,y);
		scale.scale((double) (tileSize-1)/64,(double) (tileSize-1)/64);
		
		//dessine le fond de la case
		if(isRiverRamp) {
			g2.drawImage(ImageSprite.mapSprite[2],scale,null);
		}
		else if(isRiver) {
			g2.drawImage(ImageSprite.mapSprite[6+idRiver],scale,null);
		}
		else {
			g2.drawImage(ImageSprite.mapSprite[idEmpty],scale,null);
		}
		
		if(isBridge) {
			if(idRiver == 1) {
				g2.drawImage(ImageSprite.mapSprite[4],scale,null);
			}
			else {
				g2.drawImage(ImageSprite.mapSprite[3],scale,null);
			}
		}
	}
	
	public void draw(Graphics g) {	
		int tileSize = FDamier.tileSize;
		int x = FDamier.posX+(xCoord*(tileSize));
		int y = FDamier.posY+((tileSize)*yCoord);
		
		//dessine le contour 
		if(this.hover && this.base == null) {
			g.setColor(new Color(255,255,255,140));
			g.fillRect(x, y, tileSize-1, tileSize-1);
		}
		if(this.deplacement) {
			g.setColor(Color.blue);
			g.drawRect(x,y,tileSize-2,tileSize-2);
			if(this.tir) {
				g.setColor(Color.red);
				g.drawRect(x+1,y+1,tileSize-4,tileSize-4);
			}
		}
		else if(this.cible) {
			g.setColor(Color.red);
			g.fillRect(x,y,tileSize-1,tileSize-1);
		}
		else if(this.tir) {
			g.setColor(Color.red);
			g.drawRect(x,y,tileSize-2,tileSize-2);
		}
		else if(this.select) {
			g.setColor(Color.green);
			g.drawRect(x,y,tileSize-2,tileSize-2);
		}
		
		//dessine les differents vehicule 
		if(vehicule != null) {
			vehicule.draw(g,x,y);
		}
		if(flying != null) {
			flying.draw(g,x,y);
		}
		else if(obstacle != null) {
			obstacle.draw(g,x,y);
		}
	}
	
	//retourn true si la case est vide
	public boolean isEmpty() {
		boolean ret = true;
		
		if(this.vehicule != null) {
			ret = false;
		}
		else if(this.obstacle != null){
			ret = false;
		}
		else if(this.isBase()){
			ret = false;
		}
		else if(this.flying != null) {
			ret = false;
		}
		return ret;
	}
	
	// methode de bordure
	public void setCible(boolean b){
		this.cible = b;
	}
	
	public boolean getCible() {
		return this.cible;
	}
	
	public void setTir(boolean b){
	
		this.tir = b;
	}
	
	public boolean getTir() {
		return this.tir;
	}
	
	public void setDeplacement(boolean b){
	
		this.deplacement = b;
	}
	
	public boolean getDeplacement() {
		return this.deplacement;
	}
	
	public void setSelect(boolean b){
	
		this.select = b;
	}
	
	public boolean getSelect() {
		return this.select;
	}
	
	// methode de Vehicule	
	public Vehicule getVehicule(){
		return vehicule;
	}
	
	public Vehicule getFlying() {
		return flying;
	}
	
	public void addVehicule(Vehicule v) {
		if(v != null) {
			if(v.isFlying()) {
				flying = v;
			}
			else {
				vehicule = v;
			}
		}
		else {
			System.out.println("addVehicule : Vehicule null");
		}
	}
	
	public void removeVehicule(Vehicule v) {
		if(v != null) {
			if(v.isFlying() && flying == v) {
				flying = null;
			}
			else if(vehicule == v) {
				vehicule = null;
			}
		}
		else {
			System.out.println("removeVehicule : Vehicule null");
		}
	}
	
	// methode d'obstacle
	public Obstacle getObstacle(){
		return this.obstacle;
	}
	
	public void setObstacle(Obstacle o){
		if (o != null){
			this.obstacle = o;
		}
	}
	
	public boolean isObtacle() {
		return (this.obstacle != null);
	}
	
	// methode pour la riviere
	public void setRiver() {
		this.isRiver = true;
	}
	
	public void setIdRiver(int i) {
		this.idRiver = i;
	}
	
	public boolean isRiver() {
		return this.isRiver;
	}
	
	public void setRiverRamp() {
		this.isRiverRamp = true;
	}
	
	public boolean isRiverRamp() {
		return this.isRiverRamp;
	}
	
	public void setBridge(boolean b) {
		this.isBridge = b;
		if(b) {
			this.isRiverRamp = false;
		}
		//+ ajout image pont
	}
	
	public boolean haveBridge() {
		return this.isBridge;
	}
	
	//autre
	public boolean isBase() {
		return (this.base != null);
	}
	
	public void setBase(Base b) {
		this.base = b;
	}
	
	public Base getBase() {
		return this.base;
	}
	
	public boolean isExit() {
		return false;
	}
	
	public int getXCoord() {
		return this.xCoord;
	}
	
	public int getYCoord() {
		return this.yCoord;
	}
	
	public String toString() {
		return "Case en "+this.xCoord+" : "+this.yCoord;
	}
	
	public void mouseEntered() {
		this.hover = true;
	}
	
	public void mouseExited() {
		this.hover = false;
	}
}