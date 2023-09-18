package org.example.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class OrderBookTest {

    private OrderBook orderBook;

    @BeforeEach
    public void setUp() {
        orderBook = new OrderBook();
    }

    @Test
    void testAddBidOrder() {
        orderBook.addBidOrder("1", 150, 10);
        assertEquals(1, orderBook.getBidOrders().size());
    }

    @Test
    void testAddAskOrder() {
        orderBook.addAskOrder("1", 151, 15);
        assertEquals(1, orderBook.getAskOrders().size());
    }

    @Test
    void testMatchOrders_BidHigherPrice() {
        orderBook.addBidOrder("1", 151, 10);
        orderBook.addAskOrder("2", 150, 5);
        orderBook.matchOrders();

        assertEquals(1, orderBook.getBidOrders().size());
        assertEquals(0, orderBook.getAskOrders().size());

        Order remainingBid = orderBook.getBidOrders().poll();
        Order remainingAsk = orderBook.getAskOrders().poll();

        assertEquals("1", remainingBid.getOrderId());
        assertEquals(5, remainingBid.getQuantity());
        assertNull(remainingAsk);
    }

    @Test
    void testMatchOrders_AskHigherPrice() {
        orderBook.addBidOrder("1", 150, 10);
        orderBook.addAskOrder("2", 151, 5);
        orderBook.matchOrders();

        assertEquals(1, orderBook.getBidOrders().size());
        assertEquals(1, orderBook.getAskOrders().size());

        Order remainingBid = orderBook.getBidOrders().poll();
        Order remainingAsk = orderBook.getAskOrders().poll();

        assertEquals("1", remainingBid.getOrderId());
        assertEquals("2", remainingAsk.getOrderId());
        assertEquals(10, remainingBid.getQuantity());
        assertEquals(5, remainingAsk.getQuantity());
    }

    @Test
    void testMatchOrders_EqualPrice() {
        orderBook.addBidOrder("1", 150, 10);
        orderBook.addAskOrder("2", 150, 5);
        orderBook.matchOrders();

        assertEquals(1, orderBook.getBidOrders().size());
        assertEquals(0, orderBook.getAskOrders().size());

        Order remainingBid = orderBook.getBidOrders().poll();
        Order remainingAsk = orderBook.getAskOrders().poll();

        assertEquals("1", remainingBid.getOrderId());
        assertEquals(5, remainingBid.getQuantity());
        assertNull(remainingAsk);
    }
}


