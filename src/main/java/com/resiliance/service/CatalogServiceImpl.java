package com.resiliance.service;


import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.annotation.PostConstruct;
import org.resilience.dto.CatalogResponse;
import org.resilience.dto.DiscountResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class CatalogServiceImpl implements CatalogService{

    @Autowired
    private RestTemplate restTemplate;

    private static final String BASE_URL="http://localhost:8080/discount";
    private static final String SERVICE_NAME="catalogService";

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
    public List<CatalogResponse> getCatalog(String category) {
        double amount;
        if(category!=null && !category.isEmpty()){
            String finalURL=BASE_URL+"?category="+category;
            DiscountResponse discountResponse=callDiscountService(finalURL);
            if(discountResponse!=null){
               amount = discountResponse.getAmount();
            } else {
                amount = 0.0;
            }
            return catalogResponseList.stream()
                    .filter(item->item.getCategory().equalsIgnoreCase(category))
                    .peek(product-> product.setPrice(product.getPrice()-amount))
                    .toList();

        }
        return catalogResponseList;
    }

    @CircuitBreaker(name = "discountService", fallbackMethod = "fallBackDiscountService")
    private DiscountResponse callDiscountService(String finalURL){
        return restTemplate.getForEntity(finalURL, DiscountResponse.class).getBody();
    }

    private DiscountResponse fallBackDiscountService(Throwable throwable){
        System.out.println("Exception for fallback");
        return new DiscountResponse("", 100.0);
    }
}
