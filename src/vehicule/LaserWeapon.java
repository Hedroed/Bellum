package vehicule;

import component.Case;
import component.Animation;
import main.FDamier;

import java.awt.Graphics;

public class LaserWeapon extends Weapon{

	public LaserWeapon(int range) {
		this.range = range;
		this.name = "LaserWeapon";
	}
	
	public void placeShooting(FDamier damier, Case center, Vehicule v)  {
		
		int x = center.getXCoord();
		int y = center.getYCoord();
		
		
		calculeLaser(damier,x,y,v, 0,-1);
		calculeLaser(damier,x,y,v, 0,1);
		calculeLaser(damier,x,y,v, -1,0);
		calculeLaser(damier,x,y,v, 1,0);
		
	}
	
	public void placeFake(FDamier damier, Case center, Vehicule v)  {
		
		int x = center.getXCoord();
		int y = center.getYCoord();
		
		
		calculeLaserFake(damier,x,y,v, 0,-1);
		calculeLaserFake(damier,x,y,v, 0,1);
		calculeLaserFake(damier,x,y,v, -1,0);
		calculeLaserFake(damier,x,y,v, 1,0);
		
	}
	
	private void calculeLaser(FDamier damier, int x, int y,Vehicule v, int i1, int i2) {
		Case c = null;
		for(int i = 1; i <= range; i++){
			
			c = damier.getCaseCoord(x+(i1*i),y+(i2*i));
			
			if(c != null && !(i1 == 0 && i2 == 0)) {
				if(v.canShoot(c)) {
					c.setCible(true);
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
	
	private void calculeLaserFake(FDamier damier, int x, int y,Vehicule v, int i1, int i2) {
		Case c = null;
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
	
	public void draw(Graphics g) {
		g.drawString("Arme : "+name,10,0);
	}
	
	public Animation getAnimation(Case dep, Case arr) {
		return null;
	}
	
}