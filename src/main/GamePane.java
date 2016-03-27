package main;

import joueur.*;
import glass.*;
import component.MapLoader;

import java.awt.BorderLayout;
import javax.swing.JPanel;

public class GamePane extends JPanel {
	private Joueur joueur1;
	private Joueur joueur2;
	
	private FRessource fRessource;
	private FDamier fDamier;
	private FEtat fEtat;
	
	public GamePane(Joueur jo1, Joueur jo2, String map, MyGlassPane glass) {
		joueur1 = jo1;
		joueur2 = jo2;
		
		this.fRessource = new FRessource(glass,this.joueur1,this.joueur2);
		this.fEtat = new FEtat();
		
		this.fDamier = new FDamier(this.fEtat,this.fRessource);
		
		Joueur[] js = new Joueur[2];
		js[0] = joueur1;
		js[1] = joueur2;
		
		MapLoader mL = new MapLoader("maps/"+map,fDamier,js);
		if(!mL.getError()) {
			this.fDamier.initDamier(mL);
		}
		else {
			this.fDamier.initDamier(jo1,jo2);//a terme envoyer un tableau pour plus que 2 joueurs
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
		
		this.fRessource.activeTurn();
	}
	
	public void nextTurn() {
		fRessource.activeTurn();
	}
	
	// fDamier.addVehicule(Sortie.H_D, TypeVec.bridger, this.joueur1);
		// fDamier.addVehicule(Sortie.H_M, TypeVec.bridger, this.joueur1);
		// fDamier.addVehicule(Sortie.H_G, TypeVec.bridger, this.joueur1);
		// fDamier.addVehicule(Sortie.B_G, TypeVec.bridger, this.joueur2);
		// fDamier.addHelicopter(Sortie.B_M, this.joueur2);
		// fDamier.addVehicule(Sortie.B_D, TypeVec.bridger, this.joueur2);
}