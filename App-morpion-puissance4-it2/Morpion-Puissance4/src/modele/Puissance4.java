package Main.modele;

public class Puissance4 {
    private char[][] grille;
    private int coupsJoues;
    private static final int lignes = 6;
    private static final int colonnes = 7;

    public Puissance4() {
        this.grille = new char[lignes][colonnes];
        this.coupsJoues = 0;
        initialiserGrille();
    }

    //on représente les case vides par un point '.'
    private void initialiserGrille() {
        for (int i = 0; i < lignes; i++) {
            for (int j = 0; j < colonnes; j++) {
                grille[i][j] = '.';
            }
        }
    }

    //mets a jour la grille actuelle
    public String obtenirGrille() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < lignes; i++) {
            for (int j = 0; j < colonnes; j++) {
                sb.append("|").append(grille[i][j]);
            }
            sb.append("|\n");
        }
        sb.append(" 1 2 3 4 5 6 7 "); //ajout du numéro des colonnes
        return sb.toString();
    }

    //fonction pour mettre le jeton dans la case la plus basse de la colonnes et vérifie si la case est valide
    public boolean jouerCoup(int col, String nomJoueur) {
        if (col < 0 || col >= colonnes || grille[0][col] != '.') {
            return false; // Colonne pleine ou invalide
        }

        //on cherche la ligne la plus basse vide
        //R:Rouge ou J:Jaune
        for (int i = lignes - 1; i >= 0; i--) {
            if (grille[i][col] == '.') {
                grille[i][col] = (coupsJoues % 2 == 0) ? 'R' : 'J';
                coupsJoues++;
                return true;
            }
        }
        return false;
    }

    public boolean Gagne() {
        //lance la vérification des lignes pour savoir si le joueur gagne
        return (verifieHorizontale() || verifieVerticale() || verifieDiagonales());
    }

    private boolean verifieHorizontale() {
        for (int i = 0; i < lignes; i++) {
            for (int j = 0; j <= colonnes - 4; j++) {
                char c = grille[i][j];
                if (c != '.' && c == grille[i][j+1] && c == grille[i][j+2] && c == grille[i][j+3]) return true;
            }
        }
        return false;
    }

    private boolean verifieVerticale() {
        for (int j = 0; j < colonnes; j++) {
            for (int i = 0; i <= lignes - 4; i++) {
                char c = grille[i][j];
                if (c != '.' && c == grille[i+1][j] && c == grille[i+2][j] && c == grille[i+3][j]) return true;
            }
        }
        return false;
    }

    private boolean verifieDiagonales() {
        //vérification de la diagonale du haut gauche vers bas droit ('\')
        for (int i = 0; i <= lignes - 4; i++) {
            for (int j = 0; j <= colonnes - 4; j++) {
                char c = grille[i][j];
                if (c != '.' && c == grille[i+1][j+1] && c == grille[i+2][j+2] && c == grille[i+3][j+3]) return true;
            }
        }
        //vérification de la diagonale du bas gauche au haut droit ('/')
        for (int i = 3; i < lignes; i++) {
            for (int j = 0; j <= colonnes - 4; j++) {
                char c = grille[i][j];
                if (c != '.' && c == grille[i-1][j+1] && c == grille[i-2][j+2] && c == grille[i-3][j+3]) return true;
            }
        }
        return false;
    }

    public boolean estPleine() {
        return coupsJoues == lignes * colonnes;
    }

    public boolean estFini() {
        return Gagne() || estPleine();
    }
}