import java.util.*;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
public class LifePotion extends Potion {
    
    public LifePotion(Player player,String ItemName) {
        super(player,"Life Potion",0);
    }
    
    //method to save life potion
    public void save(FileWriter writer) throws IOException 
    {       
        super.save(writer);
    };
    
    //method to load life potion
    public void loadItem(Scanner reader) throws IOException
    {
        super.setQuantity(Integer.parseInt(reader.next()));
        super.setPotionUnits(Integer.parseInt(reader.next()));        
    }
    
    
    //method overrides useItem in superclass Item. Dynamic Binding.
    public void useItem(InventorySpace inventoryspace) { //instance method for using health potion.
             super.useItem(inventoryspace);
             player.addLives();            
    }   
       
        
            
    //method to display lives stats
     public String UpdateStats() {
        return "Player Lives Left: " + player.getLives();
    }
}