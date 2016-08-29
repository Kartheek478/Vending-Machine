package dataaccess;



import java.util.HashMap;
import java.util.Map;
import business.*;

public class VendingCalculationUtil {
	public Map<String,Integer> dispensedItemList = new HashMap<>();
	
	public String getItems(String itemName, ItemDetailsUtil itemUtil, float amount){
		String returnMsg = null;
		dispensedItemList = new HashMap<>();
		VendingItem item = itemUtil.getVendingItem(itemUtil.getItemsList(), itemName);
		if(item.getAmount() > amount){
			returnMsg = "Enter additional ONE DOLLAR for this item";
		}else if(item.getItemCount() == 0){
			returnMsg = item.getItemName()+" is running out of STOCK";
		}else{			
			int count = (int)Math.floor(amount/item.getAmount());
			dispensedItemList.put(itemName, count);			
			itemUtil.removeExistingItem(itemName, count);
			amount = amount - count*item.getAmount();
			if(amount > 0){
				boolean changeAvailable = itemUtil.changeAvailable(amount,dispensedItemList);
				if(!changeAvailable){
					returnMsg = "Machine is running out of STOCK on change";
				}					
			}
		}
		return returnMsg;
	}

	public Map<String, Integer> getDispensedItemList() {
		return dispensedItemList;
	}

	public void setDispensedItemList(Map<String, Integer> dispensedItemList) {
		this.dispensedItemList = dispensedItemList;
	}

	public float refundAmount(ItemDetailsUtil itemUtil){
		float amount = 0.0f;
		for(Map.Entry<String,Integer> entry:this.dispensedItemList.entrySet()){
			VendingItem item = itemUtil.addExistingItem(entry.getKey(),entry.getValue());
			amount = amount+item.getAmount()*entry.getValue();
		}
		return amount;
	}
	
}
