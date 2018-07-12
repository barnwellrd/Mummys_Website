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
        } catch (Exception e) {
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
        ArrayList<String> options = new ArrayList<String>();
        options.add("Login");
        options.add("Register");
        options.add("Quit");
        int count = 0;
        for (String option : options) {
            count++;
            System.out.println(count + ". " + option);
        }

        while (!sc.hasNextInt()) {
            System.out.println("Please type in a number.");
            sc.nextLine();
        }
        int input = sc.nextInt();
        sc.nextLine();
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
        String email = sc.nextLine();
        System.out.println("Enter password:");
        String password = sc.nextLine();

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
            currentOrder.setDelivery_status_id("0");
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
        String email = sc.nextLine();
        System.out.println("Enter password:");
        String password = sc.nextLine();
        System.out.println("Enter password again:");
        String passwordConfirm = sc.nextLine();
        System.out.println("Enter first name:");
        String first = sc.nextLine();
        System.out.println("Enter last name:");
        String last = sc.nextLine();
        System.out.println("Enter phone:");
        String phone = sc.nextLine();
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
            currentOrder.setDelivery_status_id("0");
            homeScreen();
        } else {
            System.out.println("Mismatching passwords, try again");
            firstScreen();
        }

    }

    public static void homeScreen() {
        System.out.println("Welcome " + currentUser.getFirstName());
        System.out.println("\n*Home*");
        ArrayList<String> options = new ArrayList<String>();
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
            while (!sc.hasNextInt()) {
            System.out.println("Please type in a number.");
            sc.nextLine();
            }
            int input = sc.nextInt();
            if((input<1) || (input>options.size())) {
                System.out.println("Please type in the right value.");
                sc.nextLine();
                continue;
            }
            sc.nextLine();
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
            while (!sc.hasNextInt()) {
                System.out.println("Please type in a number.");
                sc.nextLine();
            }
            int input = sc.nextInt();
            if((input<1) || (input>(menus.size()+1))) {
                System.out.println("Please type in the right value.");
                sc.nextLine();
                continue;
            }
            sc.nextLine();
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
            while (!sc.hasNextInt()) {
                System.out.println("Please type in a number.");
                sc.nextLine();
            }
            int input = sc.nextInt();
            if((input<1) || (input>2)) {
                System.out.println("Please type in the right value.");
                sc.nextLine();
                continue;
            }
            sc.nextLine();
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
        int input=0;
        boolean isOk = true;
        while(isOk) {
            while (!sc.hasNextInt()) {
                System.out.println("Please type in a number.");
                sc.nextLine();
            }
            input = sc.nextInt();
            if(input<=0) {
                System.out.println("Quantity must be greater than 0.");
            } else
                isOk=false;
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
        System.out.println("Delivered: " + currentOrder.getDelivery_timestamp());
        ServiceWrapper sw = new ServiceWrapper(con);
        currentOrder.setTotal_price((float) sw.calculateTotalPrice(currentOrder.getItemCount()));
        String formattedString = String.format("%.02f", currentOrder.getTotal_price()+currentOrder.getTip());
	System.out.println("Total price: $" +formattedString);        
        System.out.println("Method: " + currentOrder.getDelivery_method_id());
        System.out.println("Status: " + currentOrder.getDelivery_status_id());
        System.out.println("1. Cancel");
        System.out.println("2. View\\Edit Items");
        System.out.println("3. Edit Order");
        System.out.println("4. Submit Order");
        System.out.println("5. Go Back");
        boolean isOk = true;
        while(isOk) {
            while (!sc.hasNextInt()) {
                System.out.println("Please type in a number.");
                sc.nextLine();
            }
            int input = sc.nextInt();
            if((input<1) || (input>5)) {
                System.out.println("Please type in the right value.");
                sc.nextLine();
                continue;
            }
            sc.nextLine();
            if (input == 1 && confirm()) {
                currentOrder = new Order();
                currentOrder.setOrder_id("1"); // set id=1 for now
                currentOrder.setUser_id(currentUser.getUserId());
                currentOrder.setDelivery_status_id("0");
                homeScreen();
            } else if (input == 2) {
                viewEditOrderItems(currentOrder);
            } else if (input == 3) {
                editOrder(currentOrder);
            } else if (input == 4 && confirm()) {
                if(currentOrder.getItemCount().size() == 0) {
                    System.out.println("Cart is empty.");
                    currentOrderScreen();
                } else {
                    currentOrder.setCard_id(submitOrder());
                    sw.submitOrder(currentOrder);
                   // System.out.println("Order Complete");
                    currentOrder = new Order();
                    currentOrder.setOrder_id("1"); // set id=1 for now
                    currentOrder.setUser_id(currentUser.getUserId());
                    currentOrder.setDelivery_status_id("0");
                    homeScreen();
                }
            } else if (input == 5) {
                homeScreen();
            } else {
                currentOrderScreen();
            }
            isOk=false;
        }
    }

    private static void editOrder(Order currentOrder2) {
        System.out.println("\n*Edit Order*");
        ArrayList<String> options = new ArrayList<String>();
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
            while (!sc.hasNextInt()) {
                System.out.println("Please type in a number.");
                sc.nextLine();
            }
            int input = sc.nextInt();
            
            if((input<1) || (input>options.size())) {
                System.out.println("Please type in the right value.");
                sc.nextLine();
                continue;
            }
            sc.nextLine();
            if (input == 1) {
                float newTip=0;
                String s = editString();
                try 
                {
                    newTip = Float.valueOf(s.trim()).floatValue();
                }
                catch (NumberFormatException nfe) 
                {
                    System.err.println("NumberFormatException: " + nfe.getMessage());
                    System.err.println("Error reading input!");

                }
               // int newTip = Integer.parseInt(editString());
                currentOrder.setTip(newTip);
                System.out.println("Tip Changed to: $" + newTip);
            } else if (input == 2) {
                String newInstructions = editString();
                currentOrder.setInstuctions(newInstructions);
                System.out.println("Instructions Changed to: " + newInstructions);
            } else if (input == 3) {
                System.out.println("Pick one of the options below.");
                System.out.println("1. Deliver Food");
                System.out.println("2. Pick up");
                System.out.println("3. Dine in");
                boolean isOk1=true;
                int input2=0;
                while(isOk1) {
                    while(!sc.hasNextInt()) {
                        System.out.println("Please type in a number.");
                        sc.nextLine();
                    }
                    input2=sc.nextInt();
                    if((input2<1) ||(input2>3)) {
                        System.out.println("Please type in a valid option.");
                        sc.nextLine();
                        continue;
                    }
                    isOk1=false;
                }
                sc.nextLine();
                if(input2==1) {
                    // ask the user for delivery time
                    System.out.println("Pick a delivery time.");
                    int newDelivery_timestamp = Integer.parseInt(editString());
                    currentOrder.setDelivery_timestamp(newDelivery_timestamp);
                    System.out.println("Delivery Time Changed to: " + newDelivery_timestamp);
                }
                String newDelivery_method = Integer.toString(input2);
                //String newDelivery_method = editString();
                currentOrder.setDelivery_method_id(newDelivery_method);
                System.out.println("Delivery Method Changed to: " + newDelivery_method);
            } else if (input == 4) {
                String newStore = editString();
                currentOrder.setStore_id(newStore);
                System.out.println("Delivery Method Changed to: " + newStore);
            } else if (input == 5) {
                homeScreen();
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
                    int input = 0;
                    while (!sc.hasNextInt())  {
                        System.out.println("Please type in " + (items.size()+1) +".");
                        sc.nextLine();
                    }
                    input = sc.nextInt();
                    if(input == (items.size()+1))
                        currentOrderScreen();
                    System.out.println("Please type in " + (items.size()+1) +".");
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
            while (!sc.hasNextInt()) {
                System.out.println("Please type in a number.");
                sc.nextLine();
            }
            int input = sc.nextInt();
            sc.nextLine();
            if(input < items.size()+1){
                System.out.println("Enter a new quantity");
                while (!sc.hasNextInt()) {
                System.out.println("Please type in a number.");
                sc.nextLine();
                }
                int newQuantity = sc.nextInt();
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
        String cardId="-1";
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
                while(isOk) {
                    while (!sc.hasNextInt()) {
                        System.out.println("Please type in a number.");
                        sc.nextLine();
                    }
                    int input = sc.nextInt();
                    sc.nextLine();
                    if((input<1) || (input>2)) {
                        System.out.println("Please type in the right number.");
                        continue;
                    }
                    if(input==2) {
                        deleteCard(cardId);
                        cardId=addCard(currentUser.getUserId()); // add/replace
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
    
    public static String addCard(String uid) {
        System.out.println("Enter your credit card info.");
        String id = "1"; // make id = 1 for now
        String userid = uid;
        System.out.println("Enter card number: ");
        String card_num = sc.nextLine();
        System.out.println("Enter card expiration date year (YY)");
        int year = sc.nextInt();
        System.out.println("Enter card expiration date month (MM)");
        int month = sc.nextInt();
        System.out.println("Enter card expiration date day (DD)");
        int d = sc.nextInt();
        
        SimpleDateFormat ft = new SimpleDateFormat ("yy-MM-dd"); 
       // String input = args.length == 0 ? "1818-11-11" : args[0]; 
        String date = (Integer.toString(year)+"-"+Integer.toString(month)+"-"
                +Integer.toString(d));
      //  System.out.print(input + " Parses as "); 
        Date t=null;
        System.out.println("Enter securit code.");
        String sec = sc.nextLine();
        Card c = new Card(id,userid,card_num,t,sec);
        CardService cw = new CardService(con);
        cw.add(c);
        return id;
    }
    
    public static void deleteCard(String cid) {
        CardService cw = new CardService(con);
        cw.deleteById(cid);
    }
    
    public static void accountScreen() {
        System.out.println("\n*Account*");
        ArrayList<String> options = new ArrayList<String>();
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
            while (!sc.hasNextInt()) {
                System.out.println("Please type in a number.");
                sc.nextLine();
            }
            int input = sc.nextInt();
            sc.nextLine();
            if((input<0) || (input>options.size())) {
                System.out.println("Please type in the right number.");
                continue;
            }
            if (input == 1) {
                String newFirstName = editString();
                currentUser.setFirstName(newFirstName);
                System.out.println("First Name Changed to: " + newFirstName);
            } else if (input == 2) {
                String newLastName = editString();
                currentUser.setLastName(newLastName);
                System.out.println("Last Name Changed to: " + newLastName);
            } else if (input == 3) {
                String newEmail = editString();
                currentUser.setEmail(newEmail);
                System.out.println("Email Changed to: " + newEmail);
            } else if (input == 4) {
                String newPassword = editString();
                currentUser.setPassword(newPassword);
                System.out.println("Password Changed to: " + newPassword);
            } else if (input == 5) {
                String newPhoneNumber = editString();
                currentUser.setPhone(newPhoneNumber);
                System.out.println("Phone Number Changed to: " + newPhoneNumber);
            } else if (input == 6) {
                editCards();
            } else if (input == 7) {
                viewAllLocations();
            } else if (input == 8) {
                allOrdersScreen();
            } else if (input == 9) {
                homeScreen();
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
            while (!sc.hasNextInt()) {
                System.out.println("Please type in a number.");
                sc.nextLine();
            }
            int input = sc.nextInt();
            //System.out.println(input);
            sc.nextLine();
            if((input<1) || (input>locations.size()+1)) {
                System.out.println("Please type in the right number.");
                continue;
            }
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

    private static String editString() {
        System.out.println("Enter new value");
        String inp = sc.nextLine();
        return inp;
    }

    public static void allOrdersScreen() {
        System.out.println("\n*All orders*");
        OrderService os = new OrderService(con);
        ArrayList<Order> orders = os.getUserOrders(currentUser.getUserId());
        ServiceWrapper.printOrders(orders);
        boolean isOk=true;
        System.out.println("Here are all the orders.");
        while(isOk) {
            while (!sc.hasNextInt()) {
                System.out.println("Please type in a number.");
                sc.nextLine();
            }
            int input = sc.nextInt();
            //System.out.println(input);
            sc.nextLine();
            if((input<0) || (input>orders.size())) {
                System.out.println("Please type in the right number.");
                continue;
            }
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
            while (!sc.hasNextInt()) {
                System.out.println("Please type in a number.");
                sc.nextLine();
            }
            int input = sc.nextInt();
            sc.nextLine();
            if((input<1) || (input>2)) {
                System.out.println("Please type in the right number.");
                continue;
            }
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
        boolean isOk=true;
        while(isOk) {
            while (!sc.hasNextInt()) {
                System.out.println("Please type in a number.");
                sc.nextLine();
            }
            int input = sc.nextInt();
            sc.nextLine();
            if (input == 1) {
                return true;
            } else if(input==2) {
                return false;
            } else {
                System.out.println("Please type either 1 or 2.");
            }
        }
        return true;
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
            while (!sc.hasNextInt()) {
                System.out.println("Please type in a number.");
                sc.nextLine();
            }
            int input = sc.nextInt();
            sc.nextLine();
            if((input<1) || (input>3)) {
                System.out.println("Please type in the right number.");
                continue;
            }
            if (input == 1) {
                currentLocation = location;
                System.out.println("Current location updated!");
                viewLocationScreen(location);
            } else if (input == 2) {
                editLocationScreen(location);
                LocationService ls = new LocationService(con);
                ls.updateLoc(location);
                System.out.println("Location updated!");
                viewLocationScreen(location);
            } else if (input == 3){
                viewAllLocations();
            }
        }
    }

    private static void editLocationScreen(Location location) {
            System.out.println("Enter street:");
	    String street = sc.nextLine();
            location.setStreet(street);
            System.out.println("Enter city:");
	    String city = sc.nextLine();
            location.setCity(city);
            System.out.println("Enter state:");
	    String state = sc.nextLine();
            location.setState(state);
            System.out.println("Enter country:");
	    String country = sc.nextLine();
            location.setCountry(country);
            System.out.println("Enter zip:");
	    String zip = sc.nextLine();
            location.setZip(zip);   
    }
}
