import java.util.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
public class Knight extends Character { //class Knight is a subclass of superclass NonPlayer, inherits from NonPlayer.
    //instance variables attack and defence.
    private int sword; 
    private String type;
    
    //constructors to initialize instance variables
    public Knight(String initialName,int armour,int sword) { 
        super(initialName,"Knight",armour); 
        this.sword = sword; //initialises sword.
        super.setHealth(super.getHealth()+100);
    }     
           
    
    
    //setter method for sword
    public void setSword(int newSword) {
        sword = newSword;
    }
    
    //getter method for sword
    public int getSword() {
        return sword;
    }   
    
    
    //getAttack method overrides getAttack() in superclass Character
    public int getAttack() { 
        return super.getAttack() + getSword();
    }       
           
    public void setDamage(int damage) {
        super.setDamage(damage - getArmour());
    }
    
    //damageTaken method overrides inherited instance method damageTaken() from superclass Character   
    public int damageTaken(int damage) {
        int remainHealth = super.damageTaken(damage-getArmour());
        if(remainHealth <= 70) {
            super.setAttack(getAttack()-15); //reduces attack level by 15.
        }
        return remainHealth;
    }   
      
       
}