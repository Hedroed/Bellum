package main;

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

import java.awt.geom.AffineTransform;

public class MenuPane extends JPanel {
	
	
	private String title;
	private String[][] options = {{"Jouer ","Options","Quitter"},{"Continuer","Recommencer","Options","Quitter"}};
	
	private BufferedImage background;
	private PlaySound soundChange ;
	
	private Boolean gameRunning = false;
	
	private int current = 0;
	
	public MenuPane() {
		this.title = "Bellum";
		this.soundChange = new PlaySound("ressources/menu.wav",-30);
		this.setBackground(Color.black);
		
		
		//load background
		try {
			background = ImageIO.read(new File("ressources/bg_menu.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void down() {
		current++;
		
		int i=0;
		if(gameRunning) i=1;
		
		if(current >= options[i].length) {
			current = 0;
		}
		this.soundChange.play();
		this.repaint();
	}
	
	public void up() {
		current--;
		
		int i=0;
		if(gameRunning) i=1;
		
		if(current < 0) {
			current = options[i].length -1;
		}
		this.soundChange.play();
		this.repaint();
	}
	
	public void close() {
		this.soundChange.close();
	}
	
	public void setGameRunning(boolean b) {
		gameRunning = b;
	}
	
	public void paintComponent(Graphics g) {
		//clear screen
		g.setColor(Color.white);
		g.fillRect(0,0,getWidth(),getHeight());
		
		//draw background
		Graphics2D g2D = (Graphics2D) g;
		
		AffineTransform scale = new AffineTransform();
		System.out.println("scale h:"+(double)getWidth()/background.getWidth()+"  scale v:"+(double)getHeight()/background.getHeight());
		scale.scale((double)getWidth()/background.getWidth(),(double)getHeight()/background.getHeight());
		
		g2D.drawImage(background,scale,this);
		
		
		
		
		//draw text
		Font f1 = null;
        try {
            f1 = Font.createFont(Font.TRUETYPE_FONT, new File("ressources/DALEK.ttf"));
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		f1 = f1.deriveFont(80f);
		g.setColor(new Color(128,0,0));
		g.setFont(f1);
		
		g.drawString(title,(getWidth()-(title.length()*40))/2,90);
		
		f1 = f1.deriveFont(30f);
		g.setFont(f1);
		
		if(!gameRunning) {
			for(int i = 0 ; i < options[0].length; i++) {
				
				if(current == i) {
					g.setColor(new Color(170,0,0));
				}
				else {
					g.setColor(Color.black);
				}
				g.drawString(options[0][i], (getWidth()-(options[0][i].length()*15))/2,380+(30*i));
			}
		}
		else {
			for(int i = 0 ; i < options[1].length; i++) {
				
				if(current == i) {
					g.setColor(new Color(170,0,0));
				}
				else {
					g.setColor(Color.black);
				}
				g.drawString(options[1][i], (getWidth()-(options[1][i].length()*15))/2,380+(30*i));
			}
		}
	}
	
	public String getCurrent() {
		if(!gameRunning) {
			return options[0][current];
		}
		else {
			return options[1][current];
		}
	}
	
}





