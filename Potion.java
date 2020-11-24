import java.util.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
public class Potion extends Item {   
    private int potionUnits; //instance for storing amount of units in potion
      
    public Potion(Player player,String itemName,int takeSpace) { //constructor
        super(player,itemName,takeSpace);      
        potionUnits = 0;
    }  
    
    //method to save potion
    public void save(FileWriter writer) throws IOException 
    {
        super.save(writer);
        writer.write(getPotionUnits()+"\n"); //save potion units
    };
    
    //method to add potion discovered.
    public void addItem(InventorySpace inventoryspace) 
    {                    
                super.addItem(inventoryspace);
                if(super.getQuantity()==1) //sets potion units to 100 first potion for potion type is added in inventory.
                {
                    potionUnits = 100;
                }
    }
    
    //method to use item. Overrides useItem in superclass Item.
    public void useItem(InventorySpace inventoryspace) {
        potionUnits = potionUnits - 50;
        if(potionUnits <= 0) 
        {
            super.useItem(inventoryspace);
            potionUnits = 100;
            if(super.getQuantity()<=0)
            {
                potionUnits = 0;
            }
        }        
    }      
    
    //method to drop item
    public void dropItem(InventorySpace inventoryspace) {
        super.dropItem(inventoryspace);
        if(super.getQuantity() >= 0) 
        {
            potionUnits = 100;
        }        
    }
    
    //method to return potionUnits
    public int getPotionUnits() 
    {
        return potionUnits;
    }
    
    //method to set potionUnits
    public void setPotionUnits(int potionUnits)
    {
        this.potionUnits = potionUnits;
    }
           
      
}
    
    