package com.api.product.utility;

import com.api.product.dto.ProductRequest;
import com.api.product.model.Product;
import lombok.Data;
import org.springframework.context.annotation.Bean;

public class ProductMapper {
    public static Product mapToProduct(ProductRequest request){

        Product product = new Product();

        product.setName(request.getName());
        product.setBrand(request.getBrand());
        product.setPrice(request.getPrice());
        product.setStockQuantity(request.getStockQuantity());
        product.setDescription(request.getDescription());

        return product;
    }
}
