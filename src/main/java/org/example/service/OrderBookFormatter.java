package org.example.service;

import org.example.entity.Order;
import org.example.entity.OrderBook;

import java.text.DecimalFormat;
import java.util.Queue;

public class OrderBookFormatter implements Formatter {

    private final DecimalFormat quantityFormat;

    public OrderBookFormatter() {
        this.quantityFormat = new DecimalFormat();
    }

    @Override
    public String formatOrderBook(OrderBook orderBook) {
        Queue<Order> bidOrders = orderBook.getBidOrders();
        Queue<Order> askOrders = orderBook.getAskOrders();

        StringBuilder formattedOrderBook = new StringBuilder();

        int max = Math.max(bidOrders.size(), askOrders.size());
        for (int i = 0; i < max; i++) {
            String bidLine = !bidOrders.isEmpty() ? formatOrderLine(bidOrders.poll(), true) : defaultLinePadding();
            String askLine = !askOrders.isEmpty() ? formatOrderLine(askOrders.poll(), false) : defaultLinePadding();

            formattedOrderBook
                    .append("\n")
                    .append(bidLine)
                    .append(" | ")
                    .append(askLine);
        }
        return formattedOrderBook
                .insert(0, orderBook.getTradeLog().stripTrailing())
                .toString();
    }

    private String defaultLinePadding() {
        return " ".repeat(18);
    }

    private String formatOrderLine(Order order, boolean isBidFormat) {
        int maxLengthOfPrice = 6;
        int maxLengthOfQuantity = 11;
        String priceFormatted = String.valueOf(order.getPrice());
        String quantityFormatted = quantityFormat.format(order.getQuantity());

        priceFormatted = leftPad(priceFormatted, maxLengthOfPrice);
        quantityFormatted = leftPad(quantityFormatted, maxLengthOfQuantity);
        if (isBidFormat) {
            return quantityFormatted + " " + priceFormatted;
        }
        return priceFormatted + " " + quantityFormatted;
    }

    private String leftPad(String input, int width) {
        int padding = width - input.length();
        return " ".repeat(Math.max(0, padding)) + input;
    }
}
