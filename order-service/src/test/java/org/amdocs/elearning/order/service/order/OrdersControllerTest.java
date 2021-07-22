package org.amdocs.elearning.order.service.order;

import org.amdocs.elearning.order.service.authorization.UserAuthService;
import org.amdocs.elearning.order.service.user.User;
import org.amdocs.elearning.order.service.user.UserType;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class OrdersControllerTest {
	private static final String MOCK_STATUS = "PENDING";
	
    private OrderService  orderService = Mockito.mock(OrderService.class);
    private UserAuthService userAuthService = Mockito.mock(UserAuthService.class);
    private OrdersController controller = new OrdersController(orderService, userAuthService);
    
    private final OrderDetails mockOrder1 = new OrderDetails("Event1", LocalDateTime.now(), "Venue 1", 2);
    private final OrderDetails mockOrder2 = new OrderDetails("Event2", LocalDateTime.now(), "Venue 5", 1);
    private final UUID sampleUUID1 = UUID.randomUUID();
    private final UUID sampleUUID2 = UUID.randomUUID();

    @Test
    public void getAllOrders_EmptyOrders_OK_Response() {
        final User mockUser = new User("0", "Jon", "Doe", "C", UserType.PATRON,  LocalDate.now());
        final Optional<User> mockResponse = Optional.of(mockUser);

        Mockito.when(orderService.getAllOrders()).thenReturn(new ArrayList<>());
        Mockito.when(userAuthService.validateUserByID(Mockito.anyString())).thenReturn(mockResponse);

        final ResponseEntity<List<Order>> responseEntity = this.controller.getAllOrders("0");

        Assert.assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        Assert.assertNotNull(responseEntity.getBody());
        Assert.assertTrue(responseEntity.getBody().isEmpty());
    }

    @Test
    public void getAllOrders_EmptyOrders_UNAUTHORIZED_Response() {
        Mockito.when(userAuthService.validateUserByID(Mockito.anyString())).thenReturn(Optional.empty());

        final ResponseEntity<List<Order>> responseEntity = this.controller.getAllOrders("0");

        Assert.assertEquals(HttpStatus.UNAUTHORIZED.value(), responseEntity.getStatusCodeValue());
        Assert.assertNull(responseEntity.getBody());
    }

    @Test
    public void getAllOrders_WithOrders_OK_Response() {
        final List<Order> mockOrders = new ArrayList<>();
        final User mockUser = new User("0", "Jon", "Doe", "C", UserType.PATRON,  LocalDate.now());
        final Optional<User> mockResponse = Optional.of(mockUser);

        final Order order1 = new Order(sampleUUID1, LocalDateTime.now(), MOCK_STATUS, mockOrder1);
        final Order order2 = new Order(sampleUUID2, LocalDateTime.now(), MOCK_STATUS, mockOrder2);
        mockOrders.add(order1);
        mockOrders.add(order2);

        Mockito.when(orderService.getAllOrders()).thenReturn(mockOrders);
        Mockito.when(userAuthService.validateUserByID(Mockito.anyString())).thenReturn(mockResponse);

        final ResponseEntity<List<Order>> responseEntity = this.controller.getAllOrders("1");

        Assert.assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        Assert.assertNotNull(responseEntity.getBody());
        Assert.assertEquals(responseEntity.getBody(), this.orderService.getAllOrders());
        Assert.assertEquals(responseEntity.getBody(), mockOrders);
    }

    @Test
    public void getAllOrders_WithOrders_UNAUTHORIZED_Response() {
        Mockito.when(userAuthService.validateUserByID(Mockito.anyString())).thenReturn(Optional.empty());

        final ResponseEntity<List<Order>> responseEntity = this.controller.getAllOrders("1");

        Assert.assertEquals(HttpStatus.UNAUTHORIZED.value(), responseEntity.getStatusCodeValue());
        Assert.assertNull(responseEntity.getBody());
    }

    @Test
    public void placeOrder_withHeaders_OK_Response() {
        final Order mockOrder = new Order(sampleUUID1, LocalDateTime.now(), MOCK_STATUS, mockOrder1);
        final User mockUser = new User("0", "Jon", "Doe", "C", UserType.PATRON,  LocalDate.now());
        final Optional<User> mockResponse = Optional.of(mockUser);

        Mockito.when(userAuthService.validateUserByID(Mockito.anyString())).thenReturn(mockResponse);
        Mockito.when(orderService.placeOrder(Mockito.any())).thenReturn(mockOrder);

        final ResponseEntity<Order> response = controller.placeOrder("0", mockOrder1);

        Assert.assertEquals(response.getBody(), mockOrder);
        Assert.assertEquals(response.getStatusCode(), HttpStatus.CREATED);
    }

    @Test
    public void placeOrder_withHeaders_UNAUTHORIZED_Response() {
        Mockito.when(userAuthService.validateUserByID(Mockito.anyString())).thenReturn(Optional.empty());

        final ResponseEntity<Order> response = controller.placeOrder("0",mockOrder1);

        Assert.assertNull(response.getBody());
        Assert.assertEquals(response.getStatusCode(), HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void getOrderByID_withExistingOrder_OK_Response() {
        final Order mockOrder = new Order(sampleUUID1, LocalDateTime.now(), MOCK_STATUS, mockOrder1);
        final User mockUser = new User("0", "Jon", "Doe", "C", UserType.PATRON,  LocalDate.now());
        final Optional<User> mockResponse = Optional.of(mockUser);

        Mockito.when(orderService.getOrderById(Mockito.anyString())).thenReturn(Optional.of(mockOrder));
        Mockito.when(userAuthService.validateUserByID(Mockito.anyString())).thenReturn(mockResponse);

        ResponseEntity<Order> response = controller.getOrderByID("1", "1");

        Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assert.assertEquals(response.getBody(), mockOrder);
    }

    @Test
    public void getOrderByID_withExistingOrder_UNAUTHORIZED_Response() {
        Mockito.when(userAuthService.validateUserByID(Mockito.anyString())).thenReturn(Optional.empty());

        final ResponseEntity<Order> response = controller.getOrderByID("1", "1");

        Assert.assertEquals(response.getStatusCode(), HttpStatus.UNAUTHORIZED);
        Assert.assertNull(response.getBody());
    }

    @Test
    public void getOrderByID_withNONExistingOrder_OK_Response() {
        final User mockUser = new User("0", "Jon", "Doe", "C", UserType.PATRON,  LocalDate.now());

        Mockito.when(orderService.getOrderById(Mockito.anyString())).thenReturn(Optional.empty());
        Mockito.when(userAuthService.validateUserByID(Mockito.anyString())).thenReturn(Optional.of(mockUser));

        final ResponseEntity<Order> response = controller.getOrderByID("1", "1");

        Assert.assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
        Assert.assertNull(response.getBody());
    }
}