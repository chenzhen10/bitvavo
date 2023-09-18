package org.example.service;

import org.example.entity.OrderBook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderBookFormatterTest {

    private Formatter formatter;
    private OrderProcessor orderProcessor;

    @BeforeEach
    void setUp() {
        formatter = new OrderBookFormatter();
        orderProcessor = new ConsoleOrderProcessor();
    }

    @Test
    void testFormatOrderBook_trade() {
        String expectedResult = """
                trade 10006 10001 100 500
                trade 10006 10002 100 10000
                trade 10006 10004 103 100
                trade 10006 10005 105 5400
                     50,000     99 |    105      14,600
                     25,500     98 |\s""".trim();
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
        String formattedResult = formatter.formatOrderBook(orderBook);
        assertEquals(expectedResult, formattedResult.trim());
    }

    @Test
    void testFormatOrderBook_noTrade() {
        String expectedResult = """
                50,000     99 |    100         500
                     25,500     98 |    100      10,000
                                   |    103         100
                                   |    105      20,000
                      """.trim();
        String[] orders = new String[]{
                "10000,B,98,25500",
                "10005,S,105,20000",
                "10001,S,100,500",
                "10002,S,100,10000",
                "10003,B,99,50000",
                "10004,S,103,100",
        };
        OrderBook orderBook = orderProcessor.processOrders(orders);
        String formattedResult = formatter.formatOrderBook(orderBook);
        assertEquals(expectedResult, formattedResult.trim());
    }
}