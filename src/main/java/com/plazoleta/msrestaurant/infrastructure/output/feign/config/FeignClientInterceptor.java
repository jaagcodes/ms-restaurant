package com.plazoleta.msrestaurant.infrastructure.output.feign.config;

import com.plazoleta.msrestaurant.infrastructure.security.util.AuthTokenHolder;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientInterceptor {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return (RequestTemplate template) -> {
            String token = AuthTokenHolder.getToken();
            if (token != null && !token.isEmpty()) {
                template.header("Authorization", "Bearer " + token);
            }
        };
    }
}
