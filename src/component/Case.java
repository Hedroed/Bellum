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
import java.awt.Composite;
import java.awt.Graphics2D;

public class Case extends JPanel implements MouseListener{
	
	private int xCoord, yCoord;
	private int posX, posY;
	private boolean isRiver;
	private boolean isRiverRamp;
	private boolean isBridge;
	private Vehicule vehicule;
	private Base base;
	private Helicopter helicopter;
	private Vehicule mainVec;
	private Obstacle obstacle;
	protected FDamier fDamier;
	
	private boolean cible = false;
	private boolean tir = false;
	private boolean select = false;
	private boolean deplacement = false;
	
	private boolean hover = false;
	
	private Composite transparence;
	
	public Case(int x, int y, FDamier fd) {
		// this.setPreferredSize(new Dimension(43,43));
		this.xCoord = x;
		this.yCoord = y;
		this.fDamier = fd;
		// this.posX = 12+x*44;
		// this.posY = 18+y*44;
		// this.setBackground(Color.white);
		this.addMouseListener(this);
		
		setOpaque(false);
		transparence = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.55f);
		
	}
	
	public void paintComponent(Graphics g){
		
		// System.out.println("repaint case");
		
		Graphics2D g2d = (Graphics2D) g;
		
		if(this.hover) {
			g2d.setComposite(transparence);
			g2d.setColor(Color.white);
			g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
		}
		
		if(this.deplacement) {
			g.setColor(Color.blue);
			g.drawRect(0,0,this.getWidth()-1,this.getHeight()-1);
			if(this.tir) {
				g.setColor(Color.red);
				// g.drawLine(0,0,this.getWidth()-1,this.getHeight()-1);
				// g.drawLine(0,this.getWidth()-1,this.getHeight()-1,0);
				g.drawRect(1,1,this.getWidth()-3,this.getHeight()-3);
			}
		}
		else if(this.cible) {
			g.setColor(Color.red);
			g.fillRect(0,0,this.getWidth(),this.getHeight());
		}
		else if(this.tir) {
			g.setColor(Color.red);
			g.drawRect(0,0,this.getWidth()-1,this.getHeight()-1);
		}
		else if(this.select) {
			g.setColor(Color.green);
			g.drawRect(0,0,this.getWidth()-1,this.getHeight()-1);
		}
		
		if(this.isRiver && !isBridge) {
			g.setColor(Color.blue);
			g.drawRect(10,10,this.getWidth()-21,this.getHeight()-21);
			g.drawRect(15,15,this.getWidth()-31,this.getHeight()-31);
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
		else if(this.helicopter != null) {
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
		return this.mainVec;
	}
	
	public void placeVehicule(Vehicule v) {
		if (v != null){
			
			// this.removeAll();
			this.vehicule = v;
			this.add(v);
			v.enter();
			this.mainVec = v;
		}
	}
	
	public void setVehicule(Vehicule v){
		
		if(this.vehicule != null) {
			this.remove(this.vehicule);
		}
		this.vehicule = v;
		if (v != null){
			this.add(v);
			this.mainVec = v;
		}
		else {
			this.setDefaultMainVec();
		}
	}
	
	public boolean isVehicule() {
		return (this.vehicule != null);
	}
	
	
	
	private void setDefaultMainVec() {
		if(this.helicopter != null) {
			this.mainVec = this.helicopter;
		}
		else if(this.vehicule != null) {
			this.mainVec = this.vehicule;
		}
		else {
			this.mainVec = null;
		}
	}
	
	// methode d'obstacle
	public Obstacle getObstacle(){
		return this.obstacle;
	}
	
	public void setObstacle(Obstacle o){
		if (o != null){
			this.removeAll();
			this.obstacle = o;
			this.add(o);
		}
	}
	
	public boolean isObtacle() {
		return (this.obstacle != null);
	}
	
	// methode d'helicopter
	public Helicopter getHelicopter(){
		return this.helicopter;
	}
	
	public void setHelicopter(Helicopter h){
		if(this.helicopter != null) {
			this.remove(this.helicopter);
		}
		this.helicopter = h;
		if (h != null){
			this.add(h);
			this.mainVec = h;
			if(this.vehicule != null) {
				this.remove(this.vehicule);
				this.add(this.vehicule);
			}
		}
		else {
			this.setDefaultMainVec();
		}
	}
	
	public void placeHelicopter(Helicopter h) {
		if (h != null){
			
			// this.removeAll();
			this.helicopter = h;
			this.add(h);
			h.enter();
			this.mainVec = h;
		}
	}
	
	public boolean isHelicopter() {
		return (this.helicopter != null);
	}
	
	// methode pour la riviere
	public void setRiver() {
		this.isRiver = true;
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
	
	//Mouse Listener
	public void mouseClicked(MouseEvent e) {}

    public void mouseReleased(MouseEvent e) {}

    public void mouseEntered(MouseEvent e) {
		this.hover = true;
		this.repaint();
	}

    public void mouseExited(MouseEvent e) {
		this.hover = false;
		this.repaint();
	}

    public void mousePressed(MouseEvent e) {
		// System.out.println("Clique sur case en "+this.xCoord+" : "+this.yCoord);
		
		if(this.vehicule != null && this.helicopter != null) {
			if(e.getButton() == MouseEvent.BUTTON1) {
				this.mainVec = this.vehicule;
			}
			else {
				this.mainVec = this.helicopter;
			}
		}
		
		this.fDamier.caseClicked(this);
		
    }
}