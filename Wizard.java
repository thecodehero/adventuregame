import java.util.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
public class Wizard extends Character {
    //instance variables
    private int magic; 
    private String type;
    
    //constructor
    public Wizard(String initialName,int magic){
        super(initialName,"Wizard",0); 
        this.magic = magic;       
    }  
        
    //getter and setter methods 
    
    //getAttack() method overrides getAttack() method in the superclass Character.
    public int getAttack() {
        return super.getAttack() + magic;
    }      
    
    //setDamage() method overrides setDamage() method in the superclass Character    
    public void setDamage(int damage) {  
        int healing = (int)0.1*damage;
        super.setDamage(damage-healing);
    }
    
          
    //method damageTaken overrides method damageTaken in superclass Character
    public int damageTaken(int damage) {
        int remainHealth = super.damageTaken(damage);
        remainHealth += 0.1*damage;
        if(remainHealth > 100) {
            return 100;
        }
        return remainHealth;
    }    
    
}