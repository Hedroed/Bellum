package vehicule;

public enum TypeVec {
	mangouste ("ressources/Mangouste.png",2,2,2),
	warthog ("ressources/Warthog.png",2,4,1),
	scorpion ("ressources/Scorpion.png",3,6,1),
	helicopter ("ressources/Helicopter.png",2,3,2),
	walker ("ressources/Walker.png",1,1,1),
	bridger ("ressources/Bridger.png",5,0,1),
	turret ("ressources/turret.png",3,0,0),
	activeTurret("ressources/activeTurret.png",3,3,0);
	// ghost("ghost.png"),
	// revenant("revenant.png"),
	// apparition("apparition.png");

	private String lien;
	private int vie;
	private int porteeTire;
	private int dep;

    //Constructeur
	TypeVec(String s, int v, int p, int d){
		this.lien = s;
		this.vie = v;
		this.porteeTire = p;
		this.dep = d;
	}

	public String getLien(){
		return this.lien;
	}

	public int getVieMax(){
		return this.vie;
	}

	public int getPortee(){
		return this.porteeTire;
	}

	public int getDep(){
		return this.dep;
	}
}
