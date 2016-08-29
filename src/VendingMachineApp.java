
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import business.VendingItem;
import dataaccess.ItemDetailsUtil;
import dataaccess.VendingCalculationUtil;

public class VendingMachineApp implements ActionListener, ListSelectionListener {

	private final ItemDetailsUtil itemDetailsUtil = new ItemDetailsUtil();
	private final VendingCalculationUtil vendingCalutil= new VendingCalculationUtil();
	private final DefaultListModel<VendingItem> model = new DefaultListModel<>();
	private JList<VendingItem> itemList = null;
	private JTextField itemName = null;
	private JTextField amountDeposit = null;
	private int selectedIndex;	
	
	public static void main(String[] args){
		// This is the main frame in which all the components are appended.
		VendingMachineApp vendingMachineApp = new VendingMachineApp();
		JFrame jframe = new JFrame("Vending Machine");
		JButton addButton = new JButton("ADD ITEM");
		JButton removeButton = new JButton("REMOVE ITEM");
		JButton changePriceButton = new JButton("CHANGE PRICE");
		JButton buyItemsButton = new JButton("BUY ITEMS");
		JButton refundButton = new JButton("REFUND AMOUNT");
		JLabel itemNameLabel = new JLabel("ITEM NAME:");
		JLabel amountDeposited = new JLabel("AMOUNT DEPOSIT:");
		
		// Using BorderLayout for arranging various components in the frame.
		BorderLayout layout = new BorderLayout();	
		jframe.setLayout(layout);
		
		List<VendingItem> itemsList = vendingMachineApp.itemDetailsUtil.getItemsList();
                itemsList.stream().forEach((item) -> {
                    vendingMachineApp.model.addElement(item);
            });
		vendingMachineApp.itemList = new JList<>(vendingMachineApp.model);
		JScrollPane itemsPane = new JScrollPane(vendingMachineApp.itemList);
		vendingMachineApp.itemList.addListSelectionListener(vendingMachineApp);
		
		jframe.add(itemsPane,BorderLayout.CENTER);
		
		JPanel itemPanel = new JPanel();
		GridLayout gridLayout = new GridLayout(10,1);
		itemPanel.setLayout(gridLayout);
		itemPanel.add(itemNameLabel);
		vendingMachineApp.itemName = new JTextField();
		vendingMachineApp.itemName.setColumns(10);
		itemPanel.add(vendingMachineApp.itemName);
		addButton.addActionListener(vendingMachineApp);
		itemPanel.add(addButton);
		removeButton.addActionListener(vendingMachineApp);
		itemPanel.add(removeButton);
		changePriceButton.addActionListener(vendingMachineApp);
		itemPanel.add(changePriceButton);
		itemPanel.add(amountDeposited);
		vendingMachineApp.amountDeposit = new JTextField();
		vendingMachineApp.amountDeposit.setColumns(10);
		itemPanel.add(vendingMachineApp.amountDeposit);
		buyItemsButton.addActionListener(vendingMachineApp);
		itemPanel.add(buyItemsButton);
		refundButton.addActionListener(vendingMachineApp);
		itemPanel.add(refundButton);
		jframe.add(itemPanel,BorderLayout.EAST);
		
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		jframe.setSize(600, 320);
		jframe.setVisible(true);
	}
	
	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		VendingItem item = this.itemList.getSelectedValue();
		this.selectedIndex = this.itemList.getSelectedIndex();		
		this.itemName.setText("");
		this.itemName.setText(item.getItemName());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand().equalsIgnoreCase("ADD ITEM")){
			if(!"".equalsIgnoreCase(this.itemName.getText())){
				String count = JOptionPane.showInputDialog(null, "Enter the number of "+this.itemName.getText() +" to be added:");
				if(count != null && !"".equalsIgnoreCase(count)){
					this.itemDetailsUtil.addExistingItem(this.itemName.getText(),Integer.parseInt(count));				
					JOptionPane.showMessageDialog(null, count+" "+this.itemName.getText()+" are added successfully");
					this.itemName.setText("");
				}				
			}else{
				JOptionPane.showMessageDialog(null, "Please select an item to add");
			}
		}else if(e.getActionCommand().equalsIgnoreCase("REMOVE ITEM")){
			if(!"".equalsIgnoreCase(this.itemName.getText())){
				String count = JOptionPane.showInputDialog(null, "Enter the number of "+this.itemName.getText() +" to be Removed:");
				if(count!=null && !"".equalsIgnoreCase(count)){
					this.itemDetailsUtil.removeExistingItem(this.itemName.getText(),Integer.parseInt(count));				
					JOptionPane.showMessageDialog(null, count+" "+this.itemName.getText()+" are removed successfully");
					this.itemName.setText("");
				}
			}else{
				JOptionPane.showMessageDialog(null, "Please select an item which needs to be removed");
			}
		}else if(e.getActionCommand().equalsIgnoreCase("CHANGE PRICE")){
			if(!"".equalsIgnoreCase(this.itemName.getText())){
				String count = JOptionPane.showInputDialog(null, "Enter the new price of "+this.itemName.getText()+" :");
				if(count!=null && !"".equalsIgnoreCase(count)){
					VendingItem item = this.itemDetailsUtil.setPriceForExistingItem(this.itemName.getText(),Float.parseFloat(count));				
					JOptionPane.showMessageDialog(null, "The new price of: "+this.itemName.getText()+" is: "+count);
					this.model.set(this.selectedIndex, item);
					this.itemName.setText("");
				}								
			}else{
				JOptionPane.showMessageDialog(null, "Please select an item for which price needs to be changed");
			}
		}else if(e.getActionCommand().equalsIgnoreCase("BUY ITEMS")){
			if(!"".equalsIgnoreCase(this.amountDeposit.getText()) && !"".equalsIgnoreCase(this.itemName.getText())){
				String str = this.vendingCalutil.getItems(this.itemName.getText(), this.itemDetailsUtil, Float.parseFloat(this.amountDeposit.getText()));
				if(str == null){
					Map<String,Integer> boughtItems = this.vendingCalutil.getDispensedItemList();
					StringBuffer output = new StringBuffer("Please collect your items");
					output.append("\n");
                                        boughtItems.entrySet().stream().map((Map.Entry<String, Integer> entry) -> {
                                            output.append(entry.getKey()).append("  ").append(entry.getValue());
                                            return entry;
                                        }).forEach((_item) -> {
                                        output.append("\n");
                                    });
					JOptionPane.showMessageDialog(null, output);
					this.itemName.setText("");
					this.amountDeposit.setText("");
				}else{
					JOptionPane.showMessageDialog(null, str);
				}				
			}else{
				JOptionPane.showMessageDialog(null, "Please select an item to Buy / Please deposit an amount to buy item");
			}
		}else if(e.getActionCommand().equalsIgnoreCase("REFUND AMOUNT")){
			float amount = this.vendingCalutil.refundAmount(this.itemDetailsUtil);
			StringBuffer output = new StringBuffer("Please collect your "+amount+"$ of amount \n Please return the following items \n");
			Map<String,Integer> boughtItems = this.vendingCalutil.getDispensedItemList();
                        boughtItems.entrySet().stream().map((entry) -> {
                            output.append(entry.getKey()).append("  ").append(entry.getValue());
                        return entry;
                    }).forEach((_item) -> {
                        output.append("\n");
                    });
			JOptionPane.showMessageDialog(null, output);
		}else{
			
		}
	}
	
	
}
