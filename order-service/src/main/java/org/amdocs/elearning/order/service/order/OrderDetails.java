package org.amdocs.elearning.order.service.order;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class OrderDetails {
    @NotNull
    protected String eventName;
    @NotNull
    protected String venue;
    @NotNull
    protected LocalDateTime eventDate;
    @NotNull
    protected int numberOfTickets;

    public OrderDetails() {
    }

    public OrderDetails(@NotNull String eventName, @NotNull LocalDateTime eventDate, @NotNull String venue, @NotNull int numberOfTickets) {
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.venue = venue;
        this.numberOfTickets = numberOfTickets;
    }

    public String getEventName() {
        return eventName;
    }

    public String getVenue() {
        return venue;
    }

    public LocalDateTime getEventDate() {
        return eventDate;
    }

    public int getNumberOfTickets() {
        return numberOfTickets;
    }
}
