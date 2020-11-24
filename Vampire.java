import java.util.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
public class Vampire extends Character {
    private int claws; //overrides attack
    private int fangs; //overrides attack
    private int thickskin; //overrides defence
    private String type;
    
    public Vampire(String initialName,int claws,int fangs) {
        super(initialName,"Vampire",0);
        this.claws = claws;
        this.fangs = fangs;
        thickskin = 8;        
    }
    
    
    
    //getter and setter methods     
    public int getAttack() {
        return super.getAttack() + claws + fangs;        
    }     
    
    //method to set overall damage taken by vampire. setDamage overrides setDamage in superclass Character
    public void setDamage(int damage) {
        super.setDamage(damage-thickskin);
    }    
    
    public int damageTaken(int damage) {
        int remainHealth = super.damageTaken(damage-thickskin);
        if(remainHealth <= 70) {
            super.setAttack(25); //sets attacks to 25 if health is 70 or less.
        }
        return remainHealth;
    }
    
    //getter method for fangs
    public int getFangs() {
        return fangs;
    }
    
    //getter method for claws
    public int getClaws() {
        return claws;
    }
    
    //getter method for thickskin 
    public int getThickSkin() {
        return thickskin;
    }
}