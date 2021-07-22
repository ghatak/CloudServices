package org.amdocs.elearning.order.service.authorization;

import org.amdocs.elearning.order.service.user.User;
import org.amdocs.elearning.order.service.user.UserType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

public class UserAuthService {
    @Value("${service.user.hostname}")
    private String userServiceHost;

    @Value("${service.user.port}")
    private String userServicePort;

    private final RestTemplate restTemplate;

    public UserAuthService() {
        this.restTemplate = new RestTemplate();
    }

    public UserAuthService(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Optional<User> validateUserByID(String userID) {
        String USER_SERVICE_URL= userServiceHost + ":" + userServicePort + "/users/" + userID;

        try{
            User user = this.restTemplate.getForObject(USER_SERVICE_URL, User.class);

            return user.getUserType().equals(UserType.PATRON) ? Optional.of(user) : Optional.empty();

        } catch(HttpStatusCodeException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}

