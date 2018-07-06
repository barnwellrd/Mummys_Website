package cli;

import cli.ServiceWrapper;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import domain.Card;
import domain.Menu;
import domain.Order;
import domain.Store;
import domain.User;
import services.CardService;
import services.MenuServices;
import services.OrderService;
import services.StoreService;
import services.UserService;

public class AdminAndManager {
	
    static Connection con;
	
    public AdminAndManager(Connection con){
        AdminAndManager.con = con;
    }

    public void adminScreen(){
        ArrayList<String> options = new ArrayList<String>();
        System.out.println("Admin View");
        options.add("Alter Cards");
        options.add("Alter Combos");
        options.add("Alter Delivery Methods");
        options.add("Alter Delivery Statuses");
        options.add("Alter Items");
        options.add("Alter Item Types");
        options.add("Alter Locations");
        options.add("Alter Orders");
        options.add("Alter Order_items"); //Probably don't need this one
        options.add("Alter Users");
        options.add("Alter User Statuses");
        options.add("Display Pending Orders");
        ServiceWrapper.printOptions(options);
        Scanner sc = new Scanner(System.in);
        int input = sc.nextInt();
        int option = 0;
        switch(input){
            case 1:
            {
                option = optionsScreen("Card");
                switch(option){
                    case 1:
                        alterCardScreen();
                        break;
                    case 2:
                        addCardScreen();
                        break;
                    case 3:
                        deleteCardScreen();
                        break;
                    case 4: 
                        adminScreen();
                        break;
                }
                break;
            }
            case 3:
            {
                option = optionsScreen("Delivery Method");
                switch(option){
                    case 1:
                        alterDeliveryMethodScreen();
                        break;
                    case 2:
                        addDeliveryMethodScreen();
                        break;
                    case 3:
                        deleteDeliveryMethodScreen();
                        break;
                    case 4:
                        adminScreen();
                        break;
                }
                break;
            }
            case 4:
            {
                option = optionsScreen("Delivery Statuse");
                switch(option)
                {
                    case 1:
                        alterDeliveryStatusScreen();
                        break;
                    case 2:
                        addDeliveryStatusScreen();
                        break;
                    case 3:
                        deleteDeliveryStatusScreen();
                        break;
                    case 4:
                        adminScreen();
                }
                break;
            }
            case 5:
            {
                option = optionsScreen("Item");
                switch(option){
                    case 1:
                        alterItemScreen();
                        break;
                    case 2:
                        addItemScreen();
                        break;
                    case 3:
                        deleteItemScreen();
                        break;
                    case 4:
                        adminScreen();
                        break;
                }
                break;
            }
            case 6:
            {
                option = optionsScreen("Item Type");
                switch(option){
                    case 1:
                        alterItemTypeScreen();
                        break;
                    case 2:
                        addItemTypeScreen();
                       break;
                    case 3:
                        deleteItemTypeScreen();
                        break;
                    case 4:
                        adminScreen();
                        break;
                }
                break;
            }
            case 7:
            {
                option = optionsScreen("Location");
                switch(option){
                    case 1:
                        alterLocationScreen();
                        break;
                    case 2:
                        addLocationScreen();
                        break;
                    case 3:
                        deleteLocationScreen();
                        break;
                    case 4:
                        adminScreen();
                        break;
                }
                break;
            }
            case 8:
            {
                   //Specialized menu so user can view filter pending orders
                option = optionsScreen("Orders");
                switch(option){
                    case 1:
                        alterOrdersScreen();
                        break;
                    case 2:
                        addOrdersScreen();
                        break;
                    case 3:
                        deleteOrdersScreen();
                        break;
                    case 4:
                        adminScreen();
                        break;
                }
                break;
            }
            case 9:
            {
                option = optionsScreen("Order Item");
                break;
            }
            case 10:
            {
                option = optionsScreen("User");
                switch(option){
                    case 1:
                        alterUserScreen();
                        break;
                    case 2:
                        addUserScreen();
                        break;
                    case 3:
                        deleteUserScreen();
                        break;
                    case 4:
                        adminScreen();
                        break;
                }
                break;
            }
            case 11:
            {
                option = optionsScreen("User Statuses");
                switch(option){
                    case 1:
                        alterUserStatusScreen();
                        break;
                    case 2:
                        addUserStatusScreen();
                        break;
                    case 3:
                        deleteUserStatusScreen();
                        break;
                    case 4:
                        adminScreen();
                        break;
                }
                break;
            }
            case 12:
            {
                displayPendingOrdersScreen();
                break;
            }
            case 13:
            {
                //Returns to initial screen
                Tiger.firstScreen();
            }
            case 14:
                System.exit(0);
        }

        adminScreen();

    }
	
	
    public static int optionsScreen(String thing){
        System.out.println("How would you like to alter " + thing);
        ArrayList<String> options = new ArrayList<String>();
        options.add("Alter");
        options.add("Add");
        options.add("Delete");
        ServiceWrapper.printOptions(options);
        Scanner sc = new Scanner(System.in);
        int input = sc.nextInt();
        return input;
    }
        
    public static void displayPendingOrdersScreen()
    {
        Scanner sc =new Scanner(System.in);
        System.out.println("Put orders here");
        System.out.println("Press any key to exit");
        sc.next();
    }

    //Doesn't work
    public static void addCardScreen(){
        System.out.println("Add a Credit Card");
        Scanner sc = new Scanner(System.in);
        System.out.println("\nEnter Card id: ");
        String cardId= sc.next();
        System.out.println("\nEnter id of user this card belongs to: ");
        String userId= sc.next();
        System.out.println("\nEnter Card number: ");
        String cardNumber= sc.next();
        System.out.println("\nEnter expiration year: ");
        int year = sc.nextInt();
        System.out.println("\nEnter expiration month: ");
        int month = sc.nextInt();
        System.out.println("\nEnter expiration date: ");
        int day = sc.nextInt();
        Date expiryDate= new Date(year, month, day);
        System.out.println("Enter Security code: ");
        String securityCode= sc.next();
        Card c = new Card(cardId, userId, cardNumber, expiryDate, securityCode);

        CardService cs = new CardService(con);
        cs.add(c);
        AdminAndManager aam = new AdminAndManager(con);
        aam.adminScreen();
    }

    public static void deleteCardScreen(){
        System.out.println("List of cards");
        CardService cs = new CardService(con);
        ArrayList<Card> cl = cs.getAll();
            int count=1;
        for(Card c:cl){
            System.out.println(count + ": " + c.getCardNumber());
            count++;
        }
        System.out.println("Select card you'd like to delete");
        Scanner sc = new Scanner(System.in);
        int input = sc.nextInt();
        cs.deleteById(cl.get(input-1).getCardId());
        System.out.println("Deleted Card");	
    }
	
    public static void alterCardScreen(){
        System.out.println("List of cards");
        CardService cs = new CardService(con);
        ArrayList<Card> cl = cs.getAll();
        int count=1;
        for(Card c:cl){
                System.out.println(count + ": " + c.getCardNumber());
                count++;
        }
        System.out.println("Enter the number of the card you'd like to alter");
        Scanner sc = new Scanner(System.in);
        int input = sc.nextInt();

        String cardId= cl.get(input-1).getCardId();
        System.out.println("Enter id of user this card belongs to: ");
        String userId= sc.next();
        System.out.println("Enter Card number: ");
        String cardNumber= sc.next();
        System.out.println("Enter expiration year: ");
        int year = sc.nextInt();
        System.out.println("Enter expiration month: ");
        int month = sc.nextInt();
        System.out.println("Enter expiration date: ");
        int day = sc.nextInt();
        Date expiryDate= new Date(year, month, day);
        System.out.println("Enter Security code: ");
        String securityCode= sc.next();

        Card c = new Card(cardId, userId, cardNumber, expiryDate, securityCode);

        cs.update(c);
        AdminAndManager aam = new AdminAndManager(con);
        aam.adminScreen();
    }
	
    public static void addItemScreen(){
        System.out.println("Add an item");
        Scanner sc = new Scanner(System.in);
        System.out.println("\nEnter item id: ");
        String id= sc.next();
        System.out.println("\nEnter item name: ");
        sc.nextLine();
        String name= sc.nextLine();
        System.out.println("\nEnter vegeterian (y or n): ");
        String vege = sc.next();
        char vegetarian = vege.charAt(0);
        System.out.println("\nEnter a description: ");
        sc.nextLine();
        String description= sc.nextLine();
        System.out.println("\nEnter type number id: ");
        String type= sc.next();
        System.out.println("\nEnter meal time: ");
        String slot_ID= sc.next();
        System.out.println("\nEnter photo link: ");
        String photo= sc.next();
        System.out.println("\nEnter a price: ");
        float price= sc.nextFloat();

        Menu men = new Menu(id, name, vegetarian, type, description, slot_ID, photo, price);
        MenuServices menServ = new MenuServices(con);
        menServ.add(men);
        System.out.println("\n" + name + " added to database\n");
        AdminAndManager aam = new AdminAndManager(con);
        aam.adminScreen();	
    }
	
    public static void deleteItemScreen(){
        System.out.println("Choose an item to delete");
        MenuServices ms = new MenuServices(con);
        ArrayList<Menu> menus = ms.getAll();
        ServiceWrapper.printMenuItems(menus);
        Scanner sc = new Scanner(System.in);
        int input = sc.nextInt();
        if(input == menus.size() + 1)
            return;
        if(input == menus.size()+2)
            System.exit(0);
        MenuServices menServ = new MenuServices(con);

        menServ.deleteById(menus.get(input-1).getId());
        System.out.println("Deleted " + menus.get(input-1).getName());
    }
	
    public static void alterItemScreen(){
        System.out.println("Choose an item to alter");
        MenuServices ms = new MenuServices(con);
        ArrayList<Menu> menus = ms.getAll();
        ServiceWrapper.printMenuItems(menus);
        Scanner sc = new Scanner(System.in);
        int input = sc.nextInt();
        Menu men = menus.get(input-1);
        MenuServices menServ = new MenuServices(con);
        System.out.println("Enter item name: ");
        sc.nextLine();
        String name= sc.nextLine();
        System.out.println("Enter vegeterian (y or n): ");
        String vege = sc.next();
        char vegetarian = vege.charAt(0);
        System.out.println("Enter a description: ");
        sc.nextLine();
        String description= sc.nextLine();
        System.out.println("Enter type number id: ");
        String type= sc.next();
        System.out.println("Enter meal time: ");
        String slot_ID= sc.next();
        System.out.println("Enter photo link: ");
        String photo= sc.next();
        System.out.println("Enter a price: ");
        float price= sc.nextFloat();
        String id = men.getId();
        Menu menUp = new Menu(id, name, vegetarian, type, description, slot_ID, photo, price);
        menServ.update(menUp);
        System.out.println("Updated " + name);
    }
    public static void alterUserScreen() {
        System.out.println("Choose a user to alter (choose 0 to go to previous screen)");
        Scanner sc = new Scanner(System.in);
        UserService us = new UserService(con);
        ArrayList<User> userList = us.getAll();
        for(User user: userList){
            System.out.println(user.getUserId()+","+user.getEmail());
        }
        System.out.print("Enter ID:");
        String id = sc.next();
        if(id.equals("0")){
            return;
        }
        User user = us.getById(id);
        alterUserFieldScreen(user);
    }
    public static void alterUserFieldScreen(User user){
        UserService us = new UserService(con);
        Scanner sc = new Scanner(System.in);
        System.out.println("Choose a field to modify");
        System.out.println("1. User ID:"+user.getUserId());
        System.out.println("2. First Name:"+user.getFirstName());
        System.out.println("3. Last Name:"+user.getLastName());
        System.out.println("4. Email:"+user.getEmail());
        System.out.println("5. Password:"+user.getPassword());
        System.out.println("6. Phone Number:"+user.getPhone());
        System.out.println("7. Return to previous screen");
        int option = sc.nextInt();
        switch(option){
            case 1:
            {
                System.out.println("Enter the new User Id");
                String newId = sc.next();
                user.setUserId(newId);
                break;
            }
            case 2:
            {
                System.out.println("Enter the new name");
                String newName = sc.next();
                user.setFirstName(newName);
                break;
            }
            case 3:
            {
                System.out.println("Enter the new name");
                String newName = sc.next();
                user.setLastName(newName);
                break;
            }
            case 4:
            {
                System.out.println("Enter the new email");
                String newEmail = sc.next();
                user.setEmail(newEmail);
                break;
            }
            case 5:
            {
                System.out.println("Enter the new password");
                String newPassword = sc.next();
                user.setPassword(newPassword);
                break;
            }
            case 6:
            {
                System.out.println("Enter the new phone number");
                String newNumber = sc.next();
                user.setPhone(newNumber);
                break;
            }
            case 7:
            {
                return;
            }
        }
        us.update(user);
        alterUserFieldScreen(user);
    }

    public static void addUserScreen(){
        System.out.println("Add a User");
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter user id: ");
        String userId = sc.next();
        System.out.println("Enter first name: ");
        String firstName = sc.next();
        System.out.println("Enter last name: ");
        String lastName = sc.next();
        System.out.println("Enter email: ");
        String email = sc.next();
        System.out.println("Enter password: ");
        String password = sc.next();
        System.out.println("Enter status id: ");
        String userStatusId = sc.next();
        System.out.println("Enter location id: ");
        String locationId = sc.next();
        User u = new User(userId, firstName, lastName, email, password, userStatusId, locationId);
        UserService us = new UserService(con);
        us.add(u);

        AdminAndManager aam = new AdminAndManager(con);
        aam.adminScreen();
    }
	
    public static void deleteUserScreen() {
        System.out.println("List of users");
        UserService us = new UserService(con);
        ArrayList<User> uArr = us.getAll();
        int count=1;
        for(User u:uArr){
            System.out.println(count + " " + u.getFirstName() + " " + u.getLastName());
            count++;
        }

        System.out.println("Select user you'd like to delete");
        Scanner sc = new Scanner(System.in);
        int input = sc.nextInt();
        us.deleteById(uArr.get(input-1).getUserId());
        System.out.println(uArr.get(input-1).getFirstName() + "has been deleted");

    }
    public static void alterDeliveryMethodScreen(){

    }
    public static void addDeliveryMethodScreen(){

    }
    public static void deleteDeliveryMethodScreen(){

    }
    public static void alterDeliveryStatusScreen(){

    }
    public static void addDeliveryStatusScreen(){

    }
    public static void deleteDeliveryStatusScreen(){

    }
    public static void alterItemTypeScreen(){

    }
    public static void addItemTypeScreen(){

    }
    public static void deleteItemTypeScreen(){

    }
    public static void alterLocationScreen(){

    }
    public static void addLocationScreen(){

    }
    public static void deleteLocationScreen(){

    }
    public static void alterOrdersScreen(){

    }
    public static void addOrdersScreen(){

    }
    public static void deleteOrdersScreen(){

    }
    public static void alterUserStatusScreen(){

    }
    public static void addUserStatusScreen(){

    }
    public static void deleteUserStatusScreen(){

    }
}
