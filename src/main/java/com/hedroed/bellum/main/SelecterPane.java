package com.hedroed.bellum.main;

import com.hedroed.bellum.component.*;
import com.hedroed.bellum.joueur.*;
import com.hedroed.bellum.vehicule.*;

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
	
	private PlayersSelecterState playerState;
	
	private Fenetre fenetre;
	
	public SelecterPane(Fenetre f) {
		this.fenetre = f;
	}
	
	public void init(int nbPlayer) {
		removeAll();
		System.out.println("selecter pour :"+nbPlayer);
		this.setBackground(Color.black);
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		JPanel buttonPan = new JPanel();
		// buttonPan.setPreferredSize(new Dimension(10,50));
		
		// JButton exit = new JButton("Retour");
		// exit.addActionListener(fenetre);
		// exit.setActionCommand("exit");
		// buttonPan.add(exit);
		
		// JButton ok = new JButton("Ok");
		// ok.addActionListener(fenetre);
		// ok.setActionCommand("ok");
		// buttonPan.add(ok);
		
		playerState = new PlayersSelecterState(fenetre);
		playerState.init(nbPlayer);
		
		this.add(new JScrollPane(playerState));
		this.add(buttonPan);
		
		setVisible(true);
	}
	
	public Joueur[] getJoueur() {
		return playerState.getJoueur();
	}
	
	public void paintComponent(Graphics g) {
		//clear screen
		// g.setColor(Color.white);
		// g.fillRect(0,0,getWidth(),getHeight());
		
		//draw background
	}
	
}





