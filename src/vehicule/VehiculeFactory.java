package vehicule;

import joueur.*;
import main.FDamier;

import javax.swing.JPanel;

public class VehiculeFactory {
	
	
	public Vehicule getVehiculeByType(TypeVec type,int angle, FDamier damier, JPanel pan, Joueur joueur) {
		Vehicule ret = null;
		switch (type) {
			case mangouste:
				ret = new Mangouste(angle, damier, pan, joueur);
				break;
			case warthog: 
				ret = new Warthog(angle, damier, pan, joueur);
				break;
			case scorpion:        
				ret = new Scorpion(angle, damier, pan, joueur);
				break;
			case walker:        
				ret = new Walker(angle, damier, pan, joueur);
				break;
			case bridger:
				ret = new Bridger(angle, damier, pan, joueur);
				break;
			case turret:        
				ret = new Turret(angle, damier, pan, joueur);
				break;
			case activeTurret:        
				ret = new ActiveTurret(angle, damier, pan, joueur);
				break;
			case helicopter:        
				ret = new Helicopter(angle, damier, pan, joueur);
				break;
			default:
				System.out.println("Erreur :: nom inconnu");
				break;
		}
		return ret;
		
	}
}