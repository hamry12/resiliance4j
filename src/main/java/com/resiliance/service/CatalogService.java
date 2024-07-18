package com.resiliance.service;

import org.resilience.dto.CatalogResponse;

import java.util.List;

public interface CatalogService {

    List<CatalogResponse> getCatalog(String categoryName);
}
