package component;

import joueur.Joueur;
import vehicule.TypeVec;

public class TransferVec {
	private TypeVec type;
	private int nbRestant;
	private Joueur joueur;
	
	public TransferVec(TypeVec t, int nb, Joueur jo) {
		this.type = t;
		this.joueur = jo;
		this.nbRestant = nb;
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
	
	public String toString() {
		return "TransferVec [type:"+this.type.name()+", restant:"+this.nbRestant+", joueur:"+this.joueur+"]";
	}
	
	public void isExited() {
		if(this.nbRestant > 0) {
			this.nbRestant--;
		}
	}
}