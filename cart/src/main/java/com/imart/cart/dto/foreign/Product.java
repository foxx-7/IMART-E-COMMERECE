package com.imart.cart.dto.foreign;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Product {
    private Long id;
    //because of potential price fluctuation price snapshot will be taken at time of adding to cart
    private BigDecimal price;
}
