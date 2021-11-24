package view;

import game.RicochetsRobot;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;


// Affichage des règles

public class Rules {
    private RicochetsRobot game;
    private JPanel panelRules;
    private JTextArea textArea1;
    private JButton retourAuMenuButton;

    //Constructeur
    public Rules(RicochetsRobot game) {
        this.game = game;
        textArea1.setText("Le but du jeu est de trouver, pour chaque tour, un chemin permettant au bon robot d'atteindre la cible en jeu pour ce tour-ci. \n" +
                "Il faut proposer un nombre de mouvements à effectuer. Puis prouver que ce nombre de mouvement est valide. Le joueur qui aura trouvé un chemin en le moins de coup possible gagne le point de ce tour. \n" +
                "Le premier joueur a avoir 8 points gagne la partie.\n"
        );
        retourAuMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.setPage("Menu");
            }
        });
    }

    //Accesseur
    public JPanel getPanelRules() {
        return panelRules;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
