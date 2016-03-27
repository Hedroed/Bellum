package main;

import component.*;
import joueur.*;
import vehicule.*;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.AlphaComposite;
import java.awt.Dimension;
import java.awt.image.BufferedImage;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import javax.swing.JSlider;
import javax.swing.JScrollPane;
import java.awt.CardLayout;

public class SelecterPane extends JPanel {
	
	private CardLayout cL;
	
	private JPanel joueurContainer;
	private PlayerSelecterState playerState;
	private MapSelecterState mapState;
	
	public SelecterPane(Fenetre f) {
		cL = new CardLayout();
		
		this.setBackground(Color.black);
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		JPanel buttonPan = new JPanel();
		
		JButton exit = new JButton("Retour");
		exit.addActionListener(f);
		exit.setActionCommand("exit");
		buttonPan.add(exit);
		
		JButton ok = new JButton("Ok");
		ok.addActionListener(f);
		ok.setActionCommand("ok");
		buttonPan.add(ok);
		
		JButton joueur = new JButton("Joueur");
		joueur.addActionListener(f);
		joueur.setActionCommand("joueur");
		buttonPan.add(joueur);
		
		JButton map = new JButton("Map");
		map.addActionListener(f);
		map.setActionCommand("map");
		buttonPan.add(map);
		
		playerState = new PlayerSelecterState(2);
		
		mapState = new MapSelecterState();
		
		joueurContainer = new JPanel();
		joueurContainer.setLayout(cL);
		joueurContainer.add(new JScrollPane(playerState),"Joueur");
		joueurContainer.add(mapState,"Map");
		
		this.add(joueurContainer);
		this.add(buttonPan);
		
		setVisible(true);
	}
	
	public void showPan(String s) {
		this.cL.show(joueurContainer,s);
	}
	
	public Joueur getJoueur(int i) {
		return playerState.getJoueur(i);
	}
	
	public String getMap() {
		return mapState.getMap();
	}
	
	public void paintComponent(Graphics g) {
		//clear screen
		// g.setColor(Color.white);
		// g.fillRect(0,0,getWidth(),getHeight());
		
		//draw background
	}
	
}





