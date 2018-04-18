package com.hedroed.bellum.component;

import com.hedroed.bellum.main.*;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.Image;

public class AnimationShell extends Animation {
	
	private double dx,dy;
	private Image image;
	
	private double angle;
	
	public AnimationShell(double x, double y, double x2, double y2) {
		super(0,0);
		posX = x;
		posY = y;
		
		dx = x2-x;
		dy = y2-y;
		double len = Math.sqrt((dx*dx)+(dy*dy));
		
		angle = Math.atan(dy/dx) + Math.PI/2;
		if(x2 < x) {
			angle += Math.PI;
		}
		
		dx = (double) (dx/len)/2;
		dy = (double) (dy/len)/2;
		
		setTime((int)len*2);
		
		// System.out.println("Creation obus en case "+x+" :: "+y);
		image = ImageSprite.createImage("ressources/obus.png");
	}
	
	public AnimationShell(int x, int y, int x2, int y2,boolean b) {
		super(x,y);
		
		this.dx = x2;
		this.dy = y2;
		double len = Math.sqrt((dx*dx)+(dy*dy));
		
		angle = Math.atan(dy/dx) + Math.PI/2;
		if(x2 < x) {
			angle += Math.PI;
		}
		
		dx = (double) (dx/len)*15;
		dy = (double) (dy/len)*15;
		
		setTime((int)len/15);
		
		System.out.println("Creation obus en "+x+" :: "+y+" dx: "+dx+" dy: "+dy);
		image = ImageSprite.createImage("ressources/obus.png");
	}
	
	public void draw(Graphics g) {
		
	
		Graphics2D g2D= (Graphics2D) g;
		
		if(ind < time) {
			AffineTransform trans = new AffineTransform();
			
			// System.out.println("draw obus en "+posX*FDamier.tileSize+" :: "+posY*FDamier.tileSize);
			
			
			trans.translate((posX*FDamier.tileSize)-4,(posY*FDamier.tileSize)-4);
			trans.scale(0.5,0.5);
			trans.rotate(angle,8,8);
			
			
			g2D.drawImage(image,trans,null);
			
			ind++;
			posX = posX+dx;
			posY = posY+dy;
			
		}
		
	}
	
}