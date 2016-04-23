package vehicule;

import joueur.*;
import main.FDamier;

import javax.swing.JPanel;

public class VehiculeFactory {
	
	
	public Vehicule getVehiculeByType(TypeVec type,int angle, FDamier damier, Joueur joueur) {
		Vehicule ret = null;
		switch (type) {
			case mangouste:
				ret = new Mangouste(angle, damier, joueur);
				break;
			case warthog: 
				ret = new Warthog(angle, damier, joueur);
				break;
			case scorpion:        
				ret = new Scorpion(angle, damier, joueur);
				break;
			case walker:        
				ret = new Walker(angle, damier, joueur);
				break;
			case bridger:
				ret = new Bridger(angle, damier, joueur);
				break;
			case turret:        
				ret = new Turret(angle, damier, joueur);
				break;
			case activeTurret:        
				ret = new ActiveTurret(angle, damier, joueur);
				break;
			case helicopter:        
				ret = new Helicopter(angle, damier, joueur);
				break;
			default:
				System.out.println("Erreur :: nom inconnu");
				break;
		}
		return ret;
		
	}
}