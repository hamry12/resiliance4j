package com.saga.order_service.service;

import org.resilience.dto.DiscountResponse;

import java.util.List;

public interface DiscountService {
    DiscountResponse getDiscount(String category);
}
