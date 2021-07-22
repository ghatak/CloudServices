package org.amdocs.elearning.order.service.order;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class OrderService {
    private final List<Order> orders = new ArrayList<>();
    private static final String status = "PENDING";

    public Order placeOrder(final OrderDetails orderDetails) {
        final UUID orderID = UUID.randomUUID();
        final LocalDateTime orderDate = LocalDateTime.now();

        final Order order = new Order(orderID, orderDate, status, orderDetails);
        orders.add(order);

        return order;
    }

    public List<Order> getAllOrders() {
        return orders;
    }

    public Optional<Order> getOrderById(final String orderID) {
        return orders.stream().filter(order -> order.getOrderId().toString().equals(orderID)).findFirst();
    }
}
