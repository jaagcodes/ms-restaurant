package com.plazoleta.msrestaurant.infrastructure.output.feign.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "userClient", url = "${users.service.url}")
public interface UserClient {
    @GetMapping("/users/{id}/validate-owner")
    Boolean isUserOwner(@PathVariable("id") Long id);

    @GetMapping("/users/validate-employee")
    Boolean isEmployeeOfRestaurant(@RequestParam Long employeeId, @RequestParam Long restaurantId);
}
