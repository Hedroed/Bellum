package main;

import component.*;

import java.util.ArrayList;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Font;
import java.awt.FontMetrics;
import java.io.*;
import java.awt.event.*;

public class OptionState extends JPanel implements MouseListener{
	
	//game option with default values
	public static int musicVolume = -40;
	public static int soundVolume = -20;
	
	public static int sizeX = 1200;
	public static int sizeY = 900; 
	
	private File optionFile;
	
	private Fenetre fenetre;
	private Font f1;
	
	private Image marbre;
	
	public OptionState(Fenetre f) {
		this.fenetre = f;
		
		optionFile = new File("option");
		this.addMouseListener(this);
		
		marbre = ImageSprite.createImage("ressources/interface.png");
		
        try {
            f1 = Font.createFont(Font.TRUETYPE_FONT, new File("ressources/DALEK.ttf"));
			f1 = f1.deriveFont(20f);
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		optionSave();
	}
	
	public void optionSave() {
		
		BufferedReader in = null;
		
		try {
			in = new BufferedReader(new FileReader(optionFile));
			
			int nb = getOptionValue(in.readLine());
			if(nb != -1) {
				sizeX=nb;
			}
			nb = getOptionValue(in.readLine());
			if(nb != -1) {
				sizeY=nb;
			}
			nb = getOptionValue(in.readLine());
			if(nb != -1) {
				musicVolume=nb;
			}
			nb = getOptionValue(in.readLine());
			if(nb != -1) {
				soundVolume=nb;
			}
			
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	private int getOptionValue(String s) {
		int i = s.indexOf(':');
		s = s.substring(i+1);
		return Integer.parseInt(s);
	}
	
	public void paintComponent(Graphics g) {
		int w = getWidth();
		int h = getHeight();
		int center = w/2;
		
		//generate background
		g.drawImage(ImageSprite.menuBackground,0,0,w,h,null);
		g.drawImage(marbre,center-340,0,680,h,null);
		
		g.setColor(new Color(100,50,8));
		// g.drawLine(center,80,center,h);
		g.drawLine(0,h-1,w-1,h-1);
		
		//set text property
		g.setFont(f1);
		FontMetrics metrics = g.getFontMetrics(f1);
		g.setColor(new Color(128,0,0));
		
		int length = metrics.stringWidth(sizeX+" x "+sizeY);
		g.drawString(sizeX+" x "+sizeY,center-(length/2),150);
		
		length = metrics.stringWidth("Music Volume : "+ musicVolume);
		g.drawString("Music Volume : "+musicVolume,center-(length/2),200);
		for(int i=0; i<10; i++) {
			if(-60+(i*5) <= musicVolume) {
				g.setColor(Color.white);
			}
			else {
				g.setColor(Color.gray);
			}
			g.fillRect((center-200)+(40*i),220,30,20);
		}
		
		g.setColor(new Color(128,0,0));
		length = metrics.stringWidth("Sound Volume : "+ soundVolume);
		g.drawString("Sound Volume : "+ soundVolume,center-(length/2),300);
		for(int i=0; i<10; i++) {
			if(-40+(i*5) <= soundVolume) {
				g.setColor(Color.white);
			}
			else {
				g.setColor(Color.gray);
			}
			g.fillRect((center-200)+(40*i),320,30,20);
		}
		
		//back buttun
		g.setColor(new Color(223,118,11));
		g.fillRect(center-130,h-55,260,30);
		g.setColor(new Color(128,0,0));
		g.drawString("Back",center-20,h-32);
		
	}
	
	public void save() {
		try {
			PrintWriter out = new PrintWriter(new FileWriter(optionFile),false);
			out.println("Longueur:"+sizeX);
			out.println("Largeur:"+sizeY);
			out.println("MusicVolume:"+musicVolume);
			out.println("SoundVolume:"+soundVolume);
			out.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	//MouseListener
	public void mouseClicked(MouseEvent e) {}

	public void mouseReleased(MouseEvent e) {}

	public void mouseEntered(MouseEvent e) {}

	public void mouseExited(MouseEvent e) {}

	public void mousePressed(MouseEvent e) {
		int x=e.getX();
		int y=e.getY();
		int center = this.getWidth()/2;
		int h = this.getHeight();
		
		int volX = (x-(center-200))/40;
		
		if(x >= center-200 && x < center+200 && y >= 220 && y < 240) {
			musicVolume = -60+(volX*5);
		}
		else if(x >= center-200 && x < center+200 && y >= 320 && y < 340) {
			soundVolume = -40+(volX*5);
		}
		else if(x >= center-130 && x < center+130 && y >= h-55 && y < h-25) {
			System.out.println("back button");
			fenetre.goMenu();
		}
		save();
		repaint();
	}
	
}