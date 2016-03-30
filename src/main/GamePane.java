package main;

import joueur.*;
import glass.*;
import component.*;
import vehicule.*;

import java.awt.BorderLayout;
import javax.swing.JPanel;

public class GamePane extends JPanel {
	private FRessource fRessource;
	private FDamier fDamier;
	private FEtat fEtat;
	private Fenetre fenetre;
	
	private MyGlassPane glass;
	
	public GamePane(Fenetre f, MyGlassPane glass) {
		this.glass = glass;
		this.fenetre = f;
	}
	
	public void newGame(Joueur[] players, String map) {
		removeAll();
		
		this.fRessource = new FRessource(glass,players);
		this.fEtat = new FEtat();
		
		this.fDamier = new FDamier(this.fEtat,this.fRessource);
		
		MapLoader mL = new MapLoader("maps/"+map,fDamier,players);
		if(!mL.getError()) {
			this.fDamier.initDamier(mL);
		}
		else {
			this.fDamier.initDamier(players[0],players[1]);//a terme envoyer un tableau pour plus que 2 joueurs
		}
		
		this.fEtat.setFDamier(this.fDamier);
		this.fRessource.setFDamier(this.fDamier);
		
		//setBackground(Color.white);
		
		this.setLayout(new BorderLayout());
		
		JPanel center = new JPanel();
		center.add(fDamier);
		
		this.add(fEtat,BorderLayout.WEST);
		this.add(center,BorderLayout.CENTER);
		this.add(fRessource,BorderLayout.EAST);
		
		this.fRessource.startGame();
		
		// fDamier.addVehicule(new Exit(5,5,54), TypeVec.scorpion, players[0]);
	}
	
	public void nextTurn() {
		fRessource.activeTurn();
	}
	
	
}