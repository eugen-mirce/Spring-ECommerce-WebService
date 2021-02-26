package com.project.app.controller;

import com.project.app.shared.dto.UserDto;
import com.project.app.ui.model.request.UserDetailsRequestModel;
import com.project.app.ui.model.response.ErrorMessages;
import com.project.app.ui.model.response.OperationStatusModel;
import com.project.app.ui.model.response.RequestOperationStatus;
import com.project.app.ui.model.response.UserRest;
import com.project.app.ws.exceptions.UserServiceException;
import com.project.app.ws.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("users")        //http://localhost:8080/users
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping(
            path="/{id}",
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }
            )
    public UserRest getUser(@PathVariable String id) {
        UserRest returnValue = new UserRest();

        UserDto userDto = userService.getUserByUserId(id);
        BeanUtils.copyProperties(userDto,returnValue);

        return returnValue;
    }
    @PostMapping(
            consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }
            )
    public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails) throws UserServiceException {
        UserRest returnValue = new UserRest();

        if(userDetails.getFirstName().isEmpty()) throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());

        /*      Changed To ModelMapper
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userDetails, userDto);
        */
        ModelMapper modelMapper = new ModelMapper();
        UserDto userDto = modelMapper.map(userDetails,UserDto.class);

        UserDto createdUser = userService.createUser(userDto);

        /* Changed to ModelMapper
        BeanUtils.copyProperties(createdUser, returnValue);
        */
        returnValue = modelMapper.map(createdUser,UserRest.class);

        return returnValue;
    }
    @PutMapping(
            path = "/{id}",
            consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }
            )
    public UserRest updateUser(@PathVariable String id, @RequestBody UserDetailsRequestModel userDetails) {
        UserRest returnValue = new UserRest();

        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userDetails, userDto);

        UserDto createdUser = userService.updateUser(id,userDto);
        BeanUtils.copyProperties(createdUser, returnValue);

        return returnValue;
    }
    @DeleteMapping(
            path = "/{id}",
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }
            )
    public OperationStatusModel deleteUser(@PathVariable String id) {
        OperationStatusModel returnValue = new OperationStatusModel();
        returnValue.setOperationName(RequestOperationName.DELETE.name());
        userService.deleteUser(id);
        returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());

        return returnValue;
    }

    @GetMapping(produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public List<UserRest> getUsers(@RequestParam(value="page", defaultValue = "1") int page, @RequestParam(value = "limit", defaultValue = "10") int limit) {
        List<UserRest> returnValue = new ArrayList<>();
        List<UserDto> users = userService.getUsers(page,limit);
        for(UserDto userDto : users) {
            UserRest userModel = new UserRest();
            BeanUtils.copyProperties(userDto,userModel);
            returnValue.add(userModel);
        }
        return returnValue;
    }
}
