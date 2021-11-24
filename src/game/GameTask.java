package game;

import players.*;


//Les taches en cours pour le jeu
abstract public class GameTask extends Thread {
	protected Player player;
	protected String taskName;

	//Constructeur
	public GameTask(Player player, String taskName) {
		this.player = player;
		this.taskName = taskName;
	}

	//Lancement de la tache
	abstract public void run();

	//Dit si la tache correspond à ce joueur qui fait tel tache
	public boolean isTask(Player player, String taskName) {
		return this.player==player && this.taskName==taskName;
	}

	//Dit si la tache est celle ci
	public boolean isTask(String taskName) {
		return this.taskName==taskName;
	}
}