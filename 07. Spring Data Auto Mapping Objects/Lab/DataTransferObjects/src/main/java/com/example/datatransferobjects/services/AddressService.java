package com.example.datatransferobjects.services;

import com.example.datatransferobjects.entities.Address;
import com.example.datatransferobjects.entities.dtos.AddressDTO;

public interface AddressService {
    Address create(AddressDTO data);
}
