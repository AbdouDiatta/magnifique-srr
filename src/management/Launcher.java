package management;

import game.*;
import view.*;
import players.*;

import java.util.Scanner;


//La classe qui gère le déroulement d'une partie.

public class Launcher {
    protected Scanner clavier;//Permet l'interaction avec le terminal
    protected RicochetsRobot game;//Le jeu qui sera joué


    //Constructeur
    public Launcher(RicochetsRobot game) {
        this.clavier = new Scanner(System.in);
        this.game = game;
    }

    //Lancement du jeu
    public void startPlay() {
        System.out.println("Lancement du programme RicochetsRobot avec terminal.");
        System.out.println("=====================");
        System.out.println("Poursuivre avec le terminal ou lancer l'interface graphique");
        System.out.println("=====================");
        System.out.println(" 0 - Terminal");
        System.out.println(" 1 - Interface graphique");

        //Affichages
        // conflit entre l'interface et le ui. on ne pourra lancer qu'un a la fois
        String choose = clavier.next();//Recoit le choix
        if (choose.equals("0") || choose.equals("T") || choose.equals("terminal")) {
            TerminalView terminalView = new TerminalView(game, clavier);
        } else if (choose.equals("1") || choose.equals("I") || choose.equals("UI")) {
            System.out.println("Lancement de l'interface graphique");
            System.out.println("Le programme du terminal va s'arreter");
            WindowView windowView = new WindowView(game);
        }
        //Lancement du menu
        game.setPage("Menu");

    }
}