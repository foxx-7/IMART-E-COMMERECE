package com.imart.user.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.aspectj.bridge.IMessage;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "address-table")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ref_user_id",nullable = false)
    private UserProfile userProfile;
    private Long userId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String zipCode;
    private String street;
    private String country;
    private String state;
    private String city;
    private boolean isValidated;
    private boolean isDefault;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;


}
