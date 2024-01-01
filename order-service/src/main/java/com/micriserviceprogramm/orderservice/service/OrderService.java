package com.micriserviceprogramm.orderservice.service;
import com.micriserviceprogramm.orderservice.config.WebClientConfig;
import com.micriserviceprogramm.orderservice.dto.InventoryResponse;
import com.micriserviceprogramm.orderservice.dto.OrderLineItemsDto;
import com.micriserviceprogramm.orderservice.dto.OrderRequest;
import com.micriserviceprogramm.orderservice.model.Order;
import com.micriserviceprogramm.orderservice.model.OrderLineItems;
import com.micriserviceprogramm.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.UUID;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    private final WebClient webClient;
    @Autowired
    OrderRepository orderRepository;
    public void placeOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
       List<OrderLineItems> orderLineItems= orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(this::mapToDo)
                .toList();
       order.setOrderLineItemsList(orderLineItems);
     List<String> skuCodes =  order.getOrderLineItemsList().stream()
             .map(OrderLineItems::getSkuCode)
               .toList();

        // call Inventory Service, and place order if product is in stock.
        InventoryResponse[] inventoryResponseArray = webClient.get()
               .uri("http://localhost:8082/api/inventory",
                uriBuilder -> uriBuilder.queryParam("skuCode",skuCodes)
               .build())
               .retrieve()
               .bodyToMono(InventoryResponse[].class)
               .block();
       // assert inventoryResponseArray != null;
        boolean allProductsInStock = Arrays.stream(inventoryResponseArray)
               .allMatch(InventoryResponse::isInStock);
      if (allProductsInStock){
          orderRepository.save(order);
      }else {
          throw new IllegalArgumentException("Product is not in stock, please try again later");
      }

    }

    private OrderLineItems mapToDo(OrderLineItemsDto orderLineItemsDto){
        OrderLineItems orderLineItems= new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;

    }
}
