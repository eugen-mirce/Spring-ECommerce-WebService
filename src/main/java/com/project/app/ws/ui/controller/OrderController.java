package com.project.app.ws.ui.controller;

import com.project.app.ws.service.OrderService;
import com.project.app.ws.shared.dto.OrderDTO;
import com.project.app.ws.ui.model.request.OrderRequestModel;
import com.project.app.ws.ui.model.response.OrderRest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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
}
