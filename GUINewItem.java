import java.awt.*; //imports awt package
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.io.File;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.text.*;
import java.io.PrintWriter;

public class GUINewItem extends Frame implements ActionListener,SaveGame
{
    private Inventory inventory;
    private int currentRoom;
    
    private Room roomObj;    
    private Player player;
    private Item[] items;
    private int chosenItem;
    private FileWriter writer;
    private String fileName;
    
    ArrayList<String> roomName = new ArrayList<String>(); 
    ArrayList<String> characters = new ArrayList<String>();
    
    private JLabel lblItemAlert;
    private JButton btnPickUp,btnLeaveItem,btnInventory,btnExit;
    private JButton btnSave;
    private JPanel titlePanel,buttonPanel;
    
    
    public GUINewItem(Player player,ArrayList characters, Inventory inventory,ArrayList rooms,Room roomObj,int startRoom,Item[] items) 
    {
        super("Item Discovered");        
        this.player = player;
        this.characters = characters;
        this.inventory = inventory;
        roomName = rooms;
        this.roomObj = roomObj;
        this.currentRoom = startRoom;
        this.items = items;
        
        chosenItem = Utility.getRandomNumber(7,0);
        
        setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
        
        titlePanel = new JPanel();
        buttonPanel = new JPanel();
        
        int usedSpace = items[chosenItem].getTakeSpace() + 1;
        int units = items[chosenItem].getPotionUnits();
        
        add(titlePanel);
        lblItemAlert = new JLabel("Item Discovered: " + items[chosenItem].getItemName() + "; Takes Space: " + usedSpace +
        "; Units: " + units);
        titlePanel.add(lblItemAlert);
        
        add(buttonPanel);
        buttonPanel.setLayout(new BoxLayout(buttonPanel,BoxLayout.LINE_AXIS));
        
        btnPickUp = new JButton("Pick Up");
        btnLeaveItem = new JButton("Leave Item");
        btnInventory = new JButton("View Inventory");
        btnSave = new JButton("Save Game");
        btnExit = new JButton("Quit");
        
        buttonPanel.add(btnPickUp);
        buttonPanel.add(btnLeaveItem);
        buttonPanel.add(btnInventory);
        buttonPanel.add(btnSave);
        buttonPanel.add(btnExit);        
        
        btnPickUp.addActionListener(this);
        btnLeaveItem.addActionListener(this);        
        btnSave.addActionListener(this);
        btnInventory.addActionListener(this);
        btnExit.addActionListener(this);      
        
        pack();
        setVisible(true);
        setLocationRelativeTo(null);        
    }
    
    public void actionPerformed(ActionEvent e)     
    {
        boolean enoughSpace;
        InventorySpace inventoryspace = inventory.getInventorySpace();
        if(e.getSource()==btnPickUp) 
        {                                     
            enoughSpace = items[chosenItem].isMaxCapacity(inventoryspace);
            if(enoughSpace==true) 
            {
                dispose();
                items[chosenItem].addItem(inventoryspace);
                JOptionPane.showMessageDialog(null,items[chosenItem].getItemName() + " picked up!");
                new GUIMovement(player,characters,inventory,roomName,roomObj,currentRoom);
            }
            else 
            {
                JOptionPane.showMessageDialog(null,"Sorry, no space left in inventory.\n Drops items to for space!");
            }
            
                             
        }
        else if(e.getSource()==btnLeaveItem) 
        {
            dispose();
            new GUIMovement(player,characters,inventory,roomName,roomObj,currentRoom);
        }
        else if(e.getSource()==btnSave) 
        {
           fileName = JOptionPane.showInputDialog(null, "Enter name of save.");        
           File saveFile = new File(fileName + ".txt");
           try { //method to create new save file and write state of game to it.
              writer = new FileWriter(fileName + ".txt");              
              save(writer);
              writer.close();
              JOptionPane.showMessageDialog(null,"Save Successful.");              
            } 
            catch (IOException ex) {
              JOptionPane.showMessageDialog(null,"An error occurred. Please try again!"); 
              ex.printStackTrace();
            }
            try { //method to create new save file and write state of game to it.
              File savesListFile = new File("savelist.txt");  
              BufferedWriter writer2 = new BufferedWriter(new FileWriter("savelist.txt",true));  
              writer2.newLine();
              writer2.write(fileName + ".txt");               
              writer2.close();                          
            } 
            catch (IOException ex) 
            {             
              ex.printStackTrace();
              Utility.print("Error");
            }        
        }
        else if(e.getSource()==btnInventory) 
        {            
            inventory.inventoryOptions(inventory.getItems());            
        }
        else 
        {
            dispose();
            new Game();
        }
    }
    
    public void save(FileWriter writer) throws IOException 
    {        
        player.save(writer); //save player object
        inventory.save(writer); //save inventory objects which includes items objects
        roomObj.save(writer); //save room object
        inventory.getInventorySpace().save(writer); //save inventoryspace object        
    }
}



