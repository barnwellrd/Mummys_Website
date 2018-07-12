package cli;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


import domain.*;
import java.text.DecimalFormat;
import java.util.HashMap;


import services.MenuServices;
import services.OrderService;
import services.UserService;
import services.LocationService;

public class ServiceWrapper {
	
	Connection con;
	
	
	
	public ServiceWrapper(Connection con) {
		super();
		this.con = con;

	}

	public User login(String email, String password){
		
		UserService us = new UserService(con);
		User candidate = us.getByEmail(email);
		System.out.println(candidate.getFirstName());
		if(password.equals(candidate.getPassword())) return candidate;
		else return null;
	}
	
	public User register(String firstName, String lastName, String phone, String email, String password){
		//, String street, String city, String state, String country, String zip, String userStatus
		boolean result = false;
		String userId = Double.toString(Math.random()* 10001);
		String userStatusId = "1";

		User user = new User(userId,firstName,lastName,phone, email,password,userStatusId);
		UserService us = new UserService(con);
		result =  us.add(user);
		return user;
	}

	public static void printOptions(ArrayList<String> options){
		options.add("Go back");
		int count = 0;
		for(String option : options) {
			count++;
			System.out.println(count + ". " + option);
		}
		
	}
	
	public static void printMenuItems(ArrayList<Menu> menus){
		int count = 0;
		for(Menu menu: menus){
			count++;
                        String formattedString = String.format("%.02f", menu.getPrice());
			System.out.println(count +"."+ menu.getName()+" - $"+ formattedString+ "\n "+menu.getDescription());
                       // System.out.println(df.format(menu.getPrice()));
		}
		System.out.println(++count + ". Go Back");
	}
        
        public static void printLocations(ArrayList<Location> locs) {
            int count = 0;
            for(Location loc: locs) {
                    count++;
                    System.out.println(count + ". " + loc.toString());
            }
            System.out.println(++count + ". Go Back");
	}

	public static void printOrders(ArrayList<Order> orders){
		int count = 0;
		for(Order order: orders){
			count++;
			System.out.println(count + ". " + order.getPlaced_timestamp());
		}
		System.out.println(count++ + ". Go Back");
	}

	public void cancelOrder(Order order) {
		order.setDelivery_status_id("3");
		OrderService os = new OrderService(con);
		os.update(order);
	}

	public void submitOrder(Order currentOrder) {
		// TODO Auto-generated method stub
		
		currentOrder.setDelivery_status_id("0");
		OrderService os = new OrderService(con);
		os.add(currentOrder);
		
	}

	public ArrayList<Menu> getMenuItems(HashMap<String,Integer> itemCount) {
		
		MenuServices ms = new MenuServices(con);
		ArrayList<Menu> items = new ArrayList<>();
		
		
		for (String itemId:itemCount.keySet()){
			items.add(ms.getById(itemId));
		}

		return items;
	}

	public double calculateTotalPrice(HashMap<String,Integer> itemCount) {
		double total = 0;
		ServiceWrapper sw = new ServiceWrapper(con);
		ArrayList<Menu> items = sw.getMenuItems(itemCount);
		for(Menu item: items){
			total += item.getPrice()*itemCount.get(item.getId());
		}
		return total;
	}


}
