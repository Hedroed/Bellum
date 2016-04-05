package component;

import main.*;

import java.awt.Color;
import java.awt.Graphics;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class DragImage{
	
	private int width, height;
	private int posX, posY;
	
	private int defaultX, defaultY;
	
	private Image image;
	private TransferVec transfer;
	
	public DragImage(int x, int y, TransferVec t){
		transfer = t;
		posX = x;
		posY = y;
		width = 64;
		height = 74;
		
		BufferedImage bigImage = null;
		try {
			bigImage = ImageIO.read(new File(t.getType().getLien()));
			image = bigImage.getSubimage(0,0,64,64);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void draw(Graphics g){
		g.drawImage(image,posX,posY,null);
		g.setColor(new Color(128,0,0));
		g.setFont(g.getFont().deriveFont(13f));
		g.drawString("x"+this.transfer.getNbRestant(),posX+25,posY+74);
	}
	
	public void setPosition(int x, int y) {
		posX = x;
		posY = y;
	}
	
	public TransferVec getTransfer() {
		return this.transfer;
	}
	
	public void begin() {
		defaultX = posX;
		defaultY = posY;
	}
	
	public void restore() {
		posX = defaultX;
		posY = defaultY;
	}
	
	public int getX() {
		return posX;
	}
	
	public int getY() {
		return posY;
	}
}