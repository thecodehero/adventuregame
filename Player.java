import java.util.*;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
public class Player extends Character {  //Player is a subclass of Character
    //state
    private int lives; //instance variable for lives    
    private boolean weapon; //instance variable to store weapon status of player
    private String weaponName; //instance variable to store weapon name player is equipped with.
    private int escape; //instance variable to store remaining number of escapes.
    private int enemiesLeft; //instance variable to store remaining enemies left.
    private int weaponID; //id of current weapon equipped.
    private String name; //instance variable for the name of player    
    
    //constructor
    public Player(String initialName,int armour) {
        super(initialName,"Player",0); //inherited from superclass Character
        lives = 2;  //initialise number of lives                
        escape = 12; //initiliase number of escapes left
        weapon = false; //initially player carries no weapon
        weaponName = "none"; //initialises weaponName
        weaponID = 0;
        enemiesLeft = 6; //initialise number of enemies left in current game
    }
    
    //method to save Player
    public void save(FileWriter writer) throws IOException 
    { 
        writer.write(super.getName() + "\n");
        writer.write(super.getHealth() +"\n");
        writer.write(getLives()+"\n");
        writer.write(getEscape()+"\n");
        writer.write(getEnemiesLeft()+"\n");
        writer.write(super.getAttack()+"\n");
        writer.write(getWeaponName()+"\n");
        writer.write(String.valueOf(getWeapon())+"\n");
        writer.write(getWeaponID()+"\n");        
    }
    
    //method to load player
    public void loadPlayer(Scanner reader) throws IOException
    {
        super.setName(reader.next());
        super.setHealth(Integer.parseInt(reader.next()));
        setLives(Integer.parseInt(reader.next()));
        setEscape(Integer.parseInt(reader.next()));
        setEnemiesLeft(Integer.parseInt(reader.next()));
        super.setAttack(Integer.parseInt(reader.next()));
        setWeaponName(reader.next());
        setWeapon(Boolean.parseBoolean(reader.next()));
        setWeaponID(Integer.parseInt(reader.next()));
    }
    
    
    // method to add lives
    public void addLives() {
        lives += 1;
    }
    
    //method to subtract lives
    public void subLives() {
        lives -= 1;
        super.setAttack(getDefaultAttack());
    }
    
    //method to set lives;
    public void setLives(int lives) 
    {
        this.lives = lives;
    }
    
    //setter method to set weapon status of player.
    public void setWeapon(boolean weapon) {
        this.weapon= weapon;
    }
    
    //getter method to get weapon status of player 
    public boolean getWeapon() {
        return weapon;
    }
    
    //getter method to get lives
    public int getLives() {
        return lives;
    } 
        
    //getter method to get number of escapes left..
    public int getEscape() {
        return escape;
    }
    
    public void setEscape(int escape) {
        this.escape = escape;
    }
    
    public void useEscape(int escape) {
        this.escape = escape - 1;
    }    
    
    //method subHealth 
    public void subHealth(int Healthlost) {
        super.subHealth(Healthlost - getArmour());
    }    
    
    
    //method to take damage. Overrides takeDamage method in superclass Character           
    public int damageTaken(int damage) {
        int remainHealth = super.damageTaken(damage-getArmour());
        if(remainHealth <= 20) {
            super.setAttack(getAttack() + 10); //emergency boost attack by 10.
        }
        return remainHealth;
    }
    
    //setter method to set number of enemies left to kill
    public void setEnemiesLeft(int enemiesLeft) {
        this.enemiesLeft = enemiesLeft; 
    }
    
    //getter method to get number of enemies left to kill
    public int getEnemiesLeft() {
        return enemiesLeft;
    }
    
    //getter method to get default attack level for player
    public int getDefaultAttack() {
        return 25;
    }    
    
    //setter and getter methods for weapon name player is equipped with
    public void setWeaponName(String weaponName) {
        this.weaponName = weaponName;
    }
    
    public String getWeaponName() {
        return weaponName;
    }
    
    //method to set weapon ID
    public void setWeaponID(int weaponID) {
        this.weaponID = weaponID;
    }
    //method to get Weapon ID
    public int getWeaponID() {
        return weaponID;
    }
    
}