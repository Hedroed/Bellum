package glass;

import java.awt.Component;
import javax.swing.SwingUtilities;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;


public class MouseGlassMotionListener extends MouseAdapter{

  private MyGlassPane myGlass;
   
  public MouseGlassMotionListener(MyGlassPane glass){
    myGlass = glass;
  }
   
  /**
  * Méthode fonctionnant sur le même principe que la classe précédente
  * mais cette fois sur l'action de déplacement
  */
  public void mouseDragged(MouseEvent event) {
    //Vous connaissez maintenant…
    Component c = event.getComponent();

    Point p = (Point) event.getPoint().clone();
    SwingUtilities.convertPointToScreen(p, c);
    SwingUtilities.convertPointFromScreen(p, myGlass);
    myGlass.setLocation(p);
    myGlass.repaint();
  }
}