package com.plazoleta.msrestaurant.infrastructure.output.feign.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "userClient", url = "${users.service.url}")
public interface UserClient {
    @GetMapping("/users/{id}/validate-owner")
    Boolean isUserOwner(@PathVariable("id") Long id);
}
