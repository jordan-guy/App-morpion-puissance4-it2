package Main.modele;

public class Joueur {
    private String nom;
    private int score;
    private char symbole;

    public Joueur(String nom, char symbole) {
        this.nom = nom;
        this.symbole = symbole;
        this.score = 0;
    }


    public String getNom() {
        return nom;
    }

    public char getSymbole() {
        return symbole;
    }

    public int getScore() {
        return score;
    }

    //fonction pour incrémenter le score
    public void ajouterVictoire() {
        this.score++;
    }

    public void setSymbole(char nouveauSymbole) {
        this.symbole = nouveauSymbole;
    }
}