package Vue;

import Controleur.Controleur;
import Modele.*;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

public class AffichageGraphique extends JFrame {

    private Controleur control;
    private Partie partie;
    private CardLayout cl = new CardLayout();
    private JPanel main = new JPanel(cl);
    private Joueur joueur;
    private Niveau niveau;
    private JPanel jeu;
    private JPanel infoJeu;
    private JButton[][] buttons;
    private int haut;
    private int longu;
    private int[][] drgs = new int[haut][longu];

    public AffichageGraphique(Controleur controleur){
        control = controleur;
        setTitle("Bear Rescue Saga");
        setSize(550,725);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setAlwaysOnTop(true);
    }

    public class ButtObjet extends JButton implements MouseInputListener{

        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }

        @Override
        public void mouseDragged(MouseEvent e) {

        }

        @Override
        public void mouseMoved(MouseEvent e) {

        }
    }


    //---------------------------------------------------------
    //                   --- PARTIE 1 ---                     -
    //                       Setters                          -
    //---------------------------------------------------------


    public void setJoueur(Joueur joueur){
        this.joueur = joueur;
    }

    public void setNiveau(Niveau niveau){
        this.niveau = niveau;
    }

    public void setPartie(Partie partie){this.partie = partie;}

    //---------------------------------------------------------
    //                   --- PARTIE 2 ---                     -
    //                      Affichage                         -
    //---------------------------------------------------------


    //affiche de l'ecran d'accueil
    public void ecranCo(){
        setAlwaysOnTop(false);
        JLabel txt = new JLabel();
        JButton conn = new JButton();
        JButton ins = new JButton();

        JPanel princ = new ImagePanel("Ressources/Fonds/principal.jpg");
        princ.setLayout(new GridBagLayout());

        txt.setText("<html><h1><strong>Rescue All Dragons</strong></h1><hr></html>");
        txt.setForeground(Color.BLACK);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(30,0,0,0);
        princ.add(txt, gbc);

        JPanel butt= new JPanel();
        butt.setOpaque(false);
        butt.setLayout(new GridBagLayout());

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 0, 5, 0);
        conn.setText("Connexion");
        conn.setPreferredSize(new Dimension(150,55));
        conn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                control.connexion();
            }
        });
        butt.add(conn, gbc);

        ins.setText("Incription");
        ins.setPreferredSize(new Dimension(150,55));
        ins.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                control.inscription();
            }
        });
        butt.add(ins, gbc);
        gbc.weighty = 1;
        princ.add(butt,gbc);
        main.add(princ, "accueil");
        getContentPane().add(main);
        main.setBounds(0, 0, 550, 725);
        setVisible(true);
    }

    //affiche de l'ecran de connexion
    public void connexion() {
        JPanel princ = new ImagePanel("Ressources/Fonds/connexion.jpg");
        princ.setLayout(new java.awt.GridBagLayout());

        JLabel txt = new JLabel("<html><h1><strong>Connexion</strong></h1><hr></html>");
        txt.setForeground(Color.BLACK);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.REMAINDER;
        gbc.insets = new Insets(30,0,0,0);
        gbc.gridx = 0;
        princ.add(txt, gbc);

        JPanel butt= new JPanel();
        butt.setOpaque(false);
        butt.setLayout(new GridBagLayout());

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(5, 0, 5, 0);

        JTextField ident = new JTextField("identifiant");
        ident.setPreferredSize(new Dimension(150,30));
        ident.setHorizontalAlignment(JTextField.CENTER);
        ident.setBorder(null);
        gbc.gridy++;
        butt.add(ident,gbc);

        JTextField pswd = new JTextField("mot de passe");
        pswd.setPreferredSize(new Dimension(150,30));
        pswd.setHorizontalAlignment(JTextField.CENTER);
        pswd.setBorder(null);
        gbc.gridy++;
        butt.add(pswd,gbc);

        JButton co = new JButton("Confirmer");
        co.setPreferredSize(new Dimension(150,30));
        co.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                control.seConnecter(ident.getText(),pswd.getText());
            }
        });
        gbc.insets = new Insets(10,0,0,0);
        gbc.gridy++;
        butt.add(co, gbc);

        JButton ins = new JButton("Incription");
        ins.setPreferredSize(new Dimension(150,30));
        ins.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                control.inscription();
            }
        });
        gbc.gridy++;
        butt.add(ins, gbc);
        gbc.weighty = 1;
        princ.add(butt,gbc);

        main.add(princ, "connexion");
        cl.show(main,"connexion");
    }

    //affichage de l'ecran d'inscription
    public void inscription(){
        JPanel princ = new ImagePanel("Ressources/Fonds/connexion.jpg");
        princ.setOpaque(false);
        princ.setLayout(new java.awt.GridBagLayout());


        JLabel txt = new JLabel("<html><h1><strong>Inscription</strong></h1><hr></html>");
        txt.setForeground(Color.BLACK);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.REMAINDER;
        gbc.insets = new Insets(30,0,0,0);
        gbc.gridx=1;
        princ.add(txt, gbc);

        JPanel butt= new JPanel();
        butt.setOpaque(false);
        butt.setLayout(new GridBagLayout());

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 0, 5, 0);

        JTextField ident = new JTextField("identifiant");
        ident.setBorder(null);
        ident.setPreferredSize(new Dimension(150,30));
        ident.setHorizontalAlignment(JTextField.CENTER);
        gbc.gridy++;
        butt.add(ident,gbc);

        JTextField pswd = new JTextField("mot de passe");
        pswd.setBorder(null);
        pswd.setPreferredSize(new Dimension(150,30));
        pswd.setHorizontalAlignment(JTextField.CENTER);
        gbc.gridy++;
        butt.add(pswd,gbc);

        JTextField pswd2 = new JTextField("mot de passe");
        pswd2.setBorder(null);
        pswd2.setPreferredSize(new Dimension(150,30));
        pswd2.setHorizontalAlignment(JTextField.CENTER);
        gbc.gridy++;
        butt.add(pswd2,gbc);

        JButton conf = new JButton("Confirmer");
        conf.setPreferredSize(new Dimension(150,30));
        conf.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                control.sInscrire(ident.getText(),pswd.getText(),pswd2.getText());
            }
        });
        gbc.insets = new Insets(10,0,0,0);
        gbc.gridy++;
        butt.add(conf, gbc);

        JButton ins = new JButton("Connexion");
        ins.setPreferredSize(new Dimension(150,30));
        ins.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                control.connexion();
            }
        });
        gbc.gridy++;
        butt.add(ins, gbc);
        gbc.weighty = 1;
        princ.add(butt,gbc);

        main.add(princ, "inscription");
        cl.show(main,"inscription");
    }

    //affichage du sommaire
    public void sommaire(){
        JPanel princ = new ImagePanel("Ressources/Fonds/sommaire.png");
        princ.setLayout(new GridBagLayout());

        JLabel txt = new JLabel("<html><h1><strong>Sommaire</strong></h1><hr></html>");
        txt.setForeground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(30,0,0,0);
        princ.add(txt, gbc);

        JPanel butt= new JPanel();
        butt.setOpaque(false);
        butt.setLayout(new GridBagLayout());

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        //création des boutons et de leur évènement lié
        JButton av = new JButton("Mode Aventure");
        av.setPreferredSize(new Dimension(150,50));
        av.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                control.modeAventure();
            }
        });

        JButton inf = new JButton("Mode Infini");
        inf.setPreferredSize(new Dimension(150,50));
        inf.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                control.modeInfini(joueur);
            }
        });

        JButton regles = new JButton("Regles du jeu");
        regles.setPreferredSize(new Dimension(150,50));
        regles.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                control.regles();
            }
        });
        gbc.insets = new Insets(0,0,30,0);
        butt.add(av, gbc);
        butt.add(inf,gbc);
        butt.add(regles,gbc);
        gbc.weighty=1;
        princ.add(butt,gbc);

        main.add(princ, "sommaire");
        cl.show(main,"sommaire");
    }

    //affichage du mode aventure
    // -> montre tous les levels
    public void modeAventure() {
        JPanel princ = new ImagePanel("Ressources/Fonds/aventure.png");
        princ.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets = new Insets(30,0,0,0);
        JLabel l = new JLabel("<html><h1><strong>Choix du Niveau</strong></h1><hr></html>");
        l.setForeground(Color.WHITE);
        princ.add(l,gbc);

        gbc.anchor =GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weighty=1;
        princ.add(choixDesLevels(),gbc);

        main.add("AVENTURE",princ);
        cl.show(main,"AVENTURE");
    }

    //retourne un panneau contenant tous les niveaux
    public JPanel choixDesLevels(){
        JPanel levels = new JPanel(new GridBagLayout());
        levels.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20,40,40,40);
        gbc.gridx=0;
        gbc.gridy=0;
        JButton lvl1 = new JButton("Niveau 1");
        lvl1.setPreferredSize(new Dimension(120,60));
        lvl1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                control.choixLevel(joueur,1);
            }
        });
        levels.add(lvl1,gbc);
        gbc.gridx=1;
        gbc.gridy=0;

        JButton lvl2 = new JButton("Niveau 2");
        lvl2.setPreferredSize(new Dimension(120,60));
        lvl2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                control.choixLevel(joueur,2);
            }
        });
        levels.add(lvl2,gbc);
        gbc.gridx=0;
        gbc.gridy=1;

        JButton lvl3 = new JButton("Niveau 3");
        lvl3.setPreferredSize(new Dimension(120,60));
        lvl3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                control.choixLevel(joueur,3);
            }
        });
        levels.add(lvl3,gbc);
        gbc.gridx=1;
        gbc.gridy=1;

        JButton lvl4 = new JButton("Niveau 4");
        lvl4.setPreferredSize(new Dimension(120,60));
        lvl4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                control.choixLevel(joueur,4);
            }
        });
        levels.add(lvl4,gbc);
        gbc.gridx=0;
        gbc.gridy=2;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0,0,40,0);

        JButton lvl5 = new JButton("Niveau 5");
        lvl5.setPreferredSize(new Dimension(120,60));
        lvl5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                control.choixLevel(joueur,5);
            }
        });
        levels.add(lvl5,gbc);
        gbc.gridy = 3;

        JPanel ret  = new JPanel(new BorderLayout());
        JButton retour= new JButton("Retour");
        retour.setPreferredSize(new Dimension(80,40));
        retour.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                control.goSommaire();
            }
        });
        ret.add(retour);
        ret.setOpaque(false);

        levels.add(ret,gbc);
        return levels;
    }

    //affiche de l'ecran de presentation du niveau choisi
    public void presentationLevel(Niveau niveau){
        JPanel princ = new ImagePanel("Ressources/Fonds/presentLevel.png");
        princ.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets = new Insets(30,0,0,0);
        JLabel l = new JLabel("<html><h1><strong>Niveau " + niveau.id + " </strong></h1><hr></html>");
        l.setForeground(Color.WHITE);
        princ.add(l,gbc);

        JPanel third = new JPanel(new GridBagLayout());
        third.setOpaque(false);

        JPanel pres = new JPanel(new GridBagLayout());
        pres.setOpaque(true);
        pres.setBackground(new Color(225,225,225,120));
        JLabel objectif = new JLabel("OBJECTIFS" );
        //objectif.setBorder(BorderFactory.createLineBorder(Color.black));
        objectif.setPreferredSize(new Dimension(100,30));
        objectif.setHorizontalAlignment(SwingConstants.CENTER);
        objectif.setForeground(Color.BLACK);

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10,0,10,0);
        pres.add(objectif,gbc);

        gbc.insets = new Insets(0,0,10,0);
        JLabel numAnimHelp= new JLabel("Dragons a sauver : " + niveau.getNb_animaux());
        //numAnimHelp.setBorder(BorderFactory.createLineBorder(Color.black));
        numAnimHelp.setPreferredSize(new Dimension(150,30));
        numAnimHelp.setHorizontalAlignment(SwingConstants.CENTER);
        numAnimHelp.setForeground(Color.BLACK);

        pres.add(numAnimHelp,gbc);

        if (niveau.getNb_coup_max() != -1){
            gbc.insets = new Insets(0,0,10,0);
            JLabel numCoupMax= new JLabel("Vous aurez " + niveau.getNb_coup_max() + " coups pour finir ce niveau");
            //numCoupMax.setBorder(BorderFactory.createLineBorder(Color.black));
            numCoupMax.setPreferredSize(new Dimension(250,30));
            numCoupMax.setHorizontalAlignment(SwingConstants.CENTER);
            numCoupMax.setForeground(Color.BLACK);
            pres.add(numCoupMax,gbc);
        }

        JPanel play = new JPanel(new BorderLayout());
        JButton demarrer= new JButton("Jouer");
        demarrer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                control.initGame(joueur,niveau);
            }
        });
        demarrer.setPreferredSize(new Dimension(100,50));
        demarrer.setHorizontalAlignment(SwingConstants.CENTER);
        play.add(demarrer);
        play.setOpaque(false);


        JPanel ret  = new JPanel(new BorderLayout());

        JButton retour= new JButton("Retour");
        retour.setPreferredSize(new Dimension(80,40));
        retour.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                control.modeAventure();
            }
        });
        ret.add(retour);
        ret.setOpaque(false);

        GridBagConstraints abc = new GridBagConstraints();
        abc.insets = new Insets(0,0,20,0);
        abc.gridy = 1;
        third.add(pres,abc);
        abc.gridy = 2;
        third.add(play,abc);
        abc.insets = new Insets(0,0,40,0);
        abc.gridy = 3;
        third.add(ret,abc);

        gbc.weighty = 1;
        princ.add(third,gbc);

        main.add("PRESENTATION",princ);
        cl.show(main,"PRESENTATION");
    }

    //affichage de l'ecran de fin de niveau
    public void finLevel(Niveau niveau, int finJeu){
        JPanel princ = new ImagePanel("Ressources/Fonds/presentLevel.png");
        princ.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets = new Insets(30,0,0,0);
        JLabel l = new JLabel("<html><h1><strong>Niveau " + niveau.id + "</strong></h1><hr></html>");
        l.setForeground(Color.WHITE);
        princ.add(l,gbc);

        JPanel third = new JPanel(new GridBagLayout());
        third.setOpaque(false);

        JPanel mess = new JPanel(new GridBagLayout());
        mess.setOpaque(true);

        gbc.insets = new Insets(20,10,20,10);
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel info = new JLabel();
        info.setForeground(Color.BLACK);
        info.setHorizontalAlignment(SwingConstants.CENTER);
        if(finJeu == 2) {
            info.setText("Bravo, vous avez gagne !");
            mess.setBackground(new Color(144, 232, 125,200));
        } else {
            info.setText("Dommage, vous avez echoue.");
            mess.setBackground(new Color(229, 116, 116,200));
        }
        mess.add(info,gbc);

        JPanel pres = new JPanel(new GridBagLayout());
        pres.setOpaque(true);
        pres.setBackground(new Color(225,225,225,120));

        gbc.insets = new Insets(10,0,10,0);
        int compt = 1;
        for (var s : niveau.best_score.entrySet()){
            JLabel infoScore = new JLabel(compt +" : " + s.getValue() +" -> " + s.getKey() + " points");
            //infoScore.setBorder(BorderFactory.createLineBorder(Color.black));
            infoScore.setPreferredSize(new Dimension(200,20));
            infoScore.setHorizontalAlignment(SwingConstants.CENTER);
            infoScore.setForeground(Color.BLACK);
            gbc.gridy=compt+1;
            pres.add(infoScore,gbc);
            compt++;
        }
        while (compt <6){
            JLabel infoScore = new JLabel(compt + " : Pas encore de meilleur score");
            infoScore.setPreferredSize(new Dimension(200,20));
            infoScore.setHorizontalAlignment(SwingConstants.CENTER);
            infoScore.setForeground(Color.BLACK);
            gbc.gridy=compt+1;
            pres.add(infoScore,gbc);
            compt++;
        }

        JPanel play = new JPanel(new BorderLayout());
        JButton next= new JButton("Suivant");
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                control.modeAventure();
            }
        });
        next.setBorder(BorderFactory.createLineBorder(Color.black));
        next.setPreferredSize(new Dimension(100,40));
        next.setHorizontalAlignment(SwingConstants.CENTER);
        play.add(next);
        play.setOpaque(false);


        GridBagConstraints abc = new GridBagConstraints();
        abc.insets = new Insets(0,0,60,0);
        abc.gridy = 1;
        third.add(mess,abc);
        abc.gridy = 2;
        third.add(pres,abc);
        abc.gridy = 3;
        third.add(play,abc);
        abc.insets = new Insets(0,0,40,0);

        gbc.weighty = 1;
        princ.add(third,gbc);

        main.add("FIN_LEVEL",princ);
        cl.show(main,"FIN_LEVEL");
    }

    public void updateGrille(Grille grid, int score, int animRes,int coupRes, boolean objetAvail){
        jeu.removeAll();
        String color;
        Icon icon;
        JButton butt;
        buttons = new JButton[haut][longu];
        JPanel big = new JPanel();
        big.setOpaque(false);
        big.setMinimumSize(new Dimension(550,490));
        big.setLayout(new GridBagLayout());
        infoJeu = new JPanel(new GridLayout(haut,longu));
        infoJeu.setOpaque(false);
        GridBagConstraints gridC = new GridBagConstraints();
        gridC.fill = GridBagConstraints.HORIZONTAL;
        gridC.gridx = 0;
        if (niveau.id == -1){
            jeu.add(etatJeuInf(score,animRes),gridC);
        } else {
            jeu.add(etatJeu(score, animRes, coupRes), gridC);
        }
        for(int i = 0; i<haut; i++){
            for(int j = 0; j<longu; j++){
                butt = new JButton();
                switch (grid.gril[i][j].getIs()) {
                    case 'a':
                        color = "dragon" + ((Dragon)grid.gril[i][j]).getWhich();
                        break;
                    case '2':
                        color = grid.gril[i][j].getColor() + "00";
                        break;
                    case ' ':
                    case 's':
                        color = "";
                        break;
                    default:
                        color = String.valueOf(grid.gril[i][j].getIs());
                        break;
                }
                butt.setContentAreaFilled(false);
                butt.setOpaque(false);
                butt.setBorder(null);
                icon = new ImageIcon("Ressources/Blocs/"+color+".png");
                butt.setIcon(icon);
                butt.setPreferredSize(new Dimension(50,50));
                buttons[i][j] = butt;
                infoJeu.add(butt);
            }
        }
        addAllListeners();
        infoJeu.setMinimumSize(new Dimension(50*longu,50*haut));
        big.setOpaque(false);
        big.add(infoJeu,new GridBagConstraints());
        jeu.add(big, gridC);
        gridC.anchor = GridBagConstraints.SOUTH;
        if(niveau.id == -1){
            jeu.add(infoObjetsInf(objetAvail),gridC);
        } else {
            jeu.add(infoObjets(objetAvail), gridC);
        }
        main.add("game",jeu);
        cl.show(main,"game");
    }

    public void initGame(Grille grid ,int score, int animRes,int coupRes, boolean objetAvail){
        haut = niveau.getGrid().getHaut();
        longu = niveau.getGrid().getLongu();
        infoJeu = new JPanel(new GridLayout(haut, longu,10,10));
        jeu = new ImagePanel("Ressources/Fonds/game.png");
        jeu.setLayout(new GridBagLayout());
        buttons = new JButton[haut][longu];
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        if (niveau.id == -1) {
            jeu.add(etatJeuInf(score, animRes), gbc);
        } else {
            jeu.add(etatJeu(score, animRes, coupRes), gbc);
        }

        String color = "";
        Icon icon;
        JButton butt;
        Random rand = new Random();
        buttons = new JButton[haut][longu];

        JPanel big = new JPanel();
        big.setMinimumSize(new Dimension(550,490));
        big.setLayout(new GridBagLayout());
        infoJeu = new JPanel(new GridLayout(haut,longu));
        for(int i = 0; i<haut; i++){
            for(int j = 0; j< longu; j++){
                butt = new JButton();
                switch (grid.gril[i][j].getIs()) {
                    case 'a':
                        color = "dragon" + ((Dragon)grid.gril[i][j]).getWhich();
                        break;
                    case ' ':
                    case 's':
                        color = "";
                        break;
                    default:
                        color = String.valueOf(grid.gril[i][j].getIs());
                        break;
                }
                icon = new ImageIcon("Ressources/Blocs/" +color+".png");
                butt.setContentAreaFilled(false);
                butt.setOpaque(false);
                butt.setBorder(null);
                butt.setIcon(icon);
                butt.setPreferredSize(new Dimension(50,50));
                buttons[i][j] = butt;
                infoJeu.add(butt);
            }
        }
        addAllListeners();
        infoJeu.setMinimumSize(new Dimension(50*longu,50*haut));
        infoJeu.setOpaque(false);
        big.setOpaque(false);
        big.add(infoJeu,new GridBagConstraints());
        jeu.add(big,gbc);
        if (niveau.id == -1){
            jeu.add(infoObjetsInf(objetAvail),gbc);
        }else {
            jeu.add(infoObjets(objetAvail), gbc);
        }
        main.add("game",jeu);
        cl.show(main,"game");
    }

    public JPanel infoObjets(boolean objetAvail){
        JPanel panel = new ImagePanel("Ressources/Fonds/bag.jpg");
        panel.setLayout(new GridBagLayout());
        panel.setVisible(true);
        GridBagConstraints gbc1 = new GridBagConstraints();
        String str = niveau.getObjDispo();

        JButton rob = new JButton("Robot");
        rob.setPreferredSize(new Dimension(70,70));
        rob.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                control.actRobot();
            }
        });
        if (!str.equals("")) {
            ButtObjet button = new ButtObjet();
            String finalStr = str;
            button.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {

                }

                @Override
                public void mousePressed(MouseEvent e) {
                    Toolkit toolkit = Toolkit.getDefaultToolkit();
                    Image image = toolkit.getImage("Ressources/Objets/"+ finalStr +".png");
                    Cursor c = toolkit.createCustomCursor(image, new Point(main.getX(), main.getY()), "img");
                    main.setCursor(c);
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    main.setCursor(Cursor.getDefaultCursor());
                    if(objetAvail) {
                        control.objetUsed(e.getXOnScreen() - getX(), e.getYOnScreen() - getY(), finalStr);
                    }
                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            });
            button.setPreferredSize(new Dimension(70, 70));
            if(!objetAvail) {
                str += "Locked";
            }

            button.setIcon(new ImageIcon("Ressources/Objets/" + str + ".png"));
            gbc1.anchor = GridBagConstraints.CENTER;
            gbc1.insets = new Insets(0, 0, 0, 60);
            panel.add(button, gbc1);
        }
        else {
            JLabel button = new JLabel("Pas d'Objet");
            button.setPreferredSize(new Dimension(70, 70));
            button.setIcon(new ImageIcon("Ressources/Objets/" + str + ".png"));
            gbc1.anchor = GridBagConstraints.CENTER;
            gbc1.insets = new Insets(0, 0, 0, 60);
            panel.add(button, gbc1);
        }

        gbc1.gridx = 2;
        gbc1.insets = new Insets(0, 80, 0, 0);
        panel.add(rob, gbc1);
        panel.setBorder(new javax.swing.border.BevelBorder(BevelBorder.RAISED));
        setVisible(true);
        return panel;
    }

    public JPanel infoObjetsInf(boolean objetAvail){
        JPanel panel = new ImagePanel("Ressources/Fonds/bag.jpg");
        panel.setLayout(new GridBagLayout());
        panel.setVisible(true);
        GridBagConstraints gbc1 = new GridBagConstraints();
        JButton rob = new JButton("Robot");
        rob.setPreferredSize(new Dimension(70,70));
        rob.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                control.actRobot();
            }
        });
            ArrayList<String> a = new ArrayList<String>(); a.add("Fusee");a.add("Bombe");a.add("Pioche");
            for (int i =0;i<3;i++) {
                ButtObjet button = new ButtObjet();
                String finalStr = a.get(i);
                String finalStr1 = finalStr;
                button.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {

                    }
                    @Override
                    public void mousePressed(MouseEvent e) {
                        Toolkit toolkit = Toolkit.getDefaultToolkit();
                        Image image = toolkit.getImage("Ressources/Objets/" + finalStr1 + ".png");
                        Cursor c = toolkit.createCustomCursor(image, new Point(main.getX(), main.getY()), "img");
                        main.setCursor(c);
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                        main.setCursor(Cursor.getDefaultCursor());
                        if (objetAvail) {
                            control.objetUsed(e.getXOnScreen() - getX(), e.getYOnScreen() - getY(), finalStr1);
                        }
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {

                    }

                    @Override
                    public void mouseExited(MouseEvent e) {

                    }
                });
                button.setPreferredSize(new Dimension(70, 70));
                if (!objetAvail) {
                    finalStr += "Locked";
                }

                button.setIcon(new ImageIcon("Ressources/Objets/" + finalStr + ".png"));
                gbc1.anchor = GridBagConstraints.CENTER;
                gbc1.insets = new Insets(0, 0, 0, 10);
                panel.add(button, gbc1);
            }

        gbc1.insets = new Insets(0, 30, 0, 0);
        panel.add(rob, gbc1);
        panel.setBorder(new javax.swing.border.BevelBorder(BevelBorder.RAISED));
        setVisible(true);
        return panel;
    }

    public JPanel etatJeu(int score, int animRes, int coupRes){
        JPanel etat = new JPanel();
        etat.setLayout(new GridBagLayout());
        etat.setMinimumSize(new Dimension(550, 100));
        etat.setBackground(new Color(0, true));
        etat.setBorder(new javax.swing.border.BevelBorder(BevelBorder.RAISED));
        GridBagConstraints gb = new GridBagConstraints();
        JLabel scoreLabel = new JLabel();
        scoreLabel.setFont(new java.awt.Font("Arial", Font.BOLD, 18));
        if(score == 0){
            scoreLabel.setText("000000");
        } else {
            String str = Integer.toString(score);
            String tmp = "";
            for (int i = 0; i < 6 - str.length(); i++) {
                tmp = tmp + "0";
            }
            tmp += str;
            scoreLabel.setText(tmp);
        }
        scoreLabel.setForeground(Color.WHITE);

        JPanel scoree = new JPanel(new GridBagLayout());
        scoree.setBackground(new Color(0x66000000, true));
        scoree.setMinimumSize(new Dimension(100,60));
        scoree.setPreferredSize(new Dimension(200,60));
        scoree.add(scoreLabel,new GridBagConstraints());

        JPanel animm = new JPanel(new GridBagLayout());
        animm.setMinimumSize(new Dimension(100,60));
        animm.setBackground(new Color(0x66000000, true));
        JLabel tete = new JLabel(new ImageIcon("Ressources/Fonds/litleDrag.png"));
        JLabel anim = new JLabel((niveau.getNb_animaux()) - animRes +" / " + niveau.getNb_animaux());
        anim.setFont(new java.awt.Font("Arial", Font.BOLD, 18));
        anim.setHorizontalAlignment(SwingConstants.CENTER);
        animm.setPreferredSize(new Dimension(200,60));
        anim.setForeground(Color.WHITE);
        animm.add(tete,new GridBagConstraints());
        animm.add(anim,new GridBagConstraints());

        JPanel coupp = new JPanel(new GridBagLayout());
        coupp.setMinimumSize(new Dimension(100,60));
        coupp.setBackground(new Color(0x66000000, true));
        JLabel coup = new JLabel();
        coup.setMinimumSize(new Dimension(100,60));
        coup.setBackground(new Color(0x66000000, true));
        coup.setForeground(Color.WHITE);
        coup.setFont(new java.awt.Font("Arial", Font.BOLD, 18));
        if (coupRes<0){
            coup.setText("-");
        } else {
            coup.setText(String.valueOf(coupRes));
        }
        coupp.add(coup,new GridBagConstraints());
        gb.insets = new Insets(0,0,0,80);
        etat.add(animm,gb);
        gb.insets = new Insets(0,40,0,40);
        etat.add(coupp,gb);
        gb.insets = new Insets(0,40,0,0);
        etat.add(scoree, gb);
        return etat;
    }

    public JPanel etatJeuInf(int score, int animRes){
        JPanel etat = new JPanel();
        etat.setLayout(new GridBagLayout());
        etat.setMinimumSize(new Dimension(550, 100));
        etat.setBackground(new Color(0, true));
        etat.setBorder(new javax.swing.border.BevelBorder(BevelBorder.RAISED));
        GridBagConstraints gb = new GridBagConstraints();
        JLabel scoreLabel = new JLabel();
        scoreLabel.setFont(new java.awt.Font("Arial", Font.BOLD, 18));
        if(score == 0){
            scoreLabel.setText("000000");
        } else {
            String str = Integer.toString(score);
            String tmp = "";
            for (int i = 0; i < 6 - str.length(); i++) {
                tmp = tmp + "0";
            }
            tmp += str;
            scoreLabel.setText(tmp);
        }
        scoreLabel.setForeground(Color.WHITE);

        JPanel scoree = new JPanel(new GridBagLayout());
        scoree.setBackground(new Color(0x66000000, true));
        scoree.setMinimumSize(new Dimension(100,60));
        scoree.setPreferredSize(new Dimension(200,60));
        scoree.add(scoreLabel,new GridBagConstraints());

        JPanel animm = new JPanel(new GridBagLayout());
        animm.setMinimumSize(new Dimension(100,60));
        animm.setBackground(new Color(0x66000000, true));
        JLabel tete = new JLabel(new ImageIcon("Ressources/Fonds/litleDrag.png"));
        JLabel anim = new JLabel(String.valueOf(-animRes-1));
        anim.setFont(new java.awt.Font("Arial", Font.BOLD, 18));
        anim.setHorizontalAlignment(SwingConstants.CENTER);
        animm.setPreferredSize(new Dimension(200,60));
        anim.setForeground(Color.WHITE);
        animm.add(tete,new GridBagConstraints());
        animm.add(anim,new GridBagConstraints());

        gb.insets = new Insets(0,0,0,80);
        etat.add(animm,gb);
        gb.insets = new Insets(0,80,0,0);
        etat.add(scoree, gb);
        return etat;
    }



    public void addAllListeners(){
        for(int i = 0; i<haut; i++){
            for(int j = 0; j<longu; j++){
                int x = i;
                int y = j;

                buttons[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        control.blockClicked(x,y);
                    }
                });
            }
        }
    }

    //affichage des règles
    public void regles(){
        JPanel princ = new ImagePanel("Ressources/Fonds/regles.jpg");
        princ.setOpaque(false);
        princ.setLayout(new GridBagLayout());

        GridBagConstraints gbc= new GridBagConstraints();
        gbc.insets = new Insets(0,0,30,0);
        gbc.gridy =0;
        JLabel l = new JLabel("<html><h1><strong>Regles</strong></h1><hr></html>");
        l.setForeground(Color.WHITE);
        princ.add(l,gbc);
        gbc.gridy = 1;
        JLabel regles = new JLabel("<html><pre>" +
                "     Rescue All Dragons est un jeu de Reflexion    "+
                "<br>" +
                "<br>" +
                "                 Votre Objectif :                  "+
                "<br>"+
                "           Sauver tous les dragons perdus          " +
                "<br>" +
                "<br>" +
                "Pour y parvenir, vous devrez :                     " +
                "<br>" +
                "  - Detruire des blocs de meme materiaux (min 2)   " +
                "<br>" +
                "  - Amenez les dragons au sol pour les sauver      " +
                "<br>" +
                "  - Faire attention aux nombre de coups maximum    " +
                "<br>" +
                "<br>" +
                "La partie est terminee lorsque :                   " +
                "<br>" +
                "  - Vous ne pouvez plus detruire de blocs          " +
                "<br>" +
                "  - Vous avez effectue tous les coups disponibles  " +
                "<br>" +
                "  - Vous avez sauve tous les dragons          " +
                "<br>" +
                "</pre></html>");
        regles.setForeground(Color.BLACK);
        regles.setOpaque(true);
        regles.setFont(new java.awt.Font("Gill Sans MT", Font.BOLD, 14));
        regles.setBackground(new Color(255,255,255,100));
        regles.setHorizontalAlignment(JLabel.CENTER);
        regles.setPreferredSize(new Dimension(450,350));
        regles.setBorder(BorderFactory.createLineBorder(Color.black));
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0,0,0,0);
        princ.add(regles,gbc);
        gbc.gridy = 2;
        gbc.insets = new Insets(20,0,0,0);
        JButton retour= new JButton("Retour");
        retour.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                control.goSommaire();
            }
        });
        retour.setBorder(BorderFactory.createLineBorder(Color.black));
        retour.setPreferredSize(new Dimension(100,40));
        retour.setHorizontalAlignment(SwingConstants.CENTER);
        princ.add(retour,gbc);

        main.add(princ, "REGLES");
        cl.show(main,"REGLES");
    }

}
