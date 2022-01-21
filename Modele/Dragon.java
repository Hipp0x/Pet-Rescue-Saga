package Modele;

import java.util.Random;

public class Dragon extends Case{
    private int which;


    public Dragon() {
        super('a');
        Random rand = new Random();
        which = rand.nextInt(5);
    }

    public Dragon(int which){
        super('a');
        Random rand = new Random();
        this.which = which;
    }

    public int getWhich(){
        return which;
    }
}
