package view;

import players.*;

// Un moyen de gérer l'affichage du jeu.

abstract public interface ModelView {

	//Mise à jour de la vue
	//abstract public void updateView();

	//Changement de page
	abstract public void updatePage();

	//Affichage reset du plateau
	abstract public void newBoard();

	//Mise à jour de tout ce qu'il y a sur le plateau
	abstract public void updateBoard();

	//Mise à jour de l'emplacement des robots
	abstract public void updateRobots();

	//Mise à jour de la liste des joueurs
	abstract public void updatePlayers();

	//Mise à jour de la proposition d'un joueur
	abstract public void updatePlayersProposal(Player player);

	//Affichage qui dit que tel joueur tente de résoudre en tant de mouvement
	abstract public void thisPlayerTest(Player player, int nbrMoves);

	//Affiche que tel joueur a échoué à faire fonctionner sa solution
	abstract public void playerLoseTurn(Player player);

	//Affichage qui dit que tel joueur a trouvé le nombre minial de mouvement du tour
	abstract public void playerWinTurn(Player player);

	//Fin de l'utilisation de la vue
	abstract public void closeView();
}