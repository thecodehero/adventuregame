import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Weapon extends Item {  //subclass Weapon inherits from super class Item.  
    private int extraAttack; //instance variable to store the amount to increase attack level of player.
    private int attacksLeft; //instance variable for storing durability of weapon. Number of times can be used in battle.
    private String weaponType; //instance variable to store weaponType;
    private String weaponKey; //stores key to press to select weapon.
    private int weaponID; //instance variable to store unique number to represent weapon.
    private BufferedReader reader;
    //Each Weapon will take up different amounts of space in inventory.
    
    //weapon constructor
    public Weapon(Player player,String itemName,int extraAttack,String weaponKey,int weaponID,int takeSpace) { //constructor
        super(player,itemName,takeSpace); //inherited from superclass Item     
        this.extraAttack = extraAttack; //initialises extra attack        
        this.weaponKey = weaponKey; //initialises weapon key.
        this.weaponID = weaponID; 
        this.reader = reader;
        attacksLeft = 3;        
         
    }
    
    //method to save item
    public void save(FileWriter writer) throws IOException 
    {    
        super.save(writer);
        writer.write(getAttacksLeft() + "\n"); //save number of attacks left for weapon        
    }
        
    
    //method to load weapon
    public void loadItem(Scanner reader) throws IOException
    {
        super.setQuantity(Integer.parseInt(reader.next()));
        attacksLeft = Integer.parseInt(reader.next());         
    }
    
    //getter and setter methods for weapon
    
    //method to equip weapon. overrides method in super class Item.
    public void useItem(InventorySpace inventoryspace) { 
        
        if(super.getQuantity() > 0 && player.getWeapon() == false)  {            
            player.setAttack(player.getAttack()+extraAttack); //increase player's attack by level of weapon.
            if(getAttacksLeft() <= 0) {
                setAttacksLeft(3);
            }            
            
            player.setWeapon(true); //player is equipped with a weapon
            player.setWeaponName(super.getItemName());
            player.setWeaponID(weaponID);
            
        }        
    }  
    
    public void dropItem(InventorySpace inventoryspace)
    {
        super.dropItem(inventoryspace);
        inventoryspace.setTotalInventory(inventoryspace.getTotalInventory() - super.getTakeSpace());
        if(getQuantity() <= 0 && inventoryspace.getTotalInventory() <= 0) {
            super.setQuantity(0);
            inventoryspace.setTotalInventory(0);            
        }
    }
    
           
    //method to unequip weapon from player
    public void unequipWeapon() {
        player.setAttack(player.getDefaultAttack());
        player.setWeaponName("none");
        player.setWeapon(false); //player is equipped with no weapons.
        player.setWeaponID(0);
    }
    
    public void setAttacksLeft(int attacksLeft) {
       this.attacksLeft = attacksLeft;
    }
    
    public int getAttacksLeft() {
        return attacksLeft;
    }
    
    //method to return update attack level.
    public String UpdateStats() {
        return "Attack Level: " + player.getAttack();
    }
    
       
        
    
}