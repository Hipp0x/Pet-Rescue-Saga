package Modele.Objet;

import Modele.Case;
import Modele.Grille;

public class Pioche extends Objet {
    int j;
    public Pioche(Grille grille, int i, int j){
        this.grille = grille;
        this.i = i;
        this.j = j;
    }

    public void execute() {
        grille.gril[i][j] = new Case('s');
    }
}
