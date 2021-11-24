package view;

import game.*;
import players.*;

import java.util.Scanner;
import java.util.ArrayList;

// Gère l'affichage du jeu par le terminal.

public class TerminalView implements ModelView {
	protected RicochetsRobot game;//Objet dont l'affichage est géré
	protected Scanner clavier;//Pour gérer l'interaction avec le terminal
	//protected ArrayList<TerminalTask> tasks;//Liste des taches, pour empécher les collisions dans l'affichage

	//Constructeur
	public TerminalView(RicochetsRobot game, Scanner clavier) {
		this.game = game;
		this.game.addModelListener(this);
		this.clavier = clavier;
	}

	//Changement de page
	@Override
	public synchronized void updatePage() {
		switch (game.getPage()) {

			case "Menu" :
			useMenu();
			break;


			case "QuickConfig" :
			//Page de configuration d'une partie rapide, en un seul tour. (Exclusif au terminal)

			if(game.getPlayers().isEmpty()) {
				//Affichage différent si il faut créer 1 joueur pour le test
				System.out.println("\n\nPartie rapide");
				System.out.println("=============");

				System.out.println("Plateau de jeu :");
				game.fireBoardInit();
				//Si il n'y a pas de joueurs, on propose de créer un humain ou un robot.
				if(game.getPlayers().isEmpty()) {
					System.out.println("Voulez vous que ça soit l'humain ou le robot qui joue ? 0 pour humain, 1 pour robot.");
					boolean haveToChoose = true;
					while(haveToChoose && game.getPage()=="QuickConfig") {
						String choose = clavier.next();//Recoit le choix
						if(game.getPage()=="QuickConfig") {
							//Si on est toujours sur la page de choix
							if(choose.equals("0") || choose.equals("h") || choose.equals("humain")) {
								//Joueur Humain
								game.addPlayer(new Human(game, "Sujet Humain"));
								haveToChoose = false;
							} else if(choose.equals("1") || choose.equals("r") || choose.equals("robot")) {
								//Joueur Robot A*
								game.addPlayer(new BotAetoile(game, "Le Bot A*pro2000"));
								haveToChoose = false;
							} else System.out.println("Vous avez fait une faute. Choisiez entre humain (0) et robot (1).");
						} else haveToChoose = false;
					}
				}
			}

			//Exécution d'un tour.
			if(game.getPage()=="QuickConfig") game.playTurn();

			game.setPage("Menu");
			break;


			case "ChooseNbrMoves" :
			System.out.println("\n\nRecherche de solutions");
			System.out.println("======================");
			newBoard();
			System.out.println("Les joueurs proposent la meilleur solution en nombre de mouvement.");
			break;


			case "TestSolution" :
			System.out.println("\n\nTest des solutions");
			System.out.println("==================");
			break;


			case "PlayersBeforeGame" :
			//Le menu de la page PlayersBeforeGame avec un bouton pour lancer la partie.
			if(usePlayerMenu("PlayersBeforeGame", "Continuer vers la partie")) game.playGame();
			break;


			case "Players" :
			//Le menu de la page Players avec un bouton retour au menu.
			if (usePlayerMenu("Players", "Retour au menu")) game.setPage("Menu");
			break;


			case "Rules" :
			System.out.println("\n\nRègles du jeu");
			System.out.println("===========");
			System.out.println("Le but du jeu est de trouver, pour chaque tour, un chemin permettant au bon robot d'atteindre la cible en jeu pour ce tour-ci.");
			System.out.println("Il faut proposer un nombre de mouvements à effectuer. Puis prouver que ce nombre de mouvement est valide. Le joueur qui aura trouvé un chemin en le moins de coup possible gagne le point de ce tour.");
			System.out.println("Le premier joueur a avoir 8 points gagne la partie.");

			System.out.println("\nAppuyez sur une touche pour retourner au menu.");
			clavier.next();
			if(game.getPage()=="Rules") game.setPage("Menu");
			break;


			case "Winner" :
			System.out.println("\n\nFin de la partie");
			System.out.println("================");
			Player winner = game.getWinner();
			if(winner!=null) System.out.println("Le gagnant est " + game.getWinner().getName() + " !\n");
			else System.out.println("Il n'y a pas de gagnant ...\n");
			//Affichage de la liste des joueurs
			for(Player player : game.getPlayers()) System.out.println(player.getName()+"   de type "+player.getType()+" a "+player.getPoints()+" points.");

			System.out.println("\nAppuyez sur une touche pour retourner au menu.");
			clavier.next();
			if(game.getPage()=="Winner") game.setPage("Menu");
			break;
		}
	}

	//Gestion de la page de menu
	private void useMenu() {
		//Affichage du menu
		System.out.println("\n\nMenu de jeu");
		System.out.println("===========");
		System.out.println("0 Partie rapide\n1 Partie complète\n2 Création des joueurs\n3 Règles du jeu\n4 Fermer");

		//Gestion du choix dans le menu
		boolean haveToChoose = true;
		while(haveToChoose && game.getPage()=="Menu") {

			int choose = -1;
			try {
				//Recoit le choix
				choose = Integer.parseInt(clavier.next());
				haveToChoose = false;
			} catch (Exception e) {
				//gère les erreurs
				haveToChoose = true;
			}
			if(game.getPage()=="Menu") {
				//Si on est toujours sur le menu
				switch (choose) {
					//Réagit en fonction du choix

					case 0:
					game.setPage("QuickConfig");//Exclusif au terminal
					break;

					case 1:
					System.out.println("\nPartie complète.");
					System.out.println("===============");
					System.out.println("Avant de commencer, il faut choisir les joueurs qui participerons.");
					game.setPage("PlayersBeforeGame");
					break;

					case 2:
					game.setPage("Players");
					break;

					case 3:
					game.setPage("Rules");
					break;

					case 4:
					game.fireCloseView();
					break;

					default:
					haveToChoose = true;
					System.out.println("Vous vous êtes trompé. Choisisez un des nombres proposé.");
					break;
				}
			} else haveToChoose = false;
		}
		
	}

	//Gestion de la page de création de joueur
	private boolean usePlayerMenu(String page, String nextAction) {
		//Affichage du menu
		System.out.println("\n\nPage de création des joueurs");
		System.out.println("============================");
		if(game.getPlayers().isEmpty()) System.out.println("Il n'y a aucun joueur");
		else {
			//Affichage de la liste des joueurs
			for(Player player : game.getPlayers()) System.out.println(player.getName() + "   de type " + player.getType());
		}

		//Gestion du choix dans le menu
		boolean haveToChoose = true;
		while(haveToChoose && game.getPage()==page) {
			System.out.println("\n0 "+nextAction+"\n1 Ajouter un joueur humain\n2 Ajouter un bot A*\n3 Supprimer un joueur");

			int choose = -1;//Recevoir un nombre de l'utilisateur
			String chooseString;//Recevoir un String de l'utilisateur

			chooseString = clavier.next();//Recoit le choix

			if(game.getPage()==page) {
				//Si on est encore dans le menu
				try {choose = Integer.parseInt(chooseString);} catch (Exception e) {//Convertit le choix et gère les erreurs
					if(chooseString.equals("q") || chooseString.equals("quit") || chooseString.equals("exit")) choose = 0;
				}
				switch (choose) {
					//Réagit en fonction du choix

					case 0:
					haveToChoose = false;
					return true;//La sortie déclanche la suite
					//break;

					case 1:
					System.out.println("Entrez le nom du joueur.");
					chooseString = clavier.next();
					game.addPlayer(new Human(game, chooseString));
					break;

					case 2:
					System.out.println("Voulez vous donner un nom au bot ?");
					chooseString = clavier.next();//Recoit le choix
					if(chooseString.equals("0") || chooseString.equals("n") || chooseString.equals("non") || chooseString.equals("Non")) {
						//Pas de nom
						System.out.println("Bot sans nom créé.");
						chooseString = "No Name";
					} else if(chooseString.equals("1") || chooseString.equals("o") || chooseString.equals("oui") || chooseString.equals("Oui")) {
						//Choix du nom
						System.out.println("Entrez le nom du bot.");
						chooseString = clavier.next();
					} else {
						//Autre String, ça sera le nom du bot
						System.out.println("Bot créé sous le nom "+chooseString+".");
					}
					game.addPlayer(new BotAetoile(game, chooseString));
					break;

					case 3:
					if(game.getPlayers().isEmpty()) System.out.println("Aucun joueur à supprimer.");
					else {
						System.out.println("Suppression pas encore possible par le terminal.");
					}
					break;

					default:
					haveToChoose = true;
					System.out.println("Vous vous êtes trompé. Choisisez un des nombres proposé.");
					break;
				}
			} else return false;//Sortie automatisé, sans déclancher la suite. (On a déjà quitté le menu)
		}
		return false;//Sortie automatisé, sans déclancher la suite.
			
	}

	//Mise à jour de la proposition de nombre de mouvement d'un joueur
	@Override
	public void updatePlayersProposal(Player player) {
		int nbrMoves = player.getNbrProposed();
		if(nbrMoves>0) System.out.println(player.getName() + " propose de jouer en " + nbrMoves + " mouvements.");
		else if (nbrMoves<0) System.out.println(player.getName() + " propose de jouer en se donnant un nombre infini de mouvements.");
		else System.out.println(player.getName() + " ne propose rien.");
	}

	//Affiche que tel joueur tente de résoudre en tant de mouvement
	@Override
	public void thisPlayerTest(Player player, int nbrMoves) {
		if(nbrMoves>0) System.out.println("\n" + player.getName() + " tente d'atteindre la cible en " + nbrMoves + " mouvements.\n");
		else if (nbrMoves<0) System.out.println("\n" + player.getName() + " tente d'atteindre la cible en se donnant un nombre infini de mouvements.\n");
		else System.out.println("\n" + player.getName() + " ne propose rien comme solution.\n");

		System.out.println("Plateau initial :");
		newBoard();
	}

	//Affiche que tel joueur a échoué à faire fonctionner sa solution
	@Override
	public void playerLoseTurn(Player player) {
		System.out.println(player.getName() + " a échoué à prouver que sa solution fonctionne.");
	}

	//Affiche que tel joueur a trouvé le nombre minial de mouvement du tour
	@Override
	public void playerWinTurn(Player player) {
		System.out.println(player.getName() + " a trouvé le nombre minimal de mouvements. (" + player.getNbrProposed() + ")");

		System.out.println("\nAppuyez sur une touche pour continuer.");
		clavier.next();
	}

	//Affichage reset du plateau
	@Override
	public void newBoard() {
		System.out.println(this.game.toString());
	}

	//Mise à jour de tout ce qu'il y a sur le plateau
	@Override
	public void updateBoard() {
		newBoard();
	}

	//Mise à jour de l'emplacement des robots
	@Override
	public void updateRobots() {
		newBoard();
	}

	//Mise à jour de la liste des joueurs
	@Override
	public void updatePlayers() {
		//Ne fait rien
	}

	//Fin de la vue sur le terminal
	@Override
	public void closeView() {
		//Ne fait rien
	}
}