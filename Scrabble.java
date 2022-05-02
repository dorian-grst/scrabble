import java.util.ArrayList;

public class Scrabble {
    private Joueur[] joueurs;
    private int numJoueur; // joueur courant (entre 0 et joueurs.length-1)
    private Plateau plateau;
    private MEE sac;
    private boolean estFinie = false;
    private static int [] nbPointsJeton = {1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 10, 1, 2, 1, 1, 3, 8, 1, 1, 1, 1, 4, 10, 10, 10, 10};

    public Scrabble(String[] nomJoueurs) {
        this.joueurs = new Joueur[nomJoueurs.length];
        for (int i = 0; i < nomJoueurs.length; i++) {
            this.joueurs[i] = new Joueur(nomJoueurs[i]);
        };

        this.numJoueur = Ut.randomMinMax(0, nomJoueurs.length - 1);
        this.plateau = new Plateau();
        this.sac = new MEE(new int[]{9, 2, 2, 3, 15, 2, 2, 2, 8, 1, 1, 5, 3, 6, 6, 2, 1, 6, 6, 6, 6, 2, 1, 1, 1, 1});
    }

    private void changeJoueur() {
        this.numJoueur ++;
        if (this.numJoueur > this.joueurs.length - 1) this.numJoueur = 0;
    }

    private ArrayList<Joueur> getGagnant() {
        ArrayList<Joueur> gagnants = new ArrayList<Joueur>();
        gagnants.add(this.joueurs[0]);
        for (int i = 1; i < this.joueurs.length; i ++) {
            if (this.joueurs[i].getScore() > gagnants.get(0).getScore()) {
                gagnants.clear();
                gagnants.add(this.joueurs[i]);
            } else if (this.joueurs[i].getScore() == gagnants.get(0).getScore()) gagnants.add(this.joueurs[i]);
        }
        return gagnants;
    }

    public void partie() {
        for (int i = 0; i < this.joueurs.length; i ++) {
            this.joueurs[i].prendJetons(this.sac, 7);
        }

        while (!this.estFinie) {
            Joueur currentJoueur = this.joueurs[this.numJoueur];

            Ut.afficherSL("C'est au tour de " + currentJoueur.toString() + " de jouer.");

            int joueRes = currentJoueur.joue(this.plateau, this.sac, this.nbPointsJeton);
            switch(joueRes) {
                case 0:
                case -1:
                    this.changeJoueur();
                    break;
                case 1:
                    this.estFinie = this.sac.estVide();
                    break;
                
            }
        }
        
        ArrayList<Joueur> gagnants = this.getGagnant();
        if (gagnants.size() == 1) Ut.afficherSL("Le gagnant de la partie est " + gagnants.get(0).toString() + " avec un score de " + gagnants.get(0).getScore()); 
        else Ut.afficherSL("Les gagnants sont : " + gagnants.toString() + " avec un score de " + gagnants.get(0).getScore());
        Ut.afficherSL("La partie est finito");
    }
}
