package com.api.product.controller;

import com.api.product.dto.ProductRequest;
import com.api.product.dto.ProductResponse;
import com.api.product.service.ProductService;
import com.api.product.utility.ProductMapper;
import com.api.product.utility.ProductResponseMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
public class productController {
    private final ProductService productService;


    @PostMapping

    public ResponseEntity< ProductResponse> createProduct(@RequestHeader ProductRequest request){
        productService.createProduct(ProductMapper.mapToProduct(request));

        return ResponseEntity.status(HttpStatus.OK).body(ProductResponseMapper.mapToProductResponse(productService.createProduct(ProductMapper.mapToProduct(request))));
    }

    @GetMapping("/{id}")
    public ResponseEntity< ProductResponse> getProductById(@PathVariable  String id){
        return productService.findProductById(id)
                        .map(ProductResponseMapper::mapToProductResponse)
                        .map(ResponseEntity::ok)
                        .orElseGet(()-> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProduct(){
        List<ProductResponse> products = productService.getAllProducts()
                .stream()
                .map(ProductResponseMapper::mapToProductResponse)
                .toList();
        return ResponseEntity.ok(products);
    }

    @PutMapping
    public ResponseEntity<Void>  updateProduct(@RequestHeader("Product_id")  String id, @RequestBody @Valid ProductRequest request){
        if(!productService.updateProduct(id , ProductMapper.mapToProduct(request))){
            return  ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable  String id){
        if(!productService.removeProductById(id)){
           return ResponseEntity.status(HttpStatus.NOT_FOUND)
                   .body("product does not exist");
        }
       return ResponseEntity.status(HttpStatus.NO_CONTENT)
               .body("product removed successfully");
    }
}
