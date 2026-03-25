package com.imart.product.controller;

import com.imart.product.dto.ProductRequest;
import com.imart.product.dto.ProductResponse;
import com.imart.product.model.Product;
import com.imart.product.service.ProductService;
import com.imart.product.utility.ProductMapper;
import com.imart.product.utility.ProductResponseMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class productController {
    private final ProductMapper productMapper;
    private final ProductService productService;
    private final ProductResponseMapper productResponseMapper;


    @PostMapping("/v1/products")

    public ResponseEntity< ProductResponse> createProduct(@RequestBody @Valid ProductRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(productResponseMapper.mapToProductResponse
                (productService.createProduct(productMapper.mapToProduct(request))));
    }

    @GetMapping("/v1/products/{productId}")
    public ResponseEntity< ProductResponse> getProductById(@PathVariable  Long productId){
        return ResponseEntity.status(HttpStatus.OK)
                .body(productResponseMapper.mapToProductResponse(productService.findProductById(productId)));
    }

    @GetMapping("/1.0/admin/products")
    public ResponseEntity<List<Product>> getAllProduct(){
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @PatchMapping("/v1/products/{productId}")
    public ResponseEntity<String>  updateProduct(@PathVariable
                                                     Long productId, @RequestBody  Map<String, Object> request){
        productService.updateProduct(productId, request);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("product updated successfully");
    }

    @DeleteMapping("/v1/products/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable  Long productId){
        productService.removeProductById(productId);
       return ResponseEntity.status(HttpStatus.NO_CONTENT)
               .body("product removed successfully");
    }
}
