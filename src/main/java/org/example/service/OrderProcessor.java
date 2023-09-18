package org.example.service;

import org.example.entity.OrderBook;

public interface OrderProcessor {

    OrderBook processOrders(String[] data);
}
