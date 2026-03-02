package com.api.product.utility;
import com.api.product.exception.FailedOperationException;
import com.api.product.model.Product;
import com.api.product.repository.ProductRepository;

public class ProductProcessor {
    public void validateProduct(Product product , ProductRepository productRepository){
        if(productRepository.existsByName(product.getName()) || productRepository.existsByBrand(product.getBrand()) || productRepository.existsByPrice(product.getPrice())){
            throw new FailedOperationException("product already exists");
        }
    }
}
