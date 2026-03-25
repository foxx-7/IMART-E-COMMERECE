package com.imart.user.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.imart.user.exception.NotFoundException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "user_profile")
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

    public void addAddress(Address address){
        address.setUserProfile(this);
        addresses.add(address);
    }

    public void removeAddress(Address address){
        address.setUserProfile(null);
        addresses.remove(address);
    }

    public Address getDefaultAddress(){
        if(addresses.isEmpty()){
            throw new NotFoundException("no address provided for user: " + userId );
        }
        for(Address address : addresses){
            if(address.isDefault()){
                return address;
            }
        }
        throw new NotFoundException("default address for user: " + userId + " not found");
    }
    public enum ROLE{
        ADMIN, CUSTOMER, SELLER;
    }
}