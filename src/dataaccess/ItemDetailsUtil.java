package dataaccess;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import business.VendingItem;

public final class ItemDetailsUtil {
	private List<VendingItem> itemsList = new ArrayList<>();
	private List<VendingItem> changeList = new ArrayList<>();
	
	public ItemDetailsUtil(){
		addItemsToTheList();
		addChangeToTheList();
	}
	
	public List<VendingItem> getItemsList() {
		return itemsList;
	}
	public void setItemsList(List<VendingItem> itemsList) {
		this.itemsList = itemsList;
	}
	public List<VendingItem> getChangeList() {
		return changeList;
	}
	public void setChangeList(List<VendingItem> changeList) {
		this.changeList = changeList;
	}
	
	public void addItemsToTheList(){
		VendingItem item;
		item = new VendingItem(false, 5, "Pepsi", 1.25f);
		this.itemsList.add(item);
		item = new VendingItem(false, 5, "Moutain Dew", 1.25f);
		this.itemsList.add(item);
		item = new VendingItem(false, 5, "Water", 1.00f);
		this.itemsList.add(item);
		item = new VendingItem(false, 5, "Dr Pepper", 1.25f);
		this.itemsList.add(item);
		item = new VendingItem(false, 5, "Cheetos", 1.00f);
		this.itemsList.add(item);
		item = new VendingItem(false, 5, "Ruffles", 1.00f);
		this.itemsList.add(item);
		item = new VendingItem(false, 5, "Lays", 1.00f);
		this.itemsList.add(item);
	}
	
	public void addChangeToTheList(){
		VendingItem item;
		item = new VendingItem(true, 20, "quarters", 0.25f);
		this.changeList.add(item);
		item = new VendingItem(true, 20, "dimes", 0.10f);
		this.changeList.add(item);
		item = new VendingItem(true, 20, "nickels", 0.05f);
		this.changeList.add(item);
	}
	
	// This method is used to get a particular item from the vending machine based on the item selected.
	public VendingItem getVendingItem(List<VendingItem> itemsList, String itemName){
		VendingItem item = null;
		for(VendingItem items : itemsList){
			if(itemName.equalsIgnoreCase(items.getItemName())){
				item = items;
				break;
			}
		}
		return item;
	}	
	
	// This method is used to add the given count of item to the existing item list.
	public VendingItem addExistingItem(String itemName, int count){
		VendingItem item = getVendingItem(this.itemsList, itemName);
		if(item == null){
			item = getVendingItem(this.changeList, itemName);
		}
		item.setItemCount(item.getItemCount()+count);
		System.out.println("The count of "+item.getItemName()+" present in the machine are:"+item.getItemCount());
		return item;
	}
	
	public void removeExistingItem(String itemName, int count){
		VendingItem item = getVendingItem(this.itemsList, itemName);
		item.setItemCount(item.getItemCount()-count);
		System.out.println("The count of "+item.getItemName()+" present in the machine are:"+item.getItemCount());
	}
	
	public void removeExistingChangeItem(String itemName, int count){
		VendingItem item = getVendingItem(this.changeList, itemName);
		item.setItemCount(item.getItemCount()-count);
		System.out.println("The count of "+item.getItemName()+" present in the machine are:"+item.getItemCount());
	}
	
	public VendingItem setPriceForExistingItem(String itemName, float price){
		VendingItem item = getVendingItem(this.itemsList, itemName);
		item.setAmount(price);
		System.out.println("The updated price of "+item.getItemName()+" in the machine are:"+item.getAmount());
		return item;		
	}
	
	public boolean changeAvailable(float amount, Map<String,Integer> dispensedItemList){
		int i =0;
		if(amount > this.changeList.get(0).getAmount()){
			int count = (int)Math.floor(amount/this.changeList.get(0).getAmount());
			removeExistingChangeItem(this.changeList.get(0).getItemName(),count);
			amount = amount - this.changeList.get(0).getAmount()*count;
			dispensedItemList.put(this.changeList.get(0).getItemName(), count);
			i++;
		}
		if(amount > this.changeList.get(1).getAmount()){
			int count = (int)Math.floor(amount/this.changeList.get(1).getAmount());
			removeExistingChangeItem(this.changeList.get(1).getItemName(),count);
			amount = amount - this.changeList.get(1).getAmount()*count;
			dispensedItemList.put(this.changeList.get(0).getItemName(), count);
			i++;
		}
		if(amount > this.changeList.get(2).getAmount()){
			int count = (int)Math.floor(amount/this.changeList.get(2).getAmount());
			removeExistingChangeItem(this.changeList.get(2).getItemName(),count);
			amount = amount - this.changeList.get(2).getAmount()*count;
			dispensedItemList.put(this.changeList.get(0).getItemName(), count);
			i++;
		}
		if(i>0 && amount == 0.0){
			return true;
		}else{
			return false;
		}		
	}
}
