package game;


public class Move {
	protected int robotSelected;//Numéro du robot sélectionné
	protected Location destination;

	public Move(int robotSelect, Location destination){
		this.robotSelected=robotSelect;
		this.destination=destination;
	}

	public String toString() {
		return this.robotSelected + " go to " + this.destination;
	}

	public int getRobotSelected() {
		return this.robotSelected;
	}
	public Location getRobotDestination() {
		return this.destination;
	}
}