import com.sun.security.jgss.GSSUtil;

import java.util.*;

public class ByteMe {

    public static ArrayList<FoodItem> Menu = new ArrayList<>();
    public static ArrayList<User> registeredUsers = new ArrayList<>();
    public static TreeMap<FoodItem, ArrayList<String>> reviews=new TreeMap<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Admin admin = new Admin();
        FoodItem f1 = new FoodItem("burger", "fastfood", 75, true, 1);
        FoodItem f2 = new FoodItem("pizza", "italian", 100, true, 2);
        FoodItem f3 = new FoodItem("pasta", "italian", 90, true, 3);
        FoodItem f4 = new FoodItem("paneer roll", "fastfood", 50, true, 4);
        FoodItem f5 = new FoodItem("cold coffee", "beverage", 60, true, 5);
        FoodItem f6 = new FoodItem("tea", "beverage",20 , true, 6);
        FoodItem f7 = new FoodItem("hot coffee", "beverage", 30, true, 7);
        Menu.add(f1);
        Menu.add(f2);
        Menu.add(f3);
        Menu.add(f4);
        Menu.add(f5);
        Menu.add(f6);
        Menu.add(f7);
        Menu m=new Menu();

        User Simar = new User("Simar", "simar12345", admin);
        User Rushil = new User("Rushil", "rushil12345", admin);
        VIPuser Saisha=new VIPuser("Saisha", "saisha123", 100.0, admin);
        registeredUsers.add(Simar);
        registeredUsers.add(Rushil);
        registeredUsers.add(Saisha);
        User newUser = null;

        boolean loggedIn = false;
        while (!loggedIn) {
            System.out.println("Welcome to ByteME, an online canteen service!");
            System.out.println("\nEVERY PROMPT IS CASE SENSITIVE.");
            System.out.println("\nAre you a student or admin? OR Do you want to LOGOUT?");
            System.out.println("Enter one of the 3 options: ");
            String role = scanner.nextLine();

            if (role.toLowerCase().equals("logout")) {
                System.out.println("Logging out from ByteME...");
                break;
            } else if (!role.equals("student") && !role.equals("admin") && !role.equals("logout")) {
                System.out.println("Invalid option chosen. Select again");
            } else if (role.equals("student")) {
                System.out.println("1-Login");
                System.out.println("2-Register");
                System.out.println("Enter option number: ");
                int option = scanner.nextInt();
                scanner.nextLine();

                if (option == 1) {
                    boolean EntryGranted = false;
                    while (!EntryGranted) {
                        System.out.println("Enter your credentials.");
                        System.out.println("Name: ");
                        String name = scanner.nextLine();
                        System.out.println("Password: ");
                        String p = scanner.nextLine();
                        for (User u : registeredUsers) {
                            if (u.getName().trim().equals(name.trim()) && u.isPswrdCorrect(p.trim())) {
                                newUser = u;
                                EntryGranted = true;
                                loggedIn = true;
                            }
                        }
                        if (!EntryGranted) {
                            System.out.println("Invalid credentials. Try again.");
                            loggedIn=true;
                        }
                    }
                } else if (option == 2) {
                    System.out.println("Enter name: ");
                    String name2=scanner.nextLine();
                    System.out.println("Create your password: ");
                    String p2= scanner.nextLine();
                    newUser=new User(name2, p2, admin);
                    System.out.println("Successfully registered!");
                    break;

                } else {
                    System.out.println("Invalid option chosen. Type 1 or 2.");
                }
                System.out.println("Welcome "+newUser.getName().toUpperCase()+" !");
                boolean menuDisplay=true;
                while(menuDisplay){
                    System.out.println("What would you like to choose? ");
                    System.out.println("1-Browse Menu");
                    System.out.println("2-Search");
                    System.out.println("3-Filter menu by category");
                    System.out.println("4-Sort menu by price");
                    System.out.println("5-Add items to cart");
                    System.out.println("6-Modify quantities");
                    System.out.println("7-Remove items");
                    System.out.println("8-View total bill");
                    System.out.println("9-Checkout and place order");
                    System.out.println("10-Check order status");
                    System.out.println("11-Cancel order");
                    System.out.println("12-Order history");
                    System.out.println("13-Write review for some item");
                    System.out.println("14-View reviews for some item");
                    System.out.println("15-Logout");
                    System.out.println("Enter option number: ");
                    int userOp= scanner.nextInt();
                    scanner.nextLine();
                    switch(userOp){
                        case 1:
                            System.out.println("View Menu");
                            newUser.viewMenu();
                            break;
                        case 2:
                            System.out.println("Search");
                            System.out.println("What are you looking for? ");
                            String search= scanner.nextLine();
                            newUser.searchFood(search);
                            break;
                        case 3:
                            System.out.println("Filter by category");
                            newUser.categorize();
                            break;
                        case 4:
                            System.out.println("Sort by price");
                            System.out.println("1-Sort by increasing prices.");
                            System.out.println("2-Sort by decreasing prices.");
                            System.out.println("Enter option number: ");
                            int opSort=scanner.nextInt();
                            scanner.nextLine();
                            if(opSort==1){
                                newUser.sortPriceAscending();
                            }
                            else if(opSort==2){
                                newUser.sortPriceDescending();
                            }
                            else{
                                System.out.println("Invalid option chosen.");
                            }
                            break;
                        case 5:
                            System.out.println("Add to cart");
                            m.viewAllItems();
                            boolean done=true;
                            while(done){
                                System.out.println("Enter number corresponding to food-item or enter -1 to exit cart.");
                                int c= scanner.nextInt();
                                scanner.nextLine();
                                if(c==-1){
                                    System.out.println("Exiting the cart......");
                                    break;
                                }
                                else if(c>=1 && c<=Menu.size()){
                                    newUser.addToCart(Menu.get(c-1));
                                }
                                else{
                                    System.out.println("Enter correct number.");
                                }
                            }
                            break;
                        case 6:
                            System.out.println("Modify quantity");
                            newUser.viewCurrOrder();
                            System.out.println("Enter number corresponding to food-item: ");
                            int m1= scanner.nextInt();
                            scanner.nextLine();
                            if(m1>=1 && m1<=newUser.myCurrOrder().size()){
                                FoodItem fSelected=newUser.myCurrOrder().get(m1-1);
                                System.out.println("Enter new qty: ");
                                int m2= scanner.nextInt();
                                scanner.nextLine();
                                newUser.changeQty(fSelected, m2);
                            }
                            else{
                                System.out.println("Enter correct number.");
                            }
                            break;
                        case 7:
                            System.out.println("Remove items from cart");
                            newUser.viewCurrOrder();
                            boolean doneRem=true;
                            while(doneRem) {
                                System.out.println("Enter number corresponding to food-item or enter -1 to exit cart: ");
                                int c = scanner.nextInt();
                                scanner.nextLine();
                                if (c == -1) {
                                    System.out.println("Exiting the cart......");
                                    break;
                                } else if (c >= 1 && c <= Menu.size()) {
                                    newUser.delFromCart(Menu.get(c - 1));
                                } else {
                                    System.out.println("Enter correct number.");
                                }
                            }
                            break;
                        case 8:
                            System.out.println("View total bill");
                            newUser.billCart();
                            break;
                        case 9:
                            System.out.println("Checkout and place order");
                            newUser.checkout();
                            break;
                        case 10:
                            System.out.println("Check order status");
                            newUser.viewStatus();
                            break;
                        case 11:
                            System.out.println("Cancel order");
                            newUser.cancelOrder();
                            break;
                        case 12:
                            System.out.println("Order history");
                            newUser.viewOrderHistory();
                            System.out.println("Re-order this ?(yes/no)");
                            String yN= scanner.nextLine();
                            if(yN.toLowerCase().equals("yes")){
                                newUser.reOrder();
                                break;
                            }
                            else{
                                break;
                            }
                        case 13:
                            System.out.println("Write a review");
                            newUser.viewMenu();
                            System.out.println("Select item for which to write a review(Enter number corresponding to item)");
                            int c = scanner.nextInt();
                            scanner.nextLine();
                            if (c >= 1 && c <= Menu.size()) {
                                FoodItem f= Menu.get(c-1);
                                System.out.println("Write a review: ");
                                String r=scanner.nextLine();
                                newUser.writeReview(f, r);
                            } else {
                                System.out.println("Enter correct number.");
                            }
                            break;
                        case 14:
                            System.out.println("View reviews");
                            newUser.viewMenu();
                            System.out.println("Select item for which to view review(Enter number corresponding to item)");
                            int cView = scanner.nextInt();
                            scanner.nextLine();
                            if (cView >= 1 && cView <= Menu.size()) {
                                FoodItem fView= Menu.get(cView-1);
                                newUser.viewReviews(fView);
                            } else {
                                System.out.println("Enter correct number.");
                            }
                            break;
                        case 15:
                            System.out.println("Logging out from account.....");
                            loggedIn = false;
                            menuDisplay=false;
                            break;
                        default:
                            System.out.println("Invalid option chosen. Enter valid option");
                            menuDisplay=false;
                            break;
                    }
                }


            } else {
                boolean EntryGranted = false;
                while (!EntryGranted) {
                    System.out.println("Enter your credentials.");
                    System.out.println("Name: ");
                    String name = scanner.nextLine();
                    System.out.println("Password: ");
                    String p = scanner.nextLine();
                    if (name.equals("AdminIIITD") && p.equals("AdminIIITD@123")) {
                        EntryGranted = true;
                        loggedIn = true;
                    } else {
                        System.out.println("Invalid credentials. Try again.");
                        break;
                    }
                    System.out.println("Welcome ADMIN !");
                    boolean menuDis=true;
                    while(menuDis){
                        System.out.println("What would you like to choose? ");
                        System.out.println("1-Add new items");
                        System.out.println("2-Update existing items");
                        System.out.println("3-Remove items");
                        System.out.println("4-Modify prices");
                        System.out.println("5-Update availability");
                        System.out.println("6-View pending orders");
                        System.out.println("7-Update order status");
                        System.out.println("8-Process refunds");
                        System.out.println("9-Handle special requests");
                        System.out.println("10-Generate Daily sales report");
                        System.out.println("11-Logout");
                        System.out.println("Enter option number: ");
                        int userOp= scanner.nextInt();
                        scanner.nextLine();
                        switch(userOp){
                            case 1:
                                System.out.println("Add new items");
                                admin.addNewItems();
                                break;
                            case 2:
                                System.out.println("Update existing items");
                                m.viewAllItems();
                                System.out.println("Select item to be updated(Enter number for item!)");
                                int c = scanner.nextInt();
                                scanner.nextLine();
                                FoodItem toUpdate=null;
                                if (c >= 1 && c <= Menu.size()) {
                                    toUpdate=Menu.get(c-1);
                                } else {
                                    System.out.println("Enter correct number.");
                                    break;
                                }
                                System.out.println("Select update to be made");
                                System.out.println("1-Update Name");
                                System.out.println("2-Update Category");
                                System.out.println("3-Update ID");
                                System.out.println("Enter number corresponding to option");
                                int opUpdate= scanner.nextInt();
                                scanner.nextLine();
                                switch(opUpdate){
                                    case 1:
                                        admin.updateName(toUpdate);
                                        break;
                                    case 2:
                                        admin.updateCategory(toUpdate);
                                        break;
                                    case 3:
                                        admin.updateID(toUpdate);
                                        break;
                                    default:
                                        System.out.println("Invalid option chosen. Try again.");
                                        break;
                                }
                                break;
                            case 3:
                                System.out.println("Remove items");
                                m.viewAllItems();
                                boolean doneRem=true;
                                while(doneRem) {
                                    System.out.println("Enter number corresponding to food-item or enter -1 to exit cart.");
                                    int cRem = scanner.nextInt();
                                    scanner.nextLine();
                                    if (cRem == -1) {
                                        System.out.println("Exiting the cart......");
                                        break;
                                    } else if (cRem >= 1 && cRem <= Menu.size()) {
                                        admin.delItems(Menu.get(cRem - 1));
                                    } else {
                                        System.out.println("Enter correct number.");
                                    }
                                }
                                break;
                            case 4:
                                System.out.println("Modify prices");
                                m.viewAllItems();
                                System.out.println("Select item to be updated(Enter number for item!)");
                                int cP = scanner.nextInt();
                                scanner.nextLine();
                                FoodItem toModify=null;
                                if (cP >= 1 && cP <= Menu.size()) {
                                    toModify=Menu.get(cP-1);
                                } else {
                                    System.out.println("Enter correct number.");
                                    break;
                                }
                                admin.updatePrice(toModify);
                                break;
                            case 5:
                                System.out.println("Update availability");
                                m.viewAllItems();
                                System.out.println("Select item to be updated(Enter number for item!)");
                                int cU = scanner.nextInt();
                                scanner.nextLine();
                                FoodItem toUpAvail=null;
                                if (cU >= 1 && cU <= Menu.size()) {
                                    toUpAvail=Menu.get(cU-1);
                                } else {
                                    System.out.println("Enter correct number.");
                                    break;
                                }
                                admin.updateAvail(toUpAvail);
                                break;
                            case 6:
                                System.out.println("View pending orders");
                                admin.viewPendingOrders();
                                break;
                            case 7:
                                System.out.println("Update order status");
                                admin.orderStatusUpdate();
                                break;
                            case 8:
                                System.out.println("Process refunds");
                                admin.refundProcedure();
                                break;
                            case 9:
                                System.out.println("Handle special requests");
                                admin.viewSpecialRequests();
                                break;
                            case 10:
                                System.out.println("Daily sales report");
                                admin.viewDailySalesReport();
                                break;
                            case 11:
                                System.out.println("Logging out from ByteMe....");
                                loggedIn = false;
                                menuDis=false;
                                break;
                            default:
                                System.out.println("Invalid option chosen. Please chose valid option.");
                                break;
                        }
                    }


                }
            }
        }
    }
}