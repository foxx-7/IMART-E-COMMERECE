package com.imart.user.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.imart.user.exception.NotFoundException;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "user_profile",
indexes = {
        @Index(name = "idx_email", columnList = "email"),
        @Index(name = "idx_phoneNumber",columnList = "phoneNumber")
},
        uniqueConstraints = {
        @UniqueConstraint(name = "uk_email",columnNames = "email"),
        @UniqueConstraint(name = "uk_phoneNumber",columnNames = "phoneNumber")
        })
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userId;//keycloak authentication id
    private String userName;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String contact;
    private String profileImageUri;
    private ROLE role = ROLE.CUSTOMER;

    @OneToMany(mappedBy = "userProfile" , cascade = CascadeType.ALL, fetch = FetchType.LAZY,orphanRemoval = true)
    @JsonManagedReference
    private List<Address> addresses= new ArrayList<>();

    @CreationTimestamp
    private LocalDateTime creationTime;
    @UpdateTimestamp
    private LocalDateTime updateTime;

    public enum ROLE{
        ADMIN, CUSTOMER, SELLER;
    }
}