

public class Base {
	private Joueur joueur;
	private int vie = 4;
	
	public Base(Joueur jo) {
		this.joueur = jo;
	}
	
	public Joueur getJoueur() {
		return this.joueur;
	}
	
	public void attack() {
		if(this.vie > 0) {
			this.vie--;
		}
		System.out.println("Vie de la base de "+this.joueur.getName()+" : "+this.vie);
	}
	
	public int getVie() {
		return this.vie;
	}
}