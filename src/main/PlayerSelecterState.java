package main;

import joueur.*;
import component.*;
import vehicule.Mangouste;

import javax.swing.JPanel;
import java.awt.Dimension;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.BorderFactory;
import java.awt.FlowLayout;
import javax.swing.JSlider;
import java.awt.Color;

public class PlayerSelecterState extends JPanel{
	
	private JPanel[] playerSelecter;
	private FImage [] fImages;
	private JTextField[] nameSelecter;
	private MyColorChooser[] colorSelecter;
	
	private JSlider slider;
	
	public PlayerSelecterState(int players) {
		
		playerSelecter = new JPanel[players];
		fImages = new FImage[players];
		nameSelecter = new JTextField[players];
		colorSelecter = new MyColorChooser[players];
		
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		Color color = new Color(255,235,175);
		int i=0;
		for(JPanel pan : playerSelecter) {
			pan = new JPanel();
			pan.setBackground(color);
			fImages[i] = new FImage();
			fImages[i].setVec(new Mangouste(0,null,new Joueur("",null,null)));
			nameSelecter[i] = new JTextField();
			colorSelecter[i] = new MyColorChooser(this);
			
			pan.setLayout(new BoxLayout(pan, BoxLayout.LINE_AXIS));
			pan.setBorder(BorderFactory.createEtchedBorder());
			
			JPanel j = new JPanel(new FlowLayout(FlowLayout.CENTER,30,30));
			j.add(fImages[i]);
			j.setBackground(color);
			pan.add(j);
			
			JPanel m = new JPanel();
			m.setBackground(color);
			m.setLayout(new BoxLayout(m, BoxLayout.PAGE_AXIS));
			m.setPreferredSize(new Dimension(250,250));
			JPanel n = new JPanel(new FlowLayout(FlowLayout.CENTER,0,20));
			n.setBackground(color);
			JPanel c = new JPanel();
			c.setBackground(color);
			
			JLabel label = new JLabel("Nom Joueur"+(i+1)+" :");
			nameSelecter[i].setPreferredSize(new Dimension(200, 30));
			// n.setBackground(Color.red);
			n.add(label);
			n.add(nameSelecter[i]);
			label = new JLabel("option Joueur"+(i+1)+" :");
			slider = new JSlider(40,55,46);
			n.add(label);
			n.add(slider);
			m.add(n);
			
			label = new JLabel("Couleur Joueur"+(i+1)+" :");
			colorSelecter[i].setCommand(i);
			colorSelecter[i].setPreferredSize(new Dimension(270, 120));

			c.add(label);
			c.add(colorSelecter[i]);
			m.add(c);
			
			pan.add(m);
			
			this.add(pan);
			i++;
		}
	}
	
	public void chooseColor(Color c, int i) {
		
		fImages[i].setVec(new Mangouste(0,null,new Joueur("",c,null)));
		
		for(MyColorChooser mcc: colorSelecter) {
			if(mcc != colorSelecter[i]) {
				mcc.block(c);
			}
		}
		
		repaint();
	}
	
	public Joueur[] getJoueur() {
		
		ImageSprite.tileSize = (int) slider.getValue();
		
		Joueur[] players = new Joueur[nameSelecter.length];
		for(int i=0; i<nameSelecter.length; i++) {
			players[i] = new Joueur(nameSelecter[i].getText(),colorSelecter[i].getColor(),null);
		}
		
		return players;
	}
}