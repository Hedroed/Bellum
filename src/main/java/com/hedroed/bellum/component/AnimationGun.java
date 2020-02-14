package com.hedroed.bellum.component;

import com.hedroed.bellum.main.*;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.Image;

public class AnimationGun extends Animation {
	
	private double dx,dy;
	private Image image;
	
	private double angle;
	
	public AnimationGun(double x, double y, double x2, double y2) {
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
		
		// System.out.println("Creation gun ball en "+x+" :: "+y);
		image = ImageSprite.createImage("/ressources/obus.png");
	}
	
	public void draw(Graphics g) {
		
	
		Graphics2D g2D= (Graphics2D) g;
		
		if(ind < time) {
			AffineTransform trans = new AffineTransform();
			
			// System.out.println("draw ball en "+posX*FDamier.tileSize+" :: "+posY*FDamier.tileSize);
			trans.translate((posX*FDamier.tileSize)-2,(posY*FDamier.tileSize)-2);
			trans.scale(0.25,0.25);
			trans.rotate(angle,8,8);
			
			
			g2D.drawImage(image,trans,null);
			
			ind++;
			posX = posX+dx;
			posY = posY+dy;
			
		}
		
	}
	
}