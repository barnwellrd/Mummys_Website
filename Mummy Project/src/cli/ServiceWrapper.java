package cli;

import static cli.Tiger.sc;
import java.sql.Connection;
import java.util.ArrayList;
import domain.*;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import services.*;


public class ServiceWrapper {

    Connection con;

    public ServiceWrapper(Connection con) {
        super();
        this.con = con;

    }

    public User login(String email, String password) {

        UserService us = new UserService(con);
        User candidate = us.getByEmail(email);
        System.out.println(candidate.getFirstName());
        if (password.equals(candidate.getPassword())) {
            return candidate;
        } else {
            return null;
        }
    }

    public User register(String firstName, String lastName, String phone, String email, String password) {
        //, String street, String city, String state, String country, String zip, String userStatus
        boolean result = false;
        String userId = Double.toString(Math.random() * 10001);
        String userStatusId = "1";

        User user = new User(userId, firstName, lastName, phone, email, password, userStatusId);
        UserService us = new UserService(con);
        result = us.add(user);
        return user;
    }

    public static void printOptions(ArrayList<String> options) {
        options.add("Go back");
        int count = 0;
        for (String option : options) {
            count++;
            System.out.println(count + ". " + option);
        }

    }

    public static void printMenuItems(ArrayList<Menu> menus) {
        int count = 0;
        for (Menu menu : menus) {
            count++;
            String formattedString = String.format("%.02f", menu.getPrice());
            System.out.println(count + "." + menu.getName() + " - $" + formattedString + "\n " + menu.getDescription());
            // System.out.println(df.format(menu.getPrice()));
        }
        System.out.println(++count + ". Go Back");
    }

    public static void printSpecials(ArrayList<Special> specs) {
        int count = 0;
        for (Special spec : specs) {
            count++;
            System.out.println(count + ". " + spec.toString());
        }
        System.out.println(++count + ". Go Back");
    }

    public static void printLocations(ArrayList<Location> locs) {
        int count = 0;
        for (Location loc : locs) {
            count++;
            System.out.println(count + ". " + loc.toString());
        }
        System.out.println(++count + ". Go Back");
    }

    public static void printOrders(ArrayList<Order> orders) {
        int count = 0;
        for (Order order : orders) {
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
        String subject = "Mummy Resaurant. Order #" + currentOrder.getOrder_id()
                + ", Order timestamp:" + currentOrder.getPlaced_timestamp();
        HashMap<String, Integer> itemCount = currentOrder.getItemCount();
        UserService us = new UserService(con);
        User user = us.getById(currentOrder.getUser_id());
        String target = user.getEmail();
        MenuServices ms = new MenuServices(con);
        String message = "";
        for (String itemId : itemCount.keySet()) {
            Menu item = ms.getById(itemId);
            int count = itemCount.get(itemId);
            String formattedString = String.format("%.02f",
                    item.getPrice() * count);
            message += item.getName()
                    + "(" + count + ")"
                    + "\t" + formattedString + "\n";
        }
        String formattedString = String.format("%.02f", currentOrder.getTip());
        message += "Tip:" + formattedString + "\n";
        formattedString = String.format("%.02f",
                currentOrder.getTotal_price() + currentOrder.getTip());
        message += "Total:" + formattedString + "\n";
        message += "Delivery Time:" + currentOrder.getDelivery_timestamp();
        System.out.println(message);

        SendEmail se = new SendEmail(subject, message, target);
        se.sendMail();
        os.add(currentOrder);

    }

    public ArrayList<Menu> getMenuItems(HashMap<String, Integer> itemCount) {

        MenuServices ms = new MenuServices(con);
        ArrayList<Menu> items = new ArrayList<>();

        for (String itemId : itemCount.keySet()) {
            items.add(ms.getById(itemId));
        }

        return items;
    }

    public static void printItemType(ArrayList<ItemType> items) {
        int count = 0;
        for (ItemType item : items) {
            count++;
            System.out.println(count + ". " + item.toString());
        }
        System.out.println(++count + ". Go Back");
    }

    public String validateEmail(String email) {
        Pattern rfc2822 = Pattern.compile("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?");
        boolean isValid = false;
        while (!isValid) {
            if (rfc2822.matcher(email).matches()) {
                isValid = true;

            } else {
                Scanner sc = new Scanner(System.in);
                System.out.println("Please enter a valid Email Address.");
                email = sc.nextLine();
            }
        }
        return email;
    }

    public String validateZipCode(String zipCode) {
        Pattern rfc2822 = Pattern.compile("^[0-9]{5}(?:-[0-9]{4})?$");
        boolean isValid = false;
        while (!isValid) {
            if (rfc2822.matcher(zipCode).matches()) {
                isValid = true;

            } else {
                Scanner sc = new Scanner(System.in);
                System.out.println("Please enter a valid Zip Code.");
                zipCode = sc.nextLine();
            }
        }
        return zipCode;
    }

    public String validatePhone(String phone) {
        Pattern rfc2822 = Pattern.compile("^(?:(?:\\+?1\\s*(?:[.-]\\s*)?)?(?:\\(\\s*([2-9]1[02-9]|[2-9][02-8]1|[2-9][02-8][02-9])\\s*\\)|([2-9]1[02-9]|[2-9][02-8]1|[2-9][02-8][02-9]))\\s*(?:[.-]\\s*)?)?([2-9]1[02-9]|[2-9][02-9]1|[2-9][02-9]{2})\\s*(?:[.-]\\s*)?([0-9]{4})(?:\\s*(?:#|x\\.?|ext\\.?|extension)\\s*(\\d+))?$");
        boolean isValid = false;
        while (!isValid) {
            if (rfc2822.matcher(phone).matches()) {
                isValid = true;

            } else {
                Scanner sc = new Scanner(System.in);
                System.out.println("Please enter a valid Phone Number.");
                phone = sc.nextLine();
            }
        }
        return phone;
    }

    //not used yet
    public String validateDate(String date) {
        Pattern rfc2822 = Pattern.compile("^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$");
        boolean isValid = false;
        while (!isValid) {
            if (rfc2822.matcher(date).matches()) {
                isValid = true;

            } else {
                Scanner sc = new Scanner(System.in);
                System.out.println("Please enter a valid Date(DD/MM/YY).");
                date = sc.nextLine();
            }
        }
        return date;
    }

    public String validateCC(String cc) {
        String regex = "^(?:(?<visa>4[0-9]{12}(?:[0-9]{3})?)|"
                + "(?<mastercard>5[1-5][0-9]{14})|"
                + "(?<discover>6(?:011|5[0-9]{2})[0-9]{12})|"
                + "(?<amex>3[47][0-9]{13})|"
                + "(?<diners>3(?:0[0-5]|[68][0-9])?[0-9]{11})|"
                + "(?<jcb>(?:2131|1800|35[0-9]{3})[0-9]{11}))$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher;
        boolean isValid = false;
        while (!isValid) {
            matcher = pattern.matcher(cc);
            if (matcher.matches()) {
                isValid = true;

            } else {
                Scanner sc = new Scanner(System.in);
                System.out.println("Please enter a valid Credit Card Number.");
                cc = sc.nextLine();
            }
        }
        return cc;
    }

    public String validatCVC(String cvc) {
        Pattern rfc2822 = Pattern.compile("^[0-9]{3,4}$");
        boolean isValid = false;
        while (!isValid) {
            if (rfc2822.matcher(cvc).matches()) {
                isValid = true;

            } else {
                Scanner sc = new Scanner(System.in);
                System.out.println("Please enter a valid CVC.");
                cvc = sc.nextLine();
            }
        }
        return cvc;
    }

    public String validateTime(String time) {
        Pattern rfc2822 = Pattern.compile("^[0-9]{1,4}$");
        boolean isValid = false;
        while (!isValid) {
            if (rfc2822.matcher(time).matches()) {
                if (Integer.getInteger(time) >= 0 && Integer.getInteger(time) <= 2400) {
                    isValid = true;

                } else {
                    Scanner sc = new Scanner(System.in);
                    System.out.println("Please enter a valid Delivery Time(0000-2400).");
                    time = sc.nextLine();
                }

            } else {
                Scanner sc = new Scanner(System.in);
                System.out.println("Please enter a valid Delivery Time(0000-2400).");
                time = sc.nextLine();
            }
        }
        return time;
    }

    public String validateNotEmpty(String input) {
        boolean isValid = false;
        while (!isValid) {
            if (input.length() > 0) {
                isValid = true;

            } else {
                Scanner sc = new Scanner(System.in);
                System.out.println("Please enter a valid input.");
                input = sc.nextLine();
            }
        }
        return input;
    }
   
    
        public static void printStores(ArrayList<Store> stores) {
           int count = 0;
           for(Store store: stores) {
                    count++;
                    System.out.println(count + ". " + store.toString());
            }
            System.out.println(++count + ". Go Back");
        }


    public boolean validatePassword(String password) {
        return false;

    }

    public double validateMoney(String money) {
        Number number = null;
        boolean isValid = false;
        while (!isValid) {
            try {
                number = NumberFormat.getCurrencyInstance(Locale.US).parse(money);
            } catch (ParseException pe) {
                // ignore
            }

            if (number != null) {
                // proceed as user entered a good value
                isValid = true;
            } else {
                // user didn't enter a good value
                System.out.println("Please type in a valid amount.");
                money = sc.nextLine();
            }
        }
        return Double.valueOf(money);
    }

    public int validateMenuOption(int start, int finish) {
        int in = 0;
        boolean isOk = true;
        while (isOk) {
            while (!sc.hasNextInt()) {
                System.out.println("Please type a number.");
                sc.nextLine();
            }

            in = sc.nextInt();
            sc.nextLine();
            if (in < start || in > finish) {
                System.out.println("Please type in a valid number.");
                continue;
            }
            isOk = false;
        }
        return in;

    }

    public int validateQuantityOption() {
        int in = 0;
        boolean isOk = true;
        while (isOk) {
            while (!sc.hasNextInt()) {
                System.out.println("Please type a number.");
                sc.nextLine();
            }

            in = sc.nextInt();
            sc.nextLine();
            if (in <= 0) {
                System.out.println("Please enter a number equal to or greater than 0.");
                continue;
            }
            isOk = false;
        }
        return in;
    }

    public int validateYear(String year) {
        Pattern rfc2822 = Pattern.compile("^[0-9]{1,2}$");
        boolean isValid = false;
        while (!isValid) {
            if (rfc2822.matcher(year).matches()) {
                if (Integer.getInteger(year) >= 0 && Integer.getInteger(year) <= 99) {
                    isValid = true;

                } else {
                    Scanner sc = new Scanner(System.in);
                    System.out.println("Please enter a valid Year(YY).");
                    year = sc.nextLine();
                }
            } else {
                Scanner sc = new Scanner(System.in);
                System.out.println("Please enter a valid Year(YY).");
                year = sc.nextLine();
            }
        }
        return Integer.getInteger(year);
    }

    public int validateDay(String day) {
        Pattern rfc2822 = Pattern.compile("^[0-9]{1,2}$");
        boolean isValid = false;
        while (!isValid) {
            if (rfc2822.matcher(day).matches()) {
                if (Integer.getInteger(day) >= 0 && Integer.getInteger(day) <= 31) {
                    isValid = true;

                } else {
                    Scanner sc = new Scanner(System.in);
                    System.out.println("Please enter a valid Day(DD).");
                    day = sc.nextLine();
                }
} else {
                Scanner sc = new Scanner(System.in);
                System.out.println("Please enter a valid Day(DD).");
                day = sc.nextLine();
            }
        }
        return Integer.getInteger(day);
    }
               

	public double calculateTotalPrice(HashMap<String,Integer> itemCount) {
            SpecialServices ss = new SpecialServices(con);
            ArrayList<Special> arrS= ss.getAll();
            
            ServiceWrapper sw = new ServiceWrapper(con);
            ArrayList<Menu> items = sw.getMenuItems(itemCount);
            
            for (int i=0; i<items.size(); i++) {
                for(int j=0;j<arrS.size();j++) {
                    if(items.get(i).getId().equals(arrS.get(j).getItem_ID())) {
                        Double disc = ((Double.valueOf(arrS.get(j).getDiscoutPercentage()))/100);
                        items.get(i).setPrice(items.get(i).getPrice()*(1-disc));
                    }
                }
            }
		double total = 0;
		//ServiceWrapper sw = new ServiceWrapper(con);
		//ArrayList<Menu> items = sw.getMenuItems(itemCount);
		for(Menu item: items){
			total += item.getPrice()*itemCount.get(item.getId());
		}
		return total;
	}


    public int validateMonth(String month) {
        Pattern rfc2822 = Pattern.compile("^(1[0-2]|0[1-9])$");
        boolean isValid = false;
        while (!isValid) {
            if (rfc2822.matcher(month).matches()) {
                isValid = true;
            } else {
                Scanner sc = new Scanner(System.in);
                System.out.println("Please enter a valid Month(MM).");
                month = sc.nextLine();
            }
        }
        return Integer.getInteger(month);
    }

}
