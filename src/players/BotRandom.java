package players;

import game.*;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;


public class BotRandom extends Player {
	Random random;

	//Constructeurs
	public BotRandom(RicochetsRobot game) {
		super(game, "No Name");
		this.random = new Random();
	}

	//Obtenir le nom
	@Override
	public String getName() {
		if(name == "No Name") return "BOT"+this.hashCode();
		else return name;
	}

	//Obtenir le type
	@Override
	public String getType() {
		return "Bot Random";
	}

	//Obtenir le nombre de mouvement (avec réflexion)
	@Override
	public void chooseNbrMoves() {
		giveNbrMoves(-1);
	}

	//Obtenir le coup qui sera joué par le bot. (avec réflexion)
	@Override
	public void chooseNextMove() {
		
		//Les mouvements disponibles
		ArrayList<Move> moves = game.getValidMoves();

		//Si il y a des coups à jouer
		if(moves.size()>0) {
			//Choisi aléatoirement
			Move move = moves.get(random.nextInt(moves.size()));
			System.out.println("\nLe bot a choisi aléatoirement un mouvement.");
			giveNextMove(move);

		} else{
			//Si il n'y a aucun coup à jouer
			System.out.println("\nLe bot dit qu'il ne peux rien jouer.");
		}
	}
}
