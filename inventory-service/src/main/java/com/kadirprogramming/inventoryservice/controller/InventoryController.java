package com.kadirprogramming.inventoryservice.controller;
import com.kadirprogramming.inventoryservice.dto.InventoryResponse;
import com.kadirprogramming.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {
    @Autowired
    InventoryService inventoryService;
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
//    public List<InventoryResponse> isInStock(@RequestParam List<String> skuCode){
//        return inventoryService.isInStock(skuCode);

    public List<InventoryResponse> isInStock(@RequestParam List<String> skuCode) {
        return inventoryService.isInStock(skuCode);
    }
    }

