package view;

import game.Location;
import game.Move;
import game.RicochetsRobot;
import players.Player;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;


// Affichage

public class PlayerMoveView implements ModelView, BoardListener {
    private RicochetsRobot game;
    public JPanel window;
    private JLabel gamerName;
    private JLabel gamerType;
    private JLabel nombreProposition;
    private JButton annulerMouvementButton;
    private JButton refaireMouvementButton;
    private JButton envoyerPropositionButton;
    private JButton annulerLaPropositionButton;
    private JButton finirLaPartieButton;
    private JButton choisirLaCibleDuButton;
    private JButton retourAuMenuButton;
    private JPanel gameBoard;
    private Player player;
    private JFrame parent;
    private Board board;

    //Constructeur
    public PlayerMoveView(RicochetsRobot game, Player player, JFrame parent) {
        this.game = game;
        this.player = player;
        this.parent = parent;
        gamerName.setText(player.getName());
        gamerType.setText(player.getType());
        board = new Board(game, parent);
        board.addBoardListener(this);
        gameBoard.add(board.getGameBoard());
        nombreProposition.setText(player.getNbrProposed() + "");
        finirLaPartieButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        annulerLaPropositionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        envoyerPropositionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        retourAuMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

    }

    @Override
    public void updatePage() {

    }

    @Override
    public void newBoard() {

    }

    @Override
    public void updateBoard() {

    }

    @Override
    public void updateRobots() {

    }

    @Override
    public void updatePlayers() {

    }

    @Override
    public void updatePlayersProposal(Player player) {

    }

    @Override
    public void thisPlayerTest(Player player, int nbrMoves) {

    }

    @Override
    public void playerLoseTurn(Player player) {

    }

    @Override
    public void playerWinTurn(Player player) {

    }

    @Override
    public void closeView() {

    }

    @Override
    public void onRobotClicked(int x, int y, int robotNumber) {
        // clean all current selection
        board.cleanBoardSelection();
        // show destination
        ArrayList<Move> moves = game.getRobotValidMoves(robotNumber);
        CaseView c = board.getPlateauViews()[x][y];
        // show current destinations
        for (int i = 0; i < moves.size(); i++) {
            Move move = moves.get(i);
            int destX = move.getRobotDestination().getX();
            int destY = move.getRobotDestination().getY();
            CaseView item = board.getPlateauViews()[destX][destY];
            item.addSelection();
            item.repaint();
        }
        board.getGameBoard().repaint();
        board.getGameBoard().revalidate();
        board.getMainPanel().repaint();
    }

    @Override
    public void onDestinationClicked(int robot, int destX, int destY) {
        board.cleanBoardSelection();
        Move move = new Move(robot , new Location(destX, destY));
        game.playMove(player , move);
        board.getGameBoard().repaint();
        board.getGameBoard().revalidate();
        board.getMainPanel().repaint();
    }
}
