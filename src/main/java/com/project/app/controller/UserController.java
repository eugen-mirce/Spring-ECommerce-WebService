package com.project.app.controller;

import com.project.app.shared.dto.AddressDTO;
import com.project.app.shared.dto.UserDto;
import com.project.app.ui.model.request.UserDetailsRequestModel;
import com.project.app.ui.model.response.*;
import com.project.app.ws.exceptions.UserServiceException;
import com.project.app.ws.service.AddressService;
import com.project.app.ws.service.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("users")        //http://localhost:8080/users
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    AddressService addressService;

    @GetMapping(
            path="/{userId}",
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }
            )
    public UserRest getUser(@PathVariable String userId) {

        UserDto userDto = userService.getUserByUserId(userId);
        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(userDto,UserRest.class);
    }
    @PostMapping(
            consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }
            )
    public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails) throws UserServiceException {

        if(userDetails.getFirstName().isEmpty()) throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());

        ModelMapper modelMapper = new ModelMapper();
        UserDto userDto = modelMapper.map(userDetails,UserDto.class);

        UserDto createdUser = userService.createUser(userDto);

        return modelMapper.map(createdUser,UserRest.class);

    }
    @PutMapping(
            path = "/{userId}",
            consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }
            )
    public UserRest updateUser(@PathVariable String userId, @RequestBody UserDetailsRequestModel userDetails) {
        ModelMapper modelMapper = new ModelMapper();

        UserDto userDto = modelMapper.map(userDetails, UserDto.class);

        UserDto createdUser = userService.updateUser(userId,userDto);
        return modelMapper.map(createdUser, UserRest.class);
    }
    @DeleteMapping(
            path = "/{userId}",
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }
            )
    public OperationStatusModel deleteUser(@PathVariable String userId) {
        OperationStatusModel returnValue = new OperationStatusModel();
        returnValue.setOperationName(RequestOperationName.DELETE.name());
        userService.deleteUser(userId);
        returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());

        return returnValue;
    }

    @GetMapping(produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public List<UserRest> getUsers(@RequestParam(value="page", defaultValue = "1") int page, @RequestParam(value = "limit", defaultValue = "10") int limit) {
        List<UserRest> returnValue = new ArrayList<>();
        List<UserDto> users = userService.getUsers(page,limit);
        ModelMapper modelMapper = new ModelMapper();
        for(UserDto userDto : users) {
            UserRest userModel = modelMapper.map(userDto,UserRest.class);
            returnValue.add(userModel);
        }
        return returnValue;
    }

    //http://localhost:8080/users/{id}/addresses
    @GetMapping(
            path = "/{userId}/addresses",
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }
    )
    public List<AddressesRest> getUserAddresses(@PathVariable String userId) {
        List<AddressesRest> returnValue = new ArrayList<>();
        List<AddressDTO> addressDTO = addressService.getAddresses(userId);

        if(addressDTO != null && !addressDTO.isEmpty()) {
            Type listType = new TypeToken<List<AddressesRest>>() {}.getType();
            returnValue = new ModelMapper().map(addressDTO,listType);
        }
        return returnValue;
    }

    //http://localhost:8080/users/{id}/addresses/{aid}
    @GetMapping(
            path = "/{userId}/addresses/{addressId}",
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }
    )
    public AddressesRest getUserAddress(@PathVariable String addressId) {
        AddressDTO addressDTO = addressService.getAddress(addressId);

        return new ModelMapper().map(addressDTO,AddressesRest.class);
    }
}
