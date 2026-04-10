package com.imart.order.model;

import com.imart.order.dto.foreign.Address;
import com.imart.order.dto.foreign.CartItem;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "order-table")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;

    @GeneratedValue(strategy = GenerationType.UUID)
    private String  checkOutId;

    private String transactionId;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "json")
    private List<CartItem> items;

    private BigDecimal checkOutAmount;
    private OrderStatus status;

    private boolean isActive;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "json")
    private Address shippingAddress;

    @CreationTimestamp
    private  LocalDateTime creationTimeStamp;

    @UpdateTimestamp
    private LocalDateTime updateTimeStamp;

}
