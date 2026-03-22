package Main.modele;

import Main.modele.Morpion;
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
            // On vérifie si la colonne n'est pas pleine (en regardant la ligne 0)
            if (jeu.obtenirGrille().contains("|.")) {
                colonnesValides.add(c + 1);
            }
        }
        return colonnesValides.get(rand.nextInt(colonnesValides.size()));
    }

    public String choisirCoupMorpion(Morpion jeu, int casesVides) {
        char[][] plateau = jeu.getGrille();
        if (casesVides > 4) {
            // Heuristique simple : Jouer au centre, sinon un coin, sinon aléatoire
            //Si l'IA peut gagner elle joue le coup gagnant
            int[] coup = chercherCoupDecisif(plateau,'O');
            if (coup != null) return coordonneeVersString(coup);

            //Si le joueur peut gagner l'ia le bloque
            coup = chercherCoupDecisif(plateau,'X');
            if (coup != null) return coordonneeVersString(coup);

            //si le centre est libre
            if (plateau[1][1]==' ') return "22";

            //pour jouer dans les coins
            int[][] coins = {{0, 0}, {0, 2}, {2, 0}, {2, 2}};
            for (int[] c : coins) if (plateau[c[0]][c[1]] == ' ') return coordonneeVersString(c);

            //pour jouer sur les côté
            int[][] cotes = {{0, 1}, {1, 0}, {1, 2}, {2, 1}};
            for (int[] c : cotes) if (plateau[c[0]][c[1]] == ' ') return coordonneeVersString(c);
        }
        //MinMax
        //return minmax;
    }

    private int[] chercherCoupDecisif(char[][] b, char s) {
        for (int i=0; i<3; i++) {
            // Lignes
            if (b[i][0]==s && b[i][1]==s && b[i][2]==' ') return new int[]{i, 2};
            if (b[i][0]==s && b[i][2]==s && b[i][1]==' ') return new int[]{i, 1};
            if (b[i][1]==s && b[i][2]==s && b[i][0]==' ') return new int[]{i, 0};
            // Colonnes
            if (b[0][i]==s && b[1][i]==s && b[2][i]==' ') return new int[]{2, i};
            if (b[0][i]==s && b[2][i]==s && b[1][i]==' ') return new int[]{1, i};
            if (b[1][i]==s && b[2][i]==s && b[0][i]==' ') return new int[]{0, i};
        }
        // Diagonales
        if (b[0][0]==s && b[1][1]==s && b[2][2]==' ') return new int[]{2, 2};
        if (b[0][2]==s && b[1][1]==s && b[2][0]==' ') return new int[]{2, 0};
        // ... (ajouter les autres combinaisons de diagonales pour être exhaustif)
        return null;
    }
    private String coordonneeVersString(int[] c) {
        return "" + (c[0] + 1) + (c[1] + 1);
    }


}
