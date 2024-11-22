import com.sun.source.tree.ForLoopTree;
import com.sun.tools.javac.Main;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TreeMap;
import java.util.*;

public class User implements OrderManagement{
    private String name;
    private String pswd;
    protected Admin admin;
    private Menu menu;
    protected Cart cart;
    private boolean orderDelivered;
    protected TreeMap<LocalDateTime, Order> prevOrders;
    private double myRefundMoney;
    private boolean myCancelledOrder;


    @Override
    public void addOrder(Order order) {

    }

    @Override
    public void updateOrderStatus(Order order, boolean status) {

    }





    public User(String n, String p, Admin a){
//        if (!userManager.registerUser(n, p)) {
//            // Handle existing user or registration failure
//            System.out.println("User already exists or registration failed.");
//        }
        this.name=n;
        this.pswd=p;
        this.menu= new Menu();
        this.cart=new Cart(this);
        this.orderDelivered=false;
        this.prevOrders=new TreeMap<>();
        this.myCancelledOrder=false;
        this.admin=a;

    }

    public String getName(){
        return this.name;
    }
    public boolean isPswrdCorrect(String p){
        return this.pswd.equals(p);
    }
    public boolean isOrderCancelled(){
        return this.myCancelledOrder;
    }
    public void cancel(){
         this.myCancelledOrder=true;
    }

    public void viewMenu(){
        menu.viewAllItems();
    }
    public void sortPriceAscending(){
        menu.displayItems(menu.priceSortingAscending());
    }
    public void sortPriceDescending(){
        menu.displayItems(menu.priceSortingDescending());
    }
    public void categorize(){
        List<FoodItem>mNew= menu.filterCategory();
        for(FoodItem f_n:mNew){
            System.out.println("Item Name: "+f_n.getFoodName());
            System.out.println("Price: "+f_n.getPrice());
            System.out.println("Category: "+f_n.getCategory());
            System.out.println("Availability : "+f_n.getAvailability()+"\n");
        }
    }
    public void searchFood(String s){
        menu.searchFooditem(s);
    }

    //CART OPERATIONS
    public void addToCart(FoodItem f){
        if(f.getAvailability()){
            cart.addItem(f);
            System.out.println("Item added successfully.");
        }
        else{
            System.out.println("Item no longer available. Can't add.");
        }

    }
    public void delFromCart(FoodItem f){
        cart.removeItem(f);
    }
    public void changeQty(FoodItem f, int newQ){
        cart.modifyQuantity(f,newQ);
    }
    public void billCart(){
        cart.getTotalBill();
    }
    public ArrayList<FoodItem> myCurrOrder(){
        ArrayList<FoodItem> myOrder=new ArrayList<>(cart.getItems());
        return myOrder;
    }
    public void viewCurrOrder(){
        int i=1;
        for(FoodItem f:this.myCurrOrder()){
            System.out.println(i+"-Item name "+f.getFoodName());
            i++;
        }
    }
    public void checkout(){
        Scanner scanner=new Scanner(System.in);
        if(cart.isCartEmpty()){
            System.out.println("Empty Cart. Add items to checkout.");
            return;
        }
        Order myOrder=new Order(this,0);
        for (FoodItem item : cart.getItems()) {
            myOrder.getCart().addItem(item);
        }
        //delivery details take
        System.out.println("Add delivery address: ");
        String del=scanner.nextLine();
        //add special requests here
        System.out.println("Mention any special requests(if any) or write NO");
        String spReq=scanner.nextLine();
        if(!spReq.toLowerCase().equals("no")){
            admin.addSpecialReq(myOrder, spReq);
        }
        myOrder.setOrderStatusPending(true);
        admin.addOrder(myOrder);
        prevOrders.put(LocalDateTime.now(), myOrder);
        System.out.println("Order placed successfully!");
        this.myCurrOrder().clear();


    }

    //ORDER RELATED OPTIONS
    public void cancelOrder(){
        if(cart.isCartEmpty()){
            System.out.println("Empty cart. No order has been placed");
            return;
        }
        else{
            if(this.orderDelivered==false){
                Order myOrder=new Order(this, 0);
                for (FoodItem item : cart.getItems()) {
                    myOrder.getCart().addItem(item);
                }
                myOrder.setOrderCancellation();
                cart.clearCart();
                this.myCurrOrder().clear();
                this.orderDelivered=false;
                this.cancel();
                System.out.println("Order cancelled!");
                System.out.println("Request for order refund initiated.");
            }
            else{
                System.out.println("Can't cancel now.");
            }
        }
    }
    public void viewStatus(){
        if(cart.isCartEmpty()){
            System.out.println("Empty cart.");
        }
        else{
            Order recentOrder=prevOrders.lastEntry().getValue();
            if(recentOrder.isOrderCancelled()){
                System.out.println("Denied. Order cancelled before");
            }
            else if(recentOrder.isOrderDenied()){
                System.out.println("Order denied as item has been removed.");
            }
            else if(recentOrder.isOrderCompleted()){
                this.orderDelivered=true;
                System.out.println("Order delivered successfully.");
            }
            else{
                System.out.println("Order received. Preparation underway.");
            }
        }
    }
    public void viewOrderHistory(){
        if(prevOrders.isEmpty()){
            System.out.println("No order history.");
        }
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        for(Map.Entry<LocalDateTime, Order> entry:prevOrders.entrySet()){
            LocalDateTime orderTime=entry.getKey();
            Order order=entry.getValue();
            System.out.println("Order placed on: "+orderTime.format(formatter));
            System.out.println("Order Details: ");
            order.displayOrder();
            if(this.myCurrOrder().isEmpty()){
                System.out.println("Order was cancelled.");
            }
        }
    }
    public ArrayList<Order> myOrders(){
        ArrayList<Order> o=new ArrayList<>();
        if(!prevOrders.isEmpty()){
            for(Map.Entry<LocalDateTime, Order> entry: prevOrders.entrySet()){
                  o.add(entry.getValue());
            }
            return o;
        }
        else{
            return new ArrayList<>();
        }
    }
    public void reOrder(){
        Scanner scanner=new Scanner(System.in);
        if(!myOrders().isEmpty()){
            this.viewOrderHistory();
            System.out.println("Select order you want to reorder(Enter number corresponding to order: ");
            int r=scanner.nextInt();
            scanner.nextLine();
            if(r>=1 &&r<=this.myOrders().size()){
                Order re=this.myOrders().get(r-1);
                //delivery details take
                System.out.println("Add delivery address: ");
                String del=scanner.nextLine();
                //add special requests here
                System.out.println("Mention any special requests(if any) or write NO");
                String spReq=scanner.nextLine();
                if(!spReq.toLowerCase().equals("no")){
                    admin.addSpecialReq(re, spReq);
                }
                prevOrders.put(LocalDateTime.now(), re);
                System.out.println("Order placed successfully!");
            }
            else{
                System.out.println("Enter valid number");
                return;
            }
        }
    }
    public void writeReview(FoodItem f, String r) {
        if (ByteMe.reviews.containsKey(f)) {
             ByteMe.reviews.get(f).add(r);
        } else {
          ArrayList<String> a=new ArrayList<>();
          a.add(r);
          ByteMe.reviews.put(f, a);
        }
    }
    public void viewReviews(FoodItem f) {
        for(Map.Entry<FoodItem, ArrayList<String>> entry: ByteMe.reviews.entrySet()){
            if(entry.getKey().equals(f)){
                if(entry.getValue().isEmpty()){
                    System.out.println("No reviews made for "+f.getFoodName());
                }
                else{
                    ArrayList<String> revs=entry.getValue();
                    for(String s:revs){
                        System.out.println(s);
                    }
                }
            }
        }
    }
    public void viewRefund(Order o){
        if(admin.processRefunds(o)){
            System.out.println("Refund of Rs."+o.getBill()+" received.");
        }
        else{
            System.out.println("Refund pending.");
        }
    }


}
