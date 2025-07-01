package com.plazoleta.msrestaurant.infrastructure.output.jpa.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "orders_dishes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDishEntity {

    @EmbeddedId
    private OrderDishId id = new OrderDishId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("orderId")
    private OrderEntity order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_dish", insertable = false, updatable = false)
    private DishEntity dish;

    @Column(nullable = false)
    private Integer quantity;
}
