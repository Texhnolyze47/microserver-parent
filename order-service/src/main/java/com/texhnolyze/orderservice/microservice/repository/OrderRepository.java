package com.texhnolyze.orderservice.microservice.repository;

import com.texhnolyze.orderservice.microservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
