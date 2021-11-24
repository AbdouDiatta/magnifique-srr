package players;

import game.*;

import java.util.*;
import static java.lang.StrictMath.abs;


public class Node {
	private State state;//Etat du plateau à ce moment

	private int cost;//Cout en nombre de mouvement minimum pour aller à cet état
	private int heuristic;//L'intérret de la Node (pour savoir quand la traiter)
	private int specialMoves;//Nombre de mouvement de robot non concerné par la cible. (pour l'heuristic)

	private ArrayList<Move> possibilities;//Toutes les possibilités de mouvement à partir d'ici
	private Node previous;//La Node précédente à celle-ci.


	//Constructeur
	public Node(State state, int cost, boolean useSpecialMove, Node previous, Target target) {
		this.state = state;
		this.cost = cost;
		this.possibilities = null;
		this.previous = previous;

		//Calcul de la valeur de specialMoves
		if(previous==null) specialMoves = 0;
		else specialMoves = previous.getSpecialMoves();
		if(useSpecialMove) specialMoves++;

		findHeusistic(target);//Donne une valeur a l'heuristic
	}

	//Accesseur
	public State getState() {
		return state;
	}
	public Location getLocation(int robot) {
		//Les coordonnées du robot en question
		return state.getRobotLocations()[robot];
	}
	public int getCost() {
		return cost;
	}
	public int getHeuristic() {
		return heuristic;
	}
	public int getSpecialMoves() {
		return specialMoves;
	}
	public ArrayList<Move> getPosibilities() {
		return possibilities;
	}
	public Node getPrevious() {
		return previous;
	}

	public void setPossibility(ArrayList<Move> possibilities) {
		this.possibilities = possibilities;
	}

	//Comparaison des états
	public boolean isSameState(State otherState) {
		return this.state.equals(otherState);
	}

	//Execute sur cet état et en donne l'état obtenu.
	public Move getMoveToMake(State objectifState) {

		//Pour chaque mouvement possible
		for(Move move : possibilities) {

			//Copie en profondeur de l'état
			State newState = this.state.copy();

			//On test si le mouvement donne l'état recherché.
			if(newState.execute(move).equals(objectifState)) return move;
		}

		return null;//Erreur. Aucun mouvement ne permet d'obtenir cet état.
	}

	//Calcul de l'heuristic (intérret de la node)
	private void findHeusistic(Target target) {
		int distance = 0;
		//Si la cible désigne un robot particulier, on calcul la distance entre ce robot et la cible
		if(target.getRobot() < 4) distance = countDistance(state.getRobotLocations()[target.getRobot()],target.getLocation());
		else {
			//Si la cible désigne n'importe quelle robot. On cherche la distance minimale.
			int minD = countDistance(state.getRobotLocations()[0],target.getLocation());
			for (int aRobot=1; aRobot<4; aRobot++) {
				 int test = countDistance(state.getRobotLocations()[aRobot],target.getLocation());
				 if(test < minD) minD = test;
			}
			distance = minD;
		}
		//Dans le calcul, le cout est ce qui a le plus d'impact, et specialMoves et la distance un impact négatif sur l'interret de la node.
		heuristic = cost*3 + distance + specialMoves*2;
	}

	//Calcul la distance parcourable minimal entre 2 points
	private int countDistance(Location c1, Location c2) {
		int x = c2.getX() - c1.getX();
		int y = c2.getY() - c1.getY();
		return abs(x) + abs(y);
	}
}