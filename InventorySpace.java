import java.util.*;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;

public class InventorySpace implements SaveGame {
    private int totalInventory; //stores total 
    private final int maxInventory; 
    
    public InventorySpace() {
        totalInventory = 0;
        maxInventory = 6;
    }
    
    //method to save item
    public void save(FileWriter writer) throws IOException 
    {              
        writer.write(totalInventory + "\n");        
    }
    
    //method to load inventory space
    public void loadInventorySpace(Scanner reader) throws IOException
    {
        setTotalInventory(Integer.parseInt(reader.next()));        
    }
    
    
    public int getMaxInventory() {
        return maxInventory;
    }
    
    //method to get total inventory
    public int getTotalInventory() {
        return totalInventory;
    }
    
    //method to set total inventory 
    public void setTotalInventory(int inventory) {
        totalInventory = inventory;
    }
    
    //method to add total inventory
    public void addTotalInventory() {
        totalInventory +=1;
        if(totalInventory > maxInventory) 
        {
            totalInventory = maxInventory;
        }
    }
    
    //method to subtract total inventory 
    public void subTotalInventory() {
        totalInventory -=1;
        if(totalInventory < 0) {
            
        }
    }
}