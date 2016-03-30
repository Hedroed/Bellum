package main;

import joueur.Joueur;
import component.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener; 
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Graphics;
import javax.swing.BorderFactory;
import javax.swing.JScrollPane;

import javax.swing.TransferHandler;
import glass.*;

import java.util.ArrayList;

public class FRessource extends JPanel implements ActionListener {
	
	private Dimension fSize = new Dimension(110,700);
	private FDamier fDamier;
	private JButton nextTurn = new JButton("Tour Suivant");
	private JPanel centerPane = new JPanel();
	
	private MyGlassPane glass;
	
	private Joueur[] players;
	private int joueurActif;
	
	private int tour;
	
	public FRessource(MyGlassPane g, Joueur[] players){
		this.setPreferredSize(fSize);
		this.setBackground(Color.black);
		this.setBorder(BorderFactory.createEtchedBorder());
		this.setLayout(new BorderLayout());
		
		this.players = players;

		this.tour = 0;
		this.glass = g;
		
		this.nextTurn.addActionListener(this);
		this.nextTurn.setFocusable(false);
		this.nextTurn.setPreferredSize(new Dimension(90,70));
		
		centerPane.setBackground(new Color(255,235,175));
		this.add(this.centerPane,BorderLayout.CENTER);//new JScrollPane(
		this.add(this.nextTurn,BorderLayout.SOUTH);
	}
	
	public void actionPerformed(ActionEvent e) {
		this.activeTurn();
	}
	
	public void setFDamier(FDamier f) {
		this.fDamier = f;
	}
	
	public void activeTurn() {
		players[joueurActif].deactiveAll();
		
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
		this.fDamier.repaint();
		this.repaint();
	}
	
	public void startGame() {
		if(tour == 0) {
			for(Joueur p : players) {
				p.deactiveAll();
			}
			joueurActif = (int) (Math.random()*players.length);
			System.out.println("Start with "+joueurActif);
			activeTurn();
		}
	}
	
	public void fillRessources() {
		this.centerPane.removeAll();
		ArrayList<TransferVec> list = players[joueurActif].getVecRestant();
		
		for(TransferVec tv : list){
			int nb = tv.getNbRestant();
			if(nb > 0) {
				this.centerPane.add(new DragImage(glass,tv,fDamier));
				// System.out.println("drag image en plus");
			}
		}
		this.centerPane.setVisible(false);
		this.centerPane.setVisible(true);
	}
	
	public Joueur getActiveJoueur() {
		return players[joueurActif];
	}
}