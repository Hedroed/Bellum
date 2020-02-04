package com.hedroed.bellum.main;

import com.hedroed.bellum.joueur.Joueur;
import com.hedroed.bellum.component.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener; 
import java.awt.Graphics;
import javax.swing.JPanel;
import java.io.IOException;
import java.awt.event.MouseEvent;

import java.util.ArrayList;

public class FRessource {
	
	private int width, height;
	private int posX, posY;
	
	private FDamier fDamier;
	private JPanel pan;
	private ArrayList<DragImage> dragList;
	
	private Joueur[] players;
	private int joueurActif;
	
	private int tour;
	
	public FRessource(JPanel pan, Joueur[] players){
		this.pan = pan;
		posX = 600;
		posY = 0;
		width = 110;
		height = 700;
		
		dragList = new ArrayList<DragImage>();
		this.players = players;

		this.tour = 0;
		
	}
	
	public void setPosition(int x, int y, int w, int h) {
		this.posX = x;
		this.posY = y;
		this.width = w;
		this.height = h;
	}
	
	public void draw(Graphics g) {		
		g.setColor(new Color(255,235,175));
		g.fillRect(posX, posY, width, height);
		
		g.setColor(Color.black);
		g.drawRect(posX,posY,width,height);
		
		if(!dragList.isEmpty()) {
			for(DragImage di : dragList) {
				di.draw(g);
			}
		}
		
		//next buttun
		g.setFont(g.getFont().deriveFont(17f));
		g.setColor(new Color(223,118,11));
		g.fillRect(posX+1,height-60,width-1,60);
		g.setColor(new Color(128,0,0));
		g.drawString("Tour suivant",posX+4,height-25);
	}
	
	public void setFDamier(FDamier f) {
		this.fDamier = f;
	}
	
	public void activeTurn() {
		players[joueurActif].deactiveAll();
		this.fDamier.setDark(false);
		
		joueurActif++;
		if(joueurActif >= players.length) {
			joueurActif = 0;
		}
		
		System.out.println("- tour : " + this.tour);
		tour ++;
		players[joueurActif].activeAll();
		
		this.fillRessources();
		this.fDamier.unselect();
		
		this.fDamier.next();
	}
	
	public void startGame() {
		if(tour == 0) {
			for(Joueur p : players) {
				p.deactiveAll();
			}
			joueurActif = (int) (Math.random()*players.length);
			activeTurn();
		}
	}
	
	public void fillRessources() {
		dragList.clear();
		ArrayList<TransferVec> list = players[joueurActif].getVecRestant();
		
		int i=0;
		for(TransferVec tv : list){
			int nb = tv.getNbRestant();
			if(nb > 0) {
				dragList.add(new DragImage(posX+25,posY+20+(83*i),tv));
				i++;
			}
		}
	}
	
	public Joueur getActiveJoueur() {
		return players[joueurActif];
	}
	
	public void mousePressed(MouseEvent e) {
		int x = e.getX()-posX;
		int y = e.getY()-posY;
		
		if(x >= 0 && y >= 0 && x < width && y < height) {
			
			if(x >= 25 && x < 25+64 && y >= 20 && y < height-60) {
				int i = (int) (y-posY-20)/83;
				DragImage drag = dragList.get(i);//check depassement
				drag.begin();
				((GamePane)pan).setDragImage(drag,(x+posX)-drag.getX(),(y+posY)-drag.getY());
			}
			else if(x >= 1 && x < width && y >= height-60 && y < height){
				activeTurn();
			}
		}
	}
}