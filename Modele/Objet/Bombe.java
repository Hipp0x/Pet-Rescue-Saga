package Modele.Objet;

import Modele.Case;
import Modele.Grille;

public class Bombe extends Objet {
    int j;
    public Bombe(Grille grille, int lig, int col){
        this.grille = grille;
        i = lig;
        j = col;
    }

    public void execute() {
        grille.gril[i][j] = new Case('s');
        if (i>0){
            if (grille.gril[i-1][j].getIs() != ' ' && grille.gril[i-1][j].getIs() != '-' && grille.gril[i-1][j].getIs() != 'a') {
                grille.gril[i - 1][j] = new Case('s');
            }
            if (j>0){
                if (grille.gril[i-1][j-1].getIs() != ' ' && grille.gril[i-1][j-1].getIs() != '-' && grille.gril[i-1][j-1].getIs() != 'a') {
                    grille.gril[i - 1][j - 1] = new Case('s');
                }
                if (grille.gril[i][j-1].getIs() != ' ' && grille.gril[i][j-1].getIs() != '-' && grille.gril[i][j-1].getIs() != 'a') {
                    grille.gril[i][j - 1] = new Case('s');
                }
            }
            if (j<grille.gril[0].length-1){
                if (grille.gril[i-1][j+1].getIs() != ' ' && grille.gril[i-1][j+1].getIs() != '-' && grille.gril[i-1][j+1].getIs() != 'a') {
                    grille.gril[i - 1][j + 1] = new Case('s');
                }
                if (grille.gril[i][j+1].getIs() != ' ' && grille.gril[i][j+1].getIs() != '-' && grille.gril[i][j+1].getIs() != 'a') {
                    grille.gril[i][j + 1] = new Case('s');
                }
            }
        }
        if (i<grille.gril.length-1){
            if (grille.gril[i+1][j].getIs() != ' ' && grille.gril[i+1][j].getIs() != '-' && grille.gril[i+1][j].getIs() != 'a') {
                grille.gril[i + 1][j] = new Case('s');
            }
            if (j>0){
                if (grille.gril[i+1][j-1].getIs() != ' ' && grille.gril[i+1][j-1].getIs() != '-' && grille.gril[i+1][j-1].getIs() != 'a') {
                    grille.gril[i + 1][j - 1] = new Case('s');
                }
            }
            if (j<grille.gril[0].length-1){
                if (grille.gril[i+1][j+1].getIs() != ' ' && grille.gril[i+1][j+1].getIs() != '-' && grille.gril[i+1][j+1].getIs() != 'a') {
                    grille.gril[i + 1][j + 1] = new Case('s');
                }
            }
        }

    }
}
