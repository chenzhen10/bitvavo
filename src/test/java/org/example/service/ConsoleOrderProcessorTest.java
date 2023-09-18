package org.example.service;

import org.example.entity.Order;
import org.example.entity.OrderBook;
import org.example.exception.InvalidDataException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Queue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ConsoleOrderProcessorTest {

    private OrderProcessor orderProcessor;

    @BeforeEach
    void setUp() {
        orderProcessor = new ConsoleOrderProcessor();
    }

    @Test
    void testProcessOrders_validOrders() {
        String[] orders = new String[]{
                "10000,B,98,25500",
                "10005,S,105,20000",
                "10001,S,100,500",
                "10002,S,100,10000",
                "10003,B,99,50000",
                "10004,S,103,100",
                "10006,B,105,16000"
        };
        OrderBook orderBook = orderProcessor.processOrders(orders);

        Queue<Order> askOrders = orderBook.getAskOrders();
        Queue<Order> bidOrders = orderBook.getBidOrders();

        assertEquals(1, askOrders.size());
        assertEquals(2, bidOrders.size());

        Order ask = askOrders.poll();
        Order bid = bidOrders.poll();

        assertEquals("10005", ask.getOrderId());
        assertEquals("10003", bid.getOrderId());
    }

    @Test
    void testProcessOrders_null() {
        assertThrows(InvalidDataException.class, () -> orderProcessor.processOrders(null));
    }

    @Test
    void testProcessOrders_validData() {
        String[] data = {
                "1,B,100,10",
                "2,S,110,5"
        };

        OrderBook orderBook = orderProcessor.processOrders(data);

        Queue<Order> askOrders = orderBook.getAskOrders();
        Queue<Order> bidOrders = orderBook.getBidOrders();

        assertEquals(1, bidOrders.size());
        assertEquals(1, askOrders.size());

        Order ask = askOrders.poll();
        Order bid = bidOrders.poll();

        assertEquals(100, bid.getPrice());
        assertEquals(110, ask.getPrice());
    }

    @Test
    void testProcessOrders_invalidData() {
        String[] data = {
                "1,X,100,10",
                "2,B,-10,5",
                "3,S,110,-5"
        };

        OrderBook orderBook = orderProcessor.processOrders(data);

        assertEquals(0, orderBook.getBidOrders().size());
        assertEquals(0, orderBook.getAskOrders().size());
    }
}