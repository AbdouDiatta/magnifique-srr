package game.board;

import game.*;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Random;


public class BoardGenerator {
	//Plateau
	protected Boolean[][] wallsX;//Murs entre la gauche et la droite
	protected Boolean[][] wallsY;//Murs entre la haut et le bas
	//Sur le plateau
	protected ArrayList<Target> targets;//Les cibles à trouver
	protected Location[] robotLocations;//Positions de base des robots


	//Constructeur
	public BoardGenerator(int generation) {
		//Si generation vaux 0, c'est un plateau entier prédéfini pour les tests
		//Si generation vaux 1, c'est un assemble des bouts de plateau fourni par le jeu
		//Si generation est un autre nombre, c'est des nouveau bouts de plateau inventé


		// ======== Initialisation (sans remplir)
		//Plateau
		//La taille normale est 16 sur 16 cases.
		/*[ligne][colonne], wallsX aura toujours +1 ligne
		  [ligne][colonne], wallsY aura toujours +1 colonne  */
		wallsX = new Boolean[17][16];
		wallsY = new Boolean[16][17];
		//Cibles
		targets = new ArrayList<Target>();
		//Robots
		robotLocations = new Location[4];


		// ======== Remplissage

		//met tout à 0
		for (int i=0; i<16; i++) {
			for (int j=0; j<=16; j++) {
				wallsX[j][i] = false;
				wallsY[i][j] = false;
			}
		}

		//On remplit
		if(generation==0) {
			//Dans le cas du plateau prédéfini pour les tests

			//mets les bords (la ligne ou colonne ajouté serra remplie)
			for (int i = 0; i < 16; i++) {
				wallsX[0][i] = true;//A gauche
				wallsX[16][i] = true;//A droite
				wallsY[i][0] = true;//En bas
				wallsY[i][16] = true;//En haut
			}

			//Les Murs des tests
			wallsY[0][3] = true;
			wallsY[0][9] = true;
			wallsX[6][3] = true;
			wallsY[5][3] = true;
			wallsX[2][4] = true;
			wallsY[1][5] = true;
			wallsY[4][1] = true;
			wallsX[4][1] = true;
			wallsY[8][6] = true;
			wallsX[8][5] = true;
			wallsX[12][7] = true;
			wallsY[11][7] = true;
			wallsX[12][4] = true;
			wallsY[12][4] = true;
			wallsX[2][11] = true;
			wallsY[2][11] = true;
			wallsX[6][10] = true;
			wallsY[6][11] = true;
			wallsX[12][11] = true;
			wallsY[11][12] = true;
			wallsX[3][6] = true;
			wallsY[3][7] = true;
			wallsX[12][13] = true;
			wallsY[12][14] = true;
			wallsX[14][10] = true;
			wallsY[5][14] = true;
			wallsX[6][14] = true;
			wallsY[14][10] = true;
			wallsY[15][4] = true;
			wallsY[15][13] = true;
			wallsX[3][15] = true;
			wallsX[10][15] = true;
			wallsX[7][0] = true;
			wallsX[11][0] = true;
			wallsY[10][2] = true;
			wallsX[10][1] = true;
			/*
			wallsY[15][13] = true;
			wallsX[3][15] = true;
			wallsX[10][15] = true;
			wallsX[5][0] = true;
			wallsX[11][0] = true;
			wallsY[12][2] = true;
			wallsX[12][1] = true;*/


			//Centre du plateau
			wallsX[7][7] = true;
			wallsX[7][8] = true;
			wallsX[9][7] = true;
			wallsX[9][8] = true;
			wallsY[7][7] = true;
			wallsY[8][7] = true;
			wallsY[7][9] = true;
			wallsY[8][9] = true;

			//Robots
			robotLocations[0] = new Location(0, 15);
			robotLocations[1] = new Location(15, 15);
			robotLocations[2] = new Location(0, 0);
			robotLocations[3] = new Location(15, 0);

			//Cibles
			//Test avec moins de cible que dans les 17 cibles normalement
			targets.add(new Target(2, new Location(15, 1)));
			targets.add(new Target(1, new Location(6, 3)));
			targets.add(new Target(3, new Location(2, 11)));
			targets.add(new Target(0, new Location(12, 13)));
			targets.add(new Target(4, new Location(10, 1)));
			targets.add(new Target(2, new Location(11, 6)));
			targets.add(new Target(2, new Location(11, 7)));//Difficile
			targets.add(new Target(4, new Location(15, 12)));
			//targets.add(new Target(1, new Location(5, 3)));//Problème difficile. Voir *X*
			/*

			*X*
			Je crois qu'il faut au moins 15 mouvements.
			Il me parais impossible de résoudre le problème sans bouger plusieurs robots non concerné par la cible.
			*/

		} else {
			//Dans le cas de la génération d'un nouveau plateau


			//Création des Bouts de plateau
			ArrayList<BoardPart> boardParts = new ArrayList<BoardPart>();
			for (int b=0; b<4; b++) {
				if(generation==1) boardParts.add(new BoardPart(b));//Generation d'un des bout de plateau fourni
				else boardParts.add(new BoardPart(4));//Generation d'un bout de plateau inventé à la volé
			}


			//Sélectionnera des bouts de plateau
			Random random = new Random(System.nanoTime());
			BoardPart boardPart;
			Boolean[][] partWallsX;
			Boolean[][] partWallsY;
			ArrayList<Target> partTargets;

			//Pour chacun des 4 bouts de plateau
			for(int part=4; part>0; part--) {

				//On sélectionne un bout de plateau
				boardPart = boardParts.get(random.nextInt(part));
				boardParts.remove(boardPart);
				//On en prend le contenu
				partWallsX = boardPart.getWallsX();
				partWallsY = boardPart.getWallsY();
				partTargets = boardPart.getTargets();

				switch(part) {
					//On place en fonction de l'emplacement ou le placer

					case 4:
					//Place le bout de plateau en haut à gauche
					for (int i=0; i<8; i++) {
						for (int j=0; j<=8; j++) {
							wallsX[j][8+i] = partWallsX[j][i];
							wallsY[i][8+j] = partWallsY[i][j];
						}
					}
					//Récupère aussi les emplacements des cibles deplacés
					for (Target target : partTargets) {
						target.move(0, 8, false);
						targets.add(target);
					}
					break;


					case 3:
					//Place le bout de plateau en haut à droite
					for (int i=0; i<8; i++) {
						for (int j=0; j<=8; j++) {
							wallsX[8+j][8+i] = wallsX[8+j][8+i] || partWallsY[7-i][j];
							wallsY[8+i][8+j] = wallsY[8+i][8+j] || partWallsX[8-j][i];
						}
					}
					//Récupère aussi les emplacements des cibles deplacés
					for (Target target : partTargets) {
						target.move(-7, 0, true);
						targets.add(target);
					}
					break;


					case 2:
					//Place le bout de plateau en bas à gauche
					for (int i=0; i<8; i++) {
						for (int j=0; j<=8; j++) {
							wallsX[j][i] = wallsX[j][i] || partWallsY[i][8-j];
							wallsY[i][j] = wallsY[i][j] || partWallsX[j][7-i];
						}
					}
					//Récupère aussi les emplacements des cibles deplacés.
					for (Target target : partTargets) {
						target.move(0, -7, true);
						targets.add(target);
					}
					break;

					default:
					//Place le bout de plateau en bas à droite
					for (int i=0; i<8; i++) {
						for (int j=0; j<=8; j++) {
							wallsX[16-j][7-i] = wallsX[16-j][7-i] || partWallsX[j][i];
							wallsY[15-i][8-j] = wallsY[15-i][8-j] || partWallsY[i][j];
						}
					}
					//Récupère aussi les emplacements des cibles deplacés.
					for (Target target : partTargets) {
						target.move(-15, -7, false);
						targets.add(target);
					}
					break;
				}
			}
			//Fin du placement des bouts de plateau


			//Ajout des Robots
			for (int r=0; r<4; r++) {
				//On prend un emplacement au hasard.
				Location place;
				boolean isBadPlace;
				//Tant que l'emplacement du robot se supperpose à quelque chose, c'est pas bon.
				do {
					place = new Location(random.nextInt(16), random.nextInt(16));
					isBadPlace = false;
					//On test si une cible est à cet endroit
					for (Target target : targets) {
						if (target.getLocation().equals(place)) {
							isBadPlace = true;
							break;
						}
					}
					//On test si un robot est à cet endroit
					for (int robot=0; robot<r; robot++) {
						if (robotLocations[robot].equals(place)) {
							isBadPlace = true;
							break;
						}
					}
					//On test si c'est le centre
					int x = place.getX();
					int y = place.getX();
					if((x==8 || x==9) && (y==8 || y==9)) isBadPlace = true;

				} while (isBadPlace);

				robotLocations[r] = place;
			}
		}
	}

	//Accesseurs
	public Boolean[][] getWallsX() {
		return wallsX;
	}
	public Boolean[][] getWallsY() {
		return wallsY;
	}
	public Location[] getRobotLocations() {
		return robotLocations;
	}
	public ArrayList<Target> getTargets() {
		return targets;
	}
}
	