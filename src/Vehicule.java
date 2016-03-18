

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.geom.AffineTransform;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.AlphaComposite;
import java.awt.Dimension;
import java.awt.image.BufferedImage;


public abstract class Vehicule extends JPanel{
	private Image image;
	private BufferedImage[] explosions;
	private boolean mort = false;
	private int ind;

	private int angle;
	private int posX = 1;
	private int posY = 1;
	protected FDamier pan;
	protected TypeVec type;
	private Joueur joueur;

	private int life;
	private int tirRestant;
	private int depRestant;
	private boolean actif = true;

	public Vehicule(FDamier f, Joueur jo, int a, TypeVec t) {
		// System.out.println("Creation vehicule "+t.name()+" ["+a+"]");
		this.pan = f;
		this.joueur = jo;
		this.angle = a;
		this.type = t;
		this.setPreferredSize(new Dimension(43,43));

		this.life = t.getVieMax();
		
		//image de l'explosion
		BufferedImage explosion = null;
		try {
			explosion = ImageIO.read(new File("ressources/explosion.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		final int width = 45;
		final int height = 45;
		final int rows = 9;
		final int cols = 9;
		explosions = new BufferedImage[rows * cols];

		for (int i = 0; i < rows; i++)
		{
			for (int j = 0; j < cols; j++)
			{
				explosions[(i * cols) + j] = explosion.getSubimage(j * width,i * height,width,height);
			}
		}
	}

	public void paintComponent(Graphics g){
		// System.out.println("repaint vehicule");
		
		Graphics2D g2D = (Graphics2D) g;
		
		if(!this.actif) {
			g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
			// g2D.setColor(new Color(50,50,50));
			// g2D.fillRect(0, 0, this.getWidth(), this.getHeight());
		}
		
		double angleR = Math.toRadians(this.angle);
		AffineTransform rotate = new AffineTransform();
		rotate.translate(this.posX,this.posY);
		rotate.scale(0.65,0.65);
		rotate.rotate(angleR,image.getWidth(this)/2,image.getHeight(this)/2);

		if(this.ind < 30) {
			g2D.drawImage(image, rotate, pan);
		}
		//explosion si mort
		if(this.mort && this.ind <= 81) {
			g.drawImage(this.explosions[this.ind],0,0,this);
		}

		//dessin de la vie
		g2D.setColor(new Color(127,0,0));
		for(int i = 0; i < this.life; i++){
			g2D.fillRect(2+6*i,this.getHeight()-5,3,3);
		}
		
		//pixel utilisation
		int nb = 0;
		
		g2D.setColor(Color.red);
		for(int i = 0; i < this.tirRestant; i++){
			g2D.fillRect(38-(6*nb),2,3,3);
			nb++;
		}
		
		g2D.setColor(Color.blue);
		for(int i = 0; i < this.depRestant; i++){
			g2D.fillRect(38-(6*nb),2,3,3);
			nb++;
		}
		
		
	}

	public void moveTo(int x, int y){

		this.posX = x;
		this.posY = y;
	}

	//accesseur et modifieur
	public void setX(int x){
		this.posX = x;
	}

	public void setY(int y){
		this.posY = y;
	}

	public int getX(){
		return this.posX;
	}

	public int getY(){
		return this.posY;
	}
	
	public void setAngle(int a){
		this.angle = a;
	}

	public int getAngle(){
		return this.angle;
	}
	
	public Joueur getJoueur() {
		return this.joueur;
	}
	
	public TypeVec getType() {
		return this.type;
	}

	public boolean isDead() {
		return this.mort;
	}

	public boolean isActif() {
		return this.actif;
	}
	
	public void setActif(boolean b) {
		this.actif = b;
	}
	
	public Image getImage() {
		return this.image;
	}

	public void setImage(Image i) {
		this.image = i;
	}
	
	public void attaque() {
		this.life--;
		if(this.life <= 0) {
			this.mort = true;
			System.out.println("Vehicule "+this.type.name()+" mort");

			//animation mort
			PlaySound ps = new PlaySound("ressources/explosion.wav");
			ps.play();
			Thread t = new Thread(new AnimeExplosion(this.pan,this));
			t.start();
		}
		else {
			this.makeImage();
		}
	}
	
	public void deplacement(Case cD, Case cA) {
		int xx = cA.getXCoord() - cD.getXCoord(); 
		int yy = cA.getYCoord() - cD.getYCoord(); 
		
		int dep = (int) Math.sqrt((xx*xx)+(yy*yy));
		System.out.println("Deplacement de "+dep);
		this.depRestant = this.depRestant - dep;
	}
	
	public int getDepRestant() {
		return this.depRestant;
	}
	
	public void tir() {
		this.tirRestant--;
	}
	
	public int getTirRestant() {
		return this.tirRestant;
	}
	
	public void debutTour() {
		if(this.type.getPortee() > 0) {
			this.tirRestant = 1;
		}
		else {
			this.tirRestant = 0;
		}
		this.depRestant = this.type.getDep();
		this.actif = true;
	}
	
	public void deactive() {
		this.depRestant = 0;
		this.tirRestant = 0;
		this.actif = false;
	}
	
	
	public abstract void makeImage();
	
	public void newCase(Case c) {}
	
	public class AnimeExplosion implements Runnable {
		private FDamier f;
		private Vehicule v;

		public AnimeExplosion(FDamier f, Vehicule v) {
			this.f = f;
			this.v = v;
		}

		public void run() {

			for(this.v.ind = 0;this.v.ind<81;this.v.ind++) {
				this.f.repaint();
				try {
				  Thread.sleep(30);
				} catch (InterruptedException e) {
				  e.printStackTrace();
				}
			}
			ind = 80;
			this.f.repaint();
			this.f.killMe(this.v);
		}
	}

	public int getLife() {
		return this.life;
	}

	public void enter() {
		
		setVisible(false);
		setVisible(true);
		
		Thread t = new Thread(new Runnable() {
			public void run() {
				int yDep = 0;

				if(angle == 0) {
					for(posY = 23; posY > 1; posY--) {
						pan.repaint();
						try {
						  Thread.sleep(30);
						} catch (InterruptedException e) {
						  e.printStackTrace();
						}
					}
				}
				else {
					for(posY = -23; posY < 1; posY++) {
						pan.repaint();
						try {
						  Thread.sleep(30);
						} catch (InterruptedException e) {
						  e.printStackTrace();
						}
					}
				}
				pan.repaint();

			}
		});
		t.start();
	}
	
	public boolean canMove(Case c) {
		boolean ret = false;
		
		if(c.isEmpty() && !c.isRiver()) {
			ret = true;
		}
		
		return ret;
	}
	
	public boolean canShoot(Case c) {
		boolean ret = false;
		
		if(c.isBase()) {
			if(c.getBase().getJoueur() != this.joueur) {
				ret = true;
			}
		}
		else if(c.getVehicule() != null) {
			if(c.getVehicule().getJoueur() != this.joueur) {
				ret = true;
			}
		}
		
		return ret;
	}
}
