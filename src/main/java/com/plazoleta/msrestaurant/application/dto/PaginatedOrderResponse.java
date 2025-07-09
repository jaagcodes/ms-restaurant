package com.plazoleta.msrestaurant.application.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PaginatedOrderResponse {

    private List<OrderResponse> orders;
    private int currentPage;
    private int totalPages;
    private long totalElements;
}
