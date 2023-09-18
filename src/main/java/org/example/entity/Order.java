package org.example.entity;

public class Order {
    private String orderId;
    private int price;
    private int quantity;

    public Order(String orderId, int price, int quantity) {
        this.orderId = orderId;
        this.price = price;
        this.quantity = quantity;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}
