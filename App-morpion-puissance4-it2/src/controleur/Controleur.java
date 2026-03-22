package controleur;

import vue.Ihm;
import modele.Joueur;
import modele.Morpion;
import modele.Puissance4;
import modele.IA;

/**
 * La classe Controleur fait le lien entre l'Ihm et le package Modele avec les différentes classes qu'elle contient.
 */
public class Controleur {
    private Ihm ihm;
    private Joueur j1;
    private Joueur j2;
    private IA ia;

    public Controleur(Ihm ihm) {
        this.ihm = ihm;
        this.ia = new IA();
    }

    public void lancerJeu() {
        //initialisation des joueurs avec l'utilisation de l'Ihm.
        String nom1 = ihm.demanderNomJoueur(1);
        String nom2;
        if ("IA".equalsIgnoreCase(ihm.demanderIA())) {
            nom2 = "IA";
        }else{
            nom2 = ihm.demanderNomJoueur(2);
        }

        //attribution des symboles aux joueurs
        this.j1 = new Joueur(nom1, 'X');
        this.j2 = new Joueur(nom2, 'O');

        boolean continuerSession = true;
        while (continuerSession) {
            //lance l'Ihm pour demander le jeu
            int choix = ihm.choisirJeu();

            if (choix == 1) {
                jouerMorpion();
            } else if(choix ==2){
                jouerPuissance4();
            }
            //vérifie si les joueurs veulent relancer en utilisant l'Ihm
            continuerSession = ihm.veutRejouer();
        }

        afficherBilanFinal();
    }

    private void jouerMorpion() {
        Morpion jeu = new Morpion();
        Joueur actuel = j1; //le joueur 1 commence toujours

        while (!jeu.estFini()) {
            //récupère la grille actuelle à l'aide de des classes dans modele et affichage avec ihm
            ihm.afficherGrille(jeu.obtenirGrille());
            String coup;
            if (actuel.getNom().equals("IA")){
                ihm.afficherMessage("L'IA joue son coup");
                int casesVides = 9-jeu.getCoupsJoues();
                coup= ia.choisirCoupMorpion(jeu,casesVides);
                ihm.afficherMessage("L'IA a joué: "+coup);
            }else{
                coup=ihm.saisirCoupMorpion(actuel.getNom());
            }
            //évite le crash si le joueur mes 1 seule valeur
            if (coup.length() < 2) {
                ihm.afficherMessage("Erreur : Vous devez entrer deux chiffres (ex: 12).");
                continue; // Relance la boucle sans changer de joueur
            }
            //conversion les caractères d'un String en coordonnées int (0 à 2)
            int ligne = Character.getNumericValue(coup.charAt(0)) - 1;
            int col = Character.getNumericValue(coup.charAt(1)) - 1;

            //vérifie si un coup est valide
            if (jeu.jouerCoup(ligne, col, actuel.getNom())) {
                if (jeu.Gagne()) {
                    ihm.afficherGrille(jeu.obtenirGrille());
                    ihm.afficherMessage("Félicitations " + actuel.getNom() + " !");
                    actuel.ajouterVictoire();
                }
                actuel = (actuel == j1) ? j2 : j1;
            } else {
                ihm.afficherMessage("Case occupée ou invalide !");
            }
        }
    }

    private void jouerPuissance4() {
        Puissance4 jeu = new Puissance4();
        Joueur actuel = j1;

        while (!jeu.estFini()) {
            ihm.afficherGrille(jeu.obtenirGrille());

            int col;

            if  (actuel.getNom().equals("IA")){
                ihm.afficherMessage("L'IA joue son coup");
                col= ia.choisirCoupPuissance4(jeu);
            }else{
                col=ihm.saisirCoupPuissance4(actuel.getNom());
            }
            int indexCol =col-1;

            if (jeu.jouerCoup(indexCol, actuel.getNom())) {
                if (jeu.Gagne()) {
                    ihm.afficherGrille(jeu.obtenirGrille());
                    ihm.afficherMessage("Victoire de " + actuel.getNom() + " !");
                    actuel.ajouterVictoire();
                }
                actuel = (actuel == j1) ? j2 : j1;
            } else {
                ihm.afficherMessage("Action impossible !");
            }
        }
    }

    //affiche le résumé des victoires et détermine le gagnant.
    private void afficherBilanFinal() {
        ihm.afficherMessage("\n--- BILAN DE LA SESSION ---");
        ihm.afficherMessage(j1.getNom() + " : " + j1.getScore() + " victoire(s)");
        ihm.afficherMessage(j2.getNom() + " : " + j2.getScore() + " victoire(s)");

        if (j1.getScore() > j2.getScore()) {
            ihm.afficherMessage("Le gagnant est " + j1.getNom() + " !");
        } else if (j2.getScore() > j1.getScore()) {
            ihm.afficherMessage("Le gagnant est " + j2.getNom() + " !");
        } else {
            ihm.afficherMessage("Match nul : Ex aequo !");
        }
    }
}