package game.board;

import game.*;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Random;


public class BoardPart {
	//Plateau
	protected Boolean[][] wallX;//Murs entre la gauche et la droite
	protected Boolean[][] wallY;//Murs entre la haut et le bas
	//Sur le plateau
	protected ArrayList<Target> targets;//Les cibles du bout de plateau


	//Constructeur
	public BoardPart(int part) {

		// ======== Initialisation (sans remplir)
		//Bout de Plateau
		wallX = new Boolean[9][8];
		wallY = new Boolean[8][9];
		//Cibles
		targets = new ArrayList<Target>();


		//Rend sans murs les cases intérieures
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				wallX[i+1][j] = false;
				wallY[i][j] = false;
			}
		}

		//mets les bords à gauche et en haut
		for (int i = 0; i < 8; i++) {
			wallX[0][i] = true;//A gauche
			wallY[i][8] = true;//En haut
		}

		//mets le centre du plateau
		wallX[7][0] = true;
		wallY[7][1] = true;



		// ======== Remplissage du bout de plateau

		//Met les murs et les cibles.
		switch(part) {

			case 0:
			//Pour le bout de plateau défini n°1
			wallY[0][1] = true;
			wallX[4][7] = true;

			wallY[1][5] = true;
			wallX[2][4] = true;
			targets.add(new Target(3, new Location(1, 4)));

			wallY[2][2] = true;
			wallX[3][2] = true;
			targets.add(new Target(0, new Location(2, 2)));

			wallY[5][4] = true;
			wallX[5][3] = true;
			targets.add(new Target(1, new Location(5, 3)));

			wallY[6][6] = true;
			wallX[6][6] = true;
			targets.add(new Target(2, new Location(6, 6)));

			wallY[7][2] = true;
			wallX[8][2] = true;
			targets.add(new Target(4, new Location(7, 2)));
			break;


			case 1:
			//Pour le bout de plateau défini n°2
			wallY[0][2] = true;
			wallX[4][7] = true;

			wallY[1][6] = true;
			wallX[1][6] = true;
			targets.add(new Target(0, new Location(1, 6)));

			wallY[2][3] = true;
			wallX[3][3] = true;
			targets.add(new Target(2, new Location(2, 3)));

			wallY[6][6] = true;
			wallX[7][5] = true;
			targets.add(new Target(1, new Location(6, 5)));

			wallY[7][3] = true;
			wallX[7][2] = true;
			targets.add(new Target(3, new Location(7, 2)));
			break;


			case 2:
			//Pour le bout de plateau défini n°3
			wallY[0][2] = true;
			wallX[2][7] = true;

			wallY[1][6] = true;
			wallX[2][5] = true;
			targets.add(new Target(1, new Location(1, 5)));

			wallY[3][1] = true;
			wallX[3][1] = true;
			targets.add(new Target(2, new Location(3, 1)));

			wallY[4][7] = true;
			wallX[4][6] = true;
			targets.add(new Target(0, new Location(4, 6)));

			wallY[6][4] = true;
			wallX[7][4] = true;
			targets.add(new Target(3, new Location(6, 4)));
			break;
			

			case 3:
			//Pour le bout de plateau défini n°4
			wallY[0][3] = true;
			wallX[5][7] = true;

			wallY[1][1] = true;
			wallX[2][1] = true;
			targets.add(new Target(1, new Location(1, 1)));

			wallY[2][7] = true;
			wallX[2][6] = true;
			targets.add(new Target(3, new Location(2, 6)));

			wallY[4][3] = true;
			wallX[5][2] = true;
			targets.add(new Target(0, new Location(4, 2)));

			wallY[6][4] = true;
			wallX[6][4] = true;
			targets.add(new Target(2, new Location(6, 4)));
			break;
			

			default:
			//Pour inventer un nouveau bout de plateau
			
			Random random = new Random(System.nanoTime());

			//Les 2 murs contre les paroies
			wallY[0][random.nextInt(5)+2] = true;
			wallX[random.nextInt(5)+2][7] = true;

			//Les emplacements des murs qui contiennent les cibles
			for(int block=0;block<3;block++) {
				//Choisi l'emplacement
				int x = random.nextInt(6)+1;
				int y = random.nextInt(6)+1;
				//Met la cible à cette endroit
				targets.add(new Target(block, new Location(x, y)));
				//Met les murs autour
				wallY[x][y] = true;
				wallX[x][y] = true;
			}
			break;
		}
	}

	//Accesseurs
	public Boolean[][] getWallsX() {
		return wallX;
	}
	public Boolean[][] getWallsY() {
		return wallY;
	}
	public ArrayList<Target> getTargets() {
		return targets;
	}

}