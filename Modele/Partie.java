package Modele;

import Modele.Objet.*;

import java.util.ArrayList;

public class Partie {
    private Joueur joueur;
    private Niveau lvl;
    private int score,coupRes,animRes,scoreObj;

    public Partie(Joueur pers, Niveau niveau){
        joueur = pers;
        lvl = niveau;
        score = 0;
        scoreObj = 0;
        coupRes = lvl.getNb_coup_max();
        animRes = lvl.getNb_animaux();
    }


    //---------------------------------------------------------
    //                   --- PARTIE 1 ---                     -
    //                 Getters et Setters                     -
    //---------------------------------------------------------


    public Joueur getJoueur(){
        return joueur;
    }

    public int getScore(){
        return score;
    }

    public int getAnimRes(){
        return animRes;
    }

    public int getCoupRes(){
        return coupRes;
    }

    public Niveau getLvl(){
        return lvl;
    }

    public int getScoreObj(){return scoreObj;}


    //---------------------------------------------------------
    //                   --- PARTIE 2 ---                     -
    //                        Actions                         -
    //---------------------------------------------------------


    //action quand decide de supprimer un bloc
    public void actionBloc(int[] coords, boolean animAlea){
        boolean ligne = false;
        int posLignne = -1;
        boolean bloc = false;
        int[] posBloc = new int[]{-1,-1};
        char color = ' ';
        if (getLvl().getGrid().gril[coords[0]][coords[1]].getIs() == '1'){ ;
            Fusee a = new Fusee(getLvl().getGrid(),coords[1]);
            a.execute();
            remplacement(animAlea);
        }else if (getLvl().getGrid().gril[coords[0]][coords[1]].getIs() == '2'){
            Ballon a = new Ballon(getLvl().getGrid().gril[coords[0]][coords[1]].getColor(),getLvl().getGrid(),coords[0],coords[1]);
            a.execute();
            remplacement(animAlea);
        } else {
            lvl.getGrid().supprimer(coords[0], coords[1]);
            //recherche si des coups spéciaux ont été réalisés
            if (lvl.id >= 3) {
                if (lvl.getGrid().coupSpecialLigne()) {
                    ligne = true;
                    posLignne = lvl.getGrid().coupSpecialLignePos();
                }
            }
            if (lvl.id >= 4) {
                if (lvl.getGrid().coupSpecialBlocs()) {
                    bloc = true;
                    posBloc = lvl.getGrid().coupSpecialBlocsPos();
                    color = lvl.getGrid().coupSpecialBlocsChar();
                }
            }
            score += lvl.getGrid().points();
            scoreObj =Math.min( scoreObj + lvl.getGrid().points(), 1000);
            //remplacer après suppression de bloc
            remplacement(animAlea);
            //s'occupe de poser les objets quand il y a des coups spéciaux
            if (ligne) {
                lvl.getGrid().poserFusee(posLignne, coords[1]);
            }
            if (bloc) {
                lvl.getGrid().poserBallon(posBloc, color);
            }
            coupRes--;
        }
    }

    //action quand decide d'utiliser un objet
    public void actionObj(boolean animAlea,String obj, int[] coords){
        if (obj.equals("bombe")){
            Bombe bom = new Bombe(this.lvl.getGrid(), coords[0], coords[1]);
            bom.execute();
        } else if(obj.equals("fusee")){
            Fusee fus = new Fusee(this.lvl.getGrid(), coords[1]);
            fus.execute();
        } else {
            Pioche pio = new Pioche(this.lvl.getGrid(), coords[0], coords[1]);
            pio.execute();
        }
        remplacement(animAlea);
        coupRes--;
    }

    //remplacement du tableau après une action
    public void remplacement(boolean animAlea){
        if (lvl.isDecale()) {
            lvl.getGrid().faireDescendreQuandDecale();
            lvl.getGrid().decaler();
        } else {
            lvl.getGrid().faireDescendre(animAlea);
        }
        //vérifie qu'il n'y a plus d'animaux en bas
        while(lvl.getGrid().animalEnBas()) {
            int  ajout = lvl.getGrid().supprimerAnimalEnBas();
            score += ajout;
            scoreObj = Math.min(scoreObj + ajout, 1000);
            animRes -= ajout/1000;
            if (lvl.isDecale()) {
                lvl.getGrid().faireDescendreQuandDecale();
                lvl.getGrid().decaler();
            } else {
                lvl.getGrid().faireDescendre(animAlea);
            }
        }
    }

    //---------------------------------------------------------
    //                   --- PARTIE 3 ---                     -
    //                      Méthodes                          -
    //---------------------------------------------------------

    //Vérfie les conditions de fin de jeu
    public int finJeu(){
        if(coupRes == 0){
            return 1;
        }else if(animRes == 0){
            return 2;
        } else if (!(lvl.getGrid().coupPossible())){
            return 3;
        }else{
            return 0;
        }
    }

    public void scoreObjReset(){
        scoreObj = 0;
    }


}
