package game;


public class Location {
	private int x,y;

	//Constructeur
	public Location(int x,int y) {
		this.x = x;
		this.y = y;
	}

	//Affichage
	public String toString() {
		return "("+this.x+","+this.y+")";
	}

	//Accesseur
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public boolean equals(Location other) {
		return (this.x == other.getX() && this.y == other.getY());
	}

	public boolean equals(int otherX, int otherY) {
		return (this.x == otherX && this.y == otherY);
	}

	public void set(int x, int y) {
		this.x = x;
		this.y = y;
	}
}