package main;

import joueur.*;
import component.*;
import vehicule.*;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.event.*;

public class GamePane extends JPanel implements MouseListener,MouseMotionListener,MouseWheelListener {
	
	private FRessource fRessource;
	private FDamier fDamier;
	private FEtat fEtat;
	private Fenetre fenetre;
	
	private int lastMiddleX,lastMiddleY;
	
	private Font f1;
	
	private DragImage dragImage;
	private int mouseX, mouseY;
	
	private long lastSpacePress;
	
	public GamePane(Fenetre f) {
		this.fenetre = f;
		
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.addMouseWheelListener(this);
		
		try {
            f1 = Font.createFont(Font.PLAIN, new File("ressources/DALEK.ttf"));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	public void newGame(Joueur[] players, String map) {
		removeAll();
		
		this.fRessource = new FRessource(this,players);
		this.fEtat = new FEtat();
		this.fDamier = new FDamier(this,this.fEtat,this.fRessource);
		
		MapLoader mL = new MapLoader("maps/"+map,fDamier,players);
		if(!mL.getError()) {
			this.fDamier.initDamier(mL);
		}
		else {
			this.fDamier.initDamier(players[0],players[1]);
		}
		
		this.fEtat.setFDamier(this.fDamier);
		this.fRessource.setFDamier(this.fDamier);
		
		this.setPosition();
		this.fRessource.startGame();
		
		// fDamier.addVehicule(new Exit(5,5,54), TypeVec.scorpion, players[0]);
		// fDamier.addVehicule(new Exit(2,12,90), TypeVec.mangouste, players[1]);
		// fDamier.addVehicule(new Exit(4,7,90), TypeVec.helicopter, players[0]);
	}
	
	public void paintComponent(Graphics g) {
		// System.out.println("paint game");
		
		g.setColor(Color.white);
		g.fillRect(0,0,getWidth(),getHeight());
		g.setFont(f1);
		
		fDamier.draw(g);
		fRessource.draw(g);
		fEtat.draw(g);
		
		if(dragImage != null) {
			dragImage.draw(g);
		}
		
		g.dispose();
		
	}
	
	public void setDragImage(DragImage d, int x, int y) {
		dragImage = d;
		mouseX = x;
		mouseY = y;
		
		fDamier.setDark(true);
	}
	
	public void setPosition() {
		
		int w = getWidth();
		int h = getHeight();
		
		// System.out.println("taille game "+w+" :: "+h);
		
		fRessource.setPosition(w-111,0,110,h-1);
		fEtat.setPosition(0,0,200,h-1);
		fDamier.setPosition(w,h);
		
	}
	
	//Mouse Listener
	public void mouseClicked(MouseEvent e) {}

    public void mouseReleased(MouseEvent e) {
		if(dragImage != null) {
			fDamier.setDark(false);
			
			//check position pour sortir un vehicule
			Case c = fDamier.getCase(e.getX(), e.getY());
			if(c != null) {
				fDamier.sortirVehicule(c,dragImage.getTransfer());
			}
			
			dragImage.restore();
			dragImage = null;
			repaint();
		}
	}

    public void mouseEntered(MouseEvent e) {}

    public void mouseExited(MouseEvent e) {
		fDamier.mouseExited(e);
	}

    public void mousePressed(MouseEvent e) {
		// System.out.println(e.paramString());
		
		if(e.getButton() == MouseEvent.BUTTON2) {
			if(e.getClickCount() == 2) {
				setPosition();
				repaint();
			}
			lastMiddleX = e.getX();
			lastMiddleY = e.getY();
		}
		else {
			if(e.getButton() == MouseEvent.BUTTON3) {
				fDamier.unselect();
				repaint();
			}
			fDamier.mousePressed(e);
			fRessource.mousePressed(e);
		}
		
		
    }
	
	//Mouse Motion Listener
	public void mouseDragged(MouseEvent e) {
		if(SwingUtilities.isMiddleMouseButton(e)) {
			int y = e.getY();
			int x = e.getX();
			int dx = (x - lastMiddleX);
			int dy = (y - lastMiddleY);
			if(dx != 0 || dy != 0) {
				lastMiddleX = e.getX();
				lastMiddleY = e.getY();
				FDamier.posX += dx;
				FDamier.posY += dy;
				// if(FDamier.posX < (FDamier.width-(getWidth()-200-110)-200) {FDamier.posX = 0;}
				// if(FDamier.posX > getWidth()-FDamier.width) {FDamier.posX = getWidth()-FDamier.width;}
				// if(FDamier.posY < 0) {FDamier.posY = 0;}
				// if(FDamier.posY > getHeight()-FDamier.height) {FDamier.posY = getHeight()-FDamier.height;}
			}
			repaint();
		}
		if(dragImage != null) {
			dragImage.setPosition(e.getX()-mouseX, e.getY()-mouseY);
			repaint();
		}
	}
	
	public void mouseMoved(MouseEvent e) {
		fDamier.mouseMoved(e);
	}
	
	//MouseWheelListener
	public void	mouseWheelMoved(MouseWheelEvent e) {
		// System.out.println(e.paramString());
		int wheel = e.getWheelRotation();
		
		if(wheel > 0 && FDamier.tileSize > 30) {
			FDamier.tileSize-=3;
			FDamier.posX += FDamier.LARGEUR;
			FDamier.posY += FDamier.LONGUEUR;
		}
		else if(wheel < 0 && FDamier.tileSize < 80) {
			FDamier.tileSize+=3;
			FDamier.posX -= FDamier.LARGEUR;
			FDamier.posY -= FDamier.LONGUEUR;
			
		}
		// System.out.println("tile size :"+FDamier.tileSize);
		fDamier.calculeSize();
		repaint();
	}
	
	public void keyPressed(int keyCode) {
		if(keyCode == KeyEvent.VK_SPACE) {
			if(System.currentTimeMillis() - lastSpacePress > 500) {
				fRessource.activeTurn();
				lastSpacePress = System.currentTimeMillis();
			} 
		}
		else if(keyCode == KeyEvent.VK_UP) {
			FDamier.posY-= 5;
		}
		else if(keyCode == KeyEvent.VK_DOWN) {
			FDamier.posY+=5;
		}
		else if(keyCode == KeyEvent.VK_RIGHT) {
			FDamier.posX+=5;
		}
		else if(keyCode == KeyEvent.VK_LEFT) {
			FDamier.posX-=5;
		}
		repaint();
	}
	
}