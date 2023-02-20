package com.texhnolyze.orderservice.microservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderListItemsDto {
    private long id;

    private String skuCode;
    private BigDecimal price;
    private Integer quantity;
}
