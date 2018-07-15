package cli;


import static cli.Tiger.sw;
import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashMap;
import domain.*;
import services.*;


public class AdminAndManager {

    static Connection con;

    public AdminAndManager(Connection con) {
        AdminAndManager.con = con;
    }

    public void adminScreen() {
        ArrayList<String> options = new ArrayList<>();
        System.out.println("Admin View");
        options.add("Alter Cards");
        options.add("Alter Specials");
        options.add("Alter Delivery Methods");
        options.add("Alter Delivery Statuses");
        options.add("Alter Items");
        options.add("Alter Item Types");
        options.add("Alter Locations");
        options.add("Alter Orders");
        options.add("Alter Order_items"); //Probably don't need this one
        options.add("Alter Users");
        options.add("Alter User Statuses");
        options.add("Alter Stores");
        options.add("Display Pending Orders");
        int input = choiceScreen(options);
        int option;
        switch (input) {
            case 1: {
                option = optionsScreen("Card");
                switch (option) {
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
            case 2: {
                option = optionsScreen("Specials");
                switch (option) {
                    case 1:
                        alterSpecialScreen();
                        break;
                    case 2:
                        addSpecialScreen();
                        break;
                    case 3:
                        deleteSpecialScreen();
                        break;
                    case 4:
                        adminScreen();
                        break;
                }
                break;
            }
            case 3: {
                option = optionsScreen("Delivery Methods");
                switch (option) {
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
                }
                break;
            }
            case 4: {
                option = optionsScreen("Delivery Statuse");
                switch (option) {
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
            case 5: {
                option = optionsScreen("Item");
                switch (option) {
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
            case 6: {
                option = optionsScreen("Item Type");
                switch (option) {
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
            case 7: {
                option = optionsScreen("Location");
                switch (option) {
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
            case 8: {
                //Specialized menu so user can view filter pending orders
                option = optionsScreen("Orders");
                switch (option) {
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
            case 9: {
                option = optionsScreen("Order Item");
                break;
            }
            case 10: {
                option = optionsScreen("User");
                switch (option) {
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
            case 11: {
                option = optionsScreen("User Statuses");
                switch (option) {
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
            case 12: {
                option = optionsScreen("Stores");
                switch (option) {
                    case 1:
                        alterStoresScreen();
                        break;
                    case 2:
                        addStoresScreen();
                        break;
                    case 3:
                        deleteStoresScreen();
                        break;
                    case 4:
                        adminScreen();
                        break;
                }
                break;
            }
            case 13: {
                displayPendingOrdersScreen();
                break;
            }
            case 14: {
                //Returns to initial screen
                Tiger.firstScreen();
            }
            case 15:
                System.exit(0);
        }

        adminScreen();

    }

    public static int optionsScreen(String thing) {
        System.out.println("How would you like to alter " + thing);
        ArrayList<String> options = new ArrayList<>();
        options.add("Alter");
        options.add("Add");
        options.add("Delete");
        ServiceWrapper.printOptions(options);
        Scanner sc = new Scanner(System.in);
        while (!sc.hasNextInt()) {
            System.out.println("Enter a valid option");
            sc.next();
        }
        int input = sc.nextInt();
        return input;
    }

    //This class does input validation automatically
    public static int choiceScreen(ArrayList<String> options) {
        Scanner sc = new Scanner(System.in);
        int input = 0;
        while (input > options.size() + 1 || input < 1) {
            ServiceWrapper.printOptions(options);

            while (!sc.hasNextInt()) {
                System.out.println("Enter a valid option");
                sc.next();
            }
            input = sc.nextInt();
            if (input > options.size() + 1 || input < 1) {
                System.out.println("Enter a valid option");
            }
        }
        return input;
    }

    public static void displayPendingOrdersScreen() {
        Scanner sc = new Scanner(System.in);
        OrderService os = new OrderService(con);
        ArrayList<Order> pendingOrders = os.getPendingOrders();
        ServiceWrapper sw = new ServiceWrapper(con);
        for(Order order : pendingOrders){
            HashMap<String, Integer> itemCount = order.getItemCount();
            ArrayList<Menu> items = sw.getMenuItems(itemCount);
            for(Menu item: items){
                int count = itemCount.get(item.getId());
                String formattedString = String.format("%.02f", item.getPrice()*count);
                System.out.println(item.getName()+"("+count+")\t"+formattedString);
            }
            String formattedString = String.format("%.02f", order.getTip());
            System.out.println("Tip:\t"+formattedString);

            formattedString = String.format("%.02f",order.getTotal_price()+order.getTip());
            System.out.println("Total\t"+formattedString);
        System.out.println("==============================================");
        }
        System.out.println("Press any key to exit");
        sc.next();
    }

    public static void alterSpecialScreen() {
        SpecialServices ss = new SpecialServices(con);
        ArrayList<Special> specs = ss.getAll();
        ServiceWrapper.printSpecials(specs);
        Scanner sc = new Scanner(System.in);
        System.out.println("Choose a special to alter");
        int input = sc.nextInt();
        //check for wrong entery
        while (input > specs.size() + 1) {
            System.out.println("Wrong entry. Please enter again: ");
            input = sc.nextInt();
        }
        //Go back 
        if (input == specs.size() + 1) {
            return;
        }
        Special spec = specs.get(input - 1);
        SpecialServices specServ = new SpecialServices(con);
        System.out.println("Alter a Special");
        System.out.println("\nEnter discount percentagee: ");
        int discPercent = sc.nextInt();
        String specId = spec.getItem_ID();
        Special updatedSpec = new Special(specId, discPercent);
        System.out.println(updatedSpec);
        specServ.update(updatedSpec);
        System.out.println("Updated Special " + specId);
    }

    public static void addSpecialScreen() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Add a Special");
        System.out.println("\nEnter special item id: ");
        String specId = sc.next();
        SpecialServices ss = new SpecialServices(con);
        while (ss.itemIdExists(specId)) {
            System.out.println("Item does not exist! Enter a different item ID: ");
            specId = sc.next();
        }
        while (!ss.specialIdExists(specId)) {
            System.out.println("Special ID already exists! Enter a different special ID: ");
            specId = sc.next();
        }
        System.out.println("\nEnter discount percentage: ");
        int discPercent = sc.nextInt();
        Special spec = new Special(specId, discPercent);
        SpecialServices specServ = new SpecialServices(con);
        specServ.add(spec);
        AdminAndManager aam = new AdminAndManager(con);
        aam.adminScreen();
    }

    public static void deleteSpecialScreen() {
        SpecialServices ss = new SpecialServices(con);
        ArrayList<Special> spec = ss.getAll();
        ServiceWrapper.printSpecials(spec);
        Scanner sc = new Scanner(System.in);
        System.out.println("Choose a special to delete");
        int input = sc.nextInt();
        if (input == spec.size() + 1) {
            return;
        }
        if (input == spec.size() + 2) {
            System.exit(0);
        }
        SpecialServices specServ = new SpecialServices(con);
        String idToDel = (spec.get(input - 1)).getItem_ID();
        specServ.deleteById(idToDel);
        System.out.println("Special successfully deleted! " + spec.get(input - 1).toString());
    }

    //Doesn't work
    public static void addCardScreen() {
        System.out.println("Add a Credit Card");
        Scanner sc = new Scanner(System.in);
        System.out.println("\nEnter Card id: ");
        String cardId = sc.next();
        System.out.println("\nEnter id of user this card belongs to: ");
        String userId = sc.next();
        System.out.println("\nEnter Card number: ");
        String cardNumber = sc.next();
        System.out.println("\nEnter expiration year: ");
        int year = sc.nextInt();
        System.out.println("\nEnter expiration month: ");
        int month = sc.nextInt();
        System.out.println("\nEnter expiration date: ");
        int day = sc.nextInt();
        Date expiryDate = new Date(year, month, day);
        System.out.println("Enter Security code: ");
        String securityCode = sc.next();
        Card c = new Card(cardId, userId, cardNumber, expiryDate, securityCode);

        CardService cs = new CardService(con);
        cs.add(c);
        AdminAndManager aam = new AdminAndManager(con);
        aam.adminScreen();
    }

    public static void deleteCardScreen() {
        System.out.println("List of cards");
        CardService cs = new CardService(con);
        ArrayList<Card> cl = cs.getAll();
        ArrayList<String> options = new ArrayList<>();
        int input;
        for (Card c : cl) {
            options.add(c.getCardNumber());
        }
        System.out.println("Select card you'd like to delete");
        input = choiceScreen(options);
        //If it isn't going back
        if (input < cl.size() + 1) {
            cs.deleteById(cl.get(input - 1).getCardId());
            System.out.println("Deleted Card");
        }
    }

    public static void alterCardScreen() {
        System.out.println("List of cards");
        CardService cs = new CardService(con);
        ArrayList<Card> cardList = cs.getAll();
        Scanner sc = new Scanner(System.in);
        int input = 0;

        while (input != cardList.size() + 1) {
            ArrayList<String> options = new ArrayList();
            for (Card c : cardList) {
                options.add(c.getCardNumber());
            }
            System.out.println("Choose card to alter:");
            input = choiceScreen(options);

            if (input < cardList.size() + 1) {
                Card card = cardList.get(input - 1);
                alterCardFieldScreen(card);
            }
        }
    }
    
    public static void alterCardFieldScreen(Card card) {

        Scanner sc = new Scanner(System.in);
        CardService cs = new CardService();
        int input = 0;
        while (input != 5) {
            ArrayList<String> options = new ArrayList<>();
            options.add("Card ID:" + card.getCardId());
            options.add("Card Number:" + card.getCardNumber());
            options.add("Expiration Date:" + card.getExpiryDate());
            options.add("Security Code:" + card.getSecurityCode());
            System.out.println("Enter a field to alter");
            input = choiceScreen(options);
            switch (input) {
                case (1): {
                    String oldId = card.getCardId();
                    System.out.println("Enter a new id");
                    String newId = sc.next();
                    card.setCardId(newId);
                    //You have to delete first beacuse in case where 
                    //the user enters the original ID the entry would be deleted.
                    cs.deleteById(oldId);
                    cs.add(card);
                    break;
                }
                case (2): {
                    System.out.print("Enter new Card Number:");
                    String newNumber = sc.next();
                    card.setCardNumber(newNumber);
                    break;
                }
                case (3): {
                    System.out.println("Enter the new month:");
                    while (!sc.hasNextInt()) {
                        System.out.println("Enter a valid integer month");
                        sc.next();
                    }
                    int newMonth = sc.nextInt();
                    System.out.println("Enter the new year:");
                    while (!sc.hasNextInt()) {
                        System.out.println("Enter a valid integer year");
                        sc.next();
                    }
                    int newYear = sc.nextInt();
                    Date date = new Date(newYear, newMonth, 1);
                    card.setExpiryDate(date);
                    break;
                }
                case (4): {
                    System.out.println("Enter the security code:");
                    String newCode = sc.next();
                    card.setSecurityCode(newCode);
                    break;
                }
            }
        }
        cs.update(card);
    }

    public static void addItemScreen() {
        System.out.println("Add an item");
        Scanner sc = new Scanner(System.in);
        System.out.println("\nEnter item id: ");
        String id = sc.next();

        //validation
        MenuServices itm = new MenuServices(con);
        while (itm.itemExist(id)) {
            System.out.println("Item ID exist in database. Please Enter a different Item ID: ");
            id = sc.next();
        }

        System.out.println("\nEnter item name: ");
        sc.nextLine();
        String name = sc.nextLine();
        System.out.println("\nEnter vegeterian (y or n): ");
        String vege = sc.next();
        char vegetarian = vege.charAt(0);
        System.out.println("\nEnter a description: ");
        sc.nextLine();
        String description = sc.nextLine();
        System.out.println("\nEnter type number id: ");
        String type = sc.next();
        System.out.println("\nEnter meal time: ");
        String slot_ID = sc.next();
        System.out.println("\nEnter photo link: ");
        String photo = sc.next();
        System.out.println("\nEnter a price: ");
        float price = sc.nextFloat();

        Menu men = new Menu(id, name, vegetarian, type, description, slot_ID, photo, price);
        MenuServices menServ = new MenuServices(con);
        menServ.add(men);
        System.out.println("\n" + name + " added to database\n");
        AdminAndManager aam = new AdminAndManager(con);
        aam.adminScreen();
    }

    public static void deleteItemScreen() {
        System.out.println("Choose an item to delete");
        MenuServices ms = new MenuServices(con);
        ArrayList<Menu> menus = ms.getAll();
        ServiceWrapper.printMenuItems(menus);
        Scanner sc = new Scanner(System.in);
        int input = sc.nextInt();
        if (input == menus.size() + 1) {
            return;
        }
        if (input == menus.size() + 2) {
            System.exit(0);
        }
        MenuServices menServ = new MenuServices(con);

        menServ.deleteById(menus.get(input - 1).getId());
        System.out.println("Deleted " + menus.get(input - 1).getName());
    }

    public static void alterItemScreen() {
        System.out.println("Choose an item to alter");
        MenuServices ms = new MenuServices(con);
        ArrayList<Menu> menus = ms.getAll();
        ServiceWrapper.printMenuItems(menus);
        Scanner sc = new Scanner(System.in);
        int input = sc.nextInt();

        //check for wrong entery
        while (input > menus.size() + 1) {
            System.out.println("Wrong entry. Please enter again: ");
            input = sc.nextInt();
        }
        //Go back 
        if (input == menus.size() + 1) {
            return;
        }

        Menu men = menus.get(input - 1);
        MenuServices menServ = new MenuServices(con);
        System.out.println("Enter item name: ");
        sc.nextLine();
        String name = sc.nextLine();
        System.out.println("Enter vegeterian (y or n): ");
        String vege = sc.next();
        char vegetarian = vege.charAt(0);
        System.out.println("Enter a description: ");
        sc.nextLine();
        String description = sc.nextLine();
        System.out.println("Enter type number id: ");
        String type = sc.next();
        System.out.println("Enter meal time: ");
        String slot_ID = sc.next();
        System.out.println("Enter photo link: ");
        String photo = sc.next();
        System.out.println("Enter a price: ");
        float price = sc.nextFloat();
        String id = men.getId();
        Menu menUp = new Menu(id, name, vegetarian, type, description, slot_ID, photo, price);
        menServ.update(menUp);
        System.out.println("Updated " + name);
    }

    public static void alterUserScreen() {
        System.out.println("Choose a user to alter");
        Scanner sc = new Scanner(System.in);
        UserService us = new UserService(con);
        ArrayList<User> userList = us.getAll();
        for (User user : userList) {
            System.out.println(user.getUserId() + "," + user.getEmail());
        }
        System.out.print("Enter ID:");
        String id = sc.next();
        if (id.equals("0")) {
            return;
        }
        User user = us.getById(id);
        alterUserFieldScreen(user);
    }

    public static void alterUserFieldScreen(User user) {

        UserService us = new UserService(con);
        Scanner sc = new Scanner(System.in);

        int input = 0;
        while (input != 8) {
            ArrayList<String> options = new ArrayList<>();
            options.add("ID:" + user.getUserId());
            options.add("First Name:" + user.getFirstName());
            options.add("Last Name:" + user.getLastName());
            options.add("Email:" + user.getEmail());
            options.add("Password:" + user.getPassword());
            options.add("Phone Number:" + user.getPhone());
            options.add("User Status ID:" + user.getUserStatusId());
            System.out.println("Choose a field to modify");
            input = choiceScreen(options);
            switch (input) {
                case 1: {
                    String oldId = user.getUserId();
                    System.out.println("Enter a new ID");
                    String newId = sc.next();
                    user.setUserId(newId);
                    us.deleteById(oldId);
                    us.add(user);
                    break;
                }
                case 2: {
                    System.out.println("Enter the new name");
                    String newName = sc.next();
                    user.setFirstName(newName);
                    break;
                }
                case 3: {
                    System.out.println("Enter the new name");
                    String newName = sc.next();
                    user.setLastName(newName);
                    break;
                }
                case 4: {
                    System.out.println("Enter the new email");
                    String newEmail = sc.next();
                    user.setEmail(newEmail);
                    break;
                }
                case 5: {
                    System.out.println("Enter the new password");
                    String newPassword = sc.next();
                    user.setPassword(newPassword);
                    break;
                }
                case 6: {
                    System.out.println("Enter the new phone number");
                    String newNumber = sc.next();
                    user.setPhone(newNumber);
                    break;
                }
                case 7: {
                    System.out.println("Enter the new user status ID");
                    String newStatusId = sc.next();
                    user.setUserStatusId(newStatusId);
                    break;
                }
            }
        }
        us.update(user);
    }

    public static void addUserScreen() {
        System.out.println("Add a User");
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter user id: ");
        String userId = sc.next();
        System.out.println("Enter first name: ");
        String firstName = sc.next();
        System.out.println("Enter last name: ");
        String lastName = sc.next();
        System.out.println("Enter the phone number:");
        String phoneNumber = sc.next();
        System.out.println("Enter email: ");
        String email = sc.next();
        System.out.println("Enter password: ");
        String password = sc.next();
        System.out.println("Enter status id: ");
        String userStatusId = sc.next();
        User u = new User(userId, firstName, lastName, phoneNumber, email, password, userStatusId);
        UserService us = new UserService(con);
        us.add(u);

        AdminAndManager aam = new AdminAndManager(con);
        aam.adminScreen();
    }

    public static void deleteUserScreen() {
        System.out.println("List of users");
        UserService us = new UserService(con);
        ArrayList<User> uArr = us.getAll();
        int count = 1;
        for (User u : uArr) {
            System.out.println(count + " " + u.getFirstName() + " " + u.getLastName());
            count++;
        }

        System.out.println("Select user you'd like to delete");
        Scanner sc = new Scanner(System.in);
        int input = sc.nextInt();
        us.deleteById(uArr.get(input - 1).getUserId());
        System.out.println(uArr.get(input - 1).getFirstName() + "has been deleted");

    }

    

    public static void alterDeliveryMethodScreen() {

        DeliveryMethodService dms = new DeliveryMethodService(con);
        ArrayList<DeliveryMethod> dmList = dms.getAll();
        ArrayList<String> options = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        System.out.println("Choose a method");
        for (DeliveryMethod dm : dmList) {
            options.add(dm.getDeliveryMethod());
        }
        int input = choiceScreen(options);
        if (input < dmList.size()) {
            DeliveryMethod deliveryMethod = dmList.get(input);
            System.out.println("Enter the new method:");
            String newMethod = sc.next();
            deliveryMethod.setDeliveryMethod(newMethod);
            dms.update(deliveryMethod);
        }
    }

    

    public static void addDeliveryMethodScreen() {

        Scanner sc = new Scanner(System.in);
        System.out.println("Add a DeliveryMethod");
        System.out.println("Enter a Delivery Method ID");
        String id = sc.next();
        System.out.println("Enter a Delivery Method Name");
        String name = sc.next();
        DeliveryMethod deliveryMethod = new DeliveryMethod(id, name);
        DeliveryMethodService dms = new DeliveryMethodService();
        dms.add(deliveryMethod);
        System.out.println("Added new Delivery Method");
    }
    public static void deleteDeliveryMethodScreen() {

        DeliveryMethodService dms = new DeliveryMethodService(con);
        ArrayList<DeliveryMethod> dmList = dms.getAll();
        ArrayList<String> options = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        System.out.println("Choose a method");
        for (DeliveryMethod dm : dmList) {
            options.add(dm.getDeliveryMethod());
        }
        int input = choiceScreen(options);
        if (input < dmList.size()) {
            dms.deleteById(dmList.get(input).getDeliveryMethodId());
            System.out.println("Method deleted");
        }
    }

    public static void alterDeliveryStatusScreen() {
        DeliveryStatusService dss = new DeliveryStatusService(con);
        ArrayList<DeliveryStatus> dsList = dss.getAll();
        ArrayList<String> options = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        System.out.println("Choose a method");
        for (DeliveryStatus ds : dsList) {
            options.add(ds.getDeliveryStatus());
        }
        int input = choiceScreen(options);
        if (input < dsList.size()) {
            DeliveryStatus deliveryStatus = dsList.get(input);
            System.out.println("Enter the new method:");
            String newMethod = sc.next();
            deliveryStatus.setDeliveryStatus(newMethod);
            dss.update(deliveryStatus);
        }
    }

    public static void addDeliveryStatusScreen() {
        
        Scanner sc = new Scanner(System.in);
        System.out.println("Add a Delivery Status");
        System.out.println("Enter a Delivery Status ID");
        String id = sc.next();
        System.out.println("Enter a Delivery Status Name");
        String name = sc.next();
        DeliveryStatus deliveryStatus = new DeliveryStatus(id, name);
        DeliveryStatusService dss = new DeliveryStatusService();
        dss.add(deliveryStatus);
        System.out.println("Added new Delivery Status");
    }

    public static void deleteDeliveryStatusScreen() {
        DeliveryStatusService dss = new DeliveryStatusService(con);
        ArrayList<DeliveryStatus> dsList = dss.getAll();
        ArrayList<String> options = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        System.out.println("Choose a status");
        for (DeliveryStatus deliveryStatus : dsList) {
            options.add(deliveryStatus.getDeliveryStatus());
        }
        int input = choiceScreen(options);
        if (input < dsList.size()) {
            dss.deleteByID(dsList.get(input).getDeliveryStatusId());
            System.out.println("Status deleted");
        }
    }

    public static void alterItemTypeScreen() {
        System.out.println("Choose an item type to alter");
        MenuServices ms = new MenuServices(con);
        ItemTypeService its = new ItemTypeService(con);
        ArrayList<ItemType> items = its.getAll();
        ArrayList<Menu> menus = ms.getAll();
        ServiceWrapper.printItemType(items);
        Scanner sc = new Scanner(System.in);
        int input = sc.nextInt();
        //check for wrong entery
        while (input > items.size() + 1) {
            System.out.println("Wrong entry. Please enter again: ");
            input = sc.nextInt();
        }
        //Go back 
        if (input == items.size() + 1) {
            return;
        }
        ItemType itm = items.get(input - 1);
        Menu men = menus.get(input - 1);
        ItemTypeService itmServ = new ItemTypeService(con);
        MenuServices menServ = new MenuServices(con);
        System.out.println("Enter item type id: ");
        sc.nextLine();
        String itemTypeId = sc.nextLine();
        System.out.println("Enter item type: ");
        String itemType = sc.next();

        ItemType itmUp = new ItemType(itemTypeId, itemType);
        itmServ.update(itmUp);
        System.out.println("Updated " + itemType);
    }
    public static void addItemTypeScreen() {

        System.out.println("Add an item type");
        Scanner sc = new Scanner(System.in);
        System.out.println("\nEnter item type id: ");
        String itemTypeId = sc.next();

        ItemTypeService it = new ItemTypeService(con);
        while (it.itemTypeExist(itemTypeId)) {
            System.out.println("Item Type ID exist in database. Please Enter a different Item Type ID: ");
            itemTypeId = sc.next();
        }

        System.out.println("\nEnter item type: ");
        sc.nextLine();
        String itemType = sc.nextLine();

        ItemType itm = new ItemType(itemTypeId, itemType);
        ItemTypeService itmServ = new ItemTypeService(con);
        itmServ.add(itm);
        System.out.println("\n" + itemType + " added to database\n");
        AdminAndManager aam = new AdminAndManager(con);
        aam.adminScreen();
    }
    public static void deleteItemTypeScreen() {

        System.out.println("Choose an item type to delete");
        ItemTypeService its = new ItemTypeService(con);
        ArrayList<ItemType> items = its.getAll();
        ServiceWrapper.printItemType(items);
        Scanner sc = new Scanner(System.in);
        int input = sc.nextInt();
        if (input == items.size() + 1) {
            return;
        }
        if (input == items.size() + 2) {
            System.exit(0);
        }
        ItemTypeService itmtypServ = new ItemTypeService(con);
        itmtypServ.deleteById(items.get(input - 1).getItemTypeId());
        System.out.println("Deleted " + items.get(input - 1).getItemType());
    }

    public static void alterLocationScreen() {
        LocationService ls = new LocationService(con);
        ArrayList<Location> locs = ls.getAll();
        ServiceWrapper.printLocations(locs);
        Scanner sc = new Scanner(System.in);
        System.out.println("Choose a location to alter");
        int input = sc.nextInt();
        //check for wrong entery
        while (input > locs.size() + 1) {
            System.out.println("Wrong entry. Please enter again: ");
            input = sc.nextInt();
        }
        //Go back 
        if (input == locs.size() + 1) {
            return;
        }
        Location loc = locs.get(input - 1);
        LocationService locServ = new LocationService(con);
        System.out.println("Alter a Location");
        System.out.println("\nEnter user id: ");
        String userId = sc.next();
        System.out.println("\nEnter tax rate: ");
        String taxRate = sc.next();
        System.out.println("\nEnter street name: ");
        String street = sc.next();
        System.out.println("\nEnter city name: ");
        String city = sc.next();
        System.out.println("\nEnter state name: ");
        String state = sc.next();
        System.out.println("\nEnter country name: ");
        String country = sc.next();
        System.out.println("\nEnter zip code: ");
        String zip = sc.next();
        String locId = loc.getLocationId();
        Location updatedLoc = new Location(locId, userId, taxRate, street, city, state, country, zip);
        System.out.println(updatedLoc);
        locServ.updateLoc(updatedLoc);
        System.out.println("Updated Location " + locId);
    }

    public static void addLocationScreen() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Add a Location");
        System.out.println("\nEnter user id: ");
        String userId = sc.next();
        UserService us = new UserService(con);
        while (!us.userExist(userId)) {
            System.out.println("USER ID DOES NOT EXIST! Enter the user ID again: ");
            userId = sc.next();
        }

        System.out.println("\nEnter location id: ");
        String locationId = sc.next();
        LocationService ls = new LocationService(con);
        while (!ls.locationIdExist(locationId)) {
            System.out.println("LOCATION ID EXISTS! Enter a different location ID: ");
            locationId = sc.next();
        }
        System.out.println("\nEnter tax rate: ");
        String taxRate = sc.next();
        System.out.println("\nEnter street name: ");
        String street = sc.next();
        System.out.println("\nEnter city name: ");
        String city = sc.next();
        System.out.println("\nEnter state name: ");
        String state = sc.next();
        System.out.println("\nEnter country name: ");
        String country = sc.next();
        System.out.println("\nEnter zip code: ");
        String zip = sc.next();

        Location loc = new Location(locationId, userId, taxRate, street, city, state, country, zip);
        LocationService locServ = new LocationService(con);
        locServ.add(loc);
        System.out.println("\n" + locationId + " added to database\n");
        AdminAndManager aam = new AdminAndManager(con);
        aam.adminScreen();
    }

    public static void deleteLocationScreen() {
        LocationService ls = new LocationService(con);
        ArrayList<Location> locs = ls.getAll();
        ServiceWrapper.printLocations(locs);
        Scanner sc = new Scanner(System.in);
        System.out.println("Choose a location to delete");
        int input = sc.nextInt();
        if (input == locs.size() + 1) {
            return;
        }
        if (input == locs.size() + 2) {
            System.exit(0);
        }
        LocationService locServ = new LocationService(con);
        String idToDel = (locs.get(input - 1)).getLocationId();
        locServ.delById(idToDel);
        System.out.println("Location successfully deleted! " + locs.get(input - 1).toString());
    }

    public static void alterOrdersScreen() {

        System.out.println("Choose an order to alter");
        OrderService os = new OrderService(con);
        ArrayList<Order> orders = os.getAll();
        ServiceWrapper.printOrders(orders);
        
        Scanner sc = new Scanner(System.in);
        int input = sc.nextInt();
        
        //check for wrong entery
        while(input > orders.size() + 1){
            System.out.println("Wrong entry. Please enter again: ");
            input = sc.nextInt();
        }
        //Go back 
        if(input == orders.size() + 1){
            return;
        }
        //ord instance use to convert the input line number to get an ID of the line
        Order ord = orders.get(input-1);
        OrderService ordServ = new OrderService(con);
        //get constent values for order
        String order_id = ord.getOrder_id();
        String user_id = ord.getUser_id();
        int placed_timestamp = ord.getPlaced_timestamp();
        String card_id = ord.getCard_id();
        
        System.out.println("Enter Tip: ");
        sc.nextLine();
        float tip = sc.nextFloat();
        System.out.println("Enter total price: ");
        float total_price = sc.nextFloat();
        System.out.println("Enter a delivery time stamp: ");
        sc.nextLine();
        int delivery_timestamp= sc.nextInt();
        System.out.println("Enter a instruction: ");
        String instructions= sc.next();
        
        System.out.println("Choose Delivery method: ");
        DeliveryMethodService dms = new DeliveryMethodService(con);
        ArrayList<DeliveryMethod> dmList = dms.getAll();
        ArrayList<String> options = new ArrayList<>();

        for(DeliveryMethod dm:dmList){
            options.add(dm.getDeliveryMethod());
        }
        int input2 = choiceScreen(options);
        //check for wrong entery
        while(input2 > dmList.size() + 1){
            System.out.println("Wrong entry. Please enter again: ");
            input2 = sc.nextInt();
        }
        //Go back 
        if(input2 == dmList.size() + 1){
            return;
        }
      
        String delivery_method_id= "";
        if(input2 < dmList.size()){
            delivery_method_id = dmList.get(input2).getDeliveryMethodId();
        }
        
        System.out.println("Choose a Store: ");
        StoreService ss = new StoreService(con);
        ArrayList<Store> ssList = ss.getAll();
        ArrayList<String> options2 = new ArrayList<>();

        for(Store s :ssList){
            options2.add(s.getStoreName());
        }
        
        
        int input3 = choiceScreen(options2);
        
        while(input3 > ssList.size() + 1){
            System.out.println("Wrong entry. Please enter again: ");
            input3 = sc.nextInt();
        }
        //Go back 
        if(input3 == ssList.size() + 1){
            return;
        }
        String store_id= "";
        if(input3 < ssList.size()){
            store_id = ssList.get(input3).getStoreId();
        }
        
        System.out.println("Choose delivery status: ");
        DeliveryStatusService ds = new DeliveryStatusService(con);
        ArrayList<DeliveryStatus> dsList = ds.getAll();
        ArrayList<String> options3 = new ArrayList<>();

        for(DeliveryStatus d :dsList){
            options3.add(d.getDeliveryStatus());
        }
        int input4 = choiceScreen(options3);
        
        while(input4 > dsList.size() + 1){
            System.out.println("Wrong entry. Please enter again: ");
            input4 = sc.nextInt();
        }
        //Go back 
        if(input4 == dsList.size() + 1){
            return;
        }
        
        String delivery_status_id= "";
        if(input4 < dsList.size()){
            delivery_status_id = dsList.get(input4).getDeliveryStatusId();
        } 
        
        
        System.out.println("Choose an item to change a quantity: ");
        HashMap<String,Integer> itemCount = ord.getItemCount();
        ArrayList<Menu> items = sw.getMenuItems(itemCount);
        int count = 0;
        
        for(Menu item: items){
                count++;
                System.out.println(count +". "+ item.getName()+ "("+itemCount.get(item.getId()) + ")");
            }
        System.out.println(++count +". Go back.");
        
        int input5 = sc.nextInt();
        //check for wrong entery
        while(input5 > items.size() + 1){
            System.out.println("Wrong entry. Please enter again: ");
            input5 = sc.nextInt();
        }
        //Go back 
        if(input5 == items.size() + 1){
            return;
        }
        System.out.println("Enter a quantity: ");
        int quantity = sc.nextInt();
     
        Order ordUp = new Order(order_id, user_id, tip, total_price, placed_timestamp,
			 delivery_timestamp, card_id, instructions, delivery_method_id, store_id,
			 delivery_status_id);
        //ordUp.setItemQuantity(items.get(input5 - 1).getId(), quantity);
        
        ordServ.update(ordUp);
        System.out.println("Updated " + order_id);   
       
        
    }

    
    public static void addOrdersScreen(){
        /*
        System.out.println("Add an order");
        Scanner sc = new Scanner(System.in);
        System.out.println("\nEnter order id: ");
        String id= sc.next();
        
        //validation
        OrderService ord = new OrderService(con);
        MenuServices itm = new MenuServices(con);
        while(ord.orderExist(id)){
            System.out.println("Order ID exist in database. Please Enter a different Order ID: ");
            id= sc.next();
        }
        
        System.out.println("\nEnter user id: ");
        String userId= sc.next();
        UserService us = new UserService(con);
        while(!us.userExist(userId)){
            System.out.println("USER ID DOES NOT EXIST! Enter the user ID again: ");
            userId= sc.next();
        }
        
        
        System.out.println("\nChoose an item: ");
        MenuServices ms = new MenuServices(con);
        ArrayList<Menu> menus = ms.getAll();
        ServiceWrapper.printMenuItems(menus);
        int input = sc.nextInt();
        
        //check for wrong entery
        while(input > menus.size() + 1){
            System.out.println("Wrong entry. Please enter again: ");
            input = sc.nextInt();
        }
        //Go back 
        if(input == menus.size() + 1){
            return;
        }
        
        Menu men = menus.get(input-1);
        
        
        
        boolean yesorno= false;
        do{
            System.out.println("would you like to add an item?");
            System.out.println("1. Yes");
            System.out.println("2. No?");
            System.out.println("3. Go back");
            
            int input = sc.nextInt();
            if(input >= 3)
                return;
            if(input == 1)
                yesorno = true;
            if(input == 2 )
                 yesorno = false;
            
        }while(true);
        
        
        
        
        
        
        
        
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
=======

    public static void addOrdersScreen() {
>>>>>>> ad9077773e50a7a06021ea331cff5c8f5a34f436

        Menu men = new Menu(id, name, vegetarian, type, description, slot_ID, photo, price);
        MenuServices menServ = new MenuServices(con);
        menServ.add(men);
        System.out.println("\n" + name + " added to database\n");
        AdminAndManager aam = new AdminAndManager(con);
        aam.adminScreen();
*/
    }

    public static void deleteOrdersScreen(){
        System.out.println("Choose an order to delete");
        
        OrderService os = new OrderService(con);
        ArrayList<Order> orders = os.getAll();
        ServiceWrapper.printOrders(orders);
        
        Scanner sc = new Scanner(System.in);
        int input = sc.nextInt();
        
        //check for wrong entery
        while(input > orders.size() + 1){
            System.out.println("Wrong entry. Please enter again: ");
            input = sc.nextInt();
        }
        //Go back 
        if(input == orders.size() + 1){
            return;
        }

        OrderService orderServ = new OrderService(con);
        orderServ.deleteById(orders.get(input-1).getOrder_id());
        System.out.println("Deleted: " + orders.get(input-1).toString());
    }

    public static void alterUserStatusScreen() {
        UserStatusService uss = new UserStatusService(con);
        ArrayList<UserStatus> userStatuses = uss.getAll();
        int input = 0;
        while (input != userStatuses.size() + 1) {
            //userStatuses = uss.getAll();
            ArrayList<String> options = new ArrayList<>();
            for (UserStatus status : userStatuses) {
                options.add(status.getUserStatus());
            }
            input = choiceScreen(options);
            if (input < userStatuses.size() + 1) {
                alterUserStatusFieldScreen(userStatuses.get(input - 1));
                System.out.println("Status Changed");
            } else if (input > userStatuses.size() + 1) {
                System.out.println("Invalid input");
            }
        }
    }

    public static void alterUserStatusFieldScreen(UserStatus status) {
        UserStatusService uss = new UserStatusService(con);
        Scanner sc = new Scanner(System.in);
        int input = 0;
        while (input != 3) {
            ArrayList<String> options = new ArrayList<>();
            options.add("ID:" + status.getUserStatusId());
            options.add("Status:" + status.getUserStatusId());
            input = choiceScreen(options);
            switch (input) {
                case 1: {
                    String oldId = status.getUserStatusId();
                    System.out.println("Enter the new Id");
                    String newId = sc.next();
                    try {
                        status.setUserStatusId(newId);
                        uss.deleteById(oldId);
                        uss.add(status);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                }
                case 2: {
                    System.out.println("Enter the new Status");
                    String newStatus = sc.next();
                    status.setUserStatus(newStatus);
                    break;
                }
            }
        }
        uss.update(status);

    }

    public static void addUserStatusScreen() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the new Status ID");
        String id = sc.next();
        System.out.println("Enter the new Status");
        String statusName = sc.next();
        try {
            UserStatus status = new UserStatus(id, statusName);
            UserStatusService uss = new UserStatusService();
            uss.add(status);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void deleteUserStatusScreen() {
        UserStatusService uss = new UserStatusService();
        ArrayList<UserStatus> statusList = uss.getAll();
        ArrayList<String> options = new ArrayList<>();
        int input;
        for (UserStatus status : statusList) {
            options.add(status.getUserStatusId() + ":" + status.getUserStatus());
        }
        input = choiceScreen(options);
        if (input < options.size() + 1) {
            uss.deleteById(statusList.get(input - 1).getUserStatusId());
        }
        System.out.println("User Status Deleted");
    }

    public static void alterStoresScreen() {
        StoreService ls = new StoreService(con);
        ArrayList<Store> stores = ls.getAll();
        ServiceWrapper.printStores(stores);
        Scanner sc = new Scanner(System.in);
        System.out.println("Choose a store to alter");
        int input = sc.nextInt();
        //check for wrong entery
        while (input > stores.size() + 1) {
            System.out.println("Wrong entry. Please enter again: ");
            input = sc.nextInt();
        }
        //Go back 
        if (input == stores.size() + 1) {
            return;
        }
        Store store = stores.get(input - 1);
        StoreService storeServ = new StoreService(con);
        System.out.println("Alter a Store");
        System.out.println("\nEnter location ID: ");
        String locationId = sc.next();
        LocationService locs = new LocationService(con);
        while (locs.locationIdExist(locationId)) {
            System.out.println("Location ID does not exist! Enter a different location ID: ");
            locationId = sc.next();
        }
        System.out.println("\nEnter store name: ");
        String storeName = sc.next();
        System.out.println("\nEnter phone number: ");
        String phoneNum = sc.next();
        System.out.println("\nEnter manager ID: ");
        String manId = sc.next();
        System.out.println("\nEnter open time: ");
        int openTime = sc.nextInt();
        System.out.println("\nEnter close time: ");
        int closeTime = sc.nextInt();
        String storeId = store.getStoreId();
        Store updatedStore = new Store(storeId, locationId, storeName, phoneNum, manId, openTime, closeTime);
        System.out.println(updatedStore);
        storeServ.update(updatedStore);
        System.out.println("Updated store " + storeId);
    }

    public static void addStoresScreen() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Add a Store");
        System.out.println("\nEnter store id: ");
        String storeId = sc.next();
        StoreService sts = new StoreService(con);
        while (!sts.storeIdExists(storeId)) {
            System.out.println("Store ID already exists! Enter a different store ID: ");
            storeId = sc.next();
        }
        System.out.println("\nEnter location id: ");
        String locationId = sc.next();
        LocationService ls = new LocationService(con);
        while (ls.locationIdExist(locationId)) {
            System.out.println("Location ID does not exist! Enter a different location ID: ");
            locationId = sc.next();
        }
        System.out.println("\nEnter store name: ");
        String storeName = sc.next();
        System.out.println("\nEnter phone number: ");
        String phoneNum = sc.next();
        System.out.println("\nEnter manager ID: ");
        String manId = sc.next();
        System.out.println("\nEnter open time: ");
        int openTime = sc.nextInt();
        System.out.println("\nEnter close time: ");
        int closeTime = sc.nextInt();

        Store store = new Store(storeId, locationId, storeName, phoneNum, manId, openTime, closeTime);
        StoreService storeServ = new StoreService(con);
        storeServ.add(store);
        System.out.println("\n" + storeName + " added to database\n");
        AdminAndManager aam = new AdminAndManager(con);
        aam.adminScreen();
    }

    public static void deleteStoresScreen() {
        StoreService sts = new StoreService(con);
        ArrayList<Store> stores = sts.getAll();
        ServiceWrapper.printStores(stores);
        Scanner sc = new Scanner(System.in);
        System.out.println("Choose a location to delete");
        int input = sc.nextInt();
        if (input == stores.size() + 1) {
            return;
        }
        if (input == stores.size() + 2) {
            System.exit(0);
        }
        StoreService storeServ = new StoreService(con);
        String idToDel = (stores.get(input - 1)).getLocationId();
        storeServ.deleteById(idToDel);
        System.out.println("Location successfully deleted! " + stores.get(input - 1).toString());
    }
}
