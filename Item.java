import java.util.*;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
public class Item implements SaveGame { //item  superclass
    private int quantity;  //declare instance variable to store quantiy of item.
   
    protected Player player; //declare instance variable to hold player object.    
    //private final int maxQuantity = 6; //stores the max number of items that player can carry in inventory
    private String itemName; //instance variable to store name of item
    private int takeSpace; //instance variable to store amount of space item takes in inventory.
    
    //item constructor
    public Item(Player player,String itemName,int takeSpace) { //constructor 
        this.itemName = itemName;
        this.quantity = 0;
        this.player = player;     
        this.takeSpace = takeSpace; //space taken is equal to take space
    }
    
    //method to save item
    public void save(FileWriter writer) throws IOException 
    {
        writer.write(getQuantity() + "\n");        
    }
    
    //method to load item
    public void loadItem(Scanner reader) throws IOException {}
    
    //setter method to set quantity of item
    public void setQuantity(int quantity) { 
        this.quantity = quantity;
        if(this.quantity <= 0) {
            this.quantity = 0;
        }
    }
    
    //getter method to set quantiy of class 
    public int getQuantity() {
        return quantity;
    }
    
        //method to get space Item takes in inventory
    public int getTakeSpace() 
    {
        return takeSpace;
    }
     
    //method to get potionUnits
    public int getPotionUnits() { return 0;}
      
          
    //method to use item and reduce quantity by 1.
    public void useItem(InventorySpace inventoryspace) {
        quantity = quantity - 1;
        inventoryspace.subTotalInventory(); 
        int currentInventory = inventoryspace.getTotalInventory();
        inventoryspace.setTotalInventory(currentInventory - takeSpace);
        
    }
    
    //method to check if item quantity is 0
    public boolean zeroItem(InventorySpace inventoryspace) {
        if(quantity <= 0) {
            quantity = 0;            
            return false;
        }
        return true;
    }
    
    //method to check if no space left in inventory
    public boolean isMaxCapacity(InventorySpace inventoryspace)
    {
        int totalSpaceUsed = inventoryspace.getTotalInventory() + 1 + takeSpace;
        int maxInventory = inventoryspace.getMaxInventory();
        if(totalSpaceUsed <= maxInventory)
        {
            return true;
        }
        else 
        {
            return false;
        }
    }
    
    
    //method to add item discovered
    public void addItem(InventorySpace inventoryspace) 
    {                    
                inventoryspace.addTotalInventory();
                int currentInventory = inventoryspace.getTotalInventory();
                inventoryspace.setTotalInventory(currentInventory + takeSpace);
                quantity = quantity + 1;           
    }
    
    //method to drop item and remove from inventory.
    public void dropItem(InventorySpace inventoryspace) {
        quantity = quantity - 1;
        inventoryspace.subTotalInventory();    
        if(getQuantity() <= 0 && inventoryspace.getTotalInventory() <= 0) {
            quantity = 0;
            inventoryspace.setTotalInventory(0);            
        }
    }
    
    /*
    public void printOptions(){}; 
    */
    public String getOptions(int i)
    {
        int totalSpaceTaken = getQuantity() * (takeSpace+1);
        return "Type " + i + " -> " + getItemName() + "; " + + getQuantity() + " remaining; Takes Space: " + totalSpaceTaken;
    }; 
           
       
    //method to check if weapon already equipped
    public boolean weaponEquipped() 
    {
        return player.getWeapon();
    }  
    
       
    //method to get item name 
    public String getItemName(){
        return itemName;
    }; 
    
    public void setAttacksLeft(int attacksLeft) {}
    
    public int getAttacksLeft() {
        return 0;
    }
    
    public String UpdateStats() {
        return "Player stats:";
    }
    
    //unequipWeapon for when there is weapon
    public void unequipWeapon() {}; 
    
}