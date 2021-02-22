package com.project.app.controller;

import com.project.app.shared.dto.UserDto;
import com.project.app.ui.model.request.UserDetailsRequestModel;
import com.project.app.ui.model.response.UserRest;
import com.project.app.ws.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")        //http://localhost:8080/users
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping(path="/{id}")
    public UserRest getUser(@PathVariable String id) {
        UserRest returnValue = new UserRest();

        UserDto userDto = userService.getUserByUserId(id);
        BeanUtils.copyProperties(userDto,returnValue);

        return returnValue;
    }
    @PostMapping
    public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails) {
        UserRest returnValue = new UserRest();
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userDetails, userDto);

        UserDto createdUser = userService.createUser(userDto);
        BeanUtils.copyProperties(createdUser, returnValue);

        return returnValue;
    }
    @PutMapping
    public String updateUser() {
        return "update user was requested";
    }
    @DeleteMapping
    public String deleteUser() {
        return "delete user was requested";
    }
}
