public interface OrderManagement {
    void addOrder(Order order);
    void updateOrderStatus(Order order, boolean status);
}
