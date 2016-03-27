package glass;

import main.*;

import java.awt.Component;
import javax.swing.SwingUtilities;
import java.awt.Point;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import javax.swing.JComponent;

public class MouseGlassListener extends MouseAdapter{

  private MyGlassPane myGlass;
  private FDamier fDamier;
  private BufferedImage image;
  
  public MouseGlassListener(MyGlassPane glass, FDamier f){
    myGlass = glass;
	fDamier = f;
  }
   
  public void mousePressed(MouseEvent event) {
    //On récupère le composant pour en déduire sa position
    Component composant = event.getComponent();
    Point location = (Point)event.getPoint().clone();
      
    //Les méthodes ci-dessous permettent, dans l'ordre, 
    //de convertir un point en coordonnées d'écran
    //et de reconvertir ce point en coordonnées fenêtres
    SwingUtilities.convertPointToScreen(location, composant);
    SwingUtilities.convertPointFromScreen(location, myGlass);
        
    //Les instructions ci-dessous permettent de redessiner le composant
    image = new BufferedImage(composant.getWidth(), composant.getHeight(), BufferedImage.TYPE_INT_ARGB);
    Graphics g = image.getGraphics();
    composant.paint(g);
        
    //On passe les données qui vont bien à notre GlassPane
    myGlass.setLocation(location);
    myGlass.setImage(image);
    
	//assombrie le damier
	fDamier.setDark(true);
	
    //On n'oublie pas de dire à notre GlassPane de s'afficher
    myGlass.setVisible(true);
  }

  public void mouseReleased(MouseEvent event) {
    //---------------------------------------------------------------------
    JComponent lab = (JComponent)event.getSource();
    //---------------------------------------------------------------------
      
    //On récupère le composant pour en déduire sa position
    Component composant = event.getComponent();
    Point location = (Point)event.getPoint().clone();
	
    //Les méthodes ci-dessous permettent, dans l'ordre, 
    //de convertir un point en coordonnées d'écran
    //et de reconvertir ce point en coordonnées fenêtre
    SwingUtilities.convertPointToScreen(location, composant);

	Point locationDamier = (Point)location.clone();
    SwingUtilities.convertPointFromScreen(location, myGlass);
	
	//On recupere les coordonnées sur le damier
	SwingUtilities.convertPointFromScreen(locationDamier, fDamier);	
	
    //On passe les données qui vont bien à notre GlassPane
    myGlass.setLocation(location);
    myGlass.setImage(null);
	
	//damier
	fDamier.setDark(false);
	fDamier.sortirVehicule(locationDamier, lab);
	
    //On n'oublie pas de ne plus l'afficher
    myGlass.setVisible(false);
      
  }
}