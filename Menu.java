import com.sun.security.jgss.GSSUtil;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


public class Menu {
    private ArrayList<FoodItem>M;
    private Admin a;

    public Menu(){
        this.M=ByteMe.Menu;
    }

    public Admin getmenuAdmin(Admin a){
        return a;
    }

    public void setmenuAdmin(Admin a){
        this.a=a;
    }

    public void addItems(FoodItem f){
        if(a!=null){
            M.add(f);
        }

    }
    public void removeItems(FoodItem f){
        if(a!=null){
            M.remove(f);
        }

    }
    public void updateItemName(FoodItem f, String newName){
        if(a!=null){
            f.setFoodName(newName);
        }
    }
    public void updateItemcategory(FoodItem f, String newCategory){
        if(a!=null){
            f.setCategory(newCategory);
        }
    }
    public void updateItemprice( FoodItem f, double newPrice){
        if(a!=null){
            f.setPrice(newPrice);
        }
    }
    public void updateItemavail(FoodItem f, boolean newAvail){
        if(a!=null){
            f.setAvailability(newAvail);
        }
    }
    public void updateID(FoodItem f, int newID){
        if(a!=null){
            f.setFoodID(newID);
        }
    }

    public void viewAllItems(){
        int i=1;
        for(FoodItem f:M){
            System.out.println(i+"-Item Name: "+f.getFoodName());
            System.out.println("Price: "+f.getPrice());
            System.out.println("Category: "+f.getCategory());
            System.out.println("Availability : "+f.getAvailability()+"\n");
            i++;
        }
    }

    public void infoItem(FoodItem f){
        System.out.println("Item Name: "+f.getFoodName());
        System.out.println("Price: "+f.getPrice());
        System.out.println("Category: "+f.getCategory());
        System.out.println("Availability : "+f.getAvailability()+"\n");
    }

    public List<FoodItem> priceSortingAscending() {
        List<FoodItem> sortedPrice = new ArrayList<>(ByteMe.Menu);
        sortedPrice.sort((f1, f2) -> Double.compare(f1.getPrice(), f2.getPrice()));
        return sortedPrice;
    }

    public List<FoodItem> priceSortingDescending() {
        List<FoodItem> sortedPrice = new ArrayList<>(ByteMe.Menu);
        sortedPrice.sort((f1, f2) -> Double.compare(f2.getPrice(), f1.getPrice()));
        return sortedPrice;
    }
    public void displayItems(List<FoodItem> sorted){
        for(FoodItem f:sorted){
            System.out.println("Item Name: "+f.getFoodName());
            System.out.println("Price: "+f.getPrice());
            System.out.println("Category: "+f.getCategory());
            System.out.println("Availability : "+f.getAvailability()+"\n");
        }
    }

    //filtering based on category alphabetically
    public List<FoodItem> filterCategory() {
        List<FoodItem> sortedCategory = new ArrayList<>(M);
        sortedCategory.sort((f1, f2) -> f1.getCategory().compareTo(f2.getCategory()));
        return sortedCategory;
    }

    public void searchFooditem(String searchTerm) {
        boolean found = false;
        for (FoodItem item : ByteMe.Menu) {
            if (item.getFoodName().toLowerCase().contains(searchTerm.toLowerCase()) ||
                    item.getCategory().toLowerCase().contains(searchTerm.toLowerCase())) {
                this.infoItem(item);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No matching item found for " + searchTerm);
        } else {
            System.out.println("Following results may help: ");
        }
    }





}
