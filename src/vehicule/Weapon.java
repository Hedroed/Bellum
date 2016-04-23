package vehicule;

import component.Case;
import component.Animation;
import main.FDamier;

import java.awt.Graphics;

public abstract class Weapon {

	protected int range;
	
	protected String name;
	
	public abstract void placeShooting(FDamier damier, Case center, Vehicule v) ;
	
	public abstract void placeFake(FDamier damier, Case center, Vehicule v);
	
	public abstract void draw(Graphics g);
	
	public abstract Animation getAnimation(Case dep, Case arr);
	
}