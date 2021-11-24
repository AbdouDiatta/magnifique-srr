package game;


public class Robot {
	protected String name;
	protected String image;
	protected Color color;

	//Constructeur personnalisé
	public Robot(String name, String image, Color color) {
		this.name = name;
		this.image = image;
		this.color = color;
	}

	//Constructeur automatique
	public Robot(int type) {
		switch(type){
			//A chaque numéro correpond une couleur différente.

			case 0:
			name = "Robot Rouge";
			image = "./images/RobotRed.png";
			color = Color.red;
			break;

			case 1:
			name = "Robot Vert";
			image = "./images/RobotGreen.png";
			color = Color.green;
			break;

			case 2:
			name = "Robot Bleu";
			image = "./images/RobotBlue.png";
			color = Color.blue;
			break;

			default:
			name = "Robot Jaune";
			image = "./images/RobotYellow.png";
			color = Color.yellow;
			break;
		}
	}

	//Accesseurs
	public String toString() {
		return getName();
	}

	public String getName() {
		return name;
	}

	public String getImage() {
		return image;
	}

	public Color getColor() {
		return color;
	}
}

enum Color {
	red,
	green,
	yellow,
	blue
}