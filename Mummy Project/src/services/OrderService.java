package services;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import domain.Order;
import java.util.HashMap;
import java.sql.Types;


public class OrderService implements Service<Order>{
	/*
	ArrayList<Integer> item_ids = new ArrayList<Integer>();
	*/
	
	Connection connection;
	
	public OrderService() {
		super();
	}

	public OrderService(Connection connection) {
		super();
		this.connection = connection;
	}

	@Override
	public boolean add(Order order){
		try{
                        //get order id
                        CallableStatement getOrderId = connection.prepareCall(
					"{?=call AssignOrderId}");
                        getOrderId.registerOutParameter(1, Types.VARCHAR);
                        getOrderId.execute();
			String orderId = getOrderId.getString(1);
                        order.setOrder_id(orderId);
                        // check for delivery method
                        if(order.getDelivery_method_id() == "0") {
                            order.setDelivery_method_id("2"); // default option is pickup
                        }
			//Add order items
			CallableStatement statement = connection.prepareCall(
					"{call AddOrder(?,?,?,?,?,?,?,?,?,?,?)}");
			statement.setString("ORDER_ID",order.getOrder_id());
			statement.setString("USER_ID",order.getUser_id());
			statement.setFloat("TIP",order.getTip());
			statement.setFloat("TOTAL_PRICE",order.getTotal_price());
			statement.setInt("PLACED_TIMESTAMP",order.getPlaced_timestamp());
			statement.setInt("DELIVERY_TIMESTAMP",order.getDelivery_timestamp());
			statement.setString("CARD_ID",order.getCard_id());
			statement.setString("INSTRUCTIONS",order.getInstructions());
			statement.setString("DELIVERY_METHOD_ID",order.getDelivery_method_id());
			statement.setString("STORE_ID",order.getStore_id());
			statement.setString("DELIVERY_STATUS_ID",order.getDelivery_status_id());
			statement.executeQuery();
			statement.close();
			
			//Add all items in order to order_items
			HashMap<String,Integer> itemCount = order.getItemCount();
			for (String item_id: itemCount.keySet()){
                            for(int i=0; i<itemCount.get(item_id);i++){
				statement = connection.prepareCall(
						"{call AddOrderItem(?,?)}");
				statement.setString(1, order.getOrder_id());
				statement.setString(2, item_id);
				statement.execute();
				statement.close();
                            }
			}
			return true;
		}catch(SQLException e){
			System.out.println(e.getMessage());
                        System.err.println("Error executing query!");
			return false;
		}	
	}
	/*
	@Override
	public void deleteById(String id){
		try{
			//Delete order_items
			CallableStatement statement = connection.prepareCall(
					"{call DeleteOrderItems(?)}");
			statement.setString(1,id);
			statement.execute();
			statement.close();
			
			//Delete order 
			statement = connection.prepareCall(
					"{call DeleteOrder(?)}");
			
			statement.setString(1,id);
			statement.execute();
			statement.close();
			
		}catch(SQLException e){
			System.out.println(e.getMessage());
                        System.err.println("Error executing query!");
		}	
	}
        */
        public void deleteById(String id){
		try {
                    connection.createStatement().executeQuery("DELETE FROM order_items WHERE order_id = " + id);
                    connection.createStatement().executeQuery("DELETE FROM orders WHERE order_id = " + id);
                    
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
                        System.err.println("Error executing query!");
		}
	}
	
	@Override
	public ArrayList<Order> getAll(){
		ArrayList<Order> orders = new ArrayList<Order>();
		Order order;
		ArrayList<String> order_items = new ArrayList<String>();
		try{
			//Get Order
			Statement statement = connection.createStatement();
			ResultSet resultSetOrders = statement.executeQuery("SELECT * FROM ORDERS");
			
			ResultSet resultSetItems;
			while(resultSetOrders.next()){
				//fetch all order items
				statement = connection.createStatement();
				resultSetItems = statement.executeQuery(
						"SELECT * FROM ORDER_ITEMS WHERE ORDER_ID = " + resultSetOrders.getString("ORDER_ID"));
				order_items.clear();
				while(resultSetItems.next()){
					order_items.add(resultSetItems.getString("ITEM_ID"));
				}
				
				//Make new order
				order = new Order(resultSetOrders.getString("ORDER_ID"),
						resultSetOrders.getString("USER_ID"),
						resultSetOrders.getFloat("TIP"),
						resultSetOrders.getFloat("TOTAL_PRICE"),
						resultSetOrders.getInt("PLACED_TIMESTAMP"),
						resultSetOrders.getInt("DELIVERY_TIMESTAMP"),
						resultSetOrders.getString("CARD_ID"),
						resultSetOrders.getString("INSTRUCTIONS"),
						resultSetOrders.getString("DELIVERY_METHOD_ID"),
						resultSetOrders.getString("STORE_ID"),
						resultSetOrders.getString("DELIVERY_STATUS_ID"),
						new HashMap<String,Integer>());
                                for(String itemId:order_items){
                                    order.addItem_id(itemId);
                                }
				orders.add(order);
			}
		}catch(SQLException e){
			System.out.println(e.getMessage());
                        System.err.println("Error executing query!");
		}
		return orders;
	}
	/*
	@Override
	public void update(Order order){
		
		try{
			//Add order items
			CallableStatement statement = connection.prepareCall(
					"{call UpdateOrder(?,?,?,?,?,?,?,?,?,?,?)}");
			
                        
			statement.setString("ORDER_ID",order.getOrder_id());
			statement.setString("USER_ID",order.getUser_id());
			statement.setFloat("TIP",order.getTip());
			statement.setFloat("TOTAL_PRICE",order.getTotal_price());
			statement.setInt("PLACED_TIMESTAMP",order.getPlaced_timestamp());
			statement.setInt("DELIVERY_TIMESTAMP",order.getDelivery_timestamp());
			statement.setString("CARD_ID",order.getCard_id());
			statement.setString("INSTRUCTIONS",order.getInstructions());
			statement.setString("DELIVERY_METHOD_ID",order.getDelivery_method_id());
			statement.setString("STORE_ID",order.getStore_id());
			statement.setString("DELIVERY_STATUS_ID",order.getDelivery_status_id());
			statement.execute();
			statement.close();
			
			//remove all items from order_items 
			statement = connection.prepareCall(
					"{call DeleteOrderItems(?)}");
			statement.setString("ORDER_ID",order.getOrder_id());
			statement.execute();
			statement.close();
			
			//Add all items in order to order_items
			HashMap<String,Integer> itemCount = order.getItemCount();
			for (String item_id: itemCount.keySet()){
                            for(int i=0; i<itemCount.get(item_id);i++){
				statement = connection.prepareCall(
						"{call AddOrderItem(?,?)}");
				statement.setString(1, order.getOrder_id());
				statement.setString(2, item_id);
				statement.execute();
				statement.close();
                            }
			}
		}catch(SQLException e){
			System.out.println(e.getMessage());
                        System.err.println("Error executing query!");
		}	
	}
*/
        
        
        @Override
	public void update(Order order){
		
		try {
			PreparedStatement preStmt = connection.prepareStatement("UPDATE orders SET "
                                + "user_id=?, tip=?, total_price=?, placed_timestamp=?, "
                                + "delivery_timestamp=?, card_id=?, instructions=?, delivery_method_id=?, "
                                + "store_id=?, delivery_status_id=?  WHERE order_id=?"  );
			
			preStmt.setString(1,order.getUser_id());
			preStmt.setFloat(2,order.getTip());
			preStmt.setFloat(3,order.getTotal_price());
			preStmt.setInt(4,order.getPlaced_timestamp());
			preStmt.setInt(5,order.getDelivery_timestamp());
			preStmt.setString(6,order.getCard_id());
			preStmt.setString(7,order.getInstructions());
			preStmt.setString(8,order.getDelivery_method_id());
			preStmt.setString(9,order.getStore_id());
			preStmt.setString(10,order.getDelivery_status_id());
                        preStmt.setString(11,order.getOrder_id());
			preStmt.executeUpdate();
			//preStmt.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
                        System.err.println("Error executing query!");
		}
	}
	@Override
	public Order getById(String id) {
		Order order = new Order();
		try{
			//Get Order
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(
					"SELECT * FROM ORDERS WHERE ORDER_ID = " + id);
			resultSet.next();
			order.setOrder_id(resultSet.getString("ORDER_ID"));
			order.setUser_id(resultSet.getString("USER_ID"));
			order.setTip(resultSet.getFloat("TIP"));
			order.setTip(resultSet.getFloat("TOTAL_PRICE"));
			order.setPlaced_timestamp(resultSet.getInt("PLACED_TIMESTAMP"));
			order.setDelivery_timestamp(resultSet.getInt("DELIVERY_TIMESTAMP"));
			order.setCard_id(resultSet.getString("CARD_ID"));
			order.setInstructions(resultSet.getString("INSTRUCTIONS"));
			order.setDelivery_method_id(resultSet.getString("DELIVERY_METHOD_ID"));
			order.setStore_id(resultSet.getString("STORE_ID"));
			order.setDelivery_status_id(resultSet.getString("DELIVERY_STATUS_ID"));


			//get order items
			statement = connection.createStatement();
			resultSet = statement.executeQuery(
					"SELECT * FROM ORDER_ITEMS WHERE ORDER_ID = " + id);
			
			ArrayList<String> order_items = new ArrayList<String>();
			while(resultSet.next()){
				order_items.add(resultSet.getString("ITEM_ID"));
			}
                        for(String item_id : order_items){
                            order.addItem_id(item_id);
                        }
		}catch(SQLException e){
			System.out.println(e.getMessage());
                        System.err.println("Error executing query!");
		}	
		
		return order;
	}
	
	public ArrayList<Order> getUserOrders(String userId){
		ArrayList<Order> orders = new ArrayList<Order>();
		Order order;
		ArrayList<String> order_items = new ArrayList<String>();
		try{
			//Get Order
			Statement statement = connection.createStatement();
			ResultSet resultSetOrders = statement.executeQuery("SELECT * FROM ORDERS WHERE USER_ID = '" + userId + "'");
			
			ResultSet resultSetItems;
			while(resultSetOrders.next()){
				//fetch all order items
				statement = connection.createStatement();
				resultSetItems = statement.executeQuery(
						"SELECT * FROM ORDER_ITEMS WHERE ORDER_ID = " + resultSetOrders.getString("ORDER_ID"));
				order_items.clear();
				while(resultSetItems.next()){
					order_items.add(resultSetItems.getString("ITEM_ID"));
				}
				
				//Make new order
				order = new Order(resultSetOrders.getString("ORDER_ID"),
						resultSetOrders.getString("USER_ID"),
						resultSetOrders.getFloat("TIP"),
						resultSetOrders.getFloat("TOTAL_PRICE"),
						resultSetOrders.getInt("PLACED_TIMESTAMP"),
						resultSetOrders.getInt("DELIVERY_TIMESTAMP"),
						resultSetOrders.getString("CARD_ID"),
						resultSetOrders.getString("INSTRUCTIONS"),
						resultSetOrders.getString("DELIVERY_METHOD_ID"),
						resultSetOrders.getString("STORE_ID"),
						resultSetOrders.getString("DELIVERY_STATUS_ID"),
						new HashMap<String,Integer>());
                                for(String itemId : order_items){
                                    order.addItem_id(itemId);
                                }
				orders.add(order);
			}
		}catch(SQLException e){
			System.out.println(e.getMessage());
                        System.err.println("Error executing query!");
		}
		return orders;

	}
	public void addItem_id(String item_id, String order_id) { 
		try{
			CallableStatement statement = connection.prepareCall(
					"{call AddOrderItem(?,?)}");
			statement.setString(1, order_id);
			statement.setString(2, item_id);
			statement.execute();
			statement.close();
		}catch(SQLException e){
			System.out.println(e.getMessage());
                        System.err.println("Error executing query!");
		}
		
	}
        
        public boolean orderExist(String id){
            Order order = null;
            boolean ord = false;
            try{
                Statement orderSt = connection.createStatement();
                ResultSet orderRs = orderSt.executeQuery("Select * from orders where order_ID = " + id);

                if(orderRs.next()){
                    return ord = true;
                }else {
                    return ord = false;
                }
            }catch(Exception e){
                System.out.println(e.getMessage());
                System.err.println("Error executing query!");

            }
            if(ord){
                return true;
            }else
                return false;
            }

	
}
