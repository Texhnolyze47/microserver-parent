package com.texhnolyze.orderservice.microservice.service;

import com.texhnolyze.orderservice.microservice.dto.InventoryResponse;
import com.texhnolyze.orderservice.microservice.dto.OrderListItemsDto;
import com.texhnolyze.orderservice.microservice.dto.OrderRequest;
import com.texhnolyze.orderservice.microservice.model.Order;
import com.texhnolyze.orderservice.microservice.model.OrderListItems;
import com.texhnolyze.orderservice.microservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;
    public void placeOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List <OrderListItems> orderListItems = orderRequest.getOrderListItemsDtoList()
                .stream()
                .map(this::mapToDto)
                .toList();
        order.setOrderListItemsList(orderListItems);

        List <String> skuCodes = order.getOrderListItemsList().stream()
                .map(OrderListItems::getSkuCode)
                .toList();

        //Llama al Inventory Service, y hace una orden sí el
        // producto esta en stock
        InventoryResponse[] inventoryResponseArray = webClientBuilder.build().get()
                .uri("http://inventory-service/api/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();

        boolean allProductsInStock = Arrays.stream(inventoryResponseArray)
                .allMatch(InventoryResponse::isInStock);

        if (allProductsInStock){
            orderRepository.save(order);
        }else {
            throw new IllegalArgumentException("Product is not in stock, please try again later");
        }
    }

    private OrderListItems mapToDto(OrderListItemsDto orderListItemsDto) {
        OrderListItems orderListItems = new OrderListItems();
        orderListItems.setPrice(orderListItemsDto.getPrice());
        orderListItems.setQuantity(orderListItemsDto.getQuantity());
        orderListItems.setSkuCode(orderListItemsDto.getSkuCode());
        return orderListItems;
    }
}
