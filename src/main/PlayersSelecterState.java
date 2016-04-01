package main;

import component.*;
import joueur.*;
import vehicule.*;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Font;
import java.awt.event.*;

public class PlayersSelecterState extends JPanel implements MouseListener{
	private Fenetre fenetre;
	
	private Image background;
	private Image inter;
	
	private Joueur[] players;
	private int currentPlayer;
	
	private Font f1;
	private PlayerSelecterPane[] selecterTab;
	
	public PlayersSelecterState(Fenetre f) {
		this.fenetre = f;
		
		addMouseListener(this);
		
		inter = ImageSprite.createImage("ressources/interface.png");
		
        try {
            f1 = Font.createFont(Font.PLAIN, new File("ressources/DALEK.ttf"));
        } catch (Exception e) {
            e.printStackTrace();
        }
		
	}
	
	public void init(int nbPlayer) {
		this.players = new Joueur[nbPlayer];
		this.selecterTab = new PlayerSelecterPane[nbPlayer];
		
		currentPlayer = 0;
		System.out.println("init");
		for(int i=0; i<players.length; i++) {
			players[i] = new Joueur("Joueur "+(i+1),null,null);
			selecterTab[i] = new PlayerSelecterPane(getWidth()/2-300,80+(i*120),players[i]);
		}
		
	}
	
	public void paintComponent(Graphics g) {
		int w = getWidth();
		int h = getHeight();
		int center = w/2;
		
		//generate background
		g.drawImage(ImageSprite.menuBackground,0,0,w,h,null);
		g.drawImage(inter,center-340,0,680,h,null);
		
		f1 = f1.deriveFont(40f);
		g.setFont(f1);
		g.setColor(new Color(128,0,0));
		g.drawString("Players",center-80,50);
		System.out.println("current players = "+currentPlayer);
		
		for(PlayerSelecterPane p : selecterTab) {
			p.draw(g);
		}
		
		//back and start buttun
		f1 = f1.deriveFont(20f);
		g.setFont(f1);
		g.setColor(new Color(223,118,11));
		g.fillRect(center-130,h-55,260,30);
		g.fillRect(center-130,h-95,260,30);
		g.setColor(new Color(128,0,0));
		g.drawString("Back",center-20,h-32);
		g.drawString("Start Game",center-55,h-72);
		
	}
	
	public Joueur[] getJoueur() {
		return players;
	}
	
	//MouseListener
	public void mouseClicked(MouseEvent e) {}

	public void mouseReleased(MouseEvent e) {}

	public void mouseEntered(MouseEvent e) {}

	public void mouseExited(MouseEvent e) {}

	public void mousePressed(MouseEvent e) {
		System.out.println(e.paramString());
		int x=e.getX();
		int y=e.getY();
		int center = this.getWidth()/2;
		int h = this.getHeight();
		
		if(y >= 80 && y < (players.length*120)+80) {
			
			int iPlayer = (y-80)/120;
			selecterTab[iPlayer].mousePressed(e);
			System.out.println("player :"+iPlayer);
		}
		else if(x >= center-130 && x < center+130 && y >= h-55 && y < h-25) {
			System.out.println("back button");
			fenetre.goMap();
		}
		else if(x >= center-130 && x < center+130 && y >= h-95 && y < h-65) {
			System.out.println("strat button");
			fenetre.startGame();
		}
		
		repaint();
		// fenetre.goGame();
	}
}