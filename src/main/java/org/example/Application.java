package org.example;

import org.example.service.Formatter;
import org.example.service.factory.ObjectFactory;
import org.example.service.OrderProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class Application {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        ObjectFactory objectFactory = ObjectFactory.INSTANCE;
        Optional<OrderProcessor> orderProcessor = objectFactory.createObject(OrderProcessor.class);
        Optional<Formatter> formatter = objectFactory.createObject(Formatter.class);
        orderProcessor
                .map(processor -> processor.processOrders(args))
                .ifPresent(orderBook -> formatter.ifPresent(fmt -> logger.info(fmt.formatOrderBook(orderBook))));
    }
}