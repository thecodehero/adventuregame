import java.util.Scanner; //import scanner class.
import java.util.ArrayList; //import the Array List class.
import java.util.Random; //import the Random class.
import java.lang.Math.*;
import java.awt.*; //imports awt package
import java.awt.event.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.util.*;
import java.io.File;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files

public class Game extends Frame implements ActionListener {
    
    private JLabel lblTitle;
    private JTextField tfName;
    private JButton btnNewGame,btnLoad,btnExit;
    private JPanel titlePanel,buttonPanel, loadPanel;
    private JComboBox loadGame;
    
    private Player player; //variable for player
    private Inventory inventory;  //variable for inventory
    private Item[] items;
    private Room roomObj; //variable for room.
    final int NumRoom = 5; //max number of rooms in game      
    private int currentRoom; //declares variable to store starting room for game
    private InventorySpace inventoryspace;
    private String saveFile;
    private static ArrayList<String> savesList = new ArrayList<String>();
    private static String[] arraySaves = {};
    private Scanner readSave;   
    private String loadFile;
    private Scanner reader;
    private JTextArea gameinfo;
    private Frame f1;
    private Frame f2;
    private Frame f3;
    
    private ArrayList<String> characters = new ArrayList<String>();
    private ArrayList<String> roomName = new ArrayList<String>();
    
    //main method
    public static void main(String[] args) 
    {       
         Game G = new Game();
         
    }
    
    public Game() {
        super("The Adventure Game");
        
        this.saveFile = saveFile; //initialise saveFile      
                       
        savesList = loadSaveList(); //loads the array list of saves.
        
        arraySaves = savesList.toArray(new String[savesList.size()]);
        
                             
        //Initialising variables
        //characters array        
        //initialise arraylist object for Characters.
        
        characters.add("Arthur");
        characters.add("Lancelot");
        characters.add("Dracula");
        characters.add("Vlad");
        characters.add("Merlin");
        characters.add("Gandalf");
        
        //create an ArrayList object for the Rooms 
        roomName.add("dungeon");
        roomName.add("courtyard");
        roomName.add("throneroom");
        roomName.add("kitchen");
        roomName.add("tower");
        
        player = new Player("name",0);
        inventory = new Inventory(player);
        roomObj = new Room(0);
        inventoryspace = new InventorySpace();
        items = inventory.getItems();
        
        setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
                      
        titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel,BoxLayout.PAGE_AXIS));
        add(titlePanel);
        
        lblTitle = new JLabel("The Adventure Game");
        titlePanel.add(lblTitle);
        
        gameinfo = new JTextArea(10,20);
        JScrollPane scrollPane = new JScrollPane(gameinfo); 
        gameinfo.setEditable(false);
        gameinfo.append("Welcome to the Adventure Game!\n");
        gameinfo.append("Kill 6 enemies and you win the game\n");
        gameinfo.append("You can pick up items including potions and weapons to add health,lives,escapes and \nincrease attack level\n");
        gameinfo.append("If you run out of health and lives you lose and its game over!");
        
        titlePanel.add(gameinfo);
        
        tfName = new JTextField("Enter name...");
        titlePanel.add(tfName);         
         
        buttonPanel = new JPanel();
        add(buttonPanel);
        
        btnNewGame = new JButton("Start Game");        
        buttonPanel.add(btnNewGame);        
        loadPanel = new JPanel();
        loadPanel.setLayout(new FlowLayout());
        
        btnLoad = new JButton("Load");
        loadGame = new JComboBox(arraySaves);
        add(loadPanel);        
        loadPanel.add(btnLoad);  
        loadPanel.add(loadGame);
        
        btnExit = new JButton("Exit Game"); 
        buttonPanel.add(btnExit);
        
        btnNewGame.addActionListener(this);
        btnLoad.addActionListener(this);
        btnExit.addActionListener(this);
        
        
        loadGame.addItemListener(
            new ItemListener() 
            {
                public void itemStateChanged(ItemEvent e) 
                {
                    if(e.getStateChange()==ItemEvent.SELECTED)
                    {
                        loadFile = loadGame.getSelectedItem().toString();
                    }
                }
            }
            );
        
        setSize(500,300);
        setVisible(true);
        setLocationRelativeTo(null); 
        
          
         
        
    }  
    
    
    public void actionPerformed(ActionEvent e) {       
        if(e.getSource()==btnNewGame) 
        {
            dispose();  
            player = new Player(tfName.getText(),0); //create player instance
            inventory = new Inventory(player); //create inventory instance
            currentRoom = Utility.getRandomNumber(4,0); //randomly choose starting room         
            roomObj = new Room(currentRoom); //create room instance            
            new GUIMovement(player,characters,inventory,roomName,roomObj,currentRoom); //create GUI movement instance
        }
        else if(e.getSource()==btnLoad) 
        {                       
            try {            
            loadFile = loadGame.getSelectedItem().toString(); 
            loadGame(loadFile);            
            dispose();
            new GUIMovement(player,characters,inventory,roomName,roomObj,currentRoom);
            }
            catch(IOException ex)
            {
                JOptionPane.showMessageDialog(null,"Alert! Error. Save not found 3");
                ex.printStackTrace();
            }
            
        }
        else {
            System.exit(0);
        }
    }
        
    
    //method to load list of save games for the combo box.
    public ArrayList loadSaveList() 
    {
        ArrayList<String> saveList = new ArrayList<String>();
        try
        {
            readSave = new Scanner(new File("savelist.txt"));
            while(readSave.hasNext())
            {
                saveList.add(readSave.next());
            }
            readSave.close();            
        }
        catch(FileNotFoundException ex)
        {
            JOptionPane.showMessageDialog(null,"Alert! Error. Save not found test 1");
        }                
        return saveList;
    }
    
    //method to load a save
    public void loadGame(String chosenSave) throws IOException
    {        
        try { 
              reader = new Scanner(new File(chosenSave));              
              while (reader.hasNext()) //loop until reach end of the file, when there is empty line. 
              {                                                 
                        player.loadPlayer(reader);
                        inventory.loadInventory(reader); //load inventory
                        roomObj.loadRoom(reader); //load room 
                        inventory.getInventorySpace().loadInventorySpace(reader); //load inventory space                    
              }
              reader.close();              
           } 
           catch (FileNotFoundException ex) {
                  JOptionPane.showMessageDialog(null,"Alert! Error. Save not found test 2");
                  ex.printStackTrace();
                }           
    }
}
