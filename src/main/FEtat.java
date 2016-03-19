package main;

import vehicule.*;
import component.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import java.awt.Graphics;
import javax.swing.BorderFactory;

import javax.swing.JTextArea;
import javax.swing.text.StyleConstants;
import javax.swing.text.SimpleAttributeSet;

import org.json.*;
import java.util.Scanner;
import java.io.UnsupportedEncodingException;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import javax.swing.JCheckBox;


public class FEtat extends JPanel {

	private Dimension size = new Dimension(200,700);

	private FDamier fDamier;

	private Vehicule vActive;
	private Image vImage;
	private FImage fImage;
	private JLabel vName;
	private JTextArea texte;
	private JLabel lVie;
	private LifeBar lifeBar;
	private JLabel lPortee;
	private JLabel vPortee;
	private JLabel lDep;
	private JLabel vDep;
	private JCheckBox check;

	public FEtat(){
		this.setPreferredSize(size);
		this.setBackground(Color.white);
		this.setBorder(BorderFactory.createEtchedBorder());
		this.setLayout(new BorderLayout());
		
		JPanel container = new JPanel();
		container.setBackground(Color.white);
		
		this.fImage = new FImage();
		container.add(this.fImage);

		//ajout label nom du vehicule
		this.vName = new JLabel();
		this.vName.setPreferredSize(new Dimension(186,20));
		this.vName.setHorizontalAlignment(JLabel.CENTER);
		container.add(this.vName);
		this.vName.setVisible(false);

		//ajout text description
		this.texte = new JTextArea("");
		this.texte.setEditable(false);
		this.texte.setPreferredSize(new Dimension(186,120));
		Font police = new Font("Tahoma", Font.CENTER_BASELINE, 12);
		this.texte.setFont(police);
		this.texte.setForeground(Color.black);
		this.texte.setAlignmentX(JTextArea.CENTER_ALIGNMENT);
		this.texte.setLineWrap(true);
		this.texte.setWrapStyleWord(true);
		container.add(this.texte);
		this.texte.setVisible(false);

		//ajout bar de vie vehicule
		this.lVie = new JLabel("Vie");
		this.lVie.setPreferredSize(new Dimension(186,20));
		this.lVie.setHorizontalAlignment(JLabel.CENTER);
		container.add(this.lVie);
		this.lVie.setVisible(false);
		this.lifeBar = new LifeBar();
		this.lifeBar.setMax(3);
		this.lifeBar.setCurrent(2);
		container.add(this.lifeBar);
		this.lifeBar.setVisible(false);

		//ajout label portee du vehicule
		this.lPortee = new JLabel("Portee de tir");
		this.lPortee.setPreferredSize(new Dimension(186,20));
		this.lPortee.setHorizontalAlignment(JLabel.CENTER);
		container.add(this.lPortee);
		this.lPortee.setVisible(false);
		this.vPortee = new JLabel();
		this.vPortee.setPreferredSize(new Dimension(186,20));
		this.vPortee.setHorizontalAlignment(JLabel.CENTER);
		container.add(this.vPortee);
		this.vPortee.setVisible(false);

		//ajout label deplacement du vehicule
		this.lDep = new JLabel("Deplacement");
		this.lDep.setPreferredSize(new Dimension(186,20));
		this.lDep.setHorizontalAlignment(JLabel.CENTER);
		container.add(this.lDep);
		this.lDep.setVisible(false);
		this.vDep = new JLabel();
		this.vDep.setPreferredSize(new Dimension(186,20));
		this.vDep.setHorizontalAlignment(JLabel.CENTER);
		container.add(this.vDep);
		this.vDep.setVisible(false);

		//ajout de la checkBox
		this.check = new JCheckBox("Afficher cases de tirs",true);
		this.check.setFocusable(false);
		// this.setPreferredSize(new Dimension(186,30));
		this.check.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.out.println("- etat : " + ((JCheckBox)e.getSource()).isSelected());
					fDamier.setAffTir(((JCheckBox)e.getSource()).isSelected());
					// repaint();
				}
			}
		);
		
		this.add(container,BorderLayout.CENTER);
		this.add(this.check,BorderLayout.SOUTH);
	}

	// public void paintComponent(Graphics g){

		// g.setColor(Color.white);
		// g.fillRect(0,0,this.getWidth(), this.getHeight());
		// g.drawImage(this.vImage, 0, 0, this);
		// this.fImage.repaint();

	// }

	public void setFDamier(FDamier f){
		this.fDamier = f;
	}

	public void setActiveVehicule(Vehicule v){
		this.vActive = v;

		if(v != null) {
			this.vName.setText(v.getType().name()+" de "+v.getJoueur().getName());
			try {
				String content = new Scanner(new File("ressources/description.txt")).useDelimiter("\\Z").next();
				JSONObject obj = new JSONObject(content);

				String desc = obj.getJSONObject(this.vActive.getType().name()).getString("description");
				this.texte.setText(desc);
			}
			catch(IOException e) {}
			this.lifeBar.setMax(v.getType().getVieMax());
			this.lifeBar.setCurrent(v.getLife());

			this.vPortee.setText(this.vActive.getType().getPortee()+" cases");
			int dep = this.vActive.getType().getDep();
			String s = null;
			if(dep > 1) {
				s = dep+" cases";
			}
			else {
				s = dep+" case";
			}
			this.vDep.setText(s);

			//active les composants :)
			this.fImage.setVec(v);
			this.vName.setVisible(true);
			this.texte.setVisible(true);
			this.lVie.setVisible(true);
			this.lifeBar.setVisible(true);
			this.lPortee.setVisible(true);
			this.vPortee.setVisible(true);
			this.lDep.setVisible(true);
			this.vDep.setVisible(true);
		}
		else {
			this.fImage.setVec(null);
			this.vName.setVisible(false);
			this.texte.setVisible(false);
			this.lVie.setVisible(false);
			this.lifeBar.setVisible(false);
			this.lPortee.setVisible(false);
			this.vPortee.setVisible(false);
			this.lDep.setVisible(false);
			this.vDep.setVisible(false);
		}
		
		repaint();
	}

}
