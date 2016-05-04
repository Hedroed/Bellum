package main;

import component.*;
import joueur.Joueur;
import vehicule.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import javax.swing.JPanel;
import java.awt.Point;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;

import java.util.ArrayList;

import java.awt.event.*;

/** Class du damier de jeu contient les objets cases dans un tableau a 2 dimensions
  *	Contient une image de fond et le Vehicule/case selectionné
  * Calcule les zones de tirs et de deplacements du vehicule Selectinné
  * Autorise les deplacements de ce dernier
  */
public class FDamier {
	
	private Base[] bases;
	private Image bridge;
	private Image gameInterface;
	private boolean[][] bridges;
	private FEtat fEtat;
	private FRessource fRess;

	public static int LARGEUR;
	public static int LONGUEUR;
	public static int tileSize;
	
	public static int width, height;
	public static int drawX,drawY,drawX2,drawY2;
	
	private int lastMiddleX,lastMiddleY;
	
	private int posX, posY;
	
	private Case[][] damier;
	private Case caseSelec = null;
	private Vehicule vehiculeSelect = null;
	
	private boolean affTir = true;
	private boolean dark = false;
	private int messageAffiche;
	
	private GamePane pan;
	
	private int hoverCaseX;
	private int hoverCaseY;
	
	private GameMessage message;
	private Joueur winner = null;
	private int winnerInd;
	
	private VehiculeFactory factory;
	
	private ArrayList<Animation> animations;
	
	public FDamier(GamePane pan, FEtat fe, FRessource fr){
		this.fRess = fr;
		this.fEtat = fe;
		this.pan = pan;
		
		factory = new VehiculeFactory();
		
		tileSize = 46;
		posX = 300;
		posY = 50;
		width = 200;
		height = 700;
		
		animations = new ArrayList<Animation>();
		
		message = new GameMessage("ressources/DALEK.ttf","ressources/interface2.png",new Color(128,0,0));
		
	}
	
	/**
	  * Initialise avec les deux joueur, charge la map par defaut
	  */
	public void initDamier(Joueur jo1, Joueur jo2) {
		System.out.println("Creation map par defaut");
		LARGEUR = 9;
		LONGUEUR = 15;
		
		Image baseUp = null;
		Image baseDown = null;
		try {
			baseDown = ImageIO.read(new File("ressources/baseDown.png"));
			baseUp = ImageIO.read(new File("ressources/baseUp.png"));
			bridge = ImageIO.read(new File("ressources/pont.png"));
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

		damier = mL.getDamier();
		bases = mL.getBases();

		this.bridge = mL.getImageBridge();
	}
	
	public void testPosition() {
		posX += (Math.random()*5)-2;
		posY += (Math.random()*5)-2;
	}
	
	public void calculeSize() {
		height = LONGUEUR*tileSize;
		width = LARGEUR*tileSize;
	}
	
	public void setPosition(int w, int h) {
		calculeSize();
		posX = (w-width)/2;
		posY = (h-height)/2;
	}
	
	public void zoom(int wheel) {
		int scale = 4;
		if(wheel > 0 && tileSize > 30) {
			tileSize-=scale;
			posX += (scale/2)*LARGEUR;
			posY += (scale/2)*LONGUEUR;
		}
		else if(wheel < 0 && tileSize < 80) {
			tileSize+=scale;
			posX -= (scale/2)*LARGEUR;
			posY -= (scale/2)*LONGUEUR;
			
		}
		// System.out.println("tile size :"+FDamier.tileSize);
		calculeSize();
	}
	
	public void draw(Graphics g) {
		
		g = g.create();
		g.translate(posX,posY);
		
		g.setColor(new Color(65,65,65));
		g.fillRect(0,0,width,height);
		g.setColor(Color.black);
		g.drawRect(-1,-1,width,height);
		
		for(int j=0; j<LONGUEUR; j++) {
			for(int i=0; i<LARGEUR; i++) {
				this.damier[i][j].drawBack(g);
			}
		}
		
		for(int j=0; j<LONGUEUR; j++) {
			for(int i=0; i<LARGEUR; i++) {
				this.damier[i][j].draw(g);
			}
		}
		
		Animation toRemove = null;
		for(Animation a : animations) {
			a.draw(g);
			if(a.isEnd()) {
				toRemove = a;
			}
		}
		if(toRemove != null) {
			animations.remove(toRemove);
		}
		
		for(Base b : this.bases) {
			b.draw(g);
		}
		
		if(this.dark) {
			g.setColor(new Color(0,0,0,128));
			if(posX <= drawX && posY <= drawY && posX+width > drawX2 && posY+height > drawY2) {
				g.fillRect(drawX,drawY,drawX2-drawX,drawY2-drawY);
			}
			else {
				g.fillRect(0,0,width,height);
			}
			
			
			// draw caseExit on White :(
			for(int j=0; j<LONGUEUR; j++) {
				for(int i=0; i<LARGEUR; i++) {
					if(damier[i][j].isExit()) {
						if(((CaseExit)damier[i][j]).isWhite()) {
							g.setColor(new Color(255,255,255,128));
							g.fillRect(i*tileSize,j*tileSize,tileSize, tileSize);
						}
					}
				}
			}
			
		}
		
		if(winner != null) {
			
			winnerInd++;
			if(winnerInd > 50) {
				message.drawMessage(g,"Victoire","Du "+winner.getName());
			}
			if(winnerInd == 100) {
				pan.endGame();
			}
			
		}
		else if(messageAffiche < 30) {
			message.drawMessage(g,"Tour","Du "+getActiveJoueur().getName());
			messageAffiche++;
		}
		
		// g.setFont(g.getFont().deriveFont(20f));
		// g.drawString("Veh Select: "+vehiculeSelect+"    Case Select: "+caseSelec,posX+20,posY+height+20);
		
	}

	public void addVehicule(Exit s, String name, Joueur jo) {
		if(this.damier[s.getX()][s.getY()].isEmpty()) {
			
			Vehicule newVec = factory.getVehiculeByName(name,s.getAngle(),this,jo);
			jo.addVec(newVec);
			
			damier[s.getX()][s.getY()].addVehicule(newVec);
			newVec.moveTo(s.getX(),s.getY());
			newVec.enter();
		}
		else {
			System.out.println("Erreur :: ajoute de vehicule sur un autre");
		}
	}
	
	public void addObstacle(int x, int y) {

		Obstacle o = new Obstacle((int) (Math.random()*2));
		this.damier[x][y].setObstacle(o);
	}
	
	public void addAnimation(Animation a) {
		
		if(a != null) {
			animations.add(a);
		}
		
	}
	
	public void unselect() {
		if(this.caseSelec != null) {
			this.caseSelec.setSelect(false);
			this.caseSelec = null;
		}

		vehiculeSelect = null;
		
		fEtat.newSelect();
	}
	
	public void unselect(Vehicule v) {
		if(vehiculeSelect == v) {
			vehiculeSelect = null;
		}
		
		
		fEtat.newSelect();
	}
	
	public void select(Case c) {
		if(this.caseSelec != null) {
			this.caseSelec.setSelect(false);
			this.caseSelec = null;
		}
		caseSelec = c;
		caseSelec.setSelect(true);
		fEtat.newSelect();
	}
	
	public void select(Vehicule v) {
		if(vehiculeSelect != null) {
			vehiculeSelect = null;
		}
		vehiculeSelect = v;
		fEtat.newSelect();
	}
	
	public Case getCaseSelect() {
		return caseSelec;
	}
	
	public Vehicule getVehiculeSelect() {
		return vehiculeSelect;
	}

	/** calcule les zones de tirs et de deplacement du vehicule de la case selectionnée
	  * place ces zones dans les tableaux attribut de FDamier
	  */
	public void calculeZones() {
		// System.out.println("calcule zone");
		
		if(caseSelec != null && vehiculeSelect != null) {

			//vidage de tableaux
			this.clearTable();

			//calcule de la zone de tire
			vehiculeSelect.calculeTir(caseSelec);
			
			if(vehiculeSelect.getDepRestant() > 0) {
				//calcule de la zone de deplacement
				int dep = vehiculeSelect.getDepRestant();
				// zoneDepSelect = new ArrayList<Case>();
				this.deplacementRec(dep,this.caseSelec);

				// for(Case ca : zoneDepSelect){
					// ca.setDeplacement(true);
				// }
			}
			
		}
		else {
			//vidage de tableaux
			this.clearTable();
		}
	}

	private void clearTable() {
		for(int j=0; j<LONGUEUR; j++) {
			for(int i=0; i<LARGEUR; i++) {
				damier[i][j].clearBorder();
			}
		}
		
	}

	private boolean deplacementRec(int i,Case c){
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
						if(ca != c && vehiculeSelect.canMove(ca)) {
							ca.setDeplacement(true);
							deplacementRec(i-1,ca);
						}
					}
				}
			}
		}
		return ret;
	}

	public void setAffTir(boolean b) {
		this.affTir = b;
		this.calculeZones();
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
	
	public void next() {
		calculeZones();
		messageAffiche = 0;
	}
	
	public boolean sortirVehicule(Case c, TransferVec t) {
		boolean ret = false;
		
		if(c.isExit() && t.canExtract(((CaseExit)c).getJoueur())) {
			
			Exit e = ((CaseExit)c).getExit();
			
			if(damier[e.getX()][e.getY()].isEmpty()) {
		
				// System.out.println("Sortie de vehicule :"+t.getType().name());
				this.addVehicule(e,t.getName(), t.getJoueur());
				t.isExited();
				
				calculeZones();
				ret = true;
			}
	
			// System.out.println("transfer truc :"+v+" sortie "+e);
		}
		return ret;
	}
	
	public Case getCase(int x, int y) {
		Case ret = null;
		if(x >= posX && y >= posY && x < posX+width && y < posY+height) {
			
			// System.out.println(e.paramString());
			
			int i = (int) (x-posX)/tileSize;
			int j = (int) (y-posY)/tileSize;
			
			if(i >= 0 && i < LARGEUR && j >= 0 && j < LONGUEUR) {
				ret = damier[i][j];
			}
		}
		return ret;
	}
	
	public Case getCaseCoord(int x, int y) {
		Case ret = null;
		
		if(x >= 0 && x < LARGEUR && y >= 0 && y < LONGUEUR) {
			ret = damier[x][y];
		}
		
		return ret;
	}
	
	public void checkEndGame() {
		
		ArrayList<Joueur> enVie = new ArrayList<Joueur> ();
		
		for(Base b : bases) {
			Joueur j = b.getJoueur();
			
			int nbVec = j.getVehicules().size();
			for(TransferVec tv : j.getVecRestant()) {
				nbVec += tv.getNbRestant();
			}
			// System.out.println("Joueur : "+j+" reste "+nbVec+"vehicule");
			if(nbVec == 0) {
				b.kill();
			}
			
			if(!b.isDead()) {
				enVie.add(j);
			}
		}
		
		if(enVie.size() == 1) {
			winner = enVie.get(0);
			winnerInd = 0;
			System.out.println("Le gagnant est "+winner.getName());
		}
		
		
	}
	
	
	
	//Mouse Listener
    public void mouseExited(MouseEvent e) {
		if(hoverCaseX != -1) {
			damier[hoverCaseX][hoverCaseY].mouseExited();
			hoverCaseX = -1;
			hoverCaseY = -1;
		}
	}
	
    public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON2) {
			lastMiddleX = e.getX();
			lastMiddleY = e.getY();
		}
		else {
			Case c = getCase(e.getX(),e.getY());
			
			if(c != null) {
				
				c.mousePressed(e);
				calculeZones();
				
				messageAffiche = 30;
			}
		}
		
    }
	
	//Mouse Motion Listener
	public void mouseMoved(MouseEvent e) {
		// System.out.println(e.paramString());
		
		int x = (int) (e.getX()-posX)/tileSize;
		int y = (int) (e.getY()-posY)/tileSize;
		
		if(x >= 0 && x < LARGEUR && y >= 0 && y < LONGUEUR) {
			if(x != hoverCaseX || y != hoverCaseY) {
				if(hoverCaseX != -1) {
					damier[hoverCaseX][hoverCaseY].mouseExited();
				}
				damier[x][y].mouseEntered();
				hoverCaseX = x;
				hoverCaseY = y;
				// System.out.println("new hover :"+x+" :: "+y);
				
			}
		}
		
	}
	
	public void mouseDragged(MouseEvent e) {
		int y = e.getY();
		int x = e.getX();
		int dx = (x - lastMiddleX);
		int dy = (y - lastMiddleY);
		if(dx != 0 || dy != 0) {
			lastMiddleX = x;
			lastMiddleY = y;
			posX += dx;
			posY += dy;
			// if(FDamier.posX < (FDamier.width-(getWidth()-200-110)-200) {FDamier.posX = 0;}
			// if(FDamier.posX > getWidth()-FDamier.width) {FDamier.posX = getWidth()-FDamier.width;}
			// if(FDamier.posY < 0) {FDamier.posY = 0;}
			// if(FDamier.posY > getHeight()-FDamier.height) {FDamier.posY = getHeight()-FDamier.height;}
		}
		
	}
	
	//MouseWheelListener
	public void	mouseWheelMoved(MouseWheelEvent e) {
		zoom(e.getWheelRotation());
	}
	
	
	//keyListener
	public void keyPressed(int keyCode) {
		if(keyCode == KeyEvent.VK_UP) {
			posY-= 10;
		}
		else if(keyCode == KeyEvent.VK_DOWN) {
			posY+=10;
		}
		else if(keyCode == KeyEvent.VK_RIGHT) {
			posX+=10;
		}
		else if(keyCode == KeyEvent.VK_LEFT) {
			posX-=10;
		}
		else if(keyCode == KeyEvent.VK_ADD) {
			zoom(-1);
		}
		else if(keyCode == KeyEvent.VK_SUBTRACT) {
			zoom(1);
		}
	}
}
