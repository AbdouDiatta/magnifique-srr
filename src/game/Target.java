package game;

public class Target {
	protected int robotTarget;//Le robot ciblé, 4 pour dire tous robot possible (C'est la cible vortexe cosmic)
	protected Location location;//L'emplacement de la cible
	protected String image;//L'image utilisé pour l'affichage
	protected Color color;//Couleur de la cible

	//Constructeur
	public Target(int robotTarget, Location location) {
		this.robotTarget = robotTarget;
		this.location = location;
		switch(robotTarget) {
			case 0:
			color = Color.red;
			image = "./images/TargetRed.png";
			break;

			case 1:
			color = Color.green;
			image = "./images/TargetGreen.png";
			break;

			case 2:
			color = Color.blue;
			image = "./images/TargetBlue.png";
			break;

			case 3:
			color = Color.yellow;
			image = "./images/TargetYellow.png";
			break;

			default:
			color = Color.yellow;
			image = "./images/TargetMulti.png";
			break;
		}
	}

	//Accesseurs
	public String toString() {
		if (robotTarget == 4) return "Cible Vortex cosmic (pour tout robot) situé en " + location;
		else return "Cible situé en " + location + " de la même couleur que robot numéro " + robotTarget;
	}

	public int getRobot() {
		return robotTarget;
	}

	public Location getLocation() {
		return location;
	}

	public int getRobotTarget() {
		return robotTarget;
	}

	public String getImage() {
		return image;
	}

	public Color getColor() {
		return color;
	}

	//Déplace la cible selon des variations sur les 2 axes et éventuellement une inversion des axes.
	public void move(int addX, int addY, boolean rotation) {
		//On récupère les coordonnées
		int x = location.getX();
		int y = location.getY();

		//On change la coordonnée x
		if(addX<0) x = -addX-x;
		else x = x+addX;

		//On change la coordonnée y
		if(addY<0) y = -addY-y;
		else y = y+addY;

		//On enrengiste les coordonnées, en faisant la rotation si il y en a
		if(!rotation) location.set(x, y);
		else location.set(y, x);
	}
}