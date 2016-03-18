
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.AlphaComposite;
import java.awt.Dimension;
import java.awt.image.BufferedImage;

public class MenuPane extends JPanel {
	
	
	private String title;
	private String[] options = {"Jouer ","Option ","Quitter"};
	
	private BufferedImage background;
	private PlaySound soundChange ;
	
	private int current = 0;
	
	public MenuPane() {
		this.title = "Bellum";
		this.soundChange = new PlaySound("ressources/menu.wav",-30);
		
		//load background
		// try {
			// background = ImageIO.read(new File("ressources/background.png"));
		// } catch (IOException e) {
			// e.printStackTrace();
		// }
		
	}
	
	public void down() {
		current++;
		if(current >= options.length) {
			current = 0;
		}
		this.soundChange.play();
		this.repaint();
	}
	
	public void up() {
		current--;
		if(current < 0) {
			current = options.length -1;
		}
		this.soundChange.play();
		this.repaint();
	}
	
	public void close() {
		this.soundChange.close();
	}
	
	public void paintComponent(Graphics g) {
		//clear screen
		g.setColor(Color.white);
		g.fillRect(0,0,getWidth(),getHeight());
		
		//draw background
		
		//draw text
		Font f1 = null;
        try {
            f1 = Font.createFont(Font.TRUETYPE_FONT, new File("ressources/DALEK.ttf"));
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		f1 = f1.deriveFont(70f);
		g.setColor(new Color(128,0,0));
		g.setFont(f1);
		
		g.drawString(title,(getWidth()-(title.length()*35))/2,70);
		
		f1 = f1.deriveFont(30f);
		g.setFont(f1);
		
		for(int i = 0 ; i < options.length; i++) {
			
			if(current == i) {
				g.setColor(new Color(170,0,0));
			}
			else {
				g.setColor(Color.black);
			}
			g.drawString(options[i], (getWidth()-(options[i].length()*15))/2,450+(30*i));
		}
	}
	
}





