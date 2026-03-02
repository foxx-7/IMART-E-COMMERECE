package com.api.product.service;

import com.api.product.model.Product;
import com.api.product.repository.ProductRepository;
import com.api.product.utility.ProductProcessor;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@DynamicUpdate
@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public Product createProduct(Product product){
        ProductProcessor productValidator = new ProductProcessor();
        productValidator.validateProduct(product, productRepository);
        return productRepository.save(product);
    }

    public  Optional<Product> findProductById(String id){
        return productRepository.findById(Long.valueOf(id));
    }

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public boolean removeProductById(String id) {

        if (productRepository.existsById(Long.valueOf(id))) {
            return false;
        }
        productRepository.deleteById(Long.valueOf(id));
        return true;
    }

    public boolean updateProduct(String id, Product updatedProduct ){
        Optional<Product>  productOpt = productRepository.findById(Long.valueOf(id));
        if(productOpt.isEmpty()){
            return false;
        }
        Product product = productOpt.get();
        updateProduct(product, updatedProduct);
        productRepository.save(product);
        return true;
    }
    
    private void updateProduct(Product product, Product update){
        product.setName(update.getName());
        product.setBrand(update.getBrand());
        product.setPrice(update.getPrice());
        product.setDescription(update.getDescription());
        product.setStockQuantity(update.getStockQuantity());
    }
}

