package modele;

import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public class IA  extends Joueur{
    private Random rand = new Random();

    //constructeur IA
    public IA(){
        super("IA",'O');
    }

    // IA naif pour puissance4
    public int choisirCoupPuissance4(Puissance4 jeu) {
        List<Integer> colonnesValides = new ArrayList<>();
        for (int c = 0; c < 7; c++) {
            // On vérifie si la colonne n'est pas pleine (en regardant la ligne 0).
            if (jeu.obtenirGrille().contains("|.")) {
                colonnesValides.add(c + 1);
            }
        }
        return colonnesValides.get(rand.nextInt(colonnesValides.size()));
    }

    public String choisirCoupMorpion(Morpion jeu, int casesVides) {
        char[][] grille = jeu.getGrille();

        if (casesVides > 4) {
            //Heuristique simple : Jouer au centre, sinon un coin, sinon aléatoire.
            //Si l'IA peut gagner, elle joue le coup gagnant.
            int[] coup = chercherCoupDecisif(grille,'O');
            if (coup != null) return coordonneeVersString(coup);

            //Si le joueur peut gagner l'IA le bloque.
            coup = chercherCoupDecisif(grille,'X');
            if (coup != null) return coordonneeVersString(coup);

            //si le centre est libre
            if (grille[1][1]==' ') return "22";

            //pour jouer dans les coins
            int[][] coins = {{0, 0}, {0, 2}, {2, 0}, {2, 2}};
            for (int[] c : coins)
                if (grille[c[0]][c[1]] == ' ')
                    return coordonneeVersString(c);

            //pour jouer sur les côtés
            int[][] cotes = {{0, 1}, {1, 0}, {1, 2}, {2, 1}};
            for (int[] c : cotes)
                if (grille[c[0]][c[1]] == ' ')
                    return coordonneeVersString(c);
        }
        return minmax(grille);
    }

    private String minmax(char[][] grille) {
        //Initialiser meilleurScore avec la valeur int la plus petite possible
        //Initialiser meilleurCoup comme une liste vide
        int meilleurScore = Integer.MIN_VALUE;
        int[] meilleurCoups = null;

        for (int i=0;i<3;i++) {
            for (int j=0;j<3;j++) {
                if (grille[i][j]==' '){
                    grille[i][j]='O';
                    int score = calculMinMax(grille, false); //lance la fonction récursive pour trouver le meilleur score
                    grille[i][j]=' ';

                    //mets à jour le meilleurScore
                    if (score>meilleurScore) {
                        meilleurScore = score;
                        //enregistre le coup dans meilleurCOups
                        meilleurCoups=new int[]{i,j};
                    }
                }
            }
        }
        return coordonneeVersString(meilleurCoups);
    }

    //calcul la valeur du meilleur coup que l'IA peut jouer en minimisant les coups du joueur
    private int calculMinMax(char[][] grille, boolean tour_IA) {
        //L'IA gagne un point pour la win
        if (aGagne(grille,'O')) return 1;
        //L'IA perd un point pour la loose
        if (aGagne(grille,'X')) return -1;
        //Pas de point gagner pour l'égalité
        if (estPlein(grille)) return 0;

        if (tour_IA){
            //prend la valeur la plus petite possible pour meilleur
            int meilleur = Integer.MIN_VALUE;
            for (int i=0;i<3;i++) {
                for (int j=0;j<3;j++) {
                    if (grille[i][j]==' '){
                        grille[i][j]='O'; //joue un coup de l'IA pour tester le meilleur coup
                        int score = calculMinMax(grille,false)+1; //appelle de la fonction récursive pour trouver le meilleur coup en ajoutant pour son meilleur coup
                        grille[i][j]=' '; //remet la case a vide après le test
                        meilleur=Math.max(meilleur,score); //choisie le score le plus grand pour être le meilleur
                    }
                }
            }
            return meilleur;
        }else{
            int meilleur = Integer.MAX_VALUE;
            for (int i=0;i<3;i++) {
                for (int j=0;j<3;j++) {
                    if (grille[i][j]==' '){
                        grille[i][j]='X'; //joue un coup pour le joueur pour tester le meilleur coup
                        int score = calculMinMax(grille,true)-1; //appelle de la fonction récursive pour trouver le meilleur coup en enlevant pour son meilleur coup
                        grille[i][j]=' ';
                        meilleur=Math.min(meilleur,score);  //choisie le score le plus petit pour être le meilleur
                    }
                }
            }
            return meilleur;
        }
    }

    private boolean estPlein(char[][] grille) {
        for (int i=0;i<3;i++) {
            for (int j=0;j<3;j++) {
                if (grille[i][j]==' ') return false;
            }
        }
        return true;
    }

    private boolean aGagne(char[][] grille,char symbole) {
        for (int i=0;i<3;i++) {
            //Lignes
            if (grille[i][0]==symbole &&  grille[i][1]==symbole && grille[i][2]==symbole) return true;
            //Colonnes
            if (grille[0][i]==symbole &&  grille[1][i]==symbole && grille[2][i]==symbole) return true;
        }
        //Diagonales
        if (grille[0][0] == symbole && grille[1][1]== symbole && grille[2][2]== symbole) return true;
        if (grille[0][2] == symbole && grille[1][1]== symbole && grille[2][0]== symbole) return true;
        //Pas gagnés
        return false;
    }

    private int[] chercherCoupDecisif(char[][] grille, char symbole) {
        for (int i=0; i<3; i++) {
            // Lignes
            if (grille[i][0]==symbole && grille[i][1]==symbole && grille[i][2]==' ') return new int[]{i, 2};
            if (grille[i][0]==symbole && grille[i][2]==symbole && grille[i][1]==' ') return new int[]{i, 1};
            if (grille[i][1]==symbole && grille[i][2]==symbole && grille[i][0]==' ') return new int[]{i, 0};
            // Colonnes
            if (grille[0][i]==symbole && grille[1][i]==symbole && grille[2][i]==' ') return new int[]{2, i};
            if (grille[0][i]==symbole && grille[2][i]==symbole && grille[1][i]==' ') return new int[]{1, i};
            if (grille[1][i]==symbole && grille[2][i]==symbole && grille[0][i]==' ') return new int[]{0, i};
        }
        // Diagonales
        if (grille[0][0]==symbole && grille[1][1]==symbole && grille[2][2]==' ') return new int[]{2, 2};
        if (grille[0][2]==symbole && grille[1][1]==symbole && grille[2][0]==' ') return new int[]{2, 0};
        return null;
    }

    //transforme les coordonnées vers un String
    private String coordonneeVersString(int[] c) {
        return "" + (c[0] + 1) + (c[1] + 1);
    }
}