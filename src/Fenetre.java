import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;

import javax.swing.JOptionPane;
import javax.swing.JColorChooser;

import glass.*;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

import java.awt.CardLayout;

/** La fenetre est la class principal
  *	Elle cree les 3 sous partie de l'interface: damier, etat et ressource
  * On control toutes les methodes d'ici
  */
public class Fenetre extends JFrame implements KeyListener {

	private FDamier fDamier ;
	private FEtat fEtat ;
	private FRessource fRessource ;
	private JPanel container;

	private Joueur joueur1;
	private Joueur joueur2;
	
	private MyGlassPane glass;
	
	private CardLayout cL;
	private String[] panelList = {"jeu","menu"};
	
	public Fenetre() {
		this.setTitle("Bellum");
		this.setSize(651, 850);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);

		this.setVisible(true);
		
		this.init();
	}
	
	
	public void init(){
		
		this.container = new JPanel();
		this.glass = new MyGlassPane();
		this.cL = new CardLayout();
				
		String[] race = {"humain", "humain"};
		JOptionPane jop = new JOptionPane();
		
		String nom1 = jop.showInputDialog(null, "Nom du Joueur1", "Ce systeme de saisie est temporaire", JOptionPane.QUESTION_MESSAGE);
		// String race1 = (String)jop.showInputDialog(null, "Race du Joueur1", "Ce systeme de saisie est temporaire", JOptionPane.QUESTION_MESSAGE, null,
			// race, race[0]);
		Color color1 = JColorChooser.showDialog(this, "Couleur du Joueur1", Color.white);
		// this.joueur1 = new Joueur(nom1,color1,race1);
		this.joueur1 = new Joueur(nom1,color1,null);
		
		String nom2 = jop.showInputDialog(null, "Nom du Joueur2", "Ce systeme de saisie est temporaire", JOptionPane.QUESTION_MESSAGE);
		// String race2 = (String)jop.showInputDialog(null, "Race du Joueur2", "Ce systeme de saisie est temporaire", JOptionPane.QUESTION_MESSAGE, null,
			// race, race[0]);
		Color color2 = JColorChooser.showDialog(this, "Couleur du Joueur2", null);
		// this.joueur2 = new Joueur(nom2,color2,race2);
		this.joueur2 = new Joueur(nom2,color2,null);
		
		this.fRessource = new FRessource(glass,this.joueur1,this.joueur2);
		this.fEtat = new FEtat();
		this.fDamier = new FDamier(this.fEtat,this.fRessource,this.joueur1,this.joueur2, this);

		this.fEtat.setFDamier(this.fDamier);
		this.fRessource.setFDamier(this.fDamier);
		
		container.setLayout(cL);
		this.addKeyListener(this);
		
		JPanel j1 = new JPanel();
		JPanel j2 = new JPanel();
		
		j1.add(fEtat);
		j1.add(fDamier);
		j1.add(fRessource);
		
		j2.setBackground(Color.red);
		
		container.add(j1,panelList[0]);
		container.add(j2,panelList[1]);
		this.setGlassPane(glass);
		this.setContentPane(container);
		
		// fDamier.addVehicule(Sortie.H_D, TypeVec.bridger, this.joueur1);
		// fDamier.addVehicule(Sortie.H_M, TypeVec.bridger, this.joueur1);
		// fDamier.addVehicule(Sortie.H_G, TypeVec.bridger, this.joueur1);
		// fDamier.addVehicule(Sortie.B_G, TypeVec.bridger, this.joueur2);
		// fDamier.addHelicopter(Sortie.B_M, this.joueur2);
		// fDamier.addVehicule(Sortie.B_D, TypeVec.bridger, this.joueur2);
		
		// fDamier.addHelicopter(Sortie.B_M, this.joueur2);
		// fDamier.addHelicopter(Sortie.H_M, this.joueur1);
		
		this.fRessource.activeTurn();

	}

	public static void main(String[] args) {
		Fenetre f = new Fenetre();
		// System.out.println("Sort on d'ici ?");
	}
	
	public void keyPressed(KeyEvent e) {
	
		System.out.println(e.paramString());
		
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			cL.next(this.container);
		}
		else if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			this.fRessource.activeTurn();
		}
	}
	public void keyReleased(KeyEvent e) {}
	public void	keyTyped(KeyEvent e) {}
	
	public void fin() {
		cL.next(this.container);
	}
}
