package com.saga.order_service.service;

import jakarta.annotation.PostConstruct;
import org.resilience.dto.DiscountResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DiscountServiceImpl implements DiscountService{

    private List<DiscountResponse> discountResponseList;

    @PostConstruct
    public void init(){
        discountResponseList= new ArrayList<>();
        discountResponseList.add(new DiscountResponse("mobile", 1000.0));
        discountResponseList.add(new DiscountResponse("cloth", 600));
        discountResponseList.add(new DiscountResponse("laptop", 750));
        discountResponseList.add(new DiscountResponse("television", 1250));
    }

    @Override
    public DiscountResponse getDiscount(String category) {
        if(category!=null && !category.isEmpty()){
            System.out.println("category\t"+category);
            return discountResponseList.stream()
                    .filter(discount->category.equalsIgnoreCase(discount.getCategory()))
                    .findFirst()
                    .orElse(new DiscountResponse("", 0.0));
        }
        return new DiscountResponse("", 0.0);
    }
}
