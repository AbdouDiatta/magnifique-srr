package view;

import game.*;
import players.*;

import java.awt.*;
import java.awt.event.*;
import java.text.NumberFormat;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.text.NumberFormatter;


// Gére l'affichage de l'interface graphique (une fenêtre)

public class WindowView extends JFrame implements ModelView { //implemente aussi MouseListener pour la détection de clic
    //JFrame permets d'utiliser les fonctions de le librairie graphique

    //c’est le panel principal
    private JPanel contentPanel;
    private JPanel gamePanel;
    private JPanel controlPanel;
    private JPanel playersPanel;
    private Box box;

    private GridLayout windowGrid;//Gére l’affichage en grille // à refaire pour plus tard
    private GridLayout playerGrid;//Gére l’affichage en grille // à refaire pour plus tard
    private RicochetsRobot game;//initialisation de la classe RicochetsRobot
    private CaseView[][] plateauViews;
    private JComponent[][] playerViews;
    private final int WIDTH = 16;
    private final int HEIGHT = 16;

    public WindowView(RicochetsRobot game) {

        //Lien avec le jeu
        this.game = game;
        this.game.addModelListener(this);

        //Initialisation fenêtre
        setTitle("Magifique solveur de RicochetsRobot");//Le Nom de la fenêtre
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// dit à l'application de se fermer lors du clique sur le croix
        this.setSize(1000, 600);
        this.setPreferredSize(new Dimension(1000, 600));
        this.setLocationRelativeTo(null);//On centre la fenêtre sur l'écran
        this.setResizable(false); //On interdit la redimensionnement de la fenêtre
        this.setVisible(true);
        //Contenu de la fenêtre
        // contentPanel, la section de l'ensemble de la fenêtre
        contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setSize(100, 500);
        this.setContentPane(contentPanel);
        newBoard();
        revalidate();
    }

    //Changement de page
    @Override
    public synchronized void updatePage() {
        //Change l'affichage en fonction de la page
        switch (game.getPage()) {

            case "Menu":
                // show menu
                Menu menu = new Menu(game);
                this.setContentPane(menu.getPanelMain());
                pack();
                break;

            case "QuickConfig":
                Board board = new Board(game , this);
                //Page de configuration d'une partie rapide, en un seul tour. (Exclusif au terminal)
                this.setContentPane(board.getMainPanel());
                pack();
                /*
                if (game.getPlayers().isEmpty()) {
                    //Affichage différent si il faut créer 1 joueur pour le test
                }

                //Exécution d'un tour.
                game.playTurn();

                game.setPage("Menu");*/
                break;


            case "ChooseNbrMoves":
                this.setContentPane(contentPanel);
                pack();/*
                System.out.println("\n\nRecherche de solutions");
                System.out.println("======================");
                newBoard();*/
                break;


            case "TestSolution":
                this.setContentPane(contentPanel);
                pack();
                break;


            case "PlayersBeforeGame":
                PlayersView playersView = new PlayersView(game , true);
                this.setContentPane(playersView.getPanelPlayers());
                pack();
                break;


            case "Players":
                //Le menu de la page PlayersView avec un bouton retour au menu.
                PlayersView playersView_ = new PlayersView(game , false);
                this.setContentPane(playersView_.getPanelPlayers());
                pack();
                break;

            case "Rules":
                Rules rules= new Rules(game);
                this.setContentPane(rules.getPanelRules());
                pack();
                break;


            case "Winner":
                this.setContentPane(contentPanel);
                pack();
                break;
        }
    }

    //Recharge complètement le plateau (murs compris)
    @Override
    public void newBoard() {


    }

    public void updatePlayerViews() {

    }

    //Mise à jour de ce qu'il y a sur le plateau
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
    }

    //Mise à jour de la proposition de nombre de mouvement d'un joueur
    @Override
    public void updatePlayersProposal(Player player) {
        //Ne fait rien pour l'instant
        //updatePlayerViews();
    }

    //Affiche que tel joueur tente de résoudre en tant de mouvement
    @Override
    public void thisPlayerTest(Player player, int nbrMoves) {
        //Ne fait rien pour l'instant
    }

    //Affiche que tel joueur a échoué à faire fonctionner sa solution
    @Override
    public void playerLoseTurn(Player player) {
        //Ne fait rien pour l'instant
    }

    //Affiche que tel joueur a trouvé le nombre minial de mouvement du tour
    @Override
    public void playerWinTurn(Player player) {
        //Ne fait rien
    }

    //Fin de la vue de l'interface graphique (fermeture)
    @Override
    public void closeView() {
        //dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));//Fermeture forçant l'arrêt de l'application
        dispose();//Fermeture ne forçant pas l'arrêt de l'application
    }

}
