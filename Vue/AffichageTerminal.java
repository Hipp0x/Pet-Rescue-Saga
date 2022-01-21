package Vue;

import Controleur.Controleur;
import Modele.Joueur;
import Modele.Niveau;
import Modele.Partie;

import java.util.ArrayList;

import static Controleur.Controleur.choixLevel;

public class AffichageTerminal {

    Joueur joueur;
    Niveau niveau;
    Partie partie;
    Controleur controleur;

    public AffichageTerminal(){}


    //---------------------------------------------------------
    //                   --- PARTIE 1 ---                     -
    //                       Setters                          -
    //---------------------------------------------------------


    public void setJoueur(Joueur joueur) {
        this.joueur = joueur;
    }

    public void setPartie(Partie partie){
        this.partie = partie;
        this.niveau = partie.getLvl();
        controleur = new Controleur(partie);
    }


    //---------------------------------------------------------
    //                   --- PARTIE 2 ---                     -
    //                      Affichage                         -
    //---------------------------------------------------------


    //affiche les niveaux possibles
    public void afficheNiveauPossible(){
        int parcours = 0;
        String a  = "";
        String b = "";
        String c = "";
        ArrayList<Integer> copie = joueur.getNivAcess();
        for (Integer parcrs: copie){
            a+="-----  ";
            c+="-----  ";
            b+="| "+  copie.get(parcours) + " |  ";
            parcours++;
        }
        System.out.println(a);
        System.out.println(b);
        System.out.println(c);
    }

    //affichage des propriétés du niveau en mode texte
    public void afficherPresentNiveau(){
        System.out.println("Niveau "+niveau.id);
        System.out.println("Objectifs :");
        System.out.println("Sauver "+niveau.getNb_animaux()+" dragons");
        if (niveau.getNb_coup_max() != -1){
            System.out.println(niveau.getNb_coup_max()+" coups maximum");
        }
        System.out.println();
        afficher_score();
    }

    //affiche les meilleurs scores du niveau choisi
    public void afficher_score(){
        System.out.println("Meilleurs scores :");
        int compt = 1;
        for (var item : niveau.best_score.entrySet()) {
            System.out.println(compt + " : " + item.getKey() +" -> " + item.getValue());
            compt++;
        }
        if (compt == 0){
            System.out.println("Pas encore de scores");
        }
        System.out.println();
    }

    //affiche l'etat de la partie en cours
    public void afficheEtat(boolean animAlea){
        System.out.println("Score : " + partie.getScore() + " points");
        System.out.println((niveau.getNb_animaux()-partie.getAnimRes()) + "/" + niveau.getNb_animaux() + " dragons sauves");
        if (niveau.getNb_coup_max() != -1){
            System.out.println("Coups restants : " + partie.getCoupRes());
        }
        System.out.println();
        afficherGrille();
        Controleur.demandeAction(animAlea);
    }

    //affiche la grille du niveau
    public void afficherGrille(){
        System.out.print("   ");
        for (int k=0;k<niveau.getGrid().gril[0].length;k++){
            System.out.print((char)(k+65) + " ");
        }
        System.out.println();
        for (int i = 0;i<niveau.getGrid().gril.length;i++) {
            if (i<10) {
                System.out.print(i+ "  ");
            }else {
                System.out.print(i+" ");
            }
            for (int j=0;j<niveau.getGrid().gril[0].length;j++) {
                System.out.print(niveau.getGrid().gril[i][j].getIs() + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    //affichage pour la fin de la partie
    public void affichageFinDePartie(int finJeu){
        if (finJeu != 2 ){
            if (finJeu == 1) {
                System.out.println("Vous avez epuise le nombre de coups disponible.");
            } else if (finJeu == 3){
                System.out.println("Plus aucun coup n'est possible.");
            }
            System.out.println("Vous n'avez pas reussi a sauver tous les dragons !");
            System.out.println(":(");

        } else {
            System.out.println("Bravo, vous avez sauve tous les dragons!");
            System.out.println(":)");
            System.out.println("Vous avez obtenu un score de " + this.partie.getScore() + " points");
            if (this.partie.getScore() > niveau.recupDernierScore(niveau.best_score)){
                System.out.println("Vous etes desormais dans le top 5 des meilleurs joueurs !");
                controleur.miseAJourScore(this.partie.getScore(),this.joueur.getNom(),this.niveau);
            }
        }
        Controleur.lancement(joueur);
    }

    //affiche les objets possible à utiliser
    public void afficherObjetPossible(String liste){
        System.out.println("Vous pouvez utiliser : ");

            String a1 = " " + "-".repeat(liste.length() + 2) + "   ";
            String b = " " + "-".repeat(liste.length() + 2) + "   ";;
                String c =  "| " + liste + " |  ";
            System.out.println(a1);
            System.out.println(c);
            System.out.println(b);
            System.out.println("Voulez vous l'utiliser ? O ou N");
    }

    public void afficherRegles(){
        System.out.println("     Rescue All Dragons est un jeu de Reflexion    "+

                       "\n\n                 Votre Objectif :                  "+

                        "\n           Sauver tous les dragons perdus          " +

                      "\n\nPour y parvenir, vous devrez :                     " +

                "\n  - Detruire des blocs de meme materiaux (min 2)   " +

                "\n  - Amenez les dragons au sol pour les sauver      " +

                "\n  - Faire attention aux nombre de coups maximum    " +

                "\n\nLa partie est terminee lorsque :                   " +

                "\n  - Vous ne pouvez plus detruire de blocs          " +

                "\n  - Vous avez effectue tous les coups disponibles  " +

                "\n  - Vous avez sauve tous les dragons          \n");
        choixLevel();
    }



}
