package players;

import game.*;

import java.util.*;


public class BotAetoile extends Player {
	private ArrayList<Node> openNodes;//Etats encore à exploiter (et leurs couts)
	private ArrayList<Node> closedNodes;//Etats déjà exploités (et leurs couts), on en connais les possibilitées

	private ArrayList<Move> proposedMoves;//Proposition de solution
	private int usedMoves;//Quantité de mouvements déjà donnés


	//Constructeurs avec nom
	public BotAetoile(RicochetsRobot game, String botName) {
		super(game, botName);
		this.openNodes = new ArrayList<>();
		this.closedNodes = new ArrayList<>();
		this.proposedMoves = new ArrayList<>();
	}

	//Constructeurs sans nom
	public BotAetoile(RicochetsRobot game) {
		this(game, "No Name");
	}

	//Obtenir le nom
	@Override
	public String getName() {
		if(name == "No Name") return "BOTa"+this.hashCode();
		else return name;
	}

	//Obtenir le type
	@Override
	public String getType() {
		return "Bot A*";
	}

	//Obtenir le nombre de mouvement (avec réflexion)
	@Override
	public void chooseNbrMoves() {
		//Execute l'algorithme A*, si besoin (sinon nombre déjà choisi)
		algorithmeAstar();
		//Quand la réponse est trouvé, elle est remise automatiquement.
	}

	//Obtenir le coup suivant qui sera joué par le bot. (avec réflexion)
	@Override
	public void chooseNextMove() {
		//Execute l'algorithme A*, si besoin, pour avoir une réponse.
		algorithmeAstar();

		//On donne le mouvement suivant et ajoute après 1 au nombre de mouvement donné
		giveNextMove(proposedMoves.get(usedMoves++));
	}

	//Obtenir la liste de coup qui sera joué par le bot. (avec réflexion)
	public ArrayList<Move> chooseAllMoves() {

		//Les mouvements disponibles
		ArrayList<Move> moves = super.game.getValidMoves();

		//Si il y a des coups à jouer
		if(moves.size()>0) {

			algorithmeAstar();

			return this.proposedMoves;

		} else {
			//Si il n'y a aucun coup à jouer
			System.out.println("\n"+getName()+" dit qu'il ne peux rien jouer.");
		}

		return null;//Arriver là signifierait qu'on ne peux rien jouer.
	}

	//Execution de l'algorithme A*
	public boolean algorithmeAstar() {

		//Test si l'algorithme A* n'a pas déjà été executé
		if(proposedMoves.isEmpty()) {

			System.out.println("\n"+getName()+" réfléchit à ses mouvements.");

			//Initialisation de la recherche
			Target target = super.game.getCurrentTarget();//La cible cherché
			Location targetLocation = target.getLocation();//Ses coordonnées
			int robot = target.getRobot();//robot à y ammener

			//Initialisation du premier Noeud.
			State initialState = super.game.getState();
			Node firtNode = new Node(initialState, 0, false, null, target);
			openNodes.add(firtNode);

			while (!openNodes.isEmpty()) {//Tant qu'il reste des chemins non testés
				Node thisNode = openNodes.get(0);

				//On test si cet état est l'état final
				//Est-ce qu'il y a plus d'1 mouvement
				if (thisNode.getCost()>1) {
					if(robot < 4) {//Pour une cible de couleur est-ce que le robot recherché est au bon endroit
						Location thisLocation = thisNode.getLocation(robot);
						if(thisLocation.equals(targetLocation)) {
							saveProposal(searchPath(thisNode));
							return true;//Fin de A*
						}

					} else {//Pour le vortex est-ce que un des robot est au bon endroit
						for (int aRobot=0; aRobot<4; aRobot++) {
							if(thisNode.getLocation(aRobot).equals(targetLocation)) {
								saveProposal(searchPath(thisNode));
								return true;//Fin de A*
							}
						}
					}
				}

				//Obtenir les mouvement à partir de cette Node (et donc les états voisins accessibles)
				ArrayList<Move> moves = super.game.getValidMoves(thisNode.getState());
				thisNode.setPossibility(moves);
				for(Move move : moves) {
					//Pour chaque mouvement disponible pour cet état

					//On obtient l'état donné par le mouvement
					State newState = thisNode.getState().execute(move);

					//Calcul du cout du nouvel état
					int newCost = thisNode.getCost()+1;

					//On regarde si on doit ajouter cet état.
					boolean haveToAdd = true;
					//On regarde si on a déjà obtenu cet état parmis les étas étudiés.
					for(Node oldNode : closedNodes) {
						if(oldNode.isSameState(newState)) haveToAdd = false;
					}
					//On regarde si on as déjà obtenu un meilleur résultat parmis les états ouvert.
					for(int i=0 ; i<openNodes.size(); i++) {
						Node oldNode = openNodes.get(i);
						if(oldNode.isSameState(newState)) {
							//Si on trouve un état déjà trouvé, on regarde si le moyen de l'obtenir est meilleur.
							if(!(newCost < oldNode.getCost() || oldNode.getCost() <= 2)) haveToAdd = false;
							else if(newCost < oldNode.getCost() && oldNode.getCost() > 2) {
								//Si l'ancien node était moins bon et n'était pas l'une des nodes en trop peu de mouvement
								//alors je retire cette ancienne node.
								openNodes.remove(i);
								i--;//On retire 1 à i pour décaller par rapport à la suppression.
							}
						}
					}

					if(haveToAdd) {
						//On note si on utilise un coup n'implicant pas le robot privilégié
						boolean useSpecialMove = false;
						if(robot < 4) useSpecialMove = move.getRobotSelected()!=robot;
						//On ajoute cette possibilité en node à traiter
						openNodes.add(new Node(newState, newCost, useSpecialMove, thisNode, target));
					}
				}

				//On passe la node en mode traité
				closedNodes.add(thisNode);
				openNodes.remove(0);

				//On trie les Nodes ouvertes en mettant les plus importantes en premier.
				sortNodes();
			}
			//Si on est là, c'est qu'il n'y a pas de solution.
			//saveProposal(new ArrayList<>());
		}
		//Si A* a déjà été executé alors ça ne fait rien.
		return false;
	}

	//Comparer quelle node est le plus proche de l'objectif
	private int compareNode(Node n1, Node n2) {
		if(n1.getHeuristic() > n2.getHeuristic()) return 1;
		else if(n1.getHeuristic() < n2.getHeuristic()) return -1;
		else return 0;
	}

	//Trie les Nodes ouvertes en mettant les prioritaire en plus petit numéro
	private void sortNodes() {
		//Initialisation de la liste triée
		ArrayList<Node> sortedNodes = new ArrayList<>();

		for(Node node : openNodes) {
			//On cherche l'emplacement où mettre la node.
			Boolean again = true;
			int i=0;
			while(again) {
				//On met la node plus loin si elle a un heuristic plus gros
				if(i<sortedNodes.size() && compareNode(node, sortedNodes.get(i))>=0 ) i++;
				else again = false;
			}
			//On met la node à cet endroit
			sortedNodes.add(i, node);
		}

		//On enregistre la version triée.
		openNodes = sortedNodes;
	}

	//Retrace le chemin de la node finale à la node de départ, et donne la liste des mouvement pour faire le chemin.
	private ArrayList<Move> searchPath(Node finalNode) {

		//Initialisation des la liste des mouvements.
		ArrayList<Move> pathMoves = new ArrayList<Move>();

		//Première node du chemin pour l'instant trouvé.
		Node headNode = finalNode;
		Node previous = null;

		while (headNode.getCost() > 0 && headNode.getPrevious() != null) {
			previous = headNode.getPrevious();
			//On ajoute le mouvement de la meilleur Node au début de la suite des mouvement menant à la Node final.
			pathMoves.add(0, previous.getMoveToMake(headNode.getState()));
			//On transforme le meilleur Node voisine en la nouvelle tête du chemin.
			headNode = previous;
		}
		/*
		//ENCIENNE VERSION

		//Node suivante en recherche
		Node bestNextNode = headNode;

		while (headNode.getCost() > 0) {
			ArrayList<Move> moves = headNode.getPosibilities();
			System.out.println("On en est au cost = "+headNode.getCost()+". Il y a "+moves.size()+" moves.");
			System.out.println(moves);
			//Pour chaque possibilité de mouvement à partir de la node de tête
			for (Move move : moves) {
				//On obtient l'état de la node voisine correspondant à ce mouvement.
				State testState = headNode.getState().execute(move);

				System.out.println("Test un move.");

				//On regarde si on a une node voisine qui correspond avec un cout inférieur.
				for (Node node : closedNodes) {
					System.out.println("Test une node.");
					if(node.isSameState(testState)) {

						System.out.println("Etat qui correspond.");

						if(node.getCost() < bestNextNode.getCost()) {
							bestNextNode = node;
							System.out.println("A trouvé une node suivante.");
						}
					}
					//On prend la meilleur node voisine.
				}
			}

			//On ajoute le mouvement de la meilleur Node au début de la suite des mouvement menant à la Node final.
			pathMoves.add(0, bestNextNode.getMoveToMake(headNode.getState()));
			//On transforme le meilleur Node voisine en la nouvelle tête du chemin.
			headNode = bestNextNode;
		}*/
		return pathMoves;
	}

	//Sauvegarde une proposition
	private void saveProposal(ArrayList<Move> proposedMoves) {
		this.proposedMoves = proposedMoves;
		if (game.getPage()=="ChooseNbrMoves"){
			//Si on est dans la section où on propose les nombre, on essaye de proposer le nombre.
			if (giveNbrMoves(proposedMoves.size())) {//Essaye de sauvegarder cette proposition
				//Echec.
				System.out.println(getName()+" a tenté de faire un changement de son nombre de mouvements non autorisé.");
			}
		}
		
	}

	//Passer au tour suivant, donc reset ce qui doit l'être
	@Override
	public void toNextTurn() {
		super.toNextTurn();

		this.openNodes.clear();
		this.closedNodes.clear();

		this.proposedMoves.clear();
		this.usedMoves = 0;
	}
}
