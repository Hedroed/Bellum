package main;

import joueur.Joueur;

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

import javax.swing.JOptionPane;
import javax.swing.JColorChooser;

import glass.*;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener; 

import java.awt.CardLayout;

/** La fenetre est la class principal
  *	Elle cree les 3 sous partie de l'interface: damier, etat et ressource
  * On control toutes les methodes d'ici
  */
public class Fenetre extends JFrame implements KeyListener, ActionListener {
	
	public final int MENU = 1;
	public final int GAME = 0;
	public final int SELECT = 2;
	
	private FDamier fDamier ;
	private FEtat fEtat ;
	private FRessource fRessource ;
	private JPanel container;
	
	private GamePane gamePanel;
	private MenuPane menuPane;
	private SelecterPane selectPanel;
	
	private Joueur joueur1;
	private Joueur joueur2;
	
	private MyGlassPane glass;
	
	private CardLayout cL;
	private String[] panelList = {"game","menu","select"};
	private int currentPane;
	private boolean gameRunning = false;
	
	public Fenetre() {
		this.setTitle("Bellum");
		this.setSize(651, 850);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(true);
		
		this.init();
	}
	
	
	public void init(){
		
		this.container = new JPanel();
		this.glass = new MyGlassPane();
		this.cL = new CardLayout();
		
		menuPane = new MenuPane();
		selectPanel = new SelecterPane(this);
		
		container.setLayout(cL);
		container.add(menuPane,panelList[1]);
		container.add(selectPanel,panelList[2]);
		currentPane = MENU;
		
		this.setGlassPane(glass);
		this.setContentPane(container);
		
		this.addKeyListener(this);
		this.setVisible(true);
	}

	public static void main(String[] args) {
		Fenetre f = new Fenetre();
		// System.out.println("Sort on d'ici ?");
	}
	
	public void keyPressed(KeyEvent e) {
	
		System.out.println(e.paramString());
		
		
		
		
		if(this.currentPane == GAME) {
			if(e.getKeyCode() == KeyEvent.VK_SPACE) {
				gamePanel.nextTurn();
			}
			
			if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				this.setContentPane(container);
				cL.show(this.container,"menu");
				currentPane = MENU;
				repaint();
			}
		}
		
		if(this.currentPane == MENU) {
			if(e.getKeyCode() == KeyEvent.VK_UP) {
				this.menuPane.up();
			}
			else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
				this.menuPane.down();
			}
			else if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				if(menuPane.getCurrent().equals("Jouer ") || menuPane.getCurrent().equals("Recommencer")) {
					cL.show(this.container,"select");
					this.currentPane = SELECT;
				}
				else if(menuPane.getCurrent().equals("Quitter")) {
					System.exit(0);
				}
				else if(menuPane.getCurrent().equals("Continuer")) {
					this.setContentPane(gamePanel);
					requestFocus();
					currentPane = GAME;
					repaint();
				}
			}
		}
		
	}
	
	public void keyReleased(KeyEvent e) {}
	public void	keyTyped(KeyEvent e) {}
	
	public void actionPerformed(ActionEvent e) {
		System.out.println(e.paramString());
		
		if(((JButton)e.getSource()).getActionCommand().equals("ok")) {
			System.out.println("bouton ok");
			
			gamePanel = new GamePane(selectPanel.getJoueur(1),selectPanel.getJoueur(2),glass);
			
			this.setContentPane(gamePanel);
			gamePanel.setVisible(false);
			gamePanel.setVisible(true);
			requestFocus();
			currentPane = GAME;
			gameRunning = true;
			menuPane.setGameRunning(true);
		}
		else {
			cL.show(this.container,"menu");
			this.currentPane = MENU;
			requestFocus();
		}
	}
	
}
