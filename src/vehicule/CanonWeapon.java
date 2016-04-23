package vehicule;

import component.Case;
import component.Animation;
import component.AnimationShell;
import main.FDamier;

import java.awt.Graphics;

public class CanonWeapon extends Weapon{

	public CanonWeapon(int range) {
		this.range = range;
		this.name = "CanonWeapon";
	}
	
	public void placeShooting(FDamier damier, Case center, Vehicule v)  {
		
		int x = center.getXCoord();
		int y = center.getYCoord();
		Case c = null;
		
		for(int i1 = -1; i1 <= 1; i1++) {
			for(int i2 = -1; i2 <= 1; i2++) {
				for(int i = 1; i <= range; i++){
					
					c = damier.getCaseCoord(x+(i1*i),y+(i2*i));
					
					if(c != null && !(i1 == 0 && i2 == 0)) {
						if(v.canShoot(c)) {
							c.setCible(true);
							break;
						}
						else if(!c.isEmpty()) {
							break;
						}
						else {
							c.setTir(true);
						}
					}
				}
			}
		}
		
	}
	
	public void placeFake(FDamier damier, Case center, Vehicule v)  {
		
		int x = center.getXCoord();
		int y = center.getYCoord();
		Case c = null;
		
		for(int i1 = -1; i1 <= 1; i1++) {
			for(int i2 = -1; i2 <= 1; i2++) {
				for(int i = 1; i <= range; i++){
					
					c = damier.getCaseCoord(x+(i1*i),y+(i2*i));
					
					if(c != null && !(i1 == 0 && i2 == 0)) {
						if(c.isBase() || c.isObstacle()) {
							break;
						}
						else if((c.getVehicule() != null && c.getVehicule().getJoueur() == v.getJoueur()) || (c.getFlying() != null && c.getFlying().getJoueur() == v.getJoueur())) {
							break;
						}
						else {
							c.setTir(true);
						}
					}
				}
			}
		}
		
	}
	
	public void draw(Graphics g) {
		
		g.drawString("Arme : "+name,10,0);
		
	}
	
	public Animation getAnimation(Case dep, Case arr) {
		return new AnimationShell(dep.getXCoord()+0.5,dep.getYCoord()+0.5,arr.getXCoord()+0.5,arr.getYCoord()+0.5);
	}
	
	
}