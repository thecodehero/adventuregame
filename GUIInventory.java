import java.awt.*; //imports awt package
import java.awt.event.*;
import java.awt.event.ActionListener;
import java.awt.event.TextListener;
import javax.swing.*;

public class GUIInventory extends Frame implements ActionListener {
    private JButton btnEnter,btnClear,btnBack;
    private JTextField tfTotalInventory;
    private JTextField tfHealthPotion,tfLifePotion,tfStaminaPotion;
    private JTextField tfSword,tfMace,tfAxe,tfGun,tfMachineGun;
    private JTextField tfSelection;    
    private Item[] items;
    private InventorySpace inventoryspace;
    private Frame f;
    private int i;
    private JList listInventory;
    
    private Player player;
    
    
    public GUIInventory(Item[] items,InventorySpace inventoryspace,Player player) {
        super("Inventory");
        setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
        
        this.items = items;
        this.inventoryspace = inventoryspace;
        this.player = player;
        
        Font myFont = new Font("Arial", Font.BOLD, 20);
        Font myFont2 = new Font("Arial", Font.BOLD, 15);
        
        DefaultListModel<String> listModel = new DefaultListModel<>();
        listModel.addElement("Total Space Taken: " + inventoryspace.getTotalInventory() + " out of max capacity 6.");
        for(int i = 0; i < 8; i++) {
            listModel.addElement(items[i].getOptions(i)); //add polymorphic array elements to list.
        }
        listInventory = new JList<>(listModel);
        add(listInventory);
        
        listInventory.setFont(myFont);
        
        
        tfSelection = new JTextField("Enter here...");
        add(tfSelection);
        tfSelection.setFont(myFont2);
        
        btnEnter = new JButton("Enter");
        btnBack = new JButton("Back");
        btnClear = new JButton("Clear");
        add(btnEnter);
        add(btnClear);
        add(btnBack);  
                
        btnEnter.addActionListener(this);
        btnClear.addActionListener(this);
        btnBack.addActionListener(this);
        
        setSize(600,400);
        setVisible(true);
        setLocationRelativeTo(null); 
        
          
    }
    
    public void actionPerformed(ActionEvent e) { 
        String text = tfSelection.getText();
        if(e.getSource()==btnBack) {            
            dispose();            
        }
        else if(e.getSource()==btnClear) {
            tfSelection.setText("");
        }
        else {
            boolean validInput=Utility.isInt(tfSelection.getText());
            int option;
            if(validInput) {
                option = Integer.parseInt(text);                                                                                   
                if(option >= 0 && option < 3)
                {                              
                     f = new GUIPotion(items,inventoryspace,option,player); 
                     dispose();
                }       
                else if(option >= 3 && option < 8) 
                {
                     f = new GUIWeapon(items,inventoryspace,option,player);
                     dispose();
                }                    
                else 
                {                   
                tfSelection.setText("Invalid Option. Please enter one of the numbers displayed!");
                }                
            }
            else {
                tfSelection.setText("Error. Please enter one of the numbers displayed!");
            }
        }
        
    }
    
}
