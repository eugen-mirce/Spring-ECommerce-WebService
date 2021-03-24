package com.project.app.ws.ui.controller;

import com.project.app.ws.service.Impl.UserServiceImpl;
import com.project.app.ws.shared.dto.AddressDTO;
import com.project.app.ws.shared.dto.UserDto;
import com.project.app.ws.ui.model.response.UserRest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @InjectMocks
    UserController userController;

    @Mock
    UserServiceImpl userService;

    UserDto userDto;
    String userId = "DSFfdgfdgfdsa";

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        userDto = new UserDto();
        userDto.setFirstName("FirstName");
        userDto.setLastName("LastName");
        userDto.setEmail("username@domain.tld");
        userDto.setEncryptedPassword("12345678");
        userDto.setEmailVerificationToken("12312dffdgfd");
        userDto.setEmailVerificationStatus(Boolean.FALSE);
        userDto.setUserId(userId);
        userDto.setAddresses(getAddresses());
    }

    @Test
    final void testGetUser() {
        when(userService.getUserByUserId(anyString())).thenReturn(userDto);
        UserRest userRest = userController.getUser(userId);
        assertNotNull(userRest);
        assertEquals(userRest.getUserId(),userDto.getUserId());
        assertEquals(userRest.getFirstName(),userDto.getFirstName());
        assertEquals(userRest.getLastName(),userDto.getLastName());
        assertEquals(userRest.getEmail(),userDto.getEmail());
        assertEquals(userRest.getAddresses().size(),userDto.getAddresses().size());

    }

    private List<AddressDTO> getAddresses() {
        AddressDTO shippingAddressDTO = new AddressDTO();
        shippingAddressDTO.setCity("Tirana");
        shippingAddressDTO.setCountry("Albania");
        shippingAddressDTO.setStreetName("Ali Demi");
        shippingAddressDTO.setPostalCode("1001");
        shippingAddressDTO.setType("shipping");

        AddressDTO billingAddressDTO = new AddressDTO();
        billingAddressDTO.setCity("Tirana");
        billingAddressDTO.setCountry("Albania");
        billingAddressDTO.setStreetName("Ali Demi");
        billingAddressDTO.setPostalCode("1001");
        billingAddressDTO.setType("billing");

        List<AddressDTO> addressesList= new ArrayList<>();
        addressesList.add(shippingAddressDTO);
        addressesList.add(billingAddressDTO);
        return addressesList;
    }
}
