package view;

import game.RicochetsRobot;
import players.*;

import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.*;
import java.beans.Visibility;
import java.util.ArrayList;

import static javax.swing.JComponent.WHEN_FOCUSED;


// Affichage

public class PlayersView implements ModelView {
    private RicochetsRobot game;
    private JPanel panelPlayers;
    private JTextField textFieldName;
    private JComboBox<String> comboBoxTypeJoueur;
    private JButton buttonSavePlayer;
    private JButton retourAuMenuButton;
    private JTable playersTable;
    public JPanel playersListPanel;
    private JButton lancerLaPartieButton;
    private JButton supprimerJoueurButton;
    private DefaultTableModel tableModel;
    private DefaultComboBoxModel<String> playerTypemMdel;
    private  int selectedPlayer;

    //Constructeur
    public PlayersView(RicochetsRobot game, boolean beforeGame) {
        // load players tables
        this.game = game;
        lancerLaPartieButton.setVisible(beforeGame);
        game.addModelListener(this);
        loadPlayers();
        loadPlayersTypes();
        buttonSavePlayer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // save new  player
                String name = textFieldName.getText();
                String type = (String) comboBoxTypeJoueur.getSelectedItem();
                if (type.equals("Humain")) {
                    game.addPlayer(new Human(game, name));
                }
                if (type.equals("Bot A*")) {
                    game.addPlayer(new BotAetoile(game, name));
                }
                if (type.equals("Bot Random")) {
                    game.addPlayer(new BotRandom(game));
                }
                textFieldName.setText("");
            }
        });
        comboBoxTypeJoueur.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    String item = (String) e.getItem();
                    if (item == "Bot Random") {
                        textFieldName.setVisible(false);
                    } else {
                        textFieldName.setVisible(true);
                    }
                    // do something with object
                }
            }
        });
        retourAuMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.setPage("Menu");
            }
        });

        lancerLaPartieButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.setPage("QuickConfig");
            }
        });
        supprimerJoueurButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.getPlayers().remove(selectedPlayer);
                game.firePlayersChange();
            }
        });
    }

    //Accesseurs
    public JPanel getPanelPlayers() {
        return panelPlayers;
    }

    public JTable getPlayersTable() {
        return playersTable;
    }

    private void loadPlayersTypes() {
        playerTypemMdel = new DefaultComboBoxModel<String>(new String[]{"Humain", "Bot Random", "Bot A*"});
        comboBoxTypeJoueur.setModel(playerTypemMdel);
    }

    @Override
    public void updatePage() {

    }

    public void loadPlayers() {
        supprimerJoueurButton.setVisible(false);
        ArrayList<Player> gamePlayers = game.getPlayers();
        String[] columnNames = {"Nom", "Type", "Point", "Proposition (Nbre)"};
        tableModel = new DefaultTableModel(columnNames, 0);
        for (Player player : game.getPlayers()
        ) {
            Object[] data = {player.getName(), player.getType(), player.getPoints(), player.getNbrProposed()};
            tableModel.addRow(data);
        }
        //JTableHeader header = new JTableHeader();
        playersTable.setModel(tableModel);
        playersTable.setDefaultEditor(Object.class, null);
        playersTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        playersTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                // do some actions here, for example
                // print first column value from selected row
               int currentPlayerNumber = playersTable.getSelectedRow();
               supprimerJoueurButton.setVisible(true);
            }
        });
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
    public void newBoard() {

    }

    @Override
    public void updateBoard() {

    }

    @Override
    public void updateRobots() {

    }

    @Override
    public void updatePlayersProposal(Player player) {
        loadPlayers();
    }

    @Override
    public void closeView() {

    }

    @Override
    public void updatePlayers() {
        loadPlayers();
    }


}
