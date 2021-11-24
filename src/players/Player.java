package players;

import game.*;

import java.util.ArrayList;


abstract public class Player {
	protected RicochetsRobot game;//Le lien pour communiquer avec la partie.
	protected int points;//Les point pour la session de jeu
	protected int nbrOfMoves;//La proposition de nombre de mouvement pour ce tour.
	protected String name;//Nom du joueur


	//Constructeur
	public Player(RicochetsRobot game, String name) {
		this.game = game;
		this.points = 0;
		this.nbrOfMoves = 0;
		this.name = name;
	}

	//Affichage du joueur
	public String toString() {
		return getName();
	}

	//Obtenir le nom
	public String getName() {
		if(name == "No Name") return "Joueur n° "+this.hashCode();
		else return name;
	}

	//Obtenir le type
	abstract public String getType();

	//Obtenir la dernière proposition de nombre de mouvement
	public int getNbrProposed() {
		return this.nbrOfMoves;
	}

	//Obtenir les points
	public int getPoints() {
		return this.points;
	}

	//Augmenter son nombre de points
	public void increasePoint() {
		this.points++;
	}

	//Choisir le nombre de mouvement
	abstract public void chooseNbrMoves();

	//Donner publiquement un nouveau nombre de mouvement
	public boolean giveNbrMoves(int choose) {

		if(nbrOfMoves>0) {//Le cas où un choix limité a déjà été donné
			if(choose<nbrOfMoves && choose>0) {
				nbrOfMoves = choose;
				game.firePlayersProposal(this);
				return false;//Pas d'erreur
			} else {
				return choose!=0;//Le nombre n'est pas correcte. 0 ne fait rien et le reste est une erreur.
			}
		} else {//Le cas où aucun choix limité n'a été donné
			nbrOfMoves = choose;
			game.chosenNbrMove(this);
			return false;//Pas d'erreur
		}
	}

	//Choisir le mouvement suivant
	abstract public void chooseNextMove();

	//Donner publiquement un nouveau nombre de mouvement
	public boolean giveNextMove(Move move) {

		if(game.isValid(move)) {
			if(game.getPage()=="TestSolution") game.playMove(this, move);
			return false;//Pas d'erreur
		} else return true;//Erreur
	}

	//Passer au tour suivant, donc reset ce qui doit l'être
	public void toNextTurn() {
		this.nbrOfMoves = 0;
	}
}
