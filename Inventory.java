import java.util.ArrayList; //import arraylist class.
import java.awt.*; //imports awt package
import java.awt.event.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.util.*;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Inventory implements SaveGame  { //class for containing the player's inventory.       
    private Item healthpotion; //instance variable for potion healthpotion;
    private Item lifepotion; //instance variable for potion lifepotion
    private Item sword; //instance variable for weapon sword
    private Item mace; //instance variable for weapon mace
    private Item axe; //instance variable for weapon axe
    private Item gun; 
    private Item machineGun;
    private Item staminapotion;
    private Item[] items;
    private InventorySpace inventoryspace;
    private int i;        
    
    private Player player;
    
            
    public Inventory(Player player) {         
        this.player = player;
        
        
        inventoryspace = new InventorySpace();
        
        
        //substitution principle. Healthpotion, LifePotion and Weapon are subtypes of Item type. 
        items = new Item[8]; 
        //polymorphic array. 
        items[0] = new HealthPotion(player,"Health Potion");
        items[1] = new LifePotion(player,"Life Potion");
        items[2] = new StaminaPotion(player,"Stamina Potion");
        items[3] = new Weapon(player,"Sword",14,"3",3,1);
        items[4] = new Weapon(player,"Mace",20,"4",4,1);
        items[5] = new Weapon(player,"Axe",26,"5",5,2);
        items[6] = new Weapon(player,"Gun",39,"6",6,2);
        items[7] = new Weapon(player,"MachineGun",75,"7",7,4);       
                                 
    }
    
    //method to save inventory.
    public void save(FileWriter writer) throws IOException 
    {
        for(int i = 0; i < 8; i++) 
        {
            items[i].save(writer);
        }             
    }  
    
    //method to load inventory.
    public void loadInventory(Scanner reader) throws IOException
    {
        for(int i = 0; i < 8; i++)
        {
            items[i].loadItem(reader);
        }
    }
    
    //method to display inventory options
    public void inventoryOptions(Item[] items) 
    {   
           Frame f = new GUIInventory(items,inventoryspace,player);   
    }    
    
    //getter method to access items from inventory
    public Item[] getItems() {
        return items;
    }
    
    //setter method to set items in inventory 
    public void setItems(Item[] items) {
        this.items = items;
    } 
    
    //set inventoryspace
    public void setInventorySpace(InventorySpace inventoryspace)
    {
        this.inventoryspace = inventoryspace;
    }
        
    //get inventoryspace
    public InventorySpace getInventorySpace() {
        return inventoryspace;
    }
        
    
    //method when method is used in battle 
    public boolean weaponBattle(InventorySpace inventoryspace,Item[] items) 
    {
        int equippedWeapon = player.getWeaponID();
        if(player.getWeapon()) 
        {
           items[equippedWeapon].setAttacksLeft(items[equippedWeapon].getAttacksLeft()-1);
           if(items[equippedWeapon].getAttacksLeft() <= 0) 
           {            
                items[equippedWeapon].unequipWeapon();                         
                items[equippedWeapon].setQuantity(items[equippedWeapon].getQuantity()-1);
                items[equippedWeapon].dropItem(inventoryspace);                
                return true;
           }
        }          
        return false;
    }  
    
    
    
    
}