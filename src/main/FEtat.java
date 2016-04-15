package main;

import vehicule.*;
import component.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import org.json.*;
import java.util.Scanner;
import java.io.UnsupportedEncodingException;

import java.awt.Image;

public class FEtat{

	private Dimension size = new Dimension(200,700);
	private int width, height;
	private int posX, posY;
	
	private FDamier fDamier;
	
	private Vehicule vActive;
	private Case cActive;
	
	public FEtat(){
	
		posX = 0;
		posY = 0;
		width = 200;
		height = 700;
	}

	public void draw(Graphics g){
		
		Graphics2D g2D = (Graphics2D) g;
		
		g.setColor(new Color(255,235,175));
		g.fillRect(posX, posY, width, height);
		
		g.setColor(Color.black);
		g.drawRect(posX,posY,width,height);
		
		if(vActive != null) {
			
			double angleR = Math.toRadians(vActive.getAngle());
			AffineTransform rotate = new AffineTransform();
			rotate.scale(2,2);
			rotate.translate(20,10);
			rotate.rotate(angleR,32,32);
			g2D.drawImage(vActive.getImage(), rotate, null);
			// vActive.draw(g,20,20);
			
			g.setColor(new Color(128,0,0));
			String name = vActive.getType().name();
			g.drawString(name,20,200);
			
			g.drawString(vActive.getType().getPortee()+" cases",20,250);
			
			int dep = this.vActive.getType().getDep();
			String s = null;
			if(dep > 1) {
				s = dep+" cases";
			}
			else {
				s = dep+" case";
			}
			g.drawString(s,20,300);
		}
		else if(cActive != null){
			g.setColor(new Color(128,0,0));
			g.drawString(cActive.toString(),20,200);
		}
		
		
		//check buttun
		g.setColor(new Color(223,118,11));
		g.fillRect(posX+1,height-20,width-1,20);
		g.setColor(new Color(128,0,0));
		g.drawString("Afficher cases de tirs",posX+20,height-3);

	}
	
	public void setPosition(int x, int y, int w, int h) {
		this.posX = x;
		this.posY = y;
		this.width = w;
		this.height = h;
	}
	
	public void setFDamier(FDamier f){
		this.fDamier = f;
	}
	
	public void newSelect() {
		vActive = fDamier.getVehiculeSelect();
		cActive = fDamier.getCaseSelect();
	}

}
