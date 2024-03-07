package com.example.datatransferobjects.services.impl;

import com.example.datatransferobjects.repositories.AddressRepository;
import com.example.datatransferobjects.services.AddressService;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;

    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

}
