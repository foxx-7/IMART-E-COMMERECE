package com.imart.product.utility;

import com.imart.product.dto.ProductResponse;
import com.imart.product.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductResponseMapper {
    public ProductResponse mapToProductResponse(Product product){
        ProductResponse response = new ProductResponse();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setBrand(product.getBrand());
        response.setPrice(product.getPrice());
        response.setStockQuantity(product.getStockQuantity());
        response.setDescription(product.getDescription());
        response.setCreationTimeStamp(product.getCreationTimestamp());
        response.setUpdateTimeStamp(product.getUpdateTimestamp());

        return response;
    }
}
