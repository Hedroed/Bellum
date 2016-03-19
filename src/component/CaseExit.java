package component;

import joueur.Joueur;
import main.*;

import javax.swing.TransferHandler;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.Graphics;

public class CaseExit extends Case {
	
	private Sortie exit;
	private TransferVec transfer;
	
	private Joueur joueur;
	private boolean canExitVehicule = false;
	
	public CaseExit(int x, int y, FDamier fd, Sortie s, Joueur jo) {
		super(x, y, fd);
		this.exit = s;
		this.joueur = jo;
		
		//set le transferHandle
		this.setTransferHandler(new TransferHandler("transfer"));
	}
	
	public TransferVec getTransfer() {
		return this.transfer;
	}
	
	public void setTransfer(TransferVec t) {
		this.transfer = t;
		
		if(t.canExtract(this.joueur)) {
			System.out.println("Transfer: "+t);
			//appele la method de fDamier de sortir de vehicule
			this.fDamier.sortirVehicule(this.exit, t);
			this.transfer = null;
		}
	}
	
	public boolean isExit() {
		return true;
	}
	
	public void setExit(Sortie s) {
		this.exit = s;
	}
	
	public Sortie getExit() {
		return this.exit;
	}
	
	public void setJoueur(Joueur jo) {
		this.joueur = jo;
	}
	
	public Joueur getJoueur() {
		return this.joueur;
	}
	
	public void setCanExitVehicule(boolean b) {
		this.canExitVehicule = b;
	}
	
	public void paintComponent(Graphics g){ 
		super.paintComponent(g);
		
		if(this.canExitVehicule) {
			g.setColor(new Color(255,255,255,128));
			g.fillRect(0,0,this.getWidth(), this.getHeight());
		}
	}
}