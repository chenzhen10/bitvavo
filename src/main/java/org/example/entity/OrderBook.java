package org.example.entity;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class OrderBook {
    private final Queue<Order> bidOrders; // Max-heap based on price
    private final Queue<Order> askOrders; // Min-heap based on price
    private String tradeLog;

    public OrderBook() {
        this.bidOrders = new PriorityQueue<>((o1, o2) -> Integer.compare(o2.getPrice(), o1.getPrice())); // Descending order
        this.askOrders = new PriorityQueue<>(Comparator.comparingInt(Order::getPrice)); // Ascending order
    }

    public Queue<Order> getBidOrders() {
        return bidOrders;
    }

    public Queue<Order> getAskOrders() {
        return askOrders;
    }

    public void addBidOrder(String orderId, int price, int quantity) {
        Order order = new Order(orderId, price, quantity);
        bidOrders.add(order);
    }

    public void addAskOrder(String orderId, int price, int quantity) {
        Order order = new Order(orderId, price, quantity);
        askOrders.add(order);
    }

    public String getTradeLog() {
        return tradeLog;
    }

    public void matchOrders() {
        StringBuilder stringBuilder = new StringBuilder("\n");
        while (!bidOrders.isEmpty() && !askOrders.isEmpty()) {
            Order topBid = bidOrders.peek();
            Order topAsk = askOrders.peek();

            if (topBid.getPrice() >= topAsk.getPrice()) {
                int matchedQuantity = Math.min(topBid.getQuantity(), topAsk.getQuantity());

                writeTradeLog(stringBuilder, topBid, topAsk, matchedQuantity);
                topBid.setQuantity(topBid.getQuantity() - matchedQuantity);
                topAsk.setQuantity(topAsk.getQuantity() - matchedQuantity);

                if (topBid.getQuantity() == 0) {
                    bidOrders.poll(); // Remove the order if quantity is zero
                }

                if (topAsk.getQuantity() == 0) {
                    askOrders.poll(); // Remove the order if quantity is zero
                }
            } else {
                break;
            }
        }
        tradeLog = stringBuilder.toString();
    }

    private void writeTradeLog(StringBuilder stringBuilder, Order topBid, Order topAsk, int matchedQuantity) {
        stringBuilder
                .append("trade ")
                .append(topBid.getOrderId())
                .append(" ")
                .append(topAsk.getOrderId())
                .append(" ")
                .append(topAsk.getPrice())
                .append(" ")
                .append(matchedQuantity)
                .append("\n");
    }
}


