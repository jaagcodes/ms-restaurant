package com.plazoleta.msrestaurant;

import com.plazoleta.msrestaurant.infrastructure.security.config.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = "com.plazoleta")
@SpringBootApplication
@EnableConfigurationProperties(JwtProperties.class)
public class MsRestaurantApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsRestaurantApplication.class, args);
	}

}
