package Main.vue;

import java.util.Scanner;

public class Ihm {
    private Scanner sc;

    public Ihm() {
        this.sc = new Scanner(System.in);
    }

    //affiche un message simple (ex:victoire ou erreur)
    public void afficherMessage(String message) {
        System.out.println(message);
    }

    //demande le nom des joueurs au début
    public String demanderNomJoueur(int numero) {
        System.out.print("Entrez le nom du joueur " + numero + " : ");
        return sc.nextLine();
    }

    public String demanderIA() {
        System.out.print("Voulez vous jouer contre contre l'IA (Tapez 'IA'): ");
        return sc.nextLine();
    }
    //menu pour choisir le jeu (Morpion ou Puissance 4)
    public int choisirJeu() {
        System.out.println("\n=== CHOIX DU JEU ===");
        System.out.println("1: Morpion (3x3)");
        System.out.println("2: Puissance 4 (6x7)");
        System.out.print("Votre choix : ");

        try {
            return Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            return -1; //retourne une valeur invalide si des caractére non attendu sont taper
        }
    }

    //affiche la grille en cours avec la grille du jeu selectionner
    public void afficherGrille(String renduGrille) {
        System.out.println("\nÉTAT DU PLATEAU :");
        System.out.println(renduGrille);
    }

    //envoie un message pour la saisie du coup pour le Morpion
    public String saisirCoupMorpion(String nomJoueur) {
        System.out.print(nomJoueur + ", entrez votre coup (ex: 12 pour ligne 1 colonne 2) : ");
        return sc.nextLine();
    }

    //envoie un message pour la saisie du coup pour le Puissance 4
    public int saisirCoupPuissance4(String nomJoueur) {
        System.out.print(nomJoueur + ", choisissez une colonne (1-7) : ");
        try {
            return Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            return -1;//renvoie valeur invalide si la saisie n'est pas valide (pas un int)
        }
    }

    //demande si on veut refaire une partie
    public boolean veutRejouer() {
        System.out.print("\nVoulez-vous rejouer ? (oui/non) : ");
        String reponse = sc.nextLine().trim().toLowerCase();
        return reponse.equals("oui") || reponse.equals("o");
    }
}