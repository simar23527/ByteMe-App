import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class VIPuser extends User{
    private double vipFee;

    public VIPuser(String n, String p, Double fee, Admin a){
        super(n,p,a);
        this.vipFee=fee;

    }
    public double getVipFee() {
        return vipFee;
    }

    @Override
    public String getName() {
        return super.getName();
    }
    @Override
    public boolean isPswrdCorrect(String p) {
        return super.isPswrdCorrect(p);
    }
    @Override
    public boolean isOrderCancelled() {
        return super.isOrderCancelled();
    }
    @Override
    public void cancel() {
        super.cancel();
    }
    @Override
    public void viewMenu() {
        super.viewMenu();
    }
    @Override
    public void sortPriceAscending() {
        super.sortPriceAscending();
    }
    @Override
    public void sortPriceDescending() {
        super.sortPriceDescending();
    }
    @Override
    public void categorize() {
        super.categorize();
    }
    @Override
    public void searchFood(String s) {
        super.searchFood(s);
    }
    @Override
    public void delFromCart(FoodItem f) {
        super.delFromCart(f);
    }
    @Override
    public void changeQty(FoodItem f, int newQ) {
        super.changeQty(f, newQ);
    }
    @Override
    public void billCart() {
        super.billCart();
    }
    @Override
    public ArrayList<FoodItem> myCurrOrder() {
        return super.myCurrOrder();
    }
    @Override
    public void viewCurrOrder() {
        super.viewCurrOrder();
    }
    @Override
    public void checkout() {
        Scanner scanner=new Scanner(System.in);
        if(cart.isCartEmpty()){
            System.out.println("Empty Cart. Add items to checkout.");
            return;
        }
        Order myOrder=new Order(this,1);
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
    @Override
    public void cancelOrder() {
        super.cancelOrder();
    }
    @Override
    public void viewStatus() {
        super.viewStatus();
    }
    @Override
    public void viewOrderHistory() {
        super.viewOrderHistory();
    }
    @Override
    public void writeReview(FoodItem f, String r) {
        super.writeReview(f, r);
    }
    @Override
    public void viewReviews(FoodItem f) {
        super.viewReviews(f);
    }
    @Override
    public void addToCart(FoodItem f) {
        super.addToCart(f);
    }

}
