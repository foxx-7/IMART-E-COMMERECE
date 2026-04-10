package com.imart.user.service;

import com.imart.user.dto.AddressRequest;
import com.imart.user.model.Address;
import com.imart.user.repository.AddressRepository;
import com.imart.user.utility.AddressMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;

    public Address addAddress(AddressRequest request){
        final Long userId = request.getUserId();
        List<Address > addressList = addressRepository.findAllByUserId(userId);
        if(!addressList.isEmpty()){
           for(Address address:addressList){
               if(address.getPhoneNumber().equals(request.getPhoneNumber()) || address.getEmail().equals(request.getEmail())){
                   throw new IllegalArgumentException("address already exists for user:"+userId);
               }
               Address newAddress = AddressMapper.mapToAddress(request);
               newAddress.setDefault(false);
               newAddress.setValidated(true);
              addressRepository.save(address);
              return address;
           }
        }
        Address newAddress = AddressMapper.mapToAddress(request);
        newAddress.setDefault(true);
        newAddress.setValidated(true);
        addressRepository.save(newAddress);
        return newAddress;
    }
}
