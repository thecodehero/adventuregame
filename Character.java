import java.util.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
public class Character implements SaveGame { 
    private String name;  //instant variable representing name of character  //state 
    private int health; //instant variable representing character's health
    private int attack; //instant variable representing character's attack
    private int damage; //instant variable representing character's defence.
    private int armour; 
    private String type;
    
    //constructors
    public Character(String initialName,String type,int armour) {
        this.type = type;
        this.armour = armour;
        name = initialName;   
        health = 100;
        attack = 25;
    } 
    
    //method to save Character 
    public void save(FileWriter writer) throws IOException {}
    
    //returns the type of character.
    public String getCharacterType() {
        return type;
    }
    
    //behaviour
    public void printEnemyAlert() {
        Utility.print("Alert! Enemy opponent encountered");
    }    
    
    //Getter and Setter methods
    public void setName(String newName) {
        name = newName;
    }    
    public String getName() {
        return name;        
    }
    
    public void setHealth(int health) {
        this.health = health;
    }
    
    public void addHealth(int newHealth) { //increase character's overall health
        health += newHealth;
        if(health > 300) { //max health player can have is 300. if over 300 set health to 300.
            health = 300;
        }
    }    
    
    //
    public void subHealth(int Healthlost) { //decrease characters overall health. 
        health -= Healthlost;
    }
    
    //getter method to get health of character
    public int getHealth() {
        return health;
    }
    
    //getter method to get attack of character
    public int getAttack() {
        return attack;
    }
    
    //setter method for armour
    public void setArmour(int newArmour) {
        armour = newArmour;
    }
    
    //getter method for armour
    public int getArmour() {
        return armour;
    }
    
    //method to reduce character health due to damage
    public int damageTaken(int damage) {
        return health - damage;
    }
    
    //method to set the overall damage done to character.
    public void setDamage(int damage) {
        this.damage = damage;
    }
    
    public int getDamage() {        
        return damage;
    }    
    
    public void setAttack(int attack) {
        this.attack = attack;
    }
    
    
    
    
    
    
    
    
}