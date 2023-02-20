package com.texhnolyze.orderservice.microservice.service;

import com.texhnolyze.orderservice.microservice.dto.OrderListItemsDto;
import com.texhnolyze.orderservice.microservice.dto.OrderRequest;
import com.texhnolyze.orderservice.microservice.model.Order;
import com.texhnolyze.orderservice.microservice.model.OrderListItems;
import com.texhnolyze.orderservice.microservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    public void placeOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List <OrderListItems> orderListItems = orderRequest.getOrderListItemsDtoList()
                .stream()
                .map(this::mapToDto)
                .toList();
        order.setOrderListItemsList(orderListItems);

        orderRepository.save(order);
    }

    private OrderListItems mapToDto(OrderListItemsDto orderListItemsDto) {
        OrderListItems orderListItems = new OrderListItems();
        orderListItems.setPrice(orderListItemsDto.getPrice());
        orderListItems.setQuantity(orderListItemsDto.getQuantity());
        orderListItems.setSkuCode(orderListItemsDto.getSkuCode());
        return orderListItems;
    }
}
