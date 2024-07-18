package com.saga.order_service.controller;

import com.saga.order_service.service.DiscountService;
import org.resilience.dto.DiscountResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ApplicationController {

    @Autowired
    private DiscountService discountService;

    @GetMapping("/discount")
    public DiscountResponse createOrder(@RequestParam("category") String category){
        return discountService.getDiscount(category);
    }

}
