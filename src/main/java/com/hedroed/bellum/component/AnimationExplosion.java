package com.hedroed.bellum.component;

import com.hedroed.bellum.main.*;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;


public class AnimationExplosion extends Animation {
	
	private int scale;
	private Image[] image;
	
	public AnimationExplosion(int x, int y, int scale, int n) {
		super(x,y,81);
		
		System.out.println("Creation explosion en case "+x+" :: "+y);
		
		ind = -n;
		this.scale = scale;
		
		image = ImageSprite.explosion;
	}
	
	public void draw(Graphics g) {
		Graphics2D g2D= (Graphics2D) g;
		
		if(ind < time) {
			AffineTransform trans = new AffineTransform();
			trans.translate((posX*FDamier.tileSize)-(scale/2),(posY*FDamier. tileSize)-(scale/2));
			trans.scale((double) (scale+FDamier.tileSize)/45,(double) (scale+FDamier.tileSize)/45);
			if(ind >= 0) {
				g2D.drawImage(image[ind],trans,null);
			}
		
			ind++;
		}
		
	}
	
}