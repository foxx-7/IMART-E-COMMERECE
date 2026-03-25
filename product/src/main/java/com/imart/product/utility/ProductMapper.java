package com.imart.product.utility;

import com.imart.product.dto.ProductRequest;
import com.imart.product.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public Product mapToProduct(ProductRequest request){

        Product product = new Product();

        product.setName(request.getName());
        product.setBrand(request.getBrand());
        product.setPrice(request.getPrice());
        product.setStockQuantity(request.getStockQuantity());
        product.setDescription(request.getDescription());

        return product;
    }
}
