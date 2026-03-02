package com.api.user.model;


import jakarta.annotation.Nonnull;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "user_table")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String contact;
    private Role role = Role.CUSTOMER;

    //@JoinColumn(name = "user_address")
    //@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private String address;
    @CreationTimestamp
    private LocalDateTime creationTime;
    @UpdateTimestamp
    private LocalDateTime updateTime;


    public enum Role{
        CUSTOMER, ADMIN, SELLER
    }
}