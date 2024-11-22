import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.*;

public class Admin implements OrderManagement{
    private static final String name = "AdminIIITD";
    private static final String adminPwd = "AdminIIITD@123";
    private Menu menu;
    private Queue<Order> allDayOrdersVIP=new ArrayDeque<>();
    private Queue<Order> allDayOrdersRegular=new ArrayDeque<>();
    private TreeMap<Integer, String> specialRequests=new TreeMap<>();
    private TreeMap<User, Double> refunds=new TreeMap<>();
    private Double dailySales;



    public Admin(){
          menu=new Menu();
          menu.setmenuAdmin(this);
          this.dailySales=0.0;
    }
    public boolean VerificationPwd(String ip) {
        return adminPwd.equals(ip);
    }
    public void addNewItems(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter new item details");
        String foodName="", category="";
        int id;
        boolean availability;
        double price;
        System.out.println("Name of item: ");
        foodName=scanner.nextLine();
        System.out.println("Category of item(beverage/fastfood etc): ");
        category=scanner.nextLine();
        System.out.println("Price of item: ");
        price=scanner.nextDouble();
        scanner.nextLine();
        System.out.println("Availability of item(true/false): ");
        availability=scanner.nextBoolean();
        scanner.nextLine();
        id=ByteMe.Menu.size()+1;
        FoodItem f=new FoodItem(foodName.toLowerCase(), category.toLowerCase(), price, availability, id);
        menu.addItems(f);
        System.out.println("Item added successfully.");
    }
    public void delItems(FoodItem f){
            menu.removeItems(f);
            System.out.println("Item removed successfully.");
            f.setAvailability(false);
            for (Order order : this.getAllOrders()) {
            if (order.getCart().containsItem(f) && order.getOrderStatusPending()) {
                order.setOrderDenied(true);
                System.out.println("Order " + order.getOrderNum() + " status updated to 'denied' as item has been removed.");
            }
        }
    }
    public void addOrder(Order o){
         if(o.getPriorityLevel()==1){
             allDayOrdersVIP.add(o);
         }
         else{
             allDayOrdersRegular.add(o);
         }
    }
    public void updateName(FoodItem f) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter new name for item: ");
        String newName=scanner.nextLine();
        menu.updateItemName(f, newName);
        System.out.println("Item name updated successfully.");
    }
    public void updateCategory(FoodItem f) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter new category name for item: ");
        String newC=scanner.nextLine();
        menu.updateItemcategory(f, newC);
        System.out.println("Item category updated successfully.");
    }
    public void updatePrice(FoodItem f) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter new price for item: ");
        double newP=scanner.nextDouble();
        scanner.nextLine();
        menu.updateItemprice(f, newP);
        System.out.println("Item price updated successfully.");
    }
    public void updateID(FoodItem f) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter new ID for item: ");
        int newID=scanner.nextInt();
        scanner.nextLine();
        menu.updateID(f, newID);
        System.out.println("Item ID updated successfully.");
    }
    public void updateAvail(FoodItem f) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter new availability for item: ");
        boolean newA=scanner.nextBoolean();
        scanner.nextLine();
        menu.updateItemavail(f, newA);
        System.out.println("Item availability updated successfully.");
    }
    public ArrayDeque<Order> getAllOrders() {
        ArrayDeque<Order> z=new ArrayDeque<>();
        if (this.allDayOrdersVIP.isEmpty() && this.allDayOrdersRegular.isEmpty()) {
                return new ArrayDeque<>();
        }
        z.addAll(allDayOrdersVIP);
        z.addAll(allDayOrdersRegular);
        return z;
    }
    public TreeMap<Integer, String> getAllSpecialRequests() {
        return specialRequests;
    }
    public void addSpecialReq(Order o, String s){
        if(o!=null){
            int num=o.getOrderNum();
            this.specialRequests.put(num, s);
        }
    }
    public void viewSpecialRequests(){
        if(this.getAllSpecialRequests().isEmpty()){
            System.out.println("No requests made.");
        }
        else{
            for(Map.Entry<Integer, String> entry: specialRequests.entrySet()){
                System.out.println("Request for order number "+entry.getKey());
                System.out.println(entry.getValue());
            }
        }
    }
    public void viewPendingOrders(){
        if (allDayOrdersVIP.isEmpty() && allDayOrdersRegular.isEmpty()) {
            System.out.println("No pending orders for the day.");
            return;
        }
        boolean vip=false;
        if(!allDayOrdersVIP.isEmpty()){
            System.out.println("Pending VIP orders: ");
            for(Order o:allDayOrdersVIP){
                if(!o.isOrderDenied()){
                    System.out.println(o.getOrderStatusPending());
                    if(o.getOrderStatusPending()){
                        vip=true;
                        o.displayOrder();
                    }
                }

            }
            if(vip==false){
                System.out.println("Pending orders were denied. No pending orders.");
            }
        }

        boolean r=false;
        if(!allDayOrdersRegular.isEmpty()){
            for(Order o:allDayOrdersRegular){
                if(!o.isOrderDenied()){
                    System.out.println(o.getOrderStatusPending());
                    if(o.getOrderStatusPending()){
                        r=true;
                        o.displayOrder();
                    }
                }
            }
            if(r==false){
                System.out.println("Pending orders were denied. No pending orders.");
            }
        }

    }
    public void processNextOrder(){
        Order next;
        if (!allDayOrdersVIP.isEmpty()) {
            next = allDayOrdersVIP.poll();
        } else if (!allDayOrdersRegular.isEmpty()) {
            next = allDayOrdersRegular.poll();
        } else {
            System.out.println("No pending orders to process.");
            return;
        }

        System.out.println("Processing order number " + next.getOrderNum());
        next.setOrderStatusPending(false);

    }
    public void orderStatusUpdate(){
        Scanner scanner=new Scanner(System.in);
        this.viewPendingOrders();
        System.out.println("Enter number corresponding to order to update: ");
        int oNum=scanner.nextInt();
        scanner.nextLine();
        Order selectedOrder = null;
        for (Order order : this.getAllOrders()) {
            if (order.getOrderNum() == oNum) {
                selectedOrder = order;
                break;
            }
        }
        if (selectedOrder != null) {
            System.out.print("Enter new status (true for pending, false for completed): ");
            boolean newStatus = scanner.nextBoolean();
            scanner.nextLine();
            this.updateOrderStatus(selectedOrder, newStatus);
        } else {
            System.out.println("Order not found. Please check the order number.");
        }
    }
    public void updateOrderStatus(Order o1, boolean value){
        if(o1!=null){
            if(o1.isOrderCancelled()){
                System.out.println("Order has been cancelled. Can't change status.");
            }
            else{
                o1.setOrderStatusPending(value);
                if(value==false){
                    o1.setOrderCompletion(true);
                }
                System.out.println("Status updated for order number "+o1.getOrderNum());
            }
        }
    }
    public void refundProcedure(){
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter number corresponding to the order to process refund: ");
            int oNum = scanner.nextInt();
            scanner.nextLine();
            Order selectedOrder = null;
            for (Order order : this.getAllOrders()) {
                if (order.getOrderNum() == oNum) {
                    selectedOrder = order;
                    break;
                }
            }
            if (selectedOrder != null) {
                if (selectedOrder.isOrderCancelled()) {
                    boolean refundSuccess = this.processRefunds(selectedOrder);
                    if (refundSuccess) {
                        refunds.put(selectedOrder.getOrderUser(), selectedOrder.getBill());
                        System.out.println("Refund processed successfully.");
                    } else {
                        System.out.println("Refund could not be processed.");
                    }
                } else {
                    System.out.println("Refunds can only be issued for canceled orders.");
                }
            } else {
                System.out.println("Order not found. Please check the order number.");
            }
    }
    public boolean processRefunds(Order o){
        boolean refund=false;
        if(o.isOrderCancelled()){
            System.out.println("Processing refund for order number "+o.getOrderNum());
            System.out.println("Refund of Rs."+o.getBill()+" has been initiated");
            refund=true;
        }
        return refund;
    }
    public double viewDailySalesReport() {
        if (this.getAllOrders().isEmpty()) {
            System.out.println("No orders have been made until now.");
            return 0;
        } else {
            double totalSales = 0;
            Map<FoodItem, Integer> popularItems = new HashMap<>();


            for (Order order : this.getAllOrders()) {
                totalSales += order.getBill();
                for (FoodItem item : order.getCart().getItems()) {
                    popularItems.merge(item, 1, Integer::sum);
                }
            }

            List<Map.Entry<FoodItem, Integer>> sortedPopularItems = new ArrayList<>(popularItems.entrySet());
            sortedPopularItems.sort((a, b) -> Integer.compare(b.getValue(), a.getValue()));

            System.out.println("Daily Sales Report:");
            System.out.println("Total Sales: Rs" + totalSales);
            System.out.println("Most Popular Items:");
            for (Map.Entry<FoodItem, Integer> entry : sortedPopularItems) {
                System.out.println(entry.getKey().getFoodName() + ": " + entry.getValue() + " orders");
            }
            System.out.println("Total Orders: " + this.getAllOrders().size());

            return totalSales;
        }
    }


}
