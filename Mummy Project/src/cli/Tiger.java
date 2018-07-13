package cli;

import domain.Card;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.HashMap;
import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import domain.*;
import services.*;

public class Tiger {

    public static ServiceWrapper sw;
    public static Connection con;
    public static User currentUser;
    public static Order currentOrder;
    public static Store currentStore;
    public static Card currentCard;
    public static Location currentLocation;


    static Scanner sc;

    public static void main(String[] args) {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "db_uSpring", "pass");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.err.println("Error connecting to database!");
        }
        sw = new ServiceWrapper(con);
        sc = new Scanner(System.in);
        firstScreen();
        sc.close();
    }

    public static void firstScreen() {
        System.out.println(" __  __ _                     _ _        _____       __     \n|  \\/  (_)                   (_| )      / ____|     / _|    \n| \\  / |_ _ __ ___  _ __ ___  _|/ ___  | |     __ _| |_ ___ \n| |\\/| | | '_ ` _ \\| '_ ` _ \\| | / __| | |    / _` |  _/ _ \\\n| |  | | | | | | | | | | | | | | \\__ \\ | |___| (_| | ||  __/\n|_|  |_|_|_| |_| |_|_| |_| |_|_| |___/  \\_____\\__,_|_| \\___|");
        ArrayList<String> options = new ArrayList<>();
        options.add("Login");
        options.add("Register");
        options.add("Quit");
        int count = 0;
        for (String option : options) {
            count++;
            System.out.println(count + ". " + option);
        }

        int input = sw.validateMenuOption(1, 4);
        switch (input) {
            case 1:
                loginScreen();
                break;
            case 2:
                registerScreen();
                break;
            case 3:
                System.out.println("Goodbye");
                System.exit(0);
            default:
                System.out.println("Please enter one of the options.");
                firstScreen();
            case 4:
    			AdminAndManager aam = new AdminAndManager(con);
    			aam.adminScreen(); 
        }

    }

    public static void loginScreen() {
        System.out.println("\n*Login*");
        System.out.println("Enter email:");
        String email = sw.validateEmail(sc.nextLine());
        System.out.println("Enter password:");
        String password = sw.validateNotEmpty(sc.nextLine());

        UserService us = new UserService(con);
        User candidate = us.getByEmail(email);
        if (candidate == null) {
            System.out.println("Wrong email");
            firstScreen();
        }
        if (password.equals(candidate.getPassword())) {
            if (candidate.getUserStatusId().equals("3")) {
                AdminAndManager aam = new AdminAndManager(con);
                aam.adminScreen();
            }
            currentUser = candidate;
            currentOrder = new Order();
            currentOrder.setOrder_id("1"); // just assign it order id of 1 for now.
            currentOrder.setUser_id(currentUser.getUserId());
            currentOrder.setDelivery_status_id("2");
            //currentOrder.setCard_id();
            StoreService ss = new StoreService(con);
            currentStore = ss.getById("0");
            homeScreen();
        } else {
            System.out.println("Wrong email or password");
            try {
                TimeUnit.SECONDS.sleep(1);
                firstScreen();
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.err.println("Application timed out!");

            }
        }

    }

    public static void registerScreen() {
        System.out.println("\n*Register*");
        System.out.println("Enter email:");
        String email = sw.validateEmail(sc.nextLine());
        System.out.println("Enter password:");
        String password = sw.validateNotEmpty(sc.nextLine());
        System.out.println("Enter password again:");
        String passwordConfirm = sw.validateNotEmpty(sc.nextLine());
        System.out.println("Enter first name:");
        String first = sw.validateNotEmpty(sc.nextLine());
        System.out.println("Enter last name:");
        String last = sw.validateNotEmpty(sc.nextLine());
        System.out.println("Enter phone:");
        String phone = sw.validatePhone(sc.nextLine());
        /*System.out.println("Enter street:");
	    String street = sc.nextLine();
		System.out.println("Enter city:");
	    String city = sc.nextLine();
		System.out.println("Enter state:");
	    String state = sc.nextLine();
		System.out.println("Enter country:");
	    String country = sc.nextLine();
		System.out.println("Enter zip:");
	    String zip = sc.nextLine();
		System.out.println("Enter status:");
	    String status = sc.nextLine();*/
        //, street, city, state, country, zip, status
        if (password.equals(passwordConfirm)) {
            System.out.println("Registered");
            currentUser = sw.register(first, last, phone, email, password);
            currentOrder = new Order();
            currentOrder.setOrder_id(Double.toString(Math.random() * 10001));
            currentOrder.setUser_id(currentUser.getUserId());
            currentOrder.setDelivery_status_id("2");
            homeScreen();
        } else {
            System.out.println("Mismatching passwords, try again");
            firstScreen();
        }

    }

    public static void homeScreen() {
        System.out.println("Welcome " + currentUser.getFirstName());
        System.out.println("\n*Home*");
        ArrayList<String> options = new ArrayList<>();
        options.add("Menu");
        options.add("Order");
        options.add("Account");
        options.add("Store Details");
        options.add("Logout");
        options.add("Quit");
        int count = 0;
        for (String option : options) {
            count++;
            System.out.println(count + ". " + option);
        }
        boolean isOk = true;
        while(isOk) {
            int input = sw.validateMenuOption(1, 6);
            switch(input) {
                case 1:
                    menuScreen();
                    break;
                case 2:
                    currentOrderScreen();
                    break;
                case 3:
                    accountScreen();
                    break;
                case 4:
                    storeDetailsScreen();
                    break;
                case 5:
                    firstScreen();
                    break;
                case 6:
                    System.out.println("Goodbye");
                    System.exit(0);
            }
            isOk=false;
        }
    }

    public static void menuScreen() {
        System.out.println("\n*Menu*");
        MenuServices ms = new MenuServices(con);
        ArrayList<Menu> menus = ms.getAll();
        ServiceWrapper.printMenuItems(menus);
        
        boolean isOk = true;
        while(isOk) {
            int input = sw.validateMenuOption(1, (menus.size()+1));
            if (input == menus.size() + 1) {
                homeScreen();
            } else {
                menuItemScreen(menus.get(input - 1));
            }
            isOk=false;
        }
    }

    public static void menuItemScreen(Menu menu) {
        System.out.println("\n*" + menu.getName() + "*");
        System.out.println(menu.getDescription());
        String formattedString = String.format("%.02f", menu.getPrice());
        System.out.println("$" + formattedString);
        System.out.println("1. Enter Quantity");
        System.out.println("2. Go Back");
        
        boolean isOk = true;
        while(isOk) {
            int input = sw.validateMenuOption(1, 2);
            if (input == 1) {
                itemQuantityScreen(menu);
            } else if (input == 2) {
                menuScreen();
            }
            isOk=false;
        }
    }
    //TODO finish this

    public static void itemQuantityScreen(Menu menu) {
        System.out.println("Enter Quantity");
        boolean isOk = true;
        int input = 0;
        while(isOk) {
            input = sw.validateQuantityOption();
            sc.nextLine();
        }
        
        for (int i = 0; i < input; i++) {
            currentOrder.addItem_id(menu.getId());
        }
        System.out.println("Item(s) added");
        menuScreen();
    }

    public static void currentOrderScreen() {
        System.out.println("\n*Current Order*");
        System.out.println("Placed: " + currentOrder.getPlaced_timestamp());
        System.out.println("Deliver On: " + currentOrder.getDelivery_timestamp());
        //ServiceWrapper sw = new ServiceWrapper(con);
        currentOrder.setTotal_price((float) sw.calculateTotalPrice(currentOrder.getItemCount()));
        double delivery_fee=0;
        if(currentOrder.getDelivery_method_id().equals("1")){
            delivery_fee=5.0;
        }
        String formattedString = String.format("%.02f", currentOrder.getTotal_price()+currentOrder.getTip()+delivery_fee);
        
	System.out.println("Total price: $" +formattedString);        
        System.out.println("Method: " + currentOrder.getDelivery_method_id());
        System.out.println("Status: " + currentOrder.getDelivery_status_id());
        System.out.println("1. Cancel");
        System.out.println("2. View\\Edit Items");
        System.out.println("3. Edit Order");
        System.out.println("4. Submit Order");
        System.out.println("5. Go Back");
        int input = sw.validateMenuOption(1,5);
        if (input == 1 && confirm()) {
            currentOrder = new Order();
            currentOrder.setOrder_id("1"); // set id=1 for now
            currentOrder.setUser_id(currentUser.getUserId());
            currentOrder.setDelivery_status_id("2");
            homeScreen();
        } else if (input == 2) {
            viewEditOrderItems(currentOrder);
        } else if (input == 3) {
            editOrder(currentOrder);
        } else if (input == 4 && confirm()) {
            if(currentOrder.getItemCount().isEmpty()) {
                System.out.println("Cart is empty.");
                currentOrderScreen();
            } else {
                currentOrder.setCard_id(submitOrder());
                sw.submitOrder(currentOrder);
               // System.out.println("Order Complete");
                currentOrder = new Order();
                currentOrder.setOrder_id("1"); // set id=1 for now
                currentOrder.setUser_id(currentUser.getUserId());
                currentOrder.setDelivery_status_id("2");
                homeScreen();
            }
        } else if (input == 5) {
            homeScreen();
        } else {
            currentOrderScreen();
        }
    }

    private static void editOrder(Order currentOrder2) {
        System.out.println("\n*Edit Order*");
        ArrayList<String> options = new ArrayList<>();
        options.add("Edit Tip");
       // options.add("Edit delivery time");
        options.add("Edit Instructions");
        options.add("Edit Delivery Method");
        options.add("Edit Store");
        options.add("Go Back");
        int count = 0;
        for (String option : options) {
            count++;
            System.out.println(count + ". " + option);
        }
        boolean isOk=true;
        while(isOk) {
            int input = sw.validateMenuOption(1, options.size());
            switch (input) {
                case 1:
                    System.out.println("Enter Tip Amount:");
                    double newTip=sw.validateMoney(sc.nextLine());
                    currentOrder.setTip(newTip);
                    System.out.println("Tip Changed to: $" + newTip);
                    break;
                case 2:
                    String newInstructions = sw.validateNotEmpty(sc.nextLine());
                    currentOrder.setInstuctions(newInstructions);
                    System.out.println("Instructions Changed to: " + newInstructions);
                    break;
                case 3:
                    System.out.println("Pick one of the options below.");
                    System.out.println("1. Deliver Food");
                    System.out.println("2. Pick up");
                    System.out.println("3. Dine in");
                    int input2=sw.validateMenuOption(1,3);
                    if(input2==1) {
                        getLocation();
                        System.out.println("Pick a delivery time.");
                        String newDelivery_timestamp = sw.validateTime(sc.nextLine());
                        currentOrder.setDelivery_timestamp(Integer.getInteger(newDelivery_timestamp));
                        System.out.println("Delivery Time Changed to: " + newDelivery_timestamp);
                    }   String newDelivery_method = Integer.toString(input2);
                    //String newDelivery_method = editString();
                    currentOrder.setDelivery_method_id(newDelivery_method);
                    System.out.println("Delivery Method Changed to: " + newDelivery_method);
                    break;
                case 4:
                    StoreService stores = new StoreService(con);
                    ArrayList<Store> sl = stores.getAll();
                    ArrayList<String> s_ids = new ArrayList<>();
                    System.out.println("Store id\t"+"Store Details");
                    for (Store s : sl) {
                        System.out.println(s.getStoreId()+".\t\t"+s.toString());
                        s_ids.add(s.getStoreId());
                    }   System.out.println("Pick the store id of the store that is closest to you.");
                    boolean isOk2=true;
                    String newStore="0";
                    while(isOk2) {
                        newStore = sw.validateNotEmpty(sc.nextLine());
                        if(s_ids.contains(newStore)) {
                            isOk2=false;
                        }
                        System.out.println("Please pick the right store id.");
                    }   currentOrder.setStore_id(newStore);
                    System.out.println("Store Changed to: " + newStore);
                    break;
                case 5:
                    homeScreen();
                    break;
                default:
                    break;
            }
            isOk=false;
        }

        currentOrderScreen();

    }

    //TODO get item from item id here
    private static void viewEditOrderItems(Order order) {
        System.out.println("*View Items*");
        
        boolean isOk=true;
        while(isOk) {
            HashMap<String,Integer> itemCount = currentOrder.getItemCount();
            ArrayList<Menu> items = sw.getMenuItems(itemCount);
            int count = 0;

            if (items.isEmpty()) {
                System.out.println("No items");
                System.out.println((items.size()+1) + ". Go Back");
                while(isOk)
                {
                    int input = sw.validateMenuOption(1, (items.size()+1));
                    if(input == (items.size()+1))
                        currentOrderScreen();
                }
            }
            for(Menu item: items){
                count++;
                String formattedString = String.format("%.02f", 
                        item.getPrice()*itemCount.get(item.getId()));
                System.out.println(count +". "+ item.getName()+ "("+itemCount.get(item.getId())+
                        ")\t$"+ formattedString+ "\t "+item.getDescription());
               // System.out.println(df.format(menu.getPrice()));
            }
            System.out.println((items.size()+1) + ". Go Back");
            int input = sw.validateMenuOption(1, (items.size()+1));
            if(input < items.size()+1){
                System.out.println("Enter a new quantity");
                int newQuantity = sw.validateQuantityOption();
                currentOrder.setItemQuantity(items.get(input-1).getId(), newQuantity);
            }
            else if(input == items.size()+1) {
                currentOrderScreen();
                isOk=false;
            }
            else {
                System.out.println("Please type in a valid number to go back.");
            }
        }
    }

    public static void orderItemScreen(Menu menu) {
        /*System.out.println(menu.getName());
		System.out.println(menu.getDescription());
		System.out.println(menu.getPrice());
		System.out.println("1. Enter Quantity");
		System.out.println("2. Go Back");
		Scanner sc = new Scanner(System.in);
	    int input = sc.nextInt();
        sc.nextLine();
	    if(input==1) itemQuantityScreen(menu);
	    else if(input==2) System.exit(0);*/
    }

    //TODO
    public static String submitOrder() {
        System.out.println("1. Pay in store.");
        System.out.println("2. Pay now using credit card.");
        int pay = sw.validateMenuOption(1,2);
        String cardId="-1";
        if(pay==2) {
            try {
                CallableStatement getCreditCards = con.prepareCall(
                                            "{?=call getCreditCard(?)}");
                getCreditCards.setString(2,currentUser.getUserId());
                getCreditCards.registerOutParameter(1, Types.VARCHAR);
                getCreditCards.execute();
                cardId = getCreditCards.getString(1);
                if(cardId.equals("-1")) {
                    System.out.println("You don't have a saved card.");
                    addCard(currentUser.getUserId()); // add a card
                } else {
                    // they do have a credit card.
                    System.out.println("Here is the saved card information.");
                    CardService cw = new CardService(con);
                    currentCard = cw.getById(cardId);
                    System.out.println(currentCard.toString());
                    boolean isOk=true;
                    System.out.println("1. Use this card.");
                    System.out.println("2. Replace this card.");
                    int in = sw.validateMenuOption(1,2);
                    if(in==2) {
                        deleteCard(cardId);
                        cardId=addCard(currentUser.getUserId()); // add/replace
                    } else {
                        System.out.println("Using this card now.");
                    }

                }
            } catch(SQLException e) {
                System.out.println(e.getMessage());
                System.err.println("Error executing query!");
            }
        }
        
        System.out.println("Order Complete");
        //OrderService os = new OrderService(con);
        //input should be equal to number of items in order
        //Menu menu = null;
        // int input = 0;
        //for(int i=0;i<input;i++){
        //create order item and add to item
        //os.addItem_id(menu.getId(), currentOrder.getOrder_id());
        // }
        //OrderService os = new OrderService(con);
        //os.update(currentOrder);
        return cardId;
    }

    //TODO
    public static String getLocation() {
        String loc_id="-1";
        try {
            CallableStatement getLocation = con.prepareCall(
					"{?=call getLocationId(?)}");
            getLocation.setString(2,currentUser.getUserId());
            getLocation.registerOutParameter(1, Types.VARCHAR);
            getLocation.execute();
            loc_id = getLocation.getString(1);
            if(loc_id.equals("-1")) {
                System.out.println("You don't have a saved address.");
                loc_id=addLocation(currentUser.getUserId()); // add a address
            } else {
                // they do have a saved location.
                System.out.println("Here is the saved address.");
                LocationService ls = new LocationService(con);
                currentLocation = ls.getById(loc_id);
                viewLocationScreen(currentLocation);
                boolean isOk=true;
                System.out.println("1. Use this address.");
                System.out.println("2. Change address.");
                while(isOk) {
                    int input = sw.validateMenuOption(1, 2);
                    if(input==2) {
                        deleteLocation(loc_id);
                        loc_id=addLocation(currentUser.getUserId()); // add/replace
                    } else {
                        System.out.println("Using this card now.");
                    }
                    isOk=false;
                }
                
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
            System.err.println("Error executing query!");
        }
        System.out.println("Address Added");
        //OrderService os = new OrderService(con);
        //input should be equal to number of items in order
        //Menu menu = null;
        // int input = 0;
        //for(int i=0;i<input;i++){
        //create order item and add to item
        //os.addItem_id(menu.getId(), currentOrder.getOrder_id());
        // }
        //OrderService os = new OrderService(con);
        //os.update(currentOrder);
        return loc_id;
    }
    
    public static String addCard(String uid) {
        System.out.println("Enter your credit card info.");
        String id = "1"; // make id = 1 for now
        String userid = uid;
        System.out.println("Enter card number: ");
        String card_num = sw.validateCC(sc.nextLine());
        System.out.println("Enter card expiration date year (YY)");
        int year = sw.validateYear(sc.nextLine());
        System.out.println("Enter card expiration date month (MM)");
        int month = sw.validateMonth(sc.nextLine());
        System.out.println("Enter card expiration date day (DD)");
        int d = sw.validateDay(sc.nextLine());
        sc.nextLine();
        SimpleDateFormat ft = new SimpleDateFormat ("yy-MM-dd"); 
       // String input = args.length == 0 ? "1818-11-11" : args[0]; 
        String date = (Integer.toString(year)+"-"+Integer.toString(month)+"-"
                +Integer.toString(d));
      //  System.out.print(input + " Parses as "); 
        Date t=null;
        System.out.println("Enter security code.");
        String sec = sw.validatCVC(sc.nextLine());
        Card c = new Card(id,userid,card_num,t,sec);
        CardService cw = new CardService(con);
        cw.add(c);
        return id;
    }
    
    public static void deleteCard(String cid) {
        CardService cw = new CardService(con);
        cw.deleteById(cid);
    }
    
    public static String addLocation(String uid) {
        System.out.println("Enter your address.");
        String id = "1"; // make id = 1 for now
        String userid = uid;
        String tax_rate="0.1";
        System.out.println("Enter street: ");
        String street = sw.validateNotEmpty(sc.nextLine());
        System.out.println("Enter city");
        String city = sw.validateNotEmpty(sc.nextLine());
        System.out.println("Enter state");
        String state = sw.validateNotEmpty(sc.nextLine());
        System.out.println("Enter Country");
        String country = sw.validateNotEmpty(sc.nextLine());
        System.out.println("Enter zip");
        String zip = sw.validateZipCode(sc.nextLine());
        
        
        Location l = new Location(id,userid,tax_rate,street,city,state,
            country,zip);
        LocationService ls = new LocationService(con);
        ls.add(l);
        return id;
    }
    
    public static void deleteLocation(String lid) {
        LocationService ls = new LocationService(con);
        ls.deleteById(lid);
    }
    
    public static void accountScreen() {
        System.out.println("\n*Account*");
        ArrayList<String> options = new ArrayList<>();
        options.add("Edit First Name");
        options.add("Edit Last Name");
        options.add("Edit Email");
        options.add("Edit Password");
        options.add("Edit Phone Number");
        options.add("Edit Payment Options");
        options.add("Edit Saved Locations");
        options.add("View Orders");
        options.add("Go Back");
        int count = 0;
        for (String option : options) {
            count++;
            System.out.println(count + ". " + option);
        }
        boolean isOk=true;
        while(isOk) {
            int input = sw.validateMenuOption(1, options.size());
            switch (input) {
                case 1:
                    String newFirstName = sw.validateNotEmpty(sc.nextLine());
                    currentUser.setFirstName(newFirstName);
                    System.out.println("First Name Changed to: " + newFirstName);
                    break;
                case 2:
                    String newLastName = sw.validateNotEmpty(sc.nextLine());
                    currentUser.setLastName(newLastName);
                    System.out.println("Last Name Changed to: " + newLastName);
                    break;
                case 3:
                    String newEmail = sw.validateEmail(sc.nextLine());
                    currentUser.setEmail(newEmail);
                    System.out.println("Email Changed to: " + newEmail);
                    break;
                case 4:
                    String newPassword = sw.validateNotEmpty(sc.nextLine());
                    currentUser.setPassword(newPassword);
                    System.out.println("Password Changed to: " + newPassword);
                    break;
                case 5:
                    String newPhoneNumber = sw.validatePhone(sc.nextLine());
                    currentUser.setPhone(newPhoneNumber);
                    System.out.println("Phone Number Changed to: " + newPhoneNumber);
                    break;
                case 6:
                    editCards();
                    break;
                case 7:
                    viewAllLocations();
                    break;
                case 8:
                    allOrdersScreen();
                    break;
                case 9:
                    homeScreen();
                    break;
                default:
                    break;
            }
            isOk=false;
        }
        

        UserService us = new UserService(con);
        us.update(currentUser);
        accountScreen();
    }

    private static void viewAllLocations() {
        System.out.println("\n*All Locations*");
        LocationService ls = new LocationService(con);
        ArrayList<Location> locations = ls.getUserLocations(currentUser.getUserId());
        if(locations.size() > 0){
            System.out.println("Here are all your saved locations:");

        } else{
            System.out.println("You have no saved locations!");
        }
        ServiceWrapper.printLocations(locations);
        boolean isOk=true;
        
        while(isOk) {
            int input = sw.validateMenuOption(1, (locations.size()+1));
            if (input == locations.size()+1) {
                accountScreen();
            } else {
                viewLocationScreen(locations.get(input-1));
            }
            isOk=false;
        }

    }

    private static void editCards() {
        // TODO Auto-generated method stub

    }

    public static void allOrdersScreen() {
        System.out.println("\n*All orders*");
        OrderService os = new OrderService(con);
        ArrayList<Order> orders = os.getUserOrders(currentUser.getUserId());
        ServiceWrapper.printOrders(orders);
        boolean isOk=true;
        System.out.println("Here are all the orders.");
        while(isOk) {
            int input = sw.validateMenuOption(1, orders.size());
            if (input == orders.size()) {
                homeScreen();
            } else {
                oldOrderScreen(orders.get(input));
            }
            isOk=false;
        }
        
    }

    public static void oldOrderScreen(Order order) {
        System.out.println("Placed: " + order.getPlaced_timestamp());
        System.out.println("Delivered: " + order.getDelivery_timestamp());
        System.out.println("Total price: " + order.getTotal_price());
        System.out.println("Method: " + order.getDelivery_method_id());
        System.out.println("Status: " + order.getDelivery_status_id());
        System.out.println("1. Reorder");
        System.out.println("2. Go Back");
        boolean isOk=true;
        while(isOk) {
            int input = sw.validateMenuOption(1, 2);
            if (input == 1 && confirm()) {
                currentOrder = order;
                //TODO find out what the status id this thing needs is
                currentOrder.setDelivery_status_id("1");
            } else if (input == 2) {
                accountScreen();
            } else {
                allOrdersScreen();
            }
        }
    }

    public static void storeDetailsScreen() {
        System.out.println("\n*Store*");
        System.out.println("Name: " + currentStore.getStoreName());
        System.out.println("Phone Number: " + currentStore.getPhoneNumber());
        System.out.println("Location: " + currentStore.getLocationId());
        System.out.println("Open: " + currentStore.getOpenTime());
        System.out.println("Close: " + currentStore.getCloseTime());
        homeScreen();
    }

    public static boolean confirm() {
        System.out.println("\n1*Confirm*");
        System.out.println("1. Yes");
        System.out.println("2. No");
        int input = sw.validateMenuOption(1,2);
        return input == 1;
    }
    
    public static void viewLocationScreen(Location location) {
        System.out.println("Street: " + location.getStreet());
        System.out.println("City: " + location.getCity());
        System.out.println("State: " + location.getState());
        System.out.println("Country: " + location.getCountry());
        System.out.println("Zip: " + location.getZip());
       
        System.out.println("1. Set as current location");
        System.out.println("2. Edit this location");
        System.out.println("3. Go Back");
        boolean isOk=true;
        while(isOk) {
            int input = sw.validateMenuOption(1, 3);
            switch (input) {
                case 1:
                    currentLocation = location;
                    System.out.println("Current location updated!");
                    viewLocationScreen(location);
                    break;
                case 2:
                    editLocationScreen(location);
                    LocationService ls = new LocationService(con);
                    ls.updateLoc(location);
                    System.out.println("Location updated!");
                    viewLocationScreen(location);
                    break;
                case 3:
                    viewAllLocations();
                    break;
                default:
                    break;
            }
        }
       
    }

    private static void editLocationScreen(Location location) {
            System.out.println("Enter street:");
	    String street = sw.validateNotEmpty(sc.nextLine());
            location.setStreet(street);
            System.out.println("Enter city:");
	    String city = sw.validateNotEmpty(sc.nextLine());
            location.setCity(city);
            System.out.println("Enter state:");
	    String state = sw.validateNotEmpty(sc.nextLine());
            location.setState(state);
            System.out.println("Enter country:");
	    String country = sw.validateNotEmpty(sc.nextLine());
            location.setCountry(country);
            System.out.println("Enter zip:");
	    String zip = sw.validateZipCode(sc.nextLine());
            location.setZip(zip);   
    }
}
