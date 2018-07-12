package domain;

import java.util.ArrayList;
import java.util.HashMap;

public class Order {
	
	String order_id; //varchar
	String user_id; //varchar
	float tip; //number(5,2)
	float total_price;//number(7,2)
	int placed_timestamp; //int
	int delivery_timestamp; //int
	String card_id; //varchar
	String instuctions; //carchar
	String delivery_method_id; //varchar
	String store_id; //varchar
	String delivery_status_id; //varchar
	
	//Array to hold order items rather than the order_items table
	HashMap<String,Integer> itemCount;

	
	
	public Order(String order_id, String user_id, float tip, float total_price, int placed_timestamp,
			int delivery_timestamp, String card_id, String instuctions, String delivery_method_id, String store_id,
			String delivery_status_id, HashMap<String,Integer> itemCount) {
		super();
		this.order_id = order_id;
		this.user_id = user_id;
		this.tip = tip;
		this.total_price = total_price;
		this.placed_timestamp = placed_timestamp;
		this.delivery_timestamp = delivery_timestamp;
		this.card_id = card_id;
		this.instuctions = instuctions;
		this.delivery_method_id = delivery_method_id;
		this.store_id = store_id;
		this.delivery_status_id = delivery_status_id;
		this.itemCount = itemCount;
	}

	public Order() {
		super();
		this.tip = 0;
		this.total_price = 0;
		this.placed_timestamp = 0;
		this.delivery_timestamp = 0;
		this.instuctions = "";
		this.delivery_method_id = "0";
		this.store_id = "0";
		this.delivery_status_id = "0";
                this.itemCount = new HashMap<String,Integer>();
	}
	
	public HashMap<String,Integer> getItemCount() {
		return itemCount;
	}

	public void setItemCount(HashMap<String,Integer> itemCount) { 
		this.itemCount = (HashMap<String,Integer>) itemCount.clone();
	}
	public void addItem_id(String item_id){
            if(itemCount.containsKey(item_id)){
                int newValue = itemCount.get(item_id)+1;
                itemCount.replace(item_id, newValue);
            }
            else{
                itemCount.put(item_id,1);
            }
	}
	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getUser_id() {
		return user_id;
	}
        
        public void setItemQuantity(String itemId, int quantity){
            if(!itemCount.containsKey(itemId) && quantity>0){
                itemCount.put(itemId,quantity);
            }
            else if(quantity == 0){
                itemCount.remove(itemId);
            }
            else{
                itemCount.replace(itemId,quantity);
            }
        }

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public float getTip() {
		return tip;
	}

	public void setTip(float tip) {
		this.tip = tip;
	}

	public float getTotal_price() {
		return total_price;
	}

	public void setTotal_price(float total_price) {
		this.total_price = total_price;
	}

	public int getPlaced_timestamp() {
		return placed_timestamp;
	}

	public void setPlaced_timestamp(int placed_timestamp) {
		this.placed_timestamp = placed_timestamp;
	}

	public int getDelivery_timestamp() {
		return delivery_timestamp;
	}

	public void setDelivery_timestamp(int delivery_timestamp) {
		this.delivery_timestamp = delivery_timestamp;
	}

	public String getCard_id() {
		return card_id;
	}

	public void setCard_id(String card_id) {
		this.card_id = card_id;
	}

	public String getInstuctions() {
		return instuctions;
	}

	public void setInstuctions(String instuctions) {
		this.instuctions = instuctions;
	}

	public String getDelivery_method_id() {
		return delivery_method_id;
	}

	public void setDelivery_method_id(String delivery_method_id) {
		this.delivery_method_id = delivery_method_id;
	}

	public String getStore_id() {
		return store_id;
	}

	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}

	public String getDelivery_status_id() {
		return delivery_status_id;
	}

	public void setDelivery_status_id(String delivery_status_id) {
		this.delivery_status_id = delivery_status_id;
	}

	@Override
	public String toString() {
		return "Order [order_id=" + order_id + ", user_id=" + user_id + ", tip=" + tip + ", total_price=" + total_price
				+ ", placed_timestamp=" + placed_timestamp + ", delivery_timestamp=" + delivery_timestamp + ", card_id="
				+ card_id + ", instuctions=" + instuctions + ", delivery_method_id=" + delivery_method_id
				+ ", store_id=" + store_id + ", delivery_status_id=" + delivery_status_id + ", item_ids=" + itemCount
				+ "]";
	}

	

	
}
