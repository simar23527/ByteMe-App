import java.util.Map;
import java.util.TreeMap;
import java.util.ArrayList;
import java.util.List;

public class Cart {
    private TreeMap<FoodItem, Integer> Cart;
    private User u;

    public Cart(User U){
        this.u=U;
        this.Cart=new TreeMap<>();
    }

    public boolean containsItem(FoodItem f) {
        return Cart.containsKey(f);
    }

    public User gettCartUser(){
        return this.u;
    }

    public void addItem(FoodItem f){
        if(f.getAvailability()){
            if(Cart.containsKey(f)){
                int curr=Cart.get(f);
                Cart.put(f, curr+1);
            }
            else{
                Cart.put(f, 1);
            }
        }
        else{
            //do exception handling
            System.out.println("FoodItem not available right now !");
        }

    }

    public boolean isCartEmpty(){
        return Cart.size()==0;
    }

    public void removeItem(FoodItem f){
        Cart.remove(f);
    }

    public void modifyQuantity(FoodItem f, int qty){
          this.removeItem(f);
          int currentQ=Cart.get(f);
          if(qty>currentQ){
              if(!f.getAvailability()){
                  System.out.println("Can't add more as item not available");
              }
              else{
                  Cart.put(f,qty);
                  System.out.println("Quantity modified successfully.");
              }
          }
          else{
              Cart.put(f,qty);
              System.out.println("Quantity modified successfully.");
          }

    }

    public void clearCart(){
         Cart.clear();
    }

    public double getTotalAmt(){
        double totalPrice=0;
        for(Map.Entry<FoodItem, Integer> entry: Cart.entrySet()){
            totalPrice+=( entry.getValue())*(entry.getKey().getPrice());
        }
        return totalPrice;
    }

    public void viewCartItems(){
        for(Map.Entry<FoodItem, Integer> entry: Cart.entrySet()){
            System.out.println("Item: "+entry.getKey().getFoodName()+" Qty: "+entry.getValue());
        }
        System.out.println("Total amt: "+this.getTotalAmt());
    }

    public ArrayList<FoodItem> getItems(){
        ArrayList<FoodItem> itemsOrder=new ArrayList<>();
        for(Map.Entry<FoodItem, Integer> entry: Cart.entrySet()){
            itemsOrder.add(entry.getKey());
        }
        return itemsOrder;
    }

    public void getTotalBill(){
        float totalPrice=0;
        if(Cart.isEmpty()){
            System.out.println("No items in cart.");
        }
        else{
            for(Map.Entry<FoodItem, Integer> entry: Cart.entrySet()){
                if(entry.getValue()>0){
                    totalPrice+=( entry.getValue())*(entry.getKey().getPrice());
                    System.out.println("Item: "+entry.getKey().getFoodName());
                    System.out.println("Qty: "+entry.getValue());
                    System.out.println("Cost per Item: "+entry.getKey().getPrice());
                    System.out.println("Cost in total: "+entry.getValue()*entry.getKey().getPrice());
                }
                else{
                    System.out.println("No items in cart.");
                    return;
                }

            }
            System.out.println("Total Bill: "+totalPrice);
        }

    }




}
