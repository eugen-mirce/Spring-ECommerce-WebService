package com.project.app.ws.service;

import com.project.app.ws.shared.dto.AddressDTO;

import java.util.List;

public interface AddressService {
    List<AddressDTO> getAddresses(String userId);
    AddressDTO getAddress(String userId, String addressId);
    AddressDTO addAddress(String userId, AddressDTO addressDetails);
}
