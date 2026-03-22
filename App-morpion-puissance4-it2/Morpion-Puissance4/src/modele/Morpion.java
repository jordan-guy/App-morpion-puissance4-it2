package Main.modele;

public class Morpion {
    private char[][] grille;
    public int coupsJoues;

    public Morpion() {
        //initialisation d'une grille 3x3
        this.grille = new char[3][3];
        this.coupsJoues = 0;
        initialiserGrille();
    }

    private void initialiserGrille() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                grille[i][j] = ' '; // Case vide
            }
        }
    }

    //mets a jour la grille actuelle
    public String obtenirGrille() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                sb.append("[").append(grille[i][j]).append("]");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    //vérifie si le coup est dans la grille et si la case est vide
    public boolean jouerCoup(int ligne, int col, String nomJoueur) {
        if (ligne >= 0 && ligne < 3 && col >= 0 && col < 3 && grille[ligne][col] == ' ') {
            // Le joueur 1 à 'X' et le joueur 2 à 'O'
            grille[ligne][col] = (coupsJoues % 2 == 0) ? 'X' : 'O';
            coupsJoues++;
            return true;
        }
        return false;
    }

    //fonction pour vérifier si le joueur à gagner.
    public boolean Gagne() {
        //vérification des lignes et colonnes
        for (int i = 0; i < 3; i++) {
            if (grille[i][0] != ' ' && grille[i][0] == grille[i][1] && grille[i][1] == grille[i][2]) return true;
            if (grille[0][i] != ' ' && grille[0][i] == grille[1][i] && grille[1][i] == grille[2][i]) return true;
        }
        //vérification des diagonales
        if (grille[0][0] != ' ' && grille[0][0] == grille[1][1] && grille[1][1] == grille[2][2]) return true;
        if (grille[0][2] != ' ' && grille[0][2] == grille[1][1] && grille[1][1] == grille[2][0]) return true;

        return false;
    }
    //fonction pour vérifier si la grille est pleine
    public boolean estPleine() {
        return coupsJoues == 9;
    }

    public boolean estFini() {
        return Gagne() || estPleine();
    }

    public int getCoupsJoues() {
        return coupsJoues;
    }

    public char[][] getGrille() {
        return this.grille;
    }
}