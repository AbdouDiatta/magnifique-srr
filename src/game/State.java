package game;


//Un état de partie à un instant d'un tour
public class State {
	protected Location robotLocations[];


	//Constructeur
	public State(Location robotLocations[]) {
		this.robotLocations = robotLocations;
	}

	//Accesseur
	public Location[] getRobotLocations() {
		return this.robotLocations;
	}

	//Test d'égalité
	public boolean equals(State state) {
		boolean test = true;
		//Test en profondeur de l'égalitée
		for(int i=0; i<robotLocations.length; i++) {
			if(!robotLocations[i].equals(state.getRobotLocations()[i])) return false;
		}
		return test;
	}

	//Méthode de copie
	public State copy() {
		Location[] tmp = new Location[robotLocations.length];
		for(int i=0; i<robotLocations.length; i++) {
			tmp[i] = robotLocations[i];
		}
		return new State(tmp);
	}

	//Execute sur cet état et en donne l'état obtenu.
	public State execute(Move move) {
		//Test eventuel de si le Mouvement est autorisé ?

		//Copie en profondeur de l'état
		State newState = this.copy();
		
		//Changement des Coordonnées du robot. PAS ENCORE TESTE !! RISQUE DE NE PAS MARCHER
		newState.getRobotLocations()[move.getRobotSelected()] = move.getRobotDestination();

		return newState;
	}
}