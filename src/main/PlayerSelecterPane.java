package main;

import joueur.*;
import component.*;
import vehicule.*;

import java.awt.Graphics;
import java.awt.Font;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.awt.event.MouseEvent;

public class PlayerSelecterPane{
	
	private Vehicule vec;
	private MyColorChooser mcc;
	private Joueur player;
	
	private int posX, posY;
	private Font f1;
	
	public PlayerSelecterPane(int x, int y, Joueur player) {
		this.player = player;
		mcc = new MyColorChooser(x+130,y+20,200,80);
		vec = new Mangouste(0,null,null,player);
		
		posX = x;
		posY = y;
		
		try {
            f1 = Font.createFont(Font.PLAIN, new File("ressources/DALEK.ttf"));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	public void draw(Graphics g) {
		f1 = f1.deriveFont(26f);
		g.setFont(f1);
		g.setColor(new Color(128,0,0));
		g.drawString(player.getName(),posX+380,posY+60);
		g.setColor(new Color(230,230,230));
		g.fillRect(posX+20,posY+10,100,100);
		
		g.drawImage(vec.getImage(), posX+20,posY+10,100,100, null);
		
		g.setColor(Color.black);
		g.drawLine(posX,posY,posX+600,posY);
		g.drawLine(posX,posY+120,posX+600,posY+120);
		
		mcc.draw(g);
	}
	
	public Joueur getPlayer() {
		return player;
	}
	
	public void mousePressed(MouseEvent e) {
		
		if(e.getX() >= posX+130 && e.getX() < posX+330 && e.getY() >= posY+20 && e.getY() < posY+100) {
			mcc.mousePressed(e);
			player.setColor(mcc.getColor());
			vec.makeImage();
		}
	}
}