package com.hedroed.bellum.component;

import com.hedroed.bellum.joueur.Joueur;
import com.hedroed.bellum.vehicule.TypeVec;

public class TransferVec {
	private TypeVec type;
	private String nameVec;
	private int nbRestant;
	private Joueur joueur;
	
	public TransferVec(String name, int nb, Joueur jo) {
		// this.type = t;
		this.nameVec = name;
		this.joueur = jo;
		this.nbRestant = nb;
	}
	
	public TransferVec(String name, int nb) {
		System.out.println("    Add "+nb+" "+name);
		this.nameVec = name;
		this.nbRestant = nb;
	}
	
	public void setJoueur(Joueur j) {
		joueur = j;
	}
	
	public TypeVec getType() {
		return this.type;
	}
	
	public int getNbRestant() {
		return this.nbRestant;
	}
	
	public boolean canExtract(Joueur jo) {
		boolean ret = false;
		if(this.nbRestant > 0 && this.joueur == jo) {
			ret = true;
		}
		return ret;
	}
	
	public Joueur getJoueur() {
		return this.joueur;
	}
	
	public String getName() {
		return nameVec;
	}
	
	public String toString() {
		return "TransferVec [type:"+this.type.name()+", restant:"+this.nbRestant+", joueur:"+this.joueur+"]";
	}
	
	public void isExited() {
		if(this.nbRestant > 0) {
			this.nbRestant--;
		}
	}
	
	public TransferVec clone() {
		
		return new TransferVec(nameVec,nbRestant,joueur);
		
	}
}