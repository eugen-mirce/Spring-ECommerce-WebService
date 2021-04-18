package com.project.app.ws.service.Impl;

import com.project.app.ws.exceptions.AddressServiceException;
import com.project.app.ws.exceptions.UserServiceException;
import com.project.app.ws.shared.Utils;
import com.project.app.ws.shared.dto.AddressDTO;
import com.project.app.ws.io.entity.AddressEntity;
import com.project.app.ws.io.entity.UserEntity;
import com.project.app.ws.io.repositories.AddressRepository;
import com.project.app.ws.io.repositories.UserRepository;
import com.project.app.ws.service.AddressService;
import com.project.app.ws.ui.model.response.ErrorMessages;
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

    @Autowired
    Utils utils;

    @Override
    public List<AddressDTO> getAddresses(String userId) {
        List<AddressDTO> returnValue = new ArrayList<>();   //If no addresses return empty array

        UserEntity userEntity = userRepository.findByUserId(userId);
        if(userEntity == null) return returnValue;

        Iterable<AddressEntity> addresses = addressRepository.findAllByUserDetails(userEntity);
        for(AddressEntity addressEntity: addresses) {
            returnValue.add( modelMapper.map(addressEntity,AddressDTO.class) );
        }

        return returnValue;
    }

    @Override
    public AddressDTO getAddress(String userId, String addressId) {
        UserEntity userEntity = userRepository.findByUserId(userId);
        if(userEntity == null) throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        AddressEntity addressEntity = addressRepository.findByAddressIdAndUserDetails(addressId,userEntity);
        if(addressEntity == null) throw new AddressServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        return modelMapper.map(addressEntity,AddressDTO.class);
    }

    @Override
    public AddressDTO addAddress(String userId, AddressDTO addressDetails) {
        UserEntity userEntity = userRepository.findByUserId(userId);
        if(userEntity == null) throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        AddressEntity addressEntity = modelMapper.map(addressDetails,AddressEntity.class);
        addressEntity.setAddressId(utils.generateAddressId(30));
        addressEntity.setUserDetails(userEntity);

        AddressEntity savedAddress = addressRepository.save(addressEntity);
        return modelMapper.map(savedAddress,AddressDTO.class);
    }
}
