package org.amdocs.elearning.order.service.authorization;

import org.amdocs.elearning.order.service.user.User;
import org.amdocs.elearning.order.service.user.UserType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDate;
import java.util.Optional;

public class UserAuthServiceTest {
    private User mockUser;
    private RestTemplate restTemplate;
    private UserAuthService service;

    @Before
    public void setUp() {
        restTemplate = Mockito.mock(RestTemplate.class);
        service = new UserAuthService(restTemplate);
    }

    @Test
    public void validateUserByID_OK_response_userType_PATRON() {
        mockUser = new User("0", "Jon", "Doe", "C", UserType.PATRON,  LocalDate.now());

        Mockito.when(restTemplate.getForObject(Mockito.anyString(), Mockito.any())).thenReturn(mockUser);

        Optional response = service.validateUserByID("0");

        Assert.assertTrue(response.isPresent());
    }

    @Test
    public void validateUserByID_OK_response_userType_VENUE_OWNER() {
        mockUser = new User("0", "Jon", "Doe", "C", UserType.VENUE_OWNER,  LocalDate.now());

        Mockito.when(restTemplate.getForObject(Mockito.anyString(), Mockito.any())).thenReturn(mockUser);

        Optional response = service.validateUserByID("0");

        Assert.assertFalse(response.isPresent());
    }

    @Test
    public void validateUserByID_user_service_fail() {
        Mockito.when(restTemplate.getForObject(Mockito.anyString(), Mockito.any())).thenThrow(new HttpStatusCodeException(HttpStatus.NOT_FOUND){ });

        Optional response = service.validateUserByID("1");

        Assert.assertFalse(response.isPresent());
    }
}