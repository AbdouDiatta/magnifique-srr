package players;

import game.*;
import java.util.Scanner;
import java.util.ArrayList;


public class Human extends Player {
	Scanner clavier;

	//Constructeurs
	public Human(RicochetsRobot game, String name, Scanner clavier) {
		super(game, name);
		this.clavier = clavier;
	}
	public Human(RicochetsRobot game, String name) {
		this(game, name, new Scanner(System.in));
	}

	//Obtenir le type
	@Override
	public String getType() {
		return "Humain";
	}

	//Obtenir le nombre de mouvement (avec réflexion)
	@Override
	public void chooseNbrMoves() {
		//Affichage demande
		System.out.println("\n"+name+", entrez le nombre de mouvements minimal que vous avez trouvé. (0 pour ne rien proposer.)");

		//Traitement réponse
		boolean haveToChoose = true;
		while(haveToChoose && game.getPage()=="ChooseNbrMoves") {
			try {
				int choose = Integer.parseInt(clavier.next());//Recoit le choix
				if(giveNbrMoves(choose)) {//On essaye de mettre ce nombre
					//Si le nombre n'est pas correcte
					System.out.println("Le nombre entré n'est pas correcte.");
					System.out.println("Donnez un nombre plus petit que votre encienne proposition. Ou entrez 0 pour ne rien changer à la proposition.");
				} else {
					//Si le nombre a été enregistré.
					haveToChoose = false;
				}
			} catch (Exception e) {//gère les erreurs
				if(game.getPage()=="ChooseNbrMoves")
					System.out.println("Vous n'avez pas entré un nombre. Entrez un nombre.\nVous pouvez entrer 0 pour ne rien proposer.");
			}

		}
	}

	//Obtenir le prochain coup qui sera joué par l'humain. (avec réflexion)
	@Override
	public void chooseNextMove() {
		
		//Les mouvements disponibles
		ArrayList<Move> moves = game.getValidMoves();

		//Si il y a des coups à jouer
		if(moves.size()>0) {
			//Affichage de la demande du coup du joueur
			//System.out.println("Vous pouvez jouer : " + moves);

			boolean haveToChoose = true;
			String chooseRobot = "";
			String chooseX = "";
			String chooseY = "";
			Move move;
			while (haveToChoose && game.getPage()=="TestSolution") {

				//Demande du robot à bouger
				System.out.println(name+", entrez le numéro du robot que vous bougez : ");
				chooseRobot = clavier.next();

				if(game.getPage()=="TestSolution") {
					//Demande des coordonnées où le bouger.
					System.out.println("Entrez les coordonnées x puis y où vous allez : ");
					chooseX = clavier.next();
				}
				if(game.getPage()=="TestSolution")
					chooseY = clavier.next();

				if(game.getPage()=="TestSolution") {
					//Conversion en mouvement
					int robot, x, y;
					try {
						robot = Integer.parseInt(chooseRobot);//Recoit le robot choisi par le joueur
						x = Integer.parseInt(chooseX);//Recoit le x
						y = Integer.parseInt(chooseY);//Recoit le y
						move = new Move(robot, new Location(x,y));

						//Si le mouvement est valide, on le donne (il n'y a plus de boucle), sinon on redemande un mouvement.
						if (game.isValid(move)) {
							giveNextMove(move);
							haveToChoose = false;
							break;
						} else System.out.println("Le mouvement n'est pas valide. Essayez un autre.");

					} catch (Exception e) {//gère les erreurs
						System.out.println("Vous avez fait une erreur. Entrez 3 nombres.");
					}
				}
			}

		} else {
			//Si il n'y a aucun coup à jouer
			System.out.println(name+" ne peut rien jouer.");
		}
	}
}
