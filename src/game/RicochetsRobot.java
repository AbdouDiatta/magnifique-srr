package game;

import game.board.*;
import players.*;
import view.*;

import java.util.Arrays;
import java.util.ArrayList;


public class RicochetsRobot extends AbstractModelListener {
    //Plateau
    protected Boolean[][] wallsX;//Murs entre la gauche et la droite
    protected Boolean[][] wallsY;//Murs entre la haut et le bas
    //Robots
    protected Robot[] robots;//Liste des 4 robots (les robots contiennent leurs noms et ce qu'il faut pour les afficher)
    protected Location[] robotLocations;//Contient les coordonnées des robots.
    //Cibles
    protected ArrayList<Target> targetsToFind;//Les cibles à trouver
    protected ArrayList<Target> targetsFinded;//Les cibles déjà trouvées
    protected Target currentTarget;//La cible utilisé pour ce tour. Si la partie est finie, ça reste sur la dernière cible utilisé.
    //Joueurs
    protected ArrayList<Player> players;//Liste des joueurs. (avec le nombre de points, l'affichage, et les propositions)
    //Section affichée
    protected String page;//la page où on est.
    //Taches en cours
    protected ArrayList<GameTask> currentsTasks;


    // ============ INITIALISATION ============

    //Constructeur sans paramètres
    public RicochetsRobot() {

        //Joueurs
        players = new ArrayList<Player>();
        players.add(new Human(this, "Boh"));
        //Par défault il n'y a pas de joueurs. Ils sont ajouté en fonction de la partie.


        //Génération du plateau
        BoardGenerator boardGenerator = new BoardGenerator(0);

        //Murs
        wallsX = boardGenerator.getWallsX();
        wallsY = boardGenerator.getWallsY();

        //Robots
        robots = new Robot[4];
        robots[0] = new Robot(0);
        robots[1] = new Robot(1);
        robots[2] = new Robot(2);
        robots[3] = new Robot(3);
        robotLocations = boardGenerator.getRobotLocations();

        //Cibles
        targetsToFind = boardGenerator.getTargets();
        targetsFinded = new ArrayList<Target>();
        currentTarget = targetsToFind.get(0);


        //Pas de tache en cours de base.
        currentsTasks = new ArrayList<GameTask>();
        //Le jeu s'ouvre par défault avec le choix du nombre de mouvements
        page = "Menu";
    }


    // ============ AFFICHAGE ============

    //Affichage du plateau de jeu.
    @Override
    public String toString() {

        String board = "";


        for (int y = 15; y >= 0; y--) {//Pour chaque Ligne

            //Espacement pour la ligne
            board += "    ";
            for (int x = 0; x <= 15; x++) {//Murs au dessus de chaque case de la ligne
                if (wallsY[x][y + 1] == true) board += " ==";
                else board += " --";
            }
            //Ajout du numéro de la ligne (adapté en fonction de si c'est 1 ou 2 caractères)
            if (y < 10) board += "\n " + y + "  ";
            else board += "\n" + y + "  ";

            for (int x = 0; x <= 15; x++) {//Pour chaque case de la ligne
                //Mur à gauche de la case
                if (wallsX[x][y] == true) board += "|";
                else board += " ";


                //Est-ce qu'il y a un truc sur la case ou pas ?
                boolean caseEmpty = true;
                //Est-ce qu'il y a la cible actuelle ?
                if (currentTarget.getLocation().equals(x, y)) {
                    board += "C" + currentTarget.getRobot();//Cibles des Robots 0 à 3, et 4 est le vortex cosmic
                    caseEmpty = false;
                }

                //Est-ce qu'il y a un robot ?
                if (caseEmpty) {
                    for (int robot = 0; robot < this.robotLocations.length; robot++) {
                        if (this.robotLocations[robot].equals(x, y)) {
                            board += "r" + robot;//case avec un robot, contient son numéro
                            caseEmpty = false;
                        }
                    }
                }

                //Si c'est vide on laisse l'espace vide
                if (caseEmpty) board += "  ";//case vide
            }

            //A la fin de la ligne, murs à droite de la dernière case
            if (wallsX[16][y] == true) board += "|";
            board += "\n";

        }

        //Espacement pour la ligne
        board += "    ";
        //Murs en dessous des cases de la dernière ligne
        for (int x = 0; x <= 15; x++) {
            if (wallsY[x][0] == true) board += " ==";
            else board += " --";
        }
        board += "\n";

        //Espacement pour la ligne
        board += "    ";
        //numéros des colonnes
        for (int x = 0; x <= 15; x++) {
            //On s'adapte si le numéro fait 2 caractères au lieu d'1.
            if (x < 10) board += " " + x + " ";
            else board += " " + x;
        }
        board += "\n";

        //Retourne le plateau à la fin
        return board;
    }

    // Affichage WallsX (pour débugage)
    public void printWallsX() {
        System.out.println("\nWallsX : 17 x 16");
        for (int j = 15; j >= 0; j--) {
            for (int i = 0; i < 17; i++) {
                if (wallsX[i][j] == true)
                    System.out.print("1 ");
                else
                    System.out.print("0 ");
            }
            System.out.println(" " + j);
        }
    }

    // Affichage MursY (pour débugage)
    public void printWallsY() {
        System.out.println("\nwallsY : 16 x 17");
        for (int j = 16; j >= 0; j--) {
            for (int i = 0; i < 16; i++) {
                if (wallsY[i][j] == true)
                    System.out.print("1 ");
                else
                    System.out.print("0 ");
            }
            System.out.println(" " + j);
        }
    }


    // ============ ACCESSEURS ============

    //La page de la section de la partie où on est.
    public String getPage() {
        return this.page;
    }

    //Change la page
    public void setPage(String page) {
        this.page = page;
        fireChangePage();
    }

    //Murs verticaux (bloquant les déplacements selon X)
    public Boolean[][] getWallsX() {
        return this.wallsX;
    }

    //Murs horizontaux (bloquant les déplacements selon Y)
    public Boolean[][] getWallsY() {
        return this.wallsY;
    }

    //Un joueur
    public Player getPlayer(int n) {
        return this.players.get(n);
    }

    //Tous les joueurs
    public ArrayList<Player> getPlayers() {
        return this.players;
    }

    //Ajouter un joueur
    public void addPlayer(Player player) {
        this.players.add(player);
        firePlayersChange();
    }

    //Coordonnées d'un robot.
    public Location getRobotLocations(int x) {
        return this.robotLocations[x];
    }

    //Coordonnées de tous les robots.
    public Location[] getRobotLocations() {
        return this.robotLocations;
    }

    //Etat du plateau pendant le tour
    public State getState() {
        return new State(this.robotLocations);
    }

    //Les robots (sauf leurs emplacements)
    public Robot[] getRobots() {
        return this.robots;
    }

    //Changer les infos des robots
    public void setRobots(Robot[] robots) {
        this.robots = robots;
    }

    //Méthode de copie
    public Location[] copieRobotsLocations() {
        Location[] tmp = new Location[this.robotLocations.length];
        for (int i = 0; i < this.robotLocations.length; i++) {
            tmp[i] = this.robotLocations[i];
        }
        return tmp;
    }

    //La cible actuelle
    public Target getCurrentTarget() {
        return this.currentTarget;
    }

    //Toutes les cibles
    public ArrayList<Target> getTargets() {
        return this.targetsToFind;
    }

    //Donne la liste des mouvements possibles du jeu actuel
    public ArrayList<Move> getValidMoves() {
        ArrayList<Move> retour = new ArrayList<Move>();
        for (int robot = 0; robot < 4; robot++) {//Pour chaque robot
            for (int direction = 0; direction < 4; direction++) {//Pour chaque direction

                //Le Robot par de là.
                int fromX = robotLocations[robot].getX();
                int fromY = robotLocations[robot].getY();

                //Et arrive là.
                Location goTo = moveFromHere(fromX, fromY, direction, new State(this.robotLocations));

                //Ajout du mouvement si ce n'est pas l'endroit où ce trouve déjà le robot.
                if (!robotLocations[robot].equals(goTo)) retour.add(new Move(robot, goTo));
            }
        }
        return retour;
    }

    public ArrayList<Move> getRobotValidMoves(int robotNumber) {
        ArrayList<Move> retour = new ArrayList<Move>();
        for (int direction = 0; direction < 4; direction++) {//Pour chaque direction
            //Le Robot par de là.
            int fromX = robotLocations[robotNumber].getX();
            int fromY = robotLocations[robotNumber].getY();

            //Et arrive là.
            Location goTo = moveFromHere(fromX, fromY, direction, new State(this.robotLocations));

            //Ajout du mouvement si ce n'est pas l'endroit où ce trouve déjà le robot.
            if (!robotLocations[robotNumber].equals(goTo)) retour.add(new Move(robotNumber, goTo));
        }
        return retour;
    }


    //Donne la liste des mouvements possibles pour un état précis donné
    public ArrayList<Move> getValidMoves(State state) {
        ArrayList<Move> retour = new ArrayList<Move>();
        for (int robot = 0; robot < 4; robot++) {//Pour chaque robot
            for (int direction = 0; direction < 4; direction++) {//Pour chaque direction

                //Le Robot par de là.
                int fromX = state.getRobotLocations()[robot].getX();
                int fromY = state.getRobotLocations()[robot].getY();

                //Et arrive là.
                Location goTo = moveFromHere(fromX, fromY, direction, state);

                //Ajout du mouvement si ce n'est pas l'endroit où ce trouve déjà le robot.
                if (!robotLocations[robot].equals(goTo)) retour.add(new Move(robot, goTo));
            }
        }
        return retour;
    }

    //Dit si le mouvement est valide
    public boolean isValid(Move tryMove) {
        //Le Robot par de là.
        int fromX = robotLocations[tryMove.getRobotSelected()].getX();
        int fromY = robotLocations[tryMove.getRobotSelected()].getY();

        //Si le robot est déjà à cet endroit, ce n'est pas un mouvement pour y aller.
        if (tryMove.getRobotDestination().equals(fromX, fromY)) return false;
        else {
            //On test pour chaque direction
            for (int direction = 0; direction < 4; direction++) {

                //On regarde si il arrive au bon endroit.
                if (moveFromHere(fromX, fromY, direction, new State(robotLocations)).equals(tryMove.getRobotDestination()))
                    return true;
            }
            //Si on a rien trouvé on retourne false.
            return false;
        }
    }

    //Dit si la partie du jeu est terminée ou non
    public boolean isGameOver() {
        //Test si il n'y a plus de cible.
        if (targetsToFind.isEmpty()) return true;
        else {
            //On regarde quel est le nombre maximal de points atteint par un joueur.
            int max = 0;
            for (Player player : players) {
                int p = player.getPoints();
                if (p > max) max = p;
            }
            //Test si ce nombre de points correspond à la fin pour ce nombre de joueur
            int nbrPlayer = players.size();
            return (nbrPlayer <= 2 && max >= 8) || (nbrPlayer == 3 && max >= 6) || (nbrPlayer == 4 && max >= 5);
        }
    }

    //Dit si le tour est terminée ou non (cela dure jusqu'à l'atteinte d'une cible)
    public boolean isTurnOver() {
        //Si toute la partie est terminé, le tour l'est aussi.
        if (isGameOver()) return true;

        else {
            if (currentTarget.getRobot() != 4) {
                //Si ce n'est pas la cible Vortex cosmic (qui correspond à tous les robots)
                //Test si le robot désigné par la cible actuelle est sur cette cible
                if (currentTarget.getLocation().equals(robotLocations[currentTarget.getRobot()])) return true;
                else return false;
            } else {
                //Si c'est la cible Vortex cosmic
                //On test si un robot est dessus
                for (int robot = 0; robot < 4; robot++) {
                    if (currentTarget.getLocation().equals(robotLocations[robot])) return true;
                }
                //Si aucun n'est dessus, la cible n'est pas atteinte et le tour n'est pas fini.
                return false;
            }
        }
    }

    //Donne le gagnant de la partie
    public Player getWinner() {
        //C'est le joueur qui a le plus de points
        int max = 0;
        Player bestPlayer = null;
        //On test chaque joueur
        for (Player player : players) {
            int p = player.getPoints();
            if (p > max) {
                max = p;
                bestPlayer = player;
            }
        }
        return bestPlayer;//Le joueur qui a le maximum de points
    }


    // ============ EXECUTION ============

    //Execute du session de jeu
    public void playGame() {
        //Tant que le jeu n'est pas terminé
        while (!isGameOver() && getPage() != "Winner") {
            //Chaque tour
            Player winTurn = playTurn();
            if (winTurn != null) winTurn.increasePoint();
            goToNextRound();
        }
        //Fin de partie
        setPage("Winner");
    }


    //Execute un tour de jeu
    public Player playTurn() {

        //Chaque joueur doit proposer un nombre de mouvement
        setPage("ChooseNbrMoves");
        //On créé les recherches de mouvements
        for (Player player : players) {
            ChooseNbrMovesThread newTread = new ChooseNbrMovesThread(player);
            currentsTasks.add(newTread);
            newTread.start();
        }

        //On attend que chacun ai fait son choix
        boolean waitMore = true;
        while (waitMore) {
            waitMore = false;
            //On regarde pour les taches qui correspondent
            for (int i = 0; i < currentsTasks.size(); i++) {
                GameTask thisTask = currentsTasks.get(i);
                if (thisTask.isTask("ChooseNbrMoves")) {
                    //On attend la fin de la tache
                    try {
                        thisTask.join();//Attend la mort du thread.
                    } catch (InterruptedException e) {
                        //Ne fait rien en cas d'arrêt
                    }
                    //On retire la tache de la liste
                    if (!thisTask.isAlive()) currentsTasks.remove(thisTask);
                }
            }
        }

        //On trie les joueurs en mettant ceux qui proposent le moins de mouvement en premier
        ArrayList<Player> sortedPlayers = new ArrayList<Player>();
        //On met dans la liste à trier tous les joueurs qui ont une proposition limité
        for (Player player : players) {
            if (player.getNbrProposed() > 0) {
                //On cherche où mettre chaque ce joueur dans la liste trié
                int test = player.getNbrProposed();
                Boolean again = true;
                int i = 0;//L'emplacement ou le mettre
                while (again) {
                    //On met le joueur plus loin dans la liste si sa proposition semble moins bonne
                    if (i < sortedPlayers.size() && test >= sortedPlayers.get(i).getNbrProposed()) i++;
                    else again = false;
                }
                //On met le joueur à cet endroit
                sortedPlayers.add(i, player);
            }
        }
        //On ajoute à la fin les joueurs qui ont des propositions sans limites.
        for (Player player : players) {
            if (player.getNbrProposed() < 0) sortedPlayers.add(player);
        }

        //On sauvegarde l'état initial de la partie avant les tests.
        Location initialLocation[] = copieRobotsLocations();

        //On passe à la page des test (ou on quitte si la partie est finie)
        if (getPage() == "ChooseNbrMoves") setPage("TestSolution");
        if (getPage() != "TestSolution" || getPage() != "TestSolutionUI") return null;

        //On test les propositions, en commençant par celles qui ont le moins de mouvements.
        for (Player player : sortedPlayers) {
            if (testPlayerSolution(player, player.getNbrProposed())) {
                //Solution valide, fin du tour
                fireWinTurn(player);
                return player;
            } else {
                //Solution non valide
                fireFalseSolution(player);
                //On remet en place pour le test suivant
                robotLocations = initialLocation;
            }
        }
        //Arriver là signifie que personne n'a trouvé de solution.
        return null;
    }

    //Un joueur a proposé un nombre de mouvement
    public void chosenNbrMove(Player player) {
        firePlayersProposal(player);
        if (getPage() == "ChooseNbrMoves") {
            //Ferme le tread qui y correspond.
            for (int i = 0; i < currentsTasks.size(); i++) {
                GameTask thisTask = currentsTasks.get(i);
                if (thisTask.isTask(player, "ChooseNbrMoves")) {
                    thisTask.interrupt();
                    //On retire la tache de la liste si elle s'est arrêté
                    if (!thisTask.isAlive()) currentsTasks.remove(thisTask);
                }
            }
        }
    }

    //Test si une suite de mouvement est valide
    public boolean testPlayerSolution(Player player, int nbrOfMovesMax) {
        //Affichage du test
        fireTestSolution(player, nbrOfMovesMax);

        int nbrOfMoves = 0;//Le nombre de mouvement utilisé

        //Tant que le tour n'est pas fini et qu'il reste des mouvements. (-1 pour essais infinis)
        while (!isTurnOver() && (nbrOfMoves < nbrOfMovesMax || nbrOfMovesMax < 0)) {

            //Demande de coup
            ChooseNextMoveThread thread = new ChooseNextMoveThread(player);
            thread.start();

            //On attend que le thread joue
            try {
                thread.join();//Attend la mort du thread.
            } catch (InterruptedException e) {
                //Ne fait rien en cas d'arrêt
            }

            nbrOfMoves++;
        }

        System.out.println("Fin de l'essai.");

        return isTurnOver();
    }

    //Execution et gestion de l'affichage d'un mouvement
    public void playMove(Player player, Move move) {
        if (getPage() == "TestSolutionUI") {
            //affiche ce qui a été choisi
            System.out.println(player.getName() + " a choisi le mouvement : " + move + ".");

            //Exécution du coup
            execute(move);

            //Affichage du plateau après
            fireRobotChange();
            return;
        }
        if (getPage() == "TestSolution") {
            //affiche ce qui a été choisi
            System.out.println(player.getName() + " a choisi le mouvement : " + move + ".");

            //Exécution du coup
            execute(move);

            //Affichage du plateau après
            System.out.println("\nNouvelle situation :");
            fireRobotChange();
        }
        //Ferme le tread qui y correspond.
        for (int i = 0; i < currentsTasks.size(); i++) {
            GameTask thisTask = currentsTasks.get(i);
            if (thisTask.isTask(player, "ChooseNextMove")) {
                thisTask.interrupt();
                //On retire la tache de la liste si elle s'est arrêté
                if (!thisTask.isAlive()) currentsTasks.remove(thisTask);
            }
        }
    }

    //Passe au tour suivant
    public void goToNextRound() {
        //Passe la cible actuelle en mode traité.
        if (!targetsToFind.isEmpty()) {
            targetsFinded.add(targetsToFind.get(0));
            targetsToFind.remove(0);
        }
        //Passe la cible actuelle sur la suivante
        if (!targetsToFind.isEmpty()) currentTarget = targetsToFind.get(0);

        //Reset les recherches du tour de chaque joueur
        for (Player player : players) {
            player.toNextTurn();
        }

        //Mise à jour de ce qui se trouve sur le plateau
        fireBoardChange();
    }

    //Execution d'un mouvement
    public void execute(Move thisMove) {
        //Test eventuel de si le Mouvement est autorisé.

        //Changement des Coordonnées du robot.
        if (isValid(thisMove))
            robotLocations[thisMove.getRobotSelected()] = thisMove.getRobotDestination();
    }

    //Execution d'une liste de mouvement
    public void execute(ArrayList<Move> moves) {
        //Pour chaque mouvement, execution du mouvement
        for (Move move : moves) {
            execute(move);
        }
    }

    //UTILISE UNIQUEMENT POUR LE DEBUGAGE
    public void applyAndShowState(State state) {
        robotLocations = state.getRobotLocations();
        fireRobotChange();
    }

    private Location moveFromHere(int x, int y, int direction, State state) {
        boolean more = true;//Utilisé pour 1 boucle While de direction

        //On s'adapte en fonction de la direction
        switch (direction) {

            case 0://Va vers le haut (+y)
                while (more) {
                    more = y + 1 < wallsY.length && !wallsY[x][y + 1];
                    for (int robotDevant = 0; robotDevant < 4; robotDevant++) {//Vérifier si il y a un robot devant
                        if (state.getRobotLocations()[robotDevant].equals(x, y + 1)) more = false;
                    }
                    if (more) y += 1;
                }
                break;

            case 1://Va vers la droite (+x)
                while (more) {
                    more = x + 1 < wallsX.length && !wallsX[x + 1][y];
                    for (int robotDevant = 0; robotDevant < 4; robotDevant++) {//Vérifier si il y a un robot devant
                        if (state.getRobotLocations()[robotDevant].equals(x + 1, y)) more = false;
                    }
                    if (more) x += 1;
                }
                break;

            case 2://Va vers le bas (-y)
                while (more) {
                    more = y > 0 && !wallsY[x][y];
                    for (int robotDevant = 0; robotDevant < 4; robotDevant++) {//Vérifier si il y a un robot devant
                        if (state.getRobotLocations()[robotDevant].equals(x, y - 1)) more = false;
                    }
                    if (more) y -= 1;
                }
                break;

            default://Va vers la gauche (-x)
                while (more) {
                    more = x > 0 && !wallsX[x][y];
                    for (int robotDevant = 0; robotDevant < 4; robotDevant++) {//Vérifier si il y a un robot devant
                        if (state.getRobotLocations()[robotDevant].equals(x - 1, y)) more = false;
                    }
                    if (more) x -= 1;
                }
                break;
        }

        return new Location(x, y);
    }

}

//La recherche du nombre de mouvement
class ChooseNbrMovesThread extends GameTask {

    public ChooseNbrMovesThread(Player player) {
        super(player, "ChooseNbrMoves");
    }

    public void run() {
        player.chooseNbrMoves();
    }

}

//La recherche du mouvement suivant.
class ChooseNextMoveThread extends GameTask {

    public ChooseNextMoveThread(Player player) {
        super(player, "ChooseNextMove");
    }

    public void run() {
        player.chooseNextMove();
    }

}