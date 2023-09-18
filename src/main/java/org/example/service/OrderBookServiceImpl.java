package org.example.service;

import org.example.entity.OrderBook;

public class OrderBookServiceImpl implements OrderBookService {

    private final OrderBook orderBook;

    public OrderBookServiceImpl() {
        this.orderBook = new OrderBook();
    }

    @Override
    public OrderBook getOrderBook() {
        return orderBook;
    }
}
