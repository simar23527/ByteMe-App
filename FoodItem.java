public class FoodItem implements Comparable<FoodItem>{
    private String foodName;
    private String category;
    private double price;
    private boolean availability;
    private int foodID;

    public FoodItem(String name, String category, double price, boolean availability, int ID){
        this.foodName = name;
        this.category=category;
        this.price=price;
        this.availability=availability;
        this.foodID=ID;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getFoodID() {
        return foodID;
    }

    public void setFoodID(int foodID) {
        this.foodID = foodID;
    }


    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean getAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }
    @Override
    public int compareTo(FoodItem other) {
        return Integer.compare(this.foodID, other.foodID);
    }


    @Override
    public String toString() {
        return "FoodItem{" +
                "foodName='" + foodName + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                ", availability=" + availability +
                ", foodID=" + foodID +
                '}';
    }
}
