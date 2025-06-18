package com.plazoleta.msrestaurant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = "com.plazoleta")
@SpringBootApplication
public class MsRestaurantApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsRestaurantApplication.class, args);
	}

}
