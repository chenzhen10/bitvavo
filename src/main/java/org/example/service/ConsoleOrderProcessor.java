package org.example.service;

import org.example.constant.ActionType;
import org.example.entity.OrderBook;
import org.example.exception.InvalidDataException;

import java.util.Objects;

public class ConsoleOrderProcessor implements OrderProcessor {

    private final OrderBookService orderBookService;

    public ConsoleOrderProcessor() {
        this.orderBookService = new OrderBookServiceImpl();
    }

    @Override
    public OrderBook processOrders(String[] data) {
        if (Objects.isNull(data)) {
            throw new InvalidDataException();
        }
        OrderBook orderBook = orderBookService.getOrderBook();
        for (String line : data) {
            String[] parts = line.split(",");
            if (parts.length == 4) {
                enrichOrderBook(parts, orderBook);
            }
        }
        orderBook.matchOrders();
        return orderBook;
    }

    private void enrichOrderBook(String[] parts, OrderBook orderBook) {
        String orderId = parts[0];
        String action = parts[1];
        int price = Integer.parseInt(parts[2]);
        int quantity = Integer.parseInt(parts[3]);

        if (price > 0 && quantity > 0) {
            if (action.equals(ActionType.BID.getShortName())) {
                orderBook.addBidOrder(orderId, price, quantity);
            } else if (action.equals(ActionType.ASK.getShortName())) {
                orderBook.addAskOrder(orderId, price, quantity);
            }
        }
    }
}
