package com.imart.product.service;

import com.imart.product.exception.NotFoundException;
import com.imart.product.model.Product;
import com.imart.product.repository.ProductRepository;
import com.imart.product.utility.ProductUpdateMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductUpdateMapper productUpdateMapper;

    public Product createProduct(Product product){
        return productRepository.save(product);
    }

    public  Product findProductById(Long productId){

        Optional<Product> productOpt = productRepository.findById(productId);
        if (productOpt.isEmpty()){
            throw new NotFoundException("product not found");
        }
        return productOpt.get();
    }

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public void  removeProductById(Long productId) {

        if (!productRepository.existsById(productId)){
            throw new NotFoundException("product not found");
        }
        productRepository.deleteById(productId);
    }

    public void  updateProduct(Long productId, Map<String, Object> productUpdate){
        Optional<Product>  productOpt = productRepository.findById(productId);
        if(productOpt.isEmpty()){
            throw new NotFoundException("product not found");
        }
        Product existingproduct = productOpt.get();
        productUpdateMapper.updateProductData(existingproduct, productUpdate);
        productRepository.save(existingproduct);
    }
}

