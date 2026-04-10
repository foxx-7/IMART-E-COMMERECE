package com.imart.product.utility;

import com.imart.product.exception.InvalidUpdateFieldException;
import com.imart.product.model.Product;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;

@Component
public class ProductUpdateMapper {

    public void updateProductData(Product existingProduct, Map<String, Object> productUpdate){
        productUpdate.forEach((key, value) -> {
            switch (key) {
                case "name" -> existingProduct.setName((String) value);
                case "price" -> {
                    existingProduct.setPrice(BigDecimal.valueOf(Long.parseLong(String.valueOf(value))));
                }
                case "stockQuantity" -> {
                    existingProduct.setStockQuantity((Long) value);
                }
                case "description" -> {
                    existingProduct.setDescription((String) value);
                }

                default -> throw new InvalidUpdateFieldException("invalid field: " + key);
            }
        } );
    }
}
