package component;

import main.*;
import joueur.*;

import java.util.ArrayList;
import java.awt.Image;
import java.io.*;
import javax.imageio.ImageIO;

public class MapLoader {
	
	public static final int EMPTY = 48;//ou '0'
	public static final int BASE = 'j';
	public static final int EXIT = 50;//ou '2'
	public static final int RIVER = 51;//ou '3'
	public static final int RIVER_RAMP = 52;// ou'4'
	public static final int OBSTACLE = 53;//ou '5'
	public static final int BRIDGE = 54;//ou '6'
	
	public static final int UP = 'u';
	public static final int DOWN = 'd';
	public static final int RIGHT = 'r';
	public static final int LEFT = 'l';
	
	private Case[][] cases;
	private boolean[][] bridges;
	private Base[] bases;
	private Joueur[] joueurs;
	private int[][] longueurBase;
	
	private boolean error;
	
	private Image spriteMap;
	private ArrayList<Image> basesImage;
	private Image bridge;
	
	private FDamier fDamier;
	
	public MapLoader(String fileName, FDamier f, Joueur[] jos) {
		this.fDamier = f;
		this.joueurs = jos;
		
		BufferedReader in = null;
		int width = 0, height = 0, nbBase;
		
		try {
			in = new BufferedReader(new FileReader(fileName));
			String s = in.readLine();
			System.out.println("Nom de la map : "+s);
			
			//recuperation de la taille du damier
			s = in.readLine();
			width = Integer.parseInt(s);
			s = in.readLine();
			height = Integer.parseInt(s);
			
			cases = new Case[width][height];
			bridges = new boolean[width][height];
			
			s = in.readLine();
			nbBase = Integer.parseInt(s);
			if(nbBase != joueurs.length) {
				error = true;
				return;
			}
			
			
			bases = new Base[nbBase];
			basesImage = new ArrayList<Image>(nbBase);
			longueurBase = new int[nbBase][2];
			
			for(int i=0; i<nbBase; i++) {
				s = in.readLine();
				basesImage.add(ImageIO.read(new File("ressources/"+s)));
				int lx = Character.getNumericValue(in.read());
				int ly = Character.getNumericValue(in.read());
				longueurBase[i][0] = lx;
				longueurBase[i][1] = ly;
				in.readLine();//passe a la ligne suivante
			}
			
			s = in.readLine();
			ImageSprite.mapSprite = ImageSprite.createSprite(65,65,2,6,"ressources/"+s);
			s = in.readLine();
			bridge = ImageIO.read(new File("ressources/"+s));
			
			//element de la map
			for(int i=0; i<height; i++) {
				for(int j=0; j<width; j++) {
					int t = in.read();
					if(t != -1) {
						while(t == 10 || t == 13) {
							t = in.read();
						}
						
						// System.out.print("t"+t+" ");
						
						if(t == EXIT) {
							createCaseExit(in.read(),in.read(),j,i);
						}
						else if(t == BASE) {
							createCaseBase(in.read(),j,i);
						}
						else {
							createCase(t,j,i);//creation de la case a partir du type
						}
					}
					else {
						System.out.println("Erreur sortie de fichier");
						error = true;
						return;
					}
					
				}
				// System.out.println("");
			}
			
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
		placeRiverTales(width, height);
		
	}
	
	private void createCaseExit(int j,int d, int x, int y) {
		int idJoueur = Character.getNumericValue(j);
		CaseExit ret = new CaseExit(x,y,this.fDamier);
		
		if(checkIdJoueur(idJoueur)) {
			ret.setJoueur(joueurs[idJoueur-1]);
			if(bases[idJoueur-1] == null) {
				bases[idJoueur-1] = new Base(x,y,joueurs[idJoueur-1],basesImage.get(idJoueur-1));
				bases[idJoueur-1].setBaseTile(longueurBase[idJoueur-1][0],longueurBase[idJoueur-1][1]);
			}
			ret.setBase(bases[idJoueur-1]);
		}
		
		switch(d) {
			case UP:
				ret.setExit(x,y-1,0);
				break;
			case DOWN: 
				ret.setExit(x,y+1,180);
				break;
			case RIGHT:        
				ret.setExit(x-1,y,270);
				break;
			case LEFT:        
				ret.setExit(x+1,y,90);
				break;
			default:
				System.out.println("Erreur :: direction de sortie inconnue");
				error = true;
				break;
		}
		
		cases[x][y] = ret;
	}
	
	private boolean checkIdJoueur(int id) {
		boolean ret = (id > 0 && id <=4);
		if(!ret) {
			System.out.println("Erreur :: sortie des bornes de joueur");
			error = true;
		}
		return ret;
	}
	
	private void createCaseBase(int j, int x, int y) {
		Case ret = new Case(x,y,this.fDamier);
		int idJoueur = Character.getNumericValue(j);
		
		if(checkIdJoueur(idJoueur)) {
			if(bases[idJoueur-1] == null) {
				bases[idJoueur-1] = new Base(x,y,joueurs[idJoueur-1],basesImage.get(idJoueur-1));
				bases[idJoueur-1].setBaseTile(longueurBase[idJoueur-1][0],longueurBase[idJoueur-1][1]);
			}
			ret.setBase(bases[idJoueur-1]);
		}
		
		cases[x][y] = ret;
		
	}
	
	private void createCase(int t, int x, int y) {
		
		Case ret = new Case(x,y,this.fDamier);
		
		switch(t) {
			case EMPTY:
				//rien
				break;
			case RIVER:        
				ret.setRiver();
				break;
			case RIVER_RAMP:
				ret.setRiver();
				ret.setRiverRamp();
				break;
			case OBSTACLE:        
				Obstacle o = new Obstacle((int) (Math.random()*2));
				ret.setObstacle(o);
				break;
			case BRIDGE:        
				ret.setRiver();
				ret.setBridge(true);
				bridges[x][y] = true;
				break;
			default:
				System.out.println("Erreur :: type case inconnu");
				error = true;
				break;
		}
		
		cases[x][y] = ret;
	}
	
	private void placeRiverTales(int width, int height) {
		
		for(int i=0; i<width; i++) {
			for(int j=0; j<height; j++) {
				if(cases[i][j].isRiver() && !cases[i][j].isRiverRamp()) {
					if(j-1 >= 0 && cases[i][j-1].isRiver()) {
						if(j+1 >= height || cases[i][j+1].isRiver()) {
							cases[i][j].setIdRiver(1);
						}
						else if(i+1 < width && cases[i+1][j].isRiver()) {
							cases[i][j].setIdRiver(4);
						}
						else if(i-1 >=0 && cases[i-1][j].isRiver()) {
							cases[i][j].setIdRiver(3);
						}
					}
					else if(j+1 < height && cases[i][j+1].isRiver()) {
						if(i+1 < width && cases[i+1][j].isRiver()) {
							cases[i][j].setIdRiver(5);
						}
						else if(i-1 >=0 && cases[i-1][j].isRiver()) {
							cases[i][j].setIdRiver(2);
						}
						else if(j-1 < 0) {
							cases[i][j].setIdRiver(1);
						}
					}
					else {
						cases[i][j].setIdRiver(0);
					}
				}
			}
		}
	}
	
	public Case[][] getDamier() {
		return this.cases;
	}
	
	public int getWidth() {
		return this.cases.length;
	}
	
	public int getHeight() {
		return this.cases[0].length;
	}
	
	public boolean[][] getBridges() {
		return this.bridges;
	}
	
	public Base[] getBases() {
		return this.bases;
	}
	
	public Image getImageBase(int i) {
		return this.basesImage.get(i-1);
	}
	
	public Image getImageBridge() {
		return this.bridge;
	}
	
	public boolean getError() {
		return error;
	}
	
}