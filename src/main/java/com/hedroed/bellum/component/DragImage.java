package com.hedroed.bellum.component;

import com.hedroed.bellum.main.*;

import java.awt.Color;
import java.awt.Graphics;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

public class DragImage{
	
	private int width, height;
	private double posX, posY;
	private int reelX, reelY;
	private double dx,dy;
	
	private int defaultX, defaultY;
	
	private Image image;
	private TransferVec transfer;
	
	public DragImage(int x, int y, TransferVec t){
		transfer = t;
		posX = x;
		posY = y;
		reelX = x;
		reelY = y;
		width = 64;
		height = 74;
		
		BufferedImage bigImage = null;

		try {
		    InputStream in = getClass().getResourceAsStream("/ressources/"+t.getName()+".png"); 
			bigImage = ImageIO.read(in);
			image = bigImage.getSubimage(0,0,64,64);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void draw(Graphics g){
		
		if(Math.abs(posX-reelX) > 5 || Math.abs(posY-reelY) > 5) {
			posX += dx;
			posY += dy;
		}
		else {
			posX = reelX;
			posY = reelY;
			dx = 0;
			dy = 0;
		}
		
		g.drawImage(image,(int)posX,(int)posY,null);
		g.setColor(new Color(128,0,0));
		g.setFont(g.getFont().deriveFont(13f));
		g.drawString("x"+this.transfer.getNbRestant(),(int)posX+25,(int)posY+74);
	}
	
	public void setPosition(int x, int y) {
		posX = x;
		posY = y;
		reelX = x;
		reelY = y;
	}
	
	public TransferVec getTransfer() {
		return this.transfer;
	}
	
	public void begin() {
		defaultX = reelX;
		defaultY = reelY;
	}
	
	public void restore() {
		reelX = defaultX;
		reelY = defaultY;
		dx = (reelX - posX)/10;
		dy = (reelY - posY)/10;
	}
	
	public void defaultPos() {
		reelX = defaultX;
		reelY = defaultY;
		posX = defaultX;
		posY = defaultY;
	}
	
	public int getX() {
		return (int)posX;
	}
	
	public int getY() {
		return (int)posY;
	}
}