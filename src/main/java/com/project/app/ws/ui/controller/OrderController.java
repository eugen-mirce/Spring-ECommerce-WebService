package com.project.app.ws.ui.controller;

import com.project.app.ws.service.OrderService;
import com.project.app.ws.shared.dto.AddressDTO;
import com.project.app.ws.shared.dto.OrderDTO;
import com.project.app.ws.ui.model.request.OrderRequestModel;
import com.project.app.ws.ui.model.request.OrdersRequestModel;
import com.project.app.ws.ui.model.response.AddressesRest;
import com.project.app.ws.ui.model.response.OrderRest;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    ModelMapper modelMapper;

    @GetMapping(
            path = { "/{orderId}", "/{orderId}/"},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public OrderRest getOrder(@PathVariable String orderId){
        OrderDTO orderDTO = orderService.getOrder(orderId);

        return modelMapper.map(orderDTO,OrderRest.class);
    }

    @GetMapping(
            path = { "/user/{userId}", "/user/{userId}/"},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public List<OrderRest> getOrders(
            @PathVariable String userId,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "limit", defaultValue = "20") int limit
    ){
        List<OrderRest> returnValue = new ArrayList<>();

        List<OrderDTO> orders = orderService.getOrders(userId,page,limit);

        for(OrderDTO orderDTO : orders) {
            OrderRest orderRest = modelMapper.map(orderDTO,OrderRest.class);
            returnValue.add(orderRest);
        }
        return returnValue;
    }

    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public OrderRest createOrder(@RequestBody OrderRequestModel orderRequestModel) {
        OrderDTO orderDTO = modelMapper.map(orderRequestModel, OrderDTO.class);

        OrderDTO savedOrder = orderService.createOrder(orderDTO);

        return modelMapper.map(savedOrder,OrderRest.class);
    }
    @PostMapping(
            path = { "/cart", "/cart/"},
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public List<OrderRest> createOrders(@RequestBody OrdersRequestModel ordersRequestModel) {
        List<OrderRest> ordersRestList = new ArrayList<>();

        String userId = ordersRequestModel.getUserId();

        Type listType = new TypeToken<List<OrderDTO>>() {}.getType();
        List<OrderDTO> orderDTOList = modelMapper.map(ordersRequestModel.getOrders(),listType);

        for(OrderDTO orderDTO: orderDTOList) {
            orderDTO.setUserId(userId);
            OrderRest orderRest = modelMapper.map(orderService.createOrder(orderDTO),OrderRest.class);
            ordersRestList.add(orderRest);
        }

        return ordersRestList;
    }
}
