package org.amdocs.elearning.order.service;

import org.amdocs.elearning.order.service.authorization.UserAuthService;
import org.amdocs.elearning.order.service.order.OrderService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public OrderService getOrderService() {
        return new OrderService();
    }

    @Bean
    public UserAuthService getUserAuthService() {
        return new UserAuthService();
    }
}