package org.amdocs.elearning.order.service.order;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Optional;

public class OrderServiceTest {
    private OrderDetails mockOrderDetails = new OrderDetails("eventTitle", LocalDateTime.now(), "venue1", 2);
    private OrderService orderService = new OrderService();

    @Test
    public void placeOrder() {
        final Order order = orderService.placeOrder(mockOrderDetails);

        Assert.assertNotNull(order);
        Assert.assertEquals(order.getEventName(), "eventTitle");
        Assert.assertNotNull(order.getEventDate());
        Assert.assertNotNull(order.getOrderDate());
        Assert.assertNotNull(order.getOrderId());
        Assert.assertEquals(order.getVenue(), "venue1");
        Assert.assertEquals(order.getNumberOfTickets(), 2);
        Assert.assertEquals(order.getOrderStatus(), "PENDING");
    }

    @Test
    public void cancelOrder() {
        final OrderDetails mockOrderDetails2 = new OrderDetails("event2", LocalDateTime.now(), "venue5", 1);

        final Order order1 = orderService.placeOrder(mockOrderDetails);
        final Order order2 = orderService.placeOrder(mockOrderDetails2);

        Assert.assertEquals(orderService.getAllOrders().size(), 2);
        Assert.assertTrue(orderService.getAllOrders().contains(order1));
        Assert.assertTrue(orderService.getAllOrders().contains(order2));
    }

    @Test
    public void getAllOrders() {
        final OrderDetails mockOrderDetails2 = new OrderDetails("event2", LocalDateTime.now(), "venue5", 1);

        Assert.assertEquals(orderService.getAllOrders().size(), 0);

        orderService.placeOrder(mockOrderDetails);
        orderService.placeOrder(mockOrderDetails2);

        Assert.assertEquals(orderService.getAllOrders().size(), 2);
    }

    @Test
    public void getOrderById_withCorrectID() {
        final Order mockOrder = orderService.placeOrder(mockOrderDetails);
        final String id = mockOrder.getOrderId().toString();

        Assert.assertEquals(orderService.getAllOrders().size(), 1);
        Assert.assertEquals(orderService.getOrderById(id), Optional.of(mockOrder));
    }

    @Test
    public void getOrderById_withIncorrectID() {
        Assert.assertEquals(orderService.getAllOrders().size(), 0);
        Assert.assertFalse(orderService.getAllOrders().contains(orderService.getOrderById("2")));
        Assert.assertEquals(orderService.getOrderById("2"), Optional.empty());
    }
}