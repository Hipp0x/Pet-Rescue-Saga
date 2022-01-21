package Modele;

import java.io.Serializable;
import java.util.ArrayList;

import static Controleur.Controleur.isCoupPossible;

public class Grille implements Serializable {

    public Case[][] gril;
    int longu,haut;
    static final long serialVersionUID = 10203041;

    public Grille(Case[][] grid){
        gril = grid;
        longu = grid[0].length;
        haut = grid.length;
    }


    //---------------------------------------------------------
    //              --- PARTIE 1 ---                          -
    //                  Getters                               -
    //---------------------------------------------------------

    public int getLongu() {
        return longu;
    }

    public int getHaut() {
        return haut;
    }


    //---------------------------------------------------------
    //                   --- PARTIE 2 ---                     -
    //             suppression et remplacement                -
    //---------------------------------------------------------


    //supprime la case à la position [i][j]
    //puis si les cases adjacentes sont de meme couleur
    //recursion sur elles pour les supprimer et faire de même
    public void supprimer(int x, int y){
            char res = gril[x][y].getIs();
            gril[x][y].setIs('s');
            if (x > 0) {
                if (gril[x-1][y].getIs() == res) {
                    supprimer(x - 1, y);
                }
            }
            if (x < gril.length-1){
                if (gril[x+1][y].getIs() == res) {
                    supprimer(x + 1, y);
                }
            }
            if (y > 0 ){
                if(gril[x][y-1].getIs() == res) {
                    supprimer(x, y - 1);
                }
            }
            if (y < gril[0].length-1 ){
                if(gril[x][y+1].getIs() == res) {
                    supprimer(x, y + 1);
                }
            }
    }

    //retourne vrai s'il y a des animaux en bas du tableau
    public boolean animalEnBas(){
        for (int i=0;i<gril[0].length;i++){
            if (gril[gril.length-1][i].getIs() == 'a'){
                return true;
            }
        }
        return false;
    }

    //supprime les animaux qui sont en bas du tableau
    //et retourne le nombre de points obtenus
    public int supprimerAnimalEnBas(){
        int points = 0;
        for (int i=0;i<gril[0].length;i++){
            if (gril[gril.length-1][i].getIs() == 'a'){
                gril[gril.length-1][i].setIs('s');
                points += 1000;
            }
        }
        return points;
    }

    //compte le nombre d'animaux présent sur le tableau
    //et le retourne
    public int aDAnimaux(){
        int nombre = 0;
        for (int i=0;i<gril.length;i++){
            for (int j=0;j<gril[0].length;j++) {
                if (gril[i][j].getIs() == 'a') {
                    nombre++;
                }
            }
        }
        return nombre;
    }

    //--------------------------------------------------------
    //                  si Decale
    //--------------------------------------------------------

    //regarge la colonne est vide
    public boolean estVide(int pos,Grille grille){
        for (int i=0;i<grille.gril.length;i++){
            if (grille.gril[i][pos].getIs() == 'a' || grille.gril[i][pos].getIs() == 'V' || grille.gril[i][pos].getIs() == 'J' || grille.gril[i][pos].getIs() == 'R' ||
                    grille.gril[i][pos].getIs() == 'O' || grille.gril[i][pos].getIs() == 'B'){
                return false;
            }
        }
        return true;
    }

    //decale les blocs vers la gauche
    public void decale(int pos){
        if (pos == gril[0].length-1){
            for (int i=0;i<gril.length;i++){
                if (gril[i][gril[0].length-1].getIs() != '-') {
                    gril[i][gril[0].length-1].setIs(' ');
                }
            }
        } else {
            for (int i=0;i<gril.length;i++){
                if (gril[i][pos].getIs() != '-') {
                    if (gril[i][pos + 1].getIs() == ' ') {
                        gril[i][pos].setIs(' ');
                    } else if (gril[i][pos + 1].getIs() == '-') {
                        gril[i][pos].setIs('s');
                    } else {
                        if(gril[i][pos + 1].getIs() == 'a'){
                            gril[i][pos] = new Dragon(((Dragon)gril[i][pos + 1]).getWhich());
                            gril[i][pos + 1] = new Case(' ');
                        }else {
                            char sub = gril[i][pos + 1].getIs();
                            gril[i][pos] = new Case(sub);
                            gril[i][pos + 1] = new Case(' ');
                        }
                    }
                }
            }
        }
    }

    //fait descendre les blocs sans en rajouter de nouveaux
    public void faireDescendreQuandDecale(){
        while (!remplie()) {
            for (int i = 0; i < gril.length; i++) {
                for (int j = 0; j < gril[i].length; j++) {
                    if (gril[i][j].getIs() == 's') {
                        if (i == 0) {
                            gril[i][j] = new Case(' ');
                        } else {
                            if (gril[i - 1][j].getIs() != ' ' && gril[i-1][j].getIs() != '-') {
                                if(gril[i - 1][j].getIs() == 'a'){
                                    gril[i][j] = new Dragon(((Dragon)gril[i - 1][j]).getWhich());
                                    gril[i - 1][j] = new Case('s');
                                }else {
                                    gril[i][j] = new Case(gril[i - 1][j].getIs());
                                    gril[i - 1][j] = new Case('s');
                                }
                            } else {
                                gril[i][j] = new Case(' ');
                            }
                        }
                    }
                }
            }
        }
    }

    //fonction qui effectue le décalage du tableau
    //tant que on a pas fini de switch les colonnes
    //on regarde si la colonne est vide
    // pour la decaler et faire descendre
    public void decaler(){
        while (pasFiniDeSwitch()) {
            for (int i = 0; i < gril[0].length; i++) {
                if (estVide(i, this)) {
                    decale(i);
                    faireDescendreQuandDecale();
                }
            }
        }
    }

    //regarde si on a fini de switch les colonnes
    // si on a remplie - vide - remplie => pas fini de switch
    public boolean pasFiniDeSwitch(){
        int vide = 0;
        for (int i=0;i<gril[0].length;i++){
            if (estVide(i,this)){
                vide++;
            }
            else{
                if (vide !=0){
                    return true;
                }
            }
        }
        return false;
    }

    //--------------------------------------------------------
    //                  si pas Decale
    //--------------------------------------------------------

    //regarde si le tableau est rempli
    // -> pas de case 's' présente
    public boolean remplie(){
        for (Case[] cases : gril) {
            for (Case aCase : cases) {
                if (aCase.getIs() == 's') {
                    return false;
                }
            }
        }
        return true;
    }

    //fait descendre les blocs en en rajoutant de nouveau
    //si animAlea -> un animal peut apparaître aléatoirement
    public void faireDescendre(boolean animAlea){
        while(!remplie()){
            ArrayList<Character> liste = new ArrayList<Character>();
            liste.add('J'); liste.add('O'); liste.add('R'); liste.add('B'); liste.add('V');
            if (animAlea){
                liste.add('a');
            }
            for (int i=0;i<gril.length;i++){
                for (int j=0;j<gril[i].length;j++){
                    if (gril[i][j].getIs() == 's'){
                        if (i-1 < 0){
                            int pos = (int) (Math.random()*liste.size());
                            if(liste.get(pos) == 'a'){
                                gril[i][j] = new Dragon();
                            }else {
                                gril[i][j] = new Case(liste.get(pos));
                            }
                            if (this.aDAnimaux()==5){
                                animAlea = false;
                            }
                        } else {
                            if ( gril[i-1][j].getIs() != ' ' && gril[i-1][j].getIs() != '-') {
                                if(gril[i - 1][j].getIs() == 'a'){
                                    gril[i][j] = new Dragon(((Dragon)gril[i - 1][j]).getWhich());
                                    gril[i - 1][j] = new Case('s');
                                }else {
                                    gril[i][j] = new Case(gril[i - 1][j].getIs());
                                    gril[i - 1][j] = new Case('s');
                                }
                            } else {
                                int pos = (int) (Math.random()*liste.size());
                                if(liste.get(pos) == 'a'){
                                    gril[i][j] = new Dragon();
                                }else {
                                    gril[i][j] = new Case(liste.get(pos));
                                }
                            }
                            if (this.aDAnimaux()==5){
                                animAlea = false;
                            }
                        }
                    }
                }
            }
        }
    }


    //---------------------------------------------------------
    //                   --- PARTIE 3 ---                     -
    //                 gestions des coups                     -
    //---------------------------------------------------------


    //regarde si un coup est encore possible
    // -> si au moins 2 blocs adjacents identiques
    public boolean coupPossible(){
        for (int i=0;i< getHaut();i++){
            for (int j=0;j<getLongu();j++){
                if(isCoupPossible(i, j)){
                    return true;
                }
            }
        }
        return false;
    }

    public int points(){
        int compt = this.ontEteSupprime();
        return compt*compt*10;
    }

    public int ontEteSupprime(){
        int compt = 0;
        for (Case[] cases : gril) {
            for (Case aCase : cases) {
                if (aCase.getIs() == 's') {
                    compt++;
                }
            }
        }
        return compt;
    }


    //----------------------------------------------------------
    //                Coup Spécial Fusée
    //----------------------------------------------------------

    //regarde si un coup scpécial a ete réalisé sur une ligne
    public boolean coupSpecialLigne(){
        for (int i = 0;i<gril.length;i++){
            ArrayList<Character> test = new ArrayList<>();
            for (int j=0;j<gril[i].length;j++){
                test.add(gril[i][j].getIs());
            }
            if (!test.contains('V') && !test.contains('J') && !test.contains('O') && !test.contains('R') && !test.contains('B')
                && !test.contains(' ')) {
                if (test.contains('s')){
                    return true;
                }
            }
        }
        return false;
    }

    //retourne la position du coup spécial
    //il est important de noter que cette fonction s'appelle avant le remplacement des cases supprimées
    //sinon on ne peut récuperer la position de la ligne supprimée
    public int coupSpecialLignePos(){
        for (int i = 0;i<gril.length;i++){
            ArrayList<Character> test = new ArrayList<>();
            for (int j=0;j<gril[i].length;j++){
                test.add(gril[i][j].getIs());
            }
            if (!test.contains('V') && !test.contains('J') && !test.contains('O') && !test.contains('R') && !test.contains('B')) {
                if (test.contains('s')){
                    return i;
                }
            }
        }
        return -1;
    }

    //pose une fusée sur la ligne ou le coup special a ete réalisé
    public void poserFusee(int pos, int j){
        gril[pos][j] = new Case('1',gril[pos][j].getColor());
    }

    //----------------------------------------------------------
    //                Coup Spécial Bloc
    //----------------------------------------------------------

    //regarde si un coup special de bloc a ete realisé
    //cad si un carré de 3*3 bloc de meme couleur a ete supprimé
    //regarde si pour une position i j
    //  - les 2 blocs a sa droite sont de la meme couleur
    //  - si c'est le cas on repete le processus pour les 2 lignes suivantes en partant de la meme colonne
    // si tout est bon alors c'est qu'un coup special a ete realisé
    public boolean coupSpecialBlocs(){
        for (int i = 0;i<gril.length-3;i++){
            for (int j=0;j<gril[0].length-3;j++){
                if (testCinq(i,j)){
                    if (gril[i][j].getColor() == gril[i+1][j].getColor()) {
                        if (testCinq(i + 1, j)) {
                            if (gril[i][j].getColor() == gril[i+2][j].getColor()) {
                                if (testCinq(i + 2, j)) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    //renvoie la position du premier carré d'un bloc qui a ete supprimé
    public int[] coupSpecialBlocsPos(){
        for (int i = 0;i<gril.length-3;i++){
            for (int j=0;j<gril[0].length-3;j++){
                if (testCinq(i,j)){
                    if (gril[i][j].getColor() == gril[i+1][j].getColor()) {
                        if (testCinq(i + 1, j)) {
                            if (gril[i][j].getColor() == gril[i+2][j].getColor()) {
                                if (testCinq(i + 2, j)) {
                                    return new int[]{i, j};
                                }
                            }
                        }
                    }
                }
            }
        }
        return new int[]{-1,-1};
    }

    //renvoie la couleur du coup spécial
    public char coupSpecialBlocsChar(){
        for (int i = 0;i<gril.length-3;i++){
            for (int j=0;j<gril[0].length-3;j++){
                if (testCinq(i,j)){
                    if (gril[i][j].getColor() == gril[i+1][j].getColor()) {
                        if (testCinq(i + 1, j)) {
                            if (gril[i][j].getColor() == gril[i+2][j].getColor()) {
                                if (testCinq(i + 2, j)) {
                                    return gril[i][j].getColor();
                                }
                            }
                        }
                    }
                }
            }
        }
        return ' ';
    }

    //regarde si pour une position i j
    //  - les 2 blocs a sa droite sont de la meme couleur
    public boolean testCinq(int ligneDepart, int colonneDepart){
        return gril[ligneDepart][colonneDepart].getColor() == gril[ligneDepart][colonneDepart + 1].getColor() &&
                gril[ligneDepart][colonneDepart + 1].getColor() == gril[ligneDepart][colonneDepart + 2].getColor();
    }

    //pose le ballon ou le coup spécial a été réalisé
    public void poserBallon(int[] pos, char color){
        gril[pos[0]][pos[1]] = new Case('2', color);
    }


    //---------------------------------------------------------
    //                 --- PARTIE 4 ---                       -
    //                Remplissage ses niveaux                 -
    //---------------------------------------------------------

    public void remplir_Niveau_1(ArrayList<Character> liste){
        for (int i=0;i<5;i++){
            if ((5-i-1) == 4){
                gril[1][2] = new Case(liste.get(4));gril[1][3] = new Case(liste.get(4)); gril[1][4] = new Case(liste.get(4));
                gril[3][0] = new Case(liste.get(4)); gril[3][1] = new Case(liste.get(4)); gril[4][0] = new Case(liste.get(4));
                gril[4][1] = new Case(liste.get(4)); gril[5][3] = new Case(liste.get(4)); gril[6][3] = new Case(liste.get(4));
                gril[5][6] = new Case(liste.get(4)); gril[6][6] = new Case(liste.get(4));
                liste.remove(4);
            } else if ((5-i-1) == 3){
                gril[1][5] = new Case(liste.get(3)); gril[1][6] = new Case(liste.get(3)); gril[2][5] = new Case(liste.get(3));
                gril[2][6] = new Case(liste.get(3)); gril[5][1] = new Case(liste.get(3)); gril[5][2] = new Case(liste.get(3));
                gril[6][1] = new Case(liste.get(3)); gril[6][2] = new Case(liste.get(3));
                liste.remove(3);
            } else if ((5-i-1) == 2){
                gril[2][2] = new Case(liste.get(2)); gril[2][3] = new Case(liste.get(2)); gril[2][4] = new Case(liste.get(2));
                gril[3][2] = new Case(liste.get(2)); gril[3][3] = new Case(liste.get(2)); gril[3][4] = new Case(liste.get(2));
                gril[4][2] = new Case(liste.get(2)); gril[4][3] = new Case(liste.get(2)); gril[4][4] = new Case(liste.get(2));
                liste.remove(2);
            } else if ((5-i-1) == 1){
                gril[1][0] = new Case(liste.get(1)); gril[1][1] = new Case(liste.get(1)); gril[2][0] = new Case(liste.get(1));
                gril[2][1] = new Case(liste.get(1)); gril[5][0] = new Case(liste.get(1)); gril[6][0] = new Case(liste.get(1));
                gril[5][4] = new Case(liste.get(1)); gril[5][5] = new Case(liste.get(1)); gril[6][4] = new Case(liste.get(1));
                gril[6][5] = new Case(liste.get(1));
                liste.remove(1);
            } else {
                gril[3][5] = new Case(liste.get(0)); gril[3][6] = new Case(liste.get(0));
                gril[4][5] = new Case(liste.get(0)); gril[4][6] = new Case(liste.get(0));
            }
            //pose les animaux
            gril[0][1] = new Dragon(); gril[0][5] = new Dragon();
            //pose les blocs vide du depart
            gril[0][0] = gril[0][2] = gril[0][3] = gril[0][4] = gril[0][6] = new Case(' ');
        }
    }

    public void remplir_Niveau_2(ArrayList<Character> liste){
        for (int i=0;i<3;i++){
            if ((3-i-1)==2){
                gril[0][1] = new Case(liste.get(2)); gril[0][3] = new Case(liste.get(2)); gril[1][1] = new Case(liste.get(2));
                gril[1][3] = new Case(liste.get(2)); gril[4][1] = new Case(liste.get(2)); gril[4][3] = new Case(liste.get(2));
                gril[5][1] = new Case(liste.get(2)); gril[5][3] = new Case(liste.get(2)); gril[6][0] = new Case(liste.get(2));
                gril[6][4] = new Case(liste.get(2)); gril[7][0] = new Case(liste.get(2)); gril[7][4] = new Case(liste.get(2));
                liste.remove(2);
            }
            else if ((3-i-1) == 1){
                gril[2][0] = new Case(liste.get(1)); gril[2][4] = new Case(liste.get(1)); gril[3][0] = new Case(liste.get(1));
                gril[3][4] = new Case(liste.get(1)); gril[3][2] = new Case(liste.get(1)); gril[4][2] = new Case(liste.get(1));
                gril[5][2] = new Case(liste.get(1)); gril[6][2] = new Case(liste.get(1)); gril[7][2] = new Case(liste.get(1));
                liste.remove(1);
            } else {
                gril[2][1] = new Case(liste.get(0)); gril[2][3] = new Case(liste.get(0)); gril[3][1] = new Case(liste.get(0));
                gril[3][3] = new Case(liste.get(0)); gril[4][0] = new Case(liste.get(0)); gril[4][4] = new Case(liste.get(0));
                gril[5][0] = new Case(liste.get(0)); gril[5][4] = new Case(liste.get(0)); gril[6][1] = new Case(liste.get(0));
                gril[6][3] = new Case(liste.get(0)); gril[7][1] = new Case(liste.get(0)); gril[7][3] = new Case(liste.get(0));
            }
            //pose les animaux
            gril[0][2] = new Dragon(); gril[1][2] = new Dragon();
            gril[2][2] = new Dragon(); gril[1][0] = new Dragon();
            gril[1][4] = new Dragon();
            //pose les blocs vides
            gril[0][0] = gril[0][4] = new Case (' ');
        }
    }

    public void remplir_Niveau_3(ArrayList<Character> liste){
        for (int i=0;i<3;i++){
            if ((3-i-1)==2){
                gril[1][3] = gril[2][3] = gril[3][2] = gril[4][2] = gril[4][0] = gril[6][0] = gril[7][2] = gril[8][2] = gril[7][4] = gril[8][4] = gril[1][5] = gril[2][5] = gril[3][5] = gril[4][5] = new Case(liste.get(2));
                liste.remove(2);
            } else if ((3-i-1) == 1){
                gril[1][2] = new Case(liste.get(1)); gril[2][2] = new Case(liste.get(1)); gril[3][4] = new Case(liste.get(1));
                gril[4][4] = new Case(liste.get(1)); gril[3][6] = new Case(liste.get(1)); gril[4][6] = new Case(liste.get(1));
                gril[5][0] = new Case(liste.get(1)); gril[5][1] = new Case(liste.get(1)); gril[5][3] = new Case(liste.get(1));
                gril[5][5] = new Case(liste.get(1)); gril[6][1] = new Case(liste.get(1)); gril[6][3] = new Case(liste.get(1));
                gril[6][5] = new Case(liste.get(1)); gril[7][0] = new Case(liste.get(1)); gril[7][1] = new Case(liste.get(1));
                gril[8][0] = new Case(liste.get(1)); gril[8][1] = new Case(liste.get(1));
                liste.remove(1);
            } else {
                gril[1][4] = new Case(liste.get(0)); gril[1][6] = new Case(liste.get(0)); gril[2][4] = new Case(liste.get(0));
                gril[2][6] = new Case(liste.get(0)); gril[3][3] = new Case(liste.get(0)); gril[4][3] = new Case(liste.get(0));
                gril[4][1] = new Case(liste.get(0)); gril[5][2] = new Case(liste.get(0)); gril[6][2] = new Case(liste.get(0));
                gril[5][4] = new Case(liste.get(0)); gril[6][4] = new Case(liste.get(0)); gril[5][6] = new Case(liste.get(0));
                gril[6][6] = new Case(liste.get(0)); gril[7][3] = new Case(liste.get(0)); gril[8][3] = new Case(liste.get(0));
            }
            //pose les animaux
            gril[0][2] = new Dragon(); gril[0][4] = new Dragon(); gril[0][6] = new Dragon();
            //pose les blocs vides
            gril[0][0] =  new Case(' '); gril[0][1] =  new Case(' '); gril[1][0] =  new Case(' ');
            gril[1][1] =   new Case(' ');gril[2][0] =   new Case(' ');gril[2][1] =   new Case(' ');
            gril[3][0] =  new Case(' '); gril[3][1] =  new Case(' '); gril[0][3] =  new Case(' ');
            gril[0][5] =  new Case(' ');
            //pose les blocs fixes
            gril[7][5] = gril[7][6] = gril[8][5] = gril[8][6] = new Case('-');
        }
    }

    public void remplir_Niveau_4(ArrayList<Character> liste){
        for (int i=0;i<8;i++){
            for (int j=0;j<9;j++){
                gril[i][j] = new Case (liste.get((int) (Math.random()*3)));
            }
        }
        //pose les animaux
        gril[3][1] = new Dragon(); gril[3][3] = new Dragon();
        gril[3][5] = new Dragon(); gril[3][7] = new Dragon();
    }

    public void remplir_Niveau_5(ArrayList<Character> liste){
        //pose les blocs de couleur supérieur
        for (int i=0;i<4;i++){
            for (int j=0;j<9;j++){
                gril[i][j] = new Case (liste.get((int) (Math.random()*3)));
            }
        }
        //pose les blocs de couleur inférieur
        gril[6][2] = new Case(liste.get(0)); gril[7][3] = new Case(liste.get(0));
        gril[6][4] = new Case(liste.get(0)); gril[7][5] = new Case(liste.get(0)); gril[6][6] = new Case(liste.get(0));
        gril[7][2] = new Case(liste.get(1)); gril[6][3] = new Case(liste.get(1));
        gril[7][4] = new Case(liste.get(1)); gril[6][5] = new Case(liste.get(1)); gril[7][6] = new Case(liste.get(1));
        //pose les animaux
        gril[5][2] = new Dragon(); gril[5][3] = new Dragon();
        gril[5][4] = new Dragon(); gril[5][5] = new Dragon(); gril[5][6] = new Dragon();
        //pose les blocs fixes
        gril[3][0] = new Case('-'); gril[3][8] = new Case('-'); gril[5][1] = new Case('-');
        gril[5][7] = new Case('-'); gril[6][1] = new Case('-'); gril[6][7] = new Case('-');
        gril[7][1] = new Case('-'); gril[7][7] = new Case('-'); gril[4][0] = new Case('-');
        gril[4][1] = new Case('-'); gril[4][2] = new Case('-'); gril[4][3] = new Case('-');
        gril[4][4] = new Case('-'); gril[4][5] = new Case('-'); gril[4][6] = new Case('-');
        gril[4][7] = new Case('-'); gril[4][8] = new Case('-');
        //pose les emplacements vides
        gril[0][0] = gril[0][8] = gril[5][0] = gril[5][8] = gril[6][0] = gril[6][8] = gril[7][0] = gril[7][8] = new Case(' ');
    }

    //remplis le niveau infini
    public void remplir_Niveau_inf(ArrayList<Character> liste){
        for (int i=0;i<9;i++){
            for (int j=0;j<9;j++){
                gril[i][j] = new Case (liste.get((int) (Math.random()*5)));
            }
        }
        //pose les animaux
        gril[2][2] = new Dragon(); gril[6][6] = new Dragon(); gril[7][7] = new Dragon();
        gril[4][4] = new Dragon(); gril[3][3] = new Dragon(); gril[5][5] = new Dragon();
    }

}
