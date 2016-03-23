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

public class SelecterPane extends JPanel {
	
	private BufferedImage background;
	private FImage img1;
	private FImage img2;
	private JTextField name1;
	private JTextField name2;
	
	private MyColorChooser mcc1;
	private MyColorChooser mcc2;
	
	private JButton ok;
	private JButton exit;
	
	public SelecterPane(Fenetre f) {
		
		this.setBackground(Color.black);
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		JPanel selecJoueur1 = new JPanel();
		JPanel selecJoueur2 = new JPanel();
		JPanel buttonPan = new JPanel();
		
		selecJoueur1.setLayout(new BoxLayout(selecJoueur1, BoxLayout.LINE_AXIS));
		selecJoueur2.setLayout(new BoxLayout(selecJoueur2, BoxLayout.LINE_AXIS));
		selecJoueur1.setBorder(BorderFactory.createEtchedBorder());
		selecJoueur2.setBorder(BorderFactory.createEtchedBorder());
		
		img1 = new FImage();
		img2 = new FImage();
		
		JPanel j = new JPanel(new FlowLayout(FlowLayout.CENTER,30,30));
		j.add(img1);
		selecJoueur1.add(j);
		
		j = new JPanel(new FlowLayout(FlowLayout.CENTER,30,30));
		j.add(img2);
		selecJoueur2.add(j);
		
		JPanel m = new JPanel();
		m.setLayout(new BoxLayout(m, BoxLayout.PAGE_AXIS));
		m.setPreferredSize(new Dimension(300,400));
		JPanel n = new JPanel(new FlowLayout(FlowLayout.CENTER,0,20));
		JPanel c = new JPanel();
		
		JLabel label = new JLabel("Nom Joueur1 : ");
		name1 = new JTextField();
		name1.setPreferredSize(new Dimension(200, 30));
		// n.setBackground(Color.red);
		n.add(label);
		n.add(name1);
		label = new JLabel("option Joueur1 :");
		n.add(label);
		m.add(n);
		
		label = new JLabel("Couleur Joueur1 :");
		mcc1 = new MyColorChooser(this);
		mcc1.setCommand("img1");
		mcc1.setPreferredSize(new Dimension(270, 120));
		// c.setLayout(new BoxLayout(c, BoxLayout.PAGE_AXIS));
		c.add(label);
		c.add(mcc1);
		m.add(c);
		
		selecJoueur1.add(m);
		
		
		m = new JPanel();
		m.setLayout(new BoxLayout(m, BoxLayout.PAGE_AXIS));
		m.setPreferredSize(new Dimension(300,400));
		n = new JPanel(new FlowLayout(FlowLayout.CENTER,0,20));
		c = new JPanel();
		
		label = new JLabel("Nom Joueur2 : ");
		name2 = new JTextField();
		name2.setPreferredSize(new Dimension(200, 30));
		// n.setBackground(Color.red);
		n.add(label);
		n.add(name2);
		m.add(n);
		
		label = new JLabel("Couleur Joueur2 :");
		mcc2 = new MyColorChooser(this);
		mcc2.setCommand("img2");
		mcc2.setPreferredSize(new Dimension(270, 120));
		// c.setLayout(new BoxLayout(c, BoxLayout.PAGE_AXIS));
		c.add(label);
		c.add(mcc2);
		m.add(c);
		
		selecJoueur2.add(m);
		
		exit = new JButton("Retour");
		exit.addActionListener(f);
		exit.setActionCommand("exit");
		buttonPan.add(exit);
		
		ok = new JButton("Ok");
		ok.addActionListener(f);
		ok.setActionCommand("ok");
		buttonPan.add(ok);
		
		this.add(selecJoueur1);
		this.add(selecJoueur2);
		this.add(buttonPan);
		
		//load background
		// try {
			// background = ImageIO.read(new File("ressources/background.png"));
		// } catch (IOException e) {
			// e.printStackTrace();
		// }
		// Font police = new Font("Arial", Font.BOLD, 14);
		img1.setVec(new Mangouste(0,null,new Joueur("",null,null)));
		img2.setVec(new Mangouste(0,null,new Joueur("",null,null)));
		
		setVisible(true);
	}
	
	public void chooseColor(Color c, String s) {
		if(s.equals("img1")){
			img1.setVec(new Mangouste(0,null,new Joueur("",c,null)));
			mcc2.block(c);
		}
		else if(s.equals("img2")){
			img2.setVec(new Mangouste(0,null,new Joueur("",c,null)));
			mcc1.block(c);
		}
		repaint();
	}
	
	public Joueur getJoueur(int i) {
		if(i == 1) {
			return new Joueur(name1.getText(),mcc1.getColor(),null);
		}
		else if(i == 2) {
			return new Joueur(name2.getText(),mcc2.getColor(),null);
		}
		return null;
	}
	
	public void paintComponent(Graphics g) {
		//clear screen
		// g.setColor(Color.white);
		// g.fillRect(0,0,getWidth(),getHeight());
		
		//draw background
	}
	
}





