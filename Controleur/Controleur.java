package Controleur;

import Modele.Grille;
import Modele.Joueur;
import Modele.Niveau;
import Modele.Partie;
import Vue.*;

import java.util.*;

public class Controleur {
    private static AffichageTerminal vueTerm = new AffichageTerminal();
    private static AffichageGraphique vueGraph = new AffichageGraphique(new Controleur());
    private static Partie partie;

    public Controleur(){
    }

    public Controleur(Partie parti){
        partie = parti;
    }


    //---------------------------------------------------------
    //                   --- PARTIE 1 ---                     -
    //                  Getters et Setters                    -
    //---------------------------------------------------------

    public static void setPartie(Partie parti){
        partie = parti;
    }

    //---------------------------------------------------------
    //                   --- PARTIE 2 ---                     -
    //                 Communne aux 2 vues                    -
    //---------------------------------------------------------


    //regarde si la connexion est possible
    // -> si l'identifiant est dans joueur.ser
    // -> si le mot de passe correspond à celui récuper dans joueur.ser
    public boolean connexionPossible(String ident, String pswd){
        if (Joueur.rechercheId(ident)){
            Joueur test = Joueur.getJoueur(ident);
            if (pswd.equals(test.getMdp())) {
                return true;
            }
        }
        return false;
    }

    //regarde si l'inscription est possible
    // -> si l'identifiant est dans joueur.ser
    // -> si les 2 mots de passe sont identiques
    public boolean inscriptionPossible(String ident, String pswd, String pswd2){
        if (!Joueur.rechercheId(ident)) {
            if (pswd.equals(pswd2)) {
                return true;
            }
        }
        return false;
    }

    //regarde si un coup est Valide
    // -> si on ne clique pas sur un dragon, un bloc fixe ou un espace vide
    public static boolean coupValide(int i, int j){
        if (partie.getLvl().getGrid().gril[i][j].getIs() == '1' ||
                partie.getLvl().getGrid().gril[i][j].getIs() == '2'){
            return true;
        }
        if(partie.getLvl().getGrid().gril[i][j].getIs() != ' ' &&
                partie.getLvl().getGrid().gril[i][j].getIs() != '-' &&
                partie.getLvl().getGrid().gril[i][j].getIs() != 'a') {
            return isCoupPossible(i,j);
        }
        return false;
    }

    //regarde si le coup de la fusée est possible
    // -> si on ne lance pas la fusée sur une colonne vide ou rempli de bloc fixe
    public static boolean coupValideFus(int j){
        for (int i=0;i<partie.getLvl().getGrid().gril.length;i++){
            if (partie.getLvl().getGrid().gril[i][j].getIs() != ' ' &&
                    partie.getLvl().getGrid().gril[i][j].getIs() != '-'){
                return true;
            }
        }
        return false;
    }

    //regarde si pour l'objet en entrée le coup est possible
    public boolean coupValideObj(String obj, int i, int j){
        if (obj.equals("Fusee")){
            return coupValideFus(j);
        } else {
            return coupValideBombPio(i,j);
        }
    }

    //regarde si le coup de la bombe est possible
    public static boolean coupValideBombPio(int i, int j){
        return(partie.getLvl().getGrid().gril[i][j].getIs() != ' ' &&
                partie.getLvl().getGrid().gril[i][j].getIs() != '-' &&
                partie.getLvl().getGrid().gril[i][j].getIs() != 'a');
    }


    //met à jour les score du niveau (best_score)
    public void miseAJourScore(int score,String name,Niveau lvl){
        lvl.miseAJourScore(score,name,lvl);
    }

    //regarde si un coup est possible
    // -> si un bloc en haut, en bas, à droite ou à gauche est comme lui
    public static boolean isCoupPossible(int i, int j){
        if(partie.getLvl().getGrid().gril[i][j].getIs() == '-' || partie.getLvl().getGrid().gril[i][j].getIs() == ' ' ||
                partie.getLvl().getGrid().gril[i][j].getIs() == 'a'){
            return false;
        }
        if (i>0){
            if (partie.getLvl().getGrid().gril[i-1][j].getIs() == partie.getLvl().getGrid().gril[i][j].getIs()){
                return true;
            }
        }
        if (i<partie.getLvl().getGrid().gril.length-1){
            if (partie.getLvl().getGrid().gril[i + 1][j].getIs() == partie.getLvl().getGrid().gril[i][j].getIs()) {
                return true;
            }
        }
        if(j>0) {
            if (partie.getLvl().getGrid().gril[i][j-1].getIs() == partie.getLvl().getGrid().gril[i][j].getIs()) {
                return true;
            }
        }
        if (j<partie.getLvl().getGrid().gril[0].length-1){
            if (partie.getLvl().getGrid().gril[i][j+1].getIs() == partie.getLvl().getGrid().gril[i][j].getIs()){
                return true;
            }
        }
        return false;
    }



    //---------------------------------------------------------
    //                   --- PARTIE 3 ---                     -
    //                    Pour le Terminal                    -
    //---------------------------------------------------------


    //lance la connexion ou l'inscription en fonction du choix du joueur
    public static void trouverJoueur(){
        String rep = demandeJoueur();
        Joueur joueur;
        if (rep.equals("c")){
            joueur = connexionTxt();
        } else {
            joueur = inscriptionTxt();
        }
        Controleur.lancement(joueur);
    }

    //demande au joueur s'il préfère se connecter ou s'incrire
    public static String demandeJoueur(){
        System.out.println("Co(nnexion) ou I(nscription) ?");
        String rep;
        boolean ok;
        do{
            rep = new Scanner(System.in).next();
            switch (rep.toLowerCase(Locale.ROOT)) {
                case "co", "i", "connexion","inscription" -> ok = true;
                default -> {
                    ok = false;
                    System.out.println("Vous n'avez pas repondu correctement !");
                    System.out.println("Co(nnexion) ou I(nscription) ?");
                }
            }
        }while(!ok);
        return String.valueOf(rep.toLowerCase().charAt(0));
    }

    //fonction de connexion
    public static Joueur connexionTxt(){
        System.out.println("Entrez votre identifiant : (ou i pour l'inscription)");
        String rep ;
        Joueur joueur = null;
        boolean ok = false;
        do {
            rep = new Scanner(System.in).next();
            if (rep.equals("i")) {
                joueur = inscriptionTxt();
                ok =true;
            } else if (!Joueur.rechercheId(rep)) { //regarder si present dans joueur.ser (ici cas negatif)
                System.out.println("Identifiant introuvable.");
                System.out.println("Entrez votre identifiant : (ou i pour l'inscription)");
                ok = false;
            } else { // l'identifiant est présent dans joueur.ser, on demande le mot de passe
                System.out.println("Entrez votre mot de passe :");
                String repMdp = new Scanner(System.in).next();
                if (!Joueur.rechercheMdp(rep, repMdp)) { // mauvais mot de passe
                    ok = false;
                    System.out.println("Mot de passe incorrect !");
                    System.out.println("Entrez votre identifiant : (ou i pour l'inscription)");
                } else { //mot de pass correct
                    joueur = Joueur.getJoueur(rep);
                    ok = true;
                }
            }
        } while (!ok);
        return joueur;
    }

    //fonction d'inscription
    public static Joueur inscriptionTxt(){
        System.out.println("Choisissez un identifiant : (ou c pour se connecter)");
        String rep ;
        Joueur joueur = null;
        boolean ok = false;
        do {
            rep = new Scanner(System.in).next();
            if (rep.equals("c")) {
                joueur = connexionTxt();
                ok = true;
            } else if (!Joueur.rechercheId(rep)) { //regarder si present dans joueur.ser (ici cas negatif donc demande mot de passe)
                System.out.println("Entrez votre mot de passe :");
                String repMdp = new Scanner(System.in).next();
                System.out.println("Entrez votre mot de passe une nouvelle fois :");
                String repMdp2 = new Scanner(System.in).next();
                if (repMdp.equals(repMdp2)) { // les 2 mots de passe sont pareil donc création du joueur et ecriture sur joueur.ser
                    joueur = Joueur.creerJoueur(rep, repMdp);
                    ok = true;
                } else { //mot de pass incorrect
                    ok = false;
                    System.out.println("Vos mots de passes sont differents !");
                    System.out.println("Choisissez un identifiant : (ou c pour se connecter)");
                }
            } else { // l'identifiant est présent dans joueur.ser, on ne peut pas l'utiliser
                System.out.println("Identifiant déjà utilise.");
                System.out.println("Choisissez un identifiant : (ou c pour se connecter)");
                ok = false;
            }
        } while (!ok);
        return joueur;
    }

    //lancement du jeu :
    // -> choix du niveau
    // -> lancement d'une partie
    public static void lancement(Joueur gameur){
        vueTerm.setJoueur(gameur);
        int level = choixLevel();
        while (! gameur.levelEstPossible(level)){
            System.out.println("Vous n'avez pas acces a ce niveau !");
            level = choixLevel();
        }
        Niveau vaJouerA = Niveau.recupNiveau(level);
        Objects.requireNonNull(vaJouerA).remplir_Grille();
        Partie pA = new Partie(gameur,vaJouerA);
        setPartie(pA);
        jouer(partie);
    }

    //déroulement d'une partie
    public static void jouer(Partie partie){
        vueTerm.setPartie(partie);
        vueTerm.afficherPresentNiveau();
        do{
            int animAle = partie.getLvl().getGrid().aDAnimaux();
            vueTerm.afficheEtat(animAle<5);
        }while(partie.finJeu() == 0);
        if (partie.finJeu() == 2) {
            partie.getJoueur().miseAJour(partie.getJoueur(),partie.getLvl());
            partie.getLvl().miseAJourScore(partie.getScore(),partie.getJoueur().getNom(),partie.getLvl() );
        }
        vueTerm.affichageFinDePartie(partie.finJeu());
    }

    //retourne le niveau choisi
    public static int choixLevel(){
        System.out.println("Niveaux possibles :");
        vueTerm.afficheNiveauPossible();
        char rep;
        do {
            System.out.println("Choisissez votre niveau (Tapez R pour voir les regles):");
            rep = new Scanner(System.in).next().charAt(0);
            if (rep == 'R' || rep == 'r') {
                vueTerm.afficherRegles();
            } else if (!(Character.getNumericValue(rep) > 0 && Character.getNumericValue(rep) < 6)) {
                System.out.println("Entrez un chiffre entre 1 et 5!");
            } else {
                return Character.getNumericValue(rep);
            }
        }while(false);
        return -1;
    }

    //demande au joueur quelle action il veut faire
    // -> casser des blocs ou utiliser un objet
    public static void demandeAction(boolean animAlea){
        System.out.println("Que voulez-vous faire ?");
        System.out.println("Supprimer un bloc ? Utiliser un objet ? Robot ?");
        System.out.println("B(loc) / O(bjet) / R(obot)");
        String[] coordsStr;
        int[] coords = new int[2];
        String a = new Scanner(System.in).next();
        switch (a.toLowerCase()) {
            case "r":
            case "robot":
                robot(partie);
                break;
            case "b":
            case "bloc":
                boolean flag=false;
                do {
                    System.out.println("Entrez les coordonnees : (ex: B4)");
                    coordsStr = recupCoords();
                    if (coordsVerif(coordsStr)) {
                        coords = coordsInt(coordsStr);
                        if (coupValide(coords[0],coords[1])){
                            flag = true;
                        }
                    }
                }while(!flag);
                partie.actionBloc(coords,animAlea);
                break;
            case "o":
            case "objet":
                if(partie.getScoreObj() != 1000){
                    System.out.println("L'objet n'est pas encore debloque");
                    demandeAction(animAlea);
                }else {
                    String liste = partie.getLvl().getObjDispo();
                    if (!liste.equals("")) {
                        boolean repOk = false;
                        do {
                            flag = false;
                            vueTerm.afficherObjetPossible(liste);
                            String rep = new Scanner(System.in).next();
                            if ((rep.toLowerCase()).equals("o")) {
                                switch (liste) {
                                    case "Bombe" -> {
                                        do {
                                            System.out.println("Ou voulez utiliser la Bombe ? (ex: B4)");
                                            coordsStr = recupCoords();
                                            if (coordsVerif(coordsStr)) {
                                                coords = coordsInt(coordsStr);
                                                if (coupValideBombPio(coords[0], coords[1])) {
                                                    flag = true;
                                                }
                                            }
                                        } while (!flag);
                                        partie.actionObj(animAlea, "bombe", coords);
                                    }
                                    case "Fusee" -> {
                                        do {
                                            System.out.println("Ou voulez utiliser la Fusee ? (ex: B)");
                                            String coordFus = new Scanner(System.in).next();
                                            coordsStr = new String[]{coordFus, "0"};
                                            if (coordsVerif(coordsStr)) {
                                                coords = coordsInt(coordsStr);
                                                if (coupValideFus(coords[1])) {
                                                    flag = true;
                                                }
                                            }
                                        } while (!flag);
                                        partie.actionObj(animAlea, "fusee", coords);
                                    }
                                    case "Pioche" -> {
                                        do {
                                            System.out.println("Ou voulez utiliser la Pioche ? (ex: B4)");
                                            coordsStr = recupCoords();
                                            if (coordsVerif(coordsStr)) {
                                                coords = coordsInt(coordsStr);
                                                if (coupValideBombPio(coords[0], coords[1])) {
                                                    flag = true;
                                                }
                                            }
                                        } while (!flag);
                                        partie.actionObj(animAlea, "pioche", coords);
                                    }
                                }
                                repOk = true;
                            } else if (rep.equals("n")) {
                                repOk = true;
                                demandeAction(animAlea);
                            } else {
                                System.out.println("Reponse incorrecte");
                            }
                        } while (!repOk);
                    } else {
                        System.out.println("Pas d'objet disponible :(");
                        demandeAction(animAlea);
                    }
                    break;
                }
        }
    }

    // Recupère les coordonnées de la case qui doit être affectée
    public static String[] recupCoords(){
        String coords ;
        String[] coordStr = new String[2];
        do{
            coords = new Scanner(System.in).next();
            if(coords.length() != 2)System.out.println("L'entree n'a pas la bonne taille.");
        }while(coords.length() != 2);
        coordStr[0] = coords.substring(0,1);
        coordStr[1] = coords.substring(1,2);
        return coordStr;
    }

    //Convertis les coordonnées données pour qu'elles soient utilisées
    public static int[] coordsInt(String[] coords){
        int[] intab = new int[2];
        intab[0] = Integer.parseInt(coords[0]);
        intab[1] = Integer.parseInt(coords[1]);
        return intab;
    }

    //vérifie si les coordonnées sont dans le tableau :
    // si E15 -> regarde si E est ok
    //        -> regarde si 15 est ok
    public static boolean coordsVerif(String[] coords) {
        if (coords.length >2){
            return false;
        } else {
            int longueur = partie.getLvl().getGrid().getLongu();
            int hauteur = partie.getLvl().getGrid().getHaut();
            ArrayList nmbrs = new ArrayList();
            ArrayList letters = new ArrayList();
            for (int i = 0; i < hauteur; i++) nmbrs.add(String.valueOf(i));
            for (int j = 0; j < longueur; j++) letters.add(String.valueOf((char) ('a' + j)));
            if (nmbrs.contains(coords[1]) && letters.contains(coords[0].toLowerCase())) {
                for (int k = 0; k < longueur; k++) {
                    if (letters.get(k).equals(coords[0].toLowerCase())) {
                        coords[0] = Integer.toString(k);
                    }
                }
                String tmp = coords[1];
                coords[1] = coords[0];
                coords[0] = tmp;
                return true;
            } else {
                System.out.println("Les coordonnees ne sont pas valides !");
                return false;
            }
        }
    }

    //robot pour le terminal
    public static void robot(Partie partie){
        int i;
        int j;
        do {
            Random rand = new Random();
            i = rand.nextInt(partie.getLvl().getGrid().gril.length);
            j = rand.nextInt(partie.getLvl().getGrid().gril[0].length);
        }while (!coupValide(i,j));
        int[] coor = new int[]{i,j};
        partie.actionBloc(coor, partie.getLvl().getGrid().aDAnimaux() <5);
    }


    //---------------------------------------------------------
    //                   --- PARTIE 2 ---                     -
    //            Pour l'interface graphique                  -
    //---------------------------------------------------------


    //lancement de l'interface graphique
    // -> affiche l'ecran d'accueil
    public static void lancement (){
        vueGraph = new AffichageGraphique(new Controleur());
        vueGraph.setVisible(true);
        vueGraph.ecranCo();
    }

    //affiche l'ecran de connexion
    public void connexion(){
        vueGraph.connexion();
    }

    //affiche l'ecran d'inscription
    public void inscription(){
        vueGraph.inscription();
    }

    //affiche le sommaire si la connexion de joueur s'avère possible
    public void seConnecter(String ident, String pswd){
        if (connexionPossible(ident,pswd)){
            Joueur a = Joueur.getJoueur(ident);
            vueGraph.setJoueur(a);
            vueGraph.sommaire();
        }
    }

    //affiche le sommaire si l'inscription est possible
    public void sInscrire(String ident, String pswd, String pswd2){
        if(inscriptionPossible(ident,pswd,pswd2)){
            vueGraph.setJoueur(Joueur.creerJoueur(ident,pswd));
            vueGraph.sommaire();
        }
    }

    //affiche le mode aventure
    public void modeAventure() {
        vueGraph.modeAventure();
    }

    public void modeInfini(Joueur joueur){
        Niveau inf = Niveau.recupNiveau(-1);
        inf.remplir_Grille();
        vueGraph.setNiveau(inf);
        Partie a = new Partie(joueur,inf);
        vueGraph.setPartie(a);
        setPartie(a);
        vueGraph.initGame(inf.getGrid(),a.getScore(),inf.getNb_animaux(),inf.getNb_coup_max(),a.getScoreObj()==1000);
    }

    //affiche les regles
    public void regles(){
        vueGraph.regles();
    }

    public void objetUsed(int x, int y , String obj){
        Grille grid = partie.getLvl().getGrid();
        int panelHaut = vueGraph.getSize().height - vueGraph.getInsets().top;
        int panelLongu = vueGraph.getSize().width;
        int haut = partie.getLvl().getGrid().getHaut();
        int longu = partie.getLvl().getGrid().getLongu();
        int ecartX = (panelLongu-50*longu)/2;
        int ecartY = (panelHaut-50*haut)/2;
        int i = (y - ecartY) / 50;
        int j = (x - ecartX) / 50;
        int[] coords = new int[2];
        coords[0] = i;
        coords[1] = j;
        if ((x > ecartX && x < panelLongu -ecartX) && (y > ecartY && y < panelHaut - ecartY)) {
            if (coupValideObj(obj, i, j)) {
                if (!partie.getLvl().isDecale()) {
                        partie.actionObj(grid.aDAnimaux() < 5, obj.toLowerCase(), coords);
                    } else {
                        partie.actionObj(false, obj.toLowerCase(), coords);
                    }
                partie.scoreObjReset();
                vueGraph.updateGrille(partie.getLvl().getGrid(), partie.getScore(), partie.getAnimRes(), partie.getCoupRes(), partie.getScoreObj() == 1000);
                if (partie.finJeu() != 0) {
                    partie.getJoueur().miseAJour(partie.getJoueur(),partie.getLvl());
                    partie.getLvl().miseAJourScore(partie.getScore(), partie.getJoueur().getNom(), partie.getLvl());
                    vueGraph.finLevel(partie.getLvl(), partie.finJeu());
                }
            }
        }
    }

    //affiche la presentation du niveau si on y a accès
    public void choixLevel(Joueur joueur, int id){
        if (joueur.levelEstPossible(id)) {
            Niveau level = Niveau.recupNiveau(id);
            level.remplir_Grille();
            Partie partie = new Partie(joueur, level);
            vueGraph.setPartie(partie);
            vueGraph.setNiveau(level);
            vueGraph.presentationLevel(level);
        }
    }

    public void blockClicked(int x, int y){
        Modele.Grille grid = partie.getLvl().getGrid();
        int[] coords = new int[2];
        coords[0] = x;
        coords[1] = y;
        if(coupValide(x,y)){
            if (!partie.getLvl().isDecale()) {
                partie.actionBloc(coords, grid.aDAnimaux() < 5);
            } else {
                partie.actionBloc(coords, false);
            }
            vueGraph.setPartie(partie);
            vueGraph.updateGrille(partie.getLvl().getGrid(),partie.getScore(),partie.getAnimRes(),partie.getCoupRes(), partie.getScoreObj() == 1000);
            if (partie.finJeu() != 0) {
                partie.getJoueur().miseAJour(partie.getJoueur(),partie.getLvl());
                partie.getLvl().miseAJourScore(partie.getScore(), partie.getJoueur().getNom(),partie.getLvl());
                vueGraph.finLevel(partie.getLvl(), partie.finJeu());
            }
        }
    }

    public void initGame(Joueur joueur, Niveau niveau){
        Partie a = new Partie(joueur,niveau);
        setPartie(a);
        vueGraph.initGame(a.getLvl().getGrid(),partie.getScore(),partie.getAnimRes(),partie.getCoupRes(), partie.getScoreObj() == 1000);
    }

    //affiche le sommaire
     public void goSommaire(){
         vueGraph.sommaire();
     }


    //gere l'action faite par le robot
    public void actRobot(){
        robot();
        if (partie.finJeu() == 0) {
            vueGraph.updateGrille(partie.getLvl().getGrid(),partie.getScore(),partie.getAnimRes(),partie.getCoupRes(), partie.getScoreObj() == 1000);
        } else {
            vueGraph.finLevel(partie.getLvl(),partie.finJeu());
        }
    }

    //fait l'action du robot
    public void robot(){
        int i;
        int j;
        do {
            Random rand = new Random();
            i = rand.nextInt(partie.getLvl().getGrid().gril.length);
            j = rand.nextInt(partie.getLvl().getGrid().gril[0].length);
        }while (!coupValide(i,j));
        int[] coor = new int[]{i,j};
        partie.actionBloc(coor, partie.getLvl().getGrid().aDAnimaux() <5);
    }

}
