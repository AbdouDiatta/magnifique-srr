package view;

import game.*;
import game.Robot;
import players.*;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.event.*;


// Gère l'affichage du plateau

public class Board extends BoardGeneric implements ModelView {
    private RicochetsRobot game;
    private JFrame parent;
    private JLabel timeRemaining;
    private JPanel boardPanel;
    private JPanel gamersPanel;
    private JPanel controlePanels;
    private JPanel mainPanel;
    private JButton retourAuMenuButton;
    private JButton finDesPropositionsButton;
    private JButton finirLaPartieButton;
    private JButton choisirLaCibleDuButton;
    private JButton proposerNombreDeMouvementButton;
    private JButton testerLaPropositionButton;
    private Player currentPlayer;

    private GridLayout windowGrid;
    private GridLayout playerGrid;
    private CaseView[][] plateauViews;
    private JComponent[][] playerViews;
    private final int WIDTH = 16;
    private final int HEIGHT = 16;
    private int currentPlayerNumber;
    private CaseView selectedRobot;
    private CaseView[] possibleMoves;


    //Constructeur
    public Board(RicochetsRobot game, JFrame parent) {

        //Prise des caleurs reçues
        this.game = game;
        this.parent = parent;
        //Initialisations des autres attributs
        newBoard();
        game.addModelListener(this);
        retourAuMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.setPage("Menu");
            }
        });
        proposerNombreDeMouvementButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s = (String) JOptionPane.showInputDialog(
                        parent,
                        "Proposer un nombre de mouvement",
                        game.getPlayer(currentPlayerNumber).getName() + " propose ",
                        JOptionPane.PLAIN_MESSAGE);

                //If a string was returned, say so.
                if ((s != null) && (s.length() > 0)) {
                    game.getPlayers().get(currentPlayerNumber).giveNbrMoves(Integer.valueOf(s));
                }
            }
        });
        testerLaPropositionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // aller dans la panel mouvement
                game.setPage("TestSolutionUI");
                PlayerMoveView playerMoveView = new PlayerMoveView(game, currentPlayer, parent);
                parent.setContentPane(playerMoveView.window);
                parent.pack();
            }
        });
//
        boardPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {

                if (boardPanel.getComponentAt(e.getPoint()) != boardPanel && boardPanel.getComponentAt(e.getPoint()) instanceof CaseView) {
                    CaseView caseView = (CaseView) boardPanel.getComponentAt(e.getPoint());
                    if (caseView.getRobot() != null) {
                        selectedRobot = caseView;
                        fireClickedRobots(caseView.getPosX(), caseView.getPosY(), caseView.getRobotNumber());
                    }
                    if (caseView.getSelection() != null && selectedRobot != null) {
                        fireClickedDestination(selectedRobot.getRobotNumber(), caseView.getPosX(), caseView.getPosY());
                        selectedRobot = null;
                    }
                    if (caseView.getSelection() == null && caseView.getRobot() == null) {
//                        selectedRobot = null;
                    }
                }

            }
        });
    }

    public CaseView[][] getPlateauViews() {
        return plateauViews;
    }

    public void setPlateauViews(CaseView[][] plateauViews) {
        this.plateauViews = plateauViews;
    }

    //Accesseur
    public JPanel getMainPanel() {
        return mainPanel;
    }

    //Changement de page
    @Override
    public void updatePage() {
        //Ne fait rien
    }

    //Recharge complètement le plateau (murs compris)
    @Override
    public void newBoard() {
        if (plateauViews == null) {
            proposerNombreDeMouvementButton.setEnabled(currentPlayer != null);
            testerLaPropositionButton.setEnabled(false);
            plateauViews = new CaseView[16][16];
        }
        if (windowGrid == null) {
            windowGrid = new GridLayout(16, 16, 1, 1);
            boardPanel.setLayout(windowGrid);
            boardPanel.setPreferredSize(new Dimension(500, 500));//On donne une taille à notre fenêtre
            boardPanel.setMaximumSize(new Dimension(500, 500));//On donne une taille à notre fenêtre
        }
        //Met à jour la vue boardPanel
        for (int y = HEIGHT - 1; y >= 0; y--) {
            for (int x = 0; x <= WIDTH - 1; x++) {
                CaseView c;
                if (plateauViews[x][y] == null) {
                    c = new CaseView(new ImageIcon("./images/Case-Paper.png").getImage(), boardPanel, x, y);
                    plateauViews[x][y] = c;
                    boardPanel.add(c);
                } else {
                    c = plateauViews[x][y];
                    c.cleanSelection();
                    c.clean();
                }
                // placer les murs
                if (game.getWallsX()[x][y]) {
                    if (x == WIDTH - 1) {
                        int a = 3;
                    }
                    c.addWallX();
                }
                if (game.getWallsY()[x][y]) {
                    c.addWallY();
                }
                // case plein au milieu
                if (x == (WIDTH - 1) / 2 || x == ((WIDTH - 1) / 2) + 1) {
                    if (y == ((HEIGHT - 1) / 2) || y == ((HEIGHT - 1) / 2) + 1) {
                        c.addWallPlein();
                    }
                }
                // placer les robots
                for (int i = 0; i < game.getRobots().length; i++) {
                    if (game.getRobotLocations()[i].getX() == x && game.getRobotLocations()[i].getY() == y) {
                        Robot robot = game.getRobots()[i];
                        c.addRobot(robot.getImage(), i);
                    }
                }

                Target currentTarget = game.getCurrentTarget();
                if (currentTarget.getLocation().getX() == x && currentTarget.getLocation().getY() == y) {
                    c.addTarget(currentTarget.getImage());
                }
                c.repaint();
            }
        }
        PlayersView playersView = new PlayersView(game, false);
        gamersPanel.add(playersView.getPlayersTable().getTableHeader(), BorderLayout.NORTH);
        gamersPanel.add(playersView.getPlayersTable(), BorderLayout.CENTER);
        gamersPanel.add(playersView.getPlayersTable());
        playersView.getPlayersTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                // do some actions here, for example
                // print first column value from selected row

                currentPlayerNumber = playersView.getPlayersTable().getSelectedRow();
                if (game.getPlayers().size() > currentPlayerNumber && currentPlayerNumber >= 0) {
                    currentPlayer = game.getPlayers().get(currentPlayerNumber);
                }
                if (currentPlayer.getNbrProposed() > 0) {
                    testerLaPropositionButton.setEnabled(true);
                } else {
                    testerLaPropositionButton.setEnabled(false);
                }
                proposerNombreDeMouvementButton.setEnabled(true);

            }
        });
        gamersPanel.repaint();
        boardPanel.revalidate();
        this.mainPanel.repaint();
    }

    public void cleanBoardSelection() {
        for (int y = HEIGHT - 1; y >= 0; y--) {
            for (int x = 0; x <= WIDTH - 1; x++) {
                if (plateauViews[x][y] != null) {
                    plateauViews[x][y].cleanSelection();
                }
            }
        }
    }

    public JPanel getGameBoard() {
        return this.boardPanel;
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
        testerLaPropositionButton.setEnabled(true);
        testerLaPropositionButton.setEnabled(false);
    }

    //Affiche que tel joueur tente de résoudre en tant de mouvement
    @Override
    public void thisPlayerTest(Player player, int nbrMoves) {
        //Ne fait rien pour l'instant
    }

    //Affiche que tel joueur a échoué à faire fonctionner sa solution
    @Override
    public void playerLoseTurn(Player player) {
        JOptionPane.showConfirmDialog(
                parent,
                player.getName() + " a perdu",
                "PERDU",
                JOptionPane.YES_NO_CANCEL_OPTION);
    }

    //Affiche que tel joueur a trouvé le nombre minial de mouvement du tour
    @Override
    public void playerWinTurn(Player player) {
        JOptionPane.showConfirmDialog(
                parent,
                player.getName() + " a trouvé le nombre minimal de mouvement",
                "GAGNE",
                JOptionPane.YES_NO_CANCEL_OPTION);
    }

    //Fin de la vue de l'interface graphique (fermeture)
    @Override
    public void closeView() {
        //Ne fait rien
    }


}
