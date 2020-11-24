import java.awt.*; //imports awt package
import java.awt.event.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.util.ArrayList; //import arraylist class.
import java.util.*;
import java.io.File;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.text.*;
import java.io.PrintWriter;

public class GUIMovement extends Frame implements ActionListener,SaveGame 
{
    private JButton btnEnter,btnExit;
    private JButton btnClear,btnSave;
    private JTextField tfInput;
    private JLabel lblRoom,message;
    private JTextField tfRoom, tfForward, tfBackward,tfInventory;
    private JPanel roomPanel,optionPanel,buttonPanel;    
    private Inventory inventory;
    private int currentRoom;
    private FileWriter writer;
    private String fileName;    
    
    
    private Room roomObj;    
    private Player player;
    
    private ArrayList<String> characters = new ArrayList<String>();
    private ArrayList<String> roomName = new ArrayList<String>();
    
    public GUIMovement(Player player,ArrayList characters, Inventory inventory,ArrayList roomName,Room roomObj,int currentRoom) 
    {        
        super("Movement");  
        
        this.player = player;
        this.characters = characters;
        this.inventory = inventory;
        this.roomName = roomName;
        this.currentRoom = currentRoom;
        this.roomObj = roomObj;
        setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
        
        Font myFont = new Font("Arial", Font.BOLD, 20);
        Font myFont2 = new Font("Arial", Font.BOLD, 16);
        Font myFont3 = new Font("Arial", Font.PLAIN, 16);
        
        optionPanel = new JPanel();
        optionPanel.setLayout(new BoxLayout(roomPanel,BoxLayout.PAGE_AXIS));
        
        roomPanel = new JPanel();
        roomPanel.setLayout(new BoxLayout(roomPanel,BoxLayout.LINE_AXIS));
        
        message = new JLabel("What would you like to do?");
        add(message);
        message.setFont(myFont);
        
        tfForward = new JTextField("Type 0 -> Move Forward");
        tfBackward = new JTextField("Type 1 -> Move Backward");
        tfInventory = new JTextField("Type 2 -> View Inventory"); 
        add(tfForward);
        add(tfBackward);
        add(tfInventory);
        tfBackward.setEditable(false);
        tfForward.setEditable(false);
        tfInventory.setEditable(false);
        
        tfForward.setFont(myFont3);
        tfBackward.setFont(myFont3);
        tfInventory.setFont(myFont3);
        
        lblRoom = new JLabel("Current Room:");
        add(roomPanel);
        roomPanel.add(lblRoom);
        lblRoom.setFont(myFont2);
        
        tfRoom = new JTextField(roomName.get(roomObj.getRoom()) + "");
        roomPanel.add(tfRoom);
        tfRoom.setEditable(false);
        tfRoom.setFont(myFont3);
        
        tfInput = new JTextField("Enter choice...");
        add(tfInput);  
        tfInput.setFont(myFont2);
        
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        add(buttonPanel);
        
        btnEnter = new JButton("Enter");
        btnClear = new JButton("Clear");
        btnSave = new JButton("Save Game");
        btnExit = new JButton("Exit Game");
        buttonPanel.add(btnEnter);
        buttonPanel.add(btnClear);
        buttonPanel.add(btnSave);
        buttonPanel.add(btnExit);
        
        btnEnter.addActionListener(this);
        btnClear.addActionListener(this);
        btnSave.addActionListener(this);
        btnExit.addActionListener(this);
        
        pack();
        setVisible(true);
        setLocationRelativeTo(null);        
        
    }    
    
    
    public void actionPerformed(ActionEvent e) {
        String text = tfInput.getText();        
        if(e.getSource()==btnExit) {
            dispose(); //exits current game. Returns to the start.
            new Game();
        }
        else if(e.getSource()==btnClear) {
            tfInput.setText("");
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
        else {
            boolean validInt=Utility.isInt(tfInput.getText()); //NumberFormatException Handler. Ensure only integer entered.
            
            if(validInt) {
                int option = Integer.parseInt(tfInput.getText());
                try 
                {
                    if(option==2) 
                    { //view inventory                   
                    inventory.inventoryOptions(inventory.getItems());
                    }
                    else if(option==0) {  //move forward     
                            tfRoom.setText(roomName.get(roomObj.getRoom()+1));
                            roomObj.setRoom(roomObj.getRoom()+1);                         
                            Frame f = new GUIBattle(player,characters,inventory,roomName,roomObj,inventory.getItems());                                
                            dispose();
                    }
                    else if(option==1) { //move backwards   
                            tfRoom.setText(roomName.get(roomObj.getRoom()-1));
                            roomObj.setRoom(roomObj.getRoom()-1);                         
                            Frame f = new GUIBattle(player,characters,inventory,roomName,roomObj,inventory.getItems());
                            dispose();                    
                    }     
                    else {
                        tfInput.setText("Error. Enter option above.");
                    }                
                }
                catch(IndexOutOfBoundsException ex) {                    
                    tfInput.setText("You have reached end of the map. Please turn back");
                }
                
            }
            else {
                tfInput.setText("Error. Enter option above.");
            }            
        }
    }
    
    public void save(FileWriter writer) throws IOException 
    {        
        player.save(writer); //save player object
        inventory.save(writer); //save in ventory objects which includes items objects
        roomObj.save(writer); //save room object
        inventory.getInventorySpace().save(writer); //save inventoryspace object        
    } 
     
      
    
}