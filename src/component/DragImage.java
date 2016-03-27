package component;

import main.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;
import java.awt.Graphics;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import glass.*;

public class DragImage extends JPanel {
	
	private Dimension size = new Dimension(64,74);
	private Image image;
	private TransferVec transfer;
	
	public DragImage(MyGlassPane glass, TransferVec t, FDamier fDamier){
		this.setPreferredSize(size);
		this.setBackground(Color.white);
		this.addMouseListener(new MouseGlassListener(glass,fDamier));
		this.addMouseMotionListener(new MouseGlassMotionListener(glass));

		this.transfer = t;
		
		BufferedImage bigImage = null;
		try {
			bigImage = ImageIO.read(new File(t.getType().getLien()));
			this.image = bigImage.getSubimage(0,0,64,64);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void paintComponent(Graphics g){
		// g.setColor(Color.white);
		// g.fillRect(0,0,this.getWidth(), this.getHeight())
		
		g.drawImage(this.image,0,0,this);
		g.setColor(Color.red);
		g.drawString("x"+this.transfer.getNbRestant(),25,74);
	}
	
	public TransferVec getTransfer() {
		return this.transfer;
	}
}