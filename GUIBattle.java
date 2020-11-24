import java.lang.Math.*; //import random library
import java.util.ArrayList; //import ArrayList class.
import java.awt.*; //imports awt package
import java.awt.event.*;
import java.awt.event.ActionListener;
import javax.swing.*; 
import java.util.*;
import java.io.File;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.text.*;
import java.io.PrintWriter;

public class GUIBattle extends Frame implements ActionListener,SaveGame 
{ 
    
    private Character enemy; //instance variable to store enemy
    private Player player; //declare instance variable for player
    
    private int battleOutcome; //declares instance variable to store outcome of the battle.
    private Inventory inventory;    
    private Item[] items;
    private int chosenEnemy;    
    private Room roomObj;
    private double playerWin; 
    private FileWriter writer;
    private String fileName;
    
    
    private ArrayList<String> roomName = new ArrayList<String>();   
    private Frame f;
    
    private ArrayList<String> characters = new ArrayList<String>();
    
    private JLabel lblmessage, lblBattle;
    private JButton btnEnter,btnClear,btnSave,btnExit;
    private JPanel battlePanel,buttonPanel,optionPanel; 
    private JTextField tfOpponents,tfHealth,tfAttack,tfWeapon,tfLives,tfEscapes,tfEnemiesLeft,tfChanceWin;
    private JTextField tfInput, tfCurrentRoom,tfAlert;
    private JLabel lblFight,lblEscape,lblInventory;
    
    public GUIBattle(Player player,ArrayList characters,Inventory inventory,ArrayList roomName,Room roomObj,Item[] items) 
    {
        super("Battle");
        
        this.player = player;
        this.characters = characters;
        this.inventory = inventory;
        this.roomObj = roomObj;
        this.roomName = roomName;
        this.items = items;
        this.chosenEnemy=chosenEnemy;
        this.enemy = enemy;
        
        chosenEnemy = Utility.getRandomNumber(5,0);  //randomly choose enemy 
        
        
        enemy = setEnemy(chosenEnemy);
        playerWin = battleOdds();
        
        Font myFont = new Font("Arial", Font.BOLD, 20);
        Font myFont2 = new Font("Arial", Font.BOLD, 16);
        Font myFont3 = new Font("Arial", Font.PLAIN, 16);
        
        setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));  
        
        battlePanel = new JPanel();
        battlePanel.setLayout(new BoxLayout(battlePanel,BoxLayout.PAGE_AXIS)); 
        
        lblBattle = new JLabel("Battle Commencing!");
        
        
        tfAlert = new JTextField("Alert! Enemy " + enemy.getCharacterType() + " encountered");
        tfOpponents = new JTextField(player.getName() + " vs " + enemy.getName());
        tfHealth = new JTextField("Player Health: " + player.getHealth() + "; Enemy Health: " + enemy.getHealth());
        tfWeapon = new JTextField("Weapon Equipped: " + player.getWeaponName() + " ; Weapon Attacks Left: " + items[player.getWeaponID()].getAttacksLeft());
        tfAttack = new JTextField("Your attack level: " + player.getAttack() + "; Enemy Attack Level: " +enemy.getAttack());
        tfLives = new JTextField("Lives left: " + player.getLives());
        tfEscapes = new JTextField("Escapes left: " + player.getEscape());
        tfEnemiesLeft = new JTextField("Enemies Left to Kill: " + player.getEnemiesLeft());
        tfChanceWin = new JTextField("Chance to win is: " + playerWin);
        
            
        //panel to display information about player, enemy, stats including number of lives, health, enemiesLeft, chance to win.
        add(battlePanel);
        battlePanel.add(tfAlert);
        battlePanel.add(lblBattle);
        battlePanel.add(tfOpponents);
        battlePanel.add(tfHealth);
        battlePanel.add(tfAttack);
        battlePanel.add(tfWeapon);        
        battlePanel.add(tfLives);
        battlePanel.add(tfEscapes);
        battlePanel.add(tfEnemiesLeft);
        battlePanel.add(tfChanceWin);
        
        tfAlert.setEditable(false);
        tfOpponents.setEditable(false);
        tfHealth.setEditable(false);
        tfWeapon.setEditable(false);
        tfAttack.setEditable(false);
        tfLives.setEditable(false);
        tfEscapes.setEditable(false);
        tfEnemiesLeft.setEditable(false);
        tfChanceWin.setEditable(false);
        
        
        optionPanel = new JPanel();
        optionPanel.setLayout(new BoxLayout(optionPanel,BoxLayout.PAGE_AXIS));
        add(optionPanel);
        
        //display inventory options
        lblmessage = new JLabel("What would you like to do?");        
        optionPanel.add(lblmessage);        
        lblFight = new JLabel("Type 0 -> Fight");
        lblEscape = new JLabel("Type 1 -> Escape");
        lblInventory = new JLabel("Type 2 -> Inventory");
        tfInput = new JTextField("Enter option...");
        tfCurrentRoom = new JTextField("Current Room: " + roomName.get(roomObj.getRoom()));
        
        optionPanel.add(lblFight);
        optionPanel.add(lblEscape);
        optionPanel.add(lblInventory);
        optionPanel.add(tfInput); 
        optionPanel.add(tfCurrentRoom);
        
        tfCurrentRoom.setEditable(false);
        
        
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        add(buttonPanel);
        
        //buttons to enter data, clear data in input field and exit program.
        btnEnter = new JButton("Enter");
        buttonPanel.add(btnEnter);            
        
        btnClear = new JButton("Clear");
        buttonPanel.add(btnClear);
        
        btnSave = new JButton("Save Game");
        buttonPanel.add(btnSave);
        
        btnExit = new JButton("Exit Game");
        buttonPanel.add(btnExit);
        
        
        
        btnEnter.addActionListener(this);
        btnClear.addActionListener(this);        
        btnSave.addActionListener(this);
        btnExit.addActionListener(this);
        
        pack();
        setVisible(true);
        setLocationRelativeTo(null);
    }
    
    public void actionPerformed(ActionEvent e) 
    {
        String text = tfInput.getText();        
        if(e.getSource()==btnEnter) {
            boolean validInput=Utility.isInt(text);
            if(validInput) 
            {
                int option = Integer.parseInt(text);
                if(option==0) { //Type 0 -> fight
                    if(player.getWeapon()) 
                    {
                        boolean noMoreWeaponAttack;
                        noMoreWeaponAttack = inventory.weaponBattle(inventory.getInventorySpace(),inventory.getItems());
                        playerWin = battleOdds();
                        if(noMoreWeaponAttack==true)
                        {
                            JOptionPane.showMessageDialog(null, "You have run out of weapon attacks. Dropping weapon!\n Player Attack Level: "  + player.getAttack());    
                        }
                    } 
                    if(battleResult()) //if player wins battle
                    {
                        enemy.setDamage(player.getAttack()); 
                        tfInput.setText("You have won this round! Enemy loses: " + enemy.getDamage() + " health.");
                        enemy.setHealth(enemy.damageTaken(player.getAttack())); //reduce enemy health by attack level of player
                        if(enemy.getHealth()<=0) //if is killed.
                        {
                            JOptionPane.showMessageDialog(null, "Enemy has been killed. You have won the battle");                                                    
                            player.setEnemiesLeft(player.getEnemiesLeft()-1);
                            useKnightArmour(chosenEnemy);
                            dispose();    
                            if(player.getEnemiesLeft()<=0) //if no more enemies left to kill.
                            {
                                 JOptionPane.showMessageDialog(null,"All Enemies Killed. Well Done! You have won the game!");
                                 new Game();
                                }
                            else 
                            {
                                new GUINewItem(player,characters,inventory,roomName,
                                roomObj,roomObj.getRoom(),items); 
                            }                            
                        }
                        
                    }
                        else //if player defeated
                        {            
                            tfInput.setText("Defeat. You have lost " + enemy.getAttack() + " health"); //print result lost
                            player.setHealth(player.damageTaken(enemy.getAttack())); //reduce players health by enemies attack level. 
                            if(player.getHealth()<=0) 
                            {    //when players health <= 0.
                                if(player.getLives()>0) 
                                { //when player still has lives left
                                     player.subLives();  //subtract player lives by 1
                                     player.setHealth(100); //reset player health to 100.
                                     tfInput.setText("You have been killed. Respawning. You have " + player.getLives() + " lives left");  
                                     
                                }                                
                                else { //if you lose all health and lives you lose the game.
                                    JOptionPane.showMessageDialog(null, "You have been killed. No more lives. Game Over!");
                                    dispose();  
                                    new Game();
                                    
                                }                 
                                }                           
                            }                             
                            tfAlert.setText("Alert! Enemy " + enemy.getCharacterType() + " encountered");
                            tfOpponents.setText(player.getName() + " vs " + enemy.getName());
                            tfHealth.setText("Player Health: " + player.getHealth() + "; Enemy Health: " + enemy.getHealth());
                            tfWeapon.setText("Weapon Equipped: " + player.getWeaponName() + " ; Weapon Attacks Left: " + items[player.getWeaponID()].getAttacksLeft());
                            tfAttack.setText("Your attack level: " + player.getAttack() + "; Enemy Attack Level: " +enemy.getAttack());
                            tfLives.setText("Lives left: " + player.getLives());
                            tfEscapes.setText("Escapes left: " + player.getEscape());
                            tfEnemiesLeft.setText("Enemies Left to Kill: " + player.getEnemiesLeft());
                            tfChanceWin.setText("Chance to win is: " + playerWin);
                    }  
                    else if(option==1) //if player chooses to escape
                    { //Type 1 -> Escape
                        if(player.getEscape() < 1) //if player has no more escapes left.
                        {
                        tfInput.setText("Sorry, can't escape. You have no more escapes left!");                         
                        }
                        else 
                        {
                            player.useEscape(player.getEscape()); //will use escape and reduce number of escape by 1.
                            JOptionPane.showMessageDialog(null,"You have successfully escaped.\n Escapes left: " + player.getEscape());                                           
                            dispose();                                                       
                            new GUINewItem(player,characters,inventory,roomName,
                            roomObj,roomObj.getRoom(),items);      
                        }
                    }
                else if(option==2) { //Type 2 -> View Inventory
                    inventory.inventoryOptions(inventory.getItems()); //view inventory
                    tfAlert.setText("Alert! Enemy " + enemy.getCharacterType() + " encountered");
                    tfOpponents.setText(player.getName() + " vs " + enemy.getName());
                    tfHealth.setText("Player Health: " + player.getHealth() + "; Enemy Health: " + enemy.getHealth());
                    tfWeapon.setText("Weapon Equipped: " + player.getWeaponName() + " ; Weapon Attacks Left: " + items[player.getWeaponID()].getAttacksLeft());
                    tfAttack.setText("Your attack level: " + player.getAttack() + "; Enemy Attack Level: " +enemy.getAttack());
                    tfLives.setText("Lives left: " + player.getLives());
                    tfEscapes.setText("Escapes left: " + player.getEscape());
                    tfEnemiesLeft.setText("Enemies Left to Kill: " + player.getEnemiesLeft());
                    tfChanceWin.setText("Chance to win is: " + playerWin);
                }
                else 
                {
                tfInput.setText("Invalid Option. Please enter one of the numbers displayed!");
                }
                } 
                else 
                {
                    tfInput.setText("Invalid Option. Please enter one of the numbers displayed!");
                }
            }              
        else if(e.getSource()==btnClear) 
        { //clear the input field.
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
        else 
        { 
            dispose(); //exits current game. Returns to the start.
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
    
    public void useKnightArmour(int chosenEnemy) 
    { //pick and add knight armour after killing knight.
        if(chosenEnemy==0 || chosenEnemy==1) {
            player.setArmour(enemy.getArmour());
        }        
    }    
    
    //setter method to set enemy. Polymorphic variable Character enemy is set to instance of subtype Knight, Vampire or Wizard.
    public Character setEnemy(int chosenEnemy) {           
        if(chosenEnemy == 0) {enemy = new Knight(characters.get(0),12,15);}
        else if(chosenEnemy == 1) {enemy = new Knight(characters.get(1),22,29);}
        else if(chosenEnemy == 2) {enemy = new Vampire(characters.get(2),8,6);}
        else if(chosenEnemy == 3) {enemy = new Vampire(characters.get(3),14,18);}
        else if(chosenEnemy == 4) {enemy = new Wizard(characters.get(4),9);}
        else {enemy = new Wizard(characters.get(5),14);}  
        return enemy;
        
    }        
       
        
    //method to calculate odds of player winning the battle.
    public double battleOdds() 
    {
        double x = player.getAttack();
        double y = player.getAttack() + enemy.getAttack();
        double playerWin = (x/y);        
        return playerWin;
    }
    
    //method to determine who will win the battle, player or non-player.
    public boolean battleResult() 
    {        
        double chanceWin = battleOdds();
        return Math.random()<=chanceWin;
    }    
    
    
    public void setBattleOutcome(int battleOutcome) 
    {
        this.battleOutcome = battleOutcome;
    }
    
    public int getBattleOutcome() 
    { //getter method to access outcome of battle.
        return battleOutcome;
    }
    
    
    
}