import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class Order {
    private boolean orderStatus;
    private boolean orderCancelled;
    private User user;
    private Admin admin;
    private Cart cart;
    private int orderNum;
    private int priorityLevel;
    private String specialRequest;
    private Boolean orderCompletion;
    private Boolean orderDenied;

    private static int orderCnt=1;

    public Order(User u, int pL){
          this.user=u;
          this.cart=new Cart(u);
          this.orderNum=orderCnt++;
          this.orderCancelled=false;
          this.orderStatus=false;
          this.priorityLevel=pL;
          this.specialRequest="";
          this.orderCompletion=false;
          this.orderDenied=false;
    }

    public User getOrderUser(){
        return this.user;
    }

    public int getOrderNum(){
        return this.orderNum;
    }

    public boolean getOrderStatusPending(){
        return this.orderStatus;
    }

    public boolean isOrderCancelled(){
        return this.orderCancelled;
    }
     public void setOrderCancellation(){
        this.orderCancelled=true;
     }
     public boolean isOrderDenied(){
        return this.orderDenied;
     }
     public void setOrderDenied(boolean val){
        this.orderDenied=val;
     }

     public boolean isOrderCompleted(){
        return this.orderCompletion;
     }
    public void setOrderCompletion(boolean val){
        this.orderCompletion=val;
    }

    public void setOrderStatusPending(boolean value){
        this.orderStatus=value;
    }

    public Cart getCart(){
        return this.cart;
    }

    public void displayOrder(){
        System.out.println("Order Number: "+this.getOrderNum());
        System.out.println("User: "+user.getName());
        System.out.println("Priority Level: "+(priorityLevel == 1 ? "VIP" : "Regular"));
        cart.viewCartItems();
    }

    public Double getBill(){
        return cart.getTotalAmt();
    }

    public int getPriorityLevel() {
        return priorityLevel;
    }

    public void setSpecialRequest(String request) {
        this.specialRequest = request;
    }

    public String getSpecialRequest() {
        return specialRequest;
    }








}
