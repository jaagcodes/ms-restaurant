package com.plazoleta.msrestaurant;

import com.plazoleta.msrestaurant.infrastructure.security.config.JwtProperties;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;

@OpenAPIDefinition(
		info = @Info(
				title = "API Restaurante Plazoleta",
				version = "1.0",
				description = "Documentación de la API para la gestión de pedidos"
		)
)
@EnableFeignClients(basePackages = "com.plazoleta")
@SpringBootApplication
@EnableConfigurationProperties(JwtProperties.class)
public class MsRestaurantApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsRestaurantApplication.class, args);
	}

}
