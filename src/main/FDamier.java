package main;

import component.*;
import joueur.Joueur;
import vehicule.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Graphics;
import javax.swing.BorderFactory;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;

import java.util.ArrayList;
import java.awt.GridLayout;

import java.awt.event.*;

/** Class du damier de jeu contient les objets cases dans un tableau a 2 dimensions
  *	Contient une image de fond et le Vehicule/case selectionné
  * Calcule les zones de tirs et de deplacements du vehicule Selectinné
  * Autorise les deplacements de ce dernier
  */
public class FDamier extends JPanel implements MouseListener,MouseMotionListener {

	private Base[] bases;

	private Image bridge;
	private boolean[][] bridges;
	private FEtat fEtat;
	private FRessource fRess;

	private static int LARGEUR;
	private static int LONGUEUR;
	private Case[][] damier;
	private ArrayList<Case> zoneTirSelect = null;
	private ArrayList<Case> zoneDepSelect = null;
	private ArrayList<Case> zoneCibleSelect = null;
	private Case caseSelec = null;

	private boolean affTir = true;
	private boolean dark = false;
	
	private Fenetre fenetre;
	
	private int hoverCaseX;
	private int hoverCaseY;

	public FDamier(FEtat fe, FRessource fr){
		this.fRess = fr;
		this.fEtat = fe;
		
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		
		this.setBackground(new Color(30,20,10));
		this.setBorder(BorderFactory.createEtchedBorder());
		
	}
	
	/**
	  * Initialise avec les deux joueur, charge la map par defaut
	  */
	public void initDamier(Joueur jo1, Joueur jo2) {
		System.out.println("Creation map par defaut");
		LARGEUR = 9;
		LONGUEUR = 15;
		
		this.setSize(new Dimension(LONGUEUR*46,LARGEUR*46));
		this.setLayout(new GridLayout(LONGUEUR, LARGEUR,1,1));
		
		Image baseUp = null;
		Image baseDown = null;
		try {
			baseDown = ImageIO.read(new File("ressources/baseDown.png"));
			baseUp = ImageIO.read(new File("ressources/baseUp.png"));
			this.bridge = ImageIO.read(new File("ressources/pont.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		ImageSprite.mapSprite = ImageSprite.createSprite(65,65,2,6,"ressources/spriteMap.png");
		
		damier = new Case[LARGEUR][LONGUEUR];
		
		//ajout des sortie
		this.damier[2][1] = new CaseExit(2,1,this,new Exit(2,2,180),jo1);
		this.damier[4][1] = new CaseExit(4,1,this,new Exit(4,2,180),jo1);
		this.damier[6][1] = new CaseExit(6,1,this,new Exit(6,2,180),jo1);
		this.damier[2][13] = new CaseExit(2,13,this,new Exit(2,12,0),jo2);
		this.damier[4][13] = new CaseExit(4,13,this,new Exit(4,12,0),jo2);
		this.damier[6][13] = new CaseExit(6,13,this,new Exit(6,12,0),jo2);
		
		for(int j=0; j<LONGUEUR; j++) {
			for(int i=0; i<LARGEUR; i++) {
				if(this.damier[i][j] == null) {
					this.damier[i][j] = new Case(i,j,this);
				}
				this.add(this.damier[i][j]);
			}
		}

		//ajout des element du damier
		for(int i=0; i<LARGEUR; i++){
			this.damier[i][LONGUEUR/2].setRiver();
		}
		damier[0][LONGUEUR/2].setRiverRamp();
		damier[LARGEUR-1][LONGUEUR/2].setRiverRamp();
		
		bases = new Base[2];
		bases[0] = new Base(2,0,jo1,baseUp);
		bases[1] = new Base(2,13,jo2,baseDown);
		
		for(int i=2; i<7; i++) {
			for(int j=0; j<2; j++) {
				this.damier[i][j].setBase(bases[0]);
			}
			for(int j=13; j<15; j++) {
				this.damier[i][j].setBase(bases[1]);
			}
		}
		
		//ajout obstacles
		addObstacle(3,5);
		addObstacle(5,9);

	}
	
	/** 
	  * Initialise avec un objet MapLoader qui permet de charger un fichier map
	  */
	public void initDamier(MapLoader mL){
		System.out.println("Creation map par MapLoader");
		LARGEUR = mL.getWidth();
		LONGUEUR = mL.getHeight();
		
		this.setMinimumSize(new Dimension(LARGEUR*ImageSprite.tileSize,LONGUEUR*ImageSprite.tileSize));
		this.setLayout(new GridLayout(LONGUEUR, LARGEUR,1,1));

		damier = mL.getDamier();
		bases = mL.getBases();

		this.bridge = mL.getImageBridge();
		
		for(int j=0; j<LONGUEUR; j++) {
			for(int i=0; i<LARGEUR; i++) {
				this.add(this.damier[i][j]);
			}
		}
		
		// this.add(new JLabel("test de merde"));
		
	}
	
	public void paintComponent(Graphics g){
		// g.drawImage(this.fond, 0, 0, this);
		g.setColor(new Color(65,65,65));
		g.fillRect(0,0,this.getWidth(),this.getHeight());
		
		for(int j=0; j<LONGUEUR; j++) {
			for(int i=0; i<LARGEUR; i++) {
				this.damier[i][j].draw(g);
			}
		}
		
		for(Base b : this.bases) {
			b.draw(g);
		}
		
		if(this.dark) {
			g.setColor(new Color(0,0,0,128));
			g.fillRect(0,0,this.getWidth(),this.getHeight());
		}
		
		// if(indExplose > -1 && indExplose < 48) {
			// g.drawImage(ImageSprite.baseExplosion[indExplose],50,50,null);
		// }
		
		// System.out.println("repaint damier");
	}

	public void addVehicule(Exit s, TypeVec t, Joueur jo) {
		if(t.name().equals("helicopter")) {
			addHelicopter(s,jo);
		}
		else {
			if(this.damier[s.getX()][s.getY()].isEmpty()) {
				
				this.damier[s.getX()][s.getY()].placeVehicule(vehiculeFactory(t.name(),s,jo));
			}
			else {
				System.out.println("Erreur :: ajoute de vehicule sur un autre");
			}
			this.repaint();
		}
	}
	
	private Vehicule vehiculeFactory(String n, Exit s, Joueur jo) {
		Vehicule ret = null;
		switch (n) {
			case "mangouste":
				ret = new Mangouste(s.getAngle(), (FDamier)this, jo);
				break;
			case "warthog": 
				ret = new Warthog(s.getAngle(), (FDamier)this, jo);
				break;
			case "scorpion":        
				ret = new Scorpion(s.getAngle(), (FDamier)this, jo);
				break;
			case "walker":        
				ret = new Walker(s.getAngle(), (FDamier)this, jo);
				break;
			case "bridger":        
				ret = new Bridger(s.getAngle(), (FDamier)this, jo);
				break;
			default:
				System.out.println("Erreur :: nom inconnu");
				break;
		}
		jo.addVec(ret);
		return ret;
	}
	
	public void addHelicopter(Exit s, Joueur jo) {
		if(this.damier[s.getX()][s.getY()].isEmpty()) {
			Helicopter ret = new Helicopter(s.getAngle(),(FDamier)this,jo);
			jo.addVec(ret);
			this.damier[s.getX()][s.getY()].placeHelicopter(ret);
		}
		else {
			System.out.println("Erreur :: ajoute de vehicule sur un autre");
		}
		this.repaint();
	}
	
	public void addObstacle(int x, int y) {

		Obstacle o = new Obstacle();
		this.damier[x][y].setObstacle(o);
	}

	/**	methode appelee par une case quand elle est clickee
	  * declache tout les calcule de selection
	  */
	public void caseClicked(Case c) {
		
		// System.out.println("Taille fdamier :"+this.getWidth()+":"+this.getHeight());
		
		if(this.caseSelec == null) {
			if(c.getVehicule() != null && !c.getVehicule().isDead()) {
				//selectionne la case et sont vehicule
				if(c.getVehicule().isActif()) {
					this.caseSelec = c;
					this.caseSelec.setSelect(true);
					this.fEtat.setActiveVehicule(c.getVehicule());
					this.calculeZones();
				}
			}
		}
		else { //une case est selectionnee
			
			boolean end = false;
			
			//est deplacement
			if(this.zoneDepSelect != null) {
				for(Case cDep : this.zoneDepSelect) {
					if(cDep == c) {
						// System.out.println("Est un case de deplacement");

						//deplace le vehicule
						this.caseSelec.getVehicule().deplacement(this.caseSelec, c);
						if(this.caseSelec.getVehicule().getType() == TypeVec.helicopter) {
							c.setHelicopter((Helicopter)this.caseSelec.getVehicule());
							this.caseSelec.setHelicopter(null);
						}
						else {
							c.setVehicule(this.caseSelec.getVehicule());
							this.caseSelec.setVehicule(null);
						}
						
						//deselectionne
						this.caseSelec.setSelect(false);
						this.caseSelec = c;
						this.caseSelec.setSelect(true);
						this.calculeZones();
						
						c.getVehicule().newCase(c);
						
						end = true;
						break;
					}
				}
			}
			//est tir
			if(!end && this.zoneCibleSelect != null) {
				for(Case cCible : this.zoneCibleSelect) {
					if(c == cCible && c.isBase()) {
						cCible.getBase().attack();
						if(cCible.getBase().getVie() <= 0) {
							// System.out.println("Defaite de "+cCible.getBase().getJoueur().getName());
							
							Thread t = new Thread(new AnimeExplosion(cCible.getBase()));
							t.start();
							// this.fenetre.fin();
						}
						this.caseSelec.getVehicule().tir();
						//deselectionne
						this.unselect();
						
						end = true;
						break;
					}
					else if(c == cCible && !cCible.getVehicule().isDead()) {
						if(cCible.isHelicopter()) {
							cCible.getHelicopter().attaque();
						}
						else {
							cCible.getVehicule().attaque();
						}
						this.caseSelec.getVehicule().tir();
						//deselectionne
						this.unselect();
						
						end = true;
						break;
					}
				}
			}
			
			if(!end) {
				//est lui meme
				if(c == this.caseSelec) {
					this.unselect();
				}
				else if(c.getVehicule() != null) { //est un autre vehicule
					if(c.getVehicule().isActif()) {
						this.caseSelec.setSelect(false);
						this.caseSelec = c;
						this.caseSelec.setSelect(true);
						this.fEtat.setActiveVehicule(c.getVehicule());
						this.calculeZones();
					}
				}
			}
			
		}
		
		this.fEtat.repaint();
		this.repaint();
	}
	
	public class AnimeExplosion implements Runnable {
		
		private Base b;
		
		public AnimeExplosion (Base b) {
			this.b = b;
		}
		
		public void run() {
			
			for(int i = 0;i<48;i++) {
				
				b.update();
				repaint();
				try {
				  Thread.sleep(60);
				} catch (InterruptedException e) {
				  e.printStackTrace();
				}
			}
			
		}
	}
	
	public void unselect() {
		if(this.caseSelec != null) {
			this.caseSelec.setSelect(false);
			this.caseSelec = null;
			this.fEtat.setActiveVehicule(null);
			this.calculeZones();
		}
	}

	/** calcule les zones de tirs et de deplacement du vehicule de la case selectionnée
	  * place ces zones dans les tableaux attribut de FDamier
	  */
	public void calculeZones() {
		if(this.caseSelec == null) {
			//vidage de tableaux
			this.clearTable();
		}
		else if(this.caseSelec != null) {

			//vidage de tableaux
			this.clearTable();

			//calcule de la zone de tire
			
			ArrayList<Case> zoneTir = new ArrayList<Case>();
			ArrayList<Case> zoneCible = new ArrayList<Case>();
			Case c;
			int xx,yy;
			int portee = this.caseSelec.getVehicule().getType().getPortee();
			
			if(this.caseSelec.getVehicule().getTirRestant() > 0) {
				//cree tableau zonetir contenant toutes les cases ou il peu tirer
				for(int i1 = -1; i1 <= 1; i1++) {
					for(int i2 = -1; i2 <= 1; i2++) {
						for(int i = 1; i <= portee; i++){
							xx = this.caseSelec.getXCoord()+(i1*i);
							yy = this.caseSelec.getYCoord()+(i2*i);

							if((xx >= 0 && xx < LARGEUR) && (yy >=0 && yy < LONGUEUR)) {
								c = this.damier[xx][yy];
								if(c != this.caseSelec) {
									if(this.caseSelec.getVehicule().canShoot(c)) {
										zoneCible.add(c);
										break;
									}
									else if(!c.isEmpty()) {
										break;
									}
									else {
										zoneTir.add(c);
									}
								}
							}
						}
					}
				}
			
				//active les bordures de ces cases
				if(this.affTir) {
					for(Case ca : zoneTir){
						ca.setTir(true);
					}
					this.zoneTirSelect = zoneTir;
				}

				for(Case ca : zoneCible){
					ca.setCible(true);
				}
				this.zoneCibleSelect = zoneCible;
			}
			
			if(this.caseSelec.getVehicule().getDepRestant() > 0) {
				//calcule de la zone de deplacement
				int dep = this.caseSelec.getVehicule().getDepRestant();
				ArrayList<Case> zoneDep = new ArrayList<Case>();
				this.deplacementRec(dep,zoneDep,this.caseSelec);

				for(Case ca : zoneDep){
					ca.setDeplacement(true);
				}
				this.zoneDepSelect = zoneDep;
			}
		}
	}

	private void clearTable() {
		if(this.zoneTirSelect != null && !this.zoneTirSelect.isEmpty()) {
			for(Case ca : this.zoneTirSelect){
				ca.setTir(false);
			}
			this.zoneTirSelect = null;
		}
		if(this.zoneCibleSelect != null && !this.zoneCibleSelect.isEmpty()) {
			for(Case ca : this.zoneCibleSelect){
				ca.setCible(false);
				this.zoneCibleSelect = null;
			}
		}
		if(this.zoneDepSelect != null && !this.zoneDepSelect.isEmpty()) {
			for(Case ca : this.zoneDepSelect){
				ca.setDeplacement(false);
				this.zoneDepSelect = null;
			}
		}
	}

	private boolean deplacementRec(int i, ArrayList<Case> dep,Case c){
		boolean ret = false;
		int xx,yy;
		Case ca;

		if(i >= 1){
			for(int i1 = -1; i1 <= 1; i1++) {
				for(int i2 = -1; i2 <= 1; i2++) {

					xx = c.getXCoord()+i1;
					yy = c.getYCoord()+i2;

					if((xx >= 0 && xx < LARGEUR) && (yy >=0 && yy < LONGUEUR)) {
						ca = this.damier[xx][yy];
						if(ca != c && this.caseSelec.getVehicule().canMove(ca)) {
							dep.add(ca);
							deplacementRec(i-1,dep,ca);
						}
					}
				}
			}
		}
		return ret;
	}

	public void killMe(Vehicule v) {
		for(int j=0; j<LONGUEUR; j++) {
			for(int i=0; i<LARGEUR; i++) {
				if(v.getType() == TypeVec.helicopter) {
					if(this.damier[i][j].getHelicopter() == v) {
						this.damier[i][j].setHelicopter(null);
					}
				}
				else {
					if(this.damier[i][j].getVehicule() == v) {
						this.damier[i][j].setVehicule(null);
					}
				}
				this.calculeZones();
				this.repaint();
			}
		}
	}

	public void setAffTir(boolean b) {
		this.affTir = b;
		this.calculeZones();
		this.repaint();
	}

	public void sortirVehicule(Exit s, TransferVec t) {
		if(this.damier[s.getX()][s.getY()].isEmpty()) {
			
			System.out.println("Sortie de vehicule :"+t.getType().name());
			this.addVehicule(s,t.getType(), t.getJoueur());
			t.isExited();
		}
		
		this.fRess.repaint();
	}
	
	public Joueur getActiveJoueur() {
		return this.fRess.getActiveJoueur();
	}
	
	public void setDark(boolean b) {
		this.dark = b;
		
		// System.out.println(fRess.getActiveJoueur());
		
		for(int j=0; j<LONGUEUR; j++) {
			for(int i=0; i<LARGEUR; i++) {
				if(this.damier[i][j].isExit()) {
					if(((CaseExit)this.damier[i][j]).getJoueur() == fRess.getActiveJoueur()) {
						((CaseExit)this.damier[i][j]).setCanExitVehicule(b);
					}
				} 
			}
		}
	}
	
	//Mouse Listener
	public void mouseClicked(MouseEvent e) {}

    public void mouseReleased(MouseEvent e) {}

    public void mouseEntered(MouseEvent e) {}

    public void mouseExited(MouseEvent e) {
		if(hoverCaseX != -1) {
			damier[hoverCaseX][hoverCaseY].mouseExited();
			hoverCaseX = -1;
			hoverCaseY = -1;
			this.repaint();
		}
	}

    public void mousePressed(MouseEvent e) {
		// System.out.println(e.paramString());
		
		int x = (int) (e.getX()-2)/ImageSprite.tileSize;
		int y = (int) (e.getY()-2)/ImageSprite.tileSize;
		
		if(x >= 0 && x < LARGEUR && y >= 0 && y < LONGUEUR) {
			caseClicked(damier[x][y]);
		}
    }
	
	//Mouse Motion Listener
	public void mouseDragged(MouseEvent e) {}
	
	public void mouseMoved(MouseEvent e) {
		// System.out.println(e.paramString());
		
		int x = (int) (e.getX()-2)/ImageSprite.tileSize;
		int y = (int) (e.getY()-2)/ImageSprite.tileSize;
		
		if(x >= 0 && x < LARGEUR && y >= 0 && y < LONGUEUR) {
			if(x != hoverCaseX || y != hoverCaseY) {
				if(hoverCaseX != -1) {
					damier[hoverCaseX][hoverCaseY].mouseExited();
				}
				damier[x][y].mouseEntered();
				hoverCaseX = x;
				hoverCaseY = y;
				
				this.repaint();
			}
		}
		
	}
}
