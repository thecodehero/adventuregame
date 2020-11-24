
import java.awt.*; //imports awt package
import java.awt.event.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.event.TextListener;
import java.util.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class GUIWeapon extends Frame implements ActionListener {
    
    private Item[] items;
    private InventorySpace inventoryspace;
    private JButton btnEquip,btnUnequip,btnDrop,btnClose;
    private JTextField tfItemName,tfQuantity, tfStats, tfMessage, tfCurrentWeapon;
    private JPanel itemPanel,buttonPanel; 
    private Player player;
    private int i;;
    
    
    
    public GUIWeapon(Item[] items,InventorySpace inventoryspace,int i,Player player) throws NullPointerException {         
        super(items[i].getItemName());      
             
        this.i = i;
        this.items = items;
        this.inventoryspace = inventoryspace;
        this.player = player;        
        setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS)); 
        
        itemPanel = new JPanel();
        itemPanel.setLayout(new BoxLayout(itemPanel,BoxLayout.PAGE_AXIS));
        
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel,BoxLayout.LINE_AXIS));
        //text fields to display info about weapon.
        tfItemName = new JTextField("Weapon: " + items[i].getItemName());
        tfQuantity = new JTextField("Quantity:" + items[i].getQuantity());
        tfStats = new JTextField(items[i].UpdateStats());
        tfMessage = new JTextField("Choose An Option!");
        try {
            tfCurrentWeapon = new JTextField("Current Weapon: " + player.getWeaponName()); 
        }
        catch(NullPointerException e)
        {
            tfCurrentWeapon = new JTextField("Current Weapon: none"); 
        }
        //adds items to frame.
        add(itemPanel);
        itemPanel.add(tfItemName);
        itemPanel.add(tfQuantity);
        itemPanel.add(tfStats);
        itemPanel.add(tfCurrentWeapon);
        itemPanel.add(tfMessage);
        
        //text fields can't be edited by player
        tfItemName.setEditable(false);
        tfQuantity.setEditable(false);
        tfStats.setEditable(false);
        tfMessage.setEditable(false);
        tfCurrentWeapon.setEditable(false);
        //buttons to equip,unequp,drop weapons and close window.
        btnEquip = new JButton("Equip"); 
        btnUnequip = new JButton("Unequip");
        btnDrop = new JButton("Drop");
        btnClose = new JButton("Close");
        add(buttonPanel);
        buttonPanel.add(btnEquip);  
        buttonPanel.add(btnUnequip);
        buttonPanel.add(btnDrop);
        buttonPanel.add(btnClose);  
        
        btnEquip.addActionListener(this);
        btnUnequip.addActionListener(this);
        btnDrop.addActionListener(this);
        btnClose.addActionListener(this);
        
        setSize(400,500);
        setVisible(true);
        setLocationRelativeTo(null);
        
    }
    
    public void actionPerformed(ActionEvent e) {
        boolean stillQuantity = items[i].zeroItem(inventoryspace);
        if(e.getSource()==btnClose) {
            dispose();
            new GUIInventory(items,inventoryspace,player);
        }
        else if(e.getSource() == btnEquip) {
            if(stillQuantity==false) {
                tfMessage.setText("Sorry, no more " + items[i].getItemName() + " left!");
            }    
            else {
                if(items[i].weaponEquipped()==true) {
                    tfMessage.setText("You are already equipped. Click unequip first in order to equip!");
                }
                else {
                    items[i].useItem(inventoryspace); //calls method to use/equip weapon
                    tfStats.setText(items[i].UpdateStats());               
                    tfMessage.setText(items[i].getItemName() + " equipped!");
                    try {
                        tfCurrentWeapon.setText("Current Weapon: " + player.getWeaponName());
                    }
                    catch(NullPointerException ex)
                    {
                         tfCurrentWeapon.setText("Current Weapon: none");
                    }
                    
                }
            }
        }
        else if(e.getSource() == btnUnequip) {
            if(items[i].weaponEquipped()==false) {
                tfMessage.setText("You are already unequipped!");
            }
            else {
                tfMessage.setText(player.getWeaponName() + " unequipped!");
                items[i].unequipWeapon();
                tfStats.setText(items[i].UpdateStats());               
                tfCurrentWeapon.setText("Current Weapon: " + player.getWeaponName());
                tfQuantity.setText("Quantity: " + items[i].getQuantity());
            }
            
        }
        else if(e.getSource() == btnDrop) {            
            if(stillQuantity==false) {
                tfMessage.setText("Unable to drop. Quantity already zero.");
            }
            else if(items[i].weaponEquipped()==true && items[i].getQuantity()==1) {
                tfMessage.setText("Unable to drop. As weapon to drop is still equipped. Please unequip.");
            }
            else {                
                items[i].dropItem(inventoryspace);               
                tfMessage.setText(items[i].getItemName() + " dropped!");
                tfQuantity.setText("Quantity: " + items[i].getQuantity());
            }
        }
    }
    
    public void save(FileWriter writer,Game game) throws IOException {}
    
    
}

