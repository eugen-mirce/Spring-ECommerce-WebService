package com.project.app.ws.service.Impl;

import com.project.app.ws.exceptions.AddressServiceException;
import com.project.app.ws.exceptions.OrderServiceException;
import com.project.app.ws.exceptions.ProductServiceException;
import com.project.app.ws.exceptions.UserServiceException;
import com.project.app.ws.io.entity.*;
import com.project.app.ws.io.repositories.*;
import com.project.app.ws.service.OrderService;
import com.project.app.ws.shared.Utils;
import com.project.app.ws.shared.dto.OrderDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    InvoiceRepository invoiceRepository;

    @Autowired
    Utils utils;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public OrderDTO getOrder(long id) {
        OrderEntity order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderServiceException("Order Not Found"));

        return modelMapper.map(order, OrderDTO.class);
    }

    @Override
    public OrderDTO createOrder(OrderDTO orderDTO) {
        ProductEntity productEntity = productRepository.findById(orderDTO.getProductId())
                .orElseThrow(() -> new ProductServiceException("Product Not Found"));

        OrderEntity orderEntity = modelMapper.map(orderDTO,OrderEntity.class);
        orderEntity.setProductDetails(productEntity);

        return modelMapper.map(orderEntity, OrderDTO.class);
    }
}
