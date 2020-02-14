package com.hedroed.bellum.component;

import com.hedroed.bellum.main.*;

import java.awt.Graphics;
import java.awt.Image;

public class MyCheckButton implements Drawable {
	
	private Image img;
	private String text;
	private boolean check;
	
	private int posX,posY;
	private int width,height;
	
	public MyCheckButton(int x, int y, int w, int h, String text) {
		posX = x;
		posY = y;
		width = w;
		height = h;
		this.text = text;
		check = false;
		makeImage();
		
		System.out.println("cree check en "+posX+", "+posY);
	}
	
	private void makeImage() {
		if(check) {
			img = ImageSprite.createImage("/ressources/check1.png");
		}
		else {
			img = ImageSprite.createImage("/ressources/check0.png");
		}
	}
	
	public void setCheck(boolean b) {
		this.check = b;
	}
	
	public boolean isCheck() {
		return check;
	}
	
	public int getX() {
		return posX;
	}
	
	public void setX(int x) {
		posX = x;
	}
	
	public int getY() {
		return posY;
	}
	
	public void setY(int y) {
		posY = y;
	}
	
	public void draw(Graphics g) {
		g.drawString(text,posX+20,posY+5+(height/2));
		g.drawImage(img,posX,posY,null);
	}
	
	public void mousePressed(java.awt.event.MouseEvent e) {
		if(e.getX() >= posX && e.getX() < posX+width && e.getY() >= posY && e.getY() < posY+height) {
			check = !check;
			makeImage();
		}
	}
	
	
}