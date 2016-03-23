package component;

public class Exit {
	
	private int xPos;
	private int yPos;
	private int angle;
	
	public Exit(int x, int y, int a) {
		this.xPos = x;
		this.yPos = y;
		this.angle = a;
	}
	
	public void setX(int x) {
		this.xPos = x;
	}
	
	public int getX() {
		return xPos;
	}
	
	public void setY(int y) {
		this.yPos = y;
	}
	
	public int getY() {
		return yPos;
	}
	
	public void setAngle(int a) {
		this.angle = a;
	}
	
	public int getAngle() {
		return angle;
	}
}