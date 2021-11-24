package view;

import game.RicochetsRobot;
import players.Player;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


// Gère l'affichage du menu

public class Menu {
    private final RicochetsRobot game;
    private JPanel panelMain;
    private JLabel menuLabel;
    private JButton buttonPartieRapide;
    private JButton buttonPartieComplete;
    private JButton buttonCreationJoueur;
    private JButton reglesDuJeuButton;
    private JButton buttonFermer;

    //Constructeur
    public Menu(RicochetsRobot game) {
        this.game = game;


        buttonPartieRapide.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.setPage("QuickConfig");
            }
        });

        buttonPartieComplete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.setPage("PlayersBeforeGame");
            }
        });

        buttonCreationJoueur.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.setPage("Players");
            }
        });

        reglesDuJeuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.setPage("Rules");
            }
        });

        buttonFermer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.fireCloseView();
            }
        });

    }

    //Accesseur
    public JPanel getPanelMain() {
        return panelMain;
    }
}

