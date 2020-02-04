package com.hedroed.bellum.main;

import com.hedroed.bellum.joueur.*;
import com.hedroed.bellum.component.*;
import com.hedroed.bellum.vehicule.*;

import java.awt.Graphics;
import java.awt.Font;
import java.awt.Color;
import java.io.InputStream;
import java.io.IOException;
import java.awt.event.MouseEvent;

public class PlayerSelecterPane implements ColorChooseEvent,Drawable{
	
	private Vehicule vec;
	private MyColorChooser mcc;
	private Joueur player;
	
	private int posX, posY;
	private Font f1;
	
	public PlayerSelecterPane(int x, int y, Joueur player) {
		this.player = player;
		mcc = new MyColorChooser(x+130,y+20,200,80,this);
		vec = new Mangouste(0,null,player);
		
		posX = x;
		posY = y;
		
		try {
            InputStream in = getClass().getResourceAsStream("ressources/DALEK.ttf");
            f1 = Font.createFont(Font.PLAIN, in);
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
	
	public void colorChoose(Color c) {
		System.out.println("Couleur selectionne :"+c) ;
		
		player.setColor(mcc.getColor());
		vec.makeImage();
	}
	
	public int getX() {
		return posX;
	}
	
	public void setX(int x) {
		posX = x;
		mcc.setX(x+130);
	}
	
	public int getY() {
		return posY;
	}
	
	public void setY(int y) {
		posY = y;
		mcc.setY(y+20);
	}
	
	public void mousePressed(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		
		System.out.println("click en "+e.getY()+" posY en "+posY);
		
		if(x >= posX+130 && x < posX+330 && y >= posY+20 && y < posY+100) {
			System.out.println("click zone couleur");
			mcc.mousePressed(e);
		}
	}
}