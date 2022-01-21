package Modele;

import java.io.*;
import java.util.*;

public class Niveau implements Serializable {
    
    private Grille grid;
    public int id;
    protected int nb_animaux;
    protected int nb_coup_max;
    public Map<Integer,String> best_score;
    protected ArrayList<Character> listColor;
    protected String objDispo;
    protected boolean decale;
    static final long serialVersionUID = 10203042;

    public Niveau(Grille grille, int numero, int animaux, int coup, boolean decale){
        grid = grille;
        id = numero;
        nb_animaux = animaux;
        nb_coup_max = coup;
        best_score = new TreeMap<Integer, String>(Collections.reverseOrder()); ;
        this.decale = decale;
        listColor = listeColor();
        objDispo = remplirObjetDispo(id);

    }


    //---------------------------------------------------------
    //                   --- PARTIE 1 ---                     -
    //                     Remplissage                        -
    //---------------------------------------------------------
    
    //remplissage des grilles selon le niveau
    public void remplir_Grille(){
        if (id == 1){
            grid.remplir_Niveau_1(this.liste(5));
        }else if( id == 2){
            grid.remplir_Niveau_2(this.liste(3));
        }else if (id == 3) {
            grid.remplir_Niveau_3(this.liste(3));
        } else if (id == 4){
            grid.remplir_Niveau_4(this.liste(3));
        }else if (id== 5){
            grid.remplir_Niveau_5(this.liste(3));
        } else {
            grid.remplir_Niveau_inf(this.liste(5));
        }
    }

    //rempli la liste d'objet disponible du niveau
    public String remplirObjetDispo(int id){
        if (id == 3){
            return "Fusee";
        }
        if (id == 4){
            return "Bombe";
        }
        if (id == 5){
            return "Pioche";
        }
        return "";
    }
    
    
    //---------------------------------------------------------
    //                   --- PARTIE 2 ---                     -
    //                  liste de couleurs                     -
    //---------------------------------------------------------
    

    //renvoie un chiffre aléatoire en fonction de la longueur
    public int chiffre_aleatoire(int longueur){
        Random rand = new Random();
        return rand.nextInt(longueur);
    }

    //creation et renvoie d'une liste de couleur
    public ArrayList<Character> listeColor(){
        ArrayList<Character> chiffre = new ArrayList<Character>();
        chiffre.add('J'); chiffre.add('O'); chiffre.add('R'); chiffre.add('B'); chiffre.add('V');
        return chiffre;
    }

    //creer une liste de couleur en fonction dune longueur
    // -> prends un element aléatoire dans chiffre
    // -> ajout dans notre liste puis suppression dans chiffre
    //permet d'obtenir une liste de couleur à chaque fois différente
    public ArrayList<Character> liste(int longueur){
        ArrayList<Character> chiffre = listeColor();
        ArrayList<Character> fin = new ArrayList<Character>();
        for (int i=0;i<longueur;i++){
            int compt = chiffre_aleatoire(chiffre.size());
            fin.add(chiffre.get(compt));
            chiffre.remove(compt);
        }
        return fin;
    }
    

    //---------------------------------------------------------
    //                   --- PARTIE 3 ---                     -
    //                 Getters et Setters                     -
    //---------------------------------------------------------


    public Grille getGrid() {
        return grid;
    }
    
    public int getNb_animaux() {
        return nb_animaux;
    }
    
    public int getNb_coup_max() {
        return nb_coup_max;
    }

    public boolean isDecale() {
        return decale;
    }

    public String getObjDispo(){
        return objDispo;
    }

    public static Niveau recupNiveau(int level){
        ObjectInputStream ois = null;
        try {
            FileInputStream fis = new FileInputStream("level.ser");
            if (fis.available() != 0) {
                ois = new ObjectInputStream(fis);
                while (fis.available() != 0) {
                    Niveau test = (Niveau) ois.readObject();
                    if (test.id == level) {
                        return test;
                    }
                }
            }
            return null;
        } catch (final IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
            } catch (final IOException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    public int recupDernierScore(Map<Integer,String> scores){
        int min = -1;
        for (var item : scores.entrySet()) {
            if (item.getKey() < min){
                min = item.getKey();
            }
        }
        return min;
    }


    //---------------------------------------------------------
    //                   --- PARTIE 4 ---                     -
    //                 Mise a jour du Niveau                  -
    //---------------------------------------------------------


    //met a jour le niveau dans level.ser
    public void miseAJourScore(int score, String joueur, Niveau lvl){
        if (! best_score.isEmpty()) {
            int last = recupDernierScore(best_score);
            best_score.remove(last);
        }
        best_score.put(score,joueur);
        remplir_Grille();
        Niveau a = new Niveau(new Grille(new Case[lvl.getGrid().gril.length][lvl.getGrid().gril[0].length]),lvl.id,lvl.getNb_animaux(),lvl.getNb_coup_max(), lvl.isDecale());
        a.best_score = best_score;
        ArrayList<Niveau> levels = new ArrayList<>();
        ObjectInputStream ois;
        ObjectOutputStream oos;
        try {
            FileInputStream fis = new FileInputStream("level.ser");
            if (fis.available() != 0) {
                ois = new ObjectInputStream(fis);
                while (fis.available() != 0) {
                    Niveau test = (Niveau) ois.readObject();
                    if (test.id == lvl.id){
                        levels.add(lvl);
                    }else {
                        levels.add(test);
                    }
                }
            }
            FileOutputStream fos = new FileOutputStream("level.ser");
            oos = new ObjectOutputStream(fos);
            for (Niveau level : levels) {
                oos.writeObject(level);
            }
            oos.flush();
            oos.close();
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

}
