import java.util.*;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
public class HealthPotion extends Potion { //Healthpotion is a subclass of superclass Potion
    private int extraHealth;      
    
    public HealthPotion(Player player,String ItemName) {
        super(player,"Health Potion",0);
        extraHealth = 50;                
    }
    
    //method to save health potion
    public void save(FileWriter writer) throws IOException 
    {               
        super.save(writer);      
    }
    
    //method to load item
    public void loadItem(Scanner reader) throws IOException
    {
        super.setQuantity(Integer.parseInt(reader.next()));
        super.setPotionUnits(Integer.parseInt(reader.next()));        
    }
    
    //method overrides useItem in superclass Item. Dynamic Binding.
    public void useItem(InventorySpace inventoryspace) { //instance method for using health potion.        
             super.useItem(inventoryspace);   
             player.addHealth(extraHealth);       
         }      
        
    //method to display health stats
     public String UpdateStats() {
        return "Player Health Left: " + player.getHealth();
    }
}