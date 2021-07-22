package org.amdocs.elearning.order.service.order;


import org.amdocs.elearning.order.service.authorization.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrdersController {
    private final OrderService orderService;
    private final UserAuthService userAuthService;
    private static final String orderStatus = "PENDING";

    @Autowired
    public OrdersController(final OrderService orderService, final UserAuthService userAuthService){
        this.orderService = orderService;
        this.userAuthService = userAuthService;
    }

    @RequestMapping(path = {"/", ""}, method = RequestMethod.GET)
    public ResponseEntity<List<Order>> getAllOrders(@RequestHeader(value="userID") String userID) {
        Optional validatedUser = userAuthService.validateUserByID(userID);

        if(validatedUser.isPresent()) {
            return new ResponseEntity<List<Order>>(orderService.getAllOrders(), HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    @RequestMapping(method = RequestMethod.POST, headers="Accept=application/json")
    public ResponseEntity<Order> placeOrder(@RequestHeader(value="userID") String userID,
                                            @Valid @RequestBody OrderDetails orderDetails) {
        final UUID orderID = UUID.randomUUID();
        final Order order = new Order(orderID, LocalDateTime.now(), orderStatus, orderDetails);

        Optional validatedUser = userAuthService.validateUserByID(userID);

        if (validatedUser.isPresent()) {
            final Order placeOrder = this.orderService.placeOrder(order);

            return new ResponseEntity(placeOrder, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Order> getOrderByID(@RequestHeader(value="userID") String userID, @PathVariable final String id) {

        Optional validatedUser = userAuthService.validateUserByID(userID);

        if (validatedUser.isPresent()) {
            final Optional<Order> orderOptional = this.orderService.getOrderById(id);

            return orderOptional.isPresent() ?
                    new ResponseEntity<Order>(orderOptional.get(), HttpStatus.OK) :
                    new ResponseEntity<Order>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

}
