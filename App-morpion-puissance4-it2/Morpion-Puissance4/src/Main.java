package Main;

import Main.vue.Ihm;
import Main.controleur.Controleur;
//la classe Main lance le controleur qui gere les jeux en général

public class Main {
    public static void main(String[] args) {
        Ihm ihm = new Ihm();
        Controleur controleur = new Controleur(ihm);
        controleur.lancerJeu();
    }
}