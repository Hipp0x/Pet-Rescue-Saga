package Controleur;

import java.util.Scanner;

public class Launcher {

    public static void main(String[] args){
        if (demandeJeu().equals("t")){
            Controleur.trouverJoueur();
        }else {
            Controleur.lancement();
        }
    }

    //demande au joueur de quelle manière il souhaite jouer
    // -> Interface Graphique ou via le terminal
    public static String demandeJeu(){
        System.out.println("De quelle facon voulez-vous jouer ?");
        System.out.println("T(exte) ou I(nterface) ?");
        String rep ;
        boolean ok;
        do{
            rep = new Scanner(System.in).next();
            switch (rep.toLowerCase()){
                case "t","texte","i","interface" -> ok = true;
                default -> {
                    System.out.println("Vous n'avez pas repondu correctement !");
                    System.out.println("De quelle façcon voulez-vous jouer ?");
                    System.out.println("T(exte) ou I(nterface) ?");
                    ok = false;
                }
            }
        }while(!ok);
        return String.valueOf(rep.toLowerCase().charAt(0));
    }
}
