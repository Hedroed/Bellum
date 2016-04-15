package component;

public interface Drawable {
	public void draw(java.awt.Graphics g);
	
	public int getX();
	public void setX(int x);
	public int getY();
	public void setY(int y);
	
	public void mousePressed(java.awt.event.MouseEvent e);
}
