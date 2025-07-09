package com.plazoleta.msrestaurant.infrastructure.output.jpa.entity;

import com.plazoleta.msrestaurant.domain.model.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_client", nullable = false)
    private Long clientId;

    @Column(name = "id_restaurant", nullable = false)
    private Long restaurantId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @Column(name = "id_chef")
    private Long chefId;

    private LocalDateTime date;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderDishEntity> dishes;

    @Override
    public String toString() {
        return "OrderEntity{" +
                "id=" + id +
                ", clientId=" + clientId +
                ", restaurantId=" + restaurantId +
                ", status=" + status +
                ", chefId=" + chefId +
                ", date=" + date +
                ", dishes=" + dishes +
                '}';
    }
}
