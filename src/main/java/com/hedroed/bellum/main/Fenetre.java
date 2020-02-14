package com.hedroed.bellum.main;

import com.hedroed.bellum.joueur.Joueur;
import com.hedroed.bellum.component.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener; 

import java.awt.CardLayout;

/** La fenetre est la class principal
  *	Elle cree les 3 sous partie de l'interface: damier, etat et ressource
  * On control toutes les methodes d'ici
  */
public class Fenetre extends JFrame implements KeyListener, Runnable {
	
	public final String MENU = "0";
	public final String GAME = "1";
	public final String PLAYERS = "2";
	public final String MAP = "3";
	public final String OPTION = "4";
	
	private JPanel container;
	
	private GamePane gamePanel;
	private MenuPane menuPane;
	private PlayersSelecterState playerSelecterPanel;
	private MapSelecterState mapSelecterPanel;
	private OptionState optionState;
	
	private CardLayout cL;
	private String currentPane;
	private boolean gameRunning = false;
	
	private PlaySound backSound;
	
	public Fenetre() {
		this.setTitle("Bellum");
		this.setSize(800, 850);
		
		this.setMinimumSize(new Dimension(800,600));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setLocation(0,0);
		this.setResizable(false);
		
		this.init();
	}
	
	
	public void init(){
		
		this.container = new JPanel();
		this.cL = new CardLayout();
		
		menuPane = new MenuPane(this);
		playerSelecterPanel = new PlayersSelecterState(this);
		gamePanel = new GamePane(this);
		mapSelecterPanel = new MapSelecterState(this);
		optionState = new OptionState(this);
		setSize(OptionState.sizeX,OptionState.sizeY);
		
		backSound = new PlaySound("/ressources/bellum.wav",OptionState.musicVolume);
		backSound.playContinuously();
		
		container.setLayout(cL);
		container.add(menuPane,MENU);
		container.add(optionState,OPTION);
		container.add(playerSelecterPanel,PLAYERS);
		container.add(gamePanel,GAME);
		container.add(mapSelecterPanel,MAP);
		currentPane = MENU;
		
		this.setContentPane(container);
		
		this.addKeyListener(this);
		this.setVisible(true);
		
		new Thread(this).start();
		
	}
	
	public void run() {
		long time;
		long wait;
		
		while(true) {
			time = System.nanoTime();
			
			repaint();
			// if(gameRunning) {
				// gamePanel.update();
			// }
			
			wait = 33-((System.nanoTime()-time)/1000000);
			if(wait < 0) {
				wait = 5;
				System.out.println("trop long");
			}
			// System.out.println("wait "+wait);
			try {
				Thread.sleep(wait);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		Fenetre f = new Fenetre();
		// System.out.println("Sort on d'ici ?");
	}
	
	public void goPlayer() {
		playerSelecterPanel.init(mapSelecterPanel.getNbPlayers());
		cL.show(this.container,PLAYERS);
		this.currentPane = PLAYERS;
		requestFocus();
		repaint();
	}
	
	public void goMenu() {
		backSound.setVolume(OptionState.musicVolume);
		cL.show(this.container,MENU);
		this.currentPane = MENU;
		requestFocus();
		repaint();
	}
	
	public void goGame() {
		cL.show(this.container,GAME);
		requestFocus();
		gamePanel.setPosition();
		currentPane = GAME;
		repaint();
	}
	
	public void startGame() {
		cL.show(this.container,GAME);
		
		Joueur[] players = playerSelecterPanel.getJoueur();
		if(mapSelecterPanel.getResources() != null) {
			for(Joueur j : players) {
				j.setVecRestant(mapSelecterPanel.getResources());
			}
		}
		
		gamePanel.newGame(players,mapSelecterPanel.getMap());
		
		requestFocus();
		currentPane = GAME;
		gameRunning = true;
		menuPane.setGameRunning(true);
		repaint();
	}
	
	public void endGame() {
		gameRunning = false;
		menuPane.setGameRunning(false);
		goMenu();
	}
	
	public void goMap() {
		cL.show(this.container,MAP);
		this.currentPane = MAP;
		requestFocus();
		repaint();
	}
	
	public void goOption() {
		cL.show(this.container,OPTION);
		this.currentPane = OPTION;
		requestFocus();
		repaint();
	}
	
	public void toggleMusic() {
		if(backSound.isRunning()) {
			backSound.stop();
		}
		else {
			backSound.playContinuously();
		}
		
	}
	
	//interfaces
	public void keyPressed(KeyEvent e) {
	
		// System.out.println(e.paramString());
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			goMenu();
		}
		else if(currentPane.equals(GAME)) {
			// System.out.println(e.paramString());
			gamePanel.keyPressed(e.getKeyCode());
		}
		else if(currentPane.equals(MENU)) {
			menuPane.keyPressed(e.getKeyCode());
		}
		else if(currentPane.equals(MAP)) {
			
			mapSelecterPanel.keyPressed(e.getKeyCode());
			
		}
		else if(currentPane.equals(PLAYERS)) {
			if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				startGame();
			}
		}
		
	}
	
	public void keyReleased(KeyEvent e) {}
	public void	keyTyped(KeyEvent e) {}
	
}
