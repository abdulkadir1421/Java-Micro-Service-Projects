package com.micriserviceprogramm.orderservice.service;
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

import java.util.UUID;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
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
       orderRepository.save(order);
    }

    private OrderLineItems mapToDo(OrderLineItemsDto orderLineItemsDto){
        OrderLineItems orderLineItems= new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;

    }
}
