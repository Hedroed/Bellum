import javax.swing.TransferHandler;
import java.awt.event.MouseEvent;

public class CaseExit extends Case {
	
	private Sortie exit;
	private TransferVec transfer;
	
	private Joueur joueur;
	
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
			//appele la method de fDamier de dortir de vehicule
			this.fDamier.sortirVehicule(this.exit, t.getType(), t.getJoueur());
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
}