package com.texhnolyze.orderservice.microservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "t_order_list_item")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderListItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String skuCode;
    private BigDecimal price;
    private Integer quantity;
}
