package component;

public enum Sortie {
	H_G(2,2,180),
	H_M(4,2,180),
	H_D(6,2,180),
	B_G(2,12,0),
	B_M(4,12,0),
	B_D(6,12,0);
	
	private int x,y,angle;
   
  //Constructeur
  Sortie(int x, int y, int a){
    this.x = x;
	this.y = y;
	this.angle = a;
  }
   
  public int getX(){
    return this.x;
  }
  
  public int getY(){
    return this.y;
  }
  
  public int getAngle(){
    return this.angle;
  }
}