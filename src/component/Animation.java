package component;


public abstract class Animation {

	// protected int coordX,coordY;
	protected double posX, posY;
	
	protected int time; //nombre de draw avant de disparaitre
	protected int ind = 0;
	
	public Animation(int x, int y, int t) {
		posX = (double)x;
		posY = (double)y;
		time = t;
	}
	
	public Animation(int x, int y) {
		posX = (double)x;
		posY = (double)y;
		time = 10;
	}
	
	public void setTime(int t) {
		time = t;
	}
	
	public int getTime() {
		return time;
	}
	
	public abstract void draw(java.awt.Graphics g);
	
	public boolean isEnd() {
		if(ind >= time) {
			return true;
		}
		return false;
	}
	
}