package com.project.app.ws.service.Impl;

import com.project.app.ws.shared.dto.AddressDTO;
import com.project.app.ws.io.entity.AddressEntity;
import com.project.app.ws.io.entity.UserEntity;
import com.project.app.ws.io.repositories.AddressRepository;
import com.project.app.ws.io.repositories.UserRepository;
import com.project.app.ws.service.AddressService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public List<AddressDTO> getAddresses(String userId) {
        List<AddressDTO> returnValue = new ArrayList<>();

        UserEntity userEntity = userRepository.findByUserId(userId);
        if(userEntity == null) return returnValue;

        Iterable<AddressEntity> addresses = addressRepository.findAllByUserDetails(userEntity);
        for(AddressEntity addressEntity: addresses) {
            returnValue.add( modelMapper.map(addressEntity,AddressDTO.class));
        }

        return returnValue;
    }

    @Override
    public AddressDTO getAddress(String addressId) {
        AddressDTO returnValue = null;

        AddressEntity addressEntity = addressRepository.findByAddressId(addressId);

        if(addressEntity != null) {
            returnValue = modelMapper.map(addressEntity,AddressDTO.class);
        }
        return returnValue;
    }
}
