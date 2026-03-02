package com.api.product.utility;

import com.api.product.dto.ProductResponse;
import com.api.product.model.Product;

public class ProductResponseMapper {
    public static ProductResponse mapToProductResponse(Product product){
        ProductResponse response = new ProductResponse();

        response.setName(product.getName());
        response.setBrand(product.getBrand());
        response.setPrice(product.getPrice());
        response.setStockQuantity(product.getStockQuantity());
        response.setDescription(product.getDescription());

        return response;
    }
}
