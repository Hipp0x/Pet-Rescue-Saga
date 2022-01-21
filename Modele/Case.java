package Modele;

import java.io.Serializable;

public class Case implements Serializable {

    private char is;
    private char color;

    public Case(char est){
        is = est;
        color = est;
    }

    //necessaire pour le ballon
    public Case(char est, char colo){
        is = est;
        color = colo;
    }


    //---------------------------------------------------------
    //                   --- PARTIE 1 ---                     -
    //                  Getters et Setters                    -
    //---------------------------------------------------------


    public char getIs(){
        return is;
    }

    public char getColor() { return color;}

    public void setIs(char c){
        is = c;
    }


    //---------------------------------------------------------
    //                   --- PARTIE 2 ---                     -
    //                  Infos sur les cases                   -
    //---------------------------------------------------------

    //Voici la description des différents états des cases

    //'s' = Supprimé
    //'a' = animal
    //' ' = vide
    //'-' = fixe

    //'1' = fusée
    //'2' = ballon
    //'3' = bombe
    //'4' = pioche

    //J = jaune
    //O = orange
    //R = rouge
    //B = bleu
    //V = violet


}
