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
import java.awt.Graphics;
import javax.swing.BorderFactory;
import javax.swing.JScrollPane;

import javax.swing.TransferHandler;
import glass.*;

import java.util.ArrayList;

public class FRessource extends JPanel implements ActionListener {
	
	private Dimension fSize = new Dimension(626,100);
	private FDamier fDamier;
	private JButton nextTurn = new JButton("Tour Suivant");
	private JPanel centerPane = new JPanel();
	
	private MyGlassPane glass;
	
	private Joueur joueur1;
	private Joueur joueur2;
	private Joueur joueurActif;
	
	private int tour;
	
	public FRessource(MyGlassPane g, Joueur jo1, Joueur jo2){
		this.setPreferredSize(fSize);
		this.setBackground(Color.blue);
		this.setBorder(BorderFactory.createEtchedBorder());
		this.setLayout(new BorderLayout());
		
		this.joueur1 = jo1;
		this.joueur2 = jo2;
		this.tour = 0;
		this.glass = g;
		
		this.nextTurn.addActionListener(this);
		this.nextTurn.setFocusable(false);
		
		this.add(this.centerPane,BorderLayout.CENTER);//new JScrollPane(
		this.add(this.nextTurn,BorderLayout.EAST);
	}
	
	public void actionPerformed(ActionEvent e) {
		this.activeTurn();
	}
	
	public void setFDamier(FDamier f) {
		this.fDamier = f;
	}
	
	public void activeTurn() {
		if(this.joueurActif != null) {
			this.joueurActif.deactiveAll();
		}
		
		if(this.joueurActif == null) {
			this.joueur1.deactiveAll();
			this.joueur2.deactiveAll();
			this.joueurActif = this.joueur1; //ici calcul du premier joueur
		}
		else if(this.joueurActif == this.joueur1) {
			this.joueurActif = this.joueur2;
		}
		else {
			this.joueurActif = this.joueur1;
		}
		System.out.println("- tour : " + this.tour);
		this.tour ++;
		this.joueurActif.activeAll();
		this.fillRessources();
		this.fDamier.unselect();
		this.fDamier.repaint();
		this.repaint();
	}
	
	public void fillRessources() {
		this.centerPane.removeAll();
		ArrayList<TransferVec> list = this.joueurActif.getVecRestant();
		
		for(TransferVec tv : list){
			int nb = tv.getNbRestant();
			if(nb > 0) {
				this.centerPane.add(new DragImage(glass,tv));
				// System.out.println("drag image en plus");
			}
		}
		this.centerPane.setVisible(false);
		this.centerPane.setVisible(true);
	}
	
	public Joueur getActiveJoueur() {
		return this.joueurActif;
	}
}