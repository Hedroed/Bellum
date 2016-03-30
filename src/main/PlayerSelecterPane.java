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
		vec = new Mangouste(0,null,player);
		
		posX = x;
		posY = y;
		
		try {
            f1 = Font.createFont(Font.PLAIN, new File("ressources/DALEK.ttf"));
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		
		// Color color = new Color(255,235,175);
		// int i=0;
		// for(JPanel pan : playerSelecter) {
			// pan = new JPanel();
			// pan.setBackground(color);
			// fImages[i] = new FImage();
			// fImages[i].setVec(new Mangouste(0,null,new Joueur("",null,null)));
			// nameSelecter[i] = new JTextField();
			// colorSelecter[i] = new MyColorChooser(this);
			// System.out.println("2");
			// pan.setLayout(new BoxLayout(pan, BoxLayout.LINE_AXIS));
			// pan.setBorder(BorderFactory.createEtchedBorder());
			
			// JPanel j = new JPanel(new FlowLayout(FlowLayout.CENTER,30,30));
			// j.add(fImages[i]);
			// j.setBackground(color);
			// pan.add(j);
			
			// JPanel m = new JPanel();
			// m.setBackground(color);
			// m.setLayout(new BoxLayout(m, BoxLayout.PAGE_AXIS));
			// m.setPreferredSize(new Dimension(250,250));
			// JPanel n = new JPanel(new FlowLayout(FlowLayout.CENTER,0,20));
			// n.setBackground(color);
			// JPanel c = new JPanel();
			// c.setBackground(color);
			
			// JLabel label = new JLabel("Nom Joueur"+(i+1)+" :");
			// nameSelecter[i].setPreferredSize(new Dimension(200, 30));
			// n.setBackground(Color.red);
			// n.add(label);
			// n.add(nameSelecter[i]);
			// label = new JLabel("option Joueur"+(i+1)+" :");
			// slider = new JSlider(40,55,46);
			// n.add(label);
			// n.add(slider);
			// m.add(n);
			
			// label = new JLabel("Couleur Joueur"+(i+1)+" :");
			// colorSelecter[i].setCommand(i);
			// colorSelecter[i].setPreferredSize(new Dimension(270, 120));

			// c.add(label);
			// c.add(colorSelecter[i]);
			// m.add(c);
			// System.out.println("3");
			// pan.add(m);
			
			// this.add(pan);
			// i++;
		// }
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