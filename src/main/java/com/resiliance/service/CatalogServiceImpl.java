package com.resiliance.service;


import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.annotation.PostConstruct;
import org.resilience.dto.CatalogResponse;
import org.resilience.dto.DiscountResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class CatalogServiceImpl implements CatalogService{

    @Autowired
    private RestTemplate restTemplate;


    private static final String BASE_URL="http://localhost:8080/discount";
    private static final String SERVICE_NAME="catalogServiceImpl";

    private List<CatalogResponse> catalogResponseList;

    @PostConstruct
    public void init(){
        catalogResponseList= new ArrayList<>();
        catalogResponseList.add(new CatalogResponse("P001", "iPhone 15", "mobile", 59000.0));
        catalogResponseList.add(new CatalogResponse("P002", "t-shirt", "cloth", 1500.0));
        catalogResponseList.add(new CatalogResponse("P003", "xps", "laptop", 132999.0));
        catalogResponseList.add(new CatalogResponse("P004", "bravia", "television", 95700.0));
    }

    @Override
    @CircuitBreaker(name = SERVICE_NAME, fallbackMethod = "fallBackDiscountService")
    public List<CatalogResponse> getCatalog(String category) {
        double amount;
        if(category != null && !category.isEmpty()){
            String finalURL = BASE_URL + "?category=" + category;
            ResponseEntity<?> discountResponse = restTemplate.getForEntity(finalURL, DiscountResponse.class);
        }
        return catalogResponseList;
    }


    private DiscountResponse fallBackDiscountService(Throwable throwable){
        System.out.println("Exception for fallback");
        return new DiscountResponse("", 100.0);
    }
}
