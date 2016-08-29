package business;



public class VendingItem {
	private boolean isChangeItem;
	private int itemCount;
	private String itemName;
	private float amount;
	
	public VendingItem(){
		
	}
	
	public VendingItem(boolean isChangeItem, int itemCount, String itemName, float amount){
		this.isChangeItem = isChangeItem;
		this.itemCount = itemCount;
		this.itemName = itemName;
		this.amount = amount;
	}	
	
	public boolean isChangeItem() {
		return isChangeItem;
	}

	public void setChangeItem(boolean isChangeItem) {
		this.isChangeItem = isChangeItem;
	}

	public int getItemCount() {
		return itemCount;
	}
	
	public void setItemCount(int itemCount) {
		this.itemCount = itemCount;
	}
	
	public String getItemName() {
		return itemName;
	}
	
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	
	public float getAmount() {
		return amount;
	}
	
	public void setAmount(float amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return  itemName+ "   " +amount+"$";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(amount);
		result = prime * result + (isChangeItem ? 1231 : 1237);
		result = prime * result + itemCount;
		result = prime * result
				+ ((itemName == null) ? 0 : itemName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VendingItem other = (VendingItem) obj;
		if (Float.floatToIntBits(amount) != Float.floatToIntBits(other.amount))
			return false;
		if (isChangeItem != other.isChangeItem)
			return false;
		if (itemCount != other.itemCount)
			return false;
		if (itemName == null) {
			if (other.itemName != null)
				return false;
		} else if (!itemName.equals(other.itemName))
			return false;
		return true;
	}	
}
