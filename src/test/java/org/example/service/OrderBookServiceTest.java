package org.example.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderBookServiceTest {
    private OrderBookService orderBookService;

    @BeforeEach
    void setUp() {
        orderBookService = new OrderBookServiceImpl();
    }

    @Test
    void testGetOrderBook() {
        assertNotNull(orderBookService.getOrderBook());
    }
}