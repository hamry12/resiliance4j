package com.resiliance.controller;

import com.resiliance.service.CatalogService;
import org.resilience.dto.CatalogResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CatalogController {

    @Autowired
    private CatalogService catalogService;

    @GetMapping("/order")
    public List<CatalogResponse> getCatalog(@RequestParam("category") String category){
        return catalogService.getCatalog(category);
    }

}
