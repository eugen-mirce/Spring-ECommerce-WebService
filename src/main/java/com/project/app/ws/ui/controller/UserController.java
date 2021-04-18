package com.project.app.ws.ui.controller;

import com.project.app.ws.service.InvoiceService;
import com.project.app.ws.shared.Roles;
import com.project.app.ws.shared.dto.AddressDTO;
import com.project.app.ws.shared.dto.InvoiceDTO;
import com.project.app.ws.shared.dto.OrderDTO;
import com.project.app.ws.shared.dto.UserDto;
import com.project.app.ws.ui.model.request.*;
import com.project.app.ws.ui.model.response.*;
import com.project.app.ws.exceptions.UserServiceException;
import com.project.app.ws.service.AddressService;
import com.project.app.ws.service.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("users")        //http://localhost:8080/users
public class UserController {
    private boolean skipVerification = true;

    @Autowired
    UserService userService;

    @Autowired
    AddressService addressService;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    InvoiceService invoiceService;

    /**
     * [GET] Get User Details
     * [Path] http://localhost:8080/app/users/{userId}
     * No Role Needed
     * @param userId
     * @return
     */
    //@PostAuthorize("hasRole('ADMIN') or returnObject.userId == principal.userId")
    @GetMapping(
            path = "/{userId}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
            )
    public UserRest getUser(@PathVariable String userId)
    {

        UserDto userDto = userService.getUserByUserId(userId);

        return modelMapper.map(userDto,UserRest.class);
    }

    /**
     * [POST] Create User
     * [Path] http://localhost:8080/app/users
     * No Role Needed
     * @param userDetails
     * @return
     * @throws UserServiceException
     */
    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
            )
    public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails)
            throws UserServiceException
    {

        if(userDetails.getFirstName().isEmpty()) throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
        if(userDetails.getLastName().isEmpty()) throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
        if(userDetails.getEmail().isEmpty()) throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
        if(userDetails.getPassword().isEmpty()) throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());

        UserDto userDto = modelMapper.map(userDetails,UserDto.class);
        userDto.setRoles(new HashSet<>(Arrays.asList(Roles.ROLE_USER.name())));
        UserDto createdUser = userService.createUser(userDto,skipVerification);

        if(createdUser.getAddresses() == null) createdUser.setAddresses(new ArrayList<>());

        return modelMapper.map(createdUser,UserRest.class);
    }

    /**
     * [PUT] Update User Details
     * [Path] http://localhost:8080/app/users/{userId}
     * No Role Needed
     * @param userId
     * @param userDetails
     * @return
     */
    @PutMapping(
            path = "/{userId}",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
            )
    public UserRest updateUser(@PathVariable String userId,
                               @RequestBody UserDetailsRequestModel userDetails)
    {

        UserDto userDto = modelMapper.map(userDetails, UserDto.class);

        UserDto createdUser = userService.updateUser(userId,userDto);
        return modelMapper.map(createdUser, UserRest.class);
    }

    /**
     * [DELETE] Delete User
     * [Path] http://localhost:8080/app/users/{userId}
     * //Admin Access Only
     * @param userId
     * @return
     */
    @PreAuthorize("hasRole('ROLE_ADMIN') or #id == principal.userId")
    @DeleteMapping(
            path = "/{userId}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
            )
    public OperationStatusModel deleteUser(@PathVariable String userId)
    {
        OperationStatusModel returnValue = new OperationStatusModel();
        returnValue.setOperationName(RequestOperationName.DELETE.name());
        userService.deleteUser(userId);
        returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());

        return returnValue;
    }

    /**
     * [GET] Get All Users
     * [Path] http://localhost:8080/app/users
     * //TODO Add Admin Access Only
     * @param page
     * @param limit
     * @return
     */
    @GetMapping(produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public List<UserRest> getUsers(@RequestParam(value="page", defaultValue = "1") int page,
                                   @RequestParam(value = "limit", defaultValue = "10") int limit)
    {
        List<UserRest> returnValue = new ArrayList<>();
        List<UserDto> users = userService.getUsers(page,limit);
        for(UserDto userDto : users) {
            UserRest userModel = modelMapper.map(userDto,UserRest.class);
            returnValue.add(userModel);
        }
        return returnValue;
    }

    /**
     * [GET] Get Users Addresses
     * [Path] http://localhost:8080/app/users/{userId}/addresses
     * No Role Needed
     * @param userId
     * @return
     */
    @GetMapping(
            path = "/{userId}/addresses",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, "application/hal+json" }
    )
    public List<AddressesRest> getUserAddresses(@PathVariable String userId)
    {
        List<AddressesRest> addressesListRestModel = new ArrayList<>();
        List<AddressDTO> addressDTO = addressService.getAddresses(userId);

        if(addressDTO != null && !addressDTO.isEmpty()) {
            Type listType = new TypeToken<List<AddressesRest>>() {}.getType();
            addressesListRestModel = modelMapper.map(addressDTO,listType);
        }
        return addressesListRestModel;
    }

    /**
     * [GET] Get User Specific Address
     * [Path] http://localhost:8080/app/users/{userId}/addresses/{addressId}
     * @param userId
     * @param addressId
     * @return
     */
    @GetMapping(
            path = "/{userId}/addresses/{addressId}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, "application/hal+json" }
    )
    public AddressesRest getUserAddress(@PathVariable String userId,
                                        @PathVariable String addressId)
    {
        AddressDTO addressDTO = addressService.getAddress(userId,addressId);
        return modelMapper.map(addressDTO,AddressesRest.class);
    }

    /**
     * [POST] Add Address To Specific User
     * [Path] = http://localhost:8080/app/users/{userId}/addresses
     * @param userId
     * @param addressRequestModel
     * @return
     */
    @PostMapping(
            path = { "/{userId}/addresses", "/{userId}/addresses/" }
    )
    public AddressesRest addAddress(@PathVariable String userId,
                                    @RequestBody AddressRequestModel addressRequestModel
    ) {
        AddressDTO addressDTO = modelMapper.map(addressRequestModel,AddressDTO.class);

        AddressDTO address = addressService.addAddress(userId,addressDTO);
        return modelMapper.map(address,AddressesRest.class);
    }

    /**
     * [POST] Create Invoice With Order(s)
     * [Path] = http://localhost:8080/app/users/{userId}/order
     * @param userId
     * @param ordersRequestModel
     * @return
     */
    @PostMapping(
            path = { "/{userId}/order", "/{userId}/order/"}
    )
    public InvoiceRest createOrder(@PathVariable String userId, @RequestBody OrdersRequestModel ordersRequestModel) {
        InvoiceRest returnValue = new InvoiceRest();

        InvoiceDTO invoiceDTO = new InvoiceDTO();
        List<OrderRequestModel> orders = ordersRequestModel.getOrders();

        List<OrderDTO> listOrders = new ArrayList<>();
        for(OrderRequestModel order : orders) {
            listOrders.add(modelMapper.map(order,OrderDTO.class));
        }

        invoiceDTO.setUserId(userId);
        invoiceDTO.setOrders(listOrders);
        invoiceDTO.setAddressId(ordersRequestModel.getAddressId());

        InvoiceDTO savedInvoice = invoiceService.createInvoice(invoiceDTO);
        returnValue = modelMapper.map(savedInvoice,InvoiceRest.class);
        return returnValue;
    }

    /**
     * [GET] Get All Invoices For User
     * [Path] = http://localhost:8080/app/users/{userId}/invoices
     * @param userId
     * @param page
     * @param limit
     * @return
     */
    @GetMapping(
            path = { "/{userId}/invoices", "/{userId}/invoices/" },
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public List<InvoiceRest> getInvoices(
            @PathVariable String userId,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "limit", defaultValue = "10") int limit)
    {
        List<InvoiceDTO> invoices = invoiceService.getUserInvoices(userId,page,limit);
        return modelMapper.map(invoices,new TypeToken<List<InvoiceRest>>() {}.getType());
    }

    /**
     * [GET] Get Invoice For User
     * [Path] = http://localhost:8080/app/users/{userId}/invoices
     * @param userId
     * @param invoiceId
     * @return
     */
    @GetMapping(
            path = { "/{userId}/invoices/{invoiceId}", "/{userId}/invoices/{invoiceId}/" },
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public InvoiceRest getInvoice(@PathVariable String userId,
                                  @PathVariable long invoiceId)
    {
        InvoiceDTO invoices = invoiceService.getInvoice(userId,invoiceId);
        return modelMapper.map(invoices,InvoiceRest.class);
    }


    /**
     * [GET] Verify Email
     * [Path] http://localhost:8080/app/users/email-verification
     * @param token
     * @return
     */
    @GetMapping(
            path = "/email-verification",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public OperationStatusModel verifyEmailToken(@RequestParam(value = "token") String token)
    {
        OperationStatusModel returnValue = new OperationStatusModel();
        returnValue.setOperationName(RequestOperationName.VERIFY_EMAIL.name());

        boolean isVerified = userService.verifyEmailToken(token);

        if(isVerified) {
            returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
        } else {
            returnValue.setOperationResult(RequestOperationStatus.ERROR.name());
        }

        return returnValue;
    }

    /**
     * [POST] Request Password Reset
     * [Path] http://localhost:8080/app/users/password-reset-request
     * No Role Needed
     * @param passwordResetRequestModel
     * @return
     */
    @PostMapping(
            path = "/password-reset-request",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public OperationStatusModel requestReset(@RequestBody PasswordResetRequestModel passwordResetRequestModel)
    {
        OperationStatusModel returnValue = new OperationStatusModel();

        boolean operationResult = userService.requestPasswordReset(passwordResetRequestModel.getEmail());

        returnValue.setOperationName(RequestOperationName.REQUEST_PASSWORD_RESET.name());
        returnValue.setOperationResult(RequestOperationStatus.ERROR.name());

        if(operationResult == true) {
            returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
        }
        return returnValue;
    }

    /**
     * [POST] Password Reset
     * [Path] http://localhost:8080/app/users/password-reset
     * No Role Needed
     * @param passwordResetModel
     * @return
     */
    @PostMapping(
            path = "/password-reset",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public OperationStatusModel resetPassword(@RequestBody PasswordResetModel passwordResetModel)
    {
        OperationStatusModel returnValue = new OperationStatusModel();

        boolean operationResult = userService.resetPassword(
                passwordResetModel.getToken(),
                passwordResetModel.getPassword());

        returnValue.setOperationName(RequestOperationName.PASSWORD_RESET.name());
        returnValue.setOperationResult(RequestOperationStatus.ERROR.name());

        if(operationResult == true) {
            returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
        }
        return returnValue;
    }
}
