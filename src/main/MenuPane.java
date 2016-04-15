package main;

import component.ImageSprite;

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

import java.awt.event.*;

public class MenuPane extends JPanel implements MouseListener,MouseMotionListener{
	
	public final static int PLAY = 1;
	public final static int CONTINUE = 0;
	public final static int OPTION = 2;
	public final static int EXIT = 3;
	
	private String title;
	private String[][] options = {{"Play ","Options ","Quit "},{"Continue","Restart","Options ","Quit "}};
	
	private PlaySound soundChange ;
	
	private Boolean gameRunning = false;
	private int current = 0;
	
	private Fenetre fenetre;
	
	private Image music;
	private boolean musicOff = false;
	
	public MenuPane(Fenetre f) {
		this.fenetre = f;
	
		this.title = "Bellum";
		this.soundChange = new PlaySound("ressources/menu.wav",-30,10);
		this.setBackground(Color.black);
		
		music = ImageSprite.createImage("ressources/music.png");
		
		this.addMouseMotionListener(this);
		this.addMouseListener(this);
		//load background
		
	}
	
	public void down() {
		current++;
		
		int i=0;
		if(gameRunning) i=1;
		
		if(current >= options[i].length) {
			current = 0;
		}
		this.soundChange.play();
	}
	
	public void up() {
		current--;
		
		int i=0;
		if(gameRunning) i=1;
		
		if(current < 0) {
			current = options[i].length -1;
		}
		this.soundChange.play();
	}
	
	public void close() {
		this.soundChange.close();
	}
	
	public void setGameRunning(boolean b) {
		gameRunning = b;
	}
	
	public void paintComponent(Graphics g) {
		//clear screen
		// g.setColor(Color.white);
		// g.fillRect(0,0,getWidth(),getHeight());
		
		//draw background
		Graphics2D g2D = (Graphics2D) g;
		
		AffineTransform scale = new AffineTransform();
		scale.scale((double)getWidth()/ImageSprite.menuBackground.getWidth(this),(double)getHeight()/ImageSprite.menuBackground.getHeight(this));
		
		g2D.drawImage(ImageSprite.menuBackground,scale,this);
		
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
		
		g.drawImage(music,getWidth()-64,0,this);
	}
	
	public int getCurrent() {
		if(!gameRunning) {
			return current+1;
		}
		else {
			return current;
		}
	}
	
	public void mouseClicked(MouseEvent e) {}

    public void mouseReleased(MouseEvent e) {}

    public void mouseEntered(MouseEvent e) {}

    public void mouseExited(MouseEvent e) {}

    public void mousePressed(MouseEvent e) {		
		double i = (double)(e.getY()-357)/30;
		int iMax = 0;
		
		if(gameRunning) {
			iMax = options[1].length;
		}
		else {
			iMax = options[0].length;
		}
		
		if(i >= 0 && i < iMax) {
			current = (int)i;
			fenetre.clickMenu();
		}
		
		if(e.getY() < 64 && e.getX() > getWidth()-64) {
			fenetre.toggleMusic();
			if(musicOff) {
				musicOff = false;
				music = ImageSprite.createImage("ressources/music.png");
			}
			else {
				musicOff = true;
				music = ImageSprite.createImage("ressources/musicOff.png");
			}
		}
	}
	
	//Mouse Motion Listener
	public void mouseDragged(MouseEvent e) {}
	
	public void mouseMoved(MouseEvent e) {
		double i = (double)(e.getY()-357)/30;
		int iMax = 0;
		
		if(gameRunning) {
			iMax = options[1].length;
		}
		else {
			iMax = options[0].length;
		}
		if(i >= 0 && i < iMax && current != (int)i) {
			current = (int)i;
			soundChange.play();
		}
	}
}





