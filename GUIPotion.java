import java.awt.*; //imports awt package
import java.awt.event.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.event.TextListener;

public class GUIPotion extends Frame implements ActionListener {
    
    private Item[] items;
    private InventorySpace inventoryspace;
    private JButton btnUse,btnDrop,btnClose;
    private JTextField tfItemName,tfQuantity, tfStats, tfMessage;
    private JTextField tfUnits;
    private JPanel itemPanel,buttonPanel;    
    private int i;;
    private Frame f;
    private int UnitsLeft;
    private int fullPotionLeft;
    
    private Player player;
       
   public GUIPotion(Item[] items,InventorySpace inventoryspace,int i,Player player) {         
        super(items[i].getItemName());
        
        this.i = i;
        this.items = items;
        this.inventoryspace = inventoryspace;
        
        //calculate the total potion units for the potion type.
        if(items[i].getQuantity()>0)
        {
            fullPotionLeft = items[i].getQuantity() - 1;
        }        
        else 
        {
            fullPotionLeft = 0;
        }
        UnitsLeft = items[i].getPotionUnits() + fullPotionLeft * 100;
        
        setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS)); 
        
        itemPanel = new JPanel();
        itemPanel.setLayout(new BoxLayout(itemPanel,BoxLayout.PAGE_AXIS));
        
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel,BoxLayout.LINE_AXIS));
        
        //initiate text fields for displaying potion details and player stats. 
        tfItemName = new JTextField("Potion: " + items[i].getItemName());
        tfQuantity = new JTextField("Quantity:" + items[i].getQuantity());
        tfUnits = new JTextField("Total Potion Units Left: " + UnitsLeft);
        tfStats = new JTextField(items[i].UpdateStats());
        tfMessage = new JTextField("Choose An Option!");
        
        add(itemPanel);
        itemPanel.add(tfItemName);
        itemPanel.add(tfQuantity);
        itemPanel.add(tfUnits);
        itemPanel.add(tfStats);
        itemPanel.add(tfMessage);
        
        tfItemName.setEditable(false);
        tfQuantity.setEditable(false);
        tfUnits.setEditable(false);
        tfStats.setEditable(false);
        tfMessage.setEditable(false);
        
        btnUse = new JButton("Use (-50 Units)");        
        btnDrop = new JButton("Drop");
        btnClose = new JButton("Close");
        add(buttonPanel);
        buttonPanel.add(btnUse);        
        buttonPanel.add(btnDrop);
        buttonPanel.add(btnClose);  
        
        btnUse.addActionListener(this);
        btnDrop.addActionListener(this);
        btnClose.addActionListener(this);
        
        setSize(300,500);
        setVisible(true);
        setLocationRelativeTo(null);
        
        
        
    }
    
    public void actionPerformed(ActionEvent e) {
        boolean stillQuantity = items[i].zeroItem(inventoryspace);
        //button to close Potion window.
        if(e.getSource()==btnClose) {
            dispose();
            f = new GUIInventory(items,inventoryspace,player);
        } //Button to use potion
        else if(e.getSource() == btnUse) {              
            if(stillQuantity == false) { //where is no more items left.
                tfMessage.setText("Sorry, no more " + items[i].getItemName() + " left!");
            }
            else { //if there are more than 0 quantity of potion left.
                items[i].useItem(inventoryspace); 
                tfStats.setText(items[i].UpdateStats());
                tfQuantity.setText("Quantity: " + items[i].getQuantity());
                if(items[i].getQuantity()>0)
                {
                    fullPotionLeft = items[i].getQuantity() - 1;
                }        
                else 
                {
                    fullPotionLeft = 0;
                }
                UnitsLeft = items[i].getPotionUnits() + fullPotionLeft * 100;
                tfUnits.setText("Total Potion Units Left: " + UnitsLeft);
                if(i==0) { //if potion is health potion
                    tfMessage.setText("Health Updated (+ 50)");                
                }
                else if(i==1) { //if potion is life potion
                    tfMessage.setText("Lives Updated (+ 1)");  
                }
                 else { //if potion is Stamina Potion
                    tfMessage.setText("Escapes Updated (+ 2)");  
                }
            }
        } //button to drop Potion
        else if(e.getSource() == btnDrop) {                      
            if(stillQuantity==false) {
                tfMessage.setText("No " + items[i].getItemName() + " left to drop!");
            }
            else {
                items[i].dropItem(inventoryspace);
                tfQuantity.setText("Quantity: " + items[i].getQuantity());
                tfMessage.setText(items[i].getItemName() + " dropped!");
                tfUnits.setText("Total Potion Units Left: " + UnitsLeft);
            }
        }
    }
    
    
}

