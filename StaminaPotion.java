import java.util.*;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
public class StaminaPotion extends Potion { //StaminaPotion inherits from Potion. It is a subclass of superclass potion.
    //stamina potion increases the number of escapes left.
    
    public StaminaPotion(Player player,String itemName) {
        super(player,"Stamina Potion",0);       
    }
    
    //method to stamina potion 
    public void save(FileWriter writer) throws IOException 
    {             
        super.save(writer);        
    };
    
     //method to load item
    public void loadItem(Scanner reader) throws IOException
    {
        super.setQuantity(Integer.parseInt(reader.next()));
        super.setPotionUnits(Integer.parseInt(reader.next()));        
    }
      
    //method overrides useItem in superclass Item. Dynamic Binding.
    public void useItem(InventorySpace inventoryspace) { //instance method for using health potion.
             super.useItem(inventoryspace);
             player.setEscape(player.getEscape()+2);          
        }             
     
          
        
    //method to display escape stats
     public String UpdateStats() {
        return "Player Escapes Left: " + player.getEscape();
    }
}