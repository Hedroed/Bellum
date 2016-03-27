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
	public final int PLAYERS = 2;
	public final int MAP = 3;
	public final int OPTION = 4;
	
	private JPanel container;
	
	private GamePane gamePanel;
	private MenuPane menuPane;
	private SelecterPane playerSelecterPanel;
	private MapSelecterState mapSelecterPanel;
	
	private MyGlassPane glass;
	
	private CardLayout cL;
	private String[] panelList = {"game","menu","players","map","option"};
	private int currentPane;
	private boolean gameRunning = false;
	
	public Fenetre() {
		this.setTitle("Bellum");
		this.setSize(800, 850);
		// this.setSize(1000, 850);
		this.setMinimumSize(new Dimension(800,600));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(true);
		
		this.init();
	}
	
	
	public void init(){
		
		this.container = new JPanel();
		this.glass = new MyGlassPane();
		this.cL = new CardLayout();
		
		menuPane = new MenuPane(this);
		playerSelecterPanel = new SelecterPane(this);
		gamePanel = new GamePane(this,glass);
		mapSelecterPanel = new MapSelecterState(this);
		
		container.setLayout(cL);
		container.add(menuPane,panelList[1]);
		container.add(playerSelecterPanel,panelList[2]);
		container.add(gamePanel,panelList[0]);
		container.add(mapSelecterPanel,panelList[3]);
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
	
		// System.out.println(e.paramString());
		
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
				clickMenu();
			}
		}
		
	}
	
	public void clickMenu() {
		if(menuPane.getCurrent().equals("Jouer ") || menuPane.getCurrent().equals("Recommencer")) {
			cL.show(this.container,panelList[MAP]);
			this.currentPane = MAP;
			// new PlaySound("ressources/select.wav",-30).play();
		}
		else if(menuPane.getCurrent().equals("Quitter")) {
			System.exit(0);
		}
		else if(menuPane.getCurrent().equals("Continuer")) {
			cL.show(this.container,panelList[GAME]);
			this.currentPane = GAME;
			requestFocus();
			repaint();
		}
	}
	
	public void mapNext() {
		cL.show(this.container,panelList[PLAYERS]);
		playerSelecterPanel.init(mapSelecterPanel.getNbPlayers());
		this.currentPane = PLAYERS;
		requestFocus();
		repaint();
	}
	
	public void mapBack() {
		cL.show(this.container,panelList[MENU]);
		this.currentPane = MENU;
		requestFocus();
		repaint();
	}
	
	public void keyReleased(KeyEvent e) {}
	public void	keyTyped(KeyEvent e) {}
	
	public void actionPerformed(ActionEvent e) {
		System.out.println(e.paramString());
		
		if(((JButton)e.getSource()).getActionCommand().equals("ok")) {
			System.out.println("bouton ok");
			
			cL.show(this.container,panelList[GAME]);
			gamePanel.newGame(playerSelecterPanel.getJoueur(),mapSelecterPanel.getMap());
			requestFocus();
			currentPane = GAME;
			gameRunning = true;
			menuPane.setGameRunning(true);
		}
		else {
			mapBack();
		}
	}
	
}
