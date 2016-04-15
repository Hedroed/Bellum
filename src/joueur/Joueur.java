package joueur;

import vehicule.*;
import component.TransferVec;

import java.awt.Color;
import java.util.ArrayList;

public class Joueur {
	
	private String name;
	
	private Color color;
	
	private String race;
	
	private ArrayList<Vehicule> vecActif = new ArrayList<Vehicule>();
	//liste des vehicules et de leur nombre
	private ArrayList<TransferVec> vecRestant = new ArrayList<TransferVec>();
	
	public Joueur(String n, Color c, String r) {
		this.name = n;
		this.color = c;
		this.race = r;
		
		this.vecRestant.add(new TransferVec(TypeVec.mangouste,6,this));//3
		this.vecRestant.add(new TransferVec(TypeVec.warthog,4,this));//2
		this.vecRestant.add(new TransferVec(TypeVec.scorpion,2,this));//1
		this.vecRestant.add(new TransferVec(TypeVec.helicopter,4,this));//2
		this.vecRestant.add(new TransferVec(TypeVec.walker,10,this));//5
		this.vecRestant.add(new TransferVec(TypeVec.bridger,2,this));//1
		// this.vecRestant.add(new TransferVec(TypeVec.turret,1,this));
		// this.vecRestant.add(new TransferVec(TypeVec.activeTurret,1,this));
	}
	
	public Joueur(String n, Color c) {
		this.name = n;
		this.color = c;
	}
	
	public void addVec(Vehicule v) {
		if(v != null && v.getJoueur() == this) {
			this.vecActif.add(v);
		}
	}
	
	public void activeAll() {
		for(Vehicule v : this.vecActif) {
			v.debutTour();
		}
	}
	
	public void deactiveAll() {
		for(Vehicule v : this.vecActif) {
			v.deactive();
		}
	}
	
	//accessor et modifier
	public String getName() {
		return this.name;
	}
	
	public void setName(String n) {
		this.name = n;
	}
	
	public Color getColor() {
		return this. color;
	}
	
	public void setColor(Color c) {
		this.color = c;
	}
	
	public String getRace() {
		return this.race;
	}
	
	public void setRace(String r) {
		this.race = r;
	}
	
	public ArrayList<Vehicule> getVehicules() { 
		return this.vecActif;
	}
	
	public ArrayList<TransferVec> getVecRestant() { //retourne les vehicules restant du joueur 
		return this.vecRestant;
	}
}