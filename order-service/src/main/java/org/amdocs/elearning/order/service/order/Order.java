package org.amdocs.elearning.order.service.order;

import java.time.LocalDateTime;
import java.util.UUID;

public class Order extends OrderDetails{
    private UUID id;
    private LocalDateTime orderDate;
    private String orderStatus;

    public Order() {

    }

    public Order(UUID id, LocalDateTime date, String status, OrderDetails order) {
        this.id = id;
        this.orderDate = date;
        this.orderStatus = status;
        this.eventName = order.getEventName();
        this.venue = order.getVenue();
        this.eventDate = order.getEventDate();
        this.numberOfTickets = order.getNumberOfTickets();
    }

    public Order(UUID id, LocalDateTime date, String status, String eventName, LocalDateTime eventDate, String venue, int numberOfTickets) {
        this.id = id;
        this.orderDate = date;
        this.orderStatus = status;
        this.eventName = eventName;
        this.venue = venue;
        this.eventDate = eventDate;
        this.numberOfTickets = numberOfTickets;
    }

    public UUID getOrderId() {
        return id;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public String getOrderStatus() {
        return orderStatus;
    }
}
