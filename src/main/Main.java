package main;

import game.*;
import players.*;
import management.*;

import java.util.Arrays;
import java.util.Scanner;

/*
=====> Cette classe sert à utiliser les jeux du projet

Pour compiler et exécuter(terminal ouvert dans src) :
javac -d ../build ./main/Main.java
java -cp ../build main.Main

*/

public class Main {
  public static void main(String[] args) {

    //Création du Lacuncher qui gérera le jeu.
    Launcher launcher = new Launcher(new RicochetsRobot());

    //Lancement du jeu
    launcher.startPlay();

    System.out.println("Fin du programme.\nSi le programme ne s'arrête pas, regardez si une fenêtre est restée ouverte.");
  }
 
  
}
