package org.example.service;

import org.example.exception.ImplementationNotFoundException;
import org.example.service.factory.ObjectFactory;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ObjectFactoryTest {

    private static final ObjectFactory orderProcessorFactory = ObjectFactory.INSTANCE;

    @Test
    void testCreateObject_orderProcessor() {
        Optional<OrderProcessor> orderProcessor = orderProcessorFactory.createObject(OrderProcessor.class);
        assertTrue(orderProcessor.isPresent());
        assertEquals(ConsoleOrderProcessor.class, orderProcessor.get().getClass());
    }

    @Test
    void testCreateObject_formatter() {
        Optional<Formatter> formatter = orderProcessorFactory.createObject(Formatter.class);
        assertTrue(formatter.isPresent());
        assertEquals(OrderBookFormatter.class, formatter.get().getClass());
    }

    @Test
    void testCreateObject_implementationNotFound() {
        assertThrows(ImplementationNotFoundException.class, () -> orderProcessorFactory.createObject(TestInterface.class));
    }
}