package view;

import players.*;

import java.util.ArrayList;


// Gérer l'ensemble des affichages correspondant au jeu.

abstract public class AbstractModelListener {
	ArrayList<ModelView> viewList;//La liste de toutes les vues

	//Constructeur
	public AbstractModelListener() {
		this.viewList = new ArrayList<>();
	}

	//Ajout d'une vue
	public void addModelListener(ModelView newView) {
		viewList.add(newView);
	}

	//Retrait d'une vue
	public void removeModelListener(ModelView view) {
		viewList.remove(view);
	}

	//Changement de la page affiché
	public void fireChangePage() {
		for (int v=0; v<viewList.size(); v++) {
			ModelView view = viewList.get(v);
			view.updatePage();
		}
	}

	//Initialisation du plateau entier
	public void fireBoardInit() {
		for (int v=0; v<viewList.size(); v++) {
			ModelView view = viewList.get(v);
			view.newBoard();
		}
	}

	//Mise à jour de tout ce qu'il y a sur le plateau
	public void fireBoardChange() {
		for (int v=0; v<viewList.size(); v++) {
			ModelView view = viewList.get(v);
			view.updateBoard();
		}
	}

	//Mise à jour de l'emplacement des robots
	public void fireRobotChange() {
		for (int v=0; v<viewList.size(); v++) {
			ModelView view = viewList.get(v);
			view.updateRobots();
		}
	}

	//Mise à jour de la liste des joueurs
	public void firePlayersChange() {
		for (int v=0; v<viewList.size(); v++) {
			ModelView view = viewList.get(v);
			view.updatePlayers();
		}
	}

	//Mise à jour de la proposition du nombre de mouvement d'un joueur
	public void firePlayersProposal(Player player) {
		for (int v=0; v<viewList.size(); v++) {
			ModelView view = viewList.get(v);
			view.updatePlayersProposal(player);
		}
	}

	//Affiche qu'on test la solution de tel joueur
	public void fireTestSolution(Player player, int nbrMoves) {
		for (int v=0; v<viewList.size(); v++) {
			ModelView view = viewList.get(v);
			view.thisPlayerTest(player, nbrMoves);
		}
	}

	//Affiche que tel joueur a échoué à faire fonctionner sa solution
	public void fireFalseSolution(Player player) {
		for (int v=0; v<viewList.size(); v++) {
			ModelView view = viewList.get(v);
			view.playerLoseTurn(player);
		}
	}

	//Affiche que tel joueur a trouvé la meilleur solution
	public void fireWinTurn(Player player) {
		for (int v=0; v<viewList.size(); v++) {
			ModelView view = viewList.get(v);
			view.playerWinTurn(player);
		}
	}

	//Fermeture des vues (fin du programme)
	public void fireCloseView() {
		for (int v=0; v<viewList.size(); v++) {
			ModelView view = viewList.get(v);
			view.closeView();
			//this.viewList.remove(view);
		}
	}
}