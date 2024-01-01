package com.micriserviceprogramm.orderservice.controller;
import com.micriserviceprogramm.orderservice.dto.OrderRequest;
import com.micriserviceprogramm.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    OrderService orderService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createProduct(@RequestBody OrderRequest orderRequest){
        orderService.placeOrder(orderRequest);
        return "Order palces successfully";

    }
}
